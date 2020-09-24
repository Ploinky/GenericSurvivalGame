package de.jjl.gsg.tile;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public abstract class SpriteTile extends Tile
{
	protected SpriteTile(boolean canWalkOn, int x, int y, int z, int index)
	{
		super(canWalkOn, x, y, z, index);
	}

	protected Image img = creImage();

	protected abstract Image creImage();

	@Override
	public void paintOn(GraphicsContext graphics)
	{
		if (hover)
		{
			graphics.setGlobalAlpha(0.8);
		}
		graphics.drawImage(img, getDrawX(), getDrawY());
		graphics.setGlobalAlpha(1);
	}

	protected int getDrawX()
	{
		return (int) (getX() - img.getWidth() / 2);
	}

	protected int getDrawY()
	{
		return (int) (getY() - img.getHeight() / 2 + Tile.HEIGHT / 2);
	}

	@Override
	public boolean contains(Point2D p)
	{
		int chkX = (int) (p.getX() - getDrawX());
		int chkY = (int) (p.getY() - getDrawY());

		if (chkX < 0 || chkX > img.getWidth() - 1 || chkY < 0 || chkY > img.getHeight() - 1)
		{
			return false;
		}
		PixelReader reader = img.getPixelReader();

		int color = reader.getArgb(chkX, chkY);

		int alpha = color & 61440;

		return alpha != 0;
	}
}
