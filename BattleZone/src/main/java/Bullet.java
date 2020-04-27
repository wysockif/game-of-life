import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends Rectangle {
    private BufferedImage bulletImage;
    private double speedMultiplier;
    private int direction;
    private final int yStart;
    private double xDouble, yDouble;
    private final int yTank, shift;

    public Bullet(BufferedImage bulletImage, int xCannon, int yCannon, int yTank, int shift, int direction, double speedMultiplier) {
        this.yTank = yTank;
        this.shift = shift;
        this.x = xCannon;
        this.y = yCannon + shift;
        this.bulletImage = bulletImage;
        this.width = bulletImage.getWidth();
        this.height = bulletImage.getHeight();
        this.direction = direction;
        this.speedMultiplier = speedMultiplier;

        yStart = yCannon;
        yDouble = yCannon;
        xDouble = xCannon;
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
        yDouble += -0.005 * (yTank - yStart) * speedMultiplier;
        y = (int) Math.round(yDouble) - shift;

    }


    public void setSpeedMultiplier(double speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }
}
