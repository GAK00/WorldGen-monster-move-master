package world.model;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;

public class Goblin extends Monster
{
	private Dimension playerPos;
	private Random rand;
	private int count;
	public Goblin()
	{
		super();
		count = 0;
		rand = new Random();
	}

	@Override
	public Color getColor()
	{
		return Color.blue;
	}

	@Override
	public Dimension move(Room room, Dimension Position)
	{
		
		if(count == 1)
		{
		count = 0;
		playerPos = room.getOccupyiedTile();

		ArrayList<Dimension> validPositions = new ArrayList<Dimension>();
		if (checkDimension(new Dimension(Position.width + 1, Position.height), room, Position))
		{
			validPositions.add(new Dimension(Position.width + 1, Position.height));
		}
		if (checkDimension(new Dimension(Position.width - 1, Position.height), room, Position))
		{
			validPositions.add(new Dimension(Position.width - 1, Position.height));
		}
		if (checkDimension(new Dimension(Position.width, Position.height + 1), room, Position))
		{
			validPositions.add(new Dimension(Position.width, Position.height + 1));
		}
		if (checkDimension(new Dimension(Position.width, Position.height - 1), room, Position))
		{
			validPositions.add(new Dimension(Position.width, Position.height - 1));
		}

		if (validPositions.size() != 0)
		{
			Dimension moveTo = validPositions.get(rand.nextInt(validPositions.size()));
			return moveTo;
		} else
		{
			ArrayList<Dimension> validPositions2 = new ArrayList<Dimension>();
			if (checkDimensionEasier(new Dimension(Position.width + 1, Position.height), room, Position))
			{
				validPositions.add(new Dimension(Position.width + 1, Position.height));
			}
			if (checkDimensionEasier(new Dimension(Position.width - 1, Position.height), room, Position))
			{
				validPositions.add(new Dimension(Position.width - 1, Position.height));
			}
			if (checkDimensionEasier(new Dimension(Position.width, Position.height + 1), room, Position))
			{
				validPositions.add(new Dimension(Position.width, Position.height + 1));
			}
			if (checkDimensionEasier(new Dimension(Position.width, Position.height - 1), room, Position))
			{
				validPositions.add(new Dimension(Position.width, Position.height - 1));
			}

			if (validPositions.size() != 0)
			{
				Dimension moveTo = validPositions2.get(rand.nextInt(validPositions.size()));
				return moveTo;
			} else
			return null;
		}}
		else
		{
			count++;
			return null;
		}

	}

	private boolean checkDimension(Dimension pos, Room room, Dimension ogPos)
	{
		Tile tile = room.getTile(pos);
		return tile != null && tile.canCross() && !tile.gethasMonster() && !tile.isInhabited() && !tile.getIsExit()
				&& (Math.abs((int) (playerPos.getWidth() - pos.getWidth()))
						+ Math.abs((int) (playerPos.getHeight() - pos.getHeight())) < (Math.abs((int) (playerPos.getWidth() - ogPos.getWidth()))
								+ Math.abs((int) (playerPos.getHeight() - ogPos.getHeight()))));
	}
	private boolean checkDimensionEasier(Dimension pos, Room room, Dimension ogPos)
	{
		Tile tile = room.getTile(pos);
		return tile != null && tile.canCross() && !tile.gethasMonster() && !tile.isInhabited() && !tile.getIsExit()
				&& (Math.abs((int) (playerPos.getWidth() - pos.getWidth()))
						+ Math.abs((int) (playerPos.getHeight() - pos.getHeight())) <= (Math.abs((int) (playerPos.getWidth() - ogPos.getWidth()))
								+ Math.abs((int) (playerPos.getHeight() - ogPos.getHeight()))));
	}

}
