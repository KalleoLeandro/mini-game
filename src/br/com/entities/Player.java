/**
 * 
 */
package br.com.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import br.com.main.Game;

/**
 * 
 */
public class Player extends Entity{
	
	public boolean right,up,left,down;
	public int right_dir = 0, left_dir = 1, up_dir=2, down_dir=3;
	public int dir = right_dir;
	public double speed = 1.5;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);	
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		
		upPlayer[0] = Game.playerSheet.getSprite(0, 0, 25, 32);		
		upPlayer[1] = Game.playerSheet.getSprite(25, 0, 25, 32);
		upPlayer[2] = Game.playerSheet.getSprite(0, 0, 25, 32);
		upPlayer[3] = Game.playerSheet.getSprite(25, 0, 25, 32);
		
		rightPlayer[0] = Game.playerSheet.getSprite(0, 64, 25, 32);
		rightPlayer[1] = Game.playerSheet.getSprite(25, 64, 25, 32);
		rightPlayer[2] = Game.playerSheet.getSprite(0, 64, 25, 32);
		rightPlayer[3] = Game.playerSheet.getSprite(25, 64, 25, 32);
		
		downPlayer[0] = Game.playerSheet.getSprite(0, 128, 25, 32);
		downPlayer[1] = Game.playerSheet.getSprite(25, 128, 25, 32);
		downPlayer[2] = Game.playerSheet.getSprite(0, 128, 25, 32);
		downPlayer[3] = Game.playerSheet.getSprite(25, 128, 25, 32);
		
		leftPlayer[0] = Game.playerSheet.getSprite(0, 192, 25, 32);
		leftPlayer[1] = Game.playerSheet.getSprite(25, 192, 25, 32);
		leftPlayer[2] = Game.playerSheet.getSprite(0, 192, 25, 32);
		leftPlayer[3] = Game.playerSheet.getSprite(25, 192, 25, 32);			
	}	
	
	@Override
	public void tick() {
		moved = false;
		if(right) {			
			dir = right_dir;
			x += speed;			
			moved = true;
		} else if(left) {
			dir = left_dir;
			x -=speed;			
			moved = true;
		} if(up) {
			dir = up_dir;
			y -= speed;
			moved = true;
		} else if(down) {
			dir = down_dir;
			y += speed;
			moved = true;
		}
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}		
		
	}
	
	@Override
	public void render(Graphics g) {
		if(dir == up_dir) {
			g.drawImage(upPlayer[index], this.getX(),this.getY(), null);
		} else if(dir == down_dir) {
			g.drawImage(downPlayer[index], this.getX(),this.getY(), null);			
		} else if (dir == left_dir){
			g.drawImage(leftPlayer[index], this.getX(),this.getY(), null);
		} else if(dir == right_dir) {
			g.drawImage(rightPlayer[index], this.getX(),this.getY(), null);
		}		
	}

}
