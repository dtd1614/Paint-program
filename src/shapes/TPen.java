package shapes;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D.Float;

public class TPen extends TShape{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	public TPen() {
		this.shape = new GeneralPath();
	}

	@Override
	public TShape newTShape() {
		return new TPen();
	}

	@Override
	public void prepareDrawing(int x, int y) {
		((Float) this.shape).moveTo(x, y);
	}

	@Override
	public void keepDrawing(int x, int y) {
		((Float) this.shape).lineTo(x, y);

	}

	@Override
	public void finishDrawing(int x, int y) {

	}

	@Override
	public void setLocation(int x, int y) {

	}
}
