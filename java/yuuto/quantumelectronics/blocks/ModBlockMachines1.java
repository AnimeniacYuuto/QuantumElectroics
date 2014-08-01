package yuuto.quantumelectronics.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import yuuto.quantumelectronics.QuantumElectronics;
import yuuto.quantumelectronics.blocks.base.ModBlockMachineMulti;
import yuuto.quantumelectronics.tile.TileGemFurnace;
import yuuto.quantumelectronics.tile.TileGemGrinder;
import yuuto.quantumelectronics.tile.TileGemGrinderCrank;

public class ModBlockMachines1 extends ModBlockMachineMulti{

	public ModBlockMachines1(){
		super("GemFurnaceParidot", "GemFurnaceRuby", "GemFurnaceSapphire",
				"GemGrinderParidot", "GemGrinderRuby", "GemGrinderSapphire",
				"GemGrinderCrank");
	}
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		switch(meta){
		default:
		case 0:
		case 1:
		case 2:
			return new TileGemFurnace(meta);
		case 3:
		case 4:
		case 5:
			return new TileGemGrinder(meta);
		case 6:
			return new TileGemGrinderCrank();
		}
	}
	
	@Override 
	public boolean onBlockActivated(World world, int x, int y, int z, 
			EntityPlayer player, int side, float eX, float eY, float eZ){
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null || player.isSneaking())
			return false;
		if(tile instanceof TileGemFurnace){
			if(world.isRemote)
				return true;
			player.func_146101_a((TileEntityFurnace)tile);
			return true;
		}
		if(tile instanceof TileGemGrinder){
			if(world.isRemote)
				return true;
			player.openGui(QuantumElectronics.instance, 0, world, x, y, z);
			return true;
		}
		if(tile instanceof TileGemGrinderCrank){
			if(world.isRemote)
				return true;
			((TileGemGrinderCrank)tile).onActivated();
		}
		return false;
	}

}
