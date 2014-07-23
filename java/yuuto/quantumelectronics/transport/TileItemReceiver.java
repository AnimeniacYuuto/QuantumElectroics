package yuuto.quantumelectronics.transport;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import yuuto.quantumelectronics.transport.handler.ITransportConnection;
import yuuto.quantumelectronics.transport.handler.ITransportHandler;
import yuuto.quantumelectronics.transport.inventory.ItemReceiver;
import yuuto.quantumelectronics.transport.wrapper.TransportStack;

public class TileItemReceiver extends TileTransportNode implements ITransportHandler{

	IInventory target;

	@Override
	public TransportStack receiveStack(ForgeDirection from,
			TransportStack stack, boolean simulate) {
		if(worldObj.isRemote || target == null)
			return null;
		return ItemReceiver.receiveStack(stack, target, facing);
	}

	@Override
	public boolean isEmpty(ForgeDirection from) {
		return true;
	}

	@Override
	public boolean isFull(ForgeDirection from) {
		return false;
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
