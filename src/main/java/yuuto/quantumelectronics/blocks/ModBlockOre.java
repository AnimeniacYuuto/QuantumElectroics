package yuuto.quantumelectronics.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import yuuto.quantumelectronics.ModItems;
import yuuto.quantumelectronics.blocks.base.ModBlockMulti;
import yuuto.quantumelectronics.ref.ModTabs;

public class ModBlockOre extends ModBlockMulti{

	public ModBlockOre() {
		super(Material.rock, ModTabs.TAB_MAIN, "OreParidot", "OreRuby", "OreSaphire", 
				"OreSolisCrystal");
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(soundTypePiston);
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int fortune){
		return ModItems.GEM;
	}
	@Override
	public int quantityDropped(int meta, int fortune, Random rand){
		return (rand.nextInt(3)+1)*(fortune+1);
	}
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune){
		if(meta == 3){
			ItemStack ret = ModItems.getFullyChargedSolis();
			ret.stackSize = quantityDropped(meta, fortune, world.rand);
			ArrayList<ItemStack> list = new ArrayList<ItemStack>();
			list.add(ret);
			return list;
		}
		return super.getDrops(world, x, y, z, meta, fortune);
	}


}
