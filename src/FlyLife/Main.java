package FlyLife;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

/*
 * TODO
 * - dependent lifecycle generation via mate state
 * - how does this compare to statistical distributions of different states
 * - 'interesting detection' ?
 * - next gammar type to handle better control of life events 
 */

public class Main {

	public Main(){
		prepareGUI();
	}

	public static void main(String[] args) {
		System.out.println("Welcome player one.");
		
		Colony c = new Colony(new Random());
		c.genesis();

		//Main  awtControlDemo = new Main();
		//awtControlDemo.closeAll();
		//awtControlDemo.showCanvasDemo();
	}

	private Frame mainFrame;
	private Label headerLabel;
	private Label statusLabel;
	private Panel controlPanel;

	private void closeAll() {
		System.gc();
		for (Window window : Window.getWindows()) {
		    window.dispose();
		} 
	}

	private void prepareGUI(){
		mainFrame = new Frame("Java AWT Examples");
		mainFrame.setSize(1400,400);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});    
		headerLabel = new Label();
		headerLabel.setAlignment(Label.CENTER);
		statusLabel = new Label();        
		statusLabel.setAlignment(Label.CENTER);
		statusLabel.setSize(350,100);

		controlPanel = new Panel();
		controlPanel.setLayout(new FlowLayout());

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true);  
	}

	private void showCanvasDemo(){
		headerLabel.setText("Story Time"); 

		controlPanel.add(new MyCanvas());
		mainFrame.setVisible(true);  
	} 

	class MyCanvas extends Canvas {

		public MyCanvas () {
			setBackground (Color.GRAY);
			setSize(1000, 300);
		}

		public void paint (Graphics g) {
			Graphics2D g2;
			g2 = (Graphics2D) g;
			g2.drawString("Welcome player one.", 70, 70);
		}
	}

}
