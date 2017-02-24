package world.model;

import java.awt.Dimension;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import world.controls.WorldControl;

public class Room implements java.io.Serializable
{
	private ArrayList<Tile> interior;
	private Dimension roomDimension;
	private transient WorldControl control;
	private double ambushChance;
	private int restAmount;
	private Random rand;
	private boolean discovered;
	private transient Timer timer;
	private transient TimerTask updateMonsters;
	Room room = this;

	public Room(ArrayList<Tile> interior, Dimension roomDimension, WorldControl control)
	{
		this.interior = interior;
		this.roomDimension = roomDimension;
		this.control = control;
		ambushChance = 0.1;
		restAmount = 1;
		rand = new Random();

	}

	public void resetRest()
	{
		restAmount = 1;
	}

	private void increaseAmbushChance()
	{
		if (Double.compare(ambushChance, 2.0) < 0)
		{
			ambushChance += 0.1;
		}
	}

	private void increaseRestAmount()
	{
		restAmount += 1;
	}

	public boolean isAmbush()
	{
		boolean isAmbush = false;
		int modifier = 10;
		if (Double.compare(ambushChance, 1.0) >= 0)
		{
			modifier += restAmount;
		}
		double fightChance = rand.nextInt(modifier) + (ambushChance * restAmount);
		if (restAmount < 3)
		{
			if (Double.compare(fightChance - modifier, 3.75) > 0)
			{
				isAmbush = true;
			}
		} else
		{
			if (Double.compare(fightChance, (restAmount + modifier)) > 0)
			{
				isAmbush = true;
			}
		}
		increaseRestAmount();
		increaseAmbushChance();
		return isAmbush;
	}

	public Tile getTile(Dimension retriveDimension)
	{
		int index = (int) (((retriveDimension.getWidth() - 1) * (roomDimension.getHeight())) + (retriveDimension.getHeight() - 1));
		// System.out.println(index);
		if (index <= interior.size() && index >= 0)
		{
			return interior.get(index);
		} else
		{
			return null;
		}
	}

	public Dimension getSize()
	{
		return roomDimension;
	}

	public void move(int[] direction)
	{

		Dimension currentPos = getOccupyiedTile();
		if (currentPos != null)
		{
			Dimension nextPosition = new Dimension(currentPos.width + direction[0], currentPos.height + direction[1]);
			if (getTile(currentPos).getIsExit() && getTile(currentPos).getDoorDirection()[0] == direction[0] && getTile(currentPos).getDoorDirection()[1] == direction[1])
			{
				// stop timer
				timer.cancel();
				control.updateMap(getTile(currentPos).getDoorDirection(), currentPos);
			} else
			{
				if (getTile(nextPosition).canCross())
				{
					getTile(currentPos).setInhabited(false);
					getTile(nextPosition).setInhabited(true);
					if (getTile(nextPosition).gethasMonster())
					{
						System.out.println("fight");
						getTile(nextPosition).setHasMonster(false);
					}
				}
			}
		}
	}

	public Dimension getOccupyiedTile()
	{
		for (int getX = 1; getX <= ((int) roomDimension.getWidth()); getX++)
		{
			for (int getY = 1; getY <= ((int) roomDimension.getHeight()); getY++)
			{
				if (getTile(new Dimension(getX, getY)).isInhabited())
				{
					return new Dimension(getX, getY);
				}
			}
		}
		return null;
	}

	public void clean()
	{
		for (int getX = 1; getX <= ((int) roomDimension.getWidth()); getX++)
		{
			for (int getY = 1; getY <= ((int) roomDimension.getHeight()); getY++)
			{
				if (getTile(new Dimension(getX, getY)).isInhabited())
				{
					getTile(new Dimension(getX, getY)).setInhabited(false);
				}
			}
		}
	}

	public void entered()
	{

		timer = new Timer();
		if (!discovered)
		{
			discovered = true;
		}
		// start monster timer

		updateMonsters = new TimerTask()
		{

			@Override
			public void run()
			{
				int randnumb = rand.nextInt(5);
				if(randnumb <=4){
				ArrayList<Monster> movedMonsters = new ArrayList<Monster>();
				//System.out.println("move");
				for (int getX = 1; getX <= ((int) roomDimension.getWidth()); getX++)
				{
					for (int getY = 1; getY <= ((int) roomDimension.getHeight()); getY++)
					{
						// System.out.println(getX+","+getY);
						Tile tile = getTile(new Dimension(getX, getY));
						if (tile.gethasMonster())
						{
							Dimension attemptMove = tile.getMonsterType().move(room, new Dimension(getX, getY));
							if (attemptMove != null && !tile.getMonsterType().hasMoved)
							{
								//System.out.println("starts:" + getX + "," + getY + "\nEnds:" + attemptMove);
								Monster monster = tile.getMonsterType();
								monster.hasMoved = true;
								movedMonsters.add(monster);
								tile.setHasMonster(false);
								tile.setMonsterType(null);
								Tile newTile = getTile(attemptMove);
								newTile.setHasMonster(true);
								newTile.setMonsterType(monster);
							}
						}
					}

				}
				for (Monster monster : movedMonsters)
				{
					monster.setHasMoved(false);
				}
				control.updateRender();
			}}
		};
		timer.schedule(updateMonsters,   275, 1 * 275);
		
	}

	public boolean isDiscovered()
	{
		return discovered;
	}

	public void setControlller(WorldControl controller)
	{
		this.control = controller;
		
	}

}
