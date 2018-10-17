package iptrace;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;


public class Construct extends JPanel implements ActionListener,MouseListener,MouseMotionListener{
	private static final long serialVersionUID = 1L;
	public JFrame frame;
	private JPopupMenu jpm;
	private JMenuItem jmi;
	public JPanel panel, panel1;
	public JButton done, refresh, browsePacket, sendPacket;
	public JLabel label2;
	public JLabel label1;
	int routingTable[][];
	final int a[];
	static Graphics j;
	public int doneCount = 0, lan_or_router = 0;
	int row, column;
	public static int attackedRouter = -1;
	Construct(int no_routers, int no_lans) {
		routers = no_routers;
		lans = no_lans;
		a = new int[routers];
		for(int i = 0;i<routers;i++){
			a[i] = i;
		}
		flag_button = new int[routers];
		router_degree = new int[routers];
		matrix = new float[routers][routers];
		parents = new int[routers];
		path = new int[routers];
		routingTable= new int[routers][routers];
		locations = new Point[routers*routers][2];
		for(int i = 0; i < routers; i++) {
			flag_button[i] = 0;
			router_degree[i] = 0;
			path[i] = -1;
			for(int j = 0; j < routers; j++)
				matrix[i][j] = 0;
		}
		frame = new JFrame("IP Traceback.");
		panel = new JPanel();
		panel.setLayout(null);
		panel.addMouseMotionListener(this);
		panel.addMouseListener(this);
		frame.setSize(new Dimension(1366, 768));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		label1 = new JLabel("Drag the routers to intended positions.");
		label1.setFont(new Font("Arial", Font.BOLD|Font.ITALIC, 24));
		label1.setBounds(375, 20, 700, 28);
		panel.add(label1);
		done = new JButton("DONE");

		icon_button = new JButton[routers];
		for(int i = 0; i < routers; i++) {
			icon_button[i] = new JButton(image);
			icon_button[i].setToolTipText("Router " + Integer.toString(i + 1));
			icon_button[i].setSize(new Dimension(50, 50));
			icon_button[i].setLocation(375+(53*i),58);
			panel.add(icon_button[i]);
			icon_button[i].addActionListener(this);
		}
		for(int i = 0;i<2;i++) {
			lan_icon_button[i] = new JButton(image1);
			lan_icon_button[i].setSize(new Dimension(75, 75));
			if(i == 0)
				lan_icon_button[0].setToolTipText("Sender");
			if(i == 1)
				lan_icon_button[1].setToolTipText("Receivers");
			lan_icon_button[i].setLocation(10,(85*i)+150);
			panel.add(lan_icon_button[i]);
			lan_icon_button[i].addActionListener(this);
			
		}
		attacklan_icon_button = new JButton(image1);
		attacklan_icon_button.setLocation(1200, 150);
		attacklan_icon_button.setSize(new Dimension(75, 75));
		attacklan_icon_button.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
		panel.add(attacklan_icon_button);
		frame.add(panel);
		frame.setVisible(true);
		j = panel.getGraphics();
		jpm = new JPopupMenu();
		jmi = new JMenuItem("Add");
		jpm.add(jmi);

}
