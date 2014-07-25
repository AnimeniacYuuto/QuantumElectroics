package yuuto.quantumelectronics.transport;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import yuuto.quantumelectronics.blocks.base.ModBlockContainer;
import yuuto.quantumelectronics.ref.ModTabs;

public class BlockPylon extends ModBlockContainer{

	public BlockPylon(String unlocName) {
		super(Material.rock, ModTabs.TAB_MAIN, unlocName);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TilePylon();
	}

}
