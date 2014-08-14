package yuuto.quantumelectronics.transport.managers;

import java.util.Comparator;

import yuuto.quantumelectronics.transport.routing.ItemProviderStack;

import net.minecraft.item.ItemStack;

public class StackComparatorProvider implements Comparator<ItemProviderStack>{

	@Override
	public int compare(ItemProviderStack stack1, ItemProviderStack stack2) {
		return stack1.getDisplayName().compareTo(stack2.getDisplayName());
	}

}
