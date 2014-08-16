package yuuto.quantumelectronics.gui;

import org.lwjgl.opengl.GL11;

import yuuto.quantumelectronics.gui.container.ContainerModule;
import yuuto.quantumelectronics.ref.References;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiModule extends GuiContainer{

	ItemStack module;
	static ResourceLocation texture = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/gui/filterBasic.png");
	public GuiModule(ItemStack stack, EntityPlayer player) {
		super(new ContainerModule(stack, player));
		this.module = stack;
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void initGui(){
		super.initGui();
		if(module.getItemDamage() > 1 && module.getItemDamage() != 5 &&
				module.getItemDamage() != 6){
			this.buttonList.add(new GuiButton(0, this.guiLeft+7, this.guiTop+56, 18, 18, "W"));
		}
		if(module.getItemDamage() > 1 && module.getItemDamage() <= 6){
			this.buttonList.add(new GuiButton(1, this.guiLeft+7+18, this.guiTop+56, 18, 18, "Ore"));
			this.buttonList.add(new GuiButton(2, this.guiLeft+7+36, this.guiTop+56, 18, 18, "Meta"));
			this.buttonList.add(new GuiButton(3, this.guiLeft+7+54, this.guiTop+56, 18, 18, "NBT"));
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_,
			int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(texture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void actionPerformed(GuiButton button){
		super.actionPerformed(button);
		switch(button.id){
		case 0:
			button.displayString = button.displayString == "W" ? "B" : "W";
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		default:
			break;
		}
	}

}
