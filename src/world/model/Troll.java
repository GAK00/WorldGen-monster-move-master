package world.model;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;

public class Troll extends Monster 
{
	Random rand;
	public Troll()
	{
		super();
		rand = new Random();
	}
	
	
	@Override
	public Color getColor()
	{
		return Color.yellow;
	}
	
	@Override
	public Dimension move(Room room,Dimension Position)
	{
		
		ArrayList<Dimension> validPositions = new ArrayList<Dimension>();
		if(checkDimension(new Dimension(Position.width+1,Position.height),room))
		{
			validPositions.add(new Dimension(Position.width+1,Position.height));
		}
		if(checkDimension(new Dimension(Position.width-1,Position.height),room))
		{
			validPositions.add(new Dimension(Position.width-1,Position.height));
		}
		if(checkDimension(new Dimension(Position.width,Position.height+1),room))
		{
			validPositions.add(new Dimension(Position.width,Position.height+1));
		}
		if(checkDimension(new Dimension(Position.width,Position.height-1),room))
		{
			validPositions.add(new Dimension(Position.width,Position.height-1));
		}
		
		if(validPositions.size()!=0)
		{
			Dimension moveTo = validPositions.get(rand.nextInt(validPositions.size()));
			return moveTo;
		}
		else
		{
			return null;
		}
		
		
		
	}
	private boolean checkDimension(Dimension pos,Room room)
	{
		Tile tile = room.getTile(pos);
		return tile!=null&&tile.canCross()&&!tile.gethasMonster()&&!tile.isInhabited()&&!tile.getIsExit();
	}

}