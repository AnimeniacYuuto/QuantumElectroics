package yuuto.quantumelectronics.transport.managers;

import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import yuuto.quantumelectronics.transport.TileGridTile;
import yuuto.quantumelectronics.transport.filter.ItemFilter;
import yuuto.quantumelectronics.transport.routing.IItemExtractor;
import yuuto.quantumelectronics.transport.routing.ItemWrapper;

public class ItemSupplierPassive extends ItemReceiver{

	public ItemSupplierPassive(TileGridTile tile) {
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
		for(int i : slots){
			if(isSlotFull(i, tempFilter))
				continue;
			if(target.getStackInSlot(i) == null){
				if(fillEmptySlot(i,tempFilter, itemsPushed, true))
					return;
				continue;
			}
			if(fillSlot(i, tempFilter, true))
				return;
		}
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
	public boolean isSupplier() {
		return true;
	}

}
