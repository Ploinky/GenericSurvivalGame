package de.jjl.gsg.item;

import de.jjl.gsg.tile.RockTile;
import de.jjl.gsg.tile.Tile;
import javafx.scene.image.Image;

public class RockItem extends Item
{
	private static Image image = creImage();

	@Override
	public Tile getTile(int x, int y, int z, int index)
	{
		return new RockTile(x, y, z, index);
	}

	private static Image creImage()
	{
		return new Image(WoodItem.class.getResourceAsStream("/img/item/icon/rock.png"));
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
