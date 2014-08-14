package yuuto.quantumelectronics.handlers.packets;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

import yuuto.quantumelectronics.gui.GuiRequester;
import yuuto.quantumelectronics.gui.container.ContainerRequester;
import yuuto.quantumelectronics.transport.routing.ItemProviderStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class RequesterInventoryPacket extends NbtPacket{
	
	List<ItemProviderStack> inv;
	EntityPlayer player;
	ContainerRequester container;
	
	public RequesterInventoryPacket(){super();};
	public RequesterInventoryPacket(List<ItemProviderStack> inv, ContainerRequester container){
		this.inv = inv;
		this.container = container;
		player = container.playerInv.player;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		//System.out.println("reading packet nbt");
		inv = new ArrayList<ItemProviderStack>();
		NBTTagList invList = (NBTTagList)nbt.getTag("Inventory");
		if(invList != null && invList.tagCount() > 0){
			for(int i = 0; i < invList.tagCount(); i++){
				inv.add(ItemProviderStack.loadFromNBT(invList.getCompoundTagAt(i)));
			}
		}
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
		//System.out.println("writing packet nbt");
		if(inv.size() > 0){
			NBTTagList invList = new NBTTagList();
			for(int i = 0; i < inv.size(); i++){
				invList.appendTag(inv.get(i).writeToNBT(new NBTTagCompound()));
			}
			nbt.setTag("Inventory", invList);
		}
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
		if (player != null && player.isClientWorld()) {
            Gui gui = Minecraft.getMinecraft().currentScreen;
            if (gui instanceof GuiRequester) {
                ContainerRequester container = (ContainerRequester) ((GuiRequester) gui).inventorySlots;
                container.receiveInventoryUpdate(inv);
            }
        }
	}

	

}
