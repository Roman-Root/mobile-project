package pages.vkvideo;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.appium.SelenideAppiumElement;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import java.time.Duration;

import static com.codeborne.selenide.appium.SelenideAppium.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class VkVideoPlayerPage {

    private final SelenideAppiumElement currentTime = $(AppiumBy.id("com.vk.vkvideo:id/current_progress"));
    private final SelenideAppiumElement videoViewContainer = $(AppiumBy.id("com.vk.vkvideo:id/videoViewContainer"));

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
            waitForPlayerToAppear();

            tapVideoCenter();
            currentTime.shouldBe(Condition.visible, Duration.ofSeconds(15));
            String timeStart = currentTime.getText().split(" ")[0];

            Selenide.sleep(10000);

            tapVideoCenter();
            String timeEnd = currentTime.getText().split(" ")[0];

            boolean isMoving = !timeStart.equals(timeEnd);
            System.out.println("DEBUG: Video progress: [" + timeStart + "] -> [" + timeEnd + "]");

            return isMoving;

        } catch (Throwable e) {
            System.err.println("Playback check failed: " + e.getMessage());
            return false;
        }
    }
}