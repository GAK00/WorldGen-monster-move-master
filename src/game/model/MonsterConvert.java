package game.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MonsterConvert
{
	public static dungeon.model.Monster convert(world.model.Monster toConvert)
	{
		Class<?> nMonsterClass;
		try
		{
			System.out.println(toConvert.getClass().getName());
			nMonsterClass = Class.forName("dungeon.model."+toConvert.getClass().getSimpleName());
			Constructor<?> nMonsterConstructor = nMonsterClass.getConstructor();
			Object object = nMonsterConstructor.newInstance();
			dungeon.model.Monster toReturn = (dungeon.model.Monster) object;
			System.out.println(toReturn);
			return toReturn;
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		} catch (SecurityException e)
		{
			e.printStackTrace();
		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return null;
		
	}
}
