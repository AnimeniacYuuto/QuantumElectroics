package yuuto.quantumelectronics.recipe;

import java.util.HashMap;
import java.util.Map;

import yuuto.quantumelectronics.ModItems;
import yuuto.quantumelectronics.util.ItemWrapper;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class GrinderRecipes {
	Map<ItemStack, ItemStack> recipeMap = new HashMap<ItemStack, ItemStack>();
	
	public static final GrinderRecipes instance = new GrinderRecipes();
	
	public GrinderRecipes(){
		addRecipe(new ItemStack(ModItems.DUST, 2, 0), new ItemStack(Blocks.iron_ore));
		addRecipe(new ItemStack(ModItems.DUST, 2, 1), new ItemStack(Blocks.gold_ore));
	}
	public void addRecipe(ItemStack output, ItemStack input){
		recipeMap.put(input, output);
	}
	public ItemStack getResult(ItemStack input){
		ItemWrapper searcher = new ItemWrapper(input.copy());
		if(recipeMap.containsKey(searcher)){
			return recipeMap.get(searcher);
		}
		return null;
	}
}
