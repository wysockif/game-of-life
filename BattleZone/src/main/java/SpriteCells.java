import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteCells {
    private BufferedImage spriteSheet;
    private String path;
    private int tileSizeX;
    private int tileSizeY;
    private int howManyX = 8;
    private int howManyY = 8;
    private boolean isLoaded;

    public SpriteCells(String path, int tileSizeX, int tileSizeY) {
        this.path = path;
        this.tileSizeX = tileSizeX;
        this.tileSizeY = tileSizeY;
        loadSprite();
    }

    public void loadSprite() {
        try {
            spriteSheet = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        isLoaded = true;
    }

    public BufferedImage[][] getSprites() {
        BufferedImage[][] sprites = new BufferedImage[howManyY][howManyX];
        for (int y = 0; y < howManyY; y++) {
            for (int x = 0; x < howManyX; x++) {
                sprites[y][x] = getSprite(x, y);
            }
        }
        return sprites;
    }


    public BufferedImage getSprite(int xGrid, int yGrid) {
        return spriteSheet.getSubimage(xGrid * tileSizeX, yGrid * tileSizeY, tileSizeX, tileSizeY);
    }

    public BufferedImage[] getTipsBarImages() {
        BufferedImage[] tipsImages = new BufferedImage[8];
        for (int i = 0; i < 8; i++) {
            tipsImages[i] = scaleCell(getSprite(i, i), 0.5);
        }
        return tipsImages;
    }


    public BufferedImage scaleCell(BufferedImage imageToScaling, double scale) {
        BufferedImage before = imageToScaling;
        int w = before.getWidth();
        int h = before.getHeight();
        BufferedImage after = new BufferedImage((int) Math.round(scale * w), (int) Math.round(scale * h),
                BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return scaleOp.filter(before, after);
    }
}
