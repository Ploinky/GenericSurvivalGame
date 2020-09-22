package de.jjl.gsg.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Polygon;

public abstract class Tile
{
	protected boolean hover = false;

	public static final int WIDTH = 100;

	public static final int HEIGHT = 50;

	public static final int ZHEIGHT = HEIGHT / 2;

	public static final int COLUMNS = 10;
	public static final int ROWS = 10;

	private boolean canWalkOn;

	private int x;

	private int y;

	private int z;

	private int index;

	protected Tile(boolean canWalkOn, int x, int y, int z, int index)
	{
		this.canWalkOn = canWalkOn;
		this.x = x;
		this.y = y;
		this.z = z;
		this.index = index;
	}

	public int getIndex()
	{
		return index;
	}

	public boolean canWalkOn()
	{
		return canWalkOn;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getZ()
	{
		return z;
	}

	public void setZ(int z)
	{
		this.z = z;
	}

	public int getInteractDistance()
	{
		return 200;
	}

	public abstract void paintOn(GraphicsContext graphics);

	public void hover()
	{
		hover = true;
	}

	public void hoverEnd()
	{
		hover = false;
	}

	public abstract Polygon getBounds();
}
