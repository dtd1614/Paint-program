package menus;
import java.awt.Cursor;
import java.awt.Event;
import java.awt.event.*;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import frames.DrawingPanel;
import global.Constants.EEditMenu;

public class EditMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	private DrawingPanel drawingPanel;


	@SuppressWarnings("deprecation")
	public EditMenu(String s) {
		super(s);
		
		ActionHandler actoinHandler = new ActionHandler();

		for(EEditMenu eMenuItem : EEditMenu.values()){
			JMenuItem menuItem = new JMenuItem(eMenuItem.getLabel());
			menuItem.setToolTipText(eMenuItem.getLabel());
			menuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
			menuItem.addActionListener(actoinHandler);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(eMenuItem.getShortcutKey(), Event.CTRL_MASK));
			menuItem.setActionCommand(eMenuItem.name());
			this.add(menuItem);
		}
		
	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	public void initialize() {
		
	}
	
	private void undo() {
		this.drawingPanel.undo();
	}
	
	private void redo() {
		this.drawingPanel.redo();
	}
	
	private void cut() {
		this.drawingPanel.cut();
	}
	
	private void copy() {
		this.drawingPanel.copy();
	}

	private void paste() {
		this.drawingPanel.paste();
	}
	
	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals(EEditMenu.eUndo.name())) {
				undo();
			} else if (e.getActionCommand().equals(EEditMenu.eRedo.name())) {
				redo();
			} else if (e.getActionCommand().equals(EEditMenu.eCut.name())) {
				cut();
			} else if (e.getActionCommand().equals(EEditMenu.eCopy.name())) {
				copy();
			} else if (e.getActionCommand().equals(EEditMenu.ePaste.name())) {
				paste();
			}
			
		}
	}
}
