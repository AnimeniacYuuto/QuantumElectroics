package yuuto.quantumelectronics.ref;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

public class ModTextures {
	private static final String ID = References.MOD_ID.toLowerCase();
	
	public static final ResourceLocation MODEL_GEM = 
			new ResourceLocation(ID, "models/Gem.obj");
	
	public static final ResourceLocation MODEL_PYLON_UP = 
			new ResourceLocation(ID, "models/PylonUp.obj");
	public static final ResourceLocation MODEL_PYLON_DOWN = 
			new ResourceLocation(ID, "models/PylonDown.obj");
	public static final ResourceLocation MODEL_PYLON_NORTH = 
			new ResourceLocation(ID, "models/PylonNorth.obj");
	public static final ResourceLocation MODEL_PYLON_SOUTH = 
			new ResourceLocation(ID, "models/PylonSouth.obj");
	public static final ResourceLocation MODEL_PYLON_EAST = 
			new ResourceLocation(ID, "models/PylonEast.obj");
	public static final ResourceLocation MODEL_PYLON_WEST = 
			new ResourceLocation(ID, "models/PylonWest.obj");
	
	public static final ResourceLocation MODEL_NODE_UP =
			new ResourceLocation(ID, "models/NodeUp.obj");
	public static final ResourceLocation MODEL_NODE_DOWN =
			new ResourceLocation(ID, "models/NodeDown.obj");
	public static final ResourceLocation MODEL_NODE_NORTH =
			new ResourceLocation(ID, "models/NodeNorth.obj");
	public static final ResourceLocation MODEL_NODE_SOUTH =
			new ResourceLocation(ID, "models/NodeSouth.obj");
	public static final ResourceLocation MODEL_NODE_EAST =
			new ResourceLocation(ID, "models/NodeEast.obj");
	public static final ResourceLocation MODEL_NODE_WEST =
			new ResourceLocation(ID, "models/NodeWest.obj");
	
	public static final ResourceLocation TEXT_GEM = 
			new ResourceLocation(ID, "textures/uvs/Gem.png");
	public static final ResourceLocation TEXT_NODE_OUTSIDE = 
			new ResourceLocation(ID, "textures/uvs/NodeOutside.png");
	public static final ResourceLocation TEXT_NODE_INSIDE = 
			new ResourceLocation(ID, "textures/uvs/NodeInside.png");
}
