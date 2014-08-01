package yuuto.quantumelectronics;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import yuuto.quantumelectronics.blocks.ModBlockMachines1;
import yuuto.quantumelectronics.blocks.ModBlockOre;
import yuuto.quantumelectronics.blocks.base.ModBlock;
import yuuto.quantumelectronics.blocks.base.ModBlockContainer;
import yuuto.quantumelectronics.blocks.base.ModBlockMulti;
import yuuto.quantumelectronics.items.base.ModItemBlockMulti;
import yuuto.quantumelectronics.ref.ModTabs;
import yuuto.quantumelectronics.tile.TileGemFurnace;
import yuuto.quantumelectronics.tile.TileGemGrinder;
import yuuto.quantumelectronics.transport.BlockPylon;
import yuuto.quantumelectronics.transport.TilePylon;

public class ModBlocks {
	
	public static final ModBlockContainer PYLON = new BlockPylon("Pylon");
	public static final ModBlock ORE = new ModBlockOre();
	public static final ModBlock BLOCK_GEM = new ModBlockMulti(
			Material.rock, ModTabs.TAB_MAIN, "blockParidot", "blockRuby", "blockSapphire");
	public static final ModBlockContainer MACHINES1 = new ModBlockMachines1();
	
	public static void init(){
		GameRegistry.registerBlock(PYLON, "Pylon");
		GameRegistry.registerTileEntity(TilePylon.class, "conatiner.Pylon");
		
		GameRegistry.registerBlock(ORE, ModItemBlockMulti.class, "QEBlockOre");
		OreDictionary.registerOre("oreParidot", new ItemStack(ORE, 1, 0));
		OreDictionary.registerOre("oreRuby", new ItemStack(ORE, 1, 1));
		OreDictionary.registerOre("oreSapphire", new ItemStack(ORE, 1, 2));
		
		GameRegistry.registerBlock(BLOCK_GEM, ModItemBlockMulti.class, "blockGem");
		OreDictionary.registerOre("blockParidot", new ItemStack(BLOCK_GEM, 1, 0));
		OreDictionary.registerOre("blockRuby", new ItemStack(BLOCK_GEM, 1, 1));
		OreDictionary.registerOre("blockSapphire", new ItemStack(BLOCK_GEM, 1, 2));
		
		GameRegistry.registerBlock(MACHINES1, ModItemBlockMulti.class, "ProdctionMachines");
		GameRegistry.registerTileEntity(TileGemFurnace.class, "ContainerGemFurnace");
		GameRegistry.registerTileEntity(TileGemGrinder.class, "ContainerGemGrinder");
		OreDictionary.registerOre("gemFurnace", new ItemStack(MACHINES1, 1, 0));
		OreDictionary.registerOre("gemFurnace", new ItemStack(MACHINES1, 1, 1));
		OreDictionary.registerOre("gemFurnace", new ItemStack(MACHINES1, 1, 2));
		OreDictionary.registerOre("gemGrinder", new ItemStack(MACHINES1, 1, 3));
		OreDictionary.registerOre("gemGrinder", new ItemStack(MACHINES1, 1, 4));
		OreDictionary.registerOre("gemGrinder", new ItemStack(MACHINES1, 1, 5));
	}
}
