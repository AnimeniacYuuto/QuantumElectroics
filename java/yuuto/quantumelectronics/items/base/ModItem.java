package yuuto.quantumelectronics.items.base;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import yuuto.quantumelectronics.ref.References;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModItem extends Item{

	public ModItem(CreativeTabs tab, String unlocName){
		super();
		setUnlocalizedName(unlocName);
		setCreativeTab(tab);
	}
	
	@Override
	public String getUnlocalizedName(){
		return String.format("item.%s%s", References.TEXTURE_PREFIX, unwrapUnlocalizedName(super.getUnlocalizedName()));
	}
	@Override
	public String getUnlocalizedName(ItemStack stack){
		return String.format("item.%s%s", References.TEXTURE_PREFIX, unwrapUnlocalizedName(super.getUnlocalizedName(stack)));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg){
		itemIcon = reg.registerIcon(unwrapUnlocalizedName(getUnlocalizedName()));
	}
	
	
	protected String unwrapUnlocalizedName(String unlocName){
		return unlocName.substring(unlocName.indexOf(".")+1);
	}
}
