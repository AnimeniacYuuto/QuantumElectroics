package yuuto.quantumelectronics.proxy;

import net.minecraft.creativetab.CreativeTabs;
import yuuto.quantumelectronics.ModBlocks;
import yuuto.quantumelectronics.ModItems;
import yuuto.quantumelectronics.handlers.ConfigHandler;
import yuuto.quantumelectronics.items.base.ModItem;
import yuuto.quantumelectronics.recipe.RecipeFactory;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public abstract class ProxyCommon {
	
	public void preInit(FMLPreInitializationEvent event){
		ConfigHandler.Init(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigHandler());
		ModBlocks.init();
		ModItems.init();
		RecipeFactory.init();
	}
	
	public void init(FMLInitializationEvent event){
		
	}
	
	public void postInit(FMLPostInitializationEvent event){
		RecipeFactory.postInit();
	}

}
