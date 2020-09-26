package de.jjl.gsg.item;

import java.util.ArrayList;
import java.util.List;

public class ItemStack
{
	private List<Item> items = new ArrayList<>();

	public int getSize()
	{
		return items.size();
	}

	public void addItem(Item item)
	{
		if (!items.isEmpty() && !items.get(0).getClass().isInstance(item))
		{
			items.clear();
		}

		if (items.size() < 64)
		{
			items.add(item);
		}
	}

	public Item getItem()
	{
		if (items.isEmpty())
		{
			return null;
		}

		return items.get(0);
	}

	public void removeItem()
	{
		items.remove(0);
	}

	public void removeItems(Integer value)
	{
		for (int i = 0; i < value; i++)
		{
			items.remove(0);
		}
	}
}
