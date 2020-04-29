import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import org.junit.Test;

public class BulletTest {
    @Test
    public void updateBullet_callingMethod_correctlyUpdatedBulletsPos() {
        // given
        BufferedImage bulletImage = null;
        try {
            bulletImage = ImageIO.read(new File("src/main/resources/img/leftBullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bullet b = new Bullet(bulletImage, 200, 200, 350, 1, 1, 1.5);

        // when
        b.updateBullet();
        int x = b.x;
        int y = b.y;

        // then
        int expectedX = 202;
        int expectedY = 197;
        assertThat(x).isEqualTo(expectedX);
        assertThat(y).isEqualTo(expectedY);
    }
}