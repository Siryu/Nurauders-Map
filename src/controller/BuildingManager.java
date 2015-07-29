package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import javax.swing.Timer;

import qq.model.building.Building;
import qq.model.people.*;
import view.BuildingDisplay;
import view.MaraudersMapGUI;

public class BuildingManager
{
	private static Building building;
	private static PersonLibrary people;
	private final static SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
	private static Calendar calendar;
	
	public BuildingManager()
	{
		calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 16);
		new Timer(1000, clockUpdate).start();
		
		building = new Building();
		building.load();
		
		people = new PersonLibrary();
		people.load();
	}
	
	public void startProgram()
	{
		//display = new BuildingDisplay(this);

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MaraudersMapGUI.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MaraudersMapGUI.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MaraudersMapGUI.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MaraudersMapGUI.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}


		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MaraudersMapGUI().setVisible(true);
			}
		});
	}
	
	private ActionListener clockUpdate = new ActionListener()
	{
		public void actionPerformed(ActionEvent evt)
		{
			calendar.add(Calendar.SECOND, 1);
		}
	};
	
	public BuildingManager getManager()
	{
		return this;
	}
	
	public static void addPerson(Person p)
	{
		people.addPersonAndSave(p);
	}
	
	public static Person[] getPeople()
	{
		Person[] theseGuys = new Person[people.size()];
		for(int i = 0; i < people.size(); i++)
		{
			theseGuys[i] = people.get(i);
		}
		return theseGuys;
	}
	// person is updated with the one that is passed into this function
	public void updatePerson(Person p)
	{
		for(Person person : people)
		{
			if(person.getName().equals(p.getName()))
			{
				person = p;
				people.save();
				break;
			}
		}
	}
	
	// deletes the person out of the personLibrary who matches the person passed in
	public static void deletePerson(Person p)
	{
		people.removePersonAndSave(p);
	}

	public static String getTime()
	{
		//return Integer.parseInt(timeFormat.format(this.calendar.getTime()));
		//System.out.println(calendar.toString());
		return(timeFormat.format(calendar.getTime()));
	}

	public static void setTime(int time)
	{
		int hour = time / 10000;
		int minute = (time % 10000) / 100;
		
		calendar.set(2014, 3, 1, hour, minute, 0); 
	}
	
	public static void saveBuilding()
	{
		building.save();
	}
	
	public static Building getBuilding()
	{
		return building;
	}
	
	//this method returns all the People on a certain floor
	public static Person[] getPeopleOnFloor(int floor)
	{
		ArrayList<Person> floorPeople = new ArrayList<Person>();
		for(Person person : people)
		{
			if(person.getFloor() == floor)
			{
				floorPeople.add(person);
			}
		}
		return floorPeople.toArray(new Person[floorPeople.size()]);
	}
}
