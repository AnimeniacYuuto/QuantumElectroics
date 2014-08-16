package yuuto.quantumelectronics.transport.tile;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import yuuto.quantumelectronics.tile.base.IMachine;
import yuuto.quantumelectronics.transport.ITransportNode;
import yuuto.quantumelectronics.transport.TileGridNode;
import yuuto.quantumelectronics.transport.routing.IRouter;

public class TileNodeChassi extends TileGridNode implements IMachine, ITransportNode, 
	IInventoryParrent{

	int timer = 20;
	ForgeDirection orientation = ForgeDirection.getOrientation(2);
	
	InventoryFilter moduleInventory = new InventoryFilter(9, this);
	IRouter[] routers = new IRouter[9];
	
	TileEntity target;
	
	@Override
	public void onInventoryUpdate(IInventory inventory) {
		for(int i = 0; i < 9; i++){
			if(moduleInventory.getStackInSlot(i) == null){
				routers[i] = null;
				continue;
			}
		}		
	}

	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z,
			int tileX, int tileY, int tileZ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ForgeDirection getOrientation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ForgeDirection setOrientation(ForgeDirection dir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ForgeDirection rotateAround(ForgeDirection axis) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void doWork() {
		// TODO Auto-generated method stub
		
	}

}
