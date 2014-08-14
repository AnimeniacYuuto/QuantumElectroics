package yuuto.quantumelectronics.tile;

import java.util.ArrayList;

import yuuto.quantumelectronics.transport.IGridTile;
import yuuto.quantumelectronics.transport.TileGridTile;

import cofh.api.energy.EnergyStorage;

import net.minecraftforge.common.util.ForgeDirection;

public class TilePylon extends TileGridTile{
	
	public TilePylon(){
		super();
		this.storage = new EnergyStorage(8000000, 8000000, 8000000);
	}
	public TilePylon(int storage, int range){
		super();
		this.range = range;
		this.storage = new EnergyStorage(storage);
	}
	@Override
	public void doWork() {
		propogateEnergy();		
	}
	public void propogateEnergy(){
		if(connections.size() < 1)
			return;
		if(storage.getEnergyStored() < connections.size())
			return;
		int maxOut = (int)Math.floor((float)storage.getEnergyStored()/(float)connections.size());
		if(maxOut > storage.getMaxExtract()){
			maxOut = storage.getMaxExtract();
		}
		for(int i = 0; i < connections.size(); i++){
			if(connections.get(i).getEnergyStored(ForgeDirection.UNKNOWN) >= this.storage.getEnergyStored())
				continue;
			storage.extractEnergy(connections.get(i).receiveEnergy(ForgeDirection.UNKNOWN, maxOut, false), false);
		}
	}
	
}
