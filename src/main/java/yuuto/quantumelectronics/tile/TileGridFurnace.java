package yuuto.quantumelectronics.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import yuuto.quantumelectronics.tile.base.IMachine;
import yuuto.quantumelectronics.transport.TileGridNode;
import yuuto.quantumelectronics.transport.TileGridTile;

public class TileGridFurnace extends TileGridNode implements ISidedInventory, IMachine{

	ForgeDirection orientation = ForgeDirection.getOrientation(2);
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
			if(inv[0].stackSize < 1)
				inv[0] = null;
			this.markDirty();
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
        this.markDirty();
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
	public boolean isActive() {
		return smelting != null;
	}
	
	@Override
    public Packet getDescriptionPacket(){
    	NBTTagCompound nbt = new NBTTagCompound();
    	this.writeToNBT(nbt);
    	return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
    }
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt){
    	NBTTagCompound nbt = pkt.func_148857_g();
    	if(nbt == null || nbt.hasNoTags())
    		return;
    	this.readFromNBT(nbt);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt){
    	super.readFromNBT(nbt);
    	orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
    	
    	smeltEnergy = nbt.getInteger("SmeltTime");
    	if(nbt.hasKey("Smelting")){
    		smelting = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Smelting"));
    	}else{
    		smelting = null;
    	}
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
    	nbt.setInteger("Orientation", orientation.ordinal());
    	
    	nbt.setInteger("SmeltTime", smeltEnergy);
    	if(smelting != null){
    		nbt.setTag("Smelting", smelting.writeToNBT(new NBTTagCompound()));
    	}
    	
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
