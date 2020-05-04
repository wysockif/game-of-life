import java.awt.*;
import java.awt.image.BufferedImage;

public class Cell extends Rectangle {
    private int currentValue;
    private int greatestValue;

    private BufferedImage currentImage;
    private boolean isArmageddon = false;
    private int inheritance = 0;

    public Cell(int x, int y, int w, int h, int value) {
        super(x, y, w, h);
        currentValue = greatestValue = value;
        currentImage = Cells.getCellImage(value - 1, value - 1);


    }

    public void increaseValue() {
        if (currentValue == greatestValue && currentValue < 8) {
            currentValue++;
            greatestValue++;
            refreshCellImage();
        } else if (currentValue < greatestValue && currentValue < 8) {
            currentValue++;
            refreshCellImage();

        }
    }

    public void decreaseValue() {
        currentValue--;
        refreshCellImage();
    }


    public void reduceSize(int percent) {

        x += (int) Math.round(0.01 * percent * currentImage.getWidth() / 2);
        y += (int) Math.round(0.01 * percent * currentImage.getHeight() / 2);

        refreshCellImage();
    }

    private void refreshCellImage() {
        if (currentValue > 0) {
            currentImage = Cells.getCellImage(currentValue - 1, greatestValue - 1);
            width = currentImage.getWidth();
            height = currentImage.getHeight();
        }
    }

    public boolean isOccupiedSpace(Cell c){
        if(intersects(c) || contains(c))
            return true;
        return false;
    }



    public int getXPosition() {
        return x;
    }

    public int getYPosition() {
        return y;
    }

    public BufferedImage getCurrentImage() {
        return currentImage;
    }


    public void setArmageddon(boolean armageddon) {
        isArmageddon = armageddon;
    }


    public int getInheritance() {
        return inheritance;
    }

    public void setInheritance(int inheritance) {
        this.inheritance = inheritance;
    }

    public int getGreatestValue() {
        return greatestValue;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public boolean isArmageddon() {
        return isArmageddon;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}

