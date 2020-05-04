import java.io.BufferedReader;
import java.io.IOException;
import static java.lang.String.format;

public class InputFileReader {
    private String[][] wordsInLines;
    private Constant[] constants;

    public InputFileReader(BufferedReader bufferedReader) throws IOException, NullPointerException, IllegalArgumentException {
        wordsInLines = new String[9][2];
        constants = new Constant[9];
        constants[0] = new Constant("A", "boardWidth", 0, 300, 580);
        constants[1] = new Constant("B", "boardHeight", 1, 300, 500);
        constants[2] = new Constant("P", "maxNumberOfShots", 2, 1, 10);
        constants[3] = new Constant("H", "timeToGenerateNewCells", 3, 5, 60);
        constants[4] = new Constant("X", "timeToGenerateKidsCells", 4, 5, 60);
        constants[5] = new Constant("Y", "timeToIncreaseCellsValues", 5, 5, 60);
        constants[6] = new Constant("Z", "timeToChangeBulletsSpeedAndCellsSize", 6, 5, 60);
        constants[7] = new Constant("K", "percentageIncreaseInBulletsSpeed", 7, 0, 200);
        constants[8] = new Constant("L", "percentageDecreaseInCellsSize", 8, 0, 50);

        readData(bufferedReader);

    }

    public void readData(BufferedReader bufferedReader) throws IOException {
        loadConfiguration(bufferedReader);

        for (Constant c : constants)
            assignValue(c);
    }

    private void loadConfiguration(BufferedReader bufferedReader) throws IOException, NullPointerException, IllegalArgumentException, IndexOutOfBoundsException {
        for (int i = 0; i < 9; i++) {
            String line = bufferedReader.readLine();
            int count = line.length() - line.replace("=", "").length();
            if(line.contains("=") && count == 1)
                wordsInLines[i] = line.split("=");
            else
                throw new IllegalArgumentException("Niepoprawny format parametru: " + constants[i].key);
        }
        if (bufferedReader.readLine() != null)
            throw new IllegalArgumentException();

    }

    private void assignValue(Constant constant) throws IllegalArgumentException, IndexOutOfBoundsException {
        int value;
        try {
            value = Integer.parseInt(wordsInLines[constant.index][1]);

            if (!wordsInLines[constant.index][0].equals(constant.key))
                throw new IllegalArgumentException("Wystąpił błąd podczas odczytu stałej " + constant.key);
            if (value > constant.maxValue || value < constant.minValue)
                throw new IllegalArgumentException(format("Nieprawidłowa wartość przy stałej %s. " +
                        "Dopuszczalne wartości od %d do %d.", constant.key, constant.minValue, constant.maxValue));
            constant.value = value;

        } catch (NumberFormatException e) {
            MenuPanel.isFileOK = false;
            throw new IllegalArgumentException("Niepoprawny parametr dla stałej " + constant.key);
        }
    }

    public int getBoardWidth() {
        return constants[0].value;
    }

    public int getBoardHeight() {
        return constants[1].value;
    }

    public int getMaxNumberOfShots() {
        return constants[2].value;
    }

    public int getTimeToGenerateNewCells() {
        return constants[3].value;
    }

    public int getTimeToGenerateKidsCells() {
        return constants[4].value;
    }

    public int getTimeToIncreaseCellsValues() {
        return constants[5].value;
    }

    public int getTimeToChangeBulletsSpeedAndCellsSize() {
        return constants[6].value;
    }

    public int getPercentageIncreaseInBulletsSpeed() {
        return constants[7].value;
    }

    public int getPercentageDecreaseInCellsSize() {
        return constants[8].value;
    }

    private class Constant {
        private String key;
        private int value;
        private int index;
        private int minValue;
        private int maxValue;

        public Constant(String key, String name, int index, int minValue, int maxValue) {
            this.key = key;
            this.index = index;
            this.minValue = minValue;
            this.maxValue = maxValue;
        }
    }
}
