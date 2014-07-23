package yuuto.quantumelectronics.transport.handler;

import yuuto.quantumelectronics.transport.wrapper.TransportStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public interface ITransportHandler extends ITransportConnection{
	TransportStack receiveStack(ForgeDirection from, TransportStack stack, boolean simulate);
	boolean isEmpty(ForgeDirection from);
	boolean isFull(ForgeDirection from);
}
