package de.jjl.gsg.tile;

import javafx.scene.image.Image;

public class GrassTile extends SpriteTile
{

	public GrassTile(int x, int y, int z, int index)
	{
		super(true, x, y, z, index);
	}

	@Override
	protected Image creImage()
	{
		return new Image(TreeTile.class.getResourceAsStream("/img/tile/grass.png"));
	}

	@Override
	protected int getDrawY()
	{
		// TODO Auto-generated method stub
		return (int) (getY() - Tile.HEIGHT / 2 - getZ() * Tile.HEIGHT);
	}
}
