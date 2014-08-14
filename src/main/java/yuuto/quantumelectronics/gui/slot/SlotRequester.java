package yuuto.quantumelectronics.gui.slot;

import org.lwjgl.opengl.GL11;

import yuuto.quantumelectronics.ModItems;
import yuuto.quantumelectronics.gui.GuiRequester;
import yuuto.quantumelectronics.transport.routing.ItemProviderStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

public class SlotRequester{

	GuiRequester gui;
	ItemProviderStack stack;
	
	public SlotRequester(ItemProviderStack stack, GuiRequester gui){
		this.stack = stack;
		this.gui = gui;
	}
	public void onMouseClicked(){
		if(stack.getItem() == ModItems.FLUID_DUMMY){
			gui.getContainer().selectFluid(stack.getItemStack());
			return;
		}
		ItemStack toExtract = stack.getItemStack();
		toExtract.stackSize = stack.getMaxStackSize();
		ItemStack held = gui.getContainer().playerInv.getItemStack();
		if(held != null && !gui.getContainer().compareItems(held, toExtract)){
			gui.getContainer().insertItem();
		}else{
			gui.getContainer().extractItem(toExtract);
		}
	}
	public void draw(int x, int y, int mx, int my){
		if(stack.getItem() == ModItems.FLUID_DUMMY){
			FluidStack fluid = ((IFluidContainerItem)stack.getItem()).getFluid(stack.getItemStack());
			int amt = fluid.amount;
			int count = 0;
			if(amt >= 1000){
				amt/= 1000;
				count++;
				if(amt >= 1000){
					amt/= 1000;
					count++;
					if(amt >= 1000){
						amt/= 1000;
						count++;
						if(amt >= 1000){
							amt/= 1000;
							count++;
						}	
					}
				}
			}
			String t = amt+(count < 1 ? "mB": count == 1 ? "B" : count == 2 ? "KB" :
				count == 3 ? "MB" : "GB");
			if(fluid.getFluid().getBlock() != null){
				ItemStack block = new ItemStack(fluid.getFluid().getBlock());
				gui.drawItemStack(block, x, y, t);
			}
			return;
		}
		gui.drawItemStack(stack.getItemStack(), x, y, ""+stack.stackSize);
		
	}

}
