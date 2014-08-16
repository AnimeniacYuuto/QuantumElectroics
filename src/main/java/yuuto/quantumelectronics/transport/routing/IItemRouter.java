package yuuto.quantumelectronics.transport.routing;

import yuuto.quantumelectronics.transport.filter.ItemFilter;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.common.util.ForgeDirection;

public interface IItemRouter extends IRouter{
	void setOrientation(ForgeDirection dir);
	void setChannel(int c);
	void setTarget(IInventory t);
	void setFilter(ItemFilter f);
	void doWork();
	
	void onPreGridChange();
	void onPostGridChange();
}
