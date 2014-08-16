package yuuto.quantumelectronics.transport.managers;

import java.util.List;

import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;
import yuuto.quantumelectronics.transport.IGridTile;
import yuuto.quantumelectronics.transport.TileGridTile;
import yuuto.quantumelectronics.transport.routing.IEnergyRouter;

public class EnergyAcceptor extends EnergyRouter implements IEnergyRouter{

	public EnergyAcceptor(TileGridTile parrent, EnergyStorage energyStorage) {
		super(parrent, energyStorage);
	}

	@Override
	public void doWork() {
		List<IGridTile> connections = parrent.getConnections();
		if(connections.size() < 1)
			return;
		if(energyStorage.getEnergyStored() < connections.size())
			return;
		int maxOut = (int)Math.floor((float)energyStorage.getEnergyStored()/(float)connections.size());
		if(maxOut > energyStorage.getMaxExtract()){
			maxOut = energyStorage.getMaxExtract();
		}
		for(int i = 0; i < connections.size(); i++){
			energyStorage.extractEnergy(connections.get(i).receiveEnergy(ForgeDirection.UNKNOWN, maxOut, false), false);
		}
	}

	@Override
	public void onPreGridChange(){}

	@Override
	public void onPostGridChange() {}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceiveEnergy,
			boolean simulate) {
		if(from != this.orientation)
			return 0;
		return energyStorage.receiveEnergy(maxReceiveEnergy, simulate);
	}

}
