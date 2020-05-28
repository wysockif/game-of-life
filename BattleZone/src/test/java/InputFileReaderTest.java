import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InputFileReaderTest {

    @Test(expected = IllegalArgumentException.class)
    public void readData_valueOutOfLimits_throwException() throws IOException {
        // given
        FileReader fileReader = new FileReader("src/test/resources/incorrect_config1.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // when
        InputFileReader inputFileReader = new InputFileReader(bufferedReader);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void readData_stringInsteadOfInt_throwException() throws IOException {
        // given
        FileReader fileReader = new FileReader("src/test/resources/incorrect_config2.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // when
        InputFileReader inputFileReader = new InputFileReader(bufferedReader);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void readData_tooManyLinesInFile_throwException() throws IOException {
        // given
        FileReader fileReader = new FileReader("src/test/resources/incorrect_config3.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // when
        InputFileReader inputFileReader = new InputFileReader(bufferedReader);

        // then
        assert false;
    }

    @Test(expected = NullPointerException.class)
    public void readData_tooLittleLinesInFile_throwException() throws IOException {
        // given
        FileReader fileReader = new FileReader("src/test/resources/incorrect_config4.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // when
        InputFileReader inputFileReader = new InputFileReader(bufferedReader);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void readData_negativeNumber_throwException() throws IOException {
        // given
        FileReader fileReader = new FileReader("src/test/resources/incorrect_config5.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // when
        InputFileReader inputFileReader = new InputFileReader(bufferedReader);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void readData_changedOrder_throwException() throws IOException {
        // given
        FileReader fileReader = new FileReader("src/test/resources/incorrect_config6.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // when
        InputFileReader inputFileReader = new InputFileReader(bufferedReader);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void readData_incorrectKeyLetter_throwException() throws IOException {
        // given
        FileReader fileReader = new FileReader("src/test/resources/incorrect_config7.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // when
        InputFileReader inputFileReader = new InputFileReader(bufferedReader);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void readData_toManyArgumentsForOneValue_throwException() throws IOException {
        // given
        FileReader fileReader = new FileReader("src/test/resources/incorrect_config8.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // when
        InputFileReader inputFileReader = new InputFileReader(bufferedReader);

        // then
        assert false;
    }

    @Test(expected = FileNotFoundException.class)
    public void readData_nonExistentFile_throwException() throws IOException {
        // given
        FileReader fileReader = new FileReader("src/test/resources/non-existent.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // when
        InputFileReader inputFileReader = new InputFileReader(bufferedReader);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void readData_invalidFileExtension_throwException() throws IOException {
        // given
        FileReader fileReader = new FileReader("src/test/resources/invalid_extension.docx");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // when
        InputFileReader inputFileReader = new InputFileReader(bufferedReader);

        // then
        assert false;
    }


    @Test
    public void getters_correctValues_correctlyReadValues() throws IOException {
        FileReader fileReader = new FileReader("src/test/resources/correct_config.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // when
        InputFileReader inputFileReader = new InputFileReader(bufferedReader);
        int boardWidth = inputFileReader.getBoardWidth();
        int boardHeight = inputFileReader.getBoardHeight();
        int maxNumberOfShots = inputFileReader.getMaxNumberOfShots();
        int timeToGenerateNewCells = inputFileReader.getTimeToGenerateNewCells();
        int timeToGenerateKidsCells = inputFileReader.getTimeToGenerateKidsCells();
        int timeToIncreaseCellsValues = inputFileReader.getTimeToIncreaseCellsValues();
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