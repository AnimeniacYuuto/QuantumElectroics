package yuuto.quantumelectronics.transport;

import yuuto.quantumelectronics.transport.handler.ITransportConnection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class TileTransportNode extends TileTransport{
	
	protected ForgeDirection facing;
	
	public TileTransportNode(){
		super();
		this.connections = new ITransportConnection[1];
		facing = ForgeDirection.getOrientation(2);
	}
	
	public ForgeDirection getOrientation(){
		return facing;
	}
	public ForgeDirection setOrientation(ForgeDirection dir){
		facing = dir;
		return facing;
	}
	public ForgeDirection rotate(ForgeDirection axis){
		facing = facing.getRotation(axis);
		return facing;
	}
	
	@Override
	public boolean canConnect(ForgeDirection from) {
		return from == facing.getOpposite();
	}
	
	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, 
			int tileX, int tileY, int tileZ){
		TileEntity tile = world.getTileEntity(tileX, tileY, tileZ);
		int offX = (x-tileX)*-1, offY = (y-tileY)*-1, offZ = (z-tileZ)*-1;
		
		if(facing.getOpposite().offsetX != offX || 
				facing.getOpposite().offsetY != offY || 
				facing.getOpposite().offsetZ != offZ){
			return;
		}
		
		if(tile == null || !(tile instanceof ITransportConnection)){
			connections[0] = null;
			return;
		}
		if(((ITransportConnection)tile).canConnect(facing)){
			connections[0] = (ITransportConnection)tile;
		}
	}
}
