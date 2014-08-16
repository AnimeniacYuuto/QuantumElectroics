package yuuto.quantumelectronics.transport.renderer;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import yuuto.quantumelectronics.tile.base.IMachine;
import yuuto.quantumelectronics.tile.renderer.RendererModelPylon;
import yuuto.quantumelectronics.transport.tile.TileFluidRouter;
import yuuto.quantumelectronics.transport.tile.TileGridEnergyAcceptor;
import yuuto.quantumelectronics.transport.tile.TileGridEnergySync;
import yuuto.quantumelectronics.transport.tile.TileItemRouter;
import yuuto.quantumelectronics.transport.tile.TileNodeChassi;
import yuuto.quantumelectronics.util.ColorRGBAb;


public class RendererTileRouter extends TileEntitySpecialRenderer{

	@Override
	public void renderTileEntityAt(TileEntity tile, double x,
			double y, double z, float f) {
		if(tile instanceof IMachine){
			ColorRGBAb plyonColor = getPylonColor(tile);
			RendererModelPylon.render(x, y, z+1, 1f, 1f, 1f, 
					((IMachine)tile).getOrientation().ordinal(), 
					plyonColor.red, plyonColor.green, plyonColor.blue, plyonColor.alpha);
			RendererNode.render(x, y, z+1, 1f, 1f, 1f, 
					((IMachine)tile).getOrientation().ordinal(), 0,
					96, 96, 192, 255);
			ColorRGBAb baseColor = getBaseColor(tile);
			RendererNode.render(x, y, z+1, 1f, 1f, 1f, 
					((IMachine)tile).getOrientation().ordinal(), 1,
					baseColor.red, baseColor.green, baseColor.blue, baseColor.alpha);
		}
	}
	
	private ColorRGBAb getPylonColor(TileEntity tile){
		if(tile instanceof TileFluidRouter)
			return new ColorRGBAb(0,0,255, 191);
		if(tile instanceof TileGridEnergySync || 
				tile instanceof TileGridEnergyAcceptor)
			return new ColorRGBAb(255,255,0, 191);
		if(tile instanceof TileItemRouter)
			return new ColorRGBAb(0, 255,0, 191);
		if(tile instanceof TileNodeChassi)
			return new ColorRGBAb(191, 191, 255, 204);
		return new ColorRGBAb(255, 255, 255, 191);
	}
	private ColorRGBAb getBaseColor(TileEntity tile){
		int i = tile.getBlockMetadata();
		switch(i){
		case 0:
		case 2:
		case 7:
			return new ColorRGBAb(0, 128, 0, 255);
		case 1:
		case 3:
		case 8:
			return new ColorRGBAb(128, 128, 0, 255);
		case 4:
		case 9:
			return new ColorRGBAb(0, 0, 255, 255);
		case 5:
			return new ColorRGBAb(255, 128, 0, 255);
		case 6:
			return new ColorRGBAb(255, 0, 0, 255);
		case 10:
			return new ColorRGBAb(191, 191, 255, 255);
		default:
			return new ColorRGBAb(255, 255, 255, 255);
		}
	}
}
