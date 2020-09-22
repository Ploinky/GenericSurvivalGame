package de.jjl.gsg;

import java.util.Random;

import de.jjl.gsg.player.Inventory;
import de.jjl.gsg.tile.GrassTile;
import de.jjl.gsg.tile.RockTile;
import de.jjl.gsg.tile.Tile;
import de.jjl.gsg.tile.TileLevel;
import de.jjl.gsg.tile.TileMap;
import de.jjl.gsg.tile.TreeTile;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
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
//				handleCursor();
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

					if (t.getBounds().contains(new Point2D(e.getSceneX(), e.getSceneY())))
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
	}

	protected void paintScreen()
	{
		GraphicsContext graphics = canvas.getGraphicsContext2D();

		graphics.clearRect(0, 0, 1400, 800);

		Tile playerTile = getCurrentPlayerTile();

		for (int i = 0; i < tileMap.tileLevels().length; i++)
		{
			TileLevel lvl = tileMap.getLevel(i);

			for (Tile t : lvl.tiles())
			{
				if (t != null)
				{
					t.paintOn(graphics);

					if (t == playerTile)
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
		}

	}

	protected void handlePlayerInput()
	{
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

		playerOffsetY = t.getZ() * Tile.HEIGHT / 2;

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

//			System.out.println("Tile# :" + i + ", tileX: " + tileX + ", tileY: " + tileY);

			tileMap.getLevel(0).setTile(i, new GrassTile(tileX, tileY, 0));
		}

		for (int i = 0; i < tileMap.getLevel(1).getSize(); i++)
		{
			int rowNum = i / Tile.COLUMNS;
			int colNum = i % Tile.COLUMNS;
			tileX = 700 + (colNum - rowNum) * (Tile.WIDTH / 2);
			tileY = 100 + (colNum + rowNum) * (Tile.HEIGHT / 2);

			if (r.nextInt() % 10 == 0)
			{
				tileMap.getLevel(1).setTile(i, new TreeTile(tileX, tileY, 1));
			}
			if (r.nextInt() % 16 == 0)
			{
				tileMap.getLevel(1).setTile(i, new RockTile(tileX, tileY, 1));
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

		System.out.println(tileIndex);
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
