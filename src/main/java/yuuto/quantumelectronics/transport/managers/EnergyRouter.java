package yuuto.quantumelectronics.transport.managers;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;
import yuuto.quantumelectronics.transport.TileGridTile;
import yuuto.quantumelectronics.transport.routing.IEnergyRouter;

public abstract class EnergyRouter implements IEnergyRouter{

	protected IEnergyHandler target;
	protected ForgeDirection orientation;
	protected ForgeDirection sneaky;
	protected TileGridTile parrent;
	protected EnergyStorage energyStorage;
	protected int channel;
	
	public EnergyRouter(TileGridTile parrent, EnergyStorage energyStorage){
		this.parrent = parrent;
		this.energyStorage = energyStorage;
	}
	
	@Override
	public void setSneaky(ForgeDirection dir) {
		sneaky = dir;
	}

	@Override
	public void setOrientation(ForgeDirection dir){
		orientation = dir;
	}
	@Override
	public void setChannel(int c){
		channel = c;
	}

	@Override
	public void setTarget(IEnergyHandler t) {
		target = t;
	}

}
