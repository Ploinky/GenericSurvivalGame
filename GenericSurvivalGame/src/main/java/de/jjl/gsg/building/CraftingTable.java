package de.jjl.gsg.building;

import java.util.HashMap;
import java.util.Map;

import de.jjl.gsg.item.ItemType;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CraftingTable extends Building
{
	private static Image img = creImage();

	public CraftingTable(int x, int y, int z, int index)
	{
		super(x, y, z, index);
	}

	private static Image creImage()
	{
		return new Image(CraftingTable.class.getResourceAsStream("/img/building/craftingtable.png"));
	}

	private Map<ItemType, Integer> cost;

	@Override
	public Map<ItemType, Integer> getCost()
	{
		if (cost == null || cost.isEmpty())
		{
			cost = new HashMap<>();

			cost.put(ItemType.WOOD, 4);
		}

		return cost;
	}

	@Override
	public void paintOn(GraphicsContext graphics)
	{
		graphics.drawImage(img, getDrawX(), getDrawY());
		graphics.setGlobalAlpha(1);
	}

	protected int getDrawX()
	{
		return (int) (getX() - img.getWidth() / 2);
	}

	protected int getDrawY()
	{
		return (int) (getY() - img.getHeight() / 2);
	}

	@Override
	public boolean contains(Point2D p)
	{
		return false;
	}

}
