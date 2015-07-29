package qq.model.people;

import java.io.Serializable;

import qq.model.building.Room;

public class OperatingTime implements Serializable
{
	private int start = 0;
	private int end = 0;
	private String room;
	
	public int getStart()
	{
		return start;
	}
	public void setStart(int start)
	{
		this.start = start;
	}
	public int getEnd()
	{
		return end;
	}
	public void setEnd(int end)
	{
		this.end = end;
	}
	public String getRoom()
	{
		return room;
	}
	public void setRoom(String room)
	{
		this.room = room;
	}
}
