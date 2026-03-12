package test;

import config.BaseTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static pages.Pages.*;

public class VkVideoTest extends BaseTest {

    @Test
    void testVideoPlaybackLogic() {
        openApp("vkvideo");

        try {
            vkStartPage.waitForFeedToLoad();
            vkStartPage.playRandomVideo();

            vkPlayerPage.waitForPlayerToAppear();

            boolean playing = vkPlayerPage.isVideoPlaying();

            assertTrue(playing, "Video playback failed: timer is not moving");
            System.out.println("Positive: Video is playing correctly.");

        } catch (Throwable e) {
            System.err.println("Negative: Test failed! Reason: " + e.getMessage());
            throw e;
        }
    }
}