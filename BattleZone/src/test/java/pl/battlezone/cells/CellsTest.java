package pl.battlezone.cells;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.battlezone.Game;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CellsTest {
    private static BufferedImage image;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException {
        image = ImageIO.read(new File("src/test/resources/cell.png"));
    }

    @Mock
    private Game game;

    @Test
    public void increaseValues_twoCellsWithValuesBelowMax_increasedValues() {
        // given
        int givenValue1 = 3;
        int givenValue2 = 5;
        Cells cells = new Cells(game, new SpriteCells("img/cells.png", 100, 100));
        cells.setCells(new LinkedList<>());
        cells.getCells().add(new Cell(0, 0, 10, 10, givenValue1, givenValue1 - 1, image, cells));
        cells.getCells().add(new Cell(100, 100, 20, 20, givenValue2, givenValue2 - 1, image, cells));

        // when
        cells.increaseValues();
        int value1 = cells.getCells().get(0).getCurrentValue();
        int value2 = cells.getCells().get(1).getCurrentValue();

        // then
        int expected1 = 4;
        int expected2 = 6;
        assertThat(value1).isEqualTo(expected1);
        assertThat(value2).isEqualTo(expected2);
    }

    @Test
    public void increaseValues_twoCellsWithMaxValues_doNotIncreasedValues() {
        // given
        int givenValue1 = 8;
        int givenValue2 = 8;
        Cells cells = new Cells(game, new SpriteCells("img/cells.png", 100, 100));
        cells.setCells(new LinkedList<>());
        cells.getCells().add(new Cell(0, 0, 10, 10, givenValue1, givenValue1 - 1, image, cells));
        cells.getCells().add(new Cell(100, 100, 20, 20, givenValue2, givenValue2 - 1, image, cells));

        // when
        cells.increaseValues();
        int value1 = cells.getCells().get(0).getCurrentValue();
        int value2 = cells.getCells().get(1).getCurrentValue();

        // then
        int expected1 = 8;
        int expected2 = 8;
        assertThat(value1).isEqualTo(expected1);
        assertThat(value2).isEqualTo(expected2);
    }

    @Test
    public void increaseValues_oneCellWithMaxValueAndTheOtherNot_increasedValueOfTheOtherCell() {
        // given
        int givenValue1 = 8;
        int givenValue2 = 3;
        Cells cells = new Cells(game, new SpriteCells("img/cells.png", 100, 100));
        cells.setCells(new LinkedList<>());
        cells.getCells().add(new Cell(0, 0, 10, 10, givenValue1, givenValue1 - 1, image, cells));
        cells.getCells().add(new Cell(100, 100, 20, 20, givenValue2, givenValue2 - 1, image, cells));

        // when
        cells.increaseValues();
        int value1 = cells.getCells().get(0).getCurrentValue();
        int value2 = cells.getCells().get(1).getCurrentValue();

        // then
        int expected1 = 8;
        int expected2 = 4;
        assertThat(value1).isEqualTo(expected1);
        assertThat(value2).isEqualTo(expected2);
    }

    @Test
    public void reduceSize_sizeLessThanAcceptable_doNotReduceCellsSizes() {
        // given
        Cells cells = new Cells(game, new SpriteCells("img/cells.png", 100, 100));
        cells.setCells(new LinkedList<>());
        cells.getCells().add(new Cell(0, 0, 10, 10, 3, 2, image, cells));
        cells.getCells().add(new Cell(100, 100, 10, 10, 4, 3, image, cells));

        // when
        cells.reduceSize(80);
        double width1 = cells.getCells().get(0).getWidth();
        double width2 = cells.getCells().get(1).getWidth();

        // then
        double expected1 = 10.0;
        double expected2 = 10.0;
        assertThat(width1).isEqualTo(expected1);
        assertThat(width2).isEqualTo(expected2);
    }

    @Test
    public void reduceSize_appropriateSize_reducedCellsSizes() {
        // given
        Cells cells = new Cells(game, new SpriteCells("img/cells.png", 100, 100));
        cells.setCells(new LinkedList<>());
        cells.getCells().add(new Cell(0, 0, 100, 100, 3, 2, image, cells));
        cells.getCells().add(new Cell(100, 100, 100, 100, 4, 3, image, cells));

        // when
        cells.reduceSize(15);
        double width1 = cells.getCells().get(0).getWidth();
        double width2 = cells.getCells().get(1).getWidth();

        // then
        double expected1 = 85.0;
        double expected2 = 85.0;
        assertThat(width1).isEqualTo(expected1);
        assertThat(width2).isEqualTo(expected2);
    }
}