package yuuto.quantumelectronics.transport.routing;

import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;



public interface IEnergyRouter extends IRouter{
	void setTarget(IEnergyHandler t);
	int receiveEnergy(ForgeDirection from, int maxReceiveEnergy, boolean simulate);
}
