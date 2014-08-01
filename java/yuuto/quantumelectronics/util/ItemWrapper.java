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
	
	@Override
	public boolean equals(Object o){
		if(o instanceof ItemWrapper){
			if(stack.isItemEqual(((ItemWrapper)o).getStack()))
				return true;
			return false;
		}
		if(o instanceof ItemStack){
			if(stack.isItemEqual((ItemStack)o))
				return true;
			return false;
		}
		return false;
	}
}
