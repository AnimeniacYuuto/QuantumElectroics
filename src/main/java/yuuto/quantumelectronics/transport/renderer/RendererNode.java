package yuuto.quantumelectronics.transport.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import yuuto.quantumelectronics.ref.ModTextures;
import yuuto.quantumelectronics.tile.renderer.RendererModelPylon;

public class RendererNode {

	static IModelCustom modelUp = AdvancedModelLoader.loadModel(ModTextures.MODEL_NODE_UP);
	static IModelCustom modelDown = AdvancedModelLoader.loadModel(ModTextures.MODEL_NODE_DOWN);
	static IModelCustom modelNorth = AdvancedModelLoader.loadModel(ModTextures.MODEL_NODE_NORTH);
	static IModelCustom modelSouth = AdvancedModelLoader.loadModel(ModTextures.MODEL_NODE_SOUTH);
	static IModelCustom modelEast = AdvancedModelLoader.loadModel(ModTextures.MODEL_NODE_EAST);
	static IModelCustom modelWest = AdvancedModelLoader.loadModel(ModTextures.MODEL_NODE_WEST);
	static ResourceLocation textureOut = ModTextures.TEXT_NODE_OUTSIDE;
	static ResourceLocation textureIn = ModTextures.TEXT_NODE_INSIDE;
	
	public static void render(double transX, double transY, double transZ, 
			float scaleX, float scaleY, float scaleZ,
			int rot, int pass,
			int red, int green, int blue, int alpha){
		GL11.glPushMatrix();
		GL11.glScalef(scaleX, scaleY, scaleZ);
		GL11.glTranslated(transX, transY, transZ);
		GL11.glEnable(GL11.GL_BLEND);
        if(pass == 1){
        	Minecraft.getMinecraft().renderEngine.bindTexture(textureIn);
        }else{
        	Minecraft.getMinecraft().renderEngine.bindTexture(textureOut);
        }
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
		GL11.glPopMatrix();
	}
}
