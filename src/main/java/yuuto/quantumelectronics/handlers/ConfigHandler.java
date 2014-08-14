package yuuto.quantumelectronics.handlers;

import java.io.File;

import yuuto.quantumelectronics.ref.References;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.common.config.Configuration;


public class ConfigHandler {
	public static Configuration config;
	
	public static void Init(File file){
		config = new Configuration(file);
		updateConfig();
	}
	
	@SubscribeEvent
	public void onChanged(ConfigChangedEvent.OnConfigChangedEvent event){
		if(event.modID == References.MOD_ID){
			updateConfig();
		}
	}
	
	
	static void updateConfig(){
		if(config.hasChanged())
			config.save();
	}
}
