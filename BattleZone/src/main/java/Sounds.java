import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JOptionPane;

import static javax.sound.sampled.FloatControl.Type.MASTER_GAIN;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class Sounds {

    public static synchronized void playTankFiringSound() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Sounds.class.getResource("sounds/TankFiring.wav"));
                    clip.open(inputStream);
                    turnDown(clip, 0.05f);
                    clip.start();
                } catch (Exception e) {
                    playErrorSound();
                    JOptionPane.showMessageDialog(null,
                            "Wystąpił błąd z obsługą dźwięku wystrzału!", "Błąd!", ERROR_MESSAGE);
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
                    turnDown(clip, 0.08f);
                    clip.start();
                } catch (Exception e) {
                    playErrorSound();
                    JOptionPane.showMessageDialog(null,
                            "Wystąpił błąd z obsługą dźwięku zestrzelenia komórki!", "Błąd!", ERROR_MESSAGE);
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
                    turnDown(clip, 0.1f);
                    clip.start();
                } catch (Exception e) {
                    playErrorSound();
                    JOptionPane.showMessageDialog(null,
                            "Wystąpił błąd z obsługą dźwięku zakończenia gry!", "Błąd!", ERROR_MESSAGE);
                }
            }
        }).start();
    }

    public static synchronized void playErrorSound() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Sounds.class.getResource("sounds/ErrorSound.wav"));
                    clip.open(inputStream);
                    turnDown(clip, 0.05f);
                    clip.start();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Wystąpił błąd z obsługą dźwięku wystąpienia błędu!", "Błąd!", ERROR_MESSAGE);
                }
            }
        }).start();
    }

    public static synchronized void playClockSound() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Sounds.class.getResource("sounds/Clock.wav"));
                    clip.open(inputStream);
                    turnDown(clip, 0.1f);
                    clip.start();
                } catch (Exception e) {
                    playErrorSound();
                    JOptionPane.showMessageDialog(null,
                            "Wystąpił błąd z obsługą dźwięku zegara!", "Błąd!", ERROR_MESSAGE);
                }
            }
        }).start();
    }


    public static synchronized void playExplosion() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Sounds.class.getResource("sounds/Explosion.wav"));
                    clip.open(inputStream);
                    turnDown(clip, 0.1f);
                    clip.start();
                } catch (Exception e) {
                    playErrorSound();
                    JOptionPane.showMessageDialog(null,
                            "Wystąpił błąd z obsługą dźwięku wybuchu!", "Błąd!", ERROR_MESSAGE);
                }
            }
        }).start();
    }

    private static void turnDown(Clip clip, float value) {
        FloatControl gainControl = (FloatControl) clip.getControl(MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(value));
    }
}

