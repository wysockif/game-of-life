package pl.battlezone.technical;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_UP;
import static java.awt.event.KeyEvent.VK_W;

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

    public boolean isWPressed() {
        return keys[VK_W];
    }

    public boolean isSPressed() {
        return keys[VK_S];
    }

    public boolean isAPressed() {
        return keys[VK_A];
    }

    public boolean isDPressed() {
        return keys[VK_D];
    }

    public boolean isSpacePressed() {
        return keys[VK_SPACE];
    }

    public boolean isUpKeyPressed() {
        return keys[VK_UP];
    }

    public boolean isDownKeyPressed() {
        return keys[VK_DOWN];
    }

    public boolean isRightKeyPressed() {
        return keys[VK_RIGHT];
    }

    public boolean isLeftKeyPressed() {
        return keys[VK_LEFT];
    }

    public boolean isEnterPressed() {
        return keys[VK_ENTER];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
