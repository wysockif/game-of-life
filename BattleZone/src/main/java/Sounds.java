import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JOptionPane;

public class Sounds {

    public static synchronized void playTankFiringSound() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Sounds.class.getResource("sounds/TankFiring.wav"));
                    clip.open(inputStream);
                    turnDown(clip);
                    clip.start();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Błąd z dźwiękiem wystrzału!", "Błąd!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }).start();
    }

    public static synchronized void playGamePointSound() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Sounds.class.getResource("sounds/GamingPoint.wav"));
                    clip.open(inputStream);
                    turnDown(clip);
                    clip.start();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Błąd z dźwiękiem zestrzelenia komórki!", "Błąd!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }).start();
    }


    public static synchronized void playGameOverSound() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Sounds.class.getResource("sounds/GameOver.wav"));
                    clip.open(inputStream);
                    turnDown(clip);
                    clip.start();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Błąd z dźwiękiem skończonej gry!", "Błąd!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }).start();
    }

    private static void turnDown(Clip clip) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(0.05f));
    }
}

