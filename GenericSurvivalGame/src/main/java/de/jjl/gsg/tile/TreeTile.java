package de.jjl.gsg.tile;

import javafx.scene.image.Image;

public class TreeTile extends SpriteTile
{
	public TreeTile(int x, int y, int z, int index)
	{
		super(false, x, y, z, index);
	}

	@Override
	protected Image creImage()
	{
		return new Image(TreeTile.class.getResourceAsStream("/img/tile/tree.png"));
	}

	@Override
	protected int getDrawX()
	{
		return (int) (getX() - img.getWidth() / 2);
	}

	@Override
	protected int getDrawY()
	{
		return (int) (getY() - img.getHeight() - (getZ() - 1) * Tile.ZHEIGHT);
	}
}
