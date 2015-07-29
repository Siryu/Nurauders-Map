package qq.model.people;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import qq.model.building.Node;
import qq.model.building.Room;
import controller.BuildingManager;

public class Adjunct extends Person implements Serializable
{

	private Room office;
	
	public Adjunct(String name, ArrayList<OperatingTime> schedule)
	{
		super(name, schedule, PersonStatus.ADJUNCT);
	}

	@Override
	public void run()
	{
		for(Node n : BuildingManager.getBuilding().getFloor(1).getNodes())
		{
			if(n.getRoomConnection() != null && n.getRoomConnection().equals("199"))
			{
				this.setAtNode(n);
			}
		}
	
		this.setLocationX(this.getAtNode().getX());
		this.setLocationY(this.getAtNode().getY());
		this.setFloor(1);
		OperatingTime event; 
		
		Random rand = new Random();
		long rando = rand.nextInt(15) + 1;
		
		while(true)
		{
			if(!this.isInBuilding())
			{
				try
				{
					this.sleep(1000L * (30L + rando));
				}
				catch (InterruptedException e)
				{
					System.err.println("Thread interupted sleep" + this.getName());
				}
				
				event = checkForEvent();
				if(event != null)
				{ 
					this.setInBuilding(true);
				}
			}
			else
			{
				event = checkForEvent();
				if(event != null) 
				{
					if(!this.getAtNode().getRoomConnection().equals(event.getRoom()))
					{ 
						this.headToPlace(event.getRoom());
					}
					else
					{
						try	{this.sleep(1000L * 30L);} catch (InterruptedException e){};
					}
				}
				
				// if there is not an event leave the school
				else
				{
					checkForEndOfDay();
				}
			}
		}
	}

	public Room getOffice()
	{
		return office;
	}

	public void setOffice(Room office)
	{
		this.office = office;
	}
}
