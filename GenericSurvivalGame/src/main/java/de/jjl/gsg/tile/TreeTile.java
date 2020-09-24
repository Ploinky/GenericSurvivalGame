package de.jjl.gsg.tile;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public class TreeTile extends Tile
{
	private Image img = creImage();

	private int model;

	private static int last = 0;

	public TreeTile(int x, int y, int z, int index)
	{
		super(false, x, y, z, index);
	}

	protected Image creImage()
	{
		model = last;

		last = last == 0 ? 1 : 0;

		if (model == 0)
		{
			return new Image(TreeTile.class.getResourceAsStream("/img/tile/tree.png"));
		}
		else
		{
			return new Image(TreeTile.class.getResourceAsStream("/img/tile/tree2.png"));
		}
	}

	protected int getDrawX()
	{
		if (model == 0)
		{
			return (int) (getX() - img.getWidth() / 2);
		}
		else
		{
			return (int) (getX() - img.getWidth() / 2);
		}
	}

	protected int getDrawY()
	{
		if (model == 0)
		{
			return (int) (getY() - img.getHeight() - (getZ() - 1) * Tile.ZHEIGHT);
		}
		else
		{
			return (int) (getY() - img.getHeight() - (getZ() - 1) * Tile.ZHEIGHT);
		}
	}

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
