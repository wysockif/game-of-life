import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import static java.awt.Color.BLACK;
import static java.awt.Color.DARK_GRAY;
import static java.awt.Color.WHITE;
import static java.awt.Font.BOLD;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.LEFT;

public class MenuPanel extends JPanel implements ActionListener {
    public static boolean isFileOK;
    private int gameTime, gameScore;

    private String namePlayer1 = "Gracz 1";
    private String namePlayer2 = "Gracz 2";
    private JTextField nameField1, nameField2;
    private JTextField timeField, scoreField;
    private JCheckBox attachCheckBox, soundCheckBox;
    private JButton playButton;
    private JButton attachmentButton;
    private BufferedImage image;
    private Game game;

    public MenuPanel(Game game) {
        this.game = game;
        setLayout(null);

        try {
            this.image = ImageIO.read(MenuPanel.class.getResource("/img/backgrounds/mainMenu.png"));
        } catch (IOException e) {
            Sounds.playErrorSound();
            JOptionPane.showMessageDialog(null, "Błąd krytyczny!\n" +
                    "Nie mogę znaleźć pliku z obrazem menu!", "Błąd krytyczny!", ERROR_MESSAGE);
            System.exit(2);
        }
        addPlayButton();
        addNamesFields();
        addTimesFields();
        addScoresFields();
        addAttachmentFields();
        addSoundFields();
        addDefaultFilesFields();
    }


    private void addDefaultFilesFields() {
        JLabel attachmentLabel2 = new JLabel("Użyj domyślnego:");
        attachmentLabel2.setBounds(670, 620, 300, 20);
        attachmentLabel2.setForeground(WHITE);
        add(attachmentLabel2);

        attachCheckBox = new JCheckBox();
        attachCheckBox.setBounds(770, 620, 20, 20);
        attachCheckBox.doClick();
        attachCheckBox.setBackground(DARK_GRAY);
        attachCheckBox.addActionListener(this);
        add(attachCheckBox);
    }

    private void addAttachmentFields() {
        JLabel attachmentLabel1 = new JLabel("Załącz własny plik konfiguracyjny: ", LEFT);
        attachmentLabel1.setBounds(300, 620, 300, 20);
        attachmentLabel1.setForeground(WHITE);
        add(attachmentLabel1);

        attachmentButton = new JButton("Wczytaj");
        attachmentButton.setBackground(BLACK);
        attachmentButton.setForeground(WHITE);
        attachmentButton.addActionListener(this);
        attachmentButton.setEnabled(false);
        attachmentButton.setBounds(500, 615, 100, 30);
        add(attachmentButton);
    }

    private void addScoresFields() {
        JLabel scoreLabel = new JLabel("Wprowadź maksymalną ilość punktów do zdobycia:", LEFT);
        scoreLabel.setBounds(300, 560, 300, 20);
        scoreLabel.setForeground(WHITE);
        add(scoreLabel);

        scoreField = new JTextField("500");
        scoreField.setBounds(600, 550, 200, 50);
        scoreField.setBackground(BLACK);
        scoreField.setForeground(WHITE);
        scoreField.setHorizontalAlignment(CENTER);
        addPlaceholder(scoreField, "500", "Punkty:");
        add(scoreField);
    }

    private void addTimesFields() {
        String space = "                                                                    ";
        JLabel timeLabel = new JLabel("Wprowadź maksymalny czas gry: " + space + "[s]", LEFT);
        timeLabel.setBounds(400, 510, 600, 20);
        timeLabel.setForeground(WHITE);
        add(timeLabel);

        timeField = new JTextField("100");
        timeField.setBounds(600, 500, 200, 50);
        timeField.setBackground(BLACK);
        timeField.setForeground(WHITE);
        timeField.setHorizontalAlignment(CENTER);
        addPlaceholder(timeField, "100", "Czas gry:");
        add(timeField);
    }

    private void addNamesFields() {
        nameField1 = new JTextField("Gracz 1");
        nameField1.setBounds(155, 455, 190, 40);
        nameField1.setBackground(new Color(139 ,69 ,19));
        nameField1.setForeground(WHITE);
        nameField1.setHorizontalAlignment(CENTER);
        addPlaceholder(nameField1, "Gracz 1", "Wprowadź imię:");
        add(nameField1);

        nameField2 = new JTextField("Gracz 2");
        nameField2.setBounds(855, 455, 190, 40);
        nameField2.setBackground(new Color(139 ,69 ,19));
        nameField2.setForeground(WHITE);
        addPlaceholder(nameField2, "Gracz 2", "Wprowadź imię:");
        nameField2.setHorizontalAlignment(CENTER);
        add(nameField2);
    }

    private void addPlayButton() {
        playButton = new JButton("ROZPOCZNIJ");
        playButton.setFont(new Font("Sans", BOLD, 25));
        playButton.setBackground(new Color(0 ,100 ,0));
        playButton.setForeground(WHITE);
        playButton.addActionListener(this);
        playButton.setBounds(500, 670, 200, 70);
        add(playButton);
    }


