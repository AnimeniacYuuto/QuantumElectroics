package yuuto.quantumelectronics.transport.routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import yuuto.quantumelectronics.transport.IGridTile;

public class RoutingChannel {

	List<IItemDestination> itemDestinations = new ArrayList<IItemDestination>();
	
	Map<ItemWrapper, List<IItemExtractor>> pushedItems = new HashMap<ItemWrapper, List<IItemExtractor>>();
	Map<ItemWrapper, List<IItemProvider>> providedItems = new HashMap<ItemWrapper, List<IItemProvider>>();
	
	Map<FluidStack, List<IFluidExtractor>> pushedFluids = new HashMap<FluidStack, List<IFluidExtractor>>();
	Map<FluidStack, List<IFluidProvider>> providedFluids = new HashMap<FluidStack, List<IFluidProvider>>(); 
	
	//Methods for managing pushed items
	public void pushItem(ItemWrapper w, IItemExtractor router){
		Iterator<Entry<ItemWrapper, List<IItemExtractor>>> itr = pushedItems.entrySet().iterator();
		while(itr.hasNext()){
			Entry<ItemWrapper, List<IItemExtractor>> entry = itr.next();
			if(w.equals(entry.getKey())){
				if(!entry.getValue().contains(router))
					entry.getValue().add(router);
				return;
			}
		}
		pushedItems.put(w, new ArrayList<IItemExtractor>());
		pushedItems.get(w).add(router);
	}
	public void pushItems(List<ItemWrapper> w, IItemExtractor router){
		for(int i = 0; i < w.size(); i++){
			pushItem(w.get(i), router);
		}
	}
	public void removePushedItem(ItemWrapper w, IItemExtractor router){
		Iterator<Entry<ItemWrapper, List<IItemExtractor>>> itr = pushedItems.entrySet().iterator();
		while(itr.hasNext()){
			Entry<ItemWrapper, List<IItemExtractor>> entry = itr.next();
			if(w.equals(entry.getKey())){
				if(entry.getValue().contains(router))
					entry.getValue().remove(router);
				if(entry.getValue().size() < 1)
					pushedItems.remove(entry.getKey());
				return;
			}
		}
	}
	public void removePushedItems(List<ItemWrapper> w, IItemExtractor router){
		for(int i = 0; i < w.size(); i++){
			removePushedItem(w.get(i), router);
		}
	}
	public Set<Entry<ItemWrapper, List<IItemExtractor>>> getPushedItems(){
		return pushedItems.entrySet();
	}
	public List<IItemExtractor> getRoutersPushing(ItemWrapper w){
		Iterator<Entry<ItemWrapper, List<IItemExtractor>>> itr = pushedItems.entrySet().iterator();
		while(itr.hasNext()){
			Entry<ItemWrapper, List<IItemExtractor>> entry = itr.next();
			if(w.equals(entry.getKey())){
				return entry.getValue();
			}
		}
		return null;
	}
	
	//Methods for managing provided items
	public void provideItem(ItemWrapper w, IItemProvider router){
		Iterator<Entry<ItemWrapper, List<IItemProvider>>> itr = providedItems.entrySet().iterator();
		while(itr.hasNext()){
			Entry<ItemWrapper, List<IItemProvider>> entry = itr.next();
			if(w.equals(entry.getKey())){
				if(!entry.getValue().contains(router))
					entry.getValue().add(router);
				return;
			}
		}
		providedItems.put(w, new ArrayList<IItemProvider>());
		providedItems.get(w).add(router);
	}
	public void provideItems(List<ItemWrapper> w, IItemProvider router){
		for(int i = 0; i < w.size(); i++){
			provideItem(w.get(i), router);
		}
	}
	public void removeProvidedItem(ItemWrapper w, IItemProvider router){
		Iterator<Entry<ItemWrapper, List<IItemProvider>>> itr = providedItems.entrySet().iterator();
		while(itr.hasNext()){
			Entry<ItemWrapper, List<IItemProvider>> entry = itr.next();
			if(w.equals(entry.getKey())){
				if(entry.getValue().contains(router))
					entry.getValue().remove(router);
				if(entry.getValue().size() < 1)
					providedItems.remove(entry.getKey());
				return;
			}
		}
	}
	public void removeProvidedItems(List<ItemWrapper> w, IItemProvider router){
		for(int i = 0; i < w.size(); i++){
			removeProvidedItem(w.get(i), router);
		}
	}
	public Set<Entry<ItemWrapper, List<IItemProvider>>> getProvidedItems(){
		return providedItems.entrySet();
	}
	public List<IItemProvider> getRoutersProviding(ItemWrapper w){
		Iterator<Entry<ItemWrapper, List<IItemProvider>>> itr = providedItems.entrySet().iterator();
		while(itr.hasNext()){
			Entry<ItemWrapper, List<IItemProvider>> entry = itr.next();
			if(w.equals(entry.getKey())){
				return entry.getValue();
			}
		}
		return null;
	}
	
