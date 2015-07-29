package qq.model.people;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import qq.model.building.Node;
import qq.model.building.Room;
import controller.BuildingManager;

public class Teacher extends Person implements Serializable
{
	private Room office;
	
	public Teacher(String name, ArrayList<OperatingTime> schedule)
	{
		super(name, schedule, PersonStatus.TEACHER);
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
					try
					{
						if(this.getAtNode().getRoomConnection().equals(this.operatingTime.get(0).getRoom()))
						{
							System.out.println("Thread Sleeping " + this.getName());
							this.sleep(1000L * 30L);
						}
						else
						{
							for(Room r : BuildingManager.getBuilding().getFloor(Integer.parseInt(this.operatingTime.get(0).getRoom()) / 100).getRooms())
							{
								if(r.getName().equals(this.operatingTime.get(0).getRoom()))
								{
									this.headToPlace(this.operatingTime.get(0).getRoom());
									break;
								}
							}
						}
					} 
					catch (InterruptedException e)
					{
						System.err.println("Interupted a Thread:" + this.getPersonName());
					}
				}
				this.checkForEndOfDay();
			}
		}
	}
}