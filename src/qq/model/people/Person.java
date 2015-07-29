package qq.model.people;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import qq.model.building.Node;
import qq.model.building.Room;
import controller.BuildingManager;


public abstract class Person extends Thread implements Serializable
{
	private String personName;
	private PersonStatus pstatus;
	private int locationX;
	private int locationY;
	private int floor = 1;
	private boolean inBuilding = false;
	private Node atNode;
	
	private static final int UPDATE_RATE = 30;
	private static final int MOVE_SPEED = 3;
	
	ArrayList<OperatingTime> operatingTime;
	
	public Person(String name, ArrayList<OperatingTime> schedule, PersonStatus pstatus)
	{
		this.setInBuilding(false);
		this.setPersonName(name);
		this.setOperatingTime(schedule);
		this.setPstatus(pstatus);
	}
	
	public abstract void run();
	
	// checks to see if there is an Event for a person, if there is it will return the event, if not it will return null
	protected OperatingTime checkForEvent()
	{
		for(OperatingTime ot : operatingTime)
		{
			if(ot.getStart() - 500 <= (Integer.parseInt(BuildingManager.getTime())) && (Integer.parseInt(BuildingManager.getTime())) < ot.getEnd())
			{
				return ot;
			}
		}
		return null;
	}
	
	// this will set the path for the Person to follow and set them on their way.
	protected void headToPlace(String roomName)
	{
		Room room = null;
		for(Room r : BuildingManager.getBuilding().getFloor(Integer.parseInt(roomName) / 100).getRooms())
		{
			if(r.getName().equals(roomName))
			{
				room = r; 
			}
		}
		this.MoveBetweenFloors(roomName);
		boolean foundPath = false;
		ArrayList<Node> pathway = new ArrayList<Node>();
		ArrayList<Node> connections = new ArrayList<Node>();
		Node lastNode = null;
		Node goodNode = null;
		pathway.add(room.getRoomNode());
		for(Node n : room.getRoomNode().getConnections())
		{
			connections.add(n);
		}
		goodNode = connections.get(0);
		
		while(!foundPath)
		{
			// if there are 2 connections, choose the one you didn't just come from
			if(connections.size() == 2)
			{
				for(Node n : connections)
				{
					if(!n.equals(lastNode))
					{
						goodNode = n;
					}
				}
			}
			
			//with more than 2 connections, will choose the one you didn't just come from +
			//won't choose to go into a room unless it's the final destination. +
			//if there's more than that it will find the closest distance node.
			else if(connections.size() > 2)
			{			
				for(Node n : connections)
				{
					if(!n.equals(lastNode) && (n.getRoomConnection() == null || n.getRoomConnection().equals(this.getAtNode().getRoomConnection())))
					{
						if(goodNode == null)
						{
							goodNode = n;
						}
						else
						{
							Point pPoint = new Point(this.locationX, this.locationY);
							Point nPoint = new Point(n.getX(), n.getY());
							Point gnPoint = new Point(goodNode.getX(), goodNode.getY());
							
							if(pPoint.distance(nPoint) <= pPoint.distance(gnPoint))
							{
								goodNode = n;
							}
						}
					}
				}
			}
			
			connections = goodNode.getConnections();
			lastNode = pathway.get(pathway.size() - 1);
			pathway.add(goodNode);
			if(goodNode.getRoomConnection() != null && goodNode.getRoomConnection().equals(this.getAtNode().getRoomConnection()))
			{
				foundPath = true;
			}
			goodNode = null;
		}
		ArrayList<Node> backwards = new ArrayList<Node>();
		for(int i = pathway.size() - 1; i >= 0; i--)
		{
			backwards.add(pathway.get(i));
		}
		move(backwards);
	}
	
	private void move(ArrayList<Node> pathway)
	{
		for(Node n : pathway)
		{
			this.setAtNode(n);
			this.headToPoint(new Point(n.getX(), n.getY()));
			
		}
		
		for(Person p : BuildingManager.getPeople())
		{
			if(this.getAtNode() == p.getAtNode())
			{
				this.setLocationX(this.getLocationX() + 4);
				this.setLocationY(this.getLocationY() + 1);
			}
		}
		
	}

	
	// goes to position incrementally, and exits once Person reaches that Point
	private void headToPoint(Point spot)
	{
		boolean personThere = false;
		while(!personThere)
		{
			if(spot.y > this.getLocationY())
			{
				if(spot.y - this.getLocationY() < 5)
				{
					this.setLocationY(this.getLocationY() + 1);
				}
				else
				{
				this.setLocationY(this.getLocationY() + MOVE_SPEED);
				}
			}
			else if(spot.y < this.getLocationY())
			{
				if(spot.y - this.getLocationY() < 5)
				{
					this.setLocationY(this.getLocationY() - 1);
				}
				else
				{
				this.setLocationY(this.getLocationY() - MOVE_SPEED);
				}
			}
			
			if(spot.x > this.getLocationX())
			{
				if(spot.x - this.getLocationX() < 5)
				{
					this.setLocationX(this.getLocationX() + 1);
				}
				else
				{
				this.setLocationX(this.getLocationX() + MOVE_SPEED);
				}
			}
			else if(spot.x < this.getLocationX())
			{
				if(spot.x - this.getLocationX() < 5)
				{
					this.setLocationX(this.getLocationX() - 1);
				}
				else
				{
				this.setLocationX(this.getLocationX() - MOVE_SPEED);
				}
			}
			
			// used to pause between each movement update
			try 
			{
	            Thread.sleep(1000 / UPDATE_RATE);  // milliseconds
	        } 
			catch (InterruptedException ex) { }
			
			// this determines if the Person is at the spot they designate
			if(((int)this.getLocationY() == (int)spot.y) && ((int)this.getLocationX() == (int)spot.x))
			{
				System.out.println("at spot indicated " + this.getPersonName());
				personThere = true;
			}
		}
	}
	
