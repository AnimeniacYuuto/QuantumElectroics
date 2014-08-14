package yuuto.quantumelectronics.gui.container;

import yuuto.quantumelectronics.ModItems;
import yuuto.quantumelectronics.gui.slot.SlotFluidPhantom;
import yuuto.quantumelectronics.gui.slot.SlotPhantom;
import yuuto.quantumelectronics.transport.tile.TileFluidRouter;
import yuuto.quantumelectronics.transport.tile.TileItemRouter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

public class ContainerFluidRouter extends Container{

	TileFluidRouter tile;
	InventoryPlayer playerInv;
	public ContainerFluidRouter(TileFluidRouter tile, EntityPlayer player){
		this.tile = tile;
		playerInv = player.inventory;
		bindTile();
		tile.getFilter().openInventory();
		bindPlayerInventory(8, 84);
	}
	public void bindTile(){
		for(int i = 0; i < 9; i++){
			addSlotToContainer(new SlotFluidPhantom(tile.getFilter(), i, 8+18*i, 35));
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
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot){
		return null;
	}
	
	@Override
	public ItemStack slotClick(int slotId, int button, 
			int isShift, EntityPlayer player){
		if(slotId < 0 || slotId >= inventorySlots.size())
			return super.slotClick(slotId, button, isShift, player);
		if(!(inventorySlots.get(slotId) instanceof SlotFluidPhantom))
			return super.slotClick(slotId, button, isShift, player);
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
	public void onContainerClosed(EntityPlayer player){
		super.onContainerClosed(player);
		tile.getFilter().closeInventory();
	}

}
