package yuuto.quantumelectronics.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import yuuto.quantumelectronics.transport.TileGridNode;
import yuuto.quantumelectronics.transport.TileGridTile;

public class TileGridFurnace extends TileGridNode implements ISidedInventory{

	ItemStack[] inv = new ItemStack[2];
	ItemStack smelting;
	int smeltEnergy = 0;
	static int maxSmeltEnergy = 16000;
	static int energyPerTick = 40;
	
	int[] slots = new int[]{0,1};
	
	@Override
	public void doWork() {
		if(smelting != null){
			smeltEnergy += this.storage.extractEnergy(energyPerTick, false);
			if(smeltEnergy >= maxSmeltEnergy){
				finishSmelt();
			}
		}else{
			if(canSmelt())
				startSmelt();
		}
	}
	
	protected boolean canSmelt(){
		if (this.getStackInSlot(0) == null){
            return false;
        }
		if(this.smelting != null)
			return false;
        else{
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(inv[0]);
            if (itemstack == null) return false;
            if (inv[1] == null) return true;
            if (!inv[1].isItemEqual(itemstack)) return false;
            int result = inv[1].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= inv[1].getMaxStackSize();
        }
	}
	protected void startSmelt(){
		if(canSmelt()){
			smelting = inv[0].splitStack(1);
		}
	}
	protected void finishSmelt(){
        if(smelting == null)
        	return;
		ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(smelting);

        if (this.inv[1] == null){
            this.inv[1] = itemstack.copy();
        }else if (this.inv[1].getItem() == itemstack.getItem()){
            this.inv[1].stackSize += itemstack.stackSize;
        }

        smelting = null;
        smeltEnergy-=maxSmeltEnergy;
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
		return "GridFurnace";
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
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot != 0)
			return false;
		return FurnaceRecipes.smelting().getSmeltingResult(stack) != null;
	}
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slots;
	}
	@Override
	public boolean canInsertItem(int slot, ItemStack stack,
			int side) {
		return isItemValidForSlot(slot, stack);
	}
	@Override
	public boolean canExtractItem(int slot, ItemStack stack,
			int side) {
		if(slot != 1)
			return false;
		return true;
	}

}
