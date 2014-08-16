package yuuto.quantumelectronics.transport.module;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import yuuto.quantumelectronics.QuantumElectronics;
import yuuto.quantumelectronics.items.base.ModItem;
import yuuto.quantumelectronics.items.base.ModItemMulti;
import yuuto.quantumelectronics.ref.ModTabs;
import yuuto.quantumelectronics.transport.TileGridTile;
import yuuto.quantumelectronics.transport.managers.EnergyAcceptor;
import yuuto.quantumelectronics.transport.managers.EnergySync;
import yuuto.quantumelectronics.transport.managers.FluidExtractor;
import yuuto.quantumelectronics.transport.managers.FluidProvider;
import yuuto.quantumelectronics.transport.managers.FluidReceiver;
import yuuto.quantumelectronics.transport.managers.ItemExtractor;
import yuuto.quantumelectronics.transport.managers.ItemProvider;
import yuuto.quantumelectronics.transport.managers.ItemReceiver;
import yuuto.quantumelectronics.transport.managers.ItemSupplierActive;
import yuuto.quantumelectronics.transport.managers.ItemSupplierPassive;
import yuuto.quantumelectronics.transport.routing.IFluidRouter;
import yuuto.quantumelectronics.transport.routing.IItemRouter;
import yuuto.quantumelectronics.transport.routing.IRouter;
import yuuto.quantumelectronics.transport.tile.IInventoryParrent;
import yuuto.quantumelectronics.transport.tile.TileNodeChassi;

public class ItemModule extends ModItemMulti{

	public ItemModule() {
		super(ModTabs.TAB_MAIN, "moduleEnergyAcceptor", "moduleEnergySync",
				"moduleItemExtractor", "moduleItemReceiver", "moduleItemPRovider",
				"moduleItemSupplierActive", "moduleItemSupplierPassive", 
				"moduleFluidExtractor", "moduleFluidReceiver", "moduleFluidProvider");
	}
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		System.out.println("OnItemUse");
		if(world.isRemote)
			return stack;
		player.openGui(QuantumElectronics.instance, 101, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		return stack;
	}

	public ModuleFilter openInventory(ItemStack stack){
		return new ModuleFilter(9, stack);
	}
	public ModuleFilter openInventory(ItemStack stack, IInventoryParrent parrent){
		return new ModuleFilter(9, stack, parrent);
	}
	public void saveInventory(ItemStack stack, ModuleFilter filter){
		NBTTagCompound nbt = new NBTTagCompound();
		filter.writeToNBT(nbt);
		stack.stackTagCompound = nbt;
	}
	public IRouter getRouter(ItemStack stack, TileNodeChassi tile){
		switch(stack.getItemDamage()){
		case 0:
			return new EnergyAcceptor(tile, tile.getEnergyStorage());
		case 1:
			return new EnergySync(tile, tile.getEnergyStorage());
		case 2:
			return new ItemExtractor(tile);
		case 3:
			return new ItemReceiver(tile);
		case 4:
			return new ItemProvider(tile);
		case 5:
			return new ItemSupplierActive(tile);
		case 6:
			return new ItemSupplierPassive(tile);
		case 7:
			return new FluidExtractor(tile);
		case 8:
			return new FluidReceiver(tile);
		case 9:
			return new FluidProvider(tile);
		default:
			return null;
		}
	}
	public void updateRouter(ItemStack stack, IRouter router){
		ModuleFilter filter = openInventory(stack);
		if(filter == null)
			return;
		if(router instanceof IItemRouter){
			((IItemRouter)router).setFilter(filter.getItemFilter());
		}
		if(router instanceof IFluidRouter){
			((IFluidRouter)router).setFilter(filter.getFluidFilter());
		}
		router.setSneaky(filter.sneaky());
	}
}
