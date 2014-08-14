package yuuto.quantumelectronics.transport;

import net.minecraft.world.IBlockAccess;

public interface ITransportNode {
	void onNeighborChange(IBlockAccess world, int x, int y, int z,
			int tileX, int tileY, int tileZ);
}
