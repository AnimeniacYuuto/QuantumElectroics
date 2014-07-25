package yuuto.quantumelectronics;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import yuuto.quantumelectronics.items.ItemGem;
import yuuto.quantumelectronics.items.base.ModItem;

public class ModItems {

	public static final ModItem GEM = new ItemGem();
	public static void init(){
		GameRegistry.registerItem(GEM, "QEGem");
		OreDictionary.registerOre("gemPeridot", new ItemStack(GEM, 1, 0));
		OreDictionary.registerOre("gemRuby", new ItemStack(GEM, 1, 1));
		OreDictionary.registerOre("gemSaphire", new ItemStack(GEM, 1, 2));
	}
	

	public static ItemStack getFullyChargedSolis(){
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("Energy", 1000);
		ItemStack ret = new ItemStack(ModItems.GEM, 1, 3);
		ret.setTagCompound(nbt);
		return ret;
	}
}
