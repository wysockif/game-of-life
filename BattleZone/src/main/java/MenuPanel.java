import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuPanel extends JPanel implements ActionListener {
    public static boolean isFileOK;

    private int gameTime, gameScore;
    private String namePlayer1 = "Gracz 1";
    private String namePlayer2 = "Gracz 2";

    private JTextField nameField1, nameField2;
    private JTextField timeField, scoreField;
    private JLabel timeLabel, scoreLabel;
    private JLabel attachmentLabel1, attachmentLabel2;
    private JCheckBox checkBox;
    private JButton playButton;
    private JButton attachmentButton;
    private BufferedImage image;
    private Game game;

    public MenuPanel(Game game) {
        this.game = game;
        setLayout(null);

        try {
            image = ImageIO.read(new File("src/main/resources/img/background.png"));
        } catch (IOException e) {
            System.err.println("Brak pliku background.png");
            System.exit(2);
        }

        addPlayButton();
        addNamesFields();
        addTimesFields();
        addScoresFields();
        addAttachmentFields();
        addDefaultFilesFields();
    }

    private void addDefaultFilesFields() {
        attachmentLabel2 = new JLabel("Użyj domyślnego:");
        attachmentLabel2.setBounds(670, 620, 300, 20);
        attachmentLabel2.setForeground(Color.WHITE);
        add(attachmentLabel2);

        checkBox = new JCheckBox();
        checkBox.setBounds(770, 620, 20, 20);
        checkBox.doClick();
        checkBox.setBackground(Color.darkGray);
        checkBox.addActionListener(this);
        add(checkBox);
    }

    private void addAttachmentFields() {
        attachmentLabel1 = new JLabel("Załącz własny plik konfiguracyjny: ", JLabel.LEFT);
        attachmentLabel1.setBounds(300, 620, 300, 20);
        attachmentLabel1.setForeground(Color.WHITE);
        add(attachmentLabel1);


        attachmentButton = new JButton("Wczytaj");
//        attachmentButton.setFont(new Font("Sans", Font.BOLD, 25));
        attachmentButton.setBackground(Color.black);
        attachmentButton.setForeground(Color.white);
        attachmentButton.addActionListener(this);
        attachmentButton.setEnabled(false);
        attachmentButton.setBounds(500, 615, 100, 30);
        add(attachmentButton);
    }

    private void addScoresFields() {
        scoreLabel = new JLabel("Wprowadź maksymalną ilość punktów do zdobycia:", JLabel.LEFT);
        scoreLabel.setBounds(300, 560, 300, 20);
        scoreLabel.setForeground(Color.WHITE);
        add(scoreLabel);

        scoreField = new JTextField("500");
        scoreField.setBounds(600, 550, 200, 50);
        scoreField.setBackground(Color.BLACK);
        scoreField.setForeground(Color.WHITE);
        scoreField.setHorizontalAlignment(JTextField.CENTER);
        addPlaceholder(scoreField, "500", "Punkty:");
        add(scoreField);
    }

    private void addTimesFields() {
        String space = "                                                                    ";
        timeLabel = new JLabel("Wprowadź maksymalny czas gry: " + space + "[s]", JLabel.LEFT);
        timeLabel.setBounds(400, 510, 600, 20);
        timeLabel.setForeground(Color.WHITE);
        add(timeLabel);

        timeField = new JTextField("100");
        timeField.setBounds(600, 500, 200, 50);
        timeField.setBackground(Color.BLACK);
        timeField.setForeground(Color.WHITE);
        timeField.setHorizontalAlignment(JTextField.CENTER);
        addPlaceholder(timeField, "100", "Czas gry:");
        add(timeField);
    }

    private void addNamesFields() {
        nameField1 = new JTextField("Gracz 1");
        nameField1.setBounds(155, 455, 190, 40);
        nameField1.setBackground(Color.BLACK);
        nameField1.setForeground(Color.WHITE);
        nameField1.setHorizontalAlignment(JTextField.CENTER);
        addPlaceholder(nameField1, "Gracz 1", "Wprowadź imię:");
        add(nameField1);

        nameField2 = new JTextField("Gracz 2");
        nameField2.setBounds(855, 460, 190, 40);
        nameField2.setBackground(Color.BLACK);
        nameField2.setForeground(Color.WHITE);
        addPlaceholder(nameField2, "Gracz 2", "Wprowadź imię:");
        nameField2.setHorizontalAlignment(JTextField.CENTER);
        add(nameField2);
    }

    private void addPlayButton() {
        playButton = new JButton("ROZPOCZNIJ");
        playButton.setFont(new Font("Sans", Font.BOLD, 25));
        playButton.setBackground(Color.black);
        playButton.setForeground(Color.white);
        playButton.addActionListener(this);
        playButton.setBounds(500, 670, 200, 70);
        add(playButton);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == playButton) {
            if (canReadTime() && canReadScores()) {
                namePlayer1 = nameField1.getText();
                namePlayer2 = nameField2.getText();

                if (checkBox.isSelected()) {
                    if (game.getConfig() == null) {
                        try {
                            isFileOK = true;
                            game.setConfig(new InputFileReader(new File("src/main/resources/config/ConfigFile.txt")));
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Nie znaleziono pliku!");
                        }
                    }
                }

                if (isFileOK & game.getConfig() != null)
                    game.closeStartMenu();
                else
                    JOptionPane.showMessageDialog(null, "Żaden plik nie został wczytany!");


            }
        } else if (src == checkBox) {
            if (checkBox.isSelected())
                attachmentButton.setEnabled(false);
            else
                attachmentButton.setEnabled(true);


        } else if (src == attachmentButton) {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                checkFile(fc.getSelectedFile());
            }
        }
    }


    private void checkFile(File file) {
        try {
            game.setConfig(new InputFileReader(file));
            isFileOK = true;
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Nie znaleziono pliku!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Niepoprawny plik!");
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Zły format pliku!");
        } catch (IllegalArgumentException e) {
            if( e.getMessage() == null)
            JOptionPane.showMessageDialog(null, "Niepoprawny format pliku!");
            else
                JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Błąd odczytu pliku!");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        g.setColor(Color.darkGray);
        g.fillRect(0, 450, image.getWidth(), image.getHeight() - 440);
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
            JOptionPane.showMessageDialog(null, "Niepoprawna wartość maksymalnej ilości punktów!\n" +
                    "Dopuszczalne są liczby naturalne od 10 do 999.");
        }

        return false;
    }

    private boolean canReadTime() {
        String t = timeField.getText();
        int time;
        try {
            time = Integer.parseInt(t);

            if (time > 0) {
                gameTime = time;
                return true;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Niepoprawna wartość maksymalnego czasu gry!\n" +
                    "Dopuszczalne są liczby naturalne od 10 do 999.");
        }
        return false;
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