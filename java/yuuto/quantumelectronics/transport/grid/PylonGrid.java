package yuuto.quantumelectronics.transport.grid;

import java.util.ArrayList;
import java.util.List;

import yuuto.quantumelectronics.transport.TilePylon;
import yuuto.quantumelectronics.transport.TilePylonNode;

public class PylonGrid {
	List<GridNode> connectedNodes = new ArrayList<GridNode>();
	TilePylon origin;
	
	public PylonGrid(TilePylon origin){
		this.origin = origin;
	}
	public void updateGrid(){
		connectedNodes.clear();
		List<TilePylon> checked = new ArrayList<TilePylon>();
		checkForNode(origin, checked, 0);
	}
	private void checkForNode(TilePylon pylon, List<TilePylon> checked, int depth){
		checked.add(pylon);
		if(depth > 0){
			if(pylon instanceof TilePylonNode)
				connectedNodes.add(new GridNode((TilePylonNode)pylon, depth));
		}
		List<TilePylon> checkNext = pylon.getConnections();
		for(int i = 0; i < checkNext.size(); i++){
			checkForNode(pylon, checked, depth+1);
		}
	}
}
