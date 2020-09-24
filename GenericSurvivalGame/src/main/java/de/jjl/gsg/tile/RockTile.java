package de.jjl.gsg.tile;

import javafx.scene.image.Image;

public class RockTile extends SpriteTile
{
	public RockTile(int x, int y, int z, int index)
	{
		super(true, x, y, z, index);
	}

	@Override
	protected Image creImage()
	{
		return new Image(TreeTile.class.getResourceAsStream("/img/tile/rock.png"));
	}

	@Override
	protected int getDrawX()
	{
		return (int) (getX() - img.getWidth() / 2);
	}

	@Override
	protected int getDrawY()
	{
		return (int) (getY() - img.getHeight() + Tile.HEIGHT / 2 - (getZ() - 1) * (Tile.ZHEIGHT / 2) - 10);
	}

	@Override
	public boolean canWalkOn()
	{
		return false;
	}
}
