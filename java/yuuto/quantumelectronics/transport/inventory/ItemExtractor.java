package yuuto.quantumelectronics.transport.inventory;

import yuuto.quantumelectronics.transport.handler.ITransportHandler;
import yuuto.quantumelectronics.transport.wrapper.TransportStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemExtractor {

	public static void extractItems(IInventory target, 
			ITransportHandler handler, 
			ForgeDirection facing){
		System.out.println("pullingItems");
		if(handler.isFull(facing))
			return;
		
		for(int i = 0; i < target.getSizeInventory(); i++){
			if(target.getStackInSlot(i) == null)
				continue;
			if(target instanceof ISidedInventory){
				if(((ISidedInventory)target).canExtractItem(
						i, 
						target.getStackInSlot(i), 
						facing.getOpposite().ordinal()))
					continue;
			}
			TransportStack stack = new TransportStack(target.getStackInSlot(i).copy(), "Item");
			if(handler.receiveStack(facing, stack, true) == null)
				continue;
			target.decrStackSize(i, handler.receiveStack(facing, stack, false).stack.stackSize);
			target.markDirty();
			break;
		}
	}
}
