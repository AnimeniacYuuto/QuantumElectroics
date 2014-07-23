package yuuto.quantumelectronics.items.base;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import yuuto.quantumelectronics.blocks.base.ModBlockContainerMulti;
import yuuto.quantumelectronics.blocks.base.ModBlockMulti;
import yuuto.quantumelectronics.ref.References;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ModItemBlockMulti extends ItemBlock{

	String[] unlocNames;
	public ModItemBlockMulti(ModBlockMulti block) {
		super(block);
		unlocNames = block.getUnlocalisedNames();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	public ModItemBlockMulti(ModBlockContainerMulti block) {
		super(block);
		unlocNames = block.getUnlocalisedNames();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	@Override
	public String getUnlocalizedName(){
		return getUnlocalizedName(0);
	}
	public String getUnlocalizedName(int subItem){
		if(subItem >= unlocNames.length)
			return super.getUnlocalizedName();
		return String.format("tile.%s%s", References.TEXTURE_PREFIX, unlocNames[subItem]);
	}
	@Override
	public String getUnlocalizedName(ItemStack stack){
		return getUnlocalizedName(stack.getItemDamage());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int subItem){
		return this.field_150939_a.getIcon(2, subItem);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List results){
		for(int i = 0; i < unlocNames.length; i++){
			results.add(new ItemStack(this, 1, i));
		}
	}
	
	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}

}
