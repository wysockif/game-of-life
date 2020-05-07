import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeysListener implements KeyListener {
    public boolean[] keys = new boolean[120];


    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode < keys.length)
            keys[keyCode] = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();
        if (keyCode < keys.length)
            keys[keyCode] = true;
    }

    boolean isWPressed() {
        return keys[KeyEvent.VK_W];
    }

    boolean isSPressed() {
        return keys[KeyEvent.VK_S];
    }

    boolean isAPressed() {
        return keys[KeyEvent.VK_A];
    }

    boolean isDPressed() {
        return keys[KeyEvent.VK_D];
    }

    boolean isSpacePressed() {
        return keys[KeyEvent.VK_SPACE];
    }


    boolean isUpKeyPressed() {
        return keys[KeyEvent.VK_UP];
    }

    boolean isDownKeyPressed() {
        return keys[KeyEvent.VK_DOWN];
    }

    boolean isRightKeyPressed() {
        return keys[KeyEvent.VK_RIGHT];
    }

    boolean isLeftKeyPressed() {
        return keys[KeyEvent.VK_LEFT];
    }

    boolean isEnterPressed() {
        return keys[KeyEvent.VK_ENTER];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
