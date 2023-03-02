package frames;
import java.awt.Cursor;

import javax.swing.JMenuBar;

import menus.EditMenu;
import menus.FileMenu;

public class MenuBar extends JMenuBar{

	//attributes
	private static final long serialVersionUID = 1L;
	
	//components
	private FileMenu fileMenu;

	private EditMenu editMenu;
	private DrawingPanel drawingPanel;
	
	public MenuBar() {
		//components
		this.fileMenu = new FileMenu("파일");
		this.fileMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.add(this.fileMenu);
		
		this.editMenu = new EditMenu("편집");
		this.editMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.add(this.editMenu);
	}	

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		this.fileMenu.associate(this.drawingPanel);
		this.editMenu.associate(this.drawingPanel);
	}

	public void initialize() {
		// TODO Auto-generated method stub
		this.fileMenu.initialize();
		this.editMenu.initialize();

		
	}
	
	public FileMenu getFileMenu() {
		return fileMenu;
	}

}
