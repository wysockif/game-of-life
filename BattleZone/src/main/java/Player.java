import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class Player {
    private List<Bullet> bullets;
    private int maxNumberOfShots;
    private int pointsGained;
    private BufferedImage tankSpriteSheet;
    protected int tileSizeX, tileSizeY;

    protected BufferedImage[] tankSprites;
    protected KeysListener keysListener;
    protected BufferedImage bulletImage, currentSprite;;
    protected int currentSpriteIndex;
    protected int direction;
    protected int xCannon;
    protected int yCannon;
    protected double speedMultiplier;
    protected int x, y, shift;
    protected final int NUMBER_OF_SPRITES = 49;

    protected boolean wantShot;

    public Player(int maxNumberOfShots, String tankPath, String bulletPath, KeysListener keysListener) {
        this.maxNumberOfShots = maxNumberOfShots;

        try {
            bulletImage = ImageIO.read(new File(bulletPath));
            tankSpriteSheet = ImageIO.read(new File(tankPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        bullets = new LinkedList<>();
        pointsGained = 0;
        speedMultiplier = 1;

        tileSizeX = tankSpriteSheet.getWidth();
        tileSizeY = tankSpriteSheet.getHeight() / NUMBER_OF_SPRITES;

        tankSprites = createTankSprites();
        currentSpriteIndex = 24;
        currentSprite = tankSprites[currentSpriteIndex];
        this.keysListener = keysListener;
        y = Game.BOARD_Y + Game.BOARD_HEIGHT/2 - tileSizeY/2 - 30;
    }

    public void takeAShot() {
        if (bullets.size() < maxNumberOfShots) {
            bullets.add(new Bullet(bulletImage, xCannon, yCannon, y, shift, direction, speedMultiplier));
        }
    }

    public int getMaxNumberOfShots() {
        return maxNumberOfShots;
    }

    public int getDirection() {
        return direction;
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

    public double getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void removeUnwantedBullets() {
        Iterator<Bullet> it = bullets.iterator();
        int imageWidth = bulletImage.getWidth();
        while (it.hasNext()) {
            Bullet b = it.next();
            if (b.x < 0 - imageWidth || b.x > Game.BOARD_WIDTH  || b.y < 0 || b.y > Game.BOARD_HEIGHT) {
                it.remove();
                break;
            }
        }
    }

    public void speedUpBullets(int percent) {
        if(speedMultiplier + 0.01 * percent * speedMultiplier < 3 ){
            speedMultiplier = speedMultiplier + 0.01 * percent * speedMultiplier;
            Iterator<Bullet> it = bullets.iterator();
            while (it.hasNext()) {
                Bullet b = it.next();
                b.setSpeedMultiplier(speedMultiplier);
            }
        }
    }

    private BufferedImage getTankSprite(int yGrid) {
//        System.out.println(yGrid * tileSizeY);
        return tankSpriteSheet.getSubimage(0, yGrid * tileSizeY, tileSizeX, tileSizeY);
    }

    private BufferedImage[] createTankSprites(){
        tankSprites = new BufferedImage[NUMBER_OF_SPRITES];
        for(int i = 0; i < NUMBER_OF_SPRITES; i++){
            tankSprites[i] = getTankSprite(i);
        }
        return tankSprites;
    }

    public abstract void drawTank(Graphics g);
    public abstract void updateTank();
    public abstract void updateShots();
    public abstract void checkIfShot();

    public synchronized void drawBullets(Graphics g){
        Iterator<Bullet> it = bullets.iterator();

        try {
            while (it.hasNext()) {
                Bullet b = it.next();
                b.drawBullet(g);
            }

        } catch (ConcurrentModificationException e){
            System.out.println("Stało się!!! bullets");
        }

    }
}
