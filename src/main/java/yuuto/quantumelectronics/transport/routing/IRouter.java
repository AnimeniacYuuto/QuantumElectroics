package yuuto.quantumelectronics.transport.routing;

import net.minecraftforge.common.util.ForgeDirection;

public interface IRouter {
	void setSneaky(ForgeDirection dir);
	void setOrientation(ForgeDirection dir);
	void setChannel(int c);
	void doWork();
	
	void onPreGridChange();
	void onPostGridChange();
}
