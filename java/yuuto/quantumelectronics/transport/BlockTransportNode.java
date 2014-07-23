package yuuto.quantumelectronics.transport;

import yuuto.quantumelectronics.ref.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockTransportNode extends BlockTransport{

	IIcon[][] blockIcons;
	public BlockTransportNode(String... unlocNames) {
		super(unlocNames);
	}
	
	@Override
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis){
		TileTransportNode tile = (TileTransportNode) world.getTileEntity(x, y, z);
		if(tile == null)
			return false;
		tile.rotate(axis);
		return true;
	}
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack item) {
		TileTransportNode tile = (TileTransportNode)world.getTileEntity(x, y, z);
		if(tile == null)
			return;
		if (MathHelper.abs((float)player.posX - (float)x) < 2.0F && MathHelper.abs((float)player.posZ - (float)z) < 2.0F)
        {
            double d0 = player.posY + 1.82D - (double)player.yOffset;

            if (d0 - (double)y > 2.0D)
            {
                tile.setOrientation(ForgeDirection.getOrientation(0));
            	return;
            }

            if ((double)y - d0 > 0.0D)
            {
            	tile.setOrientation(ForgeDirection.getOrientation(1));
            	return;
            }
        }
		
		int dir = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		switch(dir){
		case 0:
			tile.setOrientation(ForgeDirection.getOrientation(3));
			return;
		case 1:
			tile.setOrientation(ForgeDirection.getOrientation(4));
			return;
		case 2:
			tile.setOrientation(ForgeDirection.getOrientation(2));
			return;
		case 3:
			tile.setOrientation(ForgeDirection.getOrientation(5));
			return;
		}
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg){
		blockIcons = new IIcon[unlocNames.length][3];
		for(int i = 0; i < blockIcons.length; i++){
			blockIcons[i][0] = reg.registerIcon(getTextureName(i));
			blockIcons[i][1] = reg.registerIcon(getTextureName(i)+"Front");
			blockIcons[i][2] = reg.registerIcon(getTextureName(i)+"Back");
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
			meta = 0; 
		if(side >= blockIcons[meta].length)
			side = 0;
		return blockIcons[meta][side];
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side){
		int meta = world.getBlockMetadata(x, y, z);
		if(meta >= blockIcons.length)return null;
		TileTransportNode tile = (TileTransportNode) world.getTileEntity(x, y, z);
		if(tile == null)
			return getIcon(0, meta);
		
		ForgeDirection dir = tile.getOrientation();
		if(dir.ordinal() == side)
			return getIcon(1, meta);
		if(dir.getOpposite().ordinal() == side)
			return getIcon(2, meta);
		return getIcon(0, meta);
	}
}
