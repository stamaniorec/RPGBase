import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class TileMap {

	private int[][] map;
	private int mapHeight;
	private int mapWidth;
	private int tileSize;

	private BufferedImage tileSet;
	private int tileSetColumns;

	public TileMap(String fileName, String tileSetFileName) {
		tileSize = 32;

		try {
			tileSet = ImageIO.read(new File(tileSetFileName));
		} catch(Exception e) {}		
		tileSetColumns = 2;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			mapWidth = Integer.parseInt(reader.readLine());
			mapHeight = Integer.parseInt(reader.readLine());

			map = new int[mapHeight][mapWidth];

			for(int row = 0; row < mapHeight; ++row) {

				String line = reader.readLine();
				String[] fields = line.split(" ");

				for(int col = 0; col < mapWidth; ++col) {
					map[row][col] = Integer.parseInt(fields[col]);
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public int getWidth() { return mapWidth; }
	public int getHeight() { return mapHeight; }
	public int getTileSize() { return tileSize; }

	public void update() {

	}

	public void draw(Graphics g, Camera c) {
		int tileScreenLeftCornerIsOnRow = (Math.round(c.getY())) / tileSize; 
		int tileScreenLeftCornerIsOnCol = (Math.round(c.getX())) / tileSize;

		// the ones fix a glitch (flickering at the end of the screen)
		int tileScreenRightCornerIsOnRow = (Math.round(c.getY()) + Game.HEIGHT) / tileSize + 1; 
		int tileScreenRightCornerIsOnCol = (Math.round(c.getX()) + Game.WIDTH) / tileSize + 1;

		// check for going out of bounds because of the 1's
		if(tileScreenRightCornerIsOnCol >= mapWidth)
			tileScreenRightCornerIsOnCol = mapWidth;
		if(tileScreenRightCornerIsOnRow >= mapHeight)
			tileScreenRightCornerIsOnRow = mapHeight;

		// do not draw the whole map
		// only the part of the map that can be seen on the screen
		for(int row = tileScreenLeftCornerIsOnRow; row < tileScreenRightCornerIsOnRow; ++row) {

			for(int col = tileScreenLeftCornerIsOnCol; col < tileScreenRightCornerIsOnCol; ++col) {

				g.drawImage(
					tileSet.getSubimage(
						(map[row][col] % tileSetColumns) * tileSize, 
						(map[row][col] / tileSetColumns) * tileSize, 
						tileSize, 
						tileSize),
					(col * tileSize), 
					(row * tileSize), 
					null); 
			}
		}
	}

	public void print() {
		for(int i = 0; i < mapHeight; ++i) {
			for(int j = 0; j < mapWidth; ++j) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public boolean isBlocked(int r, int c) {
		return map[r][c] == 1;
	}

	// get number of row/column for passed position (in pixels)

	public int getColTile(int x) {
		return x / tileSize;
	}	
	public int getRowTile(int y) {
		return y / tileSize;
	}
}
