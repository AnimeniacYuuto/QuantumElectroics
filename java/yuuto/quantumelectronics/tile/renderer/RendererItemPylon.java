package yuuto.quantumelectronics.tile.renderer;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RendererItemPylon implements IItemRenderer{

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch(type){
		case ENTITY:
			renderItem(-0.5f, 0, 0.5f, item.getItemDamage(), 0.7f, 0.7f, 0.7f);
			break;
		case EQUIPPED:
			renderItem(0.55f, 0, 0.6f, item.getItemDamage(), 0.7f, 0.7f, 0.7f);
			break;
		case EQUIPPED_FIRST_PERSON:
			renderItem(0.55f, 0.2f, 0.2f, item.getItemDamage(), 0.7f, 0.7f, 0.7f);
			break;
		case FIRST_PERSON_MAP:
			renderItem(0.55f, 0, 0, item.getItemDamage(), 0.7f, 0.7f, 0.7f);
			break;
		case INVENTORY:
			//TODO
			renderItem(0.5f,0,0, item.getItemDamage(), 0.7f, 0.7f, 0.7f);
		default:
			break;
		}
	}
	private void renderItem(float x, float y, float z, int meta, 
			float scaleX, float scaleY, float scaleZ){
		RendererModelPylon.render(x, y, z, scaleX, scaleY, scaleZ, 255, 255, 255, 255);
	}

}
