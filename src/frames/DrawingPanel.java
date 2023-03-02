package frames;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.util.Stack;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import global.Constants.ETools;
import global.Constants.ETransformationStyle;
import shapes.TAnchors.EAnchors;
import shapes.TSelection;
import shapes.TShape;
import shapes.TText;
import transformer.Drawer;
import transformer.Mover;
import transformer.Resizer;
import transformer.Rotator;
import transformer.Transformer;


public class DrawingPanel extends JPanel implements Printable{
	//attribute
	private static final long serialVersionUID = 1L;
	
	private boolean updated;

	//components
	private Vector<TShape> shapes;
	private Stack<Object> undoStack;
	private Stack<Object> redoStack;


//	private BufferedImage bufferedImage;
//	public BufferedImage getBufferedImage() {
//		return bufferedImage;
//	}

//	public void setBufferedImage(Object bufferedImage) {
//		this.bufferedImage = (BufferedImage) bufferedImage;
//	}

//	private Graphics2D graphics2DBufferedImage;

	
	private enum EDrawingState{
		eIdle,
		e2PointDrawing,
		eNPointDrawing,
		eCanvasSizing
	} 
	private EDrawingState eDrawingState;
	private ETools selectedTool;
	private TShape currentShape;
	private TShape selectedShape;
	private ToolBar toolBar;
	private Transformer transformer;
//	private Ellipse2D canvasAnchor;
	private TShape copyShape;
	private Color lineColor;
	private Color fillColor;

	public DrawingPanel() {
		//attribute
		this.eDrawingState = EDrawingState.eIdle;
		this.setBackground(Color.white);
		this.updated = false;

		//components
		this.lineColor = null;
		this.fillColor = null;
		this.shapes = new Vector<TShape>();
		this.undoStack = new Stack<Object>();
		this.redoStack = new Stack<Object>();
//		this.undoStack.push(this.copyShapes());
//		this.historyIndex = 1;
//		this.canvasAnchor = new Ellipse2D.Double();
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.addMouseWheelListener(mouseHandler);	
	}
	
	public void initialize() {
//		this.bufferedImage = (BufferedImage) this.createImage(this.getWidth(), this.getHeight());
//		this.graphics2DBufferedImage = (Graphics2D) this.bufferedImage.getGraphics();
//		this.setCanvasAnchor(this.getWidth(), this.getHeight());
	}
	
	public void association(ToolBar toolBar) {
		this.toolBar = toolBar;
	}
	
	public void clearAll() {
		this.shapes.clear();
		this.repaint();
	}
	
	public void print() {
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		printerJob.setPrintable((Printable) this);
		boolean isPrinted = printerJob.printDialog();
		if(isPrinted) {
			try {
				printerJob.print();
			} catch(PrinterException e) {
				JOptionPane.showMessageDialog(this, "프린트가 불가능합니다.");
			}
		}
	}
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if(pageIndex>0) return NO_SUCH_PAGE;
		Graphics2D graphics2d = (Graphics2D)graphics;
		graphics2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		for (TShape shape : this.shapes) {
			shape.draw(graphics2d);
		}
		return PAGE_EXISTS;
	}
	
	public void saveCurrentStatus(){
		this.redoStack.clear();
		this.undoStack.push(copyShapes());
	}
	public void undo() {
		if(!undoStack.isEmpty()){
			this.redoStack.push(this.copyShapes());
			this.setShapes(this.undoStack.pop());
			setUpdated(true);
		}
	}
	
	public void redo() {
		if(!redoStack.isEmpty()) {
			this.undoStack.push(this.copyShapes());
			this.setShapes(this.redoStack.pop());
			setUpdated(true);
		}
	}
	
	public Object copyShapes(){
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(this.shapes);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			return objectInputStream.readObject();
		}
		catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Object copyShape(TShape shape){
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(shape);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			return objectInputStream.readObject();
		}
		catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void cut() {
		if(this.selectedShape!=null){
			this.copy();
			this.shapes.remove(this.selectedShape);
			this.repaint();
			this.setUpdated(true);
		}

	}

	public void copy() {
		if(this.selectedShape!=null) {
			this.copyShape = (TShape) this.copyShape(this.selectedShape);
		}
	}

	public void paste() {
		if(this.copyShape != null) {
			this.deselectAllShapes();
			this.copyShape = (TShape) this.copyShape(this.copyShape);
			this.copyShape.getAffineTransform().translate(5, 5);
			this.shapes.add(this.copyShape);
			this.selectShape(this.copyShape);
			this.setUpdated(true);
			repaint();

		}
	}
	
	public boolean isUpdated() {
		return this.updated;
	}

	public void setUpdated(boolean updated) {

		this.updated = updated;
	}

	@SuppressWarnings("unchecked")
	public void setShapes(Object shapes) {
		this.shapes = (Vector<TShape>) shapes;
		this.repaint();
	}
	
	public Object getShapes() {
		return this.shapes;
	}
	
	public TShape getSelectedShape() {
		return this.selectedShape;
	}
	
	public void setSelectedTool(ETools selectedTool) {
		this.selectedTool = selectedTool;
	}

	public void selectShape(TShape shape){
		this.selectedShape = shape;
		this.selectedShape.setSelected(true);
	}

	public void deselectShape(){
		this.selectedShape.setSelected(false);
		this.selectedShape = null;
	}

	public void deselectAllShapes(){
		for(TShape shape : this.shapes) {
			if(shape.isSelected()) {
				shape.setSelected(false);
			}
		}
	}

	public ETools getSelectedTool() {
		return this.selectedTool;
	}
		
	public void paint(Graphics graphics) {
//		super.paint(graphics);
//		this.graphics2DBufferedImage.clearRect(0, 0,
//				this.bufferedImage.getWidth(), this.bufferedImage.getHeight());
//		for(TShape shape : this.shapes) {
//			shape.draw(this.graphics2DBufferedImage);
//		}
//		graphics.drawImage(this.bufferedImage, 0, 0, this);

		super.paint(graphics);
		for(TShape shape : this.shapes) {
				shape.draw((Graphics2D)graphics);
		}
		if (this.currentShape != null) {
			this.currentShape.draw((Graphics2D)graphics);
		}
	}

