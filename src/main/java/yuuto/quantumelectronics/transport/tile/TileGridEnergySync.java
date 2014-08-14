package yuuto.quantumelectronics.transport.tile;

import cofh.api.energy.IEnergyHandler;
import yuuto.quantumelectronics.tile.base.IMachine;
import yuuto.quantumelectronics.transport.TileGridNode;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileGridEnergySync extends TileGridNode implements IMachine{
	ForgeDirection orientation = ForgeDirection.getOrientation(2);
	@Override
	public void doWork() {
		propogateEnergy();
	}
	@Override
	public boolean canConnectEnergy(ForgeDirection from){
		return from == orientation;
	}
	protected void propogateEnergy(){
		IEnergyHandler target = (IEnergyHandler)worldObj.getTileEntity(
				xCoord+orientation.offsetX, yCoord+orientation.offsetY, zCoord+orientation.offsetZ);
		if(target == null)
			return;
		int max = this.storage.getMaxExtract();
		if(max > this.storage.getEnergyStored())
			max = this.storage.getEnergyStored();
		this.storage.extractEnergy(target.receiveEnergy(orientation.getOpposite(), max, false), false);
	}
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceiveEnergy, boolean simulate){
		if(from != ForgeDirection.UNKNOWN)
			return 0;
		return super.receiveEnergy(from, maxReceiveEnergy, simulate);
	}
	@Override
	public ForgeDirection getOrientation() {
		return orientation;
	}
	@Override
	public ForgeDirection setOrientation(ForgeDirection dir) {
		orientation = dir.getOpposite();
		return orientation;
	}
	@Override
	public ForgeDirection rotateAround(ForgeDirection axis) {
		orientation = orientation.getRotation(axis);
		return orientation;
	}
	@Override
	public boolean isActive() {
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
    	super.readFromNBT(nbt);
    	orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
	}
	@Override
    public void writeToNBT(NBTTagCompound nbt){
    	super.writeToNBT(nbt);
    	nbt.setInteger("Orientation", orientation.ordinal());
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
}
