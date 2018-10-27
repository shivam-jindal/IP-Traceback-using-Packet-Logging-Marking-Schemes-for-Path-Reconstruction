package iptrace;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;


public class Paint extends JPanel{
	Paint(Graphics g){
		try {
			Graphics2D g2 = (Graphics2D)g;
			g2.setStroke(new BasicStroke(2));
			g2.drawLine(start.x, start.y, destination.x, destination.y);	
		}
		catch(Exception e) {
		}
	}	
}