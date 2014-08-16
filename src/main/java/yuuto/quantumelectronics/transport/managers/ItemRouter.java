package yuuto.quantumelectronics.transport.managers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import yuuto.quantumelectronics.transport.TileGridTile;
import yuuto.quantumelectronics.transport.filter.ItemFilter;
import yuuto.quantumelectronics.transport.routing.IItemRouter;

public abstract class ItemRouter implements IItemRouter{
	
	protected ItemFilter filter;
	protected IInventory target;
	protected ForgeDirection orientation;
	protected ForgeDirection sneaky;
	protected TileGridTile parrent;
	protected int channel;
	
	public ItemRouter(TileGridTile parrent){
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
	public void setTarget(IInventory t){
		target = t;
	}
	@Override
	public void setFilter(ItemFilter f){
		filter = f;
	}
	protected int[] getSlots(){
		if(target == null)
			return null;
		if(target instanceof ISidedInventory)
			return ((ISidedInventory)target).getAccessibleSlotsFromSide(orientation.ordinal());
		int[] ret = new int[target.getSizeInventory()];
		for(int i = 0; i < ret.length; i++){
			ret[i] = i;
		}
		return ret;
	}
	
	protected static boolean compareItems(ItemStack stack1, ItemStack stack2){
		if(stack1.getItem() != stack2.getItem())
			return false;
		if(stack1.getItemDamage() != stack2.getItemDamage())
			return false;
		return ItemStack.areItemStackTagsEqual(stack1, stack2);
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
