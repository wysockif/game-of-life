package pl.battlezone.players;

import java.awt.Graphics;
import pl.battlezone.Game;
import pl.battlezone.technical.KeysListener;

public class LeftPlayer extends Player {
    public LeftPlayer(int maxNumberOfShots, String tankPath, String bulletPath, KeysListener keysListener) {
        super(maxNumberOfShots, tankPath, bulletPath, keysListener);
        direction = 1;
        x = Game.boardX - tileSizeX + 50;
        xCannon = -bulletImage.getWidth();
        yCannon = y + 95 - Game.boardY;
    }

    @Override
    public void drawTank(Graphics g) {
        g.drawImage(currentSprite, x, y, null);
    }

    @Override
    public void updateTank() {
        if (keysListener.isWPressed()) {
            if (y > Game.boardY - tileSizeY / 2) {
                y -= 2;
                yCannon -= 2;
            }

        }
        if (keysListener.isSPressed()) {
            if (y < Game.boardY + Game.boardHeight - tileSizeY / 2 - 20) {
                y += 2;
                yCannon += 2;
            }
        }
        if (keysListener.isAPressed())
            handleAKey();
        if (keysListener.isDPressed())
            handleDKey();
    }

    private void handleDKey() {
        if (currentSpriteIndex < NUMBER_OF_SPRITES - 1) {
            currentSpriteIndex++;
            currentSprite = tankSprites[currentSpriteIndex];
            if (currentSpriteIndex > 30)
                shift = 10 + 3 * (currentSpriteIndex - 30);
            else
                shift = 0;
            if (currentSpriteIndex > 0 && currentSpriteIndex < 5)
                yCannon += 5;
            else if (currentSpriteIndex > 0 && currentSpriteIndex < 10)
                yCannon += 3;
            else
                yCannon += 5;
        }
    }

    private void handleAKey() {
        if (currentSpriteIndex > 0) {
            currentSpriteIndex--;
            currentSprite = tankSprites[currentSpriteIndex];
            if (currentSpriteIndex > 30)
                shift = 10 + 3 * (currentSpriteIndex - 30);
            else
                shift = 0;
            if (currentSpriteIndex > 0 && currentSpriteIndex < 5)
                yCannon -= 5;
            else if (currentSpriteIndex > 0 && currentSpriteIndex < 10)
                yCannon -= 3;
            else
                yCannon -= 5;
        }
    }


    @Override
    public void updateShots() {
        if (wantShot) {
            takeAShot();
            wantShot = false;
        }
    }


    @Override
    public void checkIfShot() {
        if (keysListener.isSpacePressed()) {
            wantShot = true;
        }
    }
}
