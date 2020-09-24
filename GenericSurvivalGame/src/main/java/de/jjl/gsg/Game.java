package de.jjl.gsg;

import java.util.Random;

import de.jjl.gsg.cursor.Cursors;
import de.jjl.gsg.item.RockItem;
import de.jjl.gsg.item.WoodItem;
import de.jjl.gsg.player.Inventory;
import de.jjl.gsg.tile.GrassTile;
import de.jjl.gsg.tile.RockTile;
import de.jjl.gsg.tile.Tile;
import de.jjl.gsg.tile.TileLevel;
import de.jjl.gsg.tile.TileMap;
import de.jjl.gsg.tile.TreeTile;
import de.jjl.gsg.tile.WoodTile;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Game extends Application
{
	private boolean[] move = new boolean[4];

	private Canvas canvas;

	private TileMap tileMap = new TileMap();

	private Inventory inventory = new Inventory();

	private Tile hover = null;

	private int playerX = 0;
	private int playerY = 0;

	private int playerOffsetY = 0;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		canvas = new Canvas(1400, 800);

		createMap();

		BorderPane root = new BorderPane();

		root.setCenter(canvas);
		Scene scene = new Scene(root, 1400, 800, true, SceneAntialiasing.BALANCED);

		primaryStage.setScene(scene);

		primaryStage.show();

		AnimationTimer gameLoop = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				handlePlayerInput();

				paintScreen();
//
				handleCursor();
			}
		};

		gameLoop.start();

		primaryStage.addEventFilter(KeyEvent.KEY_RELEASED, e ->
		{
			switch (e.getCode())
			{
			case W:
				move[0] = false;
				break;
			case A:
				move[1] = false;
				break;
			case S:
				move[2] = false;
				break;
			case D:
				move[3] = false;
				break;
			default:
				break;
			}
		});

		primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, e ->
		{
			switch (e.getCode())
			{
			case W:
				move[0] = true;
				break;
			case A:
				move[1] = true;
				break;
			case S:
				move[2] = true;
				break;
			case D:
				move[3] = true;
				break;
			default:
				break;
			}
		});

		primaryStage.addEventFilter(MouseEvent.MOUSE_MOVED, e ->
		{
			for (int i = tileMap.tileLevels().length - 1; i > -1; i--)
			{
				TileLevel lvl = tileMap.getLevel(i);

				for (int j = lvl.tiles().length - 1; j > -1; j--)
				{
					Tile t = lvl.getTile(j);

					if (t == null)
					{
						continue;
					}

					if (t.contains(new Point2D(e.getSceneX(), e.getSceneY())))
					{
						if (hover != null && t != hover)
						{
							hover.hoverEnd();
						}

						if (t != null)
						{
							t.hover();
							hover = t;

						}
						return;
					}
				}
			}

			if (hover != null)
			{
				hover.hoverEnd();
				hover = null;
			}
		});

		primaryStage.addEventFilter(MouseEvent.MOUSE_PRESSED, e ->
		{
			if (e.getButton() == MouseButton.PRIMARY)
			{
				if (hover == null)
				{
					return;
				}

				if (hover instanceof TreeTile || hover instanceof WoodTile)
				{
					inventory.addItem(new WoodItem());
					tileMap.getLevel(hover.getZ()).setTile(hover.getIndex(), null);
				}
				else if (hover instanceof RockTile)
				{
					inventory.addItem(new RockItem());
					tileMap.getLevel(hover.getZ()).setTile(hover.getIndex(), null);
				}
			}
			else if (e.getButton() == MouseButton.SECONDARY)
			{
				if (inventory.getActiveItem() != null && inventory.getActiveItem().isPlaceable() && hover != null)
				{
					if (tileMap.getLevel(hover.getZ() + 1) != null)
					{
						if (tileMap.getLevel(hover.getZ() + 1).getTile(hover.getIndex()) == null)
						{
							tileMap.getLevel(hover.getZ() + 1).setTile(hover.getIndex(), inventory.getActiveItem()
									.getTile(hover.getX(), hover.getY(), hover.getZ() + 1, hover.getIndex()));

							inventory.removeActiveItem();
						}
					}
				}
			}
		});

		primaryStage.addEventFilter(ScrollEvent.SCROLL, e ->
		{
			if (e.getDeltaY() < 0)
			{
				inventory.incActiveStack();
			}
			else
			{
				inventory.decActiveStack();
			}
		});
	}

	protected void handleCursor()
	{
		if (hover != null && (hover instanceof TreeTile || hover instanceof WoodTile))
		{
			canvas.getScene().setCursor(Cursors.AXE);
		}
		else if (hover != null && (hover instanceof RockTile))
		{
			canvas.getScene().setCursor(Cursors.PICKAXE);
		}
		else
		{
			canvas.getScene().setCursor(Cursor.DEFAULT);
		}
	}

	protected void paintScreen()
	{
		GraphicsContext graphics = canvas.getGraphicsContext2D();

		graphics.clearRect(0, 0, 1400, 800);

		paintTiles();
		paintGui();
	}

	private void paintGui()
	{
		GraphicsContext graphics = canvas.getGraphicsContext2D();

		for (int i = 0; i < inventory.getItemStacks().length; i++)
		{
			graphics.setFill(inventory.getActiveStackIndex() == i ? Color.LIGHTGRAY : Color.GRAY);
			graphics.setStroke(Color.BLACK);

			int x = (int) (canvas.getWidth() / 2 - 50 - 100 * (4 - i));
			int y = (int) (canvas.getHeight() - 100);

			graphics.fillRect(x, y, 100, 100);
			graphics.strokeRect(x, y, 100, 100);

			if (inventory.getItemStacks()[i].getItem() != null)
			{
				graphics.drawImage(inventory.getItemStacks()[i].getItem().getInventoryIcon(), x + 5, y + 5, 90, 90);

				graphics.setFill(Color.BLACK);
				graphics.setFont(Font.font("Sans-Serif", FontWeight.BOLD, 18));
				graphics.fillText(String.format("%2s", "" + inventory.getItemStacks()[i].getSize()), x + 75, y + 90);
			}
		}

	}

	protected void paintTiles()
	{
		GraphicsContext graphics = canvas.getGraphicsContext2D();

		Tile playerTile = getCurrentPlayerTile();
		int playerLevel = getCurrentPlayerTile().getZ();

		for (int i = 0; i < tileMap.tileLevels().length; i++)
		{
			TileLevel lvl = tileMap.getLevel(i);

			for (Tile t : lvl.tiles())
			{
				if (t != null)
				{
					if (t.getZ() > playerTile.getZ() && t.contains(new Point2D(playerX + 700, playerY + 100)))
					{
						graphics.setGlobalAlpha(0.3);
					}
					t.paintOn(graphics);
					graphics.setGlobalAlpha(1);
				}
			}

			if (i == playerLevel)
			{
				graphics.setFill(Color.DARKRED);

				graphics.fillPolygon(
						new double[] { playerX + 700, playerX + 700 + 10, playerX + 700, playerX + 700 - 10 },
						new double[] { playerY + 100 - playerOffsetY - 5, playerY + 100 - playerOffsetY,
								playerY + 100 - playerOffsetY + 5, playerY + 100 - playerOffsetY },
						4);
			}
		}

	}

	protected void handlePlayerInput()
	{
		int oldX = playerX;
		int oldY = playerY;

		if (move[0])
		{
			playerY -= 1;
		}
		if (move[1])
		{
			playerX -= 1;
		}
		if (move[2])
		{
			playerY += 1;
		}
		if (move[3])
		{
			playerX += 1;
		}

		Tile t = getCurrentPlayerTile();

		if (t == null)
		{
			playerOffsetY = 0;
			return;
		}

		if (!t.canWalkOn())
		{
			playerX = oldX;
			playerY = oldY;
			t = getCurrentPlayerTile();
		}

		playerOffsetY = t.getZ() * Tile.ZHEIGHT;
	}

	private Tile getCurrentPlayerTile()
	{
		Point2D screen = new Point2D(playerX, playerY);
		Point2D map = sceneToIso(screen);

		return tileAt(map.getX(), map.getY());
	}

	private void createMap()
	{
		int tileX = 0;
		int tileY = 0;

		Random r = new Random();

		for (int i = 0; i < tileMap.getLevel(0).getSize(); i++)
		{
			int rowNum = i / Tile.COLUMNS;
			int colNum = i % Tile.COLUMNS;

			tileX = 700 + (colNum - rowNum) * (Tile.WIDTH / 2);
			tileY = 100 + (colNum + rowNum) * (Tile.HEIGHT / 2);

			tileMap.getLevel(0).setTile(i, new GrassTile(tileX, tileY, 0, i));

			if (r.nextInt() % 10 == 0)
			{
				tileMap.getLevel(1).setTile(i, new TreeTile(tileX, tileY, 1, i));
			}
			else if (r.nextInt() % 12 == 0)
			{
				tileMap.getLevel(1).setTile(i, new GrassTile(tileX, tileY, 1, i));
			}
			else if (r.nextInt() % 16 == 0)
			{
				tileMap.getLevel(1).setTile(i, new RockTile(tileX, tileY, 1, i));
			}
		}

	}

	private Point2D sceneToIso(Point2D screen)
	{
		double sx = screen.getX();
		double sy = screen.getY();

		double x = (sx / (Tile.WIDTH / 2) + sy / (Tile.HEIGHT / 2)) / 2 + 0.5;
		double y = (sy / (Tile.HEIGHT / 2) - sx / (Tile.WIDTH / 2)) / 2 + 0.5;

		return new Point2D(x, y);
	}

	private Tile tileAt(int x, int y)
	{
		int tileIndex = x + y * Tile.COLUMNS;

		if (tileIndex < 0 || tileIndex > tileMap.getLevel(0).getSize())
		{
			return null;
		}
		return tileMap.getTopTileAt(tileIndex);
	}

	private Tile tileAt(double x, double y)
	{
		return tileAt((int) x, (int) y);
	}
}
