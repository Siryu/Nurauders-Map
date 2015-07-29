package qq.model.people;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import qq.model.building.Node;
import qq.model.building.Room;
import controller.BuildingManager;

public class Student extends Person implements Serializable
{	
	public Student(String name, ArrayList<OperatingTime> schedule)
	{
		super(name, schedule, PersonStatus.STUDENT);
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
				
				// if there is not an event going on the student will go to the commons where he will sleep
				// in 30 second intervals waiting for a new event to arise
				else
				{
					if(!this.getAtNode().getRoomConnection().equals("100"))
					{
						this.headToPlace("100");
					}
					
					else
					{
						//for(Room r : BuildingManager.getBuilding().getFloor(1).getRooms())
						//{
							//if(r.getName().equals("100"))
							//{System.out.println("Thread Sleeping " + this.getName());
						try
						{
							System.out.println("Sleeping at " + this.getAtNode().getRoomConnection() + " Named: " + this.getPersonName());
							this.sleep(1000 * 30);
						} catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
							//}
						//}
					}
					 
				}
				this.checkForEndOfDay();
			}
		}
	}
}
