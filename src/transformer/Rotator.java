package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import shapes.TShape;

public class Rotator extends Transformer {
	
	private double rotateAngle;

	public Rotator(TShape selectedShape) {
		super(selectedShape);
	}

	@Override
	public void prepare(int x, int y) {
		this.px = x; this.py = y;
		this.rx = (int) this.selectedShape.getBounds().getCenterX();
		this.ry = (int) this.selectedShape.getBounds().getCenterY();
	}

	@Override
	public void keepTransforming(int x, int y) {
		
		Point2D.Double startPoint = new Point2D.Double(this.px, this.py);
		Point2D.Double centerPoint = new Point2D.Double(this.rx, this.ry);
		Point2D.Double endPoint = new Point2D.Double(x,y);
    	double startAngle = Math.toDegrees(Math.atan2(startPoint.getY() - centerPoint.getY(), startPoint.getX()-centerPoint.getX()));
    	double endAngle = Math.toDegrees(Math.atan2(endPoint.getY()-centerPoint.getY(), endPoint.getX()-centerPoint.getX()));
    	double angle = endAngle - startAngle;   	
    	if(angle<0) {
    		angle = angle + 360;
    		}		
    	rotateAngle = Math.toRadians(angle);
    	this.affineTransform.rotate(rotateAngle, this.rx, this.ry);
    	this.px=x;
    	this.py=y;   	
	}
	
	@Override
	public void finalize(int x, int y) {
		this.selectedShape.setShape(this.affineTransform.createTransformedShape(this.selectedShape.getShape()));
		this.affineTransform.setToIdentity();	

	}
}