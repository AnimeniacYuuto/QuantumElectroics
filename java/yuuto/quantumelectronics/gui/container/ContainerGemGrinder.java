package yuuto.quantumelectronics.gui.container;

import yuuto.quantumelectronics.tile.TileGemGrinder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerGemGrinder extends Container{

	TileGemGrinder tile;
	InventoryPlayer playerInv;
	public ContainerGemGrinder(TileGemGrinder tile, EntityPlayer player){
		this.tile = tile;
		playerInv = player.inventory;
		bindTile();
		bindPlayerInventory(8, 84);
	}
	public void bindTile(){
		addSlotToContainer(new Slot(tile, 0, 56, 35));
		addSlotToContainer(new Slot(tile, 1, 116, 35));
	}
	public void bindPlayerInventory(int x, int y){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 9; j++){
				addSlotToContainer(new Slot(playerInv, i*9+j+9, x+18*j, y+18*i));
			}
		}
		for(int j = 0; j < 9; j++){
			addSlotToContainer(new Slot(playerInv, j, x+18*j, y+18*3+4));
		}	
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}
