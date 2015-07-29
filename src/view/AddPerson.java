package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.BuildingManager;
import qq.model.building.Room;
import qq.model.people.Adjunct;
import qq.model.people.Faculty;
import qq.model.people.OperatingTime;
import qq.model.people.Student;
import qq.model.people.Teacher;

public class AddPerson extends JFrame
{
	JCheckBox studentCheckBox;
	JCheckBox teacherCheckBox;
	JCheckBox adjunctCheckBox;
	JCheckBox facultyCheckBox;
	JTextField nameTextField;
	JTextField startTime;
	JTextField endTime;
	JTextField classRoom;
	JTextField classesTotal;
	JTextField officeTextField;
	JPanel addPersonPanel;
	
	ArrayList<JTextField> classData;
	
	public AddPerson()
	{
	
		// need to make a JOptionPane to get all of a person's attributes and schedule, etc
		// this doesn't work hahaha
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addPersonPanel = new JPanel();
		addPersonPanel.setLayout(new BoxLayout(addPersonPanel,BoxLayout.Y_AXIS));
		addPersonPanel.setPreferredSize(new Dimension(300, 800));
		
		
		JLabel nameLabel = new JLabel("Enter the Name of the Person");
		nameTextField = new JTextField("");
		//nameTextField.setPreferredSize(new Dimension(10, 10));
		JLabel statusLabel = new JLabel("What is this person?");
		studentCheckBox = new JCheckBox("Student");
		teacherCheckBox = new JCheckBox("Teacher");
		adjunctCheckBox = new JCheckBox("Adjunct");
		facultyCheckBox = new JCheckBox("Faculty");
		
		addPersonPanel.add(nameLabel);
		addPersonPanel.add(nameTextField);
		addPersonPanel.add(statusLabel);
		addPersonPanel.add(studentCheckBox);
		addPersonPanel.add(teacherCheckBox);
		addPersonPanel.add(adjunctCheckBox);
		addPersonPanel.add(facultyCheckBox);
		
		JLabel classesAmount = new JLabel("How many classes? 0 for faculty");
		classesTotal = new JTextField(2);
		classesTotal.addActionListener(new classesListedListener());
		
		addPersonPanel.add(classesAmount);
		addPersonPanel.add(classesTotal);
		
		this.getContentPane().add(addPersonPanel);
		this.pack();
		this.setVisible(true);
	}
	
	public void exit()
	{
		this.setVisible(false);
		this.setEnabled(false);
	}
	
	private class okButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			ArrayList<OperatingTime> ot = new ArrayList<OperatingTime>();
			if(studentCheckBox.isSelected())
			{
				for(int i = 0; i < classData.size(); i += 3)
				{
					OperatingTime otPart = new OperatingTime();
					String eventRoom = classData.get(i).getText();
					otPart.setStart(Integer.parseInt(classData.get(i + 1).getText()));
					otPart.setEnd(Integer.parseInt(classData.get(i + 2).getText()));
					
					int eventRoomFloor = Integer.parseInt(eventRoom) / 100;
					for(Room r : BuildingManager.getBuilding().getFloor(eventRoomFloor).getRooms())
					{
						if(r.getName().equals(eventRoom))
						{
							//otPart.setRoom(r);
							otPart.setRoom(eventRoom);
						}
					}
					
					ot.add(otPart);
				}
				Student person = new Student(nameTextField.getText(), ot);
				BuildingManager.addPerson(person);
			}
			
			else if(teacherCheckBox.isSelected())
			{
				for(int i = 0; i < classData.size(); i += 3)
				{
					OperatingTime otPart = new OperatingTime();
					String eventRoom = classData.get(i).getText();
					otPart.setStart(000000);
					otPart.setEnd(000000);
					
					int eventRoomFloor = Integer.parseInt(officeTextField.getText()) / 100;
					for(Room r : BuildingManager.getBuilding().getFloor(eventRoomFloor).getRooms())
					{
						if(r.getName().equals(officeTextField.getText()))
						{
							//otPart.setRoom(r);
							otPart.setRoom(eventRoom);
						}
					}
					
					otPart.setStart(Integer.parseInt(classData.get(i + 1).getText()));
					otPart.setEnd(Integer.parseInt(classData.get(i + 2).getText()));
					
					eventRoomFloor = Integer.parseInt(eventRoom) / 100;
					for(Room r : BuildingManager.getBuilding().getFloor(eventRoomFloor).getRooms())
					{
						if(r.getName().equals(eventRoom))
						{
							//otPart.setRoom(r);
							otPart.setRoom(eventRoom);
						}
					}
					
					ot.add(otPart);
				}
				Teacher person = new Teacher(nameTextField.getText(), ot);
				BuildingManager.addPerson(person);
			}
			
			else if(adjunctCheckBox.isSelected())
			{
				for(int i = 0; i < classData.size(); i += 3)
				{
					OperatingTime otPart = new OperatingTime();
					String eventRoom = classData.get(i).getText();
					otPart.setStart(Integer.parseInt(classData.get(i + 1).getText()));
					otPart.setEnd(Integer.parseInt(classData.get(i + 2).getText()));
					
					int eventRoomFloor = Integer.parseInt(eventRoom) / 100;
					for(Room r : BuildingManager.getBuilding().getFloor(eventRoomFloor).getRooms())
					{
						if(r.getName().equals(eventRoom))
						{
							//otPart.setRoom(r);
							otPart.setRoom(eventRoom);
						}
					}
					
					ot.add(otPart);
				}
				Adjunct person = new Adjunct(nameTextField.getText(), ot);
				BuildingManager.addPerson(person);
			}
			
			else if(facultyCheckBox.isSelected())
			{
				OperatingTime otPart = new OperatingTime();
				int eventRoomFloor = Integer.parseInt(officeTextField.getText()) / 100;
				for(Room r : BuildingManager.getBuilding().getFloor(eventRoomFloor).getRooms())
				{
					if(r.getName().equals(officeTextField.getText()))
					{
						//otPart.setRoom(r);
						otPart.setRoom(officeTextField.getText());
					}
				}
				otPart.setStart(80000);
				otPart.setEnd(170000);
				ot.add(otPart);
				Faculty person = new Faculty(nameTextField.getText(), ot);
				BuildingManager.addPerson(person);
			}
				
			
			exit();
		}
	}
	
	private class cancelButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			exit();			
		}
	}
	
	private class classesListedListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			JButton okButton = new JButton("OK");
			okButton.addActionListener(new okButtonListener());
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new cancelButtonListener());
			classData = new ArrayList<JTextField>();
			
			if(teacherCheckBox.isSelected() || facultyCheckBox.isSelected())
			{
				JLabel officeRoom = new JLabel("What room is the office in?");
				officeTextField = new JTextField(8);
				addPersonPanel.add(officeRoom);
				addPersonPanel.add(officeTextField);
			}
			
			for(int i = 0; i < Integer.parseInt(classesTotal.getText()); i++)
			{
				JLabel selectClass = new JLabel("Select class");
				classRoom = new JTextField("Room number");
				startTime = new JTextField("Start Time");
				endTime = new JTextField("End Time");
				
				addPersonPanel.add(selectClass);
				addPersonPanel.add(classRoom);
				addPersonPanel.add(startTime);
				addPersonPanel.add(endTime);
				
				classData.add(classRoom);
				classData.add(startTime);
				classData.add(endTime);
			}
			
			addPersonPanel.add(okButton);
			addPersonPanel.add(cancelButton);
			
			addPersonPanel.revalidate();
		}
		
	}
}
