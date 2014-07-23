package yuuto.quantumelectronics.client.gui;

import java.util.ArrayList;
import java.util.List;

import yuuto.quantumelectronics.handlers.ConfigHandler;
import yuuto.quantumelectronics.ref.References;

import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

public class ModGuiConfig extends GuiConfig{

	public ModGuiConfig(GuiScreen parentScreen) {
		super(parentScreen, 
				getConfigElements(), 
				References.MOD_ID, 
				false,
				false, 
				getAbridgedConfigPath(ConfigHandler.config.toString()));
	}
	private static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        return list;
    }

}
