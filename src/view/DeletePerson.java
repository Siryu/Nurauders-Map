package view;

import java.awt.BorderLayout;
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

import qq.model.building.Room;
import qq.model.people.OperatingTime;
import qq.model.people.Person;
import qq.model.people.Student;

import controller.BuildingManager;

public class DeletePerson extends JFrame
{
	JTextField nameTextField;
	JPanel addPersonPanel;
	Person[] theseGuys;
	
	public DeletePerson()
	{		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		addPersonPanel = new JPanel();
		//addPersonPanel.setLayout(new BorderLayout());
		addPersonPanel.setPreferredSize(new Dimension(400, 800));
		
		
		JLabel nameLabel = new JLabel("Enter the Name of the Person");
		nameTextField = new JTextField(20);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new okButtonListener());
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new cancelButtonListener());
		
		addPersonPanel.add(nameLabel);
		addPersonPanel.add(nameTextField);
		addPersonPanel.add(okButton);
		addPersonPanel.add(cancelButton);
		
		
		JPanel peoplePanel = new JPanel();
		this.theseGuys = BuildingManager.getPeople();
		
		for(Person p : theseGuys)
		{
		peoplePanel.add(new JLabel(p.getPersonName()));
		}
		
		this.getContentPane().add(addPersonPanel, BorderLayout.NORTH);
		this.getContentPane().add(peoplePanel, BorderLayout.CENTER);
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
			for(Person p : theseGuys)
			{
				if(nameTextField.getText().equals(p.getPersonName()))
				{
					BuildingManager.deletePerson(p);
				}
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
}
