import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyInputManager implements KeyListener {

	private Player player;

	public KeyInputManager(Player p) {
		player = p;
	}

	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT: 
				player.setLeft(true);
				break;
			case KeyEvent.VK_RIGHT: 
				player.setRight(true);
				break;
			case KeyEvent.VK_UP: 
				player.setUp(true);
				break;
			case KeyEvent.VK_DOWN: 
				player.setDown(true);
				break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT: 
				player.setLeft(false);
				break;
			case KeyEvent.VK_RIGHT: 
				player.setRight(false);
				break;
			case KeyEvent.VK_UP: 
				player.setUp(false);
				break;
			case KeyEvent.VK_DOWN: 
				player.setDown(false);
				break;
		}
	}

	public void keyTyped(KeyEvent e) {}

}
