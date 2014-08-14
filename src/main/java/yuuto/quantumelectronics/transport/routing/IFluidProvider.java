package yuuto.quantumelectronics.transport.routing;

import net.minecraftforge.fluids.FluidStack;

public interface IFluidProvider extends IFluidExtractor{
	public int getAmountProvided(FluidStack stack);
}