	// this method checks to see if the Person has any more to do at the school in the day and ends them if not
	protected void checkForEndOfDay()
	{
		int endOfDay = 0;
		for(OperatingTime ot : this.getOperatingTime())
		{
			endOfDay = (ot.getEnd() >= (Integer.parseInt(BuildingManager.getTime())) ? ot.getEnd() : 0);		
		}
		
		if(endOfDay == 0 || this.pstatus == PersonStatus.ADJUNCT)
		{
			System.out.println("Thread ending day " + this.getPersonName());
			//for(Room r : BuildingManager.getBuilding().getFloor(1).getRooms())
			//{
				//if(r.getName().equals("199") && !this.getAtNode().getRoomConnection().getName().equals("199"))
				//{
					this.headToPlace("199");
				//}
			//}
			this.setInBuilding(false);
		}
	}
	
	private void MoveBetweenFloors(String room)
	{		
		while(this.getFloor() != Integer.parseInt(room) / 100)
		{
			if(Integer.parseInt(room) / 100 > this.getFloor())
			{
				String stairsUp = "" + ((this.getFloor() * 100) + 98);
				if(Integer.parseInt(stairsUp) / 100 == 0)
				{
					stairsUp = "098"; 
				}
				//for(Room r : BuildingManager.getBuilding().getFloor(this.getFloor()).getRooms())
				//{
					//if(r.getName().equals(stairsUp))
					//{
						this.headToPlace(stairsUp);
						this.setFloor(this.getFloor() + 1);
						stairsUp = "" + ((this.getFloor() * 100) + 97);
						for(Room r2 : BuildingManager.getBuilding().getFloor(this.getFloor()).getRooms())
						{
							if(r2.getName().equals(stairsUp))
							{
								this.setLocationX(r2.getX());
								this.setLocationY(r2.getY());
								this.setAtNode(r2.getRoomNode());
							}
						}
					//}
				//}
			}
			else if(Integer.parseInt(room) / 100 < this.getFloor())
			{
				String stairsDown = "" + ((this.getFloor() * 100) + 97);
				//for(Room r : BuildingManager.getBuilding().getFloor(this.getFloor()).getRooms())
				//{
					//if(r.getName().equals(stairsDown))
					//{
						this.headToPlace(stairsDown);
						this.setFloor(this.getFloor() - 1);
						stairsDown = "" + ((this.getFloor() * 100) + 98);
						if(Integer.parseInt(stairsDown) / 100 == 0)
						{
							stairsDown = "098"; 
						}
						for(Room r2 : BuildingManager.getBuilding().getFloor(this.getFloor()).getRooms())
						{
							if(r2.getName().equals(stairsDown))
							{
								this.setLocationX(r2.getX());
								this.setLocationY(r2.getY());
								this.setAtNode(r2.getRoomNode());
							}
						}
					//}
				//}
			}
		}
	}

	public String getPersonName()
	{
		return personName;
	}

	public void setPersonName(String name)
	{
		this.personName = name;
	}

	public PersonStatus getPstatus()
	{
		return pstatus;
	}

	public void setPstatus(PersonStatus pstatus)
	{
		this.pstatus = pstatus;
	}

	public int getLocationX()
	{
		return locationX;
	}

	public void setLocationX(int locationX)
	{
		this.locationX = locationX;
	}

	public int getLocationY()
	{
		return locationY;
	}

	public void setLocationY(int locationY)
	{
		this.locationY = locationY;
	}

	public int getFloor()
	{
		return floor;
	}

	public void setFloor(int floor)
	{
		this.floor = floor;
	}

	public boolean isInBuilding()
	{
		return inBuilding;
	}

	public void setInBuilding(boolean inBuilding)
	{
		this.inBuilding = inBuilding;
	}

	public void addOperatingTime(OperatingTime ot)
	{
		this.getOperatingTime().add(ot);
	}
	
	public void removeOperatingtime(OperatingTime ot)
	{
		this.getOperatingTime().remove(ot);
	}
	
	public ArrayList<OperatingTime> getOperatingTime()
	{
		return operatingTime;
	}

	public void setOperatingTime(ArrayList<OperatingTime> operatingTime)
	{
		this.operatingTime = operatingTime;
	}

	public Node getAtNode()
	{
		return atNode;
	}

	public void setAtNode(Node atNode)
	{
		this.atNode = atNode;
	}
}
