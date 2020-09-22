package de.jjl.gsg.item;

import de.jjl.gsg.tile.RockTile;
import de.jjl.gsg.tile.Tile;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class RockItem extends Item
{
	private static WritableImage image = creImage();

	@Override
	public Tile getTile()
	{
		return new RockTile();
	}

	@Override
	public boolean isPlaceable()
	{
		return false;
	}

	@Override
	public Image getInventoryIcon()
	{
		return image;
	}

	private static WritableImage creImage()
	{
		Canvas canvas = new Canvas(50, 50);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		gc.setFill(Color.DARKGRAY);
		gc.setStroke(Color.BLACK);
		gc.fillOval(10, 10, 30, 30);
		gc.strokeOval(10, 10, 30, 30);

		return canvas.snapshot(null, null);
	}
}
