package yuuto.quantumelectronics.gui.container;

import yuuto.quantumelectronics.gui.container.base.ContainerGridMachine;
import yuuto.quantumelectronics.tile.TileGridGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerGridGenerator extends ContainerGridMachine<TileGridGenerator>{

	public ContainerGridGenerator(TileGridGenerator tile, EntityPlayer player) {
		super(tile, player);
	}

	@Override
	public int bindTile() {
		addSlotToContainer(new Slot(tile, 0, 80, 35));
		return 84;
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

}
