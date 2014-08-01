package yuuto.quantumelectronics.tile.base;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileMachine extends TileEntity implements IMachine{
	protected ForgeDirection orientation = ForgeDirection.getOrientation(2);
	protected boolean isActive;
	@Override
	public ForgeDirection getOrientation(){
		return orientation;
	}
	@Override
	public ForgeDirection setOrientation(ForgeDirection dir){
		orientation = dir;
		return orientation;
	}
	@Override
	public ForgeDirection rotateAround(ForgeDirection axis){
		orientation = orientation.getRotation(axis);
		return orientation;
	}
	@Override
	public boolean isActive(){
		return isActive;
	}

}
