package shapes;

import java.awt.*;

public  class TSelection extends TShape {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5361794345439001413L;

	public TSelection() {
		this.shape = new Rectangle();
	}
	
	@Override
	public TShape newTShape() {
		return new TSelection();
	}
	
	public void prepareDrawing(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setFrame(x, y, 0, 0);
	}
	
	public void keepDrawing(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setSize(x - rectangle.x, y - rectangle.y);

	}

	@Override
	public void finishDrawing(int x, int y) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setLocation(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.x = rectangle.x + 5;
		rectangle.y = rectangle.y + 5;
	}

	public boolean inShape(Rectangle shape) {
		return this.shape.contains(shape);
	}
	


}
