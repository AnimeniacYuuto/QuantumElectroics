package yuuto.quantumelectronics.transport.managers;

import java.util.Comparator;

import net.minecraft.item.ItemStack;

public class StackComparator implements Comparator<ItemStack>{

	@Override
	public int compare(ItemStack stack1, ItemStack stack2) {
		return stack1.getDisplayName().compareTo(stack2.getDisplayName());
	}

}
