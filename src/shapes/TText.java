package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;

public class TText extends TRectangle{

	private static final long serialVersionUID = 1L;
	
	private String text;
	private Font font;


	public TText() {
		this.text = "더블 클릭하여 텍스트를 입력하세요.";
		this.font =  new Font("맑은 고딕", Font.PLAIN, 15);
	}
	
	@Override
	public TShape newTShape() {
		// TODO Auto-generated method stub
		return new TText();
	}
	
	public String getText(String text) {
		// TODO Auto-generated method stub
		return this.text;
	}
	
	public void setText(String text) {
		// TODO Auto-generated method stub
		this.text = text;
	}
	
	public void draw (Graphics2D graphics) {
		graphics.setFont(this.font);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape);
		Color foreground = graphics.getColor();
		graphics.setColor(graphics.getBackground());
		graphics.draw(transformedShape);	
		graphics.setColor(foreground);
		if(!this.checkingArea())
			graphics.drawString(this.text, ((int)(transformedShape.getBounds().getX()+5.5)),
					((int)(transformedShape.getBounds().getCenterY()+5.5)));
		if(this.bSelected) {
			this.anchors.draw(graphics, transformedShape.getBounds());
		}
	}

}
