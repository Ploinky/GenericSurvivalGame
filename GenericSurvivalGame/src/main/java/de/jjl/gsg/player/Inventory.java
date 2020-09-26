package de.jjl.gsg.player;

import java.util.Map;
import java.util.Map.Entry;

import de.jjl.gsg.item.Item;
import de.jjl.gsg.item.ItemStack;
import de.jjl.gsg.item.ItemType;

public class Inventory
{
	private ItemStack[] items = new ItemStack[9];

	private int activeStack = 0;

	public Inventory()
	{
		for (int i = 0; i < items.length; i++)
		{
			items[i] = new ItemStack();
		}
	}

	public void addItem(Item item)
	{
		int stack = -1;

		for (int i = 0; i < items.length; i++)
		{
			ItemStack itemStack = items[i];

			if (itemStack.getItem() != null && itemStack.getItem().getClass().isInstance(item))
			{
				stack = i;
				break;
			}
		}

		if (stack != -1)
		{
			items[stack].addItem(item);
			return;
		}

		for (int i = 0; i < items.length; i++)
		{
			ItemStack itemStack = items[i];

			if (itemStack.getItem() == null)
			{
				stack = i;
				break;
			}
		}

		if (stack != -1)
		{
			items[stack].addItem(item);
		}
	}

	public ItemStack[] getItemStacks()
	{
		return items;
	}

	public Item getActiveItem()
	{
		return items[activeStack].getItem();
	}

	public int getActiveStackIndex()
	{
		return activeStack;
	}

	public void incActiveStack()
	{
		activeStack++;

		if (activeStack > 8)
		{
			activeStack = 0;
		}
	}

	public void decActiveStack()
	{
		activeStack--;

		if (activeStack < 0)
		{
			activeStack = 8;
		}
	}

	public void removeActiveItem()
	{
		items[activeStack].removeItem();
	}

	public boolean hasItems(Map<ItemType, Integer> cost)
	{
		boolean hasItem = true;

		for (Entry<ItemType, Integer> entry : cost.entrySet())
		{
			boolean hasType = false;

			for (ItemStack stack : items)
			{
				if (stack.getItem() != null && stack.getItem().getType() == entry.getKey()
						&& stack.getSize() >= entry.getValue())
				{
					hasType = true;
					break;
				}
			}

			if (!hasType)
			{
				hasItem = false;
				break;
			}
		}
		return hasItem;
	}

	public void useItems(Map<ItemType, Integer> cost)
	{
		for (Entry<ItemType, Integer> entry : cost.entrySet())
		{
			for (ItemStack stack : items)
			{
				if (stack.getItem().getType() == entry.getKey() && stack.getSize() >= entry.getValue())
				{
					stack.removeItems(entry.getValue());
					break;
				}
			}
		}
	}
}
