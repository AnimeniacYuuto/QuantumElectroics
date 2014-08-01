package yuuto.quantumelectronics.gui;

import yuuto.quantumelectronics.gui.container.ContainerGemGrinder;
import yuuto.quantumelectronics.tile.TileGemGrinder;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class GuiGemGrinder extends GuiContainer{

	public GuiGemGrinder(TileGemGrinder tile, EntityPlayer player) {
		super(new ContainerGemGrinder(tile, player));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_,
			int p_146976_2_, int p_146976_3_) {
				
	}

}
