package dungeon.model;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileHandler
{

	private File mainDirectory;

	public FileHandler()
	{
		try
		{
			String parentPath = getParentDirectory();
			String Path = parentPath +"/"+"GameData"+"/";
			if(Files.exists(Paths.get(Path)))
			{
				mainDirectory = new File(Path);
			}
			else
			{
				mainDirectory = new File(Path);
				mainDirectory.mkdir();
			}
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}

	public FileHandler(String path)
	{
		String parentPath = path;
		String Path = parentPath +"/"+"GameData"+"/";
		if(Files.exists(Paths.get(Path)))
		{
			mainDirectory = new File(Path);
		}
		else
		{
			mainDirectory = new File(Path);
			mainDirectory.mkdir();
		}
	}

	private String getParentDirectory() throws UnsupportedEncodingException
	{
		String parentPath = "";
		URL childPath = FileHandler.class.getProtectionDomain().getCodeSource().getLocation();
		String childFilePath = URLDecoder.decode(childPath.getFile(), "UTF-8");
		parentPath = new File(childFilePath).getParentFile().getPath();
		return parentPath;
	}
	public void writeData(String fileName,String stringData)
	{
		byte data[] = stringData.getBytes();
		Path path = Paths.get(mainDirectory.getPath()+"/"+fileName+".txt");
		try
		{
			Files.write(path, data, StandardOpenOption.CREATE);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public String readData(String fileName)
	{
		String Data = "";
		Path path = Paths.get(mainDirectory.getPath()+"/"+fileName+".txt");
		try
		{
			Data = new String(Files.readAllBytes(path),"UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return Data;
	}

}
