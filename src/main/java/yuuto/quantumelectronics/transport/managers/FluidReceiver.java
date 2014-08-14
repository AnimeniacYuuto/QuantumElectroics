package yuuto.quantumelectronics.transport.managers;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import yuuto.quantumelectronics.transport.TileGridTile;
import yuuto.quantumelectronics.transport.filter.FluidFilter;
import yuuto.quantumelectronics.transport.routing.IFluidExtractor;

public class FluidReceiver extends FluidRouter{

	public FluidReceiver(TileGridTile parrent) {
		super(parrent);
	}

	@Override
	public void doWork() {
		if(target == null)
			return;
		if(filter == null)
			filter = new FluidFilter();
		Set<Entry<FluidStack, List<IFluidExtractor>>> fluids = this.parrent.getGrid().getChannel(channel).getPushedFluids();
		//System.out.println("Found "+fluids.size()+" fluids");
		FluidTankInfo[] info = target.getTankInfo(orientation);
		for(int i = 0; i < info.length; i++){
			if(info[i].fluid != null && info[i].fluid.amount >= info[i].capacity)
				continue;
			
			if(info[i].fluid == null){
				if(fillEmptyFromPushed(info[i].capacity, fluids))
					break;
				continue;
			}
			if(fillFromPushed(info[i].fluid, info[i].capacity))
				break;
		}
		
	}
	public boolean fillEmptyFromPushed(int capacity, Set<Entry<FluidStack, List<IFluidExtractor>>> fluids){
		Iterator<Entry<FluidStack, List<IFluidExtractor>>> itr = fluids.iterator();
		int filled = 0;
		while(itr.hasNext()){
			Entry<FluidStack, List<IFluidExtractor>> entry = itr.next();
			if(!filter.matches(entry.getKey()))
				continue;
			if(!target.canFill(orientation, entry.getKey().getFluid()))
				continue;
			FluidStack max = entry.getKey().copy();
			max.amount = capacity;
			if(max.amount > 100){
				max.amount = 100;
			}
			List<IFluidExtractor> routers = entry.getValue();
			for(int j = 0; j < routers.size(); j++){
				//System.out.println("extracting Fluid");
				FluidStack extracted = routers.get(j).extractFluidStack(max, false);
				if(extracted == null)
					continue;
				//System.out.println("extracted Fluid");
				int filledAmount = this.target.fill(orientation, extracted, true);
				filled += filledAmount;
				max.amount -= filledAmount;
				if(max.amount < 1)
					return true;
			}
			if(filled != 0)
				break;
		}
		if(filled != 0)
			return true;
		return false;
	}
	public boolean fillFromPushed(FluidStack fluid, int capacity){
		if(!filter.matches(fluid))
			return false;
		if(!target.canFill(orientation, fluid.getFluid()))
			return false;
		List<IFluidExtractor> routers = this.parrent.getGrid().getChannel(channel).getRoutersPushing(fluid);
		if(routers == null)
			return false;
		FluidStack max = fluid.copy();
		max.amount = capacity - fluid.amount;
		if(max.amount > 100)
			max.amount = 100;
		int originalMax = max.amount;
		for(int i = 0; i < routers.size(); i++){
			FluidStack extracted = routers.get(i).extractFluidStack(max, false);
			if(extracted == null)
				continue;
			int filledAmount = this.target.fill(orientation, extracted, true);
			max.amount -= filledAmount;
			if(max.amount < 1)
				return true;
		}
		if(max.amount < originalMax)
			return true;
		return false;
	}

	@Override
	public void onPreGridChange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostGridChange() {
		// TODO Auto-generated method stub
		
	}

}
