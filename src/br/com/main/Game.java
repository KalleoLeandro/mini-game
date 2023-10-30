/**
 * 
 */
package br.com.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import br.com.entities.Enemy;
import br.com.entities.Entity;
import br.com.entities.Player;
import br.com.entities.Spell;
import br.com.graficos.Spritesheet;
import br.com.graficos.Ui;
import br.com.sounds.Sound;
import br.com.world.World;

/**
 * 
 */
public class Game extends Canvas implements Runnable, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Atributos

	public static JFrame frame;
	public boolean isRunning;

	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	private Thread thread;

	private static BufferedImage image;

	public static List<Entity> entities;

	public static Spritesheet playerSheet;

	public static Spritesheet wallSheet;

	public static Spritesheet floorSheet;

	public static Spritesheet itemsSheet;

	public static Spritesheet enemySheet;

	public static World world;

	public static Player player;

	public static List<Spell> spells;

	private int curLevel = 1, maxLevel = 2;

	public static String gameState = "MENU";
	
	private static boolean restartGame = false;
	
	private Sound sound;

	// 1a forma randomica
	public static Random rand;
	public boolean endGame = true;
	public int framesGameOver = 0;

	// 2a forma randomica
	public static List<Enemy> enemies;
	
	public Menu menu;

	public Ui ui;

	private int frames;

	// Método inicialização thread game
	public Game() {
		rand = new Random();
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		// Inicializando objetos

		ui = new Ui();		
		String inicio = "/mapa1.png";
		startWorld(inicio);
		//sound = new Sound("res/battle.wav");
		menu = new Menu();
	}

	// Método principal
	public static void main(String[] args) {

		wallSheet = new Spritesheet("/scenario.png");
		floorSheet = new Spritesheet("/grass.png");
		itemsSheet = new Spritesheet("/items.png");
		enemySheet = new Spritesheet("/enemy.png");
		playerSheet = new Spritesheet("/sprite.png");
		Game game = new Game();
		game.start();
	}

	// Start Game
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	// Stop game
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// contrução da tela principal do jogo
	public void initFrame() {
		frame = new JFrame("Meu jogo");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	// Atualização da tela
	public void tick() {
		if (gameState.equalsIgnoreCase("NORMAL")) {
			restartGame  = false;
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}

			for (int i = 0; i < spells.size(); i++) {
				Spell s = spells.get(i);
				s.tick();
			}

			if (enemies.size() == 0) {
				curLevel++;
				if (curLevel > maxLevel) {
					curLevel = 1;
				}
				String newWorld = "/mapa" + curLevel + ".png";
				Game.startWorld(newWorld);
			}
		} else if (gameState.equalsIgnoreCase("GAME_OVER")) {
			// Fim de jogo
			this.framesGameOver++;
			if (this.framesGameOver >= 30) {
				this.framesGameOver = 0;
				if (this.endGame) {
					this.endGame = false;
				} else {
					this.endGame = true;
				}
			}
			
			if(restartGame) {
				restartGame = false;
				curLevel = 1;
				gameState = "NORMAL";
				String newWorld = "/mapa1.png"; 
				startWorld(newWorld);				
			}
		} else if(gameState.equalsIgnoreCase("MENU")) {
			menu.tick();
		}
	}

	public static void startWorld(String level) {
		entities = new ArrayList<Entity>();
		player = new Player(150, 120, 16, 16, playerSheet.getSprite(0, 0, 16, 16));
		enemies = new ArrayList<Enemy>();
		entities.add(player);
		spells = new ArrayList<Spell>();
		world = new World(level);
		try {
			image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	// Renderização
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		world.render(g);

		// Renderizar todas as entidades
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}

		for (Spell s : spells) {
			s.render(g);
		}

		ui.render(g);

		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.setColor(Color.WHITE);
		g.drawString("Magia: " + player.ammo, WIDTH*SCALE - 100, HEIGHT*SCALE/20);
		g.setColor(Color.WHITE);
		g.drawString("FPS: " + frames, WIDTH*SCALE - 100, HEIGHT*SCALE - 10);
		if (gameState.equalsIgnoreCase("GAME_OVER")) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			g.setFont(new Font("Arial", Font.BOLD, 36));
			g.setColor(Color.WHITE);
			g.drawString("Game Over", (WIDTH * SCALE) / 2 - 100, (HEIGHT * SCALE) / 2 - 100);
			g.setFont(new Font("Arial", Font.BOLD, 28));
			g.setColor(Color.WHITE);
			if (endGame) {
				g.drawString("Pressione qualquer tecla para novo jogo", (WIDTH * SCALE) / 2 - 220,
						(HEIGHT * SCALE) / 2 + 40);
			}
		} else if(gameState.equalsIgnoreCase("MENU")) {
			menu.render(g);
		}
		bs.show();
	}

	// Thread de controle do fps
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				delta--;
				frames++;
			}
			if (System.currentTimeMillis() - timer >= 1000) {
				this.frames = frames;
				frames = 0;
				timer = System.currentTimeMillis();
			}
		}
		stop();
	}

	// Controles

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			Player.right = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			Player.left = true;
		} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			Player.up = true;	
			if(gameState.equalsIgnoreCase("MENU")) {
				menu.up = true;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			Player.down = true;
			if(gameState.equalsIgnoreCase("MENU")) {
				menu.down = true;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.shoot = true;
		} 
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			restartGame = true;			
			if(gameState == "MENU") {
				menu.enter = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			menu.pause = true;
			gameState = "MENU";
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			Player.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			Player.left = false;
		} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			Player.up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			Player.down = false;
		}
	}

}