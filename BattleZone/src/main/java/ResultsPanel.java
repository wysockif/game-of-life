import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.String.format;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.metal.MetalButtonUI;

public class ResultsPanel extends JPanel implements ActionListener {
    private BufferedImage leftWinner, rightWinner, tie;
    private BufferedImage currentBackground;

    private JLabel wayLabel;
    private JLabel saveLabel1, saveLabel2;
    private JCheckBox checkBox;

    private Game game;
    private JButton endButton;
    private JButton saveButton;
    private JLabel leftNameLabel, rightNameLabel;
    private JLabel leftScoreLabel, rightScoreLabel;
    private boolean isSaved;


    public ResultsPanel(Game game) {
        this.game = game;
        setLayout(null);
        loadBackgrounds();
        addButtons();
        addNamesLabels();
        addScoresLabels();
        addInfoLabels();
        addDefaultFilesFields();
        addAttachmentFields();
    }

    private void addScoresLabels() {
        leftScoreLabel = new JLabel("Wynik: " , JLabel.CENTER);
        leftScoreLabel.setBounds(155, 500, 240, 40);
        leftScoreLabel.setFont(new Font("Sans", Font.BOLD, 18));
        leftScoreLabel.setForeground(Color.WHITE);
        add(leftScoreLabel);

        rightScoreLabel = new JLabel("Wynik: ", JLabel.CENTER);
        rightScoreLabel.setBounds(715, 500, 240, 40);
        rightScoreLabel.setFont(new Font("Sans", Font.BOLD, 18));
        rightScoreLabel.setForeground(Color.WHITE);
        rightScoreLabel.setHorizontalAlignment(JTextField.CENTER);
        add(rightScoreLabel);

    }

    private void addInfoLabels() {
        wayLabel = new JLabel("", JLabel.CENTER);
        wayLabel.setBounds(0, 580, currentBackground.getWidth(), 40);
        wayLabel.setFont(new Font("Sans", Font.BOLD, 30));
        wayLabel.setForeground(Color.red);
        wayLabel.setHorizontalAlignment(JTextField.CENTER);
        add(wayLabel);
    }

    private void addNamesLabels() {
        leftNameLabel = new JLabel("Gracz 1", JLabel.CENTER);
        leftNameLabel.setBounds(155, 470, 240, 40);
        leftNameLabel.setFont(new Font("Sans", Font.BOLD, 30));
        leftNameLabel.setForeground(Color.WHITE);
        add(leftNameLabel);

        rightNameLabel = new JLabel("Gracz 2", JLabel.CENTER);
        rightNameLabel.setBounds(715, 470, 240, 40);
        rightNameLabel.setFont(new Font("Sans", Font.BOLD, 30));
        rightNameLabel.setForeground(Color.WHITE);
        rightNameLabel.setHorizontalAlignment(JTextField.CENTER);
        add(rightNameLabel);
    }

    private void addButtons() {
        endButton = new JButton("Zakończ");
        endButton.setFont(new Font("Sans", Font.BOLD, 25));
        endButton.setBackground(Color.black);
        endButton.setForeground(Color.white);
        endButton.addActionListener(this);
        endButton.setBounds(500, 710, 200, 60);
        endButton.setFocusable(false);
        add(endButton);
    }


    private void addDefaultFilesFields() {
        saveLabel2 = new JLabel("Zapisz pliki graficzne ostatniego stanu gry tylko do domyślnej lokalizacji:", JLabel.LEFT);
        saveLabel2.setBounds(350, 645, 500, 20);
        saveLabel2.setForeground(Color.WHITE);
        add(saveLabel2);

        checkBox = new JCheckBox();
        checkBox.setBounds(765, 645, 20, 20);
        checkBox.doClick();
        checkBox.setBackground(Color.darkGray);
        checkBox.addActionListener(this);
        add(checkBox);
    }

    private void addAttachmentFields() {
        saveLabel1 = new JLabel("Zapisz także do własnej lokalizacji: ", JLabel.RIGHT);
        saveLabel1.setBounds(150, 680, 400, 20);
        saveLabel1.setForeground(Color.WHITE);
        add(saveLabel1);


        saveButton = new JButton("Wybierz lokalizację");
        saveButton.setBackground(Color.black);
        saveButton.setForeground(Color.white);
        saveButton.addActionListener(this);
        saveButton.setEnabled(false);
        saveButton.setBounds(570, 675, 200, 30);
        saveButton.setFocusable(false);

        add(saveButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.lightGray);
        g.fillRect(0, 500, currentBackground.getWidth(), 50);
        g.setColor(Color.darkGray);
        g.fillRect(0, 550, currentBackground.getWidth(), 310);
        g.drawImage(currentBackground, 0, 0, currentBackground.getWidth(), currentBackground.getHeight(), null);
        g.fillRect(0, 0, currentBackground.getWidth(), 30);

//        g.drawRect(715, 500, 240, 40);
//        g.drawRect(155, 500, 240, 40);
    }

