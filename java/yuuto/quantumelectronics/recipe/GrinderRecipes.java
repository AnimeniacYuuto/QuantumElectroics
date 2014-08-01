package yuuto.quantumelectronics.recipe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
		System.out.println("Added Recipe");
		recipeMap.put(input, output);
	}
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
	private boolean compare(ItemStack stack1, ItemStack stack2){
        return stack2.getItem() == stack1.getItem() && (stack2.getItemDamage() == 32767 || stack2.getItemDamage() == stack1.getItemDamage());
    }
}
