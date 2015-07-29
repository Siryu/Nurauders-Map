package qq.model.building;

import java.io.Serializable;
import java.util.ArrayList;

public class Node implements Serializable
{
	private int x;
	private int y;
	private ArrayList<Node> connections;
	private String roomConnection;
	
	public Node(int x, int y)
	{
		this.connections = new ArrayList<Node>();
		this.setX(x);
		this.setY(y);
	}
	
	public void addNode(Node node)
	{
		this.connections.add(node);
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

	public ArrayList<Node> getConnections()
	{
		return connections;
	}

	public void setConnections(ArrayList<Node> connections)
	{
		this.connections = connections;
	}

	public String getRoomConnection()
	{
		return roomConnection;
	}

	public void setRoomConnection(String roomConnection)
	{
		this.roomConnection = roomConnection;
	}
	
	
}
