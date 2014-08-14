package yuuto.quantumelectronics.transport.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import yuuto.quantumelectronics.transport.TileGridTile;
import yuuto.quantumelectronics.transport.filter.ItemFilter;
import yuuto.quantumelectronics.transport.routing.IItemDestination;
import yuuto.quantumelectronics.transport.routing.IItemExtractor;
import yuuto.quantumelectronics.transport.routing.IItemRouter;
import yuuto.quantumelectronics.transport.routing.ItemWrapper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemReceiver extends ItemRouter implements IItemDestination{

	
	public ItemReceiver(TileGridTile tile){
		super(tile);
		parrent.getGrid().getChannel(channel).addDestination(this);
	}
	@Override
	public void doWork(){
		if(target == null)
			return;
		int[] slots = getSlots();
		if(slots == null || slots.length < 1)
			return;
		if(filter == null)
			filter = new ItemFilter();
		Set<Entry<ItemWrapper, List<IItemExtractor>>> items = 
				parrent.getGrid().getChannel(channel).getPushedItems();
		for(int i : slots){
			if(isSlotFull(i, filter))
				continue;
			if(target.getStackInSlot(i) == null){
				if(fillEmptySlot(i,filter, items, false))
					return;
				continue;
			}
			if(fillSlot(i, filter, false))
				return;
		}
	}
	protected boolean isSlotFull(int slot, ItemFilter filter){
		if(target.getStackInSlot(slot) == null)
			return false;
		ItemStack stack = target.getStackInSlot(slot);
		if(filter.getMaxFiltered(stack, target, false, false) < 1)
			return true;
		return false;
	}
	protected boolean fillEmptySlot(int slot, ItemFilter filter,
			Set<Entry<ItemWrapper, List<IItemExtractor>>> items, boolean supply){
		Iterator<Entry<ItemWrapper, List<IItemExtractor>>> itr = items.iterator();
		while(itr.hasNext()){
			Entry<ItemWrapper, List<IItemExtractor>> entry = itr.next();
			ItemStack stack = entry.getKey().getStack();
			ItemStack maxStack = stack.copy();
			maxStack.stackSize = filter.getMaxFiltered(stack, target, false, supply);
			if(maxStack.stackSize < 1)
				continue;
			if(target instanceof ISidedInventory){
				if(!((ISidedInventory)target).canInsertItem(slot, maxStack, orientation.ordinal()))
					continue;
			}
			List<IItemExtractor> routers = entry.getValue();
			for(int i = 0; i < routers.size(); i++){
				ItemStack s = routers.get(i).extractItemStack(maxStack, false);
				if(s != null){
					stack.stackSize+= s.stackSize;
					maxStack.stackSize -= s.stackSize;
					if(maxStack.stackSize < 1){
						target.setInventorySlotContents(slot, stack);
						target.markDirty();
						return true;
					}
				}
			}
			if(stack.stackSize > 0){
				target.setInventorySlotContents(slot, stack);
				target.markDirty();
				return true;
			}
		}
		return false;
	}
	protected boolean fillSlot(int slot, ItemFilter filter, boolean supply){
		ItemStack stack = target.getStackInSlot(slot);
		ItemStack maxStack = stack.copy();
		maxStack.stackSize = filter.getMaxFiltered(stack, target, true, supply);
		if(maxStack.stackSize < 1)
			return false;
		ItemWrapper w = new ItemWrapper(stack);
		int size = stack.stackSize;
		if(target instanceof ISidedInventory){
			if(!((ISidedInventory)target).canInsertItem(slot, maxStack, orientation.ordinal()))
				return false;
		}
		List<IItemExtractor> routers = parrent.getGrid().getChannel(channel).getRoutersPushing(w);
		if(routers == null)
			return false;
		for(int i = 0; i < routers.size(); i++){
			ItemStack s = routers.get(i).extractItemStack(maxStack, false);
			if(s != null){
				stack.stackSize+= s.stackSize;
				maxStack.stackSize -= s.stackSize;
				target.markDirty();
				if(maxStack.stackSize < 1)
					return true;
			}
		}
		return size != stack.stackSize;
	}
	public ItemStack insertItem(ItemStack stack, boolean simulate, boolean supplier, ItemFilter filter) {
		if(target == null)
			return null;
		int[] slots = getSlots();
		if(slots == null || slots.length < 1)
			return null;
		if(filter == null)
			filter = new ItemFilter();
		if(filter.getMaxFiltered(stack, target, false, supplier) < 1)
			return null;
		System.out.println("can insert "+filter.getMaxFiltered(stack, target, false, supplier));
		for(int i : slots){
			if(target instanceof ISidedInventory){
				if(!((ISidedInventory)target).canInsertItem(i, stack.copy(), orientation.ordinal()))
					continue;
			}
			if(target.getStackInSlot(i) == null){
				int max = filter.getMaxFiltered(stack, target, false, supplier);
				ItemStack stack2 = stack.copy();
				stack2.stackSize = max;
				if(!simulate){
					target.setInventorySlotContents(i, stack2);
					target.markDirty();
					return target.getStackInSlot(i);
				}
				return stack2;
			}else if(compareItems(stack, target.getStackInSlot(i))){
				int max = filter.getMaxFiltered(target.getStackInSlot(i), target, true, supplier);
				if(max < 1)
					continue;
				ItemStack stack2 = target.getStackInSlot(i).copy();
				stack2.stackSize = max;
				if(!simulate){
					target.getStackInSlot(i).stackSize += max;
					target.markDirty();
					return stack2;
				}
				return stack2;
			}
		}
		return null;
	}
	@Override
	public ItemStack insertItem(ItemStack stack, boolean simulate, boolean supplier) {
		return insertItem(stack, simulate, supplier, filter.copy());
	}
	@Override
	public boolean isSupplier() {
		return false;
	}
	@Override
	public void onPreGridChange() {
		parrent.getGrid().getChannel(channel).removeDestination(this);
	}

	@Override
	public void onPostGridChange() {
		parrent.getGrid().getChannel(channel).addDestination(this);
	}
}
