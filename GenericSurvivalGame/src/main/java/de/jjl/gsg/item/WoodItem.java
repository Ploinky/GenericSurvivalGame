package de.jjl.gsg.item;

import de.jjl.gsg.tile.Tile;
import de.jjl.gsg.tile.WoodTile;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class WoodItem extends Item
{
	private static WritableImage image = creImage();

	@Override
	public Tile getTile()
	{
		return new WoodTile();
	}

	private static WritableImage creImage()
	{
		Canvas canvas = new Canvas(50, 50);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		gc.setFill(Color.SANDYBROWN);
		gc.setStroke(Color.SADDLEBROWN);
		gc.fillRect(0, 0, 10, 50);
		gc.fillRect(10, 0, 10, 50);
		gc.fillRect(20, 0, 10, 50);
		gc.fillRect(30, 0, 10, 50);
		gc.fillRect(40, 0, 10, 50);
		gc.strokeRect(1, 1, 8, 48);
		gc.strokeRect(11, 1, 8, 48);
		gc.strokeRect(21, 1, 8, 48);
		gc.strokeRect(31, 1, 8, 48);
		gc.strokeRect(41, 1, 8, 48);

		return canvas.snapshot(null, null);
	}

	@Override
	public boolean isPlaceable()
	{
		return true;
	}

	@Override
	public Image getInventoryIcon()
	{
		return image;
	}
}