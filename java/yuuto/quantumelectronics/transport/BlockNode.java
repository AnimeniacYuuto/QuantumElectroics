package yuuto.quantumelectronics.transport;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import yuuto.quantumelectronics.blocks.base.ModBlockContainer;
import yuuto.quantumelectronics.ref.ModTabs;

public class BlockNode extends BlockTransportNode{

	public BlockNode(String... unlocNames) {
		super(unlocNames);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		switch(meta){
		default:
		case 0:
			return new TileItemExtractor();
		case 1:
			return new TileItemReceiver();
		}
	}

}
