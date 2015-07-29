package qq.model.people;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class PersonLibrary extends ArrayList<Person> implements Serializable
{
	public void addPersonAndSave(Person p)
	{
		this.add(p);
		this.save();
		p.start();
	}
	
	public void removePersonAndSave(Person p)
	{
		this.remove(p);
		this.save();
	}
	
	public void save()
	{
		OutputStream o = null;
		try
		{
			o = new FileOutputStream("People.sav");
			ObjectOutputStream oos = new ObjectOutputStream(o);
			oos.writeObject(this.toArray(new Person[this.size()]));
			oos.close();
		}
		
		catch(Exception ex)
		{
			System.err.println("Error writing savefile");
		}
		
		o = null;
		try
		{
			o = new FileOutputStream("People.bak");
			ObjectOutputStream oos = new ObjectOutputStream(o);
			oos.writeObject(this.toArray(new Person[this.size()]));
			oos.close();
		}
		
		catch(Exception ex)
		{
			System.err.println("Error writing backup savefile");
		}
	}
	
	public void load()
	{
		InputStream i = null;
		Person[] list = null;
		try
		{
			i = new FileInputStream("People.sav");
			ObjectInputStream ois = new ObjectInputStream(i);
			list = (Person[])ois.readObject();
			ois.close();
		}
		
		catch(Exception ex)
		{
			try
			{
				i = new FileInputStream("People.bak");
				ObjectInputStream ois = new ObjectInputStream(i);
				list = (Person[])ois.readObject();
			
				ois.close();
			} 
			catch (Exception e)
			{
				System.err.println("Error Loading People.sav and People.bak");
			}
		}
		
		if(list != null)
		{
			for(Person p : list)
			{
				p.setInBuilding(false);
				this.add(p);
				p.start();
			}
		}
	}
}
