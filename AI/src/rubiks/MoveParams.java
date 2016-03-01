package rubiks;

public class MoveParams
{
	private int sliceNum;
	private Axis axis;
	private Direction dir;
	
	public MoveParams(int sliceNum, Axis axis, Direction dir) {
		this.sliceNum = sliceNum;
		this.axis = axis;
		this.dir = dir;
	}
	
	public int getSliceNum() {
		return sliceNum;
	}
	public void setSliceNum(int sliceNum) {
		this.sliceNum = sliceNum;
	}
	public Axis getAxis() {
		return axis;
	}
	public void setAxis(Axis axis) {
		this.axis = axis;
	}
	public Direction getDirection() {
		return dir;
	}
	public void setDir(Direction dir) {
		this.dir = dir;
	}
}