//	public void resizeCanvas(int x, int y) {
//		this.bufferedImage = (BufferedImage) createImage(x, y);
//		this.graphics2DBufferedImage = (Graphics2D) this.bufferedImage.getGraphics();
//		repaint();
//	}
//
//	public void setCanvasAnchor(int x, int y) {
//		this.canvasAnchor.setFrame(x-10, y-10, 20, 20);
//	}
	
	private void prepareTransforming(int x, int y) {
		if(this.selectedTool == ETools.eSelection) {
			this.currentShape = onShape(x, y);
			if(this.currentShape != null) {
				EAnchors eAnchor = this.currentShape.getSelectedAnchor();
				if(eAnchor==EAnchors.eMove) { 
					this.transformer = new Mover(this.currentShape);
				}else if(eAnchor==EAnchors.eRR){
					this.transformer = new Rotator(this.currentShape);
				}else {
					this.transformer = new Resizer(this.currentShape);
				}
				this.saveCurrentStatus();
			} else {	
				this.currentShape = this.selectedTool.newShape();
//				this.currentShape.setLineColor(this.lineColor);
//				this.currentShape.setFillColor(this.fillColor);
				this.transformer = new Drawer(this.currentShape);
			}
		} else {
			this.saveCurrentStatus();
			this.currentShape = this.selectedTool.newShape();
			this.transformer = new Drawer(this.currentShape);
		}
		if(this.selectedShape != null) {
			this.deselectShape();
		}
		this.deselectAllShapes();
		this.transformer.prepare(x, y);
//		this.getGraphics().setXORMode(getBackground());
//		repaint();
	}
	
	private void keepTransforming(int x, int y) {
		
		//erase
//		this.currentShape.draw((Graphics2D)this.getGraphics());
//		//transform
//		this.transformer.keepTransforming(x, y);
//		//draw
//		this.currentShape.draw((Graphics2D)this.getGraphics());
//		this.getGraphics().drawImage(this.bufferedImage, 0, 0, this);
//		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		this.transformer.keepTransforming(x, y);
		this.repaint();
	}
	
	private void continueTransforming(int x, int y) {
		this.currentShape.addPoint(x,y);
	}
	
	private void finishTransforming(int x, int y) {
//		this.graphics2DBufferedImage.setPaintMode();
//		this.getGraphics().setPaintMode();
		this.transformer.finalize(x, y);
		if(!(this.currentShape instanceof TSelection) &&
				!(this.currentShape.checkingArea())) {
			
			if(this.selectedTool!=ETools.eSelection) {
				this.shapes.add(this.currentShape);
			}
			this.toolBar.defaultButton();
			this.setUpdated(true);
			this.selectShape(this.currentShape);
		}
		if(this.currentShape instanceof TSelection) {
			for(TShape shape : this.shapes) {
				if(((TSelection) this.currentShape).inShape(shape.getBounds())) {
					shape.setSelected(true);
				}
			}
		}
		this.currentShape = null;
		this.repaint();
	}
	
	private TShape onShape(int x, int y){
		for(TShape shape: this.shapes) {
			if(shape.contains(x, y)) {
				return shape;
			}
		}		
		return null;
	}
	
