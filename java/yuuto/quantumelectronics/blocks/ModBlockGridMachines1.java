package yuuto.quantumelectronics.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import yuuto.quantumelectronics.QuantumElectronics;
import yuuto.quantumelectronics.blocks.base.ModBlockContainerMulti;
import yuuto.quantumelectronics.ref.ModTabs;
import yuuto.quantumelectronics.tile.TileGridFurnace;
import yuuto.quantumelectronics.tile.TileGridGenerator;
import yuuto.quantumelectronics.tile.TileGridGrinder;

public class ModBlockGridMachines1 extends ModBlockContainerMulti{

	public ModBlockGridMachines1() {
		super(Material.rock, ModTabs.TAB_MAIN, "gidFurnace", "gridGenerator", "gridGrinder");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		switch(meta){
		case 0:
			return new TileGridFurnace();
		case 1:
			return new TileGridGenerator();
		case 2:
			return new TileGridGrinder();
		default:
			return null;
		}
	}
	@Override 
	public boolean onBlockActivated(World world, int x, int y, int z, 
			EntityPlayer player, int side, float eX, float eY, float eZ){
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null || player.isSneaking())
			return false;
		if(tile instanceof TileGridFurnace){
			if(world.isRemote)
				return true;
			player.openGui(QuantumElectronics.instance, 1, world, x, y, z);
			return true;
		}
		if(tile instanceof TileGridGenerator){
			if(world.isRemote)
				return true;
			player.openGui(QuantumElectronics.instance, 2, world, x, y, z);
			return true;
		}
		if(tile instanceof TileGridGrinder){
			if(world.isRemote)
				return true;
			player.openGui(QuantumElectronics.instance, 3, world, x, y, z);
			return true;
		}
		return false;
	}

}
