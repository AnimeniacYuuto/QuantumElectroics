package yuuto.quantumelectronics.tile;

import net.minecraft.tileentity.TileEntity;

public class TileGemGrinderCrank extends TileEntity{

	int timer = 0;
	
	
	@Override
	public void updateEntity(){
		if(timer > 0)
			timer --;
	}
	public void onActivated(){

		if(timer > 0)
			return;
		TileEntity tile = worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
		if(tile == null || !(tile instanceof TileGemGrinder))
			return;
		((TileGemGrinder)tile).grind();
	}
}
