package de.jjl.gsg.tile;

public class TileMap
{
	private TileLevel[] tileLevels = new TileLevel[2];

	public TileMap()
	{
		for (int i = 0; i < tileLevels.length; i++)
		{
			tileLevels[i] = new TileLevel();
		}
	}

	public TileLevel getLevel(int level)
	{
		return tileLevels[level];
	}

	public TileLevel[] tileLevels()
	{
		return tileLevels;
	}

	public Tile getTopTileAt(int index)
	{
		for (int i = tileLevels.length - 1; i > -1; i--)
		{
			if (tileLevels[i].getTile(index) != null)
			{
				return tileLevels[i].getTile(index);
			}
		}

		return null;
	}

	public int levelCount()
	{
		return tileLevels.length;
	}
}
