package de.jjl.gsg.item;

import de.jjl.gsg.tile.Tile;
import de.jjl.gsg.tile.WoodTile;
import javafx.scene.image.Image;

public class WoodItem extends Item
{
	private static Image image = creImage();

	@Override
	public Tile getTile(int x, int y, int z, int index)
	{
		return new WoodTile(x, y, z, index);
	}

	private static Image creImage()
	{
		return new Image(WoodItem.class.getResourceAsStream("/img/item/icon/wood.png"));
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
}
