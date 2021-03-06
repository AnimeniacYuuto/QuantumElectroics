package yuuto.quantumelectronics.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import yuuto.quantumelectronics.gui.base.GuiEnergyHandler;
import yuuto.quantumelectronics.gui.container.ContainerPylon;
import yuuto.quantumelectronics.ref.References;
import yuuto.quantumelectronics.tile.TilePylon;

public class GuiPylon extends GuiEnergyHandler{

	static ResourceLocation texture = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/gui/pylon.png");
	TilePylon tile;
	public GuiPylon(TilePylon tile, EntityPlayer player) {
		super(new ContainerPylon(tile, player));
		this.tile = tile;
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
        this.drawEnergyBar(x+(xSize/2)-9, y+8, 176, 0, 18, 68, tile);
	}

}
