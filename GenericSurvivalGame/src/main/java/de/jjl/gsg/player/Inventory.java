package de.jjl.gsg.player;

import de.jjl.gsg.item.Item;
import de.jjl.gsg.item.ItemStack;

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
}
