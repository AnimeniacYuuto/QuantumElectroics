package yuuto.quantumelectronics.transport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import yuuto.quantumelectronics.transport.pathfinder.BlockFinder;
import yuuto.quantumelectronics.util.Logger;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class TilePylon extends TileEntity{
	List<TilePylon> connections = new ArrayList<TilePylon>();
	protected int range = 16;
	static int maxDistance = 16;
	protected boolean initialized = false;
	protected boolean needsGridUpdate;
	
	@Override
	public void updateEntity(){
		if(!initialized){
			if(!this.isInvalid())
				initialize();
			return;
		}
		if(needsGridUpdate){
			updateGrid();
		}
	}
	protected void initialize(){
		initialized = true;
		findConnections();
		needsGridUpdate = true;
	}
	@Override
	public void invalidate(){
		super.invalidate();
		for(int i = 0; i < connections.size(); i++){
			connections.get(i).removeConnection(this);
			removeConnection(connections.get(i));
		}
		this.initialized = false;
	}
	public boolean isInitialized(){
		return initialized;
	}
	public void findConnections(){
		Logger.LogInfo("Refreshing connections");
		for(int i = 2; i < 6; i++){
			Vec3 vect = BlockFinder.findNearstOpaqueBlock(worldObj, xCoord, yCoord, zCoord, 
					ForgeDirection.getOrientation(i), range);
			if(vect == null)
				continue;
			TileEntity tile = worldObj.getTileEntity((int)vect.xCoord, (int)vect.yCoord, (int)vect.zCoord);
			if(tile == null || !(tile instanceof TilePylon) || tile == this)
				continue;
			TilePylon pylon = (TilePylon)tile;
			if(!canConnect(this) || !this.canConnect(pylon))
				continue;
			if(isInRangeOf(pylon) || pylon.isInRangeOf(this)){
				Logger.LogOff("Connecting Pylons");
				addConnection(pylon);
				pylon.addConnection(this);
			}
				
		}
	}
	
	public boolean canConnect(TilePylon pylon){
		if(pylon.worldObj.provider.dimensionId != this.worldObj.provider.dimensionId)
			return false;
		return initialized;
	}
	public boolean isInRangeOf(TilePylon pylon){
		Logger.Log("Distance: "+(getDistanceFrom(pylon.xCoord, pylon.yCoord, pylon.zCoord)/10));
		return (getDistanceFrom(pylon.xCoord, pylon.yCoord, pylon.zCoord)/10) <= range;
	}
	public boolean addConnection(TilePylon tile){
		if(connections.contains(tile))
			return true;
		markForGridUpdate();
		return connections.add(tile);
	}
	public boolean removeConnection(TilePylon tile){
		if(!connections.contains(tile))
			return true;
		markForGridUpdate();
		return connections.remove(tile);
	}
	public void markForGridUpdate(){
		if(!needsGridUpdate){
			needsGridUpdate = true;
			for(int i = 0; i < connections.size(); i++){
				connections.get(i).markForGridUpdate();
			}
		}
	}
	public void updateGrid(){
		needsGridUpdate = false;
	}
	public List<TilePylon> getConnections(){
		return connections;
	}
}
