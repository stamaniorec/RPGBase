public class Camera {

	private float x;
	private float y;

	private float minX;
	private float minY;
	private float maxX;
	private float maxY;

	public Camera(TileMap map) {

		maxX = map.getWidth() * map.getTileSize() - Game.WIDTH;
		maxY = map.getHeight() * map.getTileSize() - Game.HEIGHT;
		minX = 0;
		minY = 0;

	}

	public void update(Player p) {

		x = p.getX() - (Game.WIDTH - p.getWidth()) / 2;
		y = p.getY() - (Game.HEIGHT - p.getHeight()) / 2;

		if(x > maxX) {
			x = maxX;
		}
		else if(x < minX) {
			x = minX;
		}

		if(y > maxY) 
			y = maxY;
		else if(y < minY) 
			y = minY;

		//x--;	// scrolling to the left
	}

	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public float getX() { return x; }
	public float getY() { return y; }

}
