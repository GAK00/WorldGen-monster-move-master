package dungeon.controller;

import game.controller.GameController;

public class DungeonRunner 
{
	public static void main(String[] args)
	{
		GameController dungeonAppController = new GameController();
		dungeonAppController.start();
	}
}
