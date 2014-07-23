package yuuto.quantumelectronics.transport;

import yuuto.quantumelectronics.transport.handler.ITransportConnection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class TileTransport extends TileEntity implements ITransportConnection{

	protected ITransportConnection[] connections;
	public TileTransport(){
		connections = new ITransportConnection[6];
	}
	@Override
	public boolean canConnect(ForgeDirection from) {
		return true;
	}
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, 
			int tileX, int tileY, int tileZ){
		ITransportConnection tile = (ITransportConnection) world.getTileEntity(tileX, tileY, tileZ);
		int offX = (x-tileX)*-1, offY = (y-tileY)*-1, offZ = (z-tileZ)*-1;
		for(ForgeDirection dir : ForgeDirection.values()){
			if(dir.offsetX == offX && dir.offsetY == offY && dir.offsetZ == offZ){
				if(tile != null && canConnect(dir) && tile.canConnect(dir)){
					connections[dir.ordinal()] = tile;
					break;
				}
				connections[dir.ordinal()] = null;
				break;
			}
		}
	}

	
	
}
