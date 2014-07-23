package yuuto.quantumelectronics.blocks.base;

import java.util.List;

import yuuto.quantumelectronics.ref.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public abstract class ModBlockContainerMulti extends ModBlockContainer{

	String[] unlocNames;
	IIcon[] blockIcons;
	
	public ModBlockContainerMulti(Material mat, CreativeTabs tab,
			String... unlocNames) {
		super(mat, tab, unlocNames[0]);
		this.unlocNames = unlocNames;
	}
	
	@Override
	public int damageDropped(int metaData)
    {
        return metaData;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg){
		super.registerBlockIcons(reg);
		blockIcons = new IIcon[unlocNames.length];
		for(int i = 0; i < blockIcons.length; i++){
			blockIcons[i] = reg.registerIcon(getTextureName(i));
		}
	}
	@SideOnly(Side.CLIENT)
	protected String getTextureName(int metaData){
		return String.format("%s%s", References.TEXTURE_PREFIX, unlocNames[metaData]);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta){
		if(meta >= blockIcons.length)
			return super.getIcon(side, meta);
		return blockIcons[meta];
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
		for (int i = 0; i < 16; i++) {
			subItems.add(new ItemStack(this, 1, i));
		}
	}
	
	public String[] getUnlocalisedNames(){
		return unlocNames;
	}

}
