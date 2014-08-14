package yuuto.quantumelectronics.transport;

public class PositionInfo {
	public final int dim;
	public final int x;
	public final int y;
	public final int z;
	
	public PositionInfo(int dim, int x, int y, int z){
		this.dim = dim;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
