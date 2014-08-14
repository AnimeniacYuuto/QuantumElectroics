package yuuto.quantumelectronics.transport.routing;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;
import yuuto.quantumelectronics.transport.filter.FluidFilter;

public interface IFluidRouter {
	void setOrientation(ForgeDirection dir);
	void setChannel(int c);
	void setTarget(IFluidHandler t);
	void setFilter(FluidFilter f);
	void doWork();
	
	void onPreGridChange();
	void onPostGridChange();
}
