package yuuto.quantumelectronics.transport;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import yuuto.quantumelectronics.ref.ModTabs;

public class BlockConduit extends BlockTransport{

	public BlockConduit(String... unlocNames) {
		super(unlocNames);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileConduit();
	}

}
