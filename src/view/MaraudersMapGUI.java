package view;

import javax.swing.*;

import qq.model.building.Node;
import qq.model.building.Room;
import qq.model.people.Person;
import qq.model.people.PersonStatus;
import controller.BuildingManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MaraudersMapGUI extends javax.swing.JFrame {

	private JButton upButton;
	private JButton downButton;
	private JLabel mainPanel;
	private JLabel legendLabel;
	private JLabel floorNumberLabel;
	private JLabel floorsButtonLabel;
	private JLabel nameLabel;
	private JLabel quartInEmployLabel;
	private JLabel statusLabel;
	private JLabel currentClassLabel;
	private JLabel classScheduleLabel;
	private JLabel officeHoursLabel;
	private JMenu personMenu;
	private JMenuItem searchMenuItem;
	private JMenuBar menuBar;
	private JMenuItem addPersonMenuItem;
	private JMenuItem deletePersonMenuItem;
	private JPanel infoCenterPanel;
	private JPanel legendPanel;
	private JFormattedTextField clockTextField;
	
	private boolean isAddingNode = false;	
	private boolean isAddingRoom = false;
	private int displayFloor = 1;
	private int mouseX;
	private int mouseY;
	private int lastClickX;
	private int lastClickY;
	private String searchForPerson;
	
	public MaraudersMapGUI() 
	{
		initComponents();
	}

	private void initComponents() {

		ImageIcon upbuttonimg = new ImageIcon("upButton.png");
		ImageIcon downbuttonimg = new ImageIcon("downButton.png");
		
		floorNumberLabel = new JLabel();
		floorsButtonLabel = new JLabel();
		upButton = new JButton();
		upButton.setIcon(upbuttonimg);
		
		downButton = new JButton();
		downButton.setIcon(downbuttonimg);
		
		infoCenterPanel = new JPanel();
		nameLabel = new JLabel();
		quartInEmployLabel = new JLabel();
		statusLabel = new JLabel();
		currentClassLabel = new JLabel();
		classScheduleLabel = new JLabel();
		officeHoursLabel = new JLabel();
		legendPanel = new JPanel();
		legendLabel = new JLabel();
		clockTextField = new JFormattedTextField();
		clockTextField.setText("HHMMSS settime");
		clockTextField.addActionListener(new ChangeTimeListener());
		mainPanel = new JLabel()
		{
		    @Override
		    public void paintComponent(Graphics g)
		    {
		       super.paintComponent(g);
		       if(isAddingRoom || isAddingNode)
		       {
			       for(Node displayNode : BuildingManager.getBuilding().getFloor(displayFloor).getNodes())
			       {
			    	   g.setColor(Color.GREEN);
			    	   g.fillOval(displayNode.getX(), displayNode.getY(), 10, 10);
			       }
			       for(Room displayRoom : BuildingManager.getBuilding().getFloor(displayFloor).getRooms())
			       {
			    	   g.setColor(Color.BLUE);
			    	   g.fillOval(displayRoom.getX(), displayRoom.getY(), 10, 10);
			       }
		       }
		       for(Person p : BuildingManager.getPeopleOnFloor(displayFloor))
		       {
		    	   if(p.isInBuilding())
		    	   {
		    		   if(p.getPstatus() == PersonStatus.STUDENT)
		    			   g.setColor(Color.RED);
		    		   else if(p.getPstatus() == PersonStatus.TEACHER)
		    			   g.setColor(Color.GREEN);
		    		   else if(p.getPstatus() == PersonStatus.ADJUNCT)
		    			   g.setColor(Color.BLUE);
		    		   else
		    			   g.setColor(Color.MAGENTA);
		    		   
		    		   g.fillOval(p.getLocationX(), p.getLocationY(), 10, 10);
		    		   
		    		   if(searchForPerson != null && p.getPersonName().equals(searchForPerson))
		    		   {
		    			   g.setColor(Color.YELLOW);
		    			   g.fillRect(p.getLocationX(), p.getLocationY(), 10, 10);
		    		   }
		    	   }
		       }
		       
		       g.setColor(Color.DARK_GRAY);
		       g.setFont(new Font("Time", Font.BOLD, 24));
		       g.drawString("Time :" + BuildingManager.getTime(), this.getWidth() / 2, 35);
		       if(isAddingRoom || isAddingNode)
		       {
		    	   g.drawString("Coordinates (" + mouseX + ",  " + mouseY + ")",  20, mainPanel.getHeight() - 20);
		       }
		    }
		};
		mainPanel.addMouseListener(new FloorMouseListener());
		mainPanel.addMouseMotionListener(new FloorMouseMotionListener());
		menuBar = new JMenuBar();
		personMenu = new JMenu();
		addPersonMenuItem = new JMenuItem();
		addPersonMenuItem.addActionListener(new AddPersonListener());
		deletePersonMenuItem = new JMenuItem();
		searchMenuItem = new JMenuItem();

		//main window
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Nu-rauders Map");
		setCursor(new java.awt.Cursor(Cursor.DEFAULT_CURSOR));
		setPreferredSize(new java.awt.Dimension(1800, 960));

		floorNumberLabel.setIcon(new ImageIcon("floor1label.png"));

		floorsButtonLabel.setText("FLOORS");

		upButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		downButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		nameLabel.setText("");//Name:");

		quartInEmployLabel.setText("");//QuarterIn/Employment:");

		statusLabel.setText("");//Status:");

		currentClassLabel.setText("");//CurrentClass:");

		classScheduleLabel.setText("");//ClassSchedule:");

		officeHoursLabel.setText("");//OfficeHours:");

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				infoCenterPanel);
		infoCenterPanel.setLayout(jPanel2Layout);
		jPanel2Layout
				.setHorizontalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																currentClassLabel,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																jPanel2Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel2Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								officeHoursLabel,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								classScheduleLabel,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								statusLabel,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								quartInEmployLabel,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								222,
																								Short.MAX_VALUE)
																						.addComponent(
																								nameLabel,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE))
																		.addContainerGap()))));
		jPanel2Layout
				.setVerticalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												statusLabel,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												55,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												nameLabel,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												55,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												quartInEmployLabel,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												55,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												currentClassLabel,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												55,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												classScheduleLabel,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												55,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(148, 148, 148)
										.addComponent(
												officeHoursLabel,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												54,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(188, Short.MAX_VALUE)));

		nameLabel.getAccessibleContext().setAccessibleName("nameLabel");
		quartInEmployLabel.getAccessibleContext().setAccessibleName("qieLabel");
		statusLabel.getAccessibleContext().setAccessibleName("StatusLabel");
		currentClassLabel.getAccessibleContext().setAccessibleName("CurrentClassLabel");
		classScheduleLabel.getAccessibleContext().setAccessibleName("ClassScheduleLabel");
		officeHoursLabel.getAccessibleContext().setAccessibleName("OfficeHoursLabel");

		legendLabel.setIcon(new javax.swing.ImageIcon("buttons.png")); // NOI18N

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(
				legendPanel);
		legendPanel.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				legendLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 730,
				Short.MAX_VALUE));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				legendLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		mainPanel.setIcon(new javax.swing.ImageIcon("floor1.jpg")); // NOI18N

		personMenu.setText("Menu");

		addPersonMenuItem.setText("Add Person");
		personMenu.add(addPersonMenuItem);

		deletePersonMenuItem.setText("Delete Person");
		deletePersonMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem3ActionPerformed(evt);
			}
		});
		personMenu.add(deletePersonMenuItem);
		
	
		menuBar.add(personMenu);

		searchMenuItem.setText("Search For..");
		searchMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenu2ActionPerformed(evt);
			}
		});
		menuBar.add(searchMenuItem);
		
		JMenu developerMenu = new JMenu("Developer Tools");
		JMenuItem addNodeMenu = new JMenuItem("Add Node");
		addNodeMenu.addActionListener(new addNodeListener());
		JMenuItem addRoomMenu = new JMenuItem("Add Room");
		addRoomMenu.addActionListener(new addRoomListener());
		JMenuItem saveBuildingMenuItem = new JMenuItem();
		saveBuildingMenuItem.setText("Save Building");
		saveBuildingMenuItem.addActionListener(new SaveBuildingActionListener());
		JMenuItem exitEditingMenu = new JMenuItem("Exit Editing");
		exitEditingMenu.addActionListener(new ExitEditingListener());
		
		developerMenu.add(addRoomMenu);
		developerMenu.add(addNodeMenu);
		developerMenu.add(saveBuildingMenuItem);
		developerMenu.add(exitEditingMenu);
		
		menuBar.add(developerMenu);
		
		

		setJMenuBar(menuBar);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(20, 20,
																		20)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addComponent(
																														floorsButtonLabel,
																														javax.swing.GroupLayout.PREFERRED_SIZE,
																														53,
																														javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addComponent(
																														downButton,
																														javax.swing.GroupLayout.PREFERRED_SIZE,
																														53,
																														javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addComponent(
																														upButton,
																														javax.swing.GroupLayout.PREFERRED_SIZE,
																														53,
																														javax.swing.GroupLayout.PREFERRED_SIZE))
																								.addGap(18,
																										18,
																										18)
																								.addComponent(
																										mainPanel,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										1446,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										Short.MAX_VALUE)
																								.addComponent(
																										infoCenterPanel,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										javax.swing.GroupLayout.PREFERRED_SIZE))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										clockTextField,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										110,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addGap(0,
																										0,
																										Short.MAX_VALUE))))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(620,
																		620,
																		620)
																.addComponent(
																		floorNumberLabel,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		296,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(0,
																		0,
																		Short.MAX_VALUE)))
								.addContainerGap())
				.addGroup(
						layout.createSequentialGroup()
								.addGap(353, 353, 353)
								.addComponent(legendPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addContainerGap()
																.addComponent(
																		legendPanel,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(26, 26,
																		26)
																.addComponent(
																		clockTextField,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addGap(7, 7, 7)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														mainPanel,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		infoCenterPanel,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(299,
																		299,
																		299)
																.addComponent(
																		upButton,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		41,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		floorsButtonLabel,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		43,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		downButton,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		41,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(0,
																		0,
																		Short.MAX_VALUE)))
								.addGap(18, 18, 18)
								.addComponent(floorNumberLabel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										60,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));

		legendPanel.getAccessibleContext().setAccessibleName("LegendPanel");

		pack();
		
		new Timer(1000 / 30, redrawFloor).start();
	}
	
	private ActionListener redrawFloor = new ActionListener()
	{
		public void actionPerformed(ActionEvent evt)
		{
			mainPanel.repaint();
		}
	};
	
	private class SaveBuildingActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			BuildingManager.saveBuilding();			
		}
		
	}
	
	
	private class addNodeListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			clockTextField.setText("NodeMode");
			isAddingNode = true;	
			isAddingRoom = false;
		}
	}
	
	private class addRoomListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			clockTextField.setText("RoomMode");
			isAddingRoom = true;
			isAddingNode = false;
		}
	}
	
	private class ExitEditingListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			isAddingNode = false;
			isAddingRoom = false;
			clockTextField.setText("HHMMSS set time");
		}
	}
	
	private class ChangeTimeListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			BuildingManager.setTime(Integer.parseInt(clockTextField.getText()));
		}
	}
	
	private class FloorMouseMotionListener implements MouseMotionListener
	{

		@Override
		public void mouseDragged(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e)
		{
			if(isAddingRoom || isAddingNode)
			{
				mouseX = e.getX() - lastClickX;
				mouseY = e.getY() - lastClickY;
			}
		}
		
	}
	
	private class FloorMouseListener implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e)
		{
			
			if(isAddingNode)
			{
				BuildingManager.getBuilding().getFloor(displayFloor).addNode(new Node(e.getX(), e.getY()), e.getX(), e.getY());
			}
			else if(isAddingRoom)
			{
				String roomName = JOptionPane.showInputDialog("Room Name", "enter the room name or number");
				
				if(roomName != null && roomName.length() > 0)
				{
					BuildingManager.getBuilding().getFloor(displayFloor).addRoom(roomName, e.getX(), e.getY());
				}
			}	
			lastClickX = e.getX();
			lastClickY = e.getY();
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
				
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}
		
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)
	{
		if(displayFloor < 4)
		{
			displayFloor += 1;
			changeFloor(displayFloor);
		}
	}
	
	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) 
	{
		if(displayFloor > 0)
		{
			displayFloor -= 1;
			changeFloor(displayFloor);
		}
	}
	
	private class AddPersonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			new AddPerson();
		}
	}

	private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt)
	{
		new DeletePerson();
	}

	private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt)
	{
		//searchfor...
		String searchPersonName = JOptionPane.showInputDialog("Enter who you want to find.");
		Person[] searchPeople = BuildingManager.getPeople();
		for(Person p : searchPeople)
		{
			if(searchPersonName != null && searchPersonName.equals(p.getPersonName()))
			{
				changeFloor(p.getFloor());
				searchForPerson = searchPersonName;
			}
		}
	}
	
	private void changeFloor(int floorNumber)
	{
		displayFloor = floorNumber;
		BuildingManager.getPeopleOnFloor(displayFloor);
		if(displayFloor == 4)
		{
			mainPanel.setIcon(new javax.swing.ImageIcon("floor4.jpg"));
			floorNumberLabel.setIcon(new ImageIcon("floor4label.png"));
		}
		else if(displayFloor == 3)
		{
			mainPanel.setIcon(new javax.swing.ImageIcon("Floorthree.jpg"));
			floorNumberLabel.setIcon(new ImageIcon("floor3label.png"));
		}
		else if(displayFloor == 2)
		{
			mainPanel.setIcon(new javax.swing.ImageIcon("floor2.jpg"));
			floorNumberLabel.setIcon(new ImageIcon("floor2label.png"));
		}
		else if(displayFloor == 1)
		{
			mainPanel.setIcon(new javax.swing.ImageIcon("floor1.jpg"));
			floorNumberLabel.setIcon(new ImageIcon("floor1label.png"));
		}
		else if(displayFloor == 0)
		{
			mainPanel.setIcon(new javax.swing.ImageIcon("floor0.jpg"));
			floorNumberLabel.setIcon(new ImageIcon("floor0label.png"));
		}
	}
}
