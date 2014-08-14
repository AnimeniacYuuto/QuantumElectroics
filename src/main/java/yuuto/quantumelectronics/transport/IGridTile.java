package yuuto.quantumelectronics.transport;

import java.util.List;

import yuuto.quantumelectronics.transport.grid.PylonGrid;

import cofh.api.energy.IEnergyHandler;

public interface IGridTile extends IEnergyHandler{
	/**
	 * Can this grid tile connect to the given grid tile
	 * @param tile
	 * @return whether or not this grid tile can connect to the given grid tile
	 */
	boolean canConnect(IGridTile tile);
	/**
	 * Gets all tiles connected to the this tile
	 */
	List<IGridTile> getConnections();
	/**
	 * @return the position info for this tile
	 */
	PositionInfo getCoords();
	
	/**
	 * Attempts to connect to the given tile
	 * @param tile
	 */
	void addConnection(IGridTile tile);
	/**
	 * Attempts to remove the connection to the given tile
	 * @param tile
	 */
	void removeConnection(IGridTile tile);
	/**
	 * sets the energy of the tile, used for server-client sync
	 * @param amount
	 * @return the amount of energy in the tile
	 */
	int setEnergy(int amount);
	
	/**
	 * Gets the grid for the tile
	 */
	PylonGrid getGrid();
	/**
	 * Sets the grid for the tile
	 * @param grid
	 */
	void setGrid(PylonGrid grid);
}
