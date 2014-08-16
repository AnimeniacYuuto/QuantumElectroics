package yuuto.quantumelectronics.transport.managers;

import net.minecraftforge.common.util.ForgeDirection;
import yuuto.quantumelectronics.transport.TileGridTile;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class EnergySync extends EnergyRouter{

	public EnergySync(TileGridTile parrent, EnergyStorage energyStorage) {
		super(parrent, energyStorage);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceiveEnergy,
			boolean simulate) {
		if(from != ForgeDirection.UNKNOWN)
			return 0;
		return energyStorage.receiveEnergy(maxReceiveEnergy, simulate);
	}

	@Override
	public void doWork() {
		if(target == null)
			return;
		int max = this.energyStorage.getMaxExtract();
		if(max > this.energyStorage.getEnergyStored())
			max = this.energyStorage.getEnergyStored();
		this.energyStorage.extractEnergy(target.receiveEnergy(orientation.getOpposite(), max, false), false);
	}

	@Override
	public void onPreGridChange() {}

	@Override
	public void onPostGridChange() {}

}
