package yuuto.quantumelectronics.transport;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockTransportNode extends BlockTransport{

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
		
		int dir = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		if(dir < 0 || dir > 5)
			dir = 2;
		
		tile.setOrientation(ForgeDirection.getOrientation(dir));
	}

}
