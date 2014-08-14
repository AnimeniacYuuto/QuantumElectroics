package yuuto.quantumelectronics.handlers;


import yuuto.quantumelectronics.handlers.packets.ItemPacket;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ItemPacketHandler implements IMessageHandler<ItemPacket, IMessage>{

	@Override
	public IMessage onMessage(ItemPacket message, MessageContext ctx) {
		message.execute();
		return null;
	}

}
