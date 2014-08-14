package yuuto.quantumelectronics.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import yuuto.quantumelectronics.handlers.packets.ItemPacket;
import yuuto.quantumelectronics.handlers.packets.RequesterInventoryPacket;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
	public static SimpleNetworkWrapper wrapper = NetworkRegistry.INSTANCE.newSimpleChannel("quantumelectronics");
	
	public static void registerMessages() {
		wrapper.registerMessage(RequesterInventoryPacketHandler.class, RequesterInventoryPacket.class, 0, Side.CLIENT);
		wrapper.registerMessage(RequesterInventoryPacketHandler.class, RequesterInventoryPacket.class, 0, Side.SERVER);
		
		wrapper.registerMessage(ItemPacketHandler.class, ItemPacket.class, 1, Side.CLIENT);
		wrapper.registerMessage(ItemPacketHandler.class, ItemPacket.class, 1, Side.SERVER);
	}
	
	public static void sendToServer(IMessage packet){
		wrapper.sendToServer(packet);
	}
	public static void sendToPlayer(IMessage packet, EntityPlayerMP player){
		wrapper.sendTo(packet, player);
	}
	public static void sendToAll(IMessage packet){
		wrapper.sendToAll(packet);
	}
}
