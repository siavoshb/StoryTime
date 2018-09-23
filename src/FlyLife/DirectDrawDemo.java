package FlyLife;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import FlyLife.Grammar;

import javax.swing.JPanel;

public class DirectDrawDemo extends JPanel {

	private static final long serialVersionUID = -7702500268420125145L;

	private BufferedImage canvas;

    public DirectDrawDemo(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        fillCanvas(Color.BLACK);
    }

    public Dimension getPreferredSize() {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }

    public void fillCanvas(Color c) {
        int color = c.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }
    
    public void drawRect(Color c, int x1, int y1, int width, int height) {
        int color = c.getRGB();
        // Implement rectangle drawing
        for (int x = x1; x < x1 + width; x++) {
            for (int y = y1; y < y1 + height; y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }

    public void drawFly(Fly fly, Random rand) {
    	
    	int x = (int)(Math.floor(canvas.getWidth()/2)) + fly.birthday;
    	int y = (int)(Math.floor(rand.nextDouble() * canvas.getHeight()));
    	
    	for (int i=0; i<fly.life.length; i++) {
    		if (x+i >= canvas.getWidth() || x+i < 0) { break; }
    		Color c = null;;
    		switch ((Grammar.TERMINAL)fly.life[i]) {
			case BORN:
				c = Color.WHITE;
				break;
			case DIE:
				c = Color.BLUE;
				break;
			case EAT:
				c = Color.YELLOW;
				break;
			case FLY:
				c = Color.CYAN;
				break;
			case MATE:
				c = Color.RED;
				break;
			case NOOP:
				break;
			case SLEEP:
				c = Color.GREEN;
				break;
    		}
    		canvas.setRGB(x+i, y, c.getRGB());
    	}
    	
        repaint();
    }
}