package iptrace;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ReceiveWindow implements ActionListener{
	public JFrame frame;
	public JPanel panel;
	public JButton view,receive;
	public ImageIcon image;
	public JTextArea area,area2;
	public JScrollPane pane,pane2;
	public JLabel label,label1,label2;
	public FileReader file;
	public BufferedReader bfile;
	int attacked_flag;
	String filename2 = "/home/shivam/thread.c", filename;
	
	public ReceiveWindow(String filename, int attacked_flag) throws FileNotFoundException {
		frame = new JFrame();
		this.filename = filename;
		this.attacked_flag = attacked_flag;
		frame.setSize(new Dimension(850,600));
		panel = new JPanel(null);
		view  = new JButton("View Received File");
		view.addActionListener(this);
		view.setBounds(500,70,180,25);
		panel.add(view);
		//image = new ImageIcon("/home/shivam/sem-7/psk-project/Source Code/IP-Traceback-using-Packet-Logging-Marking-Schemes-for-Path-Reconstruction/Source Code/src/iptrace/assets/receive1.png");
		receive = new JButton("Receive");
		receive.setBounds(170,70,125,25);
		panel.add(receive);
		label = new JLabel("RECEPTION");
		label.setBounds(300,20,300,26);
		label.setFont(new Font("Arial", Font.BOLD|Font.ITALIC, 32));
		panel.add(label);
		label1 = new JLabel("Sent File");
		label1.setBounds(140,140,200,26);
		label1.setFont(new Font("Arial", Font.BOLD|Font.ITALIC, 20));
		panel.add(label1);
		label2 = new JLabel("Received File");
		label2.setBounds(560,140,200,26);
		label2.setFont(new Font("Arial", Font.BOLD|Font.ITALIC, 20));
		panel.add(label2);
		area = new JTextArea();
		area.setBounds(480,170,350,350);
		panel.add(area);
		area.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));
		pane = new JScrollPane(area);
		pane.setBounds(480,170,350,350);
		pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(pane);
		area2 = new JTextArea();
		area2 = new JTextArea();
		area2.setBounds(50,170,350,350);
		panel.add(area2);
		area2.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));
		pane2 = new JScrollPane(area2);
		pane2.setBounds(50,170,350,350);
		pane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		pane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(pane2);
		try {
			file = new FileReader(filename);
			bfile = new BufferedReader(file);
			String currentline;
			while((currentline = bfile.readLine()) != null){
				area2.append("\n");
				area2.append(currentline);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.add(panel);
		frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == receive){
			JOptionPane.showMessageDialog(null,"File received!");
		}
		if(e.getSource() == view){
			if(attacked_flag == 1){
				try{
					file = new FileReader(filename2);
					bfile = new BufferedReader(file);
					String currentline;
					while((currentline = bfile.readLine()) != null){
						area.append("\n");
						area.append(currentline);
					}
				}catch(IOException aae){
						
				}
			JOptionPane.showMessageDialog(null, "Sent File does not match with the received File!\nSend reconstruction request from the main frame.");
		}
		else {
			try {
				file = new FileReader(filename);
				bfile = new BufferedReader(file);
				String currentline;
				while((currentline = bfile.readLine()) != null){
					area2.append("\n");
					area2.append(currentline);
				}
			} catch (IOException aae) {
			
			}
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
			}
			JOptionPane.showMessageDialog(null, "Sent File matches with the received File!");
		}
		}
	}
}
