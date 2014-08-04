package yuuto.quantumelectronics.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import yuuto.quantumelectronics.QuantumElectronics;
import yuuto.quantumelectronics.blocks.base.ModBlockContainer;
import yuuto.quantumelectronics.ref.ModTabs;
import yuuto.quantumelectronics.tile.TilePylon;

public class ModBlockPylon extends ModBlockContainer{

	public ModBlockPylon() {
		super(Material.rock, ModTabs.TAB_MAIN, "Pylon");
		setLightLevel(1f);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TilePylon();
	}
	public boolean onBlockActivated(World world, int x, int y, int z, 
			EntityPlayer player, int side, float eX, float eY, float eZ){
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null || player.isSneaking())
			return false;
		if(tile instanceof TilePylon){
			if(world.isRemote)
				return true;
			player.openGui(QuantumElectronics.instance, 4, world, x, y, z);
			return true;
		}
		return false;
	}
	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l){
	   return false;
	}
	@Override
    public boolean isOpaqueCube(){
        return false;
    }
}
