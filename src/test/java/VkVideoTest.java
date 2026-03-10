package test;

import config.BaseTest;
import org.junit.jupiter.api.Test;
import pages.VkVideoPage;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VkVideoTest extends BaseTest {

    @Test
    void testVideoPlaybackLogic() {
        openApp("vkvideo");
        VkVideoPage videoPage = new VkVideoPage();

        try {
            videoPage.waitForFeedToLoad();

            videoPage.playRandomVideo();
            videoPage.waitForPlayerToAppear();

            boolean playing = videoPage.isVideoPlaying();

            assertTrue(playing, "Video playback failed: timer is not moving");
            System.out.println("Positive: Video is playing.");

        } catch (Throwable e) {
            System.err.println("Negative: Video playback failed! Reason : " + e.getMessage());
            throw e;
        }
    }
}