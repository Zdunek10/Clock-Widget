package zegar;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MainWindow {

	private JFrame frame;
	private Point point = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
					window.frame.pack(); 	 								// metoda pack dostosowuje sie do rozmiaru wewnetrznych komponentów 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		frame.setType(JFrame.Type.UTILITY);
		frame.setUndecorated(true);
	//	frame.setBackground(Color.WHITE);
		frame.setBackground(new Color(0,0,0,0));
	
		
		
	//	frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JClockPanel clockPanel = new JClockPanel(400,300);
		clockPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent me) {						// klikanie myszka i mozemy przesuwaæ obiekt 
				int x = me.getXOnScreen();
				int y = me.getYOnScreen();
			
				if(point == null){
					point = frame.getLocationOnScreen();
					point.x = x - point.x;
					point.y = y - point.y;
				}
				x = x - point.x;
				y = y - point.y;
				frame.setLocation(x,y);	
			}
		});
		clockPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent me) {
				point = null;
				if(me.isPopupTrigger()){
					System.exit(0);
				//	point = null;
				}
			}
		});
		clockPanel.setForeground(Color.WHITE);
		frame.setContentPane(clockPanel);
		clockPanel.start();
	}

}
