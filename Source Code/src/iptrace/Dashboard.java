package iptrace;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Dashboard implements ActionListener {
	public JLabel label1;
	public JLabel label2;
	public JLabel label3;
	public JLabel err_label;
	public JPanel window1;
	public JTextField routers;
	public JTextField lans;
	public JFrame frame;
	public JButton button;
	public int no_routers, no_lans;
	public static int flag = 0;
	public String err = "*Enter valid integers.";
	Dashboard() {
		frame = new JFrame("IP Traceback");
		frame.setSize(1920, 1080);
		frame.setLayout(new FlowLayout());
		window1 = new JPanel(new GridBagLayout());
		label1 = new JLabel("IP Traceback using Packet marking and packet logging\n\n");
		label1.setFont(new Font("Arial",Font.BOLD|Font.ITALIC ,32));
		GridBagConstraints cons = new GridBagConstraints();
		cons.anchor = GridBagConstraints.WEST;
		cons.insets = new Insets(50, 0, 0, 0);
		cons.gridx = 0;
		cons.gridy = 1;
		label2 = new JLabel("\n\nEnter the number of Routers:");
		window1.add(label2, cons);
		routers = new JTextField(10);
		cons.gridx = 1;
		window1.add(routers, cons);
		button = new JButton("NEXT");
		button.setBackground(Color.GRAY);
		button.addActionListener(this);
		cons.anchor = GridBagConstraints.CENTER;
		cons.gridx = 0;
	    cons.gridy = 4;
		window1.add(button, cons);
		frame.add(label1);
		frame.add(window1);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new Dashboard();
				//new Construct(10,2);
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			no_routers = Integer.valueOf(routers.getText());
			new Construct(no_routers,2);
			frame.setVisible(false);
		}
		catch(NumberFormatException n) {
			flag = 1;
			err_label = new JLabel(err);
			GridBagConstraints cons = new GridBagConstraints();
			cons.anchor = GridBagConstraints.CENTER;
			err_label.setForeground(Color.RED);
			err_label.setFont(new Font("Arial", Font.ITALIC, 12));
			cons.insets = new Insets(50, 0, 0, 0);
			cons.gridx = 0;
			cons.gridy = 3;
			window1.add(err_label, cons);
			frame.add(window1);
			frame.revalidate();
		}
	}
}
