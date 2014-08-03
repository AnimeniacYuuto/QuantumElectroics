package yuuto.quantumelectronics.gui.base;

import yuuto.quantumelectronics.gui.*;
import yuuto.quantumelectronics.gui.container.*;
import yuuto.quantumelectronics.tile.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		switch(ID){
		case 0:
			if(tile instanceof TileGemGrinder)
				return new ContainerGemGrinder((TileGemGrinder) tile, player);
			break;
		case 1:
			if(tile instanceof TileGridFurnace)
				return new ContainerGridFurnace((TileGridFurnace) tile, player);
			break;
		case 2:
			if(tile instanceof TileGridGenerator)
				return new ContainerGridGenerator((TileGridGenerator) tile, player);
			break;
		case 3:
			if(tile instanceof TileGridGrinder)
				return new ContainerGridGrinder((TileGridGrinder) tile, player);
			break;
		case 4:
			if(tile instanceof TilePylon)
				return new ContainerPylon((TilePylon)tile, player);
			break;
		default:
			return null;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		switch(ID){
		case 0:
			if(tile instanceof TileGemGrinder)
				return new GuiGemGrinder((TileGemGrinder) tile, player);
			break;
		case 1:
			if(tile instanceof TileGridFurnace)
				return new GuiGridFurnace((TileGridFurnace) tile, player);
			break;
		case 2:
			if(tile instanceof TileGridGenerator)
				return new GuiGridGenerator((TileGridGenerator) tile, player);
			break;
		case 3:
			if(tile instanceof TileGridGrinder)
				return new GuiGridGrinder((TileGridGrinder) tile, player);
			break;
		case 4:
			if(tile instanceof TilePylon)
				return new GuiPylon((TilePylon)tile, player);
			break;
		default:
			return null;
		}
		return null;
	}

}
