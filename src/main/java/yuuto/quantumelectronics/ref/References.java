package yuuto.quantumelectronics.ref;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class References {

	public static final String MOD_ID = "QuantumElectronics";
	public static final String MOD_NAME = "Quantum Electronics";
	public static final String VERSION = "1.7.10-1.1.a";
	
	public static final String ROOT = "yuuto.quantumelectronics";
	public static final String PROXY_CLIENT = ROOT+".proxy.ProxyClient";
	public static final String PROXY_SERVER = ROOT+".proxy.ProxyServer";
	public static final String GUI_FACTORY = ROOT+".client.gui.ModGuiFactory";
	
	public static final String TEXTURE_PREFIX = MOD_ID.toLowerCase()+":";
}
