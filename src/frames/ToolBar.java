package frames;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import global.Constants.ETools;

public class ToolBar extends JToolBar {
	//attributes
	private static final long serialVersionUID = 1L;
	
	//association
	private DrawingPanel drawingPanel;
	
	public ToolBar() {
		
		//components
		ButtonGroup buttonGroup = new ButtonGroup();
		ActionHandler actionHandler = new ActionHandler();
		
		for(ETools eTool : ETools.values()) {
			JRadioButton toolButton = new JRadioButton(eTool.getUnselectdIcon());
			toolButton.setRolloverIcon(eTool.getSelectdIcon());
			toolButton.setSelectedIcon(eTool.getSelectdIcon());
			toolButton.setToolTipText(eTool.getLabel());
			toolButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			toolButton.setActionCommand(eTool.name());			
			toolButton.addActionListener(actionHandler);
			this.add(toolButton);
			buttonGroup.add(toolButton);
		}		
	}

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		this.defaultButton();
	}
	
	public void defaultButton() {
		JRadioButton defaultButton = (JRadioButton) this.getComponent(ETools.eSelection.ordinal());
		defaultButton.doClick();
	}
	
	private class ActionHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub			
			drawingPanel.setSelectedTool(ETools.valueOf(e.getActionCommand()));	

			if(drawingPanel.getSelectedTool()==ETools.ePaint) {
				Color lineColor = JColorChooser.showDialog(drawingPanel, "라인 색", drawingPanel.getForeground());
				drawingPanel.setLineColor(lineColor);
				defaultButton();
			}else if(drawingPanel.getSelectedTool()==ETools.eFill) {
				Color fillColor = JColorChooser.showDialog(drawingPanel, "채우기", drawingPanel.getForeground());
				drawingPanel.setFillColor(fillColor);
				defaultButton();
			}else if(drawingPanel.getSelectedShape()!=null) {
				if(drawingPanel.getSelectedShape().isSelected()) {
					drawingPanel.deselectShape();
					drawingPanel.repaint();
				}
			}
		}		
	}

	public void initialize() {
		// TODO Auto-generated method stub
		
	}
				
}

