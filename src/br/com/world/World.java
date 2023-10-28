/**
 * 
 */
package br.com.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import br.com.entities.*;
import br.com.graficos.Camera;
import br.com.main.Game;

/**
 * 
 */

public class World {

	public static Tile[] tiles;

	public static int WIDTH, HEIGHT, TILE_SIZE = 16;	

	public World(String path) {
		try {
			BufferedImage mapa = ImageIO.read(getClass().getResource(path));
			int pixels[] = new int[mapa.getWidth() * mapa.getHeight()];
			WIDTH = mapa.getWidth();
			HEIGHT = mapa.getHeight();
			tiles = new Tile[mapa.getWidth() * mapa.getHeight()];
			mapa.getRGB(0, 0, mapa.getWidth(), mapa.getHeight(), pixels, 0, mapa.getWidth());
			for (int xx = 0; xx < mapa.getWidth(); xx++) {
				for (int yy = 0; yy < mapa.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * mapa.getWidth())];
					if (pixelAtual == 0xFF000000)/* PRETO */ {
						// Chão
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Entity.TILE_FLOOR);
					} else if (pixelAtual == 0xFFFFFFFF) /* BRANCO */ {
						// Parede
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Entity.TILE_WALL);
					} else if (pixelAtual == 0xFF0026FF) /* AZUL */ {
						// Player
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Entity.TILE_FLOOR);
					} else if (pixelAtual == 0xFFFF6A00) /* LARANJA */ {
						// Weapon
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Entity.TILE_FLOOR);
						Weapon weapon = new Weapon(xx * 16, yy * 16, 16, 16, Entity.TILE_WEAPON);
						Game.entities.add(weapon);
					} else if (pixelAtual == 0xFF00FF21) /* VERDE */ {
						// Life
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Entity.TILE_FLOOR);
						Lifepack life = new Lifepack(xx * 16, yy * 16, 16, 16, Entity.TILE_LIFE);
						life.setMasks(8, 8,8,8);
						Game.entities.add(life);						
					} else if (pixelAtual == 0xFFFF0000) /* VERMELHO */ {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Entity.TILE_FLOOR);
						Enemy enemy = new Enemy(xx * 16, yy * 16, 16, 16, Entity.TILE_ENEMY);
						Game.enemies.add(enemy);
						Game.entities.add(enemy);
						//Game.entities.add(new Enemy(xx * 16, yy * 16, 16, 16, Entity.TILE_ENEMY));
					} else if (pixelAtual == 0xFFB6FF00) /* LIMA */ {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Entity.TILE_FLOOR);
						Magic magic = new Magic(xx * 16, yy * 16, 16, 16, Entity.TILE_RECHARGE);
						magic.setMasks(8, 8, 8, 8);
						Game.entities.add(magic);
					} else {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Entity.TILE_FLOOR);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1+(y1*World.WIDTH)] instanceof WallTile) 
				|| (tiles[x2+(y2*World.WIDTH)] instanceof WallTile)
				|| (tiles[x3+(y3*World.WIDTH)] instanceof WallTile)
				|| (tiles[x4+(y4*World.WIDTH)] instanceof WallTile));
	}

	public void render(Graphics g) {
		int xStart = Camera.x >> 4;
		int yStart = Camera.y >> 4;

		int xFinal = xStart + (Game.WIDTH >> 4);
		int yFinal = yStart + (Game.HEIGHT >> 4);

		for (int xx = xStart; xx <= xFinal; xx++) {
			for (int yy = yStart; yy <= yFinal; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
	}

}
