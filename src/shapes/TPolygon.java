package shapes;

import java.awt.Polygon;

public class TPolygon extends TShape {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TPolygon() {
		this.shape = new Polygon();
	}

	public void prepareDrawing(int x, int y) {
		this.addPoint(x, y);
		this.addPoint(x, y);
	}
	
	public void addPoint(int x, int y) {
		Polygon polygon = (Polygon)this.shape;
		polygon.addPoint(x, y);
	}

	@Override
	public void setLocation(int x, int y) {

	}

	@Override
	public void keepDrawing(int x, int y) {
		Polygon polygon = (Polygon)this.shape;
		polygon.xpoints[polygon.npoints-1] = x;
		polygon.ypoints[polygon.npoints-1] = y;
	}

	@Override
	public TShape newTShape() {
		return new TPolygon();
	}

	@Override
	public void finishDrawing(int x, int y) {
		// TODO Auto-generated method stub
		
	}




}
