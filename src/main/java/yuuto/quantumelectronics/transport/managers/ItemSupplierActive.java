package yuuto.quantumelectronics.transport.managers;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

import yuuto.quantumelectronics.transport.TileGridTile;
import yuuto.quantumelectronics.transport.filter.ItemFilter;
import yuuto.quantumelectronics.transport.routing.IItemExtractor;
import yuuto.quantumelectronics.transport.routing.IItemProvider;
import yuuto.quantumelectronics.transport.routing.ItemWrapper;

public class ItemSupplierActive extends ItemReceiver{

	public ItemSupplierActive(TileGridTile tile) {
		super(tile);
	}
	
	@Override
	public void setFilter(ItemFilter f){
		if(f == null || f.isGeneric() || !f.isWhite()){
			filter = null;
		}else{
			filter = f;
		}
	}

	@Override
	public void doWork(){
		if(target == null)
			return;
		if(filter == null)
			return;
		int[] slots = getSlots();
		if(slots == null || slots.length < 1)
			return;
		ItemFilter tempFilter = filter.copy();
		adjustFilter(tempFilter);
		Set<Entry<ItemWrapper, List<IItemExtractor>>> itemsPushed = 
				parrent.getGrid().getChannel(channel).getPushedItems();
		Set<Entry<ItemWrapper, List<IItemProvider>>> itemsProvided = 
				parrent.getGrid().getChannel(channel).getProvidedItems();
		for(int i : slots){
			if(isSlotFull(i, tempFilter))
				continue;
			if(target.getStackInSlot(i) == null){
				if(fillEmptySlot(i,tempFilter, itemsPushed, true) || 
						fillEmptySlotP(i, tempFilter, itemsProvided))
					return;
				continue;
			}
			if(fillSlot(i, tempFilter, true) || fillSlotFromProviders(i, tempFilter))
				return;
		}
	}
	protected boolean fillEmptySlotP(int slot, ItemFilter filter,
			Set<Entry<ItemWrapper, List<IItemProvider>>> items){
		Iterator<Entry<ItemWrapper, List<IItemProvider>>> itr = items.iterator();
		while(itr.hasNext()){
			Entry<ItemWrapper, List<IItemProvider>> entry = itr.next();
			ItemStack stack = entry.getKey().getStack();
			ItemStack maxStack = stack.copy();
			maxStack.stackSize = filter.getMaxFiltered(stack, target, false, true);
			if(maxStack.stackSize < 1)
				continue;
			if(target instanceof ISidedInventory){
				if(!((ISidedInventory)target).canInsertItem(slot, maxStack, orientation.ordinal()))
					continue;
			}
			List<IItemProvider> routers = entry.getValue();
			for(int i = 0; i < routers.size(); i++){
				ItemStack s = routers.get(i).extractItemStack(maxStack, false);
				if(s != null){
					stack.stackSize+= s.stackSize;
					maxStack.stackSize -= s.stackSize;
					if(maxStack.stackSize < 1){
						target.setInventorySlotContents(slot, stack);
						return true;
					}
				}
			}
			if(stack.stackSize > 0){
				target.setInventorySlotContents(slot, stack);
				return true;
			}
		}
		return false;
	}
	protected boolean fillSlotFromProviders(int slot, ItemFilter filter){
		ItemStack stack = target.getStackInSlot(slot);
		ItemStack maxStack = stack.copy();
		maxStack.stackSize = filter.getMaxFiltered(stack, target, true, true);
		if(maxStack.stackSize < 1)
			return false;
		ItemWrapper w = new ItemWrapper(stack);
		int size = stack.stackSize;
		if(target instanceof ISidedInventory){
			if(!((ISidedInventory)target).canInsertItem(slot, maxStack, orientation.ordinal()))
				return false;
		}
		List<IItemProvider> routers = parrent.getGrid().getChannel(channel).getRoutersProviding(w);
		if(routers == null)
			return false;
		for(int i = 0; i < routers.size(); i++){
			ItemStack s = routers.get(i).extractItemStack(maxStack, false);
			if(s != null){
				stack.stackSize+= s.stackSize;
				maxStack.stackSize -= s.stackSize;
				if(maxStack.stackSize < 1)
					return true;
			}
		}
		return size != stack.stackSize;
	}
	protected void adjustFilter(ItemFilter filter){
		for(int i = 0; i < target.getSizeInventory(); i++){
			if(target.getStackInSlot(i) == null)
				continue;
			if(filter.getMaxFiltered(target.getStackInSlot(i), target, false, true) > 0)
				filter.removeItemStack(target.getStackInSlot(i));
		}
	}
	@Override
	public ItemStack insertItem(ItemStack stack, boolean simulate, boolean supplier) {
		ItemFilter adjustedFilter = filter.copy();
		adjustFilter(adjustedFilter);
		return insertItem(stack, simulate, supplier, adjustedFilter);
	}
	@Override
	public boolean isSupplier() {
		return true;
	}
}
