package yuuto.quantumelectronics.transport;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
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

}
