package yuuto.quantumelectronics.tile;

import yuuto.quantumelectronics.recipe.GrinderRecipes;
import yuuto.quantumelectronics.tile.base.IMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileGemGrinder extends TileEntity implements ISidedInventory, IMachine{

	String unlocName = "GemGrinder.";
	protected ForgeDirection orientation = ForgeDirection.getOrientation(2);
	int grindTime = 0;
	static int maxGrindTime = 8;
	boolean needsUpdate = true;
	
	ItemStack[] inv = new ItemStack[2];
	
	static int[] side = new int[]{0}, bottom = new int[]{1};
	
	public TileGemGrinder(){
		super();
	}
	public TileGemGrinder(int meta){
		super();
		switch(meta){
		default:
		case 0:
			unlocName+="Paridot";
			break;
		case 1:
			unlocName+="Ruby";
			break;
		case 2:
			unlocName+="Sapphire";
			break;
		}
	}
	@Override
    public void readFromNBT(NBTTagCompound nbt){
    	super.readFromNBT(nbt);
    	System.out.println("Reading NBT");
    	unlocName = nbt.getString("GemGrinderName");
    	orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
    	
    	grindTime = nbt.getInteger("GrindTime");
    	NBTTagList invList = nbt.getTagList("Inventory", 10);
		for(int i = 0; i < invList.tagCount(); i++){
			NBTTagCompound tag = invList.getCompoundTagAt(i);
			int slot = tag.getByte("Slot");
			inv[slot] = ItemStack.loadItemStackFromNBT(tag);
		}
    	
    	this.markDirty();
    }
    @Override
    public void writeToNBT(NBTTagCompound nbt){
    	super.writeToNBT(nbt);
    	System.out.println("Writing NBT");
    	nbt.setString("GemGrinderName", unlocName);
    	nbt.setInteger("Orientation", orientation.ordinal());
    	
    	nbt.setInteger("GrindTime", grindTime);
    	
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
	
	
	
	@Override
	public void updateEntity(){
		if(!canGrind()){
			grindTime = 0;
		}
	}
	public void grind(){
		if(canGrind()){
			System.out.println("Activated "+grindTime);
			grindTime++;
			if(grindTime == maxGrindTime){
				grindItem();
				grindTime = 0;
				markDirty();
			}
		}else{
			grindTime = 0;
		}
	}
	protected boolean canGrind(){
		if (inv[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = GrinderRecipes.instance.getResult(inv[0]);
            if (itemstack == null){System.out.println("Activated null"); return false;}
            if (inv[1] == null) return true;
            if (!inv[1].isItemEqual(itemstack)) return false;
            int result = inv[1].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.inv[1].getMaxStackSize();
        }
	}
	protected void grindItem(){
		if (this.canGrind())
        {
            ItemStack itemstack = GrinderRecipes.instance.getResult(inv[0]);

            if (this.inv[1] == null)
            {
                this.inv[1] = itemstack.copy();
            }
            else if (this.inv[1].getItem() == itemstack.getItem())
            {
                this.inv[1].stackSize += itemstack.stackSize;
            }

            --this.inv[0].stackSize;

            if (this.inv[0].stackSize <= 0)
            {
                this.inv[0] = null;
            }
        }
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
		return unlocName;
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
		return GrinderRecipes.instance.getResult(stack) != null;
	}
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? this.bottom : side == 1 ? null : this.side;
	}
	@Override
	public boolean canInsertItem(int slot, ItemStack stack,
			int side) {
		if(side == 1 || side == 0)
			return false;
		return isItemValidForSlot(slot, stack);
	}
	@Override
	public boolean canExtractItem(int slot, ItemStack stack,
			int side) {
		if(slot != 1 || side != 0)
			return false;
		return true;
	}
	@Override
	public ForgeDirection getOrientation(){
		return orientation;
	}
	@Override
	public ForgeDirection setOrientation(ForgeDirection dir){
		orientation = dir;
		return orientation;
	}
	@Override
	public ForgeDirection rotateAround(ForgeDirection axis){
		orientation = orientation.getRotation(axis);
		return orientation;
	}
	@Override
	public boolean isActive(){
		return false;
	}
}
