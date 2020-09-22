package de.jjl.gsg.cursor;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;

public class Cursors
{
	public static final Cursor AXE = new ImageCursor(
			new Image(Cursors.class.getResourceAsStream("/img/cursor/axe.png")));

	public static final Cursor PICKAXE = new ImageCursor(
			new Image(Cursors.class.getResourceAsStream("/img/cursor/pickaxe.png")));
}
