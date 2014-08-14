package yuuto.quantumelectronics;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import yuuto.quantumelectronics.items.ItemFluidDummy;
import yuuto.quantumelectronics.items.ItemGem;
import yuuto.quantumelectronics.items.base.ModItem;
import yuuto.quantumelectronics.items.base.ModItemMulti;
import yuuto.quantumelectronics.ref.ModTabs;

public class ModItems {

	public static final ModItem GEM = new ItemGem();
	public static final ModItem DUST = new ModItemMulti(ModTabs.TAB_MAIN, "dustIron", "dustGold");
	public static final ModItem MISC1 = new ModItemMulti(ModTabs.TAB_MAIN, "shardPylon");
	public static final ItemFluidDummy FLUID_DUMMY = new ItemFluidDummy();
	public static void init(){
		//Registers my gems
		GameRegistry.registerItem(GEM, "QEGem");
		OreDictionary.registerOre("gemParidot", new ItemStack(GEM, 1, 0));
		OreDictionary.registerOre("gemRuby", new ItemStack(GEM, 1, 1));
		OreDictionary.registerOre("gemSapphire", new ItemStack(GEM, 1, 2));
		
		//Registers my dusts
		GameRegistry.registerItem(DUST, "QEDust");
		OreDictionary.registerOre("dustIron", new ItemStack(DUST, 1, 0));
		OreDictionary.registerOre("dustGold", new ItemStack(DUST, 1, 1));
		
		//Registers my miscellaneous crafting components
		GameRegistry.registerItem(MISC1, "misc1");
		
		GameRegistry.registerItem(FLUID_DUMMY, "QE_FluidDummy");
	}
	

	public static ItemStack getFullyChargedSolis(){
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("Energy", 1000);
		ItemStack ret = new ItemStack(ModItems.GEM, 1, 3);
		ret.setTagCompound(nbt);
		return ret;
	}
}
