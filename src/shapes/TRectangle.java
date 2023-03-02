package shapes;

import java.awt.Rectangle;

public  class TRectangle extends TShape {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5361794345439001413L;

	public TRectangle() {
		this.shape = new Rectangle();
	}
	
	@Override
	public TShape newTShape() {
		return new TRectangle();
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
		if(this.checkingArea()){
			Rectangle rectangle = (Rectangle) this.shape;
			rectangle.setSize(defaultX, defaultY);
		}
	}

	@Override
	public void setLocation(int x, int y) {

	}


}
