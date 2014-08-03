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

	public void drawEnergyBar(int x, int y, int u, int v, int w, int h, IEnergyHandler energyHandler){
		float precent = (float)energyHandler.getEnergyStored(ForgeDirection.UNKNOWN)/(float)energyHandler.getMaxEnergyStored(ForgeDirection.UNKNOWN);
		float p2 = 1-precent;
		this.drawTexturedModalRect(x, (int)(y+(h*p2)), u, (int)(v+(h*p2)), w, (int)(h*precent));
	}

}