    private void loadBackgrounds() {
        try {
            this.leftWinner = ImageIO.read(new File("src/main/resources/img/backgrounds/leftWinner.png"));
            this.rightWinner = ImageIO.read(new File("src/main/resources/img/backgrounds/rightWinner.png"));
            this.tie = ImageIO.read(new File("src/main/resources/img/backgrounds/tie.png"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Błąd krytyczny!\n" +
                    "Nie mogę znaleźć pliku z obrazem panelu końcowego!", "Błąd krytyczny!", JOptionPane.ERROR_MESSAGE);
            System.exit(2);
        }
        currentBackground = tie;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();


        if (src == endButton) {
            if (checkBox.isSelected()) {
                isSaved = true;
            }

            if (isSaved) {
                game.getGamePanel().saveBoard("savedImages/Board");
                game.getGamePanel().savePanel("savedImages/Game");
                game.dispose();

            } else
                JOptionPane.showMessageDialog(null, "Żaden plik nie został wybrany!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }

        if (src == checkBox) {
            if (checkBox.isSelected()) {
                saveButton.setEnabled(false);
                isSaved = true;
            } else {
                saveButton.setEnabled(true);
                saveButton.setForeground(Color.white);
                saveButton.setText("Wybierz lokalizację");
                isSaved = false;
            }

        }
        if (src == saveButton) {
            saveButton.setForeground(Color.white);

            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                String filePath = fc.getSelectedFile().getAbsolutePath();
                if (filePath.contains("."))
                    filePath = filePath.substring(0, filePath.lastIndexOf('.'));
                game.getGamePanel().savePanel(filePath + "Panel");
                game.getGamePanel().saveBoard(filePath + "Board");
                saveButton.setEnabled(false);
                saveButton.setText("Zapisano");
                saveButton.setUI(new MetalButtonUI() {
                    protected Color getDisabledTextColor() {
                        return Color.green;
                    }
                });
                checkBox.setEnabled(false);
                setFocusable(true);
                isSaved = true;
            }
        }
    }

    public void preparePlayers(Player lPlayer, Player rPlayer, String lName, String rName) {
        leftScoreLabel.setText("Wynik: " + lPlayer.getPointsGained());
        rightScoreLabel.setText("Wynik: " + rPlayer.getPointsGained());
        leftNameLabel.setText(lName);
        rightNameLabel.setText(rName);
    }

    public void prepareBackground(Results way) {
        if (way == Results.LEFT_ARMAGEDDON || way == Results.LEFT_MAX_SCORE || way == Results.LEFT_TIME_IS_UP) {
            currentBackground = leftWinner;
        } else if (way == Results.RIGHT_ARMAGEDDON || way == Results.RIGHT_MAX_SCORE || way == Results.RIGHT_TIME_IS_UP) {
            currentBackground = rightWinner;
        } else if (way == Results.TIE)
            currentBackground = tie;
    }

    public void prepareMassage(Results way) {
        if (way == Results.TIE)
            wayLabel.setText("Koniec gry! Nastąpił remis!");
        else if (way == Results.LEFT_TIME_IS_UP)
            wayLabel.setText(format("Koniec Gry! %s zwyciężył!", leftNameLabel.getText()));
        else if (way == Results.LEFT_MAX_SCORE)
            wayLabel.setText(format("Koniec gry! %s osiągnął maksymalną ilość punktów!", leftNameLabel.getText()));
        else if (way == Results.LEFT_ARMAGEDDON)
            wayLabel.setText(format("Koniec gry! %s zestrzelił komórkę armagedon!", leftNameLabel.getText()));
        else if (way == Results.RIGHT_TIME_IS_UP)
            wayLabel.setText(format("Koniec Gry! %s zwyciężył!", rightNameLabel.getText()));
        else if (way == Results.RIGHT_MAX_SCORE)
            wayLabel.setText(format("Koniec gry! %s osiągnął maksymalną ilość punktów!", rightNameLabel.getText()));
        else if (way == Results.RIGHT_ARMAGEDDON)
        wayLabel.setText(format("Koniec gry! %s zestrzelił komórkę armagedon!", rightNameLabel.getText()));
    }
}
