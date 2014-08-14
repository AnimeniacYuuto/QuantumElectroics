package yuuto.quantumelectronics.gui.slot;

import yuuto.quantumelectronics.ModItems;
import yuuto.quantumelectronics.gui.GuiRequester;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class SlotSelectedFluid {
	IInventory inventory;
	int slot;
	int x;
	int y;
	GuiRequester gui;
	
	public SlotSelectedFluid(IInventory inv, int slot, int x, int y, GuiRequester gui){
		inventory = inv;
		this.slot = slot;
		this.x = x;
		this.y = y;
		this.gui = gui;
	}
	public void draw(){
		ItemStack stack = inventory.getStackInSlot(slot);
		if(stack == null || !(stack.getItem() == ModItems.FLUID_DUMMY))
			return;
		FluidStack fluid = ModItems.FLUID_DUMMY.getFluid(stack);
		if(fluid.getFluid().getBlock() != null){
			gui.drawItemStack(new ItemStack(fluid.getFluid().getBlock()), x+gui.guiLeft(), y+gui.guiTop(), "");
		}else{
			
		}
		
	}
}
