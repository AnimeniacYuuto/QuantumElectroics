package yuuto.quantumelectronics.transport.grid;

import java.util.ArrayList;
import java.util.List;

import yuuto.quantumelectronics.transport.IGridTile;

public class PylonGrid {
	ArrayList<IGridTile> connections = new ArrayList<IGridTile>();
	
	
	public PylonGrid(){}
	public PylonGrid(IGridTile tile){
		connections.add(tile);
	}
	public PylonGrid(List<IGridTile> tileList){
		connections.addAll(tileList);
		for(int i = 0; i < connections.size(); i++){
			connections.get(i).setGrid(this);
		}
	}
	
	public void mergeWith(PylonGrid grid){
		connections.addAll(grid.getConnections());
		for(IGridTile tile : grid.getConnections()){
			tile.setGrid(this);
		}
	}
	public void split(IGridTile tile){
	}
	public List<IGridTile> getConnections(){
		return (List<IGridTile>)connections.clone();
	}
}
