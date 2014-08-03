package yuuto.quantumelectronics.tile.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import yuuto.quantumelectronics.ref.ModTextures;

public class RendererModelPylon {
	static IModelCustom model = AdvancedModelLoader.loadModel(ModTextures.MODEL_PYLON);
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
        //GL11.glColor4b(red, green, blue, alpha);
		model.renderAll();
		GL11.glDisable(GL11.GL_BLEND);
		//GL11.glDisable(GL11.GL_LIGHT2);
		GL11.glPopMatrix();
	}
}
