package yuuto.quantumelectronics.transport.managers;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import yuuto.quantumelectronics.transport.TileGridTile;
import yuuto.quantumelectronics.transport.filter.FluidFilter;
import yuuto.quantumelectronics.transport.filter.ItemFilter;
import yuuto.quantumelectronics.transport.routing.IFluidRouter;

public abstract class FluidRouter implements IFluidRouter{

	protected FluidFilter filter;
	protected IFluidHandler target;
	protected ForgeDirection orientation;
	protected ForgeDirection sneaky;
	protected TileGridTile parrent;
	protected int channel;
	
	public FluidRouter(TileGridTile parrent){
		this.parrent = parrent;
	}
	@Override
	public void setOrientation(ForgeDirection dir){
		orientation = dir;
	}
	@Override
	public void setChannel(int c){
		channel = c;
	}
	@Override
	public void setTarget(IFluidHandler t){
		target = t;
	}
	@Override
	public void setFilter(FluidFilter f){
		filter = f;
	}
	@Override 
	public void setSneaky(ForgeDirection dir){
		sneaky = dir;
	}
	public ForgeDirection getSide(){
		if(sneaky == null || sneaky == ForgeDirection.UNKNOWN)
			return orientation;
		return sneaky;
	}

}
