package yuuto.quantumelectronics;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import yuuto.quantumelectronics.blocks.*;
import yuuto.quantumelectronics.blocks.base.*;
import yuuto.quantumelectronics.items.base.ModItemBlockMulti;
import yuuto.quantumelectronics.ref.ModTabs;
import yuuto.quantumelectronics.tile.*;
import yuuto.quantumelectronics.transport.tile.*;

public class ModBlocks {
	
	public static final ModBlockContainer PYLON = new ModBlockPylon();
	public static final ModBlock ORE = new ModBlockOre();
	public static final ModBlock BLOCK_GEM = new ModBlockMulti(
			Material.rock, ModTabs.TAB_MAIN, "blockParidot", "blockRuby", "blockSapphire");
	public static final ModBlockContainer MACHINES1 = new ModBlockMachines1();
	public static final ModBlockContainer GRID_MACHINES1 = new ModBlockGridMachines1();
	
	public static void init(){
		//registers the pylon block
		GameRegistry.registerBlock(PYLON, ModItemBlockMulti.class, "Pylon");
		GameRegistry.registerTileEntity(TilePylon.class, "conatiner.Pylon");
		
		//Registers my ore blocks
		GameRegistry.registerBlock(ORE, ModItemBlockMulti.class, "QEBlockOre");
		OreDictionary.registerOre("oreParidot", new ItemStack(ORE, 1, 0));
		OreDictionary.registerOre("oreRuby", new ItemStack(ORE, 1, 1));
		OreDictionary.registerOre("oreSapphire", new ItemStack(ORE, 1, 2));
		
		//Registers my gem storage blocks
		GameRegistry.registerBlock(BLOCK_GEM, ModItemBlockMulti.class, "blockGem");
		OreDictionary.registerOre("blockParidot", new ItemStack(BLOCK_GEM, 1, 0));
		OreDictionary.registerOre("blockRuby", new ItemStack(BLOCK_GEM, 1, 1));
		OreDictionary.registerOre("blockSapphire", new ItemStack(BLOCK_GEM, 1, 2));
		
		//Registers my gem machines
		GameRegistry.registerBlock(MACHINES1, ModItemBlockMulti.class, "ProdctionMachines");
		GameRegistry.registerTileEntity(TileGemFurnace.class, "Container.GemFurnace");
		GameRegistry.registerTileEntity(TileGemGrinder.class, "Container.GemGrinder");
		GameRegistry.registerTileEntity(TileGemGrinderCrank.class, "Container.GemCrank");
		OreDictionary.registerOre("gemFurnace", new ItemStack(MACHINES1, 1, 0));
		OreDictionary.registerOre("gemFurnace", new ItemStack(MACHINES1, 1, 1));
		OreDictionary.registerOre("gemFurnace", new ItemStack(MACHINES1, 1, 2));
		OreDictionary.registerOre("gemGrinder", new ItemStack(MACHINES1, 1, 3));
		OreDictionary.registerOre("gemGrinder", new ItemStack(MACHINES1, 1, 4));
		OreDictionary.registerOre("gemGrinder", new ItemStack(MACHINES1, 1, 5));
		
		//Registers my grid machines
		GameRegistry.registerBlock(GRID_MACHINES1, ModItemBlockMulti.class, "GridMachines");
		GameRegistry.registerTileEntity(TileGridFurnace.class, "Container.GridFurnace");
		GameRegistry.registerTileEntity(TileGridGenerator.class, "Container.GridGenerator");
		GameRegistry.registerTileEntity(TileGridGrinder.class, "Container.GridGrinder");
		GameRegistry.registerTileEntity(TileGridEnergyAcceptor.class, "Container.GridEnergyAcceptor");
		GameRegistry.registerTileEntity(TileGridEnergySync.class, "Container.GridEnergySync");
		GameRegistry.registerTileEntity(TileItemRouter.class, "Container.ItemRouter");
		GameRegistry.registerTileEntity(TileRequester.class, "Container.ItemRequester");
		GameRegistry.registerTileEntity(TileFluidRouter.class, "Container.FluidRouter");
	}
}
