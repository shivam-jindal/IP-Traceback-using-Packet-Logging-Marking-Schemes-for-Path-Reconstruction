package iptrace;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Results {
	private int count1, count2;
	private JFrame frame;
	private JPanel panel;
	private JTable router, digest;
	private Object data1[][], data2[][];
	private JScrollPane sp1, sp2;
	private String[] columns1 = {"Router Number", "Incoming Port", "Destination ID", "Outgoing Port"};
	private String[] columns2 = {"Router Number", "Incoming ID", "Stored ID"};
	private JLabel rtable, dtable;
	public Results(int routingTable[][], int path[], int routers) {
		for(int i = 0; i < routers; i++) {
			for(int j = 0; j < routers; j++) {
				System.out.print(routingTable[i][j] + " ");
			}
			System.out.println(" ");
		}
		for(int i = 0; i < routers; i++)
			System.out.println(path[i]);
		
		frame.add(panel);
		frame.setVisible(true);	
	}	
}
