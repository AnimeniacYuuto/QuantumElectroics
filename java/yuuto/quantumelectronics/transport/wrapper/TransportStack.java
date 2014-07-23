package yuuto.quantumelectronics.transport.wrapper;

import net.minecraft.item.ItemStack;

public class TransportStack {
	public final ItemStack stack;
	public final String type;
	
	public TransportStack(ItemStack stack, String type){
		this.stack = stack;
		this.type = type;
	}
	
	
	public TransportStack copy(){
		return new TransportStack(stack.copy(), type);
	}
}
