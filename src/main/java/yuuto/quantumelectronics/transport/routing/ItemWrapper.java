package yuuto.quantumelectronics.transport.routing;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemWrapper{
	Item item;
	int metaData;
	NBTTagCompound nbt;
	
	public ItemWrapper(ItemStack stack){
		this.item = stack.getItem();
		this.metaData = stack.getItemDamage();
		this.nbt = stack.getTagCompound();
	}
	public Item getItem(){
		return item;
	}
	public int getMetaData(){
		return metaData;
	}
	public NBTTagCompound getNBT(){
		return nbt;
	}
	public ItemStack getStack(){
		ItemStack ret = new ItemStack(item, 0, metaData);
		if(nbt != null)
			ret.setTagCompound((NBTTagCompound)nbt.copy());
		return ret;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof ItemWrapper))
			return super.equals(o);
		ItemWrapper w = (ItemWrapper) o;
		if(w.getItem() != item)
			return false;
		if(w.getMetaData() != metaData)
			return false;
		if(nbt == null && w.getNBT() == null)
			return true;
		if(nbt.equals(w.getNBT()))
			return true;
		return false;
	}
}
