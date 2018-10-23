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
	public ImageIcon image,image1, image2;
	public JButton[] icon_button;
	public JButton[] lan_icon_button;
	public JButton attacklan_icon_button, showDegree, showPath;
	public String msg = "";
	public static String filename = "";
	public int mouseX = 0, mouseY = 0, movX = 0, movY = 0;
	public final int routers, lans;
	public int flag_button[], router_degree[], parents[], path[];
	public Point locations[][];
	float matrix[][];
	boolean inDrag = false;
	public Point sender_lan, receiver_lan, attacked_router;
	boolean isDone = false, isAttacked = false, isPlaced = false, lan1Joined = false, lan2Joined = false, lan0_clicked = false, lan1_clicked = false;
	boolean isPressed = false;
	static int count_click = 0, toJoin = 0, row_when_router_clicked, attacked_flag, attacked_button = -1, pathshown = 0, counter = 0;
	private int row0, rowlast, j1 = 0;
	Point start,destination;
	Paint p;
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
		done.addActionListener(this);
		done.setLocation(170, 680);
		done.setSize(new Dimension(100, 30));
		panel.add(done);
		image2 = new ImageIcon("/home/shivam/sem-7/psk-project/Source Code/IP-Traceback-using-Packet-Logging-Marking-Schemes-for-Path-Reconstruction/Source Code/src/iptrace/assets/refresh1.png");
		refresh = new JButton(image2);
		refresh.addActionListener(this);
		refresh.setLocation(280, 680);
		refresh.setSize(new Dimension(30, 30));
		refresh.setEnabled(false);
		panel.add(refresh);
		showDegree = new JButton("Show Degree");
		showDegree.addActionListener(this);
		showDegree.setLocation(320, 680);
		showDegree.setSize(new Dimension(200, 30));
		showDegree.setEnabled(false);
		panel.add(showDegree);
		showPath = new JButton("Show Shortest Path");
		showPath.addActionListener(this);
		showPath.setLocation(530, 680);
		showPath.setSize(new Dimension(250, 30));
		showPath.setEnabled(false);
		panel.add(showPath);
		browsePacket = new JButton("Browse Packet");
		browsePacket.addActionListener(this);
		browsePacket.setLocation(790, 680);
		browsePacket.setSize(new Dimension(250, 30));
		browsePacket.setEnabled(false);
		panel.add(browsePacket);
		sendPacket = new JButton("Send Packet");
		sendPacket.addActionListener(this);
		sendPacket.setLocation(1050, 680);
		sendPacket.setSize(new Dimension(200, 30));
		sendPacket.setEnabled(false);
		panel.add(sendPacket);

		image = new ImageIcon("/home/shivam/sem-7/psk-project/Source Code/IP-Traceback-using-Packet-Logging-Marking-Schemes-for-Path-Reconstruction/Source Code/src/iptrace/assets/router1.png");
		image1 = new ImageIcon("/home/shivam/sem-7/psk-project/Source Code/IP-Traceback-using-Packet-Logging-Marking-Schemes-for-Path-Reconstruction/Source Code/src/iptrace/assets/pc1.png");
		lan_icon_button = new JButton[2];
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
		for( int i = 0; i<routers;i++){
			if(i == 0){
				icon_button[0].addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent me) {
						if(SwingUtilities.isRightMouseButton(me)){
							jpm.show(icon_button[0], me.getX(),me.getY());
						}
					}
				});
			}
			if(i == 1){
				icon_button[1].addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent me) {
						if(SwingUtilities.isRightMouseButton(me)){
							jpm.show(icon_button[1], me.getX(),me.getY());
						}
					}
				});
			}
			if(i == 2){
				icon_button[2].addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent me) {
						if(SwingUtilities.isRightMouseButton(me)){
							jpm.show(icon_button[2], me.getX(),me.getY());
						}
					}
				});
			}
			if(i == 3){
				icon_button[3].addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent me) {
						if(SwingUtilities.isRightMouseButton(me)){
							jpm.show(icon_button[3], me.getX(),me.getY());
						}
					}
				});
			}
			if(i == 4){
				icon_button[4].addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent me) {
						if(SwingUtilities.isRightMouseButton(me)){
							jpm.show(icon_button[4], me.getX(),me.getY());
						}
					}
				});
			}
			if(i == 5){
				icon_button[5].addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent me) {
						if(SwingUtilities.isRightMouseButton(me)){
							jpm.show(icon_button[5], me.getX(),me.getY());
						}
					}
				});
			}
			if(i == 6){
				icon_button[6].addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent me) {
						if(SwingUtilities.isRightMouseButton(me)){
							jpm.show(icon_button[6], me.getX(),me.getY());
						}
					}
				});
			}
			if(i == 7){
				icon_button[7].addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent me) {
						if(SwingUtilities.isRightMouseButton(me)){
							jpm.show(icon_button[7], me.getX(),me.getY());
						}
					}
				});
			}
			if(i == 8){
				icon_button[8].addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent me) {
						if(SwingUtilities.isRightMouseButton(me)){
							jpm.show(icon_button[8], me.getX(),me.getY());
						}
					}
				});
			}
			if(i == 9){
				icon_button[9].addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent me) {
						if(SwingUtilities.isRightMouseButton(me)){
							jpm.show(icon_button[9], me.getX(),me.getY());
						}
					}
				});
			}
		}
	}

}
