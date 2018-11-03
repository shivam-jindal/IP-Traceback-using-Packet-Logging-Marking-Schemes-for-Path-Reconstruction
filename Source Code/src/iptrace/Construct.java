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
		}
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
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		inDrag = true;
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		inDrag = false;
		mouseX = e.getX();
		mouseY = e.getY();
		if(lan_or_router == 1 && !isDone){
			lan_icon_button[0].setBounds(mouseX,mouseY, 75, 75);
			panel.add(lan_icon_button[0]);
			frame.add(panel);
			panel.repaint();
			isPlaced = true;
		}
		else if(lan_or_router == 2 && !isDone){
			lan_icon_button[1].setBounds(mouseX,mouseY,75,75);
			panel.add(lan_icon_button[1]);
			frame.add(panel);
			panel.repaint();
		}
		else {
			for(int i = 0; i < routers; i++) {
				if(isPressed){
					if(flag_button[i] == 1) {
						flag_button[i] = 0;
						icon_button[i].setBounds(mouseX, mouseY, 50, 50);
						panel.add(icon_button[i]);
						frame.add(panel);
						panel.repaint();
						isPressed = false;
						count_click = 0;
					}
				}
			}
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
			if(e.getSource() == done) {
				doneCount++;
				if(doneCount == 1){
					label1.setText("Connect the routers.");
					done.setText("Next");
					isDone = true;
					refresh.setEnabled(true);
				}
				else if(doneCount == 2){
					done.setText("Finish");
					refresh.setEnabled(false);
					done.setEnabled(false);
					showPath.setEnabled(true);
					showDegree.setEnabled(true);
					browsePacket.setEnabled(true);
					label1.setText("Click on the router to be attacked.");
					//new smallWindow(doneCount);
					/*float temp;
					for(int i = 0; i<routers; i++) {
						for(int j = 0; j<routers; j++){
							if(i == row0) {
								temp = matrix[i][j];
								matrix[i][j] = matrix[0][j];
								matrix[0][j] = temp;
							}
							if(i == rowlast) {
								temp = matrix[i][j];
								matrix[i][j] = matrix[routers-1][j];
								matrix[routers-1][j] = temp;
							}
						}
					}*/
					for(int i = 0; i < routers; i++){
						for(int j = 0; j< routers; j++)
							System.out.print(matrix[i][j]+ " ");
						System.out.println();
					}
					new Network(matrix, row0, rowlast, routers, parents);
					int index, counter = 0;
					index = rowlast;
					while(parents[index] != -1){
						if(index == rowlast) {
							path[counter++] = index;
						}
						path[counter++] = parents[index];
						index = parents[index];
					}
					System.out.println("Path");
					for(counter = routers-1; counter>=0; counter--) {
						if(path[counter] != -1)
							System.out.println(path[counter]);
					}
				}
			}
			else if(e.getSource() == sendPacket) {
				counter++;
				if(counter == 1) {
					JOptionPane.showMessageDialog(null, " Data Packet Sent Successfully!");
					label1.setText("");
					new Results(routingTable, path, routers);
					sendPacket.setText("Receive Packet");
					browsePacket.setEnabled(false);
				}
				else if(counter == 2) {
					sendPacket.setText("Reconstruction Request");
					for(int i = routers-1;i>=0;i--) {
						if(path[i] != -1) {
							if (path[i] == attacked_button) {
								attacked_flag = 1;
								break;
							}
						}
					}
					try {
						new ReceiveWindow(filename, attacked_flag);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if(counter == 3){
					//new ReconstructionWindow(matrix,attacked_button,routers,row0,rowlast, minus1);
					for(int i = 0; i<routers;i++) {
						path[i] = -1;
						parents[i] = -1;
						matrix[attacked_button][i] = 0;
						matrix[i][attacked_button] = 0;
					}
					new Network(matrix, row0, rowlast, routers, parents);
					int index, counter = 0, minus = 0;
					index = rowlast;
					while(parents[index] != -1){
						if(index == rowlast) {
							path[counter++] = index;
						}
						path[counter++] = parents[index];
						index = parents[index];
					}
					System.out.println("Path");
					for(int counter1 = routers-1; counter1>=0; counter1--) {
						if(path[counter1] != -1){
							System.out.println(path[counter1]);
							minus = 1;
						}
					}
					if(minus == 0) {
						JOptionPane.showMessageDialog(null, "No alternate path is found!");
						System.exit(0);
					}
					Color color = panel.getBackground();
						browsePacket.setEnabled(false);
						for(int i = 0; i<j1; i++){
							start = locations[i][0].getLocation();
							destination = locations[i][1].getLocation();
							Graphics2D g2 = (Graphics2D)j;
							g2.setStroke(new BasicStroke(2));
							g2.setColor(color);
							g2.drawLine(start.x, start.y, destination.x, destination.y);
						}
						for(int i= routers-1;i>0;i--) {
							if(path[i] != -1) {
								try {
									TimeUnit.SECONDS.sleep(1);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
								}
								start = icon_button[path[i]].getLocation();
								destination = icon_button[path[i-1]].getLocation();
								Graphics2D g2 = (Graphics2D)j;
								g2.setColor(color.BLACK);
								g2.drawLine(start.x, start.y, destination.x, destination.y);
							}
						}
						attacked_flag = 0;
						sendPacket.setText("Send Packet!");
				}
				else if(counter == 4){
					JOptionPane.showMessageDialog(null, " Data Packet Sent Successfully!");
					label1.setText("");
					new Results(routingTable, path, routers);
					sendPacket.setText("Receive Packet");
					counter = 1;
				}
			}
			else if(e.getSource() == refresh) {
				panel.repaint();
				for(int i = 0; i < routers; i++){
					for(int j = 0; j< routers; j++)
						matrix[i][j] = 0;
				}
				for(int i = 0; i<routers; i++){
					flag_button[i] = 0;
					router_degree[i] = 0;
				}
				count_click = 0;
				lan1Joined = false; 
				lan2Joined = false; 
				lan0_clicked = false; 
				lan1_clicked = false;
			}
			else if(e.getSource() == showDegree) {
				new Information(path,router_degree,  routers);
			}
			else if(e.getSource() == showPath) {
				Color color = panel.getBackground();
				for(int i = 0; i<j1; i++){
					start = locations[i][0].getLocation();
					destination = locations[i][1].getLocation();
					Graphics2D g2 = (Graphics2D)j;
					g2.setStroke(new BasicStroke(2));
					g2.setColor(color);
					g2.drawLine(start.x, start.y, destination.x, destination.y);
				}
				for(int i= routers-1;i>0;i--) {
					if(path[i] != -1) {
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e1) {
						}
						start = icon_button[path[i]].getLocation();
						destination = icon_button[path[i-1]].getLocation();
						Graphics2D g2 = (Graphics2D)j;
						g2.setColor(color.BLACK);
						g2.drawLine(start.x, start.y, destination.x, destination.y);
					}
				}
				//label1.setText("Click on the router or the packet to see the related tables.");
				new RouterTables(matrix, routingTable, routers);
			}
			else if(e.getSource() == browsePacket) {
				 SourceDetails details = new SourceDetails(filename);
				 sendPacket.setEnabled(true);
			}
			else if(e.getSource() == lan_icon_button[0]){
				if(!isDone){
					sender_lan = lan_icon_button[0].getLocation();
					lan_or_router = 1;
					isPlaced = true;
					
				}
				else if(isPlaced && isDone && !lan1Joined && count_click != 1){
					toJoin = 1;
					start = lan_icon_button[0].getLocation();
					lan1Joined = true;
					lan0_clicked = true;
				}
				else if(isPlaced && isDone && !lan1Joined && count_click == 1){
					lan1Joined = true;
					lan0_clicked = true;
					destination = lan_icon_button[0].getLocation();
					row0 = row_when_router_clicked;
					System.out.println("sender router"+row0);
					Graphics2D g2 = (Graphics2D)j;
					g2.setStroke(new BasicStroke(2));
					g2.drawLine(start.x, start.y, destination.x, destination.y);
					count_click = 0;
					
				}
			}
			else if(e.getSource() == lan_icon_button[1]){
				if(!isDone){
					receiver_lan = lan_icon_button[1].getLocation();
					lan_or_router = 2;
				}
				else if(isPlaced && isDone && !lan2Joined && count_click != 1){
					toJoin = 1;
					start = lan_icon_button[1].getLocation();
					lan2Joined = true;
					lan1_clicked = true;
					
				}
				else if(isPlaced && isDone && !lan2Joined && count_click == 1) {
					lan2Joined = true;
					lan1_clicked = true;
					destination = lan_icon_button[1].getLocation();
					rowlast = row_when_router_clicked;
					System.out.println("receiver router"+rowlast);
					Graphics2D g2 = (Graphics2D)j;
					g2.setStroke(new BasicStroke(2));
					g2.drawLine(start.x, start.y, destination.x, destination.y);
					count_click = 0;
				}
			}
			else if(toJoin == 1){
				toJoin = 0;
				for(int i = 0; i<routers;i++){
					if(e.getSource() == icon_button[i]){
						destination = icon_button[i].getLocation();
						if(lan0_clicked){
							row0 = i;
							lan0_clicked = false;
						}
						else if(lan1_clicked){
							rowlast = i;
							lan1_clicked = false;
						}
						Graphics2D g2 = (Graphics2D)j;
						g2.setStroke(new BasicStroke(2));
						g2.drawLine(start.x, start.y, destination.x, destination.y);
						break;
						
					}
				}
			}
			else {
				lan_or_router = 0;
				if(doneCount == 2){
					for(int i = 0; i < routers; i++) {
						if(e.getSource() == icon_button[i] && !isAttacked) {
							icon_button[i].setBorder(BorderFactory.createLineBorder(Color.RED, 5));
							attacked_router = icon_button[i].getLocation();
							attacked_button = i; 
							System.out.println("his"+attacked_button);
							Graphics2D g2 = (Graphics2D)j;
							g2.setStroke(new BasicStroke(2));
							g2.setColor(Color.RED);
							g2.drawLine(1200, 150, attacked_router.x, attacked_router.y);
							isAttacked = true;
							attackedRouter = i;
							break;
						}
					}
				}
				else {
					if(isDone){
						for(int i = 0; i < routers; i++) {
							if(e.getSource() == icon_button[i]) {
								count_click++;
								if(count_click == 1){
									row = i;
									start = icon_button[i].getLocation();
									router_degree[i]++;
									row_when_router_clicked = i;
									break;
								}
								
								if(count_click == 2){
									destination = icon_button[i].getLocation();
									if(start == destination) {
										router_degree[i]--;
									}
									else {
										router_degree[i]++;
										column = i;
									}
									locations[j1][0] = start;
									locations[j1][1] = destination;
									j1++;
									matrix[row][column] = Distance(start, destination);
									matrix[column][row] = Distance(start, destination);
									count_click = 0;
									p = new Paint(j);
									frame.add(panel);
									break;
								}
							}
						}
					}
				else{
					System.out.println("action performed");
					int prev = 0;
					for(int i = 0; i < routers; i++) {
						if(e.getSource() == icon_button[i]) {
							count_click++;
							if(isPressed == false) {
								prev = i;
								flag_button[i] = 1;
								isPressed = true;
							}
							else {
								if(count_click == 2) {
									flag_button[prev] = 0;
									flag_button[i] = 1;
								}
							}
							break;
						}
					}
				}
			}
		}
	}
	float Distance(Point start, Point destination) {
		return((float)Math.sqrt((start.x - destination.x) * (start.x - destination.x) + (start.y - destination.y) * (start.y - destination.y)));
	}
}
