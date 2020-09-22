package de.jjl.gsg.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class RockTile extends Tile
{
	public RockTile()
	{
		this(0, 0, 0, 0);
	}

	public RockTile(int x, int y, int z, int index)
	{
		super(false, x, y, z, index);
	}

	@Override
	public void paintOn(GraphicsContext graphics)
	{
		double yOffset = getZ() * Tile.ZHEIGHT;

		double x1 = getX();
		double x2 = getX() + Tile.WIDTH / 2;
		double x3 = getX();
		double x4 = getX() - Tile.WIDTH / 2;

		double y1 = -yOffset + getY() - Tile.HEIGHT / 2;
		double y2 = -yOffset + getY();
		double y3 = -yOffset + getY() + Tile.HEIGHT / 2;
		double y4 = -yOffset + getY();

		graphics.setFill(hover ? Color.GRAY.brighter() : Color.GRAY);
		graphics.fillPolygon(new double[] { x1, x2, x3, x4 }, new double[] { y1, y2, y3, y4 }, 4);
		graphics.fillPolygon(new double[] { x4, x3, x3, x4 },
				new double[] { y4, y3, y3 + Tile.ZHEIGHT, y4 + Tile.ZHEIGHT }, 4);
		graphics.fillPolygon(new double[] { x2, x2, x3, x3 },
				new double[] { y2, y2 + Tile.ZHEIGHT, y3 + Tile.ZHEIGHT, y3 }, 4);
		graphics.setStroke(Color.BLACK);
		graphics.strokePolygon(new double[] { x1, x2, x3, x4 }, new double[] { y1, y2, y3, y4 }, 4);
		graphics.strokePolygon(new double[] { x4, x3, x3, x4 },
				new double[] { y4, y3, y3 + Tile.ZHEIGHT, y4 + Tile.ZHEIGHT }, 4);
		graphics.strokePolygon(new double[] { x2, x2, x3, x3 },
				new double[] { y2, y2 + Tile.ZHEIGHT, y3 + Tile.ZHEIGHT, y3 }, 4);

	}

	@Override
	public Polygon getBounds()
	{
		double yOffset = getZ() * Tile.ZHEIGHT;

		double x1 = getX();
		double x2 = getX() + Tile.WIDTH / 2;
		double x3 = getX();
		double x4 = getX() - Tile.WIDTH / 2;

		double y1 = -yOffset + getY() - Tile.HEIGHT / 2;
		double y2 = -yOffset + getY();
		double y3 = -yOffset + getY() + Tile.HEIGHT / 2;
		double y4 = -yOffset + getY();

		return new Polygon(x1, y1, x2, y2, x2, y2 + Tile.ZHEIGHT, x3, y3 + Tile.ZHEIGHT, x4, y4 + Tile.ZHEIGHT, x4, y4);
	}

}
