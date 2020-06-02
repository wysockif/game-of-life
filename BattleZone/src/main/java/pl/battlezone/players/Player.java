package pl.battlezone.players;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import pl.battlezone.Game;
import pl.battlezone.technical.KeysListener;
import pl.battlezone.technical.Sounds;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

public abstract class Player {
    private int maxNumberOfShots;
    private int pointsGained = 0;

    private BufferedImage tankSpriteSheet;
    private List<Bullet> bullets;

    protected int tileSizeX, tileSizeY;
    protected int currentSpriteIndex = 24;
    protected int x, y, shift;
    protected int direction;
    protected int xCannon;
    protected int yCannon;
    protected boolean wantShot;
    protected double speedMultiplier = 1;
    protected final static int NUMBER_OF_SPRITES = 49;

    protected BufferedImage[] tankSprites;
    protected KeysListener keysListener;
    protected BufferedImage bulletImage, currentSprite;

    public Player(int maxNumberOfShots, String tankPath, String bulletPath, KeysListener keysListener) {
        this.maxNumberOfShots = maxNumberOfShots;
        this.keysListener = keysListener;
        readSprites(tankPath, bulletPath);
        bullets = new LinkedList<>();
        tileSizeX = tankSpriteSheet.getWidth();
        tileSizeY = tankSpriteSheet.getHeight() / NUMBER_OF_SPRITES;
        tankSprites = createTankSprites();
        currentSprite = tankSprites[currentSpriteIndex];
        y = Game.boardY + Game.boardHeight / 2 - tileSizeY / 2 - 30;
    }

    private void readSprites(String tankPath, String bulletPath) {
        try {
            tankSpriteSheet = ImageIO.read(Player.class.getResource(tankPath));
        } catch (IOException | IllegalArgumentException e) {
            Sounds.playErrorSound();
            JOptionPane.showMessageDialog(null, "Błąd krytyczny!\n" +
                    "Nie mogę znaleźć pliku z obrazem czołgu!", "Błąd krytyczny!", ERROR_MESSAGE);
            System.exit(2);
        }
        try {
            bulletImage = ImageIO.read(Player.class.getResource(bulletPath));
        } catch (IOException | IllegalArgumentException e) {
            Sounds.playErrorSound();
            JOptionPane.showMessageDialog(null, "Błąd krytyczny!\n" +
                    "Nie mogę znaleźć pliku z obrazem pocisku!", "Błąd krytyczny!", ERROR_MESSAGE);
            System.exit(2);
        }
    }

    public void takeAShot() {
        if (bullets.size() < maxNumberOfShots) {
            if (Game.isSoundTurnedOn)
                Sounds.playTankFiringSound();
            bullets.add(new Bullet(bulletImage, xCannon, yCannon, y, shift, direction, speedMultiplier));
        }
    }

    public void removeUnwantedBullets() {
        Iterator<Bullet> it = bullets.iterator();
        int imageWidth = bulletImage.getWidth();
        while (it.hasNext()) {
            Bullet b = it.next();
            if (b.x < -imageWidth || b.x > Game.boardWidth || b.y < 0 || b.y > Game.boardHeight) {
                it.remove();
                break;
            }
        }
    }

    public void speedUpBullets(int percent) {
        if (speedMultiplier + 0.01 * percent * speedMultiplier < 3) {
            speedMultiplier = speedMultiplier + 0.01 * percent * speedMultiplier;
        } else
            Game.setIsMaxSpeed(true);
    }

    private BufferedImage getTankSprite(int yGrid) {
        return tankSpriteSheet.getSubimage(0, yGrid * tileSizeY, tileSizeX, tileSizeY);
    }

    public synchronized void drawBullets(Graphics g) {
        Iterator<Bullet> it = bullets.iterator();
        try {
            while (it.hasNext()) {
                Bullet b = it.next();
                b.drawBullet(g);
            }
        } catch (ConcurrentModificationException e) {
            drawBullets(g);
        }
    }

    private BufferedImage[] createTankSprites() {
        tankSprites = new BufferedImage[NUMBER_OF_SPRITES];
        for (int i = 0; i < NUMBER_OF_SPRITES; i++) {
            tankSprites[i] = getTankSprite(i);
        }
        return tankSprites;
    }

    public synchronized void updateMyBullets() {
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            b.updateBullet();
        }
    }

    public abstract void drawTank(Graphics g);

    public abstract void updateTank();

    public abstract void updateShots();

    public abstract void checkIfShot();

    public int getMaxNumberOfShots() {
        return maxNumberOfShots;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void addPointsGained(int newPoints) {
        pointsGained += newPoints;
    }

    public int getPointsGained() {
        return pointsGained;
    }

}
