package yuuto.quantumelectronics.transport.managers;

import java.util.List;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import yuuto.quantumelectronics.transport.TileGridTile;
import yuuto.quantumelectronics.transport.routing.IFluidProvider;
import yuuto.quantumelectronics.transport.routing.ItemWrapper;

public class FluidProvider extends FluidExtractor implements IFluidProvider{

	public FluidProvider(TileGridTile parrent) {
		super(parrent);
	}
	
	@Override
	protected void pushFluid(FluidStack w){
		parrent.getGrid().getChannel(channel).provideFluid(w, this);
	}
	@Override
	protected void pushFluids(List<FluidStack> w){
		parrent.getGrid().getChannel(channel).provideFluids(w, this);
	}
	@Override
	protected void removePushedFluid(FluidStack w){
		parrent.getGrid().getChannel(channel).removeProvidedFluid(w, this);
	}
	@Override
	protected void removePushedFluids(List<FluidStack> w){
		parrent.getGrid().getChannel(channel).removeProvidedFluids(w, this);
	}
	@Override
	public int getAmountProvided(FluidStack w) {
		int amount = 0;
		FluidTankInfo[] info = target.getTankInfo(orientation);
		for(int i = 0; i < info.length; i++){
			if(info[i] != null && info[i].fluid != null){
				if(info[i].fluid.isFluidEqual(w)){
					amount+= info[i].fluid.amount;
				}
			}
		}
		return amount;
	}

}
