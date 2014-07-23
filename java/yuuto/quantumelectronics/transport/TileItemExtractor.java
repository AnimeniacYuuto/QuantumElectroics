package yuuto.quantumelectronics.transport;

import yuuto.quantumelectronics.transport.handler.ITransportConnection;
import yuuto.quantumelectronics.transport.handler.ITransportHandler;
import yuuto.quantumelectronics.transport.inventory.ItemExtractor;
import yuuto.quantumelectronics.transport.wrapper.TransportStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileItemExtractor extends TileTransportNode{
	
	IInventory target;
	
	@Override
	public boolean canConnect(ForgeDirection from) {
		return from == facing.getOpposite();
	}
	
	@Override
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)
			return;
		if(target == null || connections[0] == null ||
				!(connections[0] instanceof ITransportHandler))
			return;
		ItemExtractor.extractItems(target, (ITransportHandler)connections[0], facing);
	}

}
