package yuuto.quantumelectronics.util;

import net.minecraft.item.ItemStack;

public class ItemWrapper{
	ItemStack stack;
	
	public ItemWrapper(ItemStack stack){
		this.stack = stack;
	}
	public ItemStack getStack(){
		return stack;
	}
	public static boolean compareStacks(ItemStack stack1, ItemStack stack2){
		if(stack1.getItem() != stack2.getItem())
			return false;
		if(stack1.getItemDamage() != stack2.getItemDamage())
			return false;
		return ItemStack.areItemStackTagsEqual(stack1, stack2);
	} 
	
	@Override
	public boolean equals(Object o){
		if(o instanceof ItemWrapper){
			return compareStacks(stack, ((ItemWrapper)o).getStack());
		}
		if(o instanceof ItemStack){
			return compareStacks(stack, ((ItemStack)o));
		}
		return false;
	}
}
