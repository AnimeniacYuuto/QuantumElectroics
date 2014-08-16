package yuuto.quantumelectronics.blocks.base;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import yuuto.quantumelectronics.ref.ModTabs;
import yuuto.quantumelectronics.tile.base.IMachine;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class ModBlockMachineMulti extends ModBlockContainerMulti{

	protected IIcon[][] blockIcons;
	public ModBlockMachineMulti(String... unlocNames) {
		super(Material.rock, ModTabs.TAB_MAIN, unlocNames);
	}
	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side){
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null || !(tile instanceof IMachine)){
			return getIcon(side, meta);
		}
		IMachine machine = (IMachine)tile;
		ForgeDirection dir = machine.getOrientation();
		boolean isActive = machine.isActive();
		int adjustedSide =  dir.ordinal() == side ? 1 : 0;
		return getIcon(adjustedSide, meta, isActive);
	}
	/**
	 * Gets the icon for the side and meta data given the current state
	 * @param side
	 * @param meta
	 * @param active
	 * @return the icon for the side and meta data given the current state
	 */
	public IIcon getIcon(int side, int meta, boolean active){
		int adjustedSide = active && side == 1 ? 2 : side; 
		return blockIcons[meta][adjustedSide];
	}
	@Override
	public IIcon getIcon(int side, int meta){
		return blockIcons[meta][side == 2 ? 1 : 0];
	}
	@Override
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis){
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null || !(tile instanceof IMachine))
			return false;
		((IMachine)tile).rotateAround(ForgeDirection.UP);
		return true;
	}
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, 
			EntityLivingBase entity, ItemStack stack){
		super.onBlockPlacedBy(world, x, y, x, entity, stack);
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null || !(tile instanceof IMachine))
			return;
		int dir = determineOrientation(world, x, y, z, entity);
		((IMachine)tile).setOrientation(ForgeDirection.getOrientation(dir));
		
	}
	/**
	 * Determines the orientaion of the block upon placement
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param entity
	 * @return
	 */
	public int determineOrientation(World world, int x, int y, int z, EntityLivingBase entity)
    {
        int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
    }
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg){
		blockIcon = reg.registerIcon(unwrapUnlocalizedName(getUnlocalizedName()));
		blockIcons = new IIcon[unlocNames.length][3];
		for(int i = 0; i < blockIcons.length; i++){
			blockIcons[i][0] = reg.registerIcon(getTextureName(i, 0));
			blockIcons[i][1] = reg.registerIcon(getTextureName(i, 1));
			blockIcons[i][2] = reg.registerIcon(getTextureName(i, 2));
		}
	}
	@SideOnly(Side.CLIENT)
	/**
	 * Gets the texture name for the given side and meta data
	 * @param meta
	 * @param side
	 * @return the texture name for the given side and meta data
	 */
	protected String getTextureName(int meta, int side){
		if(side == 1)
			return getTextureName(meta)+"FrontInactive";
		if(side == 2)
			return getTextureName(meta)+"FrontActive";
		return getTextureName(meta);
	}

}
