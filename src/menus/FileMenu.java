package menus;
import java.awt.Cursor;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import frames.DrawingPanel;
import global.Constants.EFileMenu;
import shapes.TShape;

public class FileMenu extends JMenu{

	private static final long serialVersionUID = 1L;

	private File file;
	private DrawingPanel drawingPanel;


	public FileMenu(String s) {		
		super(s);
		
		this.file = null;
		ActionHandler actoinHandler = new ActionHandler();

		for(EFileMenu eMenuItem : EFileMenu.values()){
			JMenuItem menuItem = new JMenuItem(eMenuItem.getLabel());
			menuItem.setToolTipText(eMenuItem.getLabel());
			menuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
			menuItem.setAccelerator(KeyStroke.getKeyStroke(eMenuItem.getShortcutKey(), Event.CTRL_MASK));
			menuItem.addActionListener(actoinHandler);
			menuItem.setActionCommand(eMenuItem.name());
			this.add(menuItem);
		}
		
	}	

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}	
	
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unchecked")
	private void store(File file) {
		
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			for(int i = 0; i<((Vector<TShape>)this.drawingPanel.getShapes()).size(); i++) {
				((Vector<TShape>)this.drawingPanel.getShapes()).get(i).setSelected(false);
			}
			objectOutputStream.writeObject(this.drawingPanel.getShapes());
			objectOutputStream.close();
			this.drawingPanel.setUpdated(false);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private void load(File file) {
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			Object object = objectInputStream.readObject();
			this.drawingPanel.setShapes(object);
			objectInputStream.close();
			this.drawingPanel.setUpdated(false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}	
	
	private void fileChoose(){
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(this.drawingPanel);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = fileChooser.getSelectedFile();
			this.load(this.file);
		}
	}

	private void newPanel() {
		int reply = this.checkingSave();
		if(reply != -1) {
			if(reply == JOptionPane.YES_OPTION) {
				this.save();
				this.drawingPanel.clearAll();
				this.file = null;
				this.drawingPanel.setUpdated(false);
			} else if(reply == JOptionPane.NO_OPTION) {
				this.drawingPanel.clearAll();
				this.file = null;
				this.drawingPanel.setUpdated(false);
			}
		} else {
			this.drawingPanel.clearAll();
			this.drawingPanel.setUpdated(false);
			this.file = null;
		}
	}	
	
	private void open() {
		int reply = this.checkingSave();
		if(reply != -1) {
			if(reply == JOptionPane.YES_OPTION) {
				this.save();
				this.fileChoose();
			} else if(reply == JOptionPane.NO_OPTION) {
				this.fileChoose();
			}
		} else {
			this.fileChoose();
		}
	}
	
	public void save() {
		if(this.file==null) {
			this.saveAs();
		} else {
			this.store(this.file);
		}	
	}
	
	private void saveAs() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showSaveDialog(this.drawingPanel);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = fileChooser.getSelectedFile();
			this.store(this.file);
		}
	}
	
	public int checkingSave() {
		if(this.drawingPanel.isUpdated()) {
			int reply = JOptionPane.CANCEL_OPTION;
			reply = JOptionPane.showConfirmDialog(this.drawingPanel, "변경내용을 저장할까요?");
		return reply;
		} else {
			return -1;
		}
	}
	
	private void print() {
		this.drawingPanel.print();
	}
	
	public void quit() {
		int reply = this.checkingSave();
		if(reply != -1) {
			if(reply == JOptionPane.YES_OPTION) {
				this.save();
				System.exit(0);
			} else if(reply == JOptionPane.NO_OPTION) {
				System.exit(0);
			}
		} else {
			System.exit(0);
		}
	}
	
	
	
	class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals(EFileMenu.eNew.name())) {
				newPanel();
			} else if (e.getActionCommand().equals(EFileMenu.eOpen.name())) {
				open();
			} else if (e.getActionCommand().equals(EFileMenu.eSave.name())) {
				save();
			} else if (e.getActionCommand().equals(EFileMenu.eSaveAs.name())) {
				saveAs();
			} else if (e.getActionCommand().equals(EFileMenu.ePrint.name())) {
				print();
			} else if (e.getActionCommand().equals(EFileMenu.eQuit.name())) {
				quit();
			}
		}


	}
}
