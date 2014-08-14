package yuuto.quantumelectronics.transport.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import yuuto.quantumelectronics.ModItems;
import yuuto.quantumelectronics.gui.container.ContainerRequester;
import yuuto.quantumelectronics.transport.TileGridTile;
import yuuto.quantumelectronics.transport.routing.IFluidProvider;
import yuuto.quantumelectronics.transport.routing.IItemDestination;
import yuuto.quantumelectronics.transport.routing.IItemExtractor;
import yuuto.quantumelectronics.transport.routing.IItemProvider;
import yuuto.quantumelectronics.transport.routing.ItemProviderStack;
import yuuto.quantumelectronics.transport.routing.ItemWrapper;

public class Requester extends ItemRouter{

	List<ItemProviderStack> inv = new ArrayList<ItemProviderStack>();
	List<ItemProviderStack> itemInv = new ArrayList<ItemProviderStack>();
	List<ItemProviderStack> fluidInv = new ArrayList<ItemProviderStack>();
	ArrayList<ContainerRequester> open = new ArrayList<ContainerRequester>();
	boolean needsRefresh = false;
	public Requester(TileGridTile parrent) {
		super(parrent);
	}

	@Override
	public void doWork() {
		if(open.size() < 1)
			return;
		if(this.parrent.getWorldObj().isRemote)
			return;
		inv.clear();
		itemInv.clear();
		fluidInv.clear();
		
		refreshItemInventory();
		refreshFluidInventory();
		
		if(parrent.getWorldObj().isRemote){
			System.out.println("Found "+inv.size()+" provided items on client");
		}else{
			System.out.println("Found "+inv.size()+" provided items on server");
		}
		Collections.sort(inv, new StackComparatorProvider());
		Collections.sort(itemInv, new StackComparatorProvider());
		Collections.sort(fluidInv, new StackComparatorProvider());
		for(ContainerRequester c : open){
			c.updateInventory();
		}
	}
	protected void refreshItemInventory(){
		Set<Entry<ItemWrapper, List<IItemProvider>>> providedItems = 
				parrent.getGrid().getChannel(channel).getProvidedItems();
		Iterator<Entry<ItemWrapper, List<IItemProvider>>> itr =
				providedItems.iterator();
		while(itr.hasNext()){
			Entry<ItemWrapper, List<IItemProvider>> entry = itr.next();
			ItemWrapper w = entry.getKey();
			ItemProviderStack stack = new ItemProviderStack(w.getStack());
			List<IItemProvider> routers = entry.getValue();
			for(int i = 0; i < routers.size(); i++){
				stack.stackSize+= routers.get(i).getNumberProvided(w);
			}
			if(stack.stackSize > 0){
				inv.add(stack);
				itemInv.add(stack);
			}
				
		}
	}
	public void refreshFluidInventory(){
		Set<Entry<FluidStack, List<IFluidProvider>>> providedFluids = 
				parrent.getGrid().getChannel(channel).getProvidedFluids();
		Iterator<Entry<FluidStack, List<IFluidProvider>>> itr =
				providedFluids.iterator();
		while(itr.hasNext()){
			Entry<FluidStack, List<IFluidProvider>> entry = itr.next();
			FluidStack fluid = entry.getKey().copy();
			fluid.amount = 0;
			ItemStack stack = new ItemStack(ModItems.FLUID_DUMMY);
			
			List<IFluidProvider> routers = entry.getValue();
			for(int i = 0; i < routers.size(); i++){
				fluid.amount+= routers.get(i).getAmountProvided(fluid);
			}
			if(fluid.amount > 0){
				ModItems.FLUID_DUMMY.fill(stack, fluid, true);
				ItemProviderStack ret = new ItemProviderStack(stack);
				inv.add(ret);
				fluidInv.add(ret);
			}
				
		}
	}
	public ItemStack extractItem(ItemStack maxExtract){
		List<IItemProvider> routers = parrent.getGrid().getChannel(channel).getRoutersProviding(new ItemWrapper(maxExtract));
		ItemStack ret = maxExtract.copy();
		if(routers == null){
			if(this.parrent.getWorldObj().isRemote){
				for(int i = 0; i < this.inv.size(); i++){
					if(this.compareItems(inv.get(i).getItemStack(), maxExtract)){
						if(inv.get(i).stackSize < ret.stackSize){
							ret.stackSize = (int)inv.get(i).stackSize;
						}
						//this.inv.get(i).stackSize-= ret.stackSize;
						return ret;
					}
				}
			}
			return null;
		}
		ret.stackSize = 0;
		for(int i = 0; i < routers.size(); i++){
			ItemStack s = routers.get(i).extractItemStack(maxExtract, false);
			if(s == null)
				continue;
			ret.stackSize+= s.stackSize;
			maxExtract.stackSize-= s.stackSize;
			if(maxExtract.stackSize < 1)
				return ret;
		}
		if(ret.stackSize < 1)
			return null;
		return ret;
	}
	public FluidStack drainFluid(FluidStack maxExtract){
		List<IFluidProvider> routers = parrent.getGrid().getChannel(channel).getRoutersProviding(maxExtract);
		FluidStack ret = maxExtract.copy();
		List<IFluidProvider> contributors = new ArrayList<IFluidProvider>();
		if(routers == null)
			return null;
		ret.amount = 0;
		for(int i = 0; i < routers.size(); i++){
			FluidStack s = routers.get(i).extractFluidStack(maxExtract, true);
			if(s == null)
				continue;
			ret.amount+= s.amount;
			maxExtract.amount-= s.amount;
			contributors.add(routers.get(i));
			if(maxExtract.amount < 1){
				maxExtract.amount = ret.amount;
				for(int j = 0; j < contributors.size(); j++){
					contributors.get(i).extractFluidStack(maxExtract, false);
				}
				return ret;
			}
		}
		return null;
	}
	public ItemStack extractItemClient(ItemStack maxExtract){
		if(this.inv == null || this.inv.size() < 1)
			return null;
		if(maxExtract.stackSize > maxExtract.getMaxStackSize())
			maxExtract.stackSize = maxExtract.getMaxStackSize();
		for(int i = 0; i < inv.size(); i++){
			ItemProviderStack stack = inv.get(i);
			if(compareItems(stack.getItemStack(), maxExtract)){
				if(stack.stackSize < maxExtract.stackSize){
					System.out.println("Stack being nulled");
					inv.remove(i);
					for(ContainerRequester c : open){
						c.updateInventoryClient();
					}
					return stack.getItemStack();
				}else{
					return stack.splitStack(maxExtract.stackSize).getItemStack();
				}
			}
		}
		return null;
	}
	public ItemStack insertItem(ItemStack stack){
		List<IItemDestination> destinations = parrent.getGrid().getChannel(channel).getDestinations();
		ItemStack maxInsert = stack.copy();
		ItemStack ret = maxInsert.copy();
		ret.stackSize = 0;
		for(IItemDestination dest : destinations){
			ItemStack stack2 = dest.insertItem(maxInsert, false, dest.isSupplier());
			if(stack2 != null){
				maxInsert.stackSize -= stack2.stackSize;
				ret.stackSize += stack2.stackSize;
				if(maxInsert.stackSize < 1)
					return ret;
			}
		}
		if(ret.stackSize > 0){
			return ret;
		}
		return null;
	}
	public void clearInventory(){
		this.inv.clear();
	}
	public void addItemStack(ItemProviderStack stack){
		inv.add(stack);
	}
	
	@Override
	public void onPreGridChange() {}

	@Override
	public void onPostGridChange() {doWork();}
	
	public List<ItemProviderStack> getInventory(){
		return inv;
	}
	public List<ItemProviderStack> getFluidInventory(){
		return fluidInv;
	}
	public List<ItemProviderStack> setInventory(List<ItemProviderStack> inv){
		this.inv = inv;
		return this.inv;
	}

	public void openInventory(ContainerRequester container) {
		if(!open.contains(container))
			open.add(container);
	}
	public void closeInventory(ContainerRequester container) {
		if(open.contains(container))
			open.remove(container);
	}
	
}
