package zegar;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

public class JClockPanel extends JPanel implements Runnable {

//	 int width = 5;
//	 int height = 5;

	private static final Font Font = null;
	private BufferedImage bi;
	private volatile boolean start = false;
	private Calendar time = Calendar.getInstance();

	public JClockPanel(int width, int height) {
		Dimension size = new Dimension(width, height);
		this.setPreferredSize(size);
		this.setSize(size);
		this.setOpaque(false);
	}

	private void paintClock(Graphics2D g2, Calendar time) {
		
		Composite defaultComposite = g2.getComposite();
																													//czyszczeie
		g2.setComposite(AlphaComposite.Clear);
		g2.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		
		float trancparency = 1f;
		Composite composite = AlphaComposite.getInstance(AlphaComposite.SRC, trancparency);
		g2.setComposite(composite);
																														
																														
																													/*	rysowanie wskazowek
																														obliczanie wspolrzednych x, y punktu 
																																				polozenie na eclipse
																														double x = a + cos(k¹t) * xPromieñ;
																														double y = b + sin(k¹t) * yPromien;
																														gdzie a,b - wspolrzedne srodka elipsy
																													*/	
		g2.setColor(Color.blue);
		
		Point2D srodek = new Point2D.Double(bi.getWidth() /2.0, bi.getHeight()/2.0);
		double xPromien = bi.getWidth() / 2;
		double yPromien = bi.getHeight() / 2;
		
		int second = time.get(Calendar.SECOND);
		double kat = (2 * Math.PI)  / 60.0 * second - Math.PI/2.0;
		
		double x = srodek.getX() +  xPromien * Math.cos(kat);
		double y = srodek.getY() +  yPromien * Math.sin(kat);
		
		
		float strokeWidth =  2f;			// gruboœæ lini
		Stroke stroke = new BasicStroke(strokeWidth,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
		g2.setStroke(stroke);
		Point2D secondPoint = new Point2D.Double(x,y);
		g2.draw(new Line2D.Double(srodek, secondPoint));								//
		
		Point2D minutePoint = new Point2D.Double(x,y);
		g2.draw(new Line2D.Double(srodek, minutePoint));								//
		
		Font secondFont = new Font("TimesNewRoman", Font.PLAIN, (int) (bi.getHeight()/ 7.0)); 
		g2.setFont(secondFont);
		
		g2.drawString(":" + second, (int) srodek.getX() + 30 , (int) srodek.getY() - 80);
		g2.draw(new Ellipse2D.Double(0, 0, bi.getWidth()-strokeWidth, bi.getHeight() - strokeWidth));
		g2.setColor(Color.red);
		g2.setComposite(defaultComposite);
		
//
		int minute = time.get(Calendar.MINUTE);
		double kat_2 = (2 * Math.PI) / 60.0 * minute - Math.PI/2;
		
		double a = srodek.getX() + 0.55 * xPromien * Math.cos(kat_2);
		double b = srodek.getY() + 0.55 * yPromien * Math.sin(kat_2);
		
		float strokeWidth_2 = 4f; // ustawienie grubosci 
		Stroke stroke_2 = new BasicStroke(strokeWidth_2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(stroke_2);
		Point2D secondPoint_2 = new Point2D.Double(a, b);
		g2.draw(new Line2D.Double(srodek, secondPoint_2));
		
		Font secondFont_2 = new Font("TimesNewRoman", Font.PLAIN, (int) (bi.getHeight() / 7.0));
		g2.setFont(secondFont_2);
		g2.drawString(" " + minute, (int) srodek.getX() - 30  , (int) srodek.getY() - 80);
		
		g2.setColor(Color.black); // kolor
		
		g2.draw(new Ellipse2D.Double(0, 0, bi.getWidth()-strokeWidth_2, bi.getHeight()-strokeWidth_2));
	
//
		int hour = time.get(Calendar.HOUR);
		double kat_3 = (2 * Math.PI) / 12 * hour - Math.PI/2;
		
		double c = srodek.getX() + 0.30 * xPromien * Math.cos(kat_3);
		double d = srodek.getY() + 0.30 * yPromien * Math.sin(kat_3);
		
		float strokeWidth_3 =6f; // ustawienie grubosci lini
		Stroke stroke_3 = new BasicStroke(strokeWidth_3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(stroke_3);
		Point2D secondPoint_3 = new Point2D.Double(c, d);
		g2.draw(new Line2D.Double(srodek, secondPoint_3));
		
		Font secondFont_3 = new Font("TimesNewRoman", Font.PLAIN, (int) (bi.getHeight() / 7.0));
		g2.setFont(secondFont_3);
		g2.drawString(hour+":" , (int) srodek.getX()-55 , (int) srodek.getY() - 80);
		
		g2.setColor(Color.green); //zdefiniowanie koloru
		
		g2.draw(new Ellipse2D.Double(0, 0, bi.getWidth()-strokeWidth_3, bi.getHeight()-strokeWidth_3));

		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		if (bi == null || bi.getWidth() != this.getWidth() && bi.getHeight() != this.getHeight()) {
			this.bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		}

		Graphics2D g2bi = bi.createGraphics();
		g2bi.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		paintClock(g2bi, time);
		g2bi.dispose();

		g2.drawImage(bi, 0, 0, this);
		// g.drawOval(130, 130, 32, 32);

	}
	int x = 0;
	@Override
	public void run() {
		int prevSecond = -1;
		while (start) {
			time.setTimeInMillis(System.currentTimeMillis());
			
			int second = time.get(Calendar.SECOND);
			
			if( second != prevSecond){
				prevSecond = second;
				repaint();
			}
		//	x+=10;
		//	repaint();

			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e) {
			//	e.printStackTrace();
			}
		}

	}

	public void start() {
		this.start = true;
		Thread thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		this.start = false;
	}

}
