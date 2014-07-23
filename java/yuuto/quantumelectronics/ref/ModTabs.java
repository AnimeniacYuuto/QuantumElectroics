package yuuto.quantumelectronics.ref;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModTabs {
	public static final CreativeTabs TAB_MAIN = new CreativeTabs("tab."+References.TEXTURE_PREFIX+"Main"){
		@Override
	    @SideOnly(Side.CLIENT)
	    public Item getTabIconItem() {
	        return Item.getItemFromBlock(Blocks.furnace);
	    }
	};
}
