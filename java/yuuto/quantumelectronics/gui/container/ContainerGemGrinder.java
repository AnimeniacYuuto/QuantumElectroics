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
		bindPlayerInventory(100, 8);
	}
	public void bindTile(){
	}
	public void bindPlayerInventory(int x, int y){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 9; j++){
				addSlotToContainer(new Slot(tile, i*9+j+9, x+18*j, y+18*i));
			}
		}
		for(int j = 0; j < 9; j++){
			addSlotToContainer(new Slot(tile, j, x+18*j, y+18*4));
		}	
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		// TODO Auto-generated method stub
		return false;
	}

}
