
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.regex.PatternSyntaxException;

import org.junit.Test;

public class InputFileReaderTest{

    @Test(expected = IllegalArgumentException.class)
    public void readData_valueOutOfLimits_throwException() throws IOException {
        // given
        File f = new File("src/test/resources/incorrect_config1.txt");

        // when
        InputFileReader inputFileReader = new InputFileReader(f);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void readData_stringInsteadOfInt_throwException() throws IOException {
        // given
        File f = new File("src/test/resources/incorrect_config2.txt");

        // when
        InputFileReader inputFileReader = new InputFileReader(f);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void readData_tooManyLinesInFile_throwException() throws IOException {
        // given
        File f = new File("src/test/resources/incorrect_config3.txt");

        // when
        InputFileReader inputFileReader = new InputFileReader(f);

        // then
        assert false;
    }

    @Test(expected = NullPointerException.class)
    public void readData_tooLittleLinesInFile_throwException() throws IOException {
        // given
        File f = new File("src/test/resources/incorrect_config4.txt");

        // when
        InputFileReader inputFileReader = new InputFileReader(f);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void readData_negativeNumber_throwException() throws IOException {
        // given
        File f = new File("src/test/resources/incorrect_config5.txt");

        // when
        InputFileReader inputFileReader = new InputFileReader(f);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void readData_changedOrder_throwException() throws IOException {
        // given
        File f = new File("src/test/resources/incorrect_config6.txt");

        // when
        InputFileReader inputFileReader = new InputFileReader(f);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void readData_incorrectKeyLetter_throwException() throws IOException {
        // given
        File f = new File("src/test/resources/incorrect_config7.txt");

        // when
        InputFileReader inputFileReader = new InputFileReader(f);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void readData_toManyArgumentsForOneValue_throwException() throws IOException {
        // given
        File f = new File("src/test/resources/incorrect_config8.txt");

        // when
        InputFileReader inputFileReader = new InputFileReader(f);

        // then
        assert false;
    }

    @Test(expected = FileNotFoundException.class)
    public void readData_nonExistentFile_throwException() throws IOException {
        // given
        File f = new File("src/test/resources/non-existent.txt");

        // when
        InputFileReader inputFileReader = new InputFileReader(f);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void readData_invalidFileExtension_throwException() throws IOException {
        // given
        File f = new File("src/test/resources/invalid_extension.docx");

        // when
        InputFileReader inputFileReader = new InputFileReader(f);

        // then
        assert false;
    }




    @Test
    public void getters_correctValues_correctlyReadValues() throws IOException {
        // given
        File f = new File("src/test/resources/correct_config.txt");

        // when
        InputFileReader inputFileReader = new InputFileReader(f);
        int boardWidth = inputFileReader.getBoardWidth();
        int boardHeight = inputFileReader.getBoardHeight();
        int maxNumberOfShots = inputFileReader.getMaxNumberOfShots();
        int timeToGenerateNewCells = inputFileReader.getTimeToGenerateNewCells();
        int timeToGenerateKidsCells = inputFileReader.getTimeToGenerateKidsCells();
        int timeToIncreaseCellsValues= inputFileReader.getTimeToIncreaseCellsValues();
        int timeToChangeBulletsSpeedAndCellsSize = inputFileReader.getTimeToChangeBulletsSpeedAndCellsSize();
        int percentageIncreaseInBulletsSpeed = inputFileReader.getPercentageIncreaseInBulletsSpeed();
        int percentageDecreaseInCellsSize = inputFileReader.getPercentageDecreaseInCellsSize();

        // then
        assertThat(boardWidth).isEqualTo(400);
        assertThat(boardHeight).isEqualTo(500);
        assertThat(maxNumberOfShots).isEqualTo(10);
        assertThat(timeToGenerateNewCells).isEqualTo(45);
        assertThat(timeToGenerateKidsCells).isEqualTo(21);
        assertThat(timeToIncreaseCellsValues).isEqualTo(16);
        assertThat(timeToChangeBulletsSpeedAndCellsSize).isEqualTo(5);
        assertThat(percentageIncreaseInBulletsSpeed).isEqualTo(15);
        assertThat(percentageDecreaseInCellsSize).isEqualTo(10);

    }
}