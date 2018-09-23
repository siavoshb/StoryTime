package FlyLife;

import java.util.Random;

import javax.swing.JFrame;

public class Main {

	public Main(){
	}

	public static void main(String[] args) {
		System.out.println("Welcome player one.");

		int width = 640;
		int height = 480;
		JFrame frame = new JFrame("Direct draw demo");

		DirectDrawDemo panel = new DirectDrawDemo(width, height);

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Colony c = new Colony(new Random(1));
		c.genesis(panel);
	}

}
