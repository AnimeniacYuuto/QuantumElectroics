package yuuto.quantumelectronics.gui.base;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;



public abstract class GuiEnergyHandler extends GuiContainer{

	public GuiEnergyHandler(Container cont) {
		super(cont);
	}

	/**
	 * Draws an energy bar for the given energy handler given these coordinates
	 * @param x
	 * @param y
	 * @param u texture uv location x
	 * @param v texture uv location y
	 * @param w width
	 * @param h height
	 * @param energyHandler
	 */
	public void drawEnergyBar(int x, int y, int u, int v, int w, int h, IEnergyHandler energyHandler){
		float precent = (float)energyHandler.getEnergyStored(ForgeDirection.UNKNOWN)/(float)energyHandler.getMaxEnergyStored(ForgeDirection.UNKNOWN);
		float p2 = 1-precent;
		this.drawTexturedModalRect(x, (int)(y+(h*p2)), u, (int)(v+(h*p2)), w, (int)(h*precent));
	}

}
