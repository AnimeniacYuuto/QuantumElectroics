package yuuto.quantumelectronics.recipe;

import yuuto.quantumelectronics.ModBlocks;
import yuuto.quantumelectronics.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeFactory {
	
	public static void init(){
		//Adds Gem Furnace Recipes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.MACHINES1, 1, 0),
			" p ", "p p", "pfp", 'f', new ItemStack(Blocks.furnace), 'p', "gemParidot"
		));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.MACHINES1, 1, 1),
			" r ", "r r", "rfr", 'r', "gemRuby", 'f', new ItemStack(Blocks.furnace)
		));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.MACHINES1, 1, 2),
			" s ", "s s", "sfs", 's', "gemSapphire", 'f', new ItemStack(Blocks.furnace)
		));
		//Adds Gem Grinder Recipes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.MACHINES1, 1, 3),
			"gwg", "g g", "ggg", 'g', "gemParidot", 'w', new ItemStack(Blocks.planks)
		));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.MACHINES1, 1, 4),
			"gwg", "g g", "ggg", 'g', "gemRuby", 'w', new ItemStack(Blocks.planks)
		));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.MACHINES1, 1, 5),
			"gwg", "g g", "ggg", 'g', "gemSapphire", 'w', new ItemStack(Blocks.planks)
		));
		
		//Adds Pylon shard Recipe
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.MISC1, 8, 0), 
			" r ", "sdp", " c ", 'r', "gemRuby", 's', "gemSapphire", 'd', new ItemStack(Items.redstone), 
			'p', "gemParidot", 'c', new ItemStack(ModItems.GEM, 1, 3)	
		));
		
		//Adds Gem Block Recipes
		GameRegistry.addRecipe(new ItemStack(ModBlocks.BLOCK_GEM, 1, 0),
			"ggg", "ggg", "ggg", 'g', new ItemStack(ModItems.GEM, 1, 0)
		);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.BLOCK_GEM, 1, 1),
			"ggg", "ggg", "ggg", 'g', new ItemStack(ModItems.GEM, 1, 1)
		);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.BLOCK_GEM, 1, 2),
			"ggg", "ggg", "ggg", 'g', new ItemStack(ModItems.GEM, 1, 2)
		);
	}
	
	public static void postInit(){
		
	}
}
