package yuuto.quantumelectronics.gui.container;

import yuuto.quantumelectronics.gui.container.base.ContainerGridMachine;
import yuuto.quantumelectronics.tile.TileGridFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerGridFurnace extends ContainerGridMachine<TileGridFurnace>{

	public ContainerGridFurnace(TileGridFurnace tile, EntityPlayer player) {
		super(tile, player);
	}

	@Override
	public int bindTile() {
		addSlotToContainer(new Slot(tile, 0, 56, 35));
		addSlotToContainer(new Slot(tile, 1, 116, 35));
		return 84;
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

}
