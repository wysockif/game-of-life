
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class PlayerTest {

    @Test
    public void takeAShot_listSizeLessThanMaxNumberOfShots_increasedBulletsSize() {
        // given
        Player player = new LeftPlayer(4, "/img/leftTanks.png", "/img/leftBullet.png", new KeysListener());

        // when
        player.takeAShot();
        int size = player.getBullets().size();

        // then
        int expected = 1;
        assertThat(size).isEqualTo(expected);
    }

    @Test
    public void takeAShot_listSizeEqualsToMaxNumberOfShots_notIncreasedBulletsSize() {
        // given
        int n = 4;
        Player player = new LeftPlayer(n, "/img/leftTanks.png", "/img/leftBullet.png", new KeysListener());
        for(int i = 0; i < n; i++)
            player.takeAShot();

        // when
        player.takeAShot();
        int size = player.getBullets().size();

        // then
        int expected = 4;
        assertThat(size).isEqualTo(expected);
    }


    @Test
    public void removeUnwantedBullets_bulletOutOfBoard_removedBullet() {
        // given
        Player player = new LeftPlayer(3, "/img/leftTanks.png", "/img/leftBullet.png", new KeysListener());
        player.takeAShot();
        Bullet b = player.getBullets().get(0);
        b.x = Game.boardX - 50;
        b.y = 30;

        // when
        player.removeUnwantedBullets();
        int size = player.getBullets().size();

        // then
        int expected = 0;
        assertThat(size).isEqualTo(expected);
    }

    @Test
    public void removeUnwantedBullets_bulletOnTheBoard_didNotRemoveBullet() {
        // given
        Player player = new LeftPlayer(3, "/img/leftTanks.png", "/img/leftBullet.png", new KeysListener());
        player.takeAShot();
        Bullet b = player.getBullets().get(0);
        b.x = Game.boardWidth /2;
        b.y = Game.boardHeight /2;

        // when
        player.removeUnwantedBullets();
        int size = player.getBullets().size();

        // then
        int expected = 1;
        assertThat(size).isEqualTo(expected);
    }

    @Test
    public void removeUnwantedBullets_emptyBulletsList_didNotRemoveBullet() {
        // given
        Player player = new LeftPlayer(3, "/img/leftTanks.png", "/img/leftBullet.png", new KeysListener());

        // when
        player.removeUnwantedBullets();
        int size = player.getBullets().size();

        // then
        int expected = 0;
        assertThat(size).isEqualTo(expected);
    }

    @Test
    public void speedUpBullets_speedLessThanLimit_speededUpBullet() {
        // given
        Player player = new LeftPlayer(3, "/img/leftTanks.png", "/img/leftBullet.png", new KeysListener());

        // when
        player.speedUpBullets(15);
        player.takeAShot();
        Bullet b = player.getBullets().get(0);
        double speedMultiplier = b.getSpeedMultiplier();

        // then
        double expected = 1.15;
        assertThat(speedMultiplier).isEqualTo(expected);
    }

    @Test
    public void speedUpBullets_speedGreaterThanLimit_notSpeededUpBullet() {
        // given
        Player player = new LeftPlayer(3, "/img/leftTanks.png", "/img/leftBullet.png", new KeysListener());

        // when
        for(int i = 0; i < 5; i++)
            player.speedUpBullets(60);
        player.takeAShot();
        Bullet b = player.getBullets().get(0);
        double speedMultiplier = b.getSpeedMultiplier();

        // then
        double expected = 2.56;
        assertThat(speedMultiplier).isEqualTo(expected);
    }

}