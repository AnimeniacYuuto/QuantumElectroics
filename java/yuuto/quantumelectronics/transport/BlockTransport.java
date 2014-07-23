package yuuto.quantumelectronics.transport;

import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import yuuto.quantumelectronics.blocks.base.ModBlockContainerMulti;
import yuuto.quantumelectronics.ref.ModTabs;

public abstract class BlockTransport extends ModBlockContainerMulti{

	public BlockTransport(String... unlocNames) {
		super(Material.rock, ModTabs.TAB_MAIN, unlocNames);
	}
	
	
	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, 
			int tileX, int tileY, int tileZ){
		TileTransport tile = (TileTransport)world.getTileEntity(x, y, z);
		if(tile == null)
			return;
		tile.onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
	}
	

}
