package shapes;

import java.awt.geom.Ellipse2D;

public class TOval extends TShape{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TOval() {
    	this.shape = new Ellipse2D.Double();
    }
    
    public void prepareDrawing(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D)this.shape;
		ellipse.setFrame(x, y, 0, 0);
    }

    public void keepDrawing(int x, int y) {
    	Ellipse2D ellipse = (Ellipse2D) this.shape;
    	ellipse.setFrame(ellipse.getX(), ellipse.getY(), x - ellipse.getX(), y - ellipse.getY());
    }

	@Override
	public TShape newTShape() {
		return new TOval();
	}

	@Override
	public void finishDrawing(int x, int y) {
		// TODO Auto-generated method stub
		if(this.checkingArea()){
	    	Ellipse2D ellipse = (Ellipse2D) this.shape;
	    	ellipse.setFrame(ellipse.getX(), ellipse.getY(), defaultX, defaultY);
		}
	}

	@Override
	public void setLocation(int x, int y) {

	}

}
