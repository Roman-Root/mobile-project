import config.BaseTest;
import org.junit.jupiter.api.Test;
import pages.VkVideoPage;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VkVideoTest extends BaseTest {

    private final VkVideoPage videoPage = new VkVideoPage();

    @Test
    void testVideoPlaybackLogic() {

        videoPage.waitForFeedToLoad();
        videoPage.playRandomVideo();
        videoPage.waitForPlayerToAppear();

        boolean isPlaying = videoPage.isVideoPlaying();

        assertTrue(isPlaying,
                "Video playback failed to start. " +
                        "Possible causes: prolonged loading, default pause state, " +
                        "network issues, invalid seek-zone locator, or application error."
        );

        System.out.println("Test passed: video playback confirmed.");
    }
}