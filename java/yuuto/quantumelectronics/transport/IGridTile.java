package yuuto.quantumelectronics.transport;

import java.util.List;

import yuuto.quantumelectronics.transport.grid.PylonGrid;

import cofh.api.energy.IEnergyHandler;

public interface IGridTile extends IEnergyHandler{
	boolean canConnect(IGridTile tile);
	List<IGridTile> getConnections();
	PositionInfo getCoords();
	
	void addConnection(IGridTile tile);
	void removeConnection(IGridTile tile);
	int setEnergy(int amount);
	
	PylonGrid getGrid();
	void setGrid(PylonGrid grid);
}
