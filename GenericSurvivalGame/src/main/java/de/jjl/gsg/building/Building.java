package de.jjl.gsg.building;

import java.util.Map;

import de.jjl.gsg.base.Drawable;
import de.jjl.gsg.item.ItemType;
import de.jjl.gsg.tile.Tile;

public abstract class Building extends Tile implements Drawable
{
	public abstract Map<ItemType, Integer> getCost();

	public Building(int x, int y, int z, int index)
	{
		super(false, x, y, z, index);
	}
}
