import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.awt.image.AffineTransformOp.TYPE_BILINEAR;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class Cells {
    private int originalWidth;
    private int currentWidth, currentHeight;

    private Game game;
    private BufferedImage[] tipsBarImages;
    private List<Cell> cells;
    private static BufferedImage[][] cellsImages;


    public Cells(Game game, SpriteCells spriteCells) {
        this.game = game;
        cellsImages = spriteCells.getSprites();
        currentWidth = originalWidth = cellsImages[0][0].getWidth();
        currentHeight = cellsImages[0][0].getHeight();
        tipsBarImages = spriteCells.getTipsBarImages();
    }


    public synchronized void createCells() {
        int n = 8;
        boolean isNew = false;
        if (cells == null) {
            cells = new LinkedList<>();
            isNew = true;
            n = 50;
        }
        Random r = new Random();
        boolean isSpace;

        for (int i = 0; i < n && (cells.size() < 20 || i == 0); i++) {
            int x = r.nextInt(Game.boardWidth - currentWidth);
            int y = r.nextInt(Game.boardHeight - currentHeight);

            int value = r.nextInt(8) + 1;
            Cell temp = new Cell(x, y, currentWidth, currentHeight, value, getCellImage(value - 1, value - 1), this);

            isSpace = true;
            for (Cell c : cells) {
                if (c.isOccupiedSpace(temp)) {
                    isSpace = false;
                    break;
                }
            }

            if (isSpace) {
                cells.add(temp);
            }
        }

        if (isNew)
            selectArmageddonCell();
        else
            resetInheritance();
        selectInheritanceCells();
    }

    public void boreChildren(){
        //TU METODA DO RODZENIA DZIECI

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
            }
        } else
            Game.setIsMinSize(true);
    }

    private void scaleCellsSprites(double scale) {
        if (couldResize(scale)) {
            for (int i = 0; i < cellsImages.length; i++) {
                for (int j = 0; j < cellsImages[i].length; j++) {
                    BufferedImage before = cellsImages[i][j];
                    int w = before.getWidth();
                    int h = before.getHeight();
                    BufferedImage after = new BufferedImage((int) Math.round(scale * w), (int) Math.round(scale * h),
                            TYPE_INT_ARGB);
                    AffineTransform at = new AffineTransform();
                    at.scale(scale, scale);
                    AffineTransformOp scaleOp = new AffineTransformOp(at, TYPE_BILINEAR);
                    after = scaleOp.filter(before, after);
                    cellsImages[i][j] = after;
                    currentWidth = cellsImages[0][0].getWidth();
                    currentHeight = cellsImages[0][0].getHeight();
                }
            }
        } else
            Game.setIsMinSize(true);
    }


    public synchronized void paintCells(Graphics g) {
        try {
            for (Cell c : cells) {
                g.drawImage(c.getCurrentImage(), Game.boardX + c.getXPosition(),
                        Game.boardY + c.getYPosition(), null);
            }
        } catch (ConcurrentModificationException e) {
            paintCells(g);
        }
    }

    private void selectArmageddonCell() {
        Random rand = new Random();
        int index = rand.nextInt(cells.size());
        cells.get(index).setArmageddon(true);
//        System.out.println(cells.get(index).x + " " + cells.get(index).y+ " " + cells.get(index).getGreatestValue());
    }

    private void resetInheritance() {
        for (Cell c : cells)
            c.setInheritance(0);
    }

    private void selectInheritanceCells() {
        Random rand = new Random();
        int n = rand.nextInt(cells.size());
        assignInheritanceToTheCells(10, n / 2);
        assignInheritanceToTheCells(20, n / 4);
        assignInheritanceToTheCells(30, n / 6);
        assignInheritanceToTheCells(50, n / 10);
        assignInheritanceToTheCells(100, n / 15);
    }

    private void assignInheritanceToTheCells(int value, int numberOfOccurrences){
        Random rand = new Random();
        for (int i = 0; i < numberOfOccurrences; i++) {
            int index = rand.nextInt(cells.size());
            while (cells.get(index).getInheritance() != 0) {
                index = rand.nextInt(cells.size());
            }
            cells.get(index).setInheritance(value);
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
                        if (cells.contains(cell)) {
                            if (cell.getCurrentValue() == 1) {
                                game.refreshScores(cell, player);
                                if (Game.isSoundTurnedOn)
                                    Sounds.playGamePointSound();
                                itCells.remove();
                            } else
                                cell.decreaseValue();
                            itBullet.remove();
                        }
                        break;
                    }
                }
            }
        } catch (ConcurrentModificationException e) {
            checkIfHit(player);
        }
    }

    public void drawTipBar(Graphics g) {
        for (int i = 0; i < 8; i++)
            g.drawImage(tipsBarImages[i], 356 + i * 60, Game.boardY + Game.boardHeight + 20, null);
    }

    private boolean couldResize(double scale) {
        return originalWidth < 2 * scale * cellsImages[0][0].getWidth();
    }

    public BufferedImage getCellImage(int x, int y) {
        return cellsImages[y][x];
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }


}
