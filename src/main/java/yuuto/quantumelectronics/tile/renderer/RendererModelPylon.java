package yuuto.quantumelectronics.tile.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import yuuto.quantumelectronics.ref.ModTextures;

public class RendererModelPylon {
	static IModelCustom modelUp = AdvancedModelLoader.loadModel(ModTextures.MODEL_PYLON_UP);
	static IModelCustom modelDown = AdvancedModelLoader.loadModel(ModTextures.MODEL_PYLON_DOWN);
	static IModelCustom modelNorth = AdvancedModelLoader.loadModel(ModTextures.MODEL_PYLON_NORTH);
	static IModelCustom modelSouth = AdvancedModelLoader.loadModel(ModTextures.MODEL_PYLON_SOUTH);
	static IModelCustom modelEast = AdvancedModelLoader.loadModel(ModTextures.MODEL_PYLON_EAST);
	static IModelCustom modelWest = AdvancedModelLoader.loadModel(ModTextures.MODEL_PYLON_WEST);
	static ResourceLocation texture = ModTextures.TEXT_GEM;
	
	public static void render(double x, double y, double z, 
			float scaleX, float scaleY, float scaleZ,
			int red, int green, int blue, int alpha){
		render(x,y,z,scaleX, scaleY, scaleZ, (byte)red, (byte)green, (byte)blue, (byte)alpha);
	}
	public static void render(double x, double y, double z, 
			float scaleX, float scaleY, float scaleZ,
			byte red, byte green, byte blue, byte alpha){
		GL11.glPushMatrix();
		GL11.glScalef(scaleX, scaleY, scaleZ);
		//GL11.glRotatef(90, 0, 1, 0);
		//GL11.glRotatef(90, 0, 0, 1);
		GL11.glTranslated(x, y, z);
		//GL11.glEnable(GL11.GL_LIGHT2);
		GL11.glEnable(GL11.GL_BLEND);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        GL11.glColor4f(0.75f, 0.75f, 1.0f, 0.8f);
		modelDown.renderAll();
		GL11.glDisable(GL11.GL_BLEND);
		//GL11.glDisable(GL11.GL_LIGHT2);
		GL11.glPopMatrix();
	}
	public static void render(double x, double y, double z, 
			float scaleX, float scaleY, float scaleZ,
			int rot,
			int red, int green, int blue, int alpha){
		GL11.glPushMatrix();
		GL11.glScalef(scaleX, scaleY, scaleZ);
		//GL11.glRotatef(90, 0, 1, 0);
		//GL11.glRotatef(90, 0, 0, 1);
		GL11.glTranslated(x, y, z);
		//GL11.glEnable(GL11.GL_LIGHT2);
		GL11.glEnable(GL11.GL_BLEND);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        GL11.glColor4f(red/255f, green/255f, blue/255f, alpha/255f);
        switch(rot){
        default:
        case 0:
        	modelDown.renderAll();
        	break;
        case 1:
        	modelUp.renderAll();
        	break;
        case 2:
        	modelSouth.renderAll();
        	break;
        case 3:
        	modelNorth.renderAll();
        	break;
        case 4:
        	modelWest.renderAll();
        	break;
        case 5:
        	modelEast.renderAll();
        	break;
        }
		GL11.glDisable(GL11.GL_BLEND);
		//GL11.glDisable(GL11.GL_LIGHT2);
		GL11.glPopMatrix();
	}
}
