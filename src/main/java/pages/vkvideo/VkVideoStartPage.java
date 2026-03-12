package pages.vkvideo;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.appium.SelenideAppiumElement;
import io.appium.java_client.AppiumBy;
import java.time.Duration;

import static com.codeborne.selenide.appium.SelenideAppium.$;

public class VkVideoStartPage {

    private final SelenideAppiumElement firstVideo = $(AppiumBy.xpath(
            "//android.widget.ImageView[contains(@resource-id, 'preview') or " +
                    "contains(@resource-id, 'thumb') or contains(@resource-id, 'cover')]"
    ));

    public void waitForFeedToLoad() {
        firstVideo.shouldBe(Condition.visible, Duration.ofSeconds(40));
    }

    public void playRandomVideo() {
        waitForFeedToLoad();
        firstVideo.click();
        System.out.println("Video clicked, opening player...");
    }
}