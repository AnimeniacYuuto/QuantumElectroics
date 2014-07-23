package yuuto.quantumelectronics.transport;

import java.util.Random;

import yuuto.quantumelectronics.transport.handler.ITransportHandler;
import yuuto.quantumelectronics.transport.handler.TransportStorage;
import yuuto.quantumelectronics.transport.wrapper.TransportStack;
import net.minecraftforge.common.util.ForgeDirection;

public class TileConduit extends TileTransport implements ITransportHandler{

	TransportStorage storage;
	ITransportHandler[] connections = new ITransportHandler[6];
	Random random = new Random();
	
	@Override
	public boolean canConnect(ForgeDirection from) {
		return true;
	}

	@Override
	public TransportStack receiveStack(ForgeDirection from,
			TransportStack stack, boolean simulate) {
		return storage.receiveStack(stack, simulate);
	}

	@Override
	public boolean isEmpty(ForgeDirection from) {
		return storage.isEmpty();
	}

	@Override
	public boolean isFull(ForgeDirection from) {
		return storage.isFull();
	}
	
	
	@Override
	public void updateEntity(){
		super.updateEntity();
		
		if(worldObj.isRemote || storage.isEmpty())
			return;
		
		int i = random.nextInt(6);
		if(connections[i] == null)
			return;
		TransportStack toExtract = storage.getStacks()[0];
		ForgeDirection dir = ForgeDirection.getOrientation(i).getOpposite();
		storage.extractStack(connections[i].receiveStack(dir, toExtract, false), false);
	}

}
