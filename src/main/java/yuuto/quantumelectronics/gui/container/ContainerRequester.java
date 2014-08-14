package yuuto.quantumelectronics.gui.container;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import yuuto.quantumelectronics.QuantumElectronics;
import yuuto.quantumelectronics.gui.GuiRequester;
import yuuto.quantumelectronics.gui.slot.SlotRequester;
import yuuto.quantumelectronics.handlers.packets.ItemPacket;
import yuuto.quantumelectronics.handlers.packets.RequesterInventoryPacket;
import yuuto.quantumelectronics.transport.managers.Requester;
import yuuto.quantumelectronics.transport.routing.ItemProviderStack;
import yuuto.quantumelectronics.transport.tile.TileRequester;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRequester extends Container{

	TileRequester tile;
	public InventoryPlayer playerInv;
	public IInventory fluidInventory;
	public Requester requester;
	
	List<ItemProviderStack> inventory;
	ItemStack selectedFluid;
	
	int firstPlayerSlot;
	boolean needsRefresh;
	GuiRequester gui;
	
	public ContainerRequester(TileRequester tile, EntityPlayer player){
		System.out.println("Created container");
		this.tile = tile;
		playerInv = player.inventory;
		fluidInventory = tile.fluidInventory();
		requester = tile.getInventory();
		requester.openInventory(this);
		bindFluidInventory();
		bindPlayerInventory(8,138);
		requester.doWork();
	}
	@Override
	public void onContainerClosed(EntityPlayer player){
		requester.closeInventory(this);
	}
	
	public void bindFluidInventory(){
		this.addSlotToContainer(new Slot(fluidInventory, 1, 174, 138));
		this.addSlotToContainer(new Slot(fluidInventory, 2, 174, 174));
	}
	public void bindPlayerInventory(int x, int y){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 9; j++){
				addSlotToContainer(new Slot(playerInv, i*9+j+9, x+18*j, y+18*i));
			}
		}
		for(int j = 0; j < 9; j++){
			addSlotToContainer(new Slot(playerInv, j, x+18*j, y+18*3+4));
		}	
	}
	
	public void setGui(GuiRequester gui){
		System.out.println("Set gui");
		this.gui = gui;
	}
	
	public void updateInventory(){
		if(!tile.getWorldObj().isRemote && playerInv.player instanceof EntityPlayerMP){
			inventory = requester.getInventory();
			(new RequesterInventoryPacket(inventory, this)).sendToPlayer((EntityPlayerMP)playerInv.player);
		}
	}
	public void updateInventoryClient(){
		this.inventory = requester.getInventory();
		gui.updateSlots();
	}
	public void receiveInventoryUpdate(List<ItemProviderStack> inv){
		this.inventory = requester.setInventory(inv);
		if(this.gui != null)
			this.gui.updateSlots();
	}
	public void extractItem(ItemStack stack){
		if(!tile.getWorldObj().isRemote){
			ItemStack held = playerInv.getItemStack();
			if(held == null){
				playerInv.setItemStack(requester.extractItem(stack));
				playerInv.markDirty();
			}else if(compareItems(stack, held)){
				stack.stackSize -= held.stackSize;
				if(stack.stackSize > 0){
					ItemStack extracted = requester.extractItem(stack);
					if(extracted != null){
						held.stackSize += extracted.stackSize;
						playerInv.markDirty();
					}
				}
			}
		}else{
			(new ItemPacket(0, stack, this)).sendToServer();
			ItemStack held = playerInv.getItemStack();
			if(held == null){
				playerInv.setItemStack(requester.extractItemClient(stack));
				playerInv.markDirty();
			}else if(compareItems(stack, held)){
				stack.stackSize -= held.stackSize;
				if(stack.stackSize > 0){
					ItemStack extracted = requester.extractItemClient(stack);
					if(extracted != null){
						held.stackSize += extracted.stackSize;
						playerInv.markDirty();
					}
				}
			}
			System.out.println("Extract Client");
		}
	}
	
	public void selectFluid(ItemStack stack){
		fluidInventory.setInventorySlotContents(0, stack);
		if(tile.getWorldObj().isRemote){
			System.out.println("Sent Client fluid packet");
			(new ItemPacket(1, stack, this)).sendToServer();
		}else{
			System.out.println("Sent Server fluid packet");
			(new ItemPacket(2, stack, this)).sendToPlayer((EntityPlayerMP)playerInv.player);
		}
	}
	public void receiveSelectedFluid(ItemStack stack){
		System.out.println("Receiverd Server fluid packet");
		fluidInventory.setInventorySlotContents(0, stack);
	}
	
	public List<ItemProviderStack> getNetInventory(String search){
		if(search == null || search == "")
			return this.inventory;
		ArrayList<ItemProviderStack> results = new ArrayList<ItemProviderStack>();
		for(ItemProviderStack stack : inventory){
			if(stack.getDisplayName().toLowerCase().contains(search))
				results.add(stack);
		}
		return results;
	}
	
	public void insertItem(){
		ItemStack held = playerInv.getItemStack();
		if(held == null)
			return;
		ItemStack inserted = this.requester.insertItem(held.copy());
		if(inserted != null && inserted.stackSize > 0){
			held.stackSize -= inserted.stackSize;
			if(held.stackSize < 1)
				playerInv.setItemStack(null);
		}
		if(tile.getWorldObj().isRemote){
			(new ItemPacket(3, held, this)).sendToServer();
		}
	}
	public void insertItem(ItemStack stack){
		if(stack == null)
			return;
		ItemStack inserted = this.requester.insertItem(stack.copy());
		if(inserted != null & inserted.stackSize > 0){
			stack.stackSize -= inserted.stackSize;
			if(inserted.stackSize < 1)
				playerInv.setItemStack(null);
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}
	public static boolean compareItems(ItemStack stack1, ItemStack stack2){
		if(stack1.getItem() != stack2.getItem())
			return false;
		if(stack1.getItemDamage() != stack2.getItemDamage())
			return false;
		return ItemStack.areItemStackTagsEqual(stack1, stack2);
	}
	

}
