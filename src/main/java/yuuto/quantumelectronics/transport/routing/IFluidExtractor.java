package yuuto.quantumelectronics.transport.routing;

import net.minecraftforge.fluids.FluidStack;

public interface IFluidExtractor extends IFluidRouter{
	FluidStack extractFluidStack(FluidStack stack, boolean simulate);
}
