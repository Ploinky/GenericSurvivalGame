package de.jjl.gsg.tile;

public class TileLevel
{
	private Tile[] tiles = new Tile[Tile.ROWS * Tile.COLUMNS];

	public TileLevel()
	{
	}

	public Tile getTile(int index)
	{
		return tiles[index];
	}

	public int getSize()
	{
		return tiles.length;
	}

	public void setTile(int index, Tile tile)
	{
		tiles[index] = tile;
	}

	public Tile[] tiles()
	{
		return tiles;
	}
}
