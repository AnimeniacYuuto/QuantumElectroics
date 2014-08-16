package yuuto.quantumelectronics.transport.renderer;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import yuuto.quantumelectronics.tile.base.IMachine;
import yuuto.quantumelectronics.tile.renderer.RendererModelPylon;
import yuuto.quantumelectronics.util.ColorRGBAb;

public class RendererItemRouter implements IItemRenderer{

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
			renderItem(-0.5f, 0, 0.5f, 0, item.getItemDamage(), 0.7f, 0.7f, 0.7f);
			break;
		case EQUIPPED:
			renderItem(0.55f, 0, 0.6f, 0, item.getItemDamage(), 0.7f, 0.7f, 0.7f);
			break;
		case EQUIPPED_FIRST_PERSON:
			renderItem(0.55f, 0.2f, 0.2f, 0, item.getItemDamage(), 0.7f, 0.7f, 0.7f);
			break;
		case FIRST_PERSON_MAP:
			renderItem(0.55f, 0, 0, 0, item.getItemDamage(), 0.7f, 0.7f, 0.7f);
			break;
		case INVENTORY:
			//TODO
			renderItem(0f,0f,0, 1, item.getItemDamage(), 14f, 14f, 14f);
		default:
			break;
		}
	}
	private void renderItem(float x, float y, float z,int rot, int meta, 
			float scaleX, float scaleY, float scaleZ){
		ColorRGBAb p = getPylonColor(meta);
		RendererModelPylon.render(x, y, z, scaleX, scaleY, scaleZ, rot, p.red, p.green, p.blue, p.alpha);
		RendererNode.render(x, y, z, scaleX, scaleY, scaleZ, rot, 0, 128, 128, 255, 255);
		ColorRGBAb b = getBaseColor(meta);
		RendererNode.render(x, y, z, scaleX, scaleY, scaleZ, rot, 1, b.red, b.green, b.blue, b.alpha);
	}
	
	private ColorRGBAb getPylonColor(int meta){
		switch(meta){
		case 0:
		case 1:
			return new ColorRGBAb(255,255,0, 191);
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			return new ColorRGBAb(0, 255,0, 191);
		case 7:
		case 8:
		case 9:
			return new ColorRGBAb(0,0,255, 191);
		case 10:
			return new ColorRGBAb(191, 191, 255, 204);
		default:
			return new ColorRGBAb(255, 255, 255, 191);
		}
	}
	private ColorRGBAb getBaseColor(int i){
		switch(i){
		case 0:
		case 2:
		case 7:
			return new ColorRGBAb(0, 128, 0, 255);
		case 1:
		case 3:
		case 8:
			return new ColorRGBAb(128, 128, 0, 255);
		case 4:
		case 9:
			return new ColorRGBAb(0, 0, 255, 255);
		case 5:
			return new ColorRGBAb(255, 128, 0, 255);
		case 6:
			return new ColorRGBAb(255, 0, 0, 255);
		case 10:
			return new ColorRGBAb(191, 191, 255, 255);
		default:
			return new ColorRGBAb(255, 255, 255, 255);
		}
	}
}
