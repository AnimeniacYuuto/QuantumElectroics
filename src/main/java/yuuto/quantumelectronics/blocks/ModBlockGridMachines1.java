package yuuto.quantumelectronics.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import yuuto.quantumelectronics.QuantumElectronics;
import yuuto.quantumelectronics.blocks.base.ModBlockContainerMulti;
import yuuto.quantumelectronics.blocks.base.ModBlockMachineMulti;
import yuuto.quantumelectronics.ref.ModTabs;
import yuuto.quantumelectronics.ref.References;
import yuuto.quantumelectronics.tile.TileGridFurnace;
import yuuto.quantumelectronics.tile.TileGridGenerator;
import yuuto.quantumelectronics.tile.TileGridGrinder;
import yuuto.quantumelectronics.transport.ITransportNode;
import yuuto.quantumelectronics.transport.tile.TileFluidRouter;
import yuuto.quantumelectronics.transport.tile.TileGridEnergyAcceptor;
import yuuto.quantumelectronics.transport.tile.TileGridEnergySync;
import yuuto.quantumelectronics.transport.tile.TileRequester;
import yuuto.quantumelectronics.transport.tile.TileItemRouter;

public class ModBlockGridMachines1 extends ModBlockMachineMulti{

	
	
	public ModBlockGridMachines1() {
		super("gridFurnace", "gridGenerator", "gridGrinder", "gridEnergyAcceptor", 
				"gridEnergySync", "ItemExtractor", "ItemReceiver", "ItemProvider", 
				"ItemSupplierActive", "ItemSupplierPassive", "ItemRequster",
				"FluidExtractor", "FluidReceiver", "FluidProvider");
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
		case 3:
			return new TileGridEnergyAcceptor();
		case 4:
			return new TileGridEnergySync();
		case 5:
			return new TileItemRouter(0);
		case 6:
			return new TileItemRouter(1);
		case 7:
			return new TileItemRouter(2);
		case 8:
			return new TileItemRouter(3);
		case 9:
			return new TileItemRouter(4);
		case 10:
			return new TileRequester();
		case 11:
			return new TileFluidRouter(0);
		case 12:
			return new TileFluidRouter(1);
		case 13:
			return new TileFluidRouter(2);
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
		if(tile instanceof TileItemRouter){
			if(world.isRemote)
				return true;
			player.openGui(QuantumElectronics.instance, 5, world, x, y, z);
			return true;
		}
		if(tile instanceof TileRequester){
			if(world.isRemote)
				return true;
			player.openGui(QuantumElectronics.instance, 6, world, x, y, z);
			return true;
		}
		if(tile instanceof TileFluidRouter){
			if(world.isRemote)
				return true;
			player.openGui(QuantumElectronics.instance, 7, world, x, y, z);
			return true;
			
		}
		return false;
	}
	@Override
	@SideOnly(Side.CLIENT)
	protected String getTextureName(int meta, int side){
		if(side == 1)
			return getTextureName(meta)+"FrontInactive";
		if(side == 2)
			return getTextureName(meta)+"FrontActive";
		return String.format("%s%s", References.TEXTURE_PREFIX, "gridMachine");
	}
	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z,
			int tileX, int tileY, int tileZ){
		super.onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null || !(tile instanceof ITransportNode))
			return;
		((ITransportNode)tile).onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
	}


}
