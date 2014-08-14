package yuuto.quantumelectronics.recipe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import yuuto.quantumelectronics.ModItems;
import yuuto.quantumelectronics.transport.routing.ItemWrapper;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class GrinderRecipes {
	Map<ItemStack, ItemStack> recipeMap = new HashMap<ItemStack, ItemStack>();
	
	public static final GrinderRecipes instance = new GrinderRecipes();
	
	public GrinderRecipes(){
		addRecipe(new ItemStack(ModItems.DUST, 2, 0), new ItemStack(Blocks.iron_ore));
		addRecipe(new ItemStack(ModItems.DUST, 2, 1), new ItemStack(Blocks.gold_ore));
	}
	/**
	 * Adds a recipe to the grinder
	 * @param output
	 * @param input
	 */
	public void addRecipe(ItemStack output, ItemStack input){
		System.out.println("Added Recipe");
		recipeMap.put(input, output);
	}
	/**
	 * Gets the result for the given item processed by the grinder
	 * @param input
	 * @return the result for the given item processed by the grinder, null if none
	 */
	public ItemStack getResult(ItemStack input){
		Iterator iterator = this.recipeMap.entrySet().iterator();
        Entry entry;

        do{
            if (!iterator.hasNext()){
                return null;
            }

            entry = (Entry)iterator.next();
        }
        while (!this.compare(input, (ItemStack)entry.getKey()));

        return (ItemStack)entry.getValue();
	}
	/**
	 * Compares the 2 itemstacks
	 * @param stack1
	 * @param stack2
	 * @return whether the 2 itemstacks are the same
	 */
	private boolean compare(ItemStack stack1, ItemStack stack2){
        return stack2.getItem() == stack1.getItem() && (stack2.getItemDamage() == 32767 || stack2.getItemDamage() == stack1.getItemDamage());
    }
}
