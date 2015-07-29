package qq.model.building;

import java.io.Serializable;


public class Room implements Serializable
{
	private String name;
	private Node roomNode;
	private int x;
	private int y;
	
	public Room(String name, Node roomNode)
	{
		this.name = name;
		this.roomNode = roomNode;
		this.setX(roomNode.getX());
		this.setY(roomNode.getY());
	}
	public Node getRoomNode()
	{
		return roomNode;
	}
	public void setRoomNode(Node roomNode)
	{
		this.roomNode = roomNode;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getX()
	{
		return x;
	}
	public void setX(int x)
	{
		this.x = x;
	}
	public int getY()
	{
		return y;
	}
	public void setY(int y)
	{
		this.y = y;
	}
}
