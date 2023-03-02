package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import java.io.Serializable;

import shapes.TAnchors.EAnchors;

abstract public class TShape implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected final static int defaultX = 200;
	protected final static int defaultY = 200;
	protected final static int defaultW = 5;
	protected final static int defaultH = 5;
	protected Shape shape;
	protected TAnchors anchors;	
	protected AffineTransform affineTransform;
	protected boolean bSelected;
	private Color lineColor;
	private Color fillColor;
	
	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	public boolean isSelected() {
		return this.bSelected;
	}
	
	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}
	
	public EAnchors getSelectedAnchor() {
		return this.anchors.geteSelectedAnchor();
	}

	public void setSelectedAnchor(EAnchors eSelectedAnchor) {
		this.anchors.seteSelectedAnchor(eSelectedAnchor);;
	}
	
	public AffineTransform getAffineTransform() {
		// TODO Auto-generated method stub
		return this.affineTransform;
	}
	
	public void setAffineTransform(AffineTransform affineTransform) {
		// TODO Auto-generated method stub
		this.affineTransform = affineTransform;
	}
	
	public TAnchors getAnchors() {
		// TODO Auto-generated method stub
		return this.anchors;
	}

	public TShape() {
		this.bSelected = false;
		this.affineTransform = new AffineTransform();
		this.affineTransform.setToIdentity();
		this.anchors = new TAnchors();
	}


	public abstract TShape newTShape();

	
	public abstract void prepareDrawing(int x, int y);
	public abstract void keepDrawing(int x, int y);
	public abstract void finishDrawing(int x, int y);

	public void addPoint(int x, int y) {}

	public abstract void setLocation(int x, int y) ;

	public boolean contains(int x, int y) {
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		if(this.bSelected) {
			 if(this.anchors.contains(x, y)) {
				 return true;
			 }
		}
		if(transformedShape.getBounds().contains(x, y)){
			this.anchors.seteSelectedAnchor(EAnchors.eMove);
			return true;
		}	
		return false;
	}
	
	public Rectangle getBounds() {
		Rectangle rectangle = this.shape.getBounds();
		return rectangle;
	}
			
	public void draw (Graphics2D graphics) {		
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		Color foreground = graphics.getColor();
		if(this.fillColor != null) {
			graphics.setColor(fillColor);
			graphics.fill(transformedShape);
			graphics.setColor(foreground);
		}
		if(this.lineColor != null) {
			graphics.setColor(lineColor);
		}
		graphics.draw(transformedShape);
		graphics.setColor(foreground);
		if(this.bSelected) {
			this.anchors.draw(graphics, transformedShape.getBounds());
		}
	}
	public boolean checkingArea() {
		if(this.getBounds().width<=defaultW && this.getBounds().height<=defaultH){
			return true;
		}
		return false;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
}

