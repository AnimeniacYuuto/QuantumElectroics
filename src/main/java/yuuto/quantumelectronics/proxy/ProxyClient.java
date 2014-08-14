package yuuto.quantumelectronics.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import yuuto.quantumelectronics.ModBlocks;
import yuuto.quantumelectronics.ModItems;
import yuuto.quantumelectronics.handlers.ConfigHandler;
import yuuto.quantumelectronics.items.renderer.RendererItemGem;
import yuuto.quantumelectronics.tile.TilePylon;
import yuuto.quantumelectronics.tile.renderer.RendererItemPylon;
import yuuto.quantumelectronics.tile.renderer.RendererTilePylon;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ProxyClient extends ProxyCommon{
	
	@Override
	public void preInit(FMLPreInitializationEvent event){
		super.preInit(event);
		MinecraftForgeClient.registerItemRenderer(ModItems.GEM, new RendererItemGem());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TilePylon.class, new RendererTilePylon());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.PYLON), new RendererItemPylon());
	}
}
