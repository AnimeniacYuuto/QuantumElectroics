package yuuto.quantumelectronics.gui.container.base;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import yuuto.quantumelectronics.tile.TileGridFurnace;
import yuuto.quantumelectronics.transport.IGridTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class ContainerGridMachine<T extends IGridTile> extends Container{
	protected T tile;
	InventoryPlayer playerInv;
	int firstTileSlot;
	int lastTileSlot;
	int firstPlayerSlot;
	int lastPlayerSlot;
	
	int lastEnergy;
	public ContainerGridMachine(T tile, EntityPlayer player){
		super();
		this.tile = tile;
		playerInv = player.inventory;
		firstTileSlot = this.inventorySlots.size();
		int y = bindTile();
		lastTileSlot = this.inventorySlots.size()-1;
		firstPlayerSlot = this.inventorySlots.size();
		bindPlayerInventory(8, y);
		lastPlayerSlot = this.inventorySlots.size()-1;
	}
	public abstract int bindTile();
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
	public void addCraftingToCrafters(ICrafting crafter){
		super.addCraftingToCrafters(crafter);
		crafter.sendProgressBarUpdate(this, 0, tile.getEnergyStored(ForgeDirection.UNKNOWN));
    }
	@Override
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		for(int i = 0; i < this.crafters.size(); i++){
			ICrafting crafter = (ICrafting)this.crafters.get(i);
			if(lastEnergy != this.tile.getEnergyStored(ForgeDirection.UNKNOWN)){
				crafter.sendProgressBarUpdate(this, 0, this.tile.getEnergyStored(ForgeDirection.UNKNOWN));
				lastEnergy = this.tile.getEnergyStored(ForgeDirection.UNKNOWN);
			}
		}
	}
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int type, int val){
		if(type == 0){
			tile.setEnergy(val);
		}
	}
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot){
		if(lastTileSlot < firstTileSlot)
			return null;
		ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
                ItemStack stackInSlot = slotObject.getStack();
                stack = stackInSlot.copy();

                //merges the item into player inventory since its in the tileEntity
                if (slot < lastTileSlot) {
                    if (!this.mergeItemStack(stackInSlot, firstPlayerSlot, lastPlayerSlot, true)) {
                            return null;
                    }
                }
                //places it into the tileEntity is possible since its in the player inventory
                else if (!this.mergeItemStack(stackInSlot, firstTileSlot, lastTileSlot, false)) {
                        return null;
                }

                if (stackInSlot.stackSize == 0) {
                        slotObject.putStack(null);
                } else {
                        slotObject.onSlotChanged();
                }

                if (stackInSlot.stackSize == stack.stackSize) {
                        return null;
                }
                slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
        
	}
	

}
