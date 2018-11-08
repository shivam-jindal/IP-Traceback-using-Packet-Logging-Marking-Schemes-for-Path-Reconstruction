package iptrace;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Information extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel panel;
	private JLabel header_label, title_label;
	private int routers;
	private JScrollPane jsp;
	private JButton path,show;
	private JTextField jtf;
	private JTable table;
	private String str="";
	String[] columns = {"Router Number", "Degree"};
	Object[][] data;
	JScrollPane sp;
	Information(int parent[],int degree[], int no_routers) {
		routers = no_routers;
		frame = new JFrame("IP_Traceback");
		panel = new JPanel();
		panel.setLayout(null);
		frame.setSize(new Dimension(800, 700));
		data = new Object[routers][2];
		for(int i = 0; i < routers; i++) {
			data[i][0] = new Integer(i + 1);
			data[i][1] = new Integer(degree[i]);
		}
		table = new JTable(data, columns);
		sp = new JScrollPane(table);
		sp.setBounds(400,250,300,300);
		sp.setBorder(BorderFactory.createLineBorder(Color.PINK, 25));
		table.setFillsViewportHeight(true);
		panel.add(sp);
		
		header_label = new JLabel("PATH SELECTION");
		header_label.setFont(new Font("Arial",Font.BOLD|Font.ITALIC ,34));
		header_label.setBounds(225,10,400,40);
		panel.add(header_label);
		
		title_label = new JLabel("Degree of routers");
		title_label.setFont(new Font("Arial",Font.BOLD ,24));
		title_label.setBounds(425,210,400,30);
		panel.add(title_label);
		
		path = new JButton("PATH");
		path.setBackground(Color.MAGENTA);
		path.setBounds(150,125,100,30);
		path.addActionListener(this);
		
		show = new JButton("SHOW");
		show.setBounds(500,125,100,30);
		show.setBackground(Color.MAGENTA);
		show.addActionListener(this);
		
		panel.add(show);
		panel.add(path);
		
		
		frame.add(panel);
		frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == path){
			toString();
			jtf.setText("");
		}
		else if(e.getSource() == show);
		
	}
}
