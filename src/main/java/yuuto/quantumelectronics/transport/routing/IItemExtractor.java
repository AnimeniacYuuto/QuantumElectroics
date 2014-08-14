package yuuto.quantumelectronics.transport.routing;

import net.minecraft.item.ItemStack;

public interface IItemExtractor extends IItemRouter{
	ItemStack extractItemStack(ItemStack stack, boolean simulate);
}
