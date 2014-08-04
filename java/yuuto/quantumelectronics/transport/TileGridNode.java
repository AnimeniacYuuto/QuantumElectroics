package yuuto.quantumelectronics.transport;

import yuuto.quantumelectronics.tile.TilePylon;

public abstract class TileGridNode extends TileGridTile{
	
	@Override
	public boolean canConnect(IGridTile tile) {
		if(tile instanceof TilePylon)
			return super.canConnect(tile);
		return false;
	}
}
