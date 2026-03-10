package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.appium.SelenideAppiumElement;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import java.time.Duration;
import static com.codeborne.selenide.appium.SelenideAppium.$;

public class VkVideoPage {

    private final SelenideAppiumElement firstVideo = $(AppiumBy.xpath(
            "//android.widget.ImageView[contains(@resource-id, 'preview') or " +
                    "contains(@resource-id, 'thumb') or contains(@resource-id, 'cover')]"
    ));

    private final SelenideAppiumElement currentTime = $(AppiumBy.id("com.vk.vkvideo:id/current_progress"));

    private final SelenideAppiumElement videoViewContainer = $(AppiumBy.id("com.vk.vkvideo:id/videoViewContainer"));

    public void waitForFeedToLoad() {
        firstVideo.shouldBe(Condition.visible, Duration.ofSeconds(40));
    }

    public void playRandomVideo() {
        waitForFeedToLoad();
        firstVideo.click();
    }

    public void waitForPlayerToAppear() {
        videoViewContainer.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    private void tapVideoCenter() {
        Selenide.executeJavaScript("mobile: clickGesture", ImmutableMap.of(
                "x", 540,
                "y", 400
        ));
    }

    public boolean isVideoPlaying() {
        try {
            tapVideoCenter();

            currentTime.shouldBe(Condition.visible, Duration.ofSeconds(15));
            String timeStart = currentTime.getText().split(" ")[0];

            Selenide.sleep(10000);

            tapVideoCenter();

            String timeEnd = currentTime.getText().split(" ")[0];

            boolean isMoving = !timeStart.equals(timeEnd);
            System.out.println("DEBUG: Checking video: [" + timeStart + "] -> [" + timeEnd + "]");

            return isMoving;

        } catch (Throwable e) {
            System.err.println("Video playback check failed: " + e.getMessage());
            return false;
        }
    }
}