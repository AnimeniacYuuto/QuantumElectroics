package yuuto.quantumelectronics.transport.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import yuuto.quantumelectronics.transport.TileGridTile;
import yuuto.quantumelectronics.transport.filter.ItemFilter;
import yuuto.quantumelectronics.transport.grid.PylonGrid;
import yuuto.quantumelectronics.transport.routing.IItemExtractor;
import yuuto.quantumelectronics.transport.routing.ItemWrapper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemExtractor extends ItemRouter implements IItemExtractor{
	
	ArrayList<ItemWrapper> pushedItems = new ArrayList<ItemWrapper>();
	
	public ItemExtractor(TileGridTile parrent){
		super(parrent);
	}
	
	@Override
	public void setChannel(int c){
		removePushedItems(pushedItems);
		super.setChannel(c);
		pushItems(pushedItems);
	}
	@Override
	public void doWork(){
		if(target == null){
			if(pushedItems.size() > 0){
				removePushedItems(pushedItems);
				pushedItems.clear();
			}
			return;
		}
		int[] slots = getSlots();
		if(slots == null || slots.length < 1)
			return;
		ArrayList<ItemWrapper> lastPushed = (ArrayList<ItemWrapper>)pushedItems.clone();
		pushedItems.clear();
		if(filter == null)
			filter = new ItemFilter();
		for(int i : slots){
			if(target.getStackInSlot(i) == null)
				continue;
			if(filter.getMaxFiltered(target.getStackInSlot(i), target, false, false) < 1){
				//System.out.println("Not Filtered");
				continue;
			}
			if(target instanceof ISidedInventory){
				if(!((ISidedInventory)target).canExtractItem(i, target.getStackInSlot(i), 
						orientation.ordinal())){
					continue;
				}
			}
			ItemWrapper w = new ItemWrapper(target.getStackInSlot(i));
			if(pushedItems.contains(w))
				continue;
			pushedItems.add(w);
			if(contains(lastPushed, w)){
				remove(lastPushed, w);
				continue;
			}
			pushItem(w);
		}
		removePushedItems(lastPushed);
	}
	private boolean contains(List<ItemWrapper> list, ItemWrapper wrapper){
		for(ItemWrapper w : list){
			if(w.equals(wrapper))
				return true;
		}
		return false;
	}
	public void remove(ArrayList<ItemWrapper> list, ItemWrapper wrapper){
		Iterator<ItemWrapper> itr = ((ArrayList<ItemWrapper>)list.clone()).iterator();
		while(itr.hasNext()){
			ItemWrapper w = itr.next();
			if(w.equals(wrapper))
				list.remove(w);
		}
	}
	@Override
	public ItemStack extractItemStack(ItemStack stack, boolean simulate) {
		if(target == null)
			return null;
		int[] slots = getSlots();
		if(slots == null || slots.length < 1)
			return null;
		for(int i : slots){
			if(target.getStackInSlot(i) == null)
				continue;
			if(target instanceof ISidedInventory){
				if(!((ISidedInventory)target).canExtractItem(i, target.getStackInSlot(i), 
						orientation.ordinal())){
					continue;
				}
			}
			if(!compareItems(stack, target.getStackInSlot(i)))
				continue;
			ItemStack ret = target.decrStackSize(i, stack.stackSize);
			/*if(target.getStackInSlot(i) == null){
				ItemWrapper w = new ItemWrapper(ret);
				if(pushedItems.contains(w))
					pushedItems.remove(w);
				removePushedItem(w);
			}*/
			target.markDirty();
			return ret;
		}
		return null;
	}
	protected void pushItem(ItemWrapper w){
		parrent.getGrid().getChannel(channel).pushItem(w, this);
	}
	protected void pushItems(List<ItemWrapper> w){
		parrent.getGrid().getChannel(channel).pushItems(w, this);
	}
	protected void removePushedItem(ItemWrapper w){
		parrent.getGrid().getChannel(channel).removePushedItem(w, this);
	}
	protected void removePushedItems(List<ItemWrapper> w){
		parrent.getGrid().getChannel(channel).removePushedItems(w, this);
	}

	@Override
	public void onPreGridChange() {
		removePushedItems(pushedItems);
	}

	@Override
	public void onPostGridChange() {
		pushItems(pushedItems);
	}
	
}
