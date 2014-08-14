package yuuto.quantumelectronics.handlers;

import yuuto.quantumelectronics.handlers.packets.RequesterInventoryPacket;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class RequesterInventoryPacketHandler implements IMessageHandler<RequesterInventoryPacket, IMessage>{

	@Override
	public IMessage onMessage(RequesterInventoryPacket message, MessageContext ctx) {
		message.execute();
		return null;
	}

}
