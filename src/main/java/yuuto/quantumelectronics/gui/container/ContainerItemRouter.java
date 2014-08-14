package yuuto.quantumelectronics.gui.container;

import yuuto.quantumelectronics.gui.slot.SlotPhantom;
import yuuto.quantumelectronics.transport.tile.TileItemRouter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerItemRouter extends Container{

	TileItemRouter tile;
	InventoryPlayer playerInv;
	public ContainerItemRouter(TileItemRouter tile, EntityPlayer player){
		this.tile = tile;
		playerInv = player.inventory;
		bindTile();
		tile.getFilter().openInventory();
		bindPlayerInventory(8, 84);
	}
	public void bindTile(){
		for(int i = 0; i < 9; i++){
			addSlotToContainer(new SlotPhantom(tile.getFilter(), i, 8+18*i, 35));
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
		if(!(inventorySlots.get(slotId) instanceof SlotPhantom))
			return super.slotClick(slotId, button, isShift, player);
		SlotPhantom slot = (SlotPhantom)inventorySlots.get(slotId);
		ItemStack held = player.inventory.getItemStack();
		if(tile.isSupplier()){
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
	@Override
	public void onContainerClosed(EntityPlayer player){
		super.onContainerClosed(player);
		tile.getFilter().closeInventory();
	}

}
