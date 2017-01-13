package world.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import world.controls.WorldControl;
import world.model.Tile;

public class gameFrame extends JFrame
{
	private WorldControl controller;
	private WorldPanel panel;

	public gameFrame(WorldControl controller, int width, int height, int posx, int posy)
	{
		super();
		this.controller = controller;
		panel = new WorldPanel(controller, width, height);
		setup(width, height, posx, posy);
		setupListeners();
	}

	private void setup(int width, int height, int posx, int posy)
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Dungeon");
		this.setSize(width, height);
		this.setLocation(posx, posy);
		this.setContentPane(panel);
		this.setVisible(true);
	}

	public JPanel getPanel()
	{
		return panel;
	}

	private void setupListeners()
	{
		this.addComponentListener(new ComponentListener()
		{

			@Override
			public void componentHidden(ComponentEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void componentMoved(ComponentEvent arg0)
			{
				controller.saveWindowData();

			}

			@Override
			public void componentResized(ComponentEvent arg0)
			{
				if (controller.getFrame().getWidth() < (int) (Tile.getMinimumSize() * controller.getMap().getCurrentRoom().getSize().getWidth())
				        || controller.getFrame().getHeight() < (int) (Tile.getMinimumSize() * controller.getMap().getCurrentRoom().getSize().getHeight()))
				{
					System.out.println("no");
					controller.getFrame().setSize(new Dimension((int) controller.getMap().getCurrentRoom().getSize().getWidth() * Tile.getMinimumSize(),
					        (int) controller.getMap().getCurrentRoom().getSize().getHeight() * Tile.getMinimumSize()));
				}

				panel.resized(controller.getFrame().getWidth(), controller.getFrame().getHeight());
				controller.saveWindowData();

			}

			@Override
			public void componentShown(ComponentEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				controller.saveMap();
			}
		});

	}
	public int[] getScreenSize()
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		int[] toReturn = {width,height};
		return toReturn;

	}
}