	//Methods for managing pushed items
	public void pushFluid(FluidStack w, IFluidExtractor router){
		Iterator<Entry<FluidStack, List<IFluidExtractor>>> itr = pushedFluids.entrySet().iterator();
		while(itr.hasNext()){
			Entry<FluidStack, List<IFluidExtractor>> entry = itr.next();
			if(w.isFluidEqual(entry.getKey())){
				if(!entry.getValue().contains(router))
					entry.getValue().add(router);
				return;
			}
		}
		pushedFluids.put(w, new ArrayList<IFluidExtractor>());
		pushedFluids.get(w).add(router);
	}
	public void pushFluids(List<FluidStack> w, IFluidExtractor router){
		for(int i = 0; i < w.size(); i++){
			pushFluid(w.get(i), router);
		}
	}
	public void removePushedFluid(FluidStack w, IFluidExtractor router){
		Iterator<Entry<FluidStack, List<IFluidExtractor>>> itr = pushedFluids.entrySet().iterator();
		while(itr.hasNext()){
			Entry<FluidStack, List<IFluidExtractor>> entry = itr.next();
			if(w.isFluidEqual(entry.getKey())){
				if(entry.getValue().contains(router))
					entry.getValue().remove(router);
				if(entry.getValue().size() < 1)
					pushedItems.remove(entry.getKey());
				return;
			}
		}
	}
	public void removePushedFluids(List<FluidStack> w, IFluidExtractor router){
		for(int i = 0; i < w.size(); i++){
			removePushedFluid(w.get(i), router);
		}
	}
	public Set<Entry<FluidStack, List<IFluidExtractor>>> getPushedFluids(){
		return pushedFluids.entrySet();
	}
	public List<IFluidExtractor> getRoutersPushing(FluidStack w){
		Iterator<Entry<FluidStack, List<IFluidExtractor>>> itr = pushedFluids.entrySet().iterator();
		while(itr.hasNext()){
			Entry<FluidStack, List<IFluidExtractor>> entry = itr.next();
			if(w.isFluidEqual(entry.getKey())){
				return entry.getValue();
			}
		}
		return null;
	}
	
	//Methods for managing provided items
	public void provideFluid(FluidStack w, IFluidProvider router){
		Iterator<Entry<FluidStack, List<IFluidProvider>>> itr = providedFluids.entrySet().iterator();
		while(itr.hasNext()){
			Entry<FluidStack, List<IFluidProvider>> entry = itr.next();
			if(w.isFluidEqual(entry.getKey())){
				if(!entry.getValue().contains(router))
					entry.getValue().add(router);
				return;
			}
		}
		providedFluids.put(w, new ArrayList<IFluidProvider>());
		providedFluids.get(w).add(router);
	}
	public void provideFluids(List<FluidStack> w, IFluidProvider router){
		for(int i = 0; i < w.size(); i++){
			provideFluid(w.get(i), router);
		}
	}
	public void removeProvidedFluid(FluidStack w, IFluidProvider router){
		Iterator<Entry<FluidStack, List<IFluidProvider>>> itr = providedFluids.entrySet().iterator();
		while(itr.hasNext()){
			Entry<FluidStack, List<IFluidProvider>> entry = itr.next();
			if(w.isFluidEqual(entry.getKey())){
				if(entry.getValue().contains(router))
					entry.getValue().remove(router);
				if(entry.getValue().size() < 1)
					providedFluids.remove(entry.getKey());
				return;
			}
		}
	}
	public void removeProvidedFluids(List<FluidStack> w, IFluidProvider router){
		for(int i = 0; i < w.size(); i++){
			removeProvidedFluid(w.get(i), router);
		}
	}
	public Set<Entry<FluidStack, List<IFluidProvider>>> getProvidedFluids(){
		return providedFluids.entrySet();
	}
	public List<IFluidProvider> getRoutersProviding(FluidStack w){
		Iterator<Entry<FluidStack, List<IFluidProvider>>> itr = providedFluids.entrySet().iterator();
		while(itr.hasNext()){
			Entry<FluidStack, List<IFluidProvider>> entry = itr.next();
			if(w.isFluidEqual(entry.getKey())){
				return entry.getValue();
			}
		}
		return null;
	}
	
	public void addDestination(IItemDestination dest){
		if(!itemDestinations.contains(dest))
			itemDestinations.add(dest);
	}
	public List<IItemDestination> getDestinations(){
		return itemDestinations;
	}
	public void removeDestination(IItemDestination dest){
		if(itemDestinations.contains(dest))
			itemDestinations.remove(dest);
	}
}
