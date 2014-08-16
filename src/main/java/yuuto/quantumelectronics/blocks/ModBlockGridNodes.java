package yuuto.quantumelectronics.blocks;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import yuuto.quantumelectronics.QuantumElectronics;
import yuuto.quantumelectronics.blocks.base.ModBlockMachineMulti;
import yuuto.quantumelectronics.ref.References;
import yuuto.quantumelectronics.tile.TileGridFurnace;
import yuuto.quantumelectronics.tile.TileGridGenerator;
import yuuto.quantumelectronics.tile.TileGridGrinder;
import yuuto.quantumelectronics.transport.ITransportNode;
import yuuto.quantumelectronics.transport.tile.TileFluidRouter;
import yuuto.quantumelectronics.transport.tile.TileGridEnergyAcceptor;
import yuuto.quantumelectronics.transport.tile.TileGridEnergySync;
import yuuto.quantumelectronics.transport.tile.TileItemRouter;
import yuuto.quantumelectronics.transport.tile.TileRequester;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModBlockGridNodes extends ModBlockMachineMulti{
	
	public ModBlockGridNodes() {
		super("gridEnergyAcceptor", "gridEnergySync", "ItemExtractor", 
				"ItemReceiver", "ItemProvider", "ItemSupplierActive", 
				"ItemSupplierPassive", "FluidExtractor", "FluidReceiver", 
				"FluidProvider");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		switch(meta){
		case 0:
			return new TileGridEnergyAcceptor();
		case 1:
			return new TileGridEnergySync();
		case 2:
			return new TileItemRouter(0);
		case 3:
			return new TileItemRouter(1);
		case 4:
			return new TileItemRouter(2);
		case 5:
			return new TileItemRouter(3);
		case 6:
			return new TileItemRouter(4);
		case 7:
			return new TileFluidRouter(0);
		case 8:
			return new TileFluidRouter(1);
		case 9:
			return new TileFluidRouter(2);
		default:
			return null;
		}
	}
	@Override 
	public boolean onBlockActivated(World world, int x, int y, int z, 
			EntityPlayer player, int side, float eX, float eY, float eZ){
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null || player.isSneaking())
			return false;
		if(tile instanceof TileItemRouter){
			if(world.isRemote)
				return true;
			player.openGui(QuantumElectronics.instance, 5, world, x, y, z);
			return true;
		}
		if(tile instanceof TileFluidRouter){
			if(world.isRemote)
				return true;
			player.openGui(QuantumElectronics.instance, 7, world, x, y, z);
			return true;
			
		}
		return false;
	}
	@Override
	@SideOnly(Side.CLIENT)
	protected String getTextureName(int meta, int side){
		if(side == 1)
			return getTextureName(meta)+"FrontInactive";
		if(side == 2)
			return getTextureName(meta)+"FrontActive";
		return String.format("%s%s", References.TEXTURE_PREFIX, "gridMachine");
	}
	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z,
			int tileX, int tileY, int tileZ){
		super.onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null || !(tile instanceof ITransportNode))
			return;
		((ITransportNode)tile).onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
	}
	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l){
	   return false;
	}
	@Override
    public boolean isOpaqueCube(){
        return false;
    }
	@Override
	public int getLightOpacity(){
		return 0;
	}

	
	@Override
	public int determineOrientation(World world, int x, int y, int z, EntityLivingBase entity)
    {
		if (MathHelper.abs((float)entity.posX - (float)x) < 2.0F && MathHelper.abs((float)entity.posZ - (float)z) < 2.0F)
        {
            double d0 = entity.posY + 1.82D - (double)entity.yOffset;

            if (d0 - (double)y > 2.0D)
            {
                return 1;
            }

            if ((double)y - d0 > 0.0D)
            {
                return 0;
            }
        }
		return super.determineOrientation(world, x, y, z, entity);
    }
}
