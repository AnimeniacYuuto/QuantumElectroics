package yuuto.quantumelectronics;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import yuuto.quantumelectronics.blocks.ModBlockOre;
import yuuto.quantumelectronics.blocks.base.ModBlock;
import yuuto.quantumelectronics.blocks.base.ModBlockContainer;
import yuuto.quantumelectronics.items.base.ModItemBlockMulti;
import yuuto.quantumelectronics.ref.ModTabs;
import yuuto.quantumelectronics.transport.BlockPylon;
import yuuto.quantumelectronics.transport.TilePylon;

public class ModBlocks {
	
	public static final ModBlockContainer PYLON = new BlockPylon("Pylon");
	public static final ModBlock ORE = new ModBlockOre();
	
	public static void init(){
		GameRegistry.registerBlock(PYLON, "Pylon");
		GameRegistry.registerTileEntity(TilePylon.class, "conatiner.Pylon");
		
		GameRegistry.registerBlock(ORE, ModItemBlockMulti.class, "QEBlockOre");
		OreDictionary.registerOre("oreParidot", new ItemStack(ORE, 1, 0));
		OreDictionary.registerOre("oreRuby", new ItemStack(ORE, 1, 1));
		OreDictionary.registerOre("oreSaphire", new ItemStack(ORE, 1, 2));
	}
}
