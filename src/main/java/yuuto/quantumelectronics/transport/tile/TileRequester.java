package yuuto.quantumelectronics.transport.tile;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import yuuto.quantumelectronics.ModItems;
import yuuto.quantumelectronics.transport.TileGridNode;
import yuuto.quantumelectronics.transport.managers.Requester;

public class TileRequester extends TileGridNode implements IInventoryParrent{

	Requester requester = new Requester(this);
	InventoryFilter customInventory = new InventoryFilter(3, this);
	int timer = 20;
	@Override
	public void doWork() {
		timer--;
		if(timer < 1){
			timer = 20;
			requester.doWork();
			if(!worldObj.isRemote)
				fillContainers();
		}
	}
	protected void fillContainers(){
		if(customInventory.getStackInSlot(0) == null || 
				customInventory.getStackInSlot(1) == null)
			return;
		if(customInventory.getStackInSlot(0).getItem() != ModItems.FLUID_DUMMY)
			return;
		if(customInventory.getStackInSlot(2) != null)
			return;
		if(customInventory.getStackInSlot(1).getItem() == ModItems.FLUID_DUMMY){
			FluidStack fluidType = ModItems.FLUID_DUMMY.getFluid(customInventory.getStackInSlot(0));
			ItemStack cStack = customInventory.getStackInSlot(1).copy();
			cStack.stackSize = 1;
			IFluidContainerItem container = (IFluidContainerItem)cStack.getItem();
			FluidStack fStack = container.getFluid(cStack);
			fluidType.amount = 1000;
			if(fluidType.amount > container.getCapacity(cStack)){
				fluidType.amount = container.getCapacity(cStack);
			}
			if(fStack == null){
				FluidStack drain = requester.drainFluid(fluidType.copy());
				if(drain != null){
					System.out.println("Filling Containers");
					container.fill(cStack, fluidType, true);
				}
			}else if(fStack.isFluidEqual(fluidType)){
				fluidType.amount -= fStack.amount;
				fluidType = requester.drainFluid(fluidType.copy());
				if(fluidType != null){
					container.fill(cStack, fluidType, true);
				}
			}
			if(container.getFluid(cStack) != null && container.getFluid(cStack).amount >=
					container.getCapacity(cStack)){
				customInventory.setInventorySlotContents(2, cStack);
				customInventory.decrStackSize(1, 1);
			}
			return;
		}
		if(FluidContainerRegistry.isEmptyContainer(customInventory.getStackInSlot(1))){
			ItemStack cStack = customInventory.getStackInSlot(1).copy();
			FluidStack fluidType = ModItems.FLUID_DUMMY.getFluid(customInventory.getStackInSlot(0));
			fluidType.amount = FluidContainerRegistry.getContainerCapacity(cStack);
			FluidStack drain = requester.drainFluid(fluidType.copy());
			ItemStack result = null;
			if(drain != null){
				result = FluidContainerRegistry.fillFluidContainer(drain, cStack);
			}
			if(result != null){
				System.out.println("Filling Containers");
				customInventory.setInventorySlotContents(2, result);
				customInventory.decrStackSize(1, 1);
			}
		}
	}
	public Requester getInventory(){
		return requester;
	}
	public IInventory fluidInventory(){
		return customInventory;
	}
	@Override
	public void onInventoryUpdate(IInventory inventory) {
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
	    super.readFromNBT(nbt);
	    this.customInventory.readFromNBT(nbt);
	    this.markDirty();
	    
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt){
	    super.writeToNBT(nbt);
	    customInventory.writeToNBT(nbt);
	}

}
