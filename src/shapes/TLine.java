package shapes;

import java.awt.geom.Line2D;

public class TLine extends TShape{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TLine() {
    	this.shape = new Line2D.Double();
    }
    
	public TShape newTShape() {
		return new TLine();	
	}
    
    public void prepareDrawing(int x, int y) {
		Line2D line = (Line2D)this.shape;
		line.setLine(x, y, x, y);
    }

    public void keepDrawing(int x2, int y2) {
		Line2D line = (Line2D)this.shape;
		line.setLine(line.getX1(), line.getY1(), x2, y2);	
    }

	@Override
	public void finishDrawing(int x, int y) {
		// TODO Auto-generated method stub
		if(this.checkingArea()){
			Line2D line = (Line2D)this.shape;
			line.setLine(line.getX1(), line.getY1(), line.getX1()+defaultX, line.getY1()+defaultY);	
		}
	}

	@Override
	public void setLocation(int x, int y) {

	}

}
