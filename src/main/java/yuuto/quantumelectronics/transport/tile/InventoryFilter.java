package yuuto.quantumelectronics.transport.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryFilter implements IInventory{

	ItemStack[] inv;
	IInventoryParrent parrent;
	
	public InventoryFilter(int size, IInventoryParrent parrent){
		inv = new ItemStack[size];
		this.parrent = parrent;
	}
	
	@Override
	public int getSizeInventory() {
		return inv.length;
	}
	@Override
	public ItemStack getStackInSlot(int slot) {
		return inv[slot];
	}
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (this.inv[slot] != null)
        {
            ItemStack itemstack;

            if (this.inv[slot].stackSize <= amount)
            {
                itemstack = this.inv[slot];
                this.inv[slot] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.inv[slot].splitStack(amount);

                if (this.inv[slot].stackSize == 0)
                {
                    this.inv[slot] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}
	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return getStackInSlot(slot);
	}
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.inv[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
		
	}
	@Override
	public String getInventoryName() {
		return "Filter";
	}
	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}
	@Override
	public void openInventory() {markDirty();}
	@Override
	public void closeInventory() {markDirty();}
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	@Override
	public void markDirty() {
		parrent.onInventoryUpdate(this);		
	}
	public ItemStack[] getItemsFiltered(){
		return inv.clone();
	}
	public boolean isEmpty(){
		for(int i = 0; i < inv.length; i++){
			if(inv[i] != null)
				return false;
		}
		return true;
	}
	
	public void readFromNBT(NBTTagCompound nbt){
	    NBTTagList invList = nbt.getTagList("Inventory", 10);
		for(int i = 0; i < invList.tagCount(); i++){
			NBTTagCompound tag = invList.getCompoundTagAt(i);
			int slot = tag.getByte("Slot");
			inv[slot] = ItemStack.loadItemStackFromNBT(tag);
		}
	}
	public void writeToNBT(NBTTagCompound nbt){
		NBTTagList invList = new NBTTagList();
    	for(int i = 0; i < inv.length; i++){
    		if(inv[i] == null)
    			continue;
    		NBTTagCompound tag = new NBTTagCompound();
    		tag.setByte("Slot", (byte)i);
    		inv[i].writeToNBT(tag);
    		invList.appendTag(tag);
    	}
    	nbt.setTag("Inventory", invList);
	}
	
	

}
