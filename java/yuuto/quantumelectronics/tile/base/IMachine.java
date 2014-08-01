package yuuto.quantumelectronics.tile.base;

import net.minecraftforge.common.util.ForgeDirection;

public interface IMachine {
	public ForgeDirection getOrientation();
	public ForgeDirection setOrientation(ForgeDirection dir);
	public ForgeDirection rotateAround(ForgeDirection axis);
	public boolean isActive();
}
