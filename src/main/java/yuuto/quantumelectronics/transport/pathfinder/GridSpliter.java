package yuuto.quantumelectronics.transport.pathfinder;

import java.util.ArrayList;
import java.util.List;

import scala.actors.threadpool.Arrays;

import yuuto.quantumelectronics.transport.IGridTile;

public class GridSpliter {

	IGridTile target;
	List<IGridTile> checked;
	List<IGridTile> results;
	List<IGridTile> ignore;
	
	public GridSpliter(IGridTile tile, IGridTile... ignore){
		this.target = tile;
		this.checked = new ArrayList<IGridTile>();
		this.results = new ArrayList<IGridTile>();
		if(ignore != null && ignore.length > 0){
			this.ignore = Arrays.asList(ignore);
		}else{
			this.ignore = new ArrayList<IGridTile>();
		}
	}
	/**
	 * Attempts to find a path from the given tile
	 * @param tile
	 * @return
	 */
	public boolean findNode(IGridTile tile){
		checked.add(tile);
		if(onSearch(tile))
			return false;
		for(IGridTile tile2 : tile.getConnections()){
			if(checked.contains(tile2))
				continue;
			if(ignore.contains(tile2))
				continue;
			if(findNode(tile2))
				return true;
		}
		return false;
	}
	/**
	 * Checks if this tile is the target tile
	 * @param tile
	 * @return
	 */
	protected boolean onSearch(IGridTile tile){
		if(tile == target){
			results.add(tile);
			return true;
		}
		return false;
	}
	public List<IGridTile> getResults(){
		return results;
	}
	public List<IGridTile> getChecked(){
		return checked;
	}
}
