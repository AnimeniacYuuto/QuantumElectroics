package yuuto.quantumelectronics;

import yuuto.quantumelectronics.handlers.PacketHandler;
import yuuto.quantumelectronics.proxy.ProxyCommon;
import yuuto.quantumelectronics.ref.References;
import yuuto.quantumelectronics.util.Logger;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = References.MOD_ID, name = References.MOD_NAME, version = References.VERSION, guiFactory = References.GUI_FACTORY)
public class QuantumElectronics {

	@Instance(References.MOD_ID)
	public static QuantumElectronics instance;
	
	@SidedProxy(clientSide = References.PROXY_CLIENT, serverSide = References.PROXY_SERVER)
	public static ProxyCommon proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event){
		proxy.preInit(event);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event){
		proxy.init(event);
		PacketHandler.registerMessages();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}
}
