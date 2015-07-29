package qq.model.building;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;


/*
 * Room 100 = commons, Room 199 is the entrance to the building, 
 * Room ending in 98 is stairs from that floor to higher floor
 * Room ending in 97 is stairs from that floor to lower floor
 */

public class Building implements Serializable
{
	//private Room entrance = new Room("199", new Node(10, 10));
	private int entranceFloor = 1;
	private Floor[] floor;
	
	public Building()
	{
		floor = new Floor[5];
		this.floor[0] = new Floor();
		this.floor[1] = new Floor();
		this.floor[2] = new Floor();
		this.floor[3] = new Floor();
		this.floor[4] = new Floor();
	}

	public void load()
	{
		InputStream i = null;
		Floor[] list = null;
		try
		{
			i = new FileInputStream("Building.sav");
			ObjectInputStream ois = new ObjectInputStream(i);
			list = (Floor[])ois.readObject();
			ois.close();
			for(int j = 0; j < 5; j++)
			{
				this.floor[j] = list[j];
			}
		}
		catch(Exception ex)
		{
			System.err.println("Error. Unable to retrieve data from the Building file.");
		}		
	}
	
	public void save()
	{
		OutputStream o = null;
		try
		{
			o = new FileOutputStream("Building.sav");
			ObjectOutputStream oos = new ObjectOutputStream(o);
			oos.writeObject(this.floor);
			oos.close();
		}
		
		catch(Exception ex)
		{
			System.err.println("Error. Unable to save Building data to file.");
		}
	}

	public Floor getFloor(int n)
	{
		return floor[n];
	}

	public void setFloor(Floor[] floor)
	{
		this.floor = floor;
	}

	public int getEntranceFloor()
	{
		return entranceFloor;
	}

	public void setEntranceFloor(int entranceFloor)
	{
		this.entranceFloor = entranceFloor;
	}
}
