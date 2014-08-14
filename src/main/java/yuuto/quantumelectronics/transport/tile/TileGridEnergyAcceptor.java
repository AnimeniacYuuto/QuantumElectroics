package yuuto.quantumelectronics.transport.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import yuuto.quantumelectronics.tile.base.IMachine;
import yuuto.quantumelectronics.transport.TileGridNode;

public class TileGridEnergyAcceptor extends TileGridNode implements IMachine{

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
		if(from != orientation)
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
