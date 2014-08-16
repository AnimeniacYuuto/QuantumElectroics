package yuuto.quantumelectronics.gui.container;

import yuuto.quantumelectronics.ModItems;
import yuuto.quantumelectronics.gui.slot.SlotFluidPhantom;
import yuuto.quantumelectronics.gui.slot.SlotPhantom;
import yuuto.quantumelectronics.transport.module.ModuleFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import static yuuto.quantumelectronics.ModItems.MODULES;

public class ContainerModule extends Container{
	
	ItemStack module;
	ModuleFilter inventory;
	InventoryPlayer playerInv;
	public ContainerModule(ItemStack module, EntityPlayer player){
		this.module = module;
		this.inventory = MODULES.openInventory(module);
		playerInv = player.inventory;
		this.inventory.openInventory();
		if(module.getItemDamage() > 1 && module.getItemDamage() <= 6)
			bindItemInventory();
		if(module.getItemDamage() > 6 && module.getItemDamage() <= 9)
			bindFluidInventory();
		bindPlayerInventory(8, 84);
	}
	public void bindItemInventory(){
		for(int i = 0; i < 9; i++){
			addSlotToContainer(new SlotPhantom(inventory, i, 8+18*i, 35));
		}
	}
	public void bindFluidInventory(){
		for(int i = 0; i < 9; i++){
			addSlotToContainer(new SlotFluidPhantom(inventory, i, 8+18*i, 35));
		}
	}
	public void bindPlayerInventory(int x, int y){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 9; j++){
				addSlotToContainer(new Slot(playerInv, i*9+j+9, x+18*j, y+18*i));
			}
		}
		for(int j = 0; j < 9; j++){
			addSlotToContainer(new Slot(playerInv, j, x+18*j, y+18*3+4));
		}	
	}
	@Override
	public ItemStack slotClick(int slotId, int button, 
			int isShift, EntityPlayer player){
		if(slotId < 0 || slotId >= inventorySlots.size() || module == null)
			return super.slotClick(slotId, button, isShift, player);
		if(!(inventorySlots.get(slotId) instanceof SlotPhantom))
			return super.slotClick(slotId, button, isShift, player);
		if(module.getItemDamage() > 1 && module.getItemDamage() <= 6)
			return slotClickItemFilter(slotId, button, isShift, player);
		if(module.getItemDamage() > 6 && module.getItemDamage() <= 9)
			return slotClickFluidFilter(slotId, button, isShift, player);
		return null;
	}
	public ItemStack slotClickItemFilter(int slotId, int button, 
			int isShift, EntityPlayer player){
		SlotPhantom slot = (SlotPhantom)inventorySlots.get(slotId);
		ItemStack held = player.inventory.getItemStack();
		if(module.getItemDamage() == 5 || module.getItemDamage() == 6){
			if(!slot.getHasStack() && held	!= null){
				ItemStack stack = held.copy();
				slot.putStack(stack);
				return held;
			}
			if(slot.getHasStack()){
				if(slot.getStack().stackSize < 128){
					if(button == 0){
						if(isShift == 0){
							slot.getStack().stackSize+= 1;
						}else{
							slot.getStack().stackSize+= 10;
						}
						if(slot.getStack().stackSize > 128)
							slot.getStack().stackSize = 128;
					}else{
						if(isShift == 0){
							slot.decrStackSize(1);
						}else{
							slot.decrStackSize(10);
						}
					}
					return slot.getStack();
				}
			}
		}
		if(isShift == 0 && held != null){
			ItemStack stack = held.copy();
			stack.stackSize = 1;
			slot.putStack(stack);
			return held;
		}
		slot.putStack(null);
		return held;
	}
	public ItemStack slotClickFluidFilter(int slotId, int button, 
			int isShift, EntityPlayer player){
		SlotFluidPhantom slot = (SlotFluidPhantom)inventorySlots.get(slotId);
		ItemStack held = player.inventory.getItemStack();
		if(held == null){
			slot.putStack(null);
			return null;
		}
		if(!(held.getItem() instanceof IFluidContainerItem)){
			if(!FluidContainerRegistry.isFilledContainer(held)){
				slot.putStack(null);
				return held;
			}
			FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(held);
			if(fluid == null){
				slot.putStack(null);
				return held;
			}
			ItemStack stack = new ItemStack(ModItems.FLUID_DUMMY);
			((IFluidContainerItem)stack.getItem()).fill(stack, fluid, true);
			slot.putStack(stack);
			return held;		
		}
		FluidStack fluid = ((IFluidContainerItem)held.getItem()).getFluid(held);
		if(isShift == 0 && fluid != null){
			ItemStack stack = new ItemStack(ModItems.FLUID_DUMMY);
			((IFluidContainerItem)stack.getItem()).fill(stack, fluid, true);
			slot.putStack(stack);
			return held;
		}
		slot.putStack(null);
		return held;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	@Override
	public void onContainerClosed(EntityPlayer player){
		super.onContainerClosed(player);
		if(!player.worldObj.isRemote)
			inventory.closeInventory();
	}

}