//	private void changeSelection(int x, int y) {
//		if( this.selectedShape != null) {
//		    this.selectedShape.setSelected(false);
//		}
//		// draw anchors
//		this.selectedShape = this.onShape(x, y);
//		if(this.selectedShape != null) {
//			this.selectedShape.setSelected(true);
//		}
//		repaint();
//	}
	
	private void changeCursor(int x, int y) {
		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		if(this.selectedTool == ETools.eSelection ||
				this.selectedTool == ETools.eEraser ||
				this.selectedTool == ETools.eFill ||
				this.selectedTool == ETools.ePaint) {
			cursor = new Cursor(Cursor.DEFAULT_CURSOR);				
			TShape shapeBelow = this.onShape(x, y);
			if(shapeBelow != null) {
				cursor = new Cursor(Cursor.MOVE_CURSOR);
				if(shapeBelow.isSelected()) {
					EAnchors eAnchor = shapeBelow.getSelectedAnchor();
					switch(eAnchor) {
						case eNW: 				
							cursor = new Cursor(Cursor.NW_RESIZE_CURSOR);
							break;
						case eWW: 				
							cursor = new Cursor(Cursor.W_RESIZE_CURSOR);
							break;
						case eSW: 				
							cursor = new Cursor(Cursor.SW_RESIZE_CURSOR);
							break;
						case eSS: 				
							cursor = new Cursor(Cursor.S_RESIZE_CURSOR);
							break;
						case eSE: 				
							cursor = new Cursor(Cursor.SE_RESIZE_CURSOR);
							break;
						case eEE: 				
							cursor = new Cursor(Cursor.E_RESIZE_CURSOR);
							break;
						case eNE: 				
							cursor = new Cursor(Cursor.NE_RESIZE_CURSOR);
							break;
						case eNN: 				
							cursor = new Cursor(Cursor.N_RESIZE_CURSOR);
							break;
						case eRR: 				
							cursor = new Cursor(Cursor.HAND_CURSOR);
							break;
						default :
							break;
					}
				}
			}
		}
//		if(this.canvasAnchor.contains(x, y)) {
//			cursor = new Cursor(Cursor.NW_RESIZE_CURSOR);
//		}
		this.setCursor(cursor);
	}
	
	public void erase(int x, int y) {
		this.saveCurrentStatus();
		TShape shape = onShape(x, y);
		if(shape!=null){
			shapes.remove(shape);
			repaint();
			this.setUpdated(true);
		}

	}
	
	private class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==MouseEvent.BUTTON1) {
				if(e.getClickCount() == 1) {
					lButtonClick(e);
				} else if(e.getClickCount() == 2) {
					lButtonDoubleClick(e);
				}
			}
		}

		private void lButtonClick(MouseEvent e) {
			if(eDrawingState == EDrawingState.eIdle) {
//				changeSelection(e.getX(), e.getY());
				if(selectedTool.getTransformationStyle() == ETransformationStyle.eNPTransformation) {
					prepareTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPointDrawing;				
				}else if(selectedTool.getTransformationStyle() == ETransformationStyle.eErase) {
					erase(e.getX(), e.getY());
				}
			}else if(eDrawingState == EDrawingState.eNPointDrawing) { 				
				continueTransforming(e.getX(), e.getY());
			}
		}


		private void lButtonDoubleClick(MouseEvent e) {
			if(eDrawingState == EDrawingState.eNPointDrawing) { 
				finishTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;				
			}
			if(eDrawingState == EDrawingState.eIdle) {
				if(selectedShape instanceof TText) {
					String text = JOptionPane.showInputDialog("텍스트를 입력하세요");
					if(text != null) {
						((TText)selectedShape).setText(text);
					}
					repaint();
				}
			}
		}


		@Override
		public void mouseMoved(MouseEvent e) {
			if(eDrawingState == EDrawingState.eNPointDrawing) {
				keepTransforming(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eIdle) {
				changeCursor(e.getX(), e.getY());
			}
			
		}
		



		@Override
		public void mousePressed(MouseEvent e) {	
			if(e.getButton()!=MouseEvent.BUTTON1) return;
			if(eDrawingState == EDrawingState.eIdle) {
//				if(canvasAnchor.contains(e.getX(), e.getY())) {
//					eDrawingState = EDrawingState.eCanvasSizing;
//					return;
//				}
				if(selectedTool.getTransformationStyle() == ETransformationStyle.e2PTransformation) {
					prepareTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2PointDrawing;
				} 
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			
			if(eDrawingState == EDrawingState.e2PointDrawing) {
				keepTransforming(e.getX(), e.getY());
			}
			if(eDrawingState==EDrawingState.eCanvasSizing) {
//				resizeCanvas(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {			
			if(eDrawingState == EDrawingState.e2PointDrawing) {
				finishTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;							
			}
			if(eDrawingState==EDrawingState.eCanvasSizing) {
//				setCanvasAnchor(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;							
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			
		}
		
	}

	public void setLineColor(Color lineColor) {
		if(this.selectedShape!=null){
			this.saveCurrentStatus();
			this.selectedShape.setLineColor(lineColor);
			repaint();
			this.setUpdated(true);
		}
	}

	public void setFillColor(Color fillColor) {
		if(this.selectedShape!=null) {
			this.saveCurrentStatus();
			this.selectedShape.setFillColor(fillColor);
			repaint();
			this.setUpdated(true);
		}
	}
}
