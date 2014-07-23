package yuuto.quantumelectronics;

import cpw.mods.fml.common.registry.GameRegistry;
import yuuto.quantumelectronics.blocks.base.ModBlockContainer;
import yuuto.quantumelectronics.items.base.ModItemBlockMulti;
import yuuto.quantumelectronics.transport.BlockConduit;
import yuuto.quantumelectronics.transport.BlockNode;

public class ModBlocks {
	public static final ModBlockContainer CONDUIT = new BlockConduit("Conduit");
	public static final ModBlockContainer NODE = new BlockNode(
			"ItemExtractorNode", "ItemReceiverNode");
	
	
	public static void init(){
		GameRegistry.registerBlock(CONDUIT, ModItemBlockMulti.class, "Conduit");
		GameRegistry.registerBlock(NODE, ModItemBlockMulti.class, "Node");
	}
}
