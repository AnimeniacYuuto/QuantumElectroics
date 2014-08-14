package yuuto.quantumelectronics.transport.managers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import yuuto.quantumelectronics.transport.TileGridTile;
import yuuto.quantumelectronics.transport.filter.FluidFilter;
import yuuto.quantumelectronics.transport.filter.ItemFilter;
import yuuto.quantumelectronics.transport.routing.IFluidExtractor;
import yuuto.quantumelectronics.transport.routing.ItemWrapper;

public class FluidExtractor extends FluidRouter implements IFluidExtractor{
	
	ArrayList<FluidStack> pushedFluids = new ArrayList<FluidStack>();
	
	public FluidExtractor(TileGridTile parrent){
		super(parrent);
	}
	
	@Override
	public void setChannel(int c){
		removePushedFluids(pushedFluids);
		pushFluids(pushedFluids);
		super.setChannel(c);
	}
	@Override
	public void doWork(){
		if(target == null){
			if(pushedFluids.size() > 0){
				removePushedFluids(pushedFluids);
				pushedFluids.clear();
			}
			return;
		}
		ArrayList<FluidStack> lastPushed = (ArrayList<FluidStack>)pushedFluids.clone();
		pushedFluids.clear();
		if(filter == null)
			filter = new FluidFilter();
		FluidTankInfo[] info = target.getTankInfo(orientation);
		for(int i = 0; i < info.length; i++){
			if(info[i].fluid == null)
				continue;
			//System.out.println("Pushing fluid");
			if(filter.matches(info[i].fluid)){
				FluidStack fluid = info[i].fluid.copy();
				if(!pushedFluids.contains(fluid))
					pushedFluids.add(fluid);
				if(lastPushed.contains(fluid)){
					lastPushed.remove(fluid);
				}else{
					this.pushFluid(fluid);
				}
			}
		}
		removePushedFluids(lastPushed);
	}
	@Override
	public FluidStack extractFluidStack(FluidStack stack, boolean simulate) {
		if(target == null)
			return null;
		if(filter != null && !filter.matches(stack))
			return null;
		//System.out.println("Matched extract filter");
		if(target.canDrain(orientation, stack.getFluid())){
			//System.out.println("Can Drain");
			FluidTankInfo[] info = target.getTankInfo(orientation);
			if(info == null || info.length < 1)
				return null;
			for(int i = 0; i < info.length; i++){
				if(info[i].fluid == null)
					continue;
				if(info[i].fluid.isFluidEqual(stack)){
					FluidStack ret = info[i].fluid.copy();
					if(stack.amount < ret.amount)
						ret.amount = stack.amount;
					if(!simulate)
						info[i].fluid.amount-= ret.amount;
					return ret;
				}
			}
		}
		return null;
	}
	protected void pushFluid(FluidStack w){
		parrent.getGrid().getChannel(channel).pushFluid(w, this);
	}
	protected void pushFluids(List<FluidStack> w){
		parrent.getGrid().getChannel(channel).pushFluids(w, this);
	}
	protected void removePushedFluid(FluidStack w){
		parrent.getGrid().getChannel(channel).removePushedFluid(w, this);
	}
	protected void removePushedFluids(List<FluidStack> w){
		parrent.getGrid().getChannel(channel).removePushedFluids(w, this);
	}

	@Override
	public void onPreGridChange() {
		removePushedFluids(pushedFluids);
	}

	@Override
	public void onPostGridChange() {
		pushFluids(pushedFluids);
	}
}