    private void addSoundFields() {
        JLabel soundLabel = new JLabel("Graj z dźwiękiem: ");
        soundLabel.setBounds(480, 470, 300, 20);
        soundLabel.setForeground(WHITE);
        add(soundLabel);

        soundCheckBox = new JCheckBox();
        soundCheckBox.setBounds(600, 470, 20, 20);
        soundCheckBox.doClick();
        soundCheckBox.setBackground(DARK_GRAY);
        soundCheckBox.addActionListener(this);
        add(soundCheckBox);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == playButton)
            handlePlayButton();
        else if (src == attachCheckBox)
            handleAttachCheckBox();
        else if (src == attachmentButton)
            handleAttachmentButton();
    }

    private void handleAttachmentButton() {
        game.setConfig(null);
        isFileOK = false;
        attachmentButton.setForeground(WHITE);

        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(null) == APPROVE_OPTION) {
            try {
                FileReader fr = new FileReader(fc.getSelectedFile());
                BufferedReader bufferedReader = new BufferedReader(fr);
                checkFile(bufferedReader);
            } catch (FileNotFoundException ex) {
                handleFileError("Błąd odczytu pliku!");
            }
            if (game.getConfig() != null && isFileOK) {
                attachmentButton.setForeground(new Color(0, 127, 14));
                setFocusable(true);
            }
        }
    }

    private void handleAttachCheckBox() {
        if (attachCheckBox.isSelected())
            attachmentButton.setEnabled(false);
        else {
            attachmentButton.setEnabled(true);
            attachmentButton.setForeground(WHITE);
            game.setConfig(null);
            isFileOK = false;
        }
    }

    private void handlePlayButton() {
        if (canReadTime() && canReadScores()) {
            namePlayer1 = nameField1.getText();
            namePlayer2 = nameField2.getText();

            if (attachCheckBox.isSelected()) {
                if (game.getConfig() == null) {
                    isFileOK = true;
                    InputStream in = MenuPanel.class.getResourceAsStream("/config/ConfigFile.txt");
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    checkFile(br);
                }
            }
            if (isFileOK & game.getConfig() != null) {
                game.closeStartMenu();
            } else {
                Sounds.playErrorSound();
                JOptionPane.showMessageDialog(null, "Żaden plik nie został wczytany!", "Błąd", ERROR_MESSAGE);
            }
        }
    }


    private void checkFile(BufferedReader bufferedReader) {
        game.setConfig(null);
        try {
            game.setConfig(new InputFileReader(bufferedReader));
            isFileOK = true;
        } catch (FileNotFoundException e) {
            handleFileError("Nie znaleziono pliku!");
        } catch (IOException e) {
            handleFileError("Niepoprawny plik!");
        } catch (NullPointerException e) {
            handleFileError("Zły format pliku!");
        } catch (IllegalArgumentException e) {
            if (e.getMessage() == null)
                handleFileError("Niepoprawny format pliku!");
            else
                handleFileError(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            handleFileError("Błąd odczytu pliku!");
        }
        if (!isFileOK)
            game.setConfig(null);
    }

    private void handleFileError(String message){
        Sounds.playErrorSound();
        JOptionPane.showMessageDialog(null, message, "Błąd", ERROR_MESSAGE);
        isFileOK = false;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        g.setColor(Color.darkGray);
        g.fillRect(0, 450, image.getWidth(), 310);
        g.fillRect(0, 0, image.getWidth(), 30);
    }

    private void addPlaceholder(JTextField jTextField, String gString, String lString) {
        jTextField.setFocusable(true);
        jTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (jTextField.getText().equals(gString)) {
                    jTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (jTextField.getText().equals("") || jTextField.getText().equals(lString)) {
                    jTextField.setText(gString);
                }
            }
        });
    }

    private boolean canReadScores() {
        String t = scoreField.getText();
        int score;
        try {
            score = Integer.parseInt(t);
            if (score >= 10 && score <= 999) {
                gameScore = score;
                return true;
            } else throw new NumberFormatException();

        } catch (NumberFormatException e) {
            Sounds.playErrorSound();
            JOptionPane.showMessageDialog(null, "Niepoprawna wartość maksymalnej ilości punktów!\n" +
                    "Dopuszczalne są liczby naturalne od 10 do 999.", "Błąd", ERROR_MESSAGE);
        }
        return false;
    }

    private boolean canReadTime() {
        String t = timeField.getText();
        int time;
        try {
            time = Integer.parseInt(t);

            if (time >= 10 && time <= 999) {
                gameTime = time;
                return true;
            } else throw new NumberFormatException();

        } catch (NumberFormatException e) {
            Sounds.playErrorSound();
            JOptionPane.showMessageDialog(null, "Niepoprawna wartość maksymalnego czasu gry!\n" +
                    "Dopuszczalne są liczby naturalne od 10 do 999.", "Błąd", ERROR_MESSAGE);
        }
        return false;
    }

    public boolean isSoundTurnedOn() {
        return soundCheckBox.isSelected();
    }

    public int getGameTime() {
        return gameTime;
    }

    public int getGameScore() {
        return gameScore;
    }

    public String getNamePlayer1() {
        return namePlayer1;
    }

    public String getNamePlayer2() {
        return namePlayer2;
    }

}
