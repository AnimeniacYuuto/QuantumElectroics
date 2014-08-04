package yuuto.quantumelectronics.tile;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;
import yuuto.quantumelectronics.tile.base.IMachine;
import yuuto.quantumelectronics.transport.TileGridNode;
import yuuto.quantumelectronics.transport.TileGridTile;

public class TileGridGenerator extends TileGridNode implements IInventory, IMachine{

	ForgeDirection orientation = ForgeDirection.getOrientation(2);
	ItemStack[] inv = new ItemStack[1];
	int burnTime;
	static int energyPerTick = 80;
	
	@Override
	public void doWork() {
		if(burnTime > 0){
			burnTime--;
			this.storage.receiveEnergy(energyPerTick, false);
			if(burnTime == 0){
				this.markDirty();
			}
		}else if(canBurn()){
			burnTime = TileEntityFurnace.getItemBurnTime(inv[0]);
			inv[0].stackSize--;
			if(inv[0].stackSize < 1)
				inv[0] = null;
			this.markDirty();
		}
		propogateEnergy();
	}
	protected boolean canBurn(){
		return TileEntityFurnace.getItemBurnTime(inv[0]) > 0;
	}
	protected void propogateEnergy(){
		if(connections.size() < 1)
			return;
		if(storage.getEnergyStored() < connections.size())
			return;
		int maxOut = (int)Math.floor((float)storage.getEnergyStored()/(float)connections.size());
		if(maxOut > storage.getMaxExtract()){
			maxOut = storage.getMaxExtract();
		}
		for(int i = 0; i < connections.size(); i++){
			storage.extractEnergy(connections.get(i).receiveEnergy(ForgeDirection.UNKNOWN, maxOut, false), false);
		}
	}
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceiveEnergy, boolean simulate){
		return 0;
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
		return GameRegistry.getFuelValue(stack) > 0;
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
		return burnTime > 0;
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
    	System.out.println("Reading NBT");
    	orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
    	
    	burnTime = nbt.getInteger("BurnTime");
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
    	nbt.setInteger("Orientation", orientation.ordinal());
    	
    	nbt.setInteger("BurnTime", burnTime);
    	
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
