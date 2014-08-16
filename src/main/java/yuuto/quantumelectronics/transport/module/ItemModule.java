package yuuto.quantumelectronics.transport.module;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import yuuto.quantumelectronics.items.base.ModItem;
import yuuto.quantumelectronics.items.base.ModItemMulti;
import yuuto.quantumelectronics.transport.TileGridTile;
import yuuto.quantumelectronics.transport.managers.FluidExtractor;
import yuuto.quantumelectronics.transport.managers.FluidProvider;
import yuuto.quantumelectronics.transport.managers.FluidReceiver;
import yuuto.quantumelectronics.transport.managers.ItemExtractor;
import yuuto.quantumelectronics.transport.managers.ItemProvider;
import yuuto.quantumelectronics.transport.managers.ItemReceiver;
import yuuto.quantumelectronics.transport.managers.ItemSupplierActive;
import yuuto.quantumelectronics.transport.managers.ItemSupplierPassive;
import yuuto.quantumelectronics.transport.routing.IRouter;

public class ItemModule extends ModItemMulti{

	/*"gridEnergyAcceptor", "gridEnergySync", "ItemExtractor", 
	"ItemReceiver", "ItemProvider", "ItemSupplierActive", 
	"ItemSupplierPassive", "FluidExtractor", "FluidReceiver", 
	"FluidProvider"*/
	
	public ItemModule(CreativeTabs tab, String unlocName) {
		super(tab, unlocName);
	}

	public ModuleFilter openInventory(ItemStack stack){
		return new ModuleFilter(9, stack);
	}
	public void saveInventory(ItemStack stack, ModuleFilter filter){
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null){
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		filter.writeToNBT(nbt);
	}
	public IRouter getRouter(ItemStack stack, TileGridTile tile){
		switch(stack.getItemDamage()){
		case 0:
			return null;
		case 1:
			return null;
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
		
	}
	
	public boolean canIgnoreMeta(ItemStack stack){
		return true;
	}
	public boolean canIgnoreNBT(ItemStack stack){
		return true;
	}
	public boolean canBlackList(ItemStack stack){
		return true;
	}
	public boolean canUseOreDict(ItemStack stack){
		return true;
	}
	public boolean canBeGeneric(ItemStack stack){
		return true;
	}
	public boolean canSneaky(ItemStack stack){
		return false;
	}
}
