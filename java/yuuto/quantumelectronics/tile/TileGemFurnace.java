package yuuto.quantumelectronics.tile;

import yuuto.quantumelectronics.tile.base.IMachine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileGemFurnace extends TileEntityFurnace implements IMachine{

	protected ForgeDirection orientation = ForgeDirection.getOrientation(2);
	protected boolean isActive;
	String unlocName = "GemFurnace.";
	int maxCookTime = 160;
	boolean needsUpdate = true;
	public TileGemFurnace(){
		super();
	}
	public TileGemFurnace(int meta){
		super();
		switch(meta){
		default:
		case 0:
			unlocName+="Paridot";
			break;
		case 1:
			unlocName+="Ruby";
			break;
		case 2:
			unlocName+="Sapphire";
			break;
		}
	}
	
	public ForgeDirection getOrientation(){
		return orientation;
	}
	public ForgeDirection setOrientation(ForgeDirection dir){
		orientation = dir;
		return orientation;
	}
	public ForgeDirection rotateAround(ForgeDirection axis){
		orientation = orientation.getRotation(axis);
		return orientation;
	}
	public boolean isActive(){
		return isActive;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int i1)
    {
        return this.furnaceCookTime * i1 / maxCookTime;
    }
	
	@Override
	public void updateEntity(){
		if(needsUpdate){
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		
		boolean flag = this.furnaceBurnTime > 0;
        boolean flag1 = false;

        if (this.furnaceBurnTime > 0)
        {
            --this.furnaceBurnTime;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.furnaceBurnTime != 0 || this.getStackInSlot(1) != null && this.getStackInSlot(0) != null)
            {
                if (this.furnaceBurnTime == 0 && this.canSmelt())
                {
                    this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.getStackInSlot(1));

                    if (this.furnaceBurnTime > 0)
                    {
                        flag1 = true;

                        if (this.getStackInSlot(1) != null)
                        {
                            --this.getStackInSlot(1).stackSize;

                            if (this.getStackInSlot(1).stackSize == 0)
                            {
                                this.setInventorySlotContents(1, getStackInSlot(1).getItem().getContainerItem(getStackInSlot(1)));
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt())
                {
                    ++this.furnaceCookTime;

                    if (this.furnaceCookTime == maxCookTime)
                    {
                        this.furnaceCookTime = 0;
                        this.smeltItem();
                        flag1 = true;
                    }
                }
                else
                {
                    this.furnaceCookTime = 0;
                }
            }

            if (flag != this.furnaceBurnTime > 0)
            {
                flag1 = true;
                isActive = this.furnaceBurnTime > 0;
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
	}
    protected boolean canSmelt()
    {
        if (this.getStackInSlot(0) == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.getStackInSlot(0));
            if (itemstack == null) return false;
            if (this.getStackInSlot(2) == null) return true;
            if (!this.getStackInSlot(2).isItemEqual(itemstack)) return false;
            int result = getStackInSlot(2).stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.getStackInSlot(2).getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }
    @Override
    public void readFromNBT(NBTTagCompound nbt){
    	super.readFromNBT(nbt);
    	unlocName = nbt.getString("GemFurnaceName");
    	isActive = nbt.getBoolean("IsActive");
    	orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
    	this.markDirty();
    }
    @Override
    public void writeToNBT(NBTTagCompound nbt){
    	super.writeToNBT(nbt);
    	nbt.setString("GemFurnaceName", unlocName);
    	nbt.setBoolean("IsActive", isActive);
    	nbt.setInteger("Orientation", orientation.ordinal());
    }
    @Override
    public Packet getDescriptionPacket(){
    	NBTTagCompound nbt = new NBTTagCompound();
    	nbt.setString("GemFurnaceName", unlocName);
    	nbt.setBoolean("IsActive", isActive);
    	nbt.setInteger("Orientation", orientation.ordinal());
    	return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
    }
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt){
    	NBTTagCompound nbt = pkt.func_148857_g();
    	if(nbt == null || nbt.hasNoTags())
    		return;
    	unlocName = nbt.getString("GemFurnaceName");
    	isActive = nbt.getBoolean("IsActive");
    	orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
    }
    
    @Override
    public String getInventoryName()
    {
        return unlocName;
    }
    
    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }
    @Override
    public void markDirty(){
    	super.markDirty();
    	needsUpdate = true;
    }
}
