package frames;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
public class MainFrame extends JFrame {

	//attributes
	private static final long serialVersionUID = 1L;
	
	//components
	private MenuBar menuBar;
	private ToolBar toolBar;
	private DrawingPanel drawingPanel;

	public MainFrame()  {

		//attributes
		this.setSize(1000, 750);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("그림판");
		this.setIconImage((new ImageIcon("icon/TitleIcon.png")).getImage());	
		this.setBackground(Color.white);
		
		//components
		BorderLayout layoutManager = new BorderLayout();
		this.setLayout(layoutManager);		
		
		this.menuBar = new MenuBar();
		this.setJMenuBar(this.menuBar);
		
		this.toolBar = new ToolBar();
		this.add(this.toolBar, BorderLayout.NORTH);
		
		this.drawingPanel = new DrawingPanel();
		this.add(this.drawingPanel, BorderLayout.CENTER);
		
		//association
		this.toolBar.associate(this.drawingPanel);
		this.menuBar.associate(this.drawingPanel);
		this.drawingPanel.association(this.toolBar);
		
		WindowHandler windowListener = new WindowHandler();
		this.addWindowListener(windowListener);
	}

	public void initialize() {
		// TODO Auto-generated method stub
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();

		
	}
	
	private class WindowHandler extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent event) {
			menuBar.getFileMenu().quit();
		}
	}

}
