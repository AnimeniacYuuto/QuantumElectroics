package yuuto.quantumelectronics.gui;


import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import yuuto.quantumelectronics.ModItems;
import yuuto.quantumelectronics.gui.container.ContainerRequester;
import yuuto.quantumelectronics.gui.slot.SlotRequester;
import yuuto.quantumelectronics.gui.slot.SlotSelectedFluid;
import yuuto.quantumelectronics.ref.References;
import yuuto.quantumelectronics.transport.routing.ItemProviderStack;
import yuuto.quantumelectronics.transport.tile.TileRequester;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiRequester extends GuiContainer{

	static ResourceLocation texture = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/gui/requester.png");
	
	ContainerRequester container;
	List<SlotRequester> slots = new ArrayList<SlotRequester>();
	SlotSelectedFluid selectedFluid;
	String search;
	GuiTextField searchField;
	public GuiRequester(TileRequester tile, EntityPlayer player) {
		super(new ContainerRequester(tile, player));
		this.xSize = 198;
		this.ySize = 220;
		container = (ContainerRequester)this.inventorySlots;
		container.setGui(this);
		selectedFluid = new SlotSelectedFluid(container.fluidInventory, 0, 174, 156, this);
	}
	@Override
	public void initGui(){
		super.initGui();
		searchField = new GuiTextField(this.fontRendererObj, 84+guiLeft, 10+guiTop, 102, 8);
		searchField.setTextColor(0xffffff);
		searchField.setFocused(false);
		searchField.setEnableBackgroundDrawing(false);
		searchField.setMaxStringLength(200);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_,
			int mx, int my) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(texture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		selectedFluid.draw();
		searchField.drawTextBox();
        this.drawSlots(mx, my);
	}
	
	public void updateSlots(){
		//System.out.println("Updating slots");
		slots.clear();
		int i = 0;
		if(container.getNetInventory(search) != null)
		for(ItemProviderStack stack : container.getNetInventory(search)){
			if(stack.stackSize < 0){
				stack.stackSize*=-1;
			}
			slots.add(new SlotRequester(stack, this));
		}
	}
	public void mouseClicked(int mx, int my, int button){
		super.mouseClicked(mx, my, button);
		searchField.mouseClicked(mx, my, button);
		int mx2 = mx-this.guiLeft;
		int my2 = my-this.guiTop;
		int x = (int)Math.floor((mx2-8)/18f);
		int y = (int)Math.floor((my2-26)/18f);
		int i = y*9+x;
		if(i >= 0 && i < slots.size()){
			slots.get(i).onMouseClicked();
			return;
		}
		if(mx2 > 8 && mx2 < 168 && my2 > 26 && my2 < 132){
			this.container.insertItem();
		}
	}
	@Override
	public void keyTyped(char c, int i){
		if(searchField.isFocused()){
			searchField.textboxKeyTyped(c, i);
		}else{
			super.keyTyped(c, i);
		}
	}
	@Override
	public void updateScreen(){
		super.updateScreen();
		if((search == null && searchField.getText().length() > 0) || 
				search != this.searchField.getText().toLowerCase()){
			search = searchField.getText().toLowerCase();
			updateSlots();
		}
	}
	public void drawSlots(int mx, int my){
		//System.out.println("Drawing slots");
		GL11.glPushMatrix();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        //RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableGUIStandardItemLighting();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f / 1.0F, 240f / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        for(int y = 0; y < 6; y++){
			for(int x = 0; x < 9; x++){
				int i = y*9+x;
				int px = x*18+8+this.guiLeft;
				int py = y*18+26+this.guiTop;
				if(i < slots.size()){
					slots.get(i).draw(px, py, mx, my);
				}
				if(mx >= px && my >= py && mx <= px+16 && my <= py+16){
					GL11.glDisable(GL11.GL_LIGHTING);
		            GL11.glDisable(GL11.GL_DEPTH_TEST);
		            GL11.glColorMask(true, true, true, false);
		            drawGradientRect(px, py, px + 16, py + 16, -2130706433, -2130706433);
		            GL11.glColorMask(true, true, true, true);
		            GL11.glEnable(GL11.GL_LIGHTING);
		            GL11.glEnable(GL11.GL_DEPTH_TEST);
				}
			}
		}
		GL11.glPopMatrix();
	}

	public void drawItemStack(ItemStack stack, int x, int y, String text){
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        FontRenderer font = null;
        if (stack != null) font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = fontRendererObj;
        String s = text != null ? text : ""+stack.stackSize;
        itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), stack, x, y);
        itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), stack, x, y, s);
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
    }
	public ContainerRequester getContainer(){
		return container;
	}
	public int guiLeft(){
		return guiLeft;
	}
	public int guiTop(){
		return guiTop;
	}
}
