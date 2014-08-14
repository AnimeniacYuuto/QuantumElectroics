package yuuto.quantumelectronics.transport.routing;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemProviderStack {
	Item item;
	public long stackSize;
	public short damage;
	boolean craftable = false;
	NBTTagCompound tag;
	
	protected ItemProviderStack(){}
	protected ItemProviderStack(ItemProviderStack original){
		this.item = original.getItem();
		this.stackSize = original.stackSize;
		this.damage = original.getItemDamage();
		this.craftable = original.craftable;
		this.tag = original.getTag();
	}
	public ItemProviderStack(ItemStack original){
		item = original.getItem();
		stackSize = original.stackSize;
		damage = (short)original.getItemDamage();
		if(original.hasTagCompound()){
			tag = original.getTagCompound();
		}
	}
	public ItemProviderStack(Block block){
        this(block, 1);
    }
    public ItemProviderStack(Block block, int amount){
        this(block, amount, 0);
    }
    public ItemProviderStack(Block block, int amount, int damage){
        this(Item.getItemFromBlock(block), amount, damage);
    }
    public ItemProviderStack(Item item)
    {
        this(item, 1);
    }
    public ItemProviderStack(Item item, int amount)
    {
        this(item, amount, 0);
    }
    public ItemProviderStack(Item item, int amount, int damage)
    {
        this.item = item;
        this.stackSize = amount;
        this.damage = (short)damage;

        if (this.damage < 0)
        {
            this.damage = 0;
        }
    }
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setShort("id", (short)Item.getIdFromItem(item));
        nbt.setLong("Count", this.stackSize);
        nbt.setShort("Damage", (short)this.damage);

        if (tag != null)
        {
            nbt.setTag("tag", this.tag);
        }

        return nbt;
    }
	protected void readFromNBT(NBTTagCompound nbt){
		this.item = Item.getItemById(nbt.getShort("id"));
        this.stackSize = nbt.getLong("Count");
        this.damage = nbt.getShort("Damage");

        if (this.damage < 0)
        {
            this.damage = 0;
        }

        if (nbt.hasKey("tag", 10))
        {
            this.tag = nbt.getCompoundTag("tag");
        }
	}
	public Item getItem(){
		return item;
	}
	public NBTTagCompound getTag(){
		return tag;
	}
	public short getItemDamage(){
		return damage;
	}
	public short getMaxDamage(){
		return (short)item.getMaxDamage();
	}
	public int getMaxStackSize(){
		return item.getItemStackLimit(getItemStack());
	}
	public String getDisplayName(){
		return getItemStack().getDisplayName();
	}
	public ItemStack getItemStack(){
		ItemStack ret = new ItemStack(item, (int)stackSize, damage);
		if(tag != null)
			ret.setTagCompound(tag);
		return ret;
	}
	public ItemProviderStack splitStack(int amount)
    {
        ItemProviderStack itemstack = this.copy();
        itemstack.stackSize = amount;

        this.stackSize -= amount;
        return itemstack;
    }
	public ItemProviderStack copy(){
		return new ItemProviderStack(this);
	}
	public static ItemProviderStack loadFromNBT(NBTTagCompound nbt){
		ItemProviderStack ret = new ItemProviderStack();
		ret.readFromNBT(nbt);
		return ret.getItem() == null ? null : ret;
	}
}
