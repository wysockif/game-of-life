import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.*;
import java.util.List;

public class Cells {
    private static BufferedImage[][] cellsImages;
    private BufferedImage[] tipsBarImages;
    private Game game;
    private SpriteCells spriteCells;
    private List<Cell> cells;
    private int originalWidth, originalHeight;
    private int currentWidth, currentHeight;

    public Cells(Game game, SpriteCells spriteCells) {
        this.game = game;
        this.spriteCells = spriteCells;
        cellsImages = spriteCells.getSprites();
        currentWidth = originalWidth = cellsImages[0][0].getWidth();
        currentHeight = originalHeight = cellsImages[0][0].getHeight();
        tipsBarImages = spriteCells.getTipsBarImages();
    }


    public void createNewCells(int bSize) {
        boolean[][] arr = new boolean[bSize][bSize];
        cells = new LinkedList<>();

        Random r = new Random();
        for (int i = 0; i < bSize * bSize - bSize; i++) {
            int x = r.nextInt(5);
            int y = r.nextInt(5);

            if (!arr[x][y]) {
                int value = r.nextInt(8) + 1;
                cells.add(new Cell(x * originalWidth, y * originalHeight, originalWidth, originalHeight,
                        value, getCellImage(value - 1, value - 1), this));
                arr[x][y] = true;
            }
        }
        selectArmageddonCell();
        selectInheritanceCells();
    }


    public synchronized void createCells() {
        boolean isNew = false;
        if (cells == null) {
            cells = new LinkedList<>();
            isNew = true;
        }
        Random r = new Random();
        boolean b = true;



        for (int i = 0; i < 20 && cells.size() < 25; i++) {
            int x = r.nextInt(Game.BOARD_WIDTH- originalWidth);
            int y = r.nextInt(Game.BOARD_HEIGHT - originalHeight);

            int value = r.nextInt(8) + 1;
            Cell temp = new Cell(x, y, originalWidth, originalHeight, value, getCellImage(value - 1, value - 1), this);

            b = true;
            for (Cell c : cells) {
                if (c.isOccupiedSpace(temp))
                    b = false;
            }



            if (b) {
                cells.add(temp);
                System.out.println(temp);
            }

        }

        selectInheritanceCells();
        if (isNew)
            selectArmageddonCell();
        System.out.println();
    }



    public BufferedImage getCellImage(int x, int y) {
        return cellsImages[y][x];
    }


    public void increaseValues() {
        for (Cell c : cells) {
            c.increaseValue();
        }
    }

    public void reduceSize(int percent) {
        double scale = 1 - percent * 0.01;
        if (couldResize(scale)) {
            scaleCellsSprites(scale);
            for (Cell c : cells) {
                c.reduceSize(percent);
//                System.out.println(c);
            }

        }
    }

    public int getNumberOfCells() {
        return cells.size();
    }

    private void scaleCellsSprites(double scale) {
        if (couldResize(scale)) {
            for (int i = 0; i < cellsImages.length; i++) {
                for (int j = 0; j < cellsImages[i].length; j++) {
                    BufferedImage before = cellsImages[i][j];
                    int w = before.getWidth();
                    int h = before.getHeight();
                    BufferedImage after = new BufferedImage((int) Math.round(scale * w), (int) Math.round(scale * h),
                            BufferedImage.TYPE_INT_ARGB);
                    AffineTransform at = new AffineTransform();
                    at.scale(scale, scale);
                    AffineTransformOp scaleOp =
                            new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    after = scaleOp.filter(before, after);
                    cellsImages[i][j] = after;

                    currentWidth = after.getWidth();
                    currentHeight = after.getHeight();
                }
            }

        }
    }

    private boolean couldResize(double scale) {
        return originalWidth < 2 * scale * cellsImages[0][0].getWidth();
    }

    public synchronized void paintCells(Graphics g) {
        try {
            for (Cell c : cells) {
                g.drawImage(c.getCurrentImage(), Game.BOARD_X + c.getXPosition(),
                        Game.BOARD_Y + c.getYPosition(), null);
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Stało się!!! cells");
        }

    }

    private void selectArmageddonCell() {
        Random rand = new Random();
        int index = rand.nextInt(cells.size());
        cells.get(index).setArmageddon(true);
    }

    private void selectInheritanceCells() {
        Random rand = new Random();
        int n = rand.nextInt(cells.size());
        int n10 = n / 2;
        int n20 = n / 4;
        int n30 = n / 6;
        int n50 = n / 10;
        int n100 = n / 15;


        for (int i = 0; i < n10; i++) {
            int index = rand.nextInt(cells.size());
            while (cells.get(index).getInheritance() != 0) {
                index = rand.nextInt(cells.size());
            }
            cells.get(index).setInheritance(10);
        }
        for (int i = 0; i < n20; i++) {
            int index = rand.nextInt(cells.size());
            while (cells.get(index).getInheritance() != 0) {
                index = rand.nextInt(cells.size());
            }
            cells.get(index).setInheritance(20);
        }
        for (int i = 0; i < n30; i++) {
            int index = rand.nextInt(cells.size());
            while (cells.get(index).getInheritance() != 0) {
                index = rand.nextInt(cells.size());
            }
            cells.get(index).setInheritance(30);

        }
        for (int i = 0; i < n50; i++) {
            int index = rand.nextInt(cells.size());
            while (cells.get(index).getInheritance() != 0) {
                index = rand.nextInt(cells.size());
            }
            cells.get(index).setInheritance(40);

        }
        for (int i = 0; i < n100; i++) {
            int index = rand.nextInt(cells.size());
            while (cells.get(index).getInheritance() != 0) {
                index = rand.nextInt(cells.size());
            }
            cells.get(index).setInheritance(100);
        }


    }

    public synchronized void checkIfHit(Player player) {
        List<Bullet> bullets = player.getBullets();
        Iterator<Bullet> itBullet = bullets.iterator();
        try {
            while (itBullet.hasNext()) {
                Bullet bullet = itBullet.next();
                Iterator<Cell> itCells = cells.iterator();
                while (itCells.hasNext()) {
                    Cell cell = itCells.next();
                    if (cell.contains(bullet) || cell.intersects(bullet)) {
//                    System.out.printf("Bullet %d, %d, %d, %d \n", bullet.x, bullet.y, bullet.width, bullet.height);
//                    System.out.printf("Cell   %d, %d, %d, %d \n", cell.x, cell.y, cell.width, cell.height);

                        if (cells.contains(cell)) {
                            if (cell.getCurrentValue() == 1) {
                                game.refreshScores(cell, player);
                                itCells.remove();
                            } else
                                cell.decreaseValue();
                            itBullet.remove();
                        }


                        break;
                    }

                }
            }
        } catch (ConcurrentModificationException e){}
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public void drawTipBar(Graphics g) {
        for (int i = 0; i < 8; i++)
            g.drawImage(tipsBarImages[i], 356 + i * 60, Game.BOARD_Y + Game.BOARD_HEIGHT + 20, null);
    }
}
