package yuuto.quantumelectronics.transport;

import java.util.ArrayList;
import java.util.List;

import yuuto.quantumelectronics.transport.grid.PylonGrid;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.TileEnergyHandler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileGridTile extends TileEnergyHandler implements IGridTile{

	protected ArrayList<IGridTile> connections = new ArrayList<IGridTile>();
	protected PylonGrid grid = new PylonGrid(this);
	protected boolean initialized = false;
	protected int range = 11;
	protected boolean needsUpdate = true;
	
	public void initialize(){
		initialized = true;
		checkConnections();
		mergeGrids();
	}
	public void uninitialize(){
		initialized = false;
		if(connections.size() < 2){
			grid.removeConnection(this);
		}else{
			grid.split(this);
		}
	}
	/**
	 * Used in place of update to do work, allows for validation checks
	 */
	public abstract void doWork();

	/**
	 * Find all tile near by that it can connect to
	 */
	public void checkConnections(){
		for(int x = 0-range; x < range; x++){
			for(int y = 0-range; y < range; y++){
				for(int z = 0-range; z < range; z++){
					TileEntity tile = worldObj.getTileEntity(
							this.xCoord+x, this.yCoord+y, this.zCoord+z);
					if(tile == null || tile == this)
						continue;
					if(!(tile instanceof IGridTile))
						continue;
					this.addConnection((IGridTile)tile);
					((IGridTile)tile).addConnection(this);
				}
			}
		}
	}
	/**
	 * merges this grid with the grids of the connections
	 */
	public void mergeGrids(){
		for(int i = 0; i < connections.size(); i++){
			if(connections.get(i).getGrid() != grid){
				connections.get(i).getGrid().mergeWith(this.grid);
			}
		}
	}
	
	@Override
	public void updateEntity(){
		if(this.isInvalid())
			return;
		if(!initialized){
			initialize();
		}
		if(needsUpdate){
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			needsUpdate = false;
		}
		doWork();
	}
	
	@Override
	public void invalidate(){
		super.invalidate();
		uninitialize();
	}
	@Override
	public void onChunkUnload(){
		super.onChunkUnload();
		uninitialize();
	}
	
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from){
		return false;
	}
	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return 0;
	}
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		int ret = super.receiveEnergy(from, maxReceive, simulate);
		if(ret > 0 && !simulate)
			this.markDirty();
		return ret;
	}
	@Override
	public int setEnergy(int amount){
		storage.setEnergyStored(amount);
		return storage.getEnergyStored();
	}
	
	@Override
	public boolean canConnect(IGridTile tile) {
		PositionInfo info = tile.getCoords();
		if(!initialized || info.dim != worldObj.provider.dimensionId)
			return false;		
		return this.getDistanceFrom(info.x, info.y, info.z) <= range*range;
	}

	@Override
	public List<IGridTile> getConnections() {
		return (List<IGridTile>)connections.clone();
	}
	@Override
	public PositionInfo getCoords() {
		return new PositionInfo(worldObj.provider.dimensionId, 
				this.xCoord, this.yCoord, this.zCoord);
	}
	@Override
	public void addConnection(IGridTile tile) {
		if(connections.contains(tile))
			return;
		if(canConnect(tile) && tile.canConnect(this))
			connections.add(tile);
	}
	@Override
	public void removeConnection(IGridTile tile) {
		if(!connections.contains(tile))
			return;
		connections.remove(tile);
	}
	
	@Override
	public PylonGrid getGrid() {
		return grid;
	}
	@Override
	public void setGrid(PylonGrid grid) {
		this.grid = grid;		
	}
	
	@Override
    public void markDirty(){
    	super.markDirty();
    	needsUpdate = true;
    }
	@Override
    public void readFromNBT(NBTTagCompound nbt){
    	super.readFromNBT(nbt);
    	range = nbt.getInteger("GridRange");
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt){
	    super.writeToNBT(nbt);
	    nbt.setInteger("GridRange", range);
	}

	
}
