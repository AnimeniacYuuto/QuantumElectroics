package yuuto.quantumelectronics.transport;

import yuuto.quantumelectronics.transport.grid.PylonGrid;

public class TileEnergyPylonNode extends TileEnergyPylon{
	PylonGrid grid = new PylonGrid(this);
	
	public TileEnergyPylonNode(int energyMax) {
		super(energyMax);
	}
	
	@Override
	public void updateGrid(){
		grid.updateGrid();
		super.updateGrid();
	}

}
