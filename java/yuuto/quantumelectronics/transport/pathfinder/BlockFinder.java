package yuuto.quantumelectronics.transport.pathfinder;

import java.util.ArrayList;
import java.util.List;

import yuuto.quantumelectronics.transport.TilePylon;
import yuuto.quantumelectronics.util.Logger;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFinder {
	public static Vec3 findNearstOpaqueBlock(World world, int x, int y, int z,
			ForgeDirection dir, int distance){
		for(int i = 0; i < distance; i++){
			x += dir.offsetX;
			y += dir.offsetY;
			z += dir.offsetZ;
			if(!world.blockExists(x, y, z))
				return null;
			if(world.isAirBlock(x, y, z))
				continue;
			if(world.getBlock(x, y, z).isOpaqueCube())
				return Vec3.createVectorHelper(x, y, z);
		}
		return null;
	}
	public static List<Vec3> findAllBlocksWithin(Block b, World world, int x, int y, int z, 
			int distance){
		List<Vec3> ret = new ArrayList<Vec3>();
		for(int x1 = x-distance; x1 <= x+distance; x1++){
			for(int y1 = y-distance; y1 <= x+distance; y1++){
				for(int z1 = x-distance; z1 <= x+distance; z1++){
					if(x1 == x && y1 == y && z1 == z)
						continue;
					if(b == world.getBlock(x1, y1, z1))
						ret.add(Vec3.createVectorHelper(x1, y1, z1));
				}
			}
		}
		return ret;
	}
}
