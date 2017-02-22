package world.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicBox implements Runnable,Serializable
{
	private Thread thread;
	private FileHandler fileHandler;
	private String fileName;
	private Clip clip;
	boolean loop;

	public MusicBox(FileHandler fileHandler, String fileName,boolean loop)
	{
		this.fileHandler = fileHandler;
		this.fileName = fileName;
		this.loop = loop;
	}
	
	public void changeDc()
	{
		try
		{
			thread.sleep(5000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(-20.0f);
	}
	public void run()
	{
		String file = fileHandler.getDirectory() + fileName;

		AudioInputStream audio;
		try
		{
			audio = AudioSystem.getAudioInputStream(new File(file));
			Clip sound = AudioSystem.getClip();
			clip = sound;
			sound.open(audio);
			sound.start();
			System.out.println("soundPlayed");
			if(loop){
			sound.loop(sound.LOOP_CONTINUOUSLY);}
			
		} catch (UnsupportedAudioFileException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void startThread()
	{
		System.out.println("new Thread");
		thread = new Thread(this, "MusicBox");
		thread.start();
	}
}
