package yuuto.quantumelectronics.transport.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import yuuto.quantumelectronics.transport.handler.ITransportHandler;
import yuuto.quantumelectronics.transport.wrapper.TransportStack;

public class ItemReceiver {
	
	public static TransportStack receiveStack(TransportStack stack, 
			IInventory target, 
			ForgeDirection facing){
		ItemStack ret = stack.stack.copy();
		for(int i = 0; i < target.getSizeInventory(); i++){
			if(!target.isItemValidForSlot(i, stack.stack))
				continue;
			if(target instanceof ISidedInventory){
				if(!((ISidedInventory)target).canInsertItem(i, ret, 
						facing.getOpposite().ordinal()))
					continue;
			}
			if(target.getStackInSlot(i) == null){
				target.setInventorySlotContents(i, ret.copy());
				ret.stackSize -= target.getStackInSlot(i).stackSize;
			}else{
				int size = target.getStackInSlot(i).stackSize;
				ItemStack newStack = target.getStackInSlot(i).copy();
				newStack.stackSize+=ret.stackSize;
				target.setInventorySlotContents(i, newStack);
				ret.stackSize -= (target.getStackInSlot(i).stackSize-size);
			}
			if(ret.stackSize < 1){
				return stack;
			}
		}
		TransportStack retStack = stack.copy();
		retStack.stack.stackSize = stack.stack.stackSize-ret.stackSize;
		return retStack;
	}
}
