package yuuto.quantumelectronics.handlers.packets;

import yuuto.quantumelectronics.handlers.PacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;

public abstract class NbtPacket implements IMessage{

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound nbt = ByteBufUtils.readTag(buf);
		if(nbt != null)
			readFromNBT(nbt);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		ByteBufUtils.writeTag(buf, nbt);
	}
	
	public World getWorld(int dim){
		World world = DimensionManager.getWorld(dim);
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
			return world != null ? world : net.minecraft.client.Minecraft.getMinecraft().theWorld;
		}
		return world;
	}
	public abstract void readFromNBT(NBTTagCompound nbt);
	public abstract void writeToNBT(NBTTagCompound nbt);
	public abstract void execute();
	
	public void sendToServer(){
		PacketHandler.sendToServer(this);
	}
	public void sendToPlayer(EntityPlayerMP player){
		PacketHandler.sendToPlayer(this, player);
	}
	public void sendToAll(){
		PacketHandler.sendToAll(this);
	}

}
