package yuuto.quantumelectronics.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import yuuto.quantumelectronics.QuantumElectronics;
import yuuto.quantumelectronics.blocks.base.ModBlockMachineMulti;
import yuuto.quantumelectronics.ref.References;
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
			return true;
		}
		return false;
	}
	@SideOnly(Side.CLIENT)
	protected String getTextureName(int meta, int side){
		if(side == 1)
			if(meta < 3 || meta > 5)
				return getTextureName(meta)+"FrontInactive";
		if(side == 2){
			if(meta < 3 || meta > 5)
				return getTextureName(meta)+"FrontActive";
		}
		if(meta == 0 || meta == 3)
			return String.format("%s%s", References.TEXTURE_PREFIX, "blockParidot");
		if(meta == 1 || meta == 4)
			return String.format("%s%s", References.TEXTURE_PREFIX, "blockRuby");
		if(meta == 2 || meta == 5)
			return String.format("%s%s", References.TEXTURE_PREFIX, "blockSapphire");
		return getTextureName(meta);
	}

}
