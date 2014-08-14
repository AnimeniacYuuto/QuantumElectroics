package yuuto.quantumelectronics.items.renderer;

import org.lwjgl.opengl.GL11;

import yuuto.quantumelectronics.ref.ModTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RendererItemGem implements IItemRenderer{
	IModelCustom model;
	ResourceLocation texture;
	
	public RendererItemGem(){
		model = AdvancedModelLoader.loadModel(ModTextures.MODEL_GEM);
		texture = ModTextures.TEXT_GEM;
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.INVENTORY;
	}
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		ItemRenderType t = ItemRenderType.INVENTORY;
		
		switch(type){
		case ENTITY:
			renderItem(0.55f, 0, 0, item.getItemDamage(), 0.7f, 0.7f, 0.7f, true);
			break;
		case EQUIPPED:
			renderItem(0.55f, 0, 0.6f, item.getItemDamage(), 0.7f, 0.7f, 0.7f, true);
			break;
		case EQUIPPED_FIRST_PERSON:
			renderItem(0.55f, -0.2f, 0.7f, item.getItemDamage(), 0.7f, 0.7f, 0.7f, true);
			break;
		case FIRST_PERSON_MAP:
			renderItem(0.55f, 0, 0, item.getItemDamage(), 0.7f, 0.7f, 0.7f, true);
			break;
		default:
			break;
		}
		
	}
	
	/**
	 * Renders the item given the following transforms
	 * @param x
	 * @param y
	 * @param z
	 * @param meta
	 * @param scaleX
	 * @param scaleY
	 * @param scaleZ
	 * @param blend
	 */
	private void renderItem(float x, float y, float z, int meta, 
			float scaleX, float scaleY, float scaleZ, boolean blend){
		GL11.glPushMatrix();
		GL11.glScalef(scaleX, scaleY, scaleZ);
		GL11.glRotatef(90, 0, 1, 0);
		GL11.glRotatef(90, 0, 0, 1);
		GL11.glTranslatef(x, y, z);
		GL11.glEnable(GL11.GL_LIGHT2);
		GL11.glEnable(GL11.GL_BLEND);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        setColor(meta);
		model.renderAll();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LIGHT2);
		GL11.glPopMatrix();
	}
	/**
	 * sets the color based on the item metadata
	 * @param meta
	 */
	private void setColor(int meta){
		switch(meta){
		default:
		case 0:
			GL11.glColor4f(0, 1, 0, 0.7f);
			break;
		case 1:
			GL11.glColor4f(1, 0, 0, 0.75f);
			break;
		case 2:
			GL11.glColor4f(0, 0, 1, 0.65f);
			break;
		case 3:
			GL11.glColor4f(1, 1, 0, 0.8f);
			break;
		}
		
	}

}
