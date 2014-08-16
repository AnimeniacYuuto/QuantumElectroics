package yuuto.quantumelectronics.handlers;

import yuuto.quantumelectronics.ModItems;
import yuuto.quantumelectronics.gui.*;
import yuuto.quantumelectronics.gui.container.*;
import yuuto.quantumelectronics.tile.*;
import yuuto.quantumelectronics.transport.tile.TileFluidRouter;
import yuuto.quantumelectronics.transport.tile.TileRequester;
import yuuto.quantumelectronics.transport.tile.TileItemRouter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		ItemStack item = player.inventory.getCurrentItem();
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
		case 5:
			if(tile instanceof TileItemRouter)
				return new ContainerItemRouter((TileItemRouter)tile, player);
			break;
		case 6:
			if(tile instanceof TileRequester)
				return new ContainerRequester((TileRequester) tile, player);
			break;
		case 7:
			if(tile instanceof TileFluidRouter)
				return new ContainerFluidRouter((TileFluidRouter)tile, player);
			break;
		case 101:
			if(item.getItem() == ModItems.MODULES)
				return new ContainerModule(item, player);
			break;
		default:
			return null;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		ItemStack item = player.inventory.getCurrentItem();
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
		case 5:
			if(tile instanceof TileItemRouter)
				return new GuiItemRouter((TileItemRouter)tile, player);
			break;
		case 6:
			if(tile instanceof TileRequester)
				return new GuiRequester((TileRequester) tile, player);
			break;
		case 7:
			if(tile instanceof TileFluidRouter)
				return new GuiFluidRouter((TileFluidRouter)tile, player);
			break;
		case 101:
			if(item.getItem() == ModItems.MODULES)
				return new GuiModule(item, player);
			break;
		default:
			return null;
		}
		return null;
	}

}
