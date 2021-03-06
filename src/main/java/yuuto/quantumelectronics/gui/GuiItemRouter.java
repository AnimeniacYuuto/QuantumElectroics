package yuuto.quantumelectronics.gui;

import org.lwjgl.opengl.GL11;

import yuuto.quantumelectronics.gui.container.ContainerItemRouter;
import yuuto.quantumelectronics.ref.References;
import yuuto.quantumelectronics.transport.tile.TileItemRouter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiItemRouter extends GuiContainer{

	static ResourceLocation texture = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/gui/filterBasic.png");
	public GuiItemRouter(TileItemRouter tile, EntityPlayer player) {
		super(new ContainerItemRouter(tile, player));
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_,
			int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(texture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
