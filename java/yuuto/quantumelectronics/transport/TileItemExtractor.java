package yuuto.quantumelectronics.transport;

import yuuto.quantumelectronics.transport.handler.ITransportConnection;
import yuuto.quantumelectronics.transport.handler.ITransportHandler;
import yuuto.quantumelectronics.transport.inventory.ItemExtractor;
import yuuto.quantumelectronics.transport.wrapper.TransportStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class TileItemExtractor extends TileTransportNode{
	
	IInventory target;
	
	@Override
	public boolean canConnect(ForgeDirection from) {
		return from == facing.getOpposite();
	}
	
	@Override
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)
			return;
		if(target == null || connections[0] == null ||
				!(connections[0] instanceof ITransportHandler))
			return;
		ItemExtractor.extractItems(target, (ITransportHandler)connections[0], facing);
	}
	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, 
			int tileX, int tileY, int tileZ){
		super.onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
		TileEntity tile = world.getTileEntity(x+facing.offsetX, y+facing.offsetY, z+facing.offsetZ);
		if(tile == null){
			target = null;
			return;
		}
		if(tile instanceof IInventory){
			target = (IInventory) tile;
			return;
		}
		target = null;
	}

}
