package yuuto.quantumelectronics.transport;

import yuuto.quantumelectronics.transport.grid.PylonGrid;

public class TilePylonNode extends TilePylon{
	PylonGrid grid = new PylonGrid(this);
	
	@Override
	public void updateGrid(){
		grid.updateGrid();
		super.updateGrid();
	}
}
