package world.model;

import java.awt.Color;
import java.awt.Dimension;

public abstract class Monster implements java.io.Serializable

{
	boolean hasMoved;
	public Monster()
	{
		hasMoved = false;
	}
	
	public boolean HasMoved()
	{
		return hasMoved;
	}

	public void setHasMoved(boolean hasMoved)
	{
		this.hasMoved = hasMoved;
	}

	public Dimension move(Room room,Dimension Position)
	{
		return null;
	}
	
	public Color getColor()
	{
		return Color.WHITE;
	}

	

}
