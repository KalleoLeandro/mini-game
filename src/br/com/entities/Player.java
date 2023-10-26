/**
 * 
 */
package br.com.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.graficos.Camera;
import br.com.main.Game;
import br.com.world.World;

/**
 * 
 */
public class Player extends Entity {

	public static boolean right, up, left, down;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	public double speed = 1.5;

	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;

	private BufferedImage[] playerDamage;

	public static boolean isDamaged;
	
	public static boolean hasGun;
	
	private int damageFrames = 0;

	public int ammo = 0;

	public static double life = 100, maxLife = 100;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		playerDamage = new BufferedImage[4];

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

		playerDamage[0] = Game.playerSheet.getSprite(168, 6, 25, 32);
		playerDamage[1] = Game.playerSheet.getSprite(172, 71, 25, 32);
		playerDamage[2] = Game.playerSheet.getSprite(172, 134, 25, 32);
		playerDamage[3] = Game.playerSheet.getSprite(169, 200, 25, 32);
	}

	@Override
	public void tick() {
		moved = false;
		if (right && World.isFree((int) (x + speed), this.getY())) {
			dir = right_dir;
			x += speed;
			moved = true;
		} else if (left && World.isFree((int) (x - speed), this.getY())) {
			dir = left_dir;
			x -= speed;
			moved = true;
		}
		if (up && World.isFree(this.getX(), (int) (y - speed))) {
			dir = up_dir;
			y -= speed;
			moved = true;
		} else if (down && World.isFree(this.getX(), (int) (y + speed))) {
			dir = down_dir;
			y += speed;
			moved = true;
		}
		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}

		this.checkCollisionLifePack();

		this.checkCollisionMagic();
		
		this.checkCollisionWeapon();
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 30) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
		
		if(life <=80) {
			life = 100;
			Game.startWorld();			
			return;
		}

		Camera.x = Camera.clamp(this.getX() - Game.WIDTH / 2, 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - Game.HEIGHT / 2, 0, World.HEIGHT * 16 - Game.HEIGHT);

	}

	public void checkCollisionLifePack() {
		for (Entity e : Game.entities) {
			if (e instanceof Lifepack) {
				if (Entity.isColliding(this, e)) {
					life += 8;
					if (life >= 100) {
						life = 100;
						Game.entities.remove(e);
						return;
					}
				}
			}
		}
	}

	public void checkCollisionMagic() {
		for (Entity e : Game.entities) {
			if (e instanceof Magic) {
				if (Entity.isColliding(this, e)) {					
					ammo += 10;
					Game.entities.remove(e);
					return;
				}
			}
		}
	}
	
	public void checkCollisionWeapon() {
		for (Entity e : Game.entities) {
			if (e instanceof Weapon) {
				if (Entity.isColliding(this, e)) {					
					ammo += 10;
					Game.entities.remove(e);
					return;
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if (isDamaged) {
			if (dir == up_dir) {
				g.drawImage(playerDamage[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
				
			} else if (dir == right_dir) {
				g.drawImage(playerDamage[1], this.getX() - Camera.x, this.getY() - Camera.y, null);
				
			} else if (dir == down_dir) {
				g.drawImage(playerDamage[2], this.getX() - Camera.x, this.getY() - Camera.y, null);
				
			} else if (dir == left_dir) {
				g.drawImage(playerDamage[3], this.getX() - Camera.x, this.getY() - Camera.y, null);
				
			}
		} else {
			if (dir == up_dir) {
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (dir == down_dir) {
				g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}
	}

}
