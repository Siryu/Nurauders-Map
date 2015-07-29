package qq.model.people;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import qq.model.building.Node;
import qq.model.building.Room;
import controller.BuildingManager;

public class Faculty extends Person implements Serializable
{

	public Faculty(String name, ArrayList<OperatingTime> schedule)
	{
		super(name, schedule, PersonStatus.FACULTY);
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
		
		Random rand = new Random();
		long rando = rand.nextInt(15) + 1;
		
		OperatingTime event; 
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
						System.out.println("Sleeping at " + this.getAtNode().getRoomConnection() + " Named: " + this.getPersonName());
						try	{this.sleep(1000L * 60L);} catch (InterruptedException e){};
					}
				}
				this.checkForEndOfDay();
			}
		}
	}
}