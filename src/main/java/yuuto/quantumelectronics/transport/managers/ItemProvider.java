package yuuto.quantumelectronics.transport.managers;

import java.util.List;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

import yuuto.quantumelectronics.transport.TileGridTile;
import yuuto.quantumelectronics.transport.routing.IItemProvider;
import yuuto.quantumelectronics.transport.routing.ItemWrapper;

public class ItemProvider extends ItemExtractor implements IItemProvider{

	public ItemProvider(TileGridTile parrent) {
		super(parrent);
	}
	@Override
	protected void pushItem(ItemWrapper w){
		parrent.getGrid().getChannel(channel).provideItem(w, this);
	}
	@Override
	protected void pushItems(List<ItemWrapper> w){
		parrent.getGrid().getChannel(channel).provideItems(w, this);
	}
	@Override
	protected void removePushedItem(ItemWrapper w){
		parrent.getGrid().getChannel(channel).removeProvidedItem(w, this);
	}
	@Override
	protected void removePushedItems(List<ItemWrapper> w){
		parrent.getGrid().getChannel(channel).removeProvidedItems(w, this);
	}
	@Override
	public int getNumberProvided(ItemWrapper w) {
		if(filter.getMaxFiltered(w.getStack(), target, false, false) < 1)
			return 0;
		int count = 0;
		ItemStack stack = w.getStack();
		int[] slots = getSlots();
		if(slots == null || slots.length < 1)
			return 0;
		for(int i : slots){
			if(target.getStackInSlot(i) == null)
				continue;
			if(!compareItems(stack, target.getStackInSlot(i)))
				continue;
			if(target instanceof ISidedInventory){
				if(!((ISidedInventory)target).canExtractItem(i, target.getStackInSlot(i), 
						orientation.ordinal())){
					System.out.println("cannot extract");
					continue;
				}
			}
			count += target.getStackInSlot(i).stackSize;
		}
		return count;
	}

}
