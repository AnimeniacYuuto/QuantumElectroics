package yuuto.quantumelectronics.transport.handler;

import net.minecraftforge.common.util.ForgeDirection;
import yuuto.quantumelectronics.transport.wrapper.TransportStack;

public interface ITransportStorage {
	TransportStack receiveStack(TransportStack stack, boolean simulate);
	TransportStack extractStack(TransportStack stack, boolean simulate);
	boolean isEmpty();
	boolean isFull();
	TransportStack[] getStacks();
}
