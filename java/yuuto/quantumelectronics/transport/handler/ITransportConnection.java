package yuuto.quantumelectronics.transport.handler;

import net.minecraftforge.common.util.ForgeDirection;

public interface ITransportConnection {
	boolean canConnect(ForgeDirection from);
}
