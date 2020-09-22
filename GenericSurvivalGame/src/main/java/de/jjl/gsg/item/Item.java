package de.jjl.gsg.item;

import de.jjl.gsg.tile.Tile;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class Item
{
	public Image getInventoryIcon()
	{
		Canvas canvas = new Canvas(50, 50);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Paint on it
		gc.setFill(Color.PURPLE);
		gc.fillRect(0, 0, 50, 50);
		gc.setFill(Color.BLACK);
		gc.setFont(new Font(57));
		gc.fillText("?", 12, 45, 50);

		// NOTE: The canvas must be part of a Scene for the following to work properly,
		// omitted for brevity

		// Obtain a snapshot of the canvas
		WritableImage image = canvas.snapshot(null, null);

		return image;
	}

	public abstract Tile getTile();

	public abstract boolean isPlaceable();
}
