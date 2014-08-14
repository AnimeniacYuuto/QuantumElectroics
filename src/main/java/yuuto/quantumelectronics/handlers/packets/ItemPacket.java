package yuuto.quantumelectronics.handlers.packets;

import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import yuuto.quantumelectronics.gui.GuiRequester;
import yuuto.quantumelectronics.gui.container.ContainerRequester;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class ItemPacket extends NbtPacket{

	int type;
	ItemStack stack;
	EntityPlayer player;
	ContainerRequester container;
	
	public ItemPacket(){super();};
	public ItemPacket(int type, ItemStack stack, ContainerRequester container){
		this.type = type;
		this.stack = stack;
		this.container = container;
		player = container.playerInv.player;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		//System.out.println("Read NBT");
		type = nbt.getInteger("Type");
		stack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Stack"));
		if(nbt.getBoolean("hasPlayer")){
			World w = getWorld(nbt.getInteger("World ID"));
			player = w.getPlayerEntityByName(nbt.getString("Player Name"));
		}
		if(player != null){
			if(player.openContainer instanceof ContainerRequester){
				container = (ContainerRequester)player.openContainer;
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Type", type);
		nbt.setTag("Stack", stack.writeToNBT(new NBTTagCompound()));
		if(player == null){
			nbt.setBoolean("hasPlayer", false);
		}else{
			nbt.setBoolean("hasPlayer", true);
			nbt.setInteger("World ID", player.worldObj.provider.dimensionId);
			nbt.setString("Player Name", player.getCommandSenderName());
		}
	}

	@Override
	public void execute() {
		System.out.println("Execute "+type);
		if(type == 0){
			if(container != null)
				container.extractItem(stack);
		}
		if(type == 1){
			if(container != null)
				container.selectFluid(stack);
		}
		if(type == 2){
			if(player != null && player.isClientWorld()){
				Gui gui = Minecraft.getMinecraft().currentScreen;
	            if (gui instanceof GuiRequester) {
	                ContainerRequester container = (ContainerRequester) ((GuiRequester) gui).inventorySlots;
	                container.receiveSelectedFluid(stack);
	            }
			}
		}
		if(type == 3){
			if(container != null)
				container.insertItem();
		}
		
	}

}
