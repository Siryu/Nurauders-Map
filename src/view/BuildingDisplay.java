package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import qq.model.building.Node;
import qq.model.building.Room;
import qq.model.people.Person;
import controller.BuildingManager;

public class BuildingDisplay
{
	private BuildingManager bm;
	private JTextField clockTextBox;
	private boolean isAddingNode = false;
	private boolean isAddingRoom = false;
	private int mouseX;
	private int mouseY;
	private int displayFloor = 1;
	
	private JPanel floorPanel;

	public BuildingDisplay(final BuildingManager bm)
	{
		this.bm = bm;
		this.clockTextBox = new JTextField(8);
		this.clockTextBox.setText(BuildingManager.getTime());
		this.clockTextBox.addActionListener(new ChangeTimeListener());
		JFrame frame = new JFrame("tempGui");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout());
		JMenu addMenu = new JMenu("Add Things");
		JMenuItem addNodeMenu = new JMenuItem("Add Node");
		addNodeMenu.addActionListener(new addNodeListener());
		JMenuItem addPersonMenu = new JMenuItem("Add Person");
		addPersonMenu.addActionListener(new AddPersonListener());
		JMenuItem addRoomMenu = new JMenuItem("Add Room");
		addRoomMenu.addActionListener(new addRoomListener());
		JMenuItem exitEditingMenu = new JMenuItem("Exit Editing");
		exitEditingMenu.addActionListener(new ExitEditingListener());
		
		addMenu.add(addPersonMenu);
		addMenu.add(addRoomMenu);
		addMenu.add(addNodeMenu);
		addMenu.add(exitEditingMenu);
		JMenuBar bar = new JMenuBar();
		bar.add(addMenu);
		menuPanel.add(bar);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setBackground(Color.blue);
		controlPanel.setPreferredSize(new Dimension(200, 800));
		controlPanel.add(clockTextBox);
		
		floorPanel = new JPanel()
		{
		    @Override
		    public void paintComponent(Graphics g)
		    {
		       super.paintComponent(g);
		       
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
		       for(Person p : BuildingManager.getPeopleOnFloor(displayFloor))
		       {
		    	   if(p.isInBuilding())
		    	   {
		    		   g.setColor(Color.ORANGE);
		    		   g.fillOval(p.getLocationX(), p.getLocationY(), 5, 5);
		    	   }
		       }
		       
		       g.setColor(Color.YELLOW);
		       g.setFont(new Font("Time", Font.BOLD, 24));
		       g.drawString("Coordinates (" + mouseX + ",  " + mouseY + ")",  20, floorPanel.getHeight() - 20);
		    }
		};
		
		
		floorPanel.setPreferredSize(new Dimension(800, 800));
		floorPanel.setBackground(Color.black);
		floorPanel.addMouseListener(new FloorMouseListener());
		
		mainPanel.add(floorPanel, BorderLayout.CENTER);
		mainPanel.add(controlPanel, BorderLayout.WEST);
		mainPanel.add(menuPanel, BorderLayout.NORTH);
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setVisible(true);
		
		new Timer(1000 / 10, redrawFloor).start();
	}
	
	private ActionListener redrawFloor = new ActionListener()
	{
		public void actionPerformed(ActionEvent evt)
		{
			floorPanel.repaint();
		}
	};
	
	
	private class ChangeTimeListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			BuildingManager.setTime(Integer.parseInt(clockTextBox.getText()));
		}
	}
	
	
	private class ExitEditingListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			isAddingNode = false;
			isAddingRoom = false;
			clockTextBox.setText(BuildingManager.getTime());
		}
		
	}
	private class addNodeListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			clockTextBox.setText("NodeMode");
			isAddingNode = true;	
			isAddingRoom = false;
		}
	}
	
	private class addRoomListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			clockTextBox.setText("RoomMode");
			isAddingRoom = true;
			isAddingNode = false;
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
	
	private class FloorMouseListener implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e)
		{
			if(isAddingNode)
			{
				// need to change this to get the appropriate floor
				BuildingManager.getBuilding().getFloor(displayFloor).addNode(new Node(e.getX(), e.getY()), e.getX(), e.getY());
			}
			else if(isAddingRoom)
			{
				// need to change this to get the appropriate floor
				String roomName = JOptionPane.showInputDialog("Room Name", "enter the room name or number");
				
				if(roomName != null && roomName.length() > 0)
					{
						BuildingManager.getBuilding().getFloor(displayFloor).addRoom(roomName, e.getX(), e.getY());
					}
			}			
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
			mouseX = e.getX();
			mouseY = e.getY();			
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}
		
	}
}


