package yuuto.quantumelectronics.gui;

import yuuto.quantumelectronics.gui.container.*;
import yuuto.quantumelectronics.tile.TileGemGrinder;
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
		default:
			return null;
		}
		return null;
	}

}
