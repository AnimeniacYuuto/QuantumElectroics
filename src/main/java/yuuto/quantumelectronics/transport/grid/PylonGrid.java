package yuuto.quantumelectronics.transport.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yuuto.quantumelectronics.transport.IGridTile;
import yuuto.quantumelectronics.transport.pathfinder.GridSpliter;
import yuuto.quantumelectronics.transport.routing.RoutingChannel;

public class PylonGrid {
	ArrayList<IGridTile> connections = new ArrayList<IGridTile>();
	Map<Integer, RoutingChannel> channels = new HashMap<Integer, RoutingChannel>();
	
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
	/**
	 * Merges the given grid with this grid
	 * @param grid
	 */
	public void mergeWith(PylonGrid grid){
		connections.addAll(grid.getConnections());
		for(IGridTile tile : grid.getConnections()){
			tile.setGrid(this);
		}
	}
	/**
	 * splits the grid around the given tile if necessary
	 * @param tile
	 */
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
	/**
	 * Removes an edge tile with out making a split check
	 * @param tile
	 */
	public void removeConnection(IGridTile tile){
		if(connections.contains(tile))
			connections.remove(tile);
	}
	/**
	 * gets a list of all connections
	 * @return
	 */
	public List<IGridTile> getConnections(){
		return (List<IGridTile>)connections.clone();
	}
	public RoutingChannel getChannel(int channel){
		if(channels.containsKey(channel))
			return channels.get(channel);
		channels.put(channel, new RoutingChannel());
		return channels.get(channel);
	}
}
