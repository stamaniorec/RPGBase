import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Player {

	private int x; // the coordinates are for the top-left corner
	private int y; // NOT the center!

	private int moveSpeed;

	// DIRECTIONS FOR MOVEMENT
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	// PLAYER IMAGE (red square)
	private BufferedImage image;
	private int[] pixels;
	
	private int width;
	private int height;

	private TileMap map;
	private Camera camera;

	// CORNERS FOR COLLISION
	private boolean topLeft;
	private boolean topRight;
	private boolean bottomLeft;
	private boolean bottomRight;

	// destination / delta x and y - for collision
	private int dx;
	private int dy;

	public Player(TileMap map) {
		this.map = map;

		moveSpeed = 5;

		width = 16;
		height = 16;

		left = right = up = down = false;

		// create the player image
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		// render the image red
		for(int i = 0; i < pixels.length; ++i) {
			pixels[i] = 0xFF0000;
		}
	}

	public void update(Camera cam) {

		dx = 0;
		dy = 0;

		if(left) {
			dx -= 5;
		}
		else if(right) {
			dx += 5;
		}
		else if(up) {
			dy -= 5;
			
		} else if(down) {
			dy += 5;
		}

		// check for player going out of the screen
		if(y + dy < 0) return;
		if(y + dy + height > map.getHeight() * map.getTileSize()) return;
		if(x + dx < 0) return;
		if(x + dx + width - cam.getX() > Game.WIDTH) return;

		int currCol = map.getColTile(x);
		int currRow = map.getRowTile(y);

		double toX = x + dx;
		double toY = y + dy;

		int tempX = x;
		int tempY = y;

		// check for collisions above and below 
		
		calculateCorners(x, toY);

		if(dy < 0) {
			if(topLeft || topRight) {
				tempY = currRow * map.getTileSize();
			} else {
				tempY += dy;
			}
		}
		if(dy > 0) {
			if(bottomLeft || bottomRight) {
				tempY  = (currRow + 1) * map.getTileSize() - height;
			} else {
				tempY += dy;
			}
		}

		// check for collisions left and right

		calculateCorners(toX, y);

		if(dx < 0) {
			if(topLeft || bottomLeft) {
				tempX = currCol * map.getTileSize();
			} else {
				tempX += dx;
			}
		}
		else if(dx > 0) {
			if(topRight || bottomRight) {
				tempX = currCol * map.getTileSize() + width;
				// OR: (currCol+1) * map.getTileSize() - width;
			} else {
				tempX += dx;
			}
		}

		x = tempX;
		y = tempY;
	}

	private void calculateCorners(double x, double y) {
		// the tiles at the CORNERS!
		int leftTile = map.getColTile((int) x);
		int rightTile = map.getColTile((int) (x + width - 1));
		int topTile = map.getRowTile((int) y);
		int bottomTile = map.getRowTile((int) (y + height - 1));

		topLeft = map.isBlocked(topTile, leftTile);
		topRight = map.isBlocked(topTile, rightTile);
		bottomLeft = map.isBlocked(bottomTile, leftTile);
		bottomRight = map.isBlocked(bottomTile, rightTile);
	}


	public void draw(Graphics g) {
		g.drawImage(image, x, y, width, height, null);
		// a plain rectangle can also work, but it's a little bit slower
	}

	public void setLeft(boolean b) { left = b; } 	
	public void setRight(boolean b) { right = b; }
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; }

	public int getMoveSpeed() { return moveSpeed; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
}
