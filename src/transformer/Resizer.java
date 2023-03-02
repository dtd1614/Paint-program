package transformer;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import shapes.TShape;
import shapes.TAnchors.EAnchors;

public class Resizer extends Transformer {
	
	protected double xScale, yScale;

	public Resizer(TShape selectedShape) {
		super(selectedShape);
	}

	@Override
	public void prepare(int x, int y) {

		Point2D resizeAnchorPoint = this.anchors.getResizeAnchorPoint(x,y);
		this.cx = resizeAnchorPoint.getX();
		this.cy = resizeAnchorPoint.getY();
		this.px = x;
		this.py = y;
	}

	@Override
	public void keepTransforming(int x, int y) {
		this.getResizeScale(x, y);
		this.affineTransform.translate(cx, cy);
		this.affineTransform.scale(this.xScale, this.yScale);
		this.affineTransform.translate(-cx, -cy);
		this.px = x;
		this.py = y;
	}

	@Override
	public void finalize(int x, int y) {
		this.selectedShape.setShape(this.affineTransform.createTransformedShape(this.selectedShape.getShape()));
		this.affineTransform.setToIdentity();
	}	

	
	private void getResizeScale(int x, int y) {
		EAnchors eResizeAnchor = this.anchors.geteResizeAnchor();
		double w1 = px-cx;
		double w2 = x-cx;
		double h1 = py-cy;
		double h2 = y-cy;
		
		switch(eResizeAnchor) {
			case eNW: xScale = w2/w1; 	yScale = h2/h1; break;
			case eWW: xScale = w2/w1; 	yScale = 1;		break;
			case eSW: xScale = w2/w1; 	yScale = h2/h1; break;
			case eSS: xScale = 1; 		yScale = h2/h1; break;
			case eSE: xScale = w2/w1; 	yScale = h2/h1; break;
			case eEE: xScale = w2/w1; 	yScale = 1; 	break;
			case eNE: xScale = w2/w1; 	yScale = h2/h1; break;
			case eNN: xScale = 1; 		yScale = h2/h1;	break;
			default: break;
		}
	}
	

}
