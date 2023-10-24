/**
 * 
 */
package br.com.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import br.com.entities.*;
import br.com.main.Game;

/**
 * 
 */

public class World {

	private Tile[] tiles;

	public static int WIDTH, HEIGHT;

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
					if (pixelAtual == 0xFF000000)/*PRETO*/ {
						// Chão
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Entity.TILE_FLOOR);
					} else if (pixelAtual == 0xFFFFFFFF) /*BRANCO*/ {
						// Parede
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Entity.TILE_WALL);
					} else if (pixelAtual == 0xFF0026FF) /*AZUL*/{
						// Player
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Entity.TILE_FLOOR);
					} else if (pixelAtual == 0xFFFF6A00) /*LARANJA*/ {
						// Weapon						
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Entity.TILE_FLOOR);
						Game.entities.add(new Item(xx*16, yy*16, 16,16, Entity.TILE_WEAPON));
					} else if (pixelAtual == 0xFF00FF21) /*VERDE*/{
						// Life						
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Entity.TILE_FLOOR);
						Game.entities.add(new Item(xx*16, yy*16, 16,16, Entity.TILE_LIFE));										
					} else if(pixelAtual == 0xFFFF0000) /*VERMELHO*/{
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Entity.TILE_FLOOR);
						Game.entities.add(new Enemy(xx*16, yy*16, 16,16, Entity.TILE_ENEMY));						
					} else if(pixelAtual == 0xFFB6FF00) /*LIMA*/ {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Entity.TILE_FLOOR);
						Game.entities.add(new Item(xx*16, yy*16, 16,16, Entity.TILE_RECHARGE));						
					} else {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Entity.TILE_FLOOR);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g) {		
			
		for (int xx = 0; xx < WIDTH; xx++) {
			for (int yy = 0; yy < HEIGHT; yy++) {				
				Tile tile = tiles[xx + (yy * WIDTH)];			
				tile.render(g);
			}
		}
	}

}
