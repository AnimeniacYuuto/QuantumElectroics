package yuuto.quantumelectronics.transport.filter;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemFilter {
	ItemStack[] filter;
	boolean generic;
	boolean white;
	boolean ore;
	boolean meta;
	boolean nbt;
	
	public ItemFilter(ItemStack[] stacks,boolean white, 
			boolean ore, boolean meta, boolean nbt){
		filter = stacks;
		this.white = white;
		this.ore = ore;
		this.meta = meta;
		this.nbt = nbt;
		
		generic = false;
	}
	public ItemFilter(){
		generic = true;
	}
	
	public int getMaxFiltered(ItemStack stack, IInventory target, 
			boolean adjusted, boolean supplier ){
		if(generic){
			int ret = stack.getMaxStackSize();
			if(adjusted)
				ret-= stack.stackSize;
			if(target.getInventoryStackLimit() < ret)
				ret = target.getInventoryStackLimit();
			return ret;
		}
		for(int i = 0; i < filter.length; i++){
			if(filter[i] == null)
				continue;
			if(matches(filter[i], stack)){
				if(!white)
					return 0;
				int ret = 0;
				if(supplier){
					ret = filter[i].stackSize;
					if(target.getInventoryStackLimit() < ret)
						ret = target.getInventoryStackLimit();
				}else{
					ret = target.getInventoryStackLimit();
				}
				if(adjusted && stack.getMaxStackSize()-stack.stackSize < ret)
					ret = stack.getMaxStackSize()-stack.stackSize;
				if(!adjusted && stack.getMaxStackSize() < ret)
					ret = stack.getMaxStackSize();
				return ret;
			}
		}
		if(white)
			return 0;
		int ret = stack.getMaxStackSize()-stack.stackSize;
		if(target.getSizeInventory() < ret)
			ret = target.getSizeInventory();
		return ret;
	}
	public void removeItemStack(ItemStack stack){
		for(int i = 0; i < filter.length; i++){
			if(filter[i] == null)
				continue;
			if(matches(filter[i], stack)){
				filter[i].stackSize -= stack.stackSize;
			}
		}
	}
	
	protected boolean matches(ItemStack filter, ItemStack target){
		if(ore){
			int[] ids = OreDictionary.getOreIDs(filter);
			if(ids != null && ids.length > 0){
				int[] ids2 = OreDictionary.getOreIDs(target);
				if(ids2 != null && ids2.length > 0){
					for(int i = 0; i < ids.length; i++){
						for(int j = 0; j < ids2.length; j++){
							if(ids[i] == ids2[j])
								return true;
		}}}}}
		if(filter.getItem() != target.getItem())
			return false;
		if(meta && filter.getItemDamage() != target.getItemDamage())
			return false;
		if(!nbt)
			return true;
		if(filter.getTagCompound() == null){
			return target.getTagCompound() == null;
		}else{
			if(target.getTagCompound() == null)
				return false;
			return filter.getTagCompound().equals(target.getTagCompound());
		}
	}
	public boolean isGeneric(){
		return generic;
	}
	public boolean isWhite(){
		return white;
	}
	public ItemFilter copy(){
		if(generic)
			return new ItemFilter();
		ItemStack[] filter2 = new ItemStack[filter.length];
		for(int i = 0; i < filter2.length; i++){
			if(filter[i] == null)
				continue;
			filter2[i] = filter[i].copy();
		}
		return new ItemFilter(filter2, white, ore, meta, nbt) ;
	}
}
