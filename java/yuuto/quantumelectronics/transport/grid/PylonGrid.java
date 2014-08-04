package yuuto.quantumelectronics.transport.grid;

import java.util.ArrayList;
import java.util.List;

import yuuto.quantumelectronics.transport.IGridTile;
import yuuto.quantumelectronics.transport.pathfinder.GridSpliter;

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
		for(IGridTile tile1 : tile.getConnections()){
			if(tile1.getGrid() != this || !this.connections.contains(tile1))
				continue;
			for(IGridTile tile2 : tile.getConnections()){
				if(tile2.getGrid() != this || !this.connections.contains(tile2))
					continue;
				GridSpliter pathfinder = new GridSpliter(tile1, tile);
				pathfinder.findNode(tile2);
				if(pathfinder.getResults().size() < 1){
					this.connections.removeAll(pathfinder.getChecked());
					PylonGrid grid = new PylonGrid(pathfinder.getChecked());
				}
			}
		}
	}
	public void removeConnection(IGridTile tile){
		if(connections.contains(tile))
			connections.remove(tile);
	}
	public List<IGridTile> getConnections(){
		return (List<IGridTile>)connections.clone();
	}
}
