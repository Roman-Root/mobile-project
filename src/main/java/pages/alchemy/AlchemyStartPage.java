package pages.alchemy;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.appium.SelenideAppiumElement;
import io.appium.java_client.AppiumBy;
import java.time.Duration;
import static com.codeborne.selenide.appium.SelenideAppium.$;

public class AlchemyStartPage {
    private final SelenideAppiumElement playButton = $(AppiumBy.xpath("//*[@text='Play']"));

    public void startGame() {
        playButton.shouldBe(Condition.visible, Duration.ofSeconds(20)).click();
    }
}