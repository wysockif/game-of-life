import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Bullet extends Rectangle {
    private final int yStart, shift;
    private final int direction;
    private double xDouble, yDouble;
    private double yTank;
    private double speedMultiplier;

    private BufferedImage bulletImage;

    public Bullet(BufferedImage bulletImage, int xCannon, int yCannon, int yTank, int shift,  int direction, double speedMultiplier) {
        this.yTank = yTank;
        this.x = xCannon;
        this.y = yCannon;
        this.bulletImage = bulletImage;
        this.width = bulletImage.getWidth();
        this.height = bulletImage.getHeight();
        this.direction = direction;
        this.speedMultiplier = speedMultiplier;
        this.shift = shift;

        yStart = yCannon;
        yDouble = yCannon;
        xDouble = xCannon;
        y = (int) Math.round(yDouble) - shift;
    }

    public void drawBullet(Graphics g) {
        if (direction == 1 && x < Game.BOARD_WIDTH - width && y > 0 && y < Game.BOARD_HEIGHT - height)
            g.drawImage(bulletImage, Game.BOARD_X + x, Game.BOARD_Y + y, width, height, null);
        if (direction == -1 && x > 0 && y > 0 && y < Game.BOARD_HEIGHT - height)
            g.drawImage(bulletImage, Game.BOARD_X + x, Game.BOARD_Y + y, width, height, null);
    }


    public void updateBullet(){
        xDouble += speedMultiplier * (direction);
        x = (int) Math.round(xDouble);
        yDouble -= 0.005 * speedMultiplier * (yTank - yStart - Game.BOARD_Y + 95);
        y = (int) Math.round(yDouble) - shift;

    }


    public void setSpeedMultiplier(double speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }
}
