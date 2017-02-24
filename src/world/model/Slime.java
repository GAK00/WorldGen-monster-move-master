package world.model;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;

public class Slime extends Monster
{
	private Dimension playerPos;
	private Random rand;
	private int count;
	MusicBox jumpSound;

	Slime()
	{
		super();
		rand = new Random();
		count = 0;
		System.out.println("ini");
		jumpSound = new MusicBox("slime.wav", false);

	}

	@Override
	public Color getColor()
	{
		return Color.green;
	}

	@Override
	public Dimension move(Room room, Dimension Position)
	{

		if (count == 3)
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
				jumpSound.startThread();
				return moveTo;
			} else
			{
				return null;
			}
		} else
		{
			count++;
			return null;
		}

	}

	private boolean checkDimension(Dimension pos, Room room, Dimension ogPos)
	{
		Tile tile = room.getTile(pos);
		return room.getTile(pos) != null && tile.canCross() && !tile.gethasMonster() && !tile.isInhabited() && !tile.getIsExit() && (Math.abs((int) (playerPos.getWidth() - pos.getWidth())) + Math
		        .abs((int) (playerPos.getHeight() - pos.getHeight())) > (Math.abs((int) (playerPos.getWidth() - ogPos.getWidth())) + Math.abs((int) (playerPos.getHeight() - ogPos.getHeight()))));
	}

	private boolean checkDimensionEasier(Dimension pos, Room room, Dimension ogPos)
	{
		Tile tile = room.getTile(pos);
		return tile != null && tile.canCross() && !tile.gethasMonster() && !tile.isInhabited() && !tile.getIsExit() && (Math.abs((int) (playerPos.getWidth() - pos.getWidth())) + Math
		        .abs((int) (playerPos.getHeight() - pos.getHeight())) >= (Math.abs((int) (playerPos.getWidth() - ogPos.getWidth())) + Math.abs((int) (playerPos.getHeight() - ogPos.getHeight()))));
	}

}
