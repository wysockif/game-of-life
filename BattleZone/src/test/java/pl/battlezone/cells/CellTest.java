package pl.battlezone.cells;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CellTest {
    private static BufferedImage image;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException {
        image = ImageIO.read(new File("src/test/resources/cell.png"));
    }

    @Mock
    private Cells cells;

    @Test
    public void increaseValue_maxPossibleValue_didNotIncreaseValue() {
        // given
        int givenValue = 8;
        Cell cell = new Cell(0, 0, 0, 0, givenValue, givenValue - 1, image, cells);

        // when
        cell.increaseValue();
        int value = cell.getCurrentValue();

        // then
        assertThat(value).isEqualTo(givenValue);
    }

    @Test
    public void decreaseValue_minPossibleValue_didNotIncreaseValue() {
        //given
        int givenValue = 1;
        Cell cell = new Cell(0, 0, 0, 0, givenValue, givenValue - 1, image, cells);

        //when
        cell.decreaseValue();
        int childrenCellsValue = cell.getChildrenCells();

        //then
        int expectedChildrenValue = 0;
        assertThat(childrenCellsValue).isEqualTo(expectedChildrenValue);
    }


    @Test
    public void decreaseChildren_lessThenMaxValue_decreasedChildrenCellsValue() {
        //given
        int givenValue = 5;
        Cell cell = new Cell(0, 0, 0, 0, givenValue, givenValue - 1, image, cells);

        //when
        cell.decreaseChildren();
        int childrenCellsValue = cell.getChildrenCells();

        //then
        int expectedChildrenValue = 3;
        assertThat(childrenCellsValue).isEqualTo(expectedChildrenValue);

    }

    @Test
    public void increaseValue_lessThanMaxPossibleValue_increasedValue() {
        // given
        int givenValue = 5;
        given(cells.getCellImage(givenValue, givenValue)).willReturn(image);
        Cell cell = new Cell(0, 0, 0, 0, givenValue, givenValue - 1, image, cells);

        // when
        cell.increaseValue();
        int value = cell.getCurrentValue();

        // then
        int expectedValue = 6;
        assertThat(value).isEqualTo(expectedValue);
    }

    @Test
    public void decreaseValue_moreThanMinPossibleValue_decreasedValue() {
        // given
        int givenValue = 5;
        given(cells.getCellImage(givenValue - 2, givenValue - 1)).willReturn(image);
        Cell cell = new Cell(0, 0, 0, 0, givenValue, givenValue - 1, image, cells);

        // when
        cell.decreaseValue();
        int value = cell.getCurrentValue();

        // then
        int expectedValue = 4;
        assertThat(value).isEqualTo(expectedValue);
    }

    @Test
    public void decreaseValue_minPossibleValue_didNotDecreaseValue() {
        // given
        int givenValue = 1;
        Cell cell = new Cell(0, 0, 0, 0, givenValue, givenValue - 1, image, cells);

        // when
        cell.decreaseValue();
        int value = cell.getCurrentValue();

        // then
        int expectedValue = 1;
        assertThat(value).isEqualTo(expectedValue);
    }

    @Test
    public void checkToDecreaseChildren_minPossibleValue_returnFalse() {
        //given
        int givenValue = 1;
        Cell cell = new Cell(0, 0, 0, 0, givenValue, givenValue - 1, image, cells);

        //when
        boolean checkChildren = cell.checkToDecreaseChildren();

        //then
        boolean expected = false;
        assertThat(checkChildren).isEqualTo(expected);

    }

    @Test
    public void checkToDecreaseChildren_minPossibleValue_returnTrue() {
        //given
        int givenValue = 5;
        Cell cell = new Cell(0, 0, 0, 0, givenValue, givenValue - 1, image, cells);

        //when
        boolean checkChildren = cell.checkToDecreaseChildren();

        //then
        boolean expected = true;
        assertThat(checkChildren).isEqualTo(expected);
    }

    @Test
    public void reduceSize_initialSizeOfCell_reducedSize() {
        // given
        int value = 5;
        Cell cell = new Cell(0, 0, 0, 0, value, value - 1, image, cells);
        given(cells.getCellImage(value - 1, value - 1)).willReturn(image);
        int initialX = cell.x;
        int initialY = cell.y;

        // when
        cell.reduceSize(15);
        int x = cell.x;
        int y = cell.y;

        // then
        int expectedX = 8;
        int expectedY = 8;
        assertThat(x).isEqualTo(expectedX);
        assertThat(y).isEqualTo(expectedY);
    }

    @Test
    public void isOccupied_twoCellsIntersect_returnTrue() {
        // given
        Cell cell1 = new Cell(10, 0, 100, 100, 5, 4, image, cells);
        Cell cell2 = new Cell(0, 10, 100, 100, 5, 4, image, cells);


        // when
        boolean isOccupied = cell1.isOccupiedSpace(cell2);

        // then
        boolean expected = true;
        assertThat(isOccupied).isEqualTo(expected);
    }

    @Test
    public void isOccupied_cellContainsAnotherCell_returnTrue() {
        // given
        Cell cell1 = new Cell(0, 0, 100, 100, 5, 4, image, cells);
        Cell cell2 = new Cell(10, 10, 30, 30, 5, 4, image, cells);

        // when
        boolean isOccupied = cell1.isOccupiedSpace(cell2);

        // then
        boolean expected = true;
        assertThat(isOccupied).isEqualTo(expected);
    }

    @Test
    public void isOccupied_twoCellsDoNotOccupiedTheSameSpace_returnFalse() {
        // given
        Cell cell1 = new Cell(0, 0, 100, 100, 5, 4, image, cells);
        Cell cell2 = new Cell(300, 300, 100, 100, 5, 4, image, cells);

        // when
        boolean isOccupied = cell1.isOccupiedSpace(cell2);

        // then
        boolean expected = false;
        assertThat(isOccupied).isEqualTo(expected);
    }

}