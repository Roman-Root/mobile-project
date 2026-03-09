package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.appium.SelenideAppiumElement;
import io.appium.java_client.AppiumBy;
import java.time.Duration;

import static com.codeborne.selenide.appium.SelenideAppium.$;

public class VkVideoPage {

    private final SelenideAppiumElement firstVideo = $(AppiumBy.xpath(
            "//android.widget.ImageView[@resource-id='com.vk.vkvideo:id/preview' or " +
                    "@resource-id='com.vk.vkvideo:id/thumb' or " +
                    "contains(@resource-id, 'cover') or contains(@resource-id, 'thumbnail')]"
    ));

    public void waitForFeedToLoad() {
        firstVideo.shouldBe(Condition.visible.because(
                "The video recommendations feed was not loaded within 40 seconds. " +
                        "Potential causes include: absence of internet connection, required user authorization, or an internal application error"
        ), Duration.ofSeconds(40));
    }

    public void playRandomVideo() {
        waitForFeedToLoad();

        if (!firstVideo.is(Condition.enabled)) {
            firstVideo.parent().shouldBe(Condition.visible).click();
        } else {
            firstVideo.click();
        }
    }

    public void waitForPlayerToAppear() {

        $(AppiumBy.id("com.vk.vkvideo:id/player_control"))
                .shouldBe(Condition.visible.because(
                        "The player did not open within 20 seconds after clicking on the video"
                ), Duration.ofSeconds(20));
    }

    public boolean isVideoPlaying() {
        return $(AppiumBy.xpath(
                "//*[contains(@resource-id, 'seek') or contains(@resource-id, 'progress') or contains(@resource-id, 'bar')]"
        ))
                .shouldBe(Condition.visible, Duration.ofSeconds(20))
                .isDisplayed();
    }

}