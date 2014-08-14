package yuuto.quantumelectronics.blocks.base;

import yuuto.quantumelectronics.ref.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModBlock extends Block{

	public ModBlock(Material mat, CreativeTabs tab, String unlocName) {
		super(mat);
		setCreativeTab(tab);
		setBlockName(unlocName);
	}
	
	@Override
	public String getUnlocalizedName(){
		return String.format("tile.%s%s", References.TEXTURE_PREFIX, unwrapUnlocalizedName(super.getUnlocalizedName()));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg){
		blockIcon = reg.registerIcon(unwrapUnlocalizedName(getUnlocalizedName()));
	}
	
	/**
	 * gets the block name without the tile. prefix
	 * @param unlocName
	 * @return the block name without the tile. prefix
	 */
	protected String unwrapUnlocalizedName(String unlocName){
		return unlocName.substring(unlocName.indexOf(".")+1);
	}

}
