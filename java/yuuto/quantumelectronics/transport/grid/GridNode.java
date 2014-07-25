package yuuto.quantumelectronics.transport.grid;

import yuuto.quantumelectronics.transport.TilePylonNode;

public class GridNode {
	TilePylonNode node;
	int distance;
	
	public GridNode(TilePylonNode node,	int distance){
		this.node = node;
		this.distance = distance;
	}
}
