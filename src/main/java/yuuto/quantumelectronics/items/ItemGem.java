package yuuto.quantumelectronics.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import yuuto.quantumelectronics.ModItems;
import yuuto.quantumelectronics.items.base.ModItemMulti;
import yuuto.quantumelectronics.ref.ModTabs;
import yuuto.quantumelectronics.util.Logger;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemGem extends ModItemMulti{

	public ItemGem() {
		super(ModTabs.TAB_MAIN, "GemPeridot", "GemRuby", "GemSaphire", "GemSolis");
		//this.addInformation(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_)
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, 
			List Reuslts, boolean bool){
		super.addInformation(stack, player, Reuslts, bool);
		if(stack.getItemDamage() == 3){
			Reuslts.add(getEnergy(stack)+"/1000 RF");
		}
	}
	
	public int getEnergy(ItemStack stack){
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null){
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		return nbt.getInteger("Energy");
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack){
        return stack.getItemDamage() == 3;
    }
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack){
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null){
			return 1d;
		}
		return 1-(nbt.getInteger("Energy")/1000);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List results){
		for(int i = 0; i < unlocNames.length; i++){
			if(i == 3){
				results.add(ModItems.getFullyChargedSolis());
			}
			results.add(new ItemStack(this, 1, i));
		}
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem e){
		
		if(!e.worldObj.canBlockSeeTheSky((int)e.posX, (int)e.posY, (int)e.posZ))
			return false;
		if(!e.worldObj.isDaytime())
			return false;
		if(e.worldObj.rand.nextInt(1500) > 1)
			return false;
		
		Logger.LogInfo("UpdateEntity ");
		ItemStack stack = e.getEntityItem();
		if(stack.getItemDamage() != 3)
			return false;
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null){
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		int energy = nbt.getInteger("Energy");
		if(energy < 1000)
			nbt.setInteger("Energy", energy+1);
		e.setEntityItemStack(stack);
		return false;
	}

}
