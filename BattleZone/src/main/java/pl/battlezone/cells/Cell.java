package pl.battlezone.cells;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Cell extends Rectangle {
    private int currentValue;
    private int greatestValue;
    private int inheritance = 0;
    private boolean isArmageddon = false;

    private BufferedImage currentImage;
    private Cells cells;
    private int childrenCells;

    public Cell(int x, int y, int w, int h, int value, int childrenCells, BufferedImage bufferedImage, Cells cells) {
        super(x, y, w, h);
        this.childrenCells = childrenCells;
        this.currentImage = bufferedImage;
        this.cells = cells;
        currentValue = greatestValue = value;
    }

    public void increaseValue() {
        if (currentValue == greatestValue && currentValue < 8) {
            currentValue++;
            greatestValue++;
            if (childrenCells != 0)
                childrenCells++;
            refreshCellImage();
        } else if (currentValue < greatestValue && currentValue < 8) {
            currentValue++;
            if (childrenCells != 0)
                childrenCells++;
            refreshCellImage();
        }
    }

    public boolean checkToDecreaseChildren() {
        return childrenCells > 0;
    }

    public void decreaseChildren() {
        childrenCells--;
    }

    public void decreaseValue() {
        if (currentValue > 1) {
            currentValue--;
            if (childrenCells != 0)
                childrenCells--;
            refreshCellImage();
        }
    }


    public void reduceSize(int percent) {
        x += (int) Math.round(0.01 * percent * currentImage.getWidth() / 2);
        y += (int) Math.round(0.01 * percent * currentImage.getHeight() / 2);
        refreshCellImage();
    }

    private void refreshCellImage() {
        if (currentValue > 0) {
            currentImage = cells.getCellImage(currentValue - 1, greatestValue - 1);
            width = currentImage.getWidth();
            height = currentImage.getHeight();
        }
    }

    public boolean isOccupiedSpace(Cell c) {
        return intersects(c) || contains(c);
    }

    public boolean isArmageddon() {
        return isArmageddon;
    }

    public BufferedImage getCurrentImage() {
        return currentImage;
    }

    public int getXPosition() {
        return x;
    }

    public int getYPosition() {
        return y;
    }

    public int getInheritance() {
        return inheritance;
    }

    public int getGreatestValue() {
        return greatestValue;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public int getChildrenCells() {
        return childrenCells;
    }

    public void setArmageddon(boolean armageddon) {
        isArmageddon = armageddon;
    }

    public void setInheritance(int inheritance) {
        this.inheritance = inheritance;
    }

    public void updateNumberOfChildren() {
        this.childrenCells = currentValue - 1;
    }
}

