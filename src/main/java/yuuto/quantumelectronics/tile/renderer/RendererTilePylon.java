package yuuto.quantumelectronics.tile.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RendererTilePylon extends TileEntitySpecialRenderer{

	@Override
	public void renderTileEntityAt(TileEntity tile, double x,
			double y, double z, float f) {
		RendererModelPylon.render(x, y, z+1, 1f, 1f, 1f, 255, 255, 255, 255);
	}
}
