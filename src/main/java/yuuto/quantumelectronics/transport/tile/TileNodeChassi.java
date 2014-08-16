package yuuto.quantumelectronics.transport.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;
import yuuto.quantumelectronics.tile.base.IMachine;
import yuuto.quantumelectronics.transport.ITransportNode;
import yuuto.quantumelectronics.transport.TileGridNode;
import yuuto.quantumelectronics.transport.grid.PylonGrid;
import yuuto.quantumelectronics.transport.module.ItemModule;
import yuuto.quantumelectronics.transport.routing.IEnergyRouter;
import yuuto.quantumelectronics.transport.routing.IFluidRouter;
import yuuto.quantumelectronics.transport.routing.IItemRouter;
import yuuto.quantumelectronics.transport.routing.IRouter;
import static yuuto.quantumelectronics.ModItems.MODULES;

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
			if(moduleInventory.getStackInSlot(i) != null &&
					moduleInventory.getStackInSlot(i).getItem() == MODULES){
				if(routers[i] != null)
					routers[i].onPreGridChange();
				routers[i] = MODULES.getRouter(moduleInventory.getStackInSlot(i), this);
				routers[i].setOrientation(orientation.getOpposite());
				MODULES.updateRouter(moduleInventory.getStackInSlot(i), routers[i]);
				continue;
			}
			if(routers[i] != null)
				routers[i].onPreGridChange();
			routers[i] = null;
		}		
	}

	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z,
			int tileX, int tileY, int tileZ) {
		TileEntity tile = worldObj.getTileEntity(
				this.xCoord+orientation.offsetX, 
				this.yCoord+orientation.offsetY, 
				this.zCoord+orientation.offsetZ);
		target = tile;
		IInventory invTarget = tile instanceof IInventory ? (IInventory)tile : null;
		IFluidHandler tankTarget = tile instanceof IFluidHandler ? (IFluidHandler)tile : null;
		IEnergyHandler energyTaget = tile instanceof IEnergyHandler ? (IEnergyHandler)tile : null;
		for(int i = 0; i < 9; i++){
			if(routers[i] == null)
				continue;
			if(routers[i] instanceof IItemRouter)
				((IItemRouter)routers[i]).setTarget(invTarget);
			if(routers[i] instanceof IFluidRouter)
				((IFluidRouter)routers[i]).setTarget(tankTarget);
			if(routers[i] instanceof IEnergyRouter)
				((IEnergyRouter)routers[i]).setTarget(energyTaget);
		}
	}
	
	@Override
	public void setGrid(PylonGrid grid) {
		for(IRouter r : routers){
			if(r == null)
				continue;
			r.onPreGridChange();
		}
		super.setGrid(grid);
		for(IRouter r : routers){
			if(r == null)
				continue;
			r.onPostGridChange();
		}
	}

	@Override
	public ForgeDirection getOrientation() {
		return orientation;
	}
	@Override
	public ForgeDirection setOrientation(ForgeDirection dir) {
		for(IRouter r : routers){
			if(r == null)
				continue;
			r.setOrientation(dir);
		}
		orientation = dir.getOpposite();
		this.onNeighborChange(worldObj, xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
		return orientation;
	}
	@Override
	public ForgeDirection rotateAround(ForgeDirection axis) {
		orientation = orientation.getRotation(axis);
		for(IRouter r : routers){
			if(r == null)
				continue;
			r.setOrientation(orientation.getOpposite());
		}
		this.onNeighborChange(worldObj, xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
		return orientation;
	}
	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public void doWork() {
		timer--;
		if(timer < 1){
			timer = 20;
			for(IRouter r : routers){
				if(r == null)
					continue;
				r.doWork();
			}
		}
	}
	
	public InventoryFilter getModules(){
		return moduleInventory;
	}
	
	public EnergyStorage getEnergyStorage(){
		return storage;
	}

}
