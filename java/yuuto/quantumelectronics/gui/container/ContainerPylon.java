package yuuto.quantumelectronics.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import yuuto.quantumelectronics.gui.container.base.ContainerGridMachine;
import yuuto.quantumelectronics.tile.TilePylon;

public class ContainerPylon extends ContainerGridMachine<TilePylon>{

	public ContainerPylon(TilePylon tile, EntityPlayer player) {
		super(tile, player);
	}

	@Override
	public int bindTile() {
		return 84;
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

}
