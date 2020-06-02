package pl.battlezone.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.metal.MetalButtonUI;
import pl.battlezone.Game;
import pl.battlezone.players.Player;
import pl.battlezone.technical.Sounds;

import static java.awt.Color.BLACK;
import static java.awt.Color.DARK_GRAY;
import static java.awt.Color.GREEN;
import static java.awt.Color.LIGHT_GRAY;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import static java.awt.Font.BOLD;
import static java.lang.String.format;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.LEFT;
import static javax.swing.SwingConstants.RIGHT;

public class ResultsPanel extends JPanel implements ActionListener {
    private boolean isSaved;

    private BufferedImage leftWinner, rightWinner, tie;
    private BufferedImage currentBackground;
    private JButton endButton;
    private JButton saveButton;
    private JLabel leftNameLabel, rightNameLabel;
    private JLabel leftScoreLabel, rightScoreLabel;
    private JLabel wayLabel;
    private JCheckBox checkBox;
    private Game game;

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
        leftScoreLabel = new JLabel("Wynik: ", CENTER);
        leftScoreLabel.setBounds(155, 500, 240, 40);
        leftScoreLabel.setFont(new Font("Sans", BOLD, 18));
        leftScoreLabel.setForeground(WHITE);
        add(leftScoreLabel);
        rightScoreLabel = new JLabel("Wynik: ", CENTER);
        rightScoreLabel.setBounds(715, 500, 240, 40);
        rightScoreLabel.setFont(new Font("Sans", BOLD, 18));
        rightScoreLabel.setForeground(WHITE);
        rightScoreLabel.setHorizontalAlignment(CENTER);
        add(rightScoreLabel);
    }

    private void addNamesLabels() {
        leftNameLabel = new JLabel("Gracz 1", CENTER);
        leftNameLabel.setBounds(155, 470, 240, 40);
        leftNameLabel.setFont(new Font("Sans", BOLD, 30));
        leftNameLabel.setForeground(WHITE);
        add(leftNameLabel);
        rightNameLabel = new JLabel("Gracz 2", CENTER);
        rightNameLabel.setBounds(715, 470, 240, 40);
        rightNameLabel.setFont(new Font("Sans", BOLD, 30));
        rightNameLabel.setForeground(WHITE);
        rightNameLabel.setHorizontalAlignment(CENTER);
        add(rightNameLabel);
    }

    private void addButtons() {
        endButton = new JButton("Zakończ");
        endButton.setFont(new Font("Sans", BOLD, 25));
        endButton.setBackground(RED);
        endButton.setForeground(WHITE);
        endButton.addActionListener(this);
        endButton.setBounds(500, 710, 200, 60);
        endButton.setFocusable(false);
        endButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                endButton.setBackground(RED.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                endButton.setBackground(RED);
            }
        });
        add(endButton);
    }

    private void addDefaultFilesFields() {
        JLabel saveLabel2 = new JLabel("Zapisz pliki graficzne ostatniego stanu gry tylko do domyślnej lokalizacji:", LEFT);
        saveLabel2.setBounds(350, 645, 500, 20);
        saveLabel2.setForeground(WHITE);
        add(saveLabel2);
        checkBox = new JCheckBox();
        checkBox.setBounds(765, 645, 20, 20);
        checkBox.doClick();
        checkBox.setBackground(DARK_GRAY);
        checkBox.addActionListener(this);
        add(checkBox);
    }

    private void addAttachmentFields() {
        JLabel saveLabel1 = new JLabel("Zapisz także do własnej lokalizacji: ", RIGHT);
        saveLabel1.setBounds(150, 680, 400, 20);
        saveLabel1.setForeground(WHITE);
        add(saveLabel1);
        saveButton = new JButton("Wybierz lokalizację");
        saveButton.setBackground(BLACK);
        saveButton.setForeground(WHITE);
        saveButton.addActionListener(this);
        saveButton.setEnabled(false);
        saveButton.setBounds(570, 675, 200, 30);
        saveButton.setFocusable(false);
        add(saveButton);
    }

    private void addInfoLabels() {
        wayLabel = new JLabel("", CENTER);
        wayLabel.setBounds(0, 580, currentBackground.getWidth(), 40);
        wayLabel.setFont(new Font("Sans", BOLD, 30));
        wayLabel.setForeground(RED);
        wayLabel.setHorizontalAlignment(CENTER);
        add(wayLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(LIGHT_GRAY);
        g.fillRect(0, 500, currentBackground.getWidth(), 50);
        g.setColor(DARK_GRAY);
        g.fillRect(0, 550, currentBackground.getWidth(), 310);
        g.drawImage(currentBackground, 0, 0, currentBackground.getWidth(), currentBackground.getHeight(), null);
        g.fillRect(0, 0, currentBackground.getWidth(), 30);
    }

    private void loadBackgrounds() {
        try {
            this.leftWinner = ImageIO.read(ResultsPanel.class.getResource("/img/backgrounds/leftWinner.png"));
            this.rightWinner = ImageIO.read(ResultsPanel.class.getResource("/img/backgrounds/rightWinner.png"));
            this.tie = ImageIO.read(ResultsPanel.class.getResource("/img/backgrounds/tie.png"));
        } catch (IOException | IllegalArgumentException e) {
            Sounds.playErrorSound();
            JOptionPane.showMessageDialog(null, "Błąd krytyczny!\n" +
                    "Nie mogę znaleźć pliku z obrazem panelu końcowego!", "Błąd krytyczny!", ERROR_MESSAGE);
            System.exit(2);
        }
        currentBackground = tie;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == endButton)
            handleEndButton();
        else if (src == checkBox)
            handleCheckBox();
        else if (src == saveButton)
            handleSaveButton();
    }

    private void handleEndButton() {
        if (checkBox.isSelected())
            isSaved = true;
        if (isSaved) {
            saveImagesToTheDefaultLocalisation();
            game.dispose();
        } else {
            Sounds.playErrorSound();
            JOptionPane.showMessageDialog(null, "Żaden plik nie został wybrany!", "Błąd",
                    ERROR_MESSAGE);
        }
    }

    private void saveImagesToTheDefaultLocalisation() {
        try {
            String path = new File(Results.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getPath();
            String name = new File(Results.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getName();
            path = path.replaceAll(name, "");
            game.getGamePanel().saveBoard(path + "Board");
            game.getGamePanel().savePanel(path + "Game");
        } catch (URISyntaxException ex) {
            Sounds.playErrorSound();
            JOptionPane.showMessageDialog(null, "Nie mogę zapisać plików w domyślnej lokalizacji!",
                    "Błąd", ERROR_MESSAGE);
        }
    }

    private void handleSaveButton() {
        saveButton.setForeground(WHITE);
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(null) == APPROVE_OPTION) {
            String filePath = fc.getSelectedFile().getAbsolutePath();
            saveImagesToTheSelectedLocalisation(filePath);
            saveButton.setText("Zapisano");
            saveButton.setUI(new MetalButtonUI() {
                protected Color getDisabledTextColor() {
                    return GREEN;
                }
            });
            saveButton.setEnabled(false);
            checkBox.setEnabled(false);
            setFocusable(true);
            isSaved = true;
        }
    }

    private void saveImagesToTheSelectedLocalisation(String filePath) {
        if (filePath.contains("."))
            filePath = filePath.substring(0, filePath.lastIndexOf('.'));
        game.getGamePanel().savePanel(filePath + "Panel");
        game.getGamePanel().saveBoard(filePath + "Board");
    }

    private void handleCheckBox() {
        if (checkBox.isSelected()) {
            saveButton.setEnabled(false);
            isSaved = true;
        } else {
            saveButton.setEnabled(true);
            saveButton.setForeground(WHITE);
            saveButton.setText("Wybierz lokalizację");
            isSaved = false;
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

    public void prepareMessage(Results way) {
        if (way == Results.TIE)
            wayLabel.setText("Koniec gry! Nastąpił remis!");
        else if (way == Results.LEFT_TIME_IS_UP)
            wayLabel.setText(format("Koniec gry! %s zwyciężył!", leftNameLabel.getText()));
        else if (way == Results.LEFT_MAX_SCORE)
            wayLabel.setText(format("Koniec gry! %s osiągnął maksymalną ilość punktów!", leftNameLabel.getText()));
        else if (way == Results.LEFT_ARMAGEDDON)
            wayLabel.setText(format("Koniec gry! %s zestrzelił komórkę armagedon!", leftNameLabel.getText()));
        else if (way == Results.RIGHT_TIME_IS_UP)
            wayLabel.setText(format("Koniec gry! %s zwyciężył!", rightNameLabel.getText()));
        else if (way == Results.RIGHT_MAX_SCORE)
            wayLabel.setText(format("Koniec gry! %s osiągnął maksymalną ilość punktów!", rightNameLabel.getText()));
        else if (way == Results.RIGHT_ARMAGEDDON)
            wayLabel.setText(format("Koniec gry! %s zestrzelił komórkę armagedon!", rightNameLabel.getText()));
    }
}
