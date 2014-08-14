package yuuto.quantumelectronics.transport.routing;

import net.minecraft.item.ItemStack;

public interface IItemDestination extends IItemRouter{

	ItemStack insertItem(ItemStack stack, boolean simulate, boolean supplier);
	
	boolean isSupplier();
}
