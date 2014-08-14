package yuuto.quantumelectronics.items.base;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import yuuto.quantumelectronics.ref.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModItemMulti extends ModItem{
	
	protected String[] unlocNames;
	IIcon[] itemIcons;
	
	public ModItemMulti(CreativeTabs tab, String ... unlocNames){
		super(tab, unlocNames[0]);
		this.unlocNames = unlocNames;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	@Override
	public String getUnlocalizedName(){
		return getUnlocalizedName(0);
	}
	/**
	 * Gets the unlocalized name for the given sub-item
	 * @param subItem
	 * @return the unlocalized name for the given sub-item
	 */
	public String getUnlocalizedName(int subItem){
		if(subItem >= unlocNames.length)
			return super.getUnlocalizedName();
		return String.format("item.%s%s", References.TEXTURE_PREFIX, unlocNames[subItem]);
	}
	@Override
	public String getUnlocalizedName(ItemStack stack){
		return getUnlocalizedName(stack.getItemDamage());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg){
		super.registerIcons(reg);
		itemIcons = new IIcon[unlocNames.length];
		for(int i = 0; i < itemIcons.length; i++){
			itemIcons[i] = reg.registerIcon(unwrapUnlocalizedName(getUnlocalizedName(i)));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int subItem){
		if(subItem >= itemIcons.length)
			return super.getIconFromDamage(subItem);
		return itemIcons[subItem];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List results){
		for(int i = 0; i < unlocNames.length; i++){
			results.add(new ItemStack(this, 1, i));
		}
	}
}
