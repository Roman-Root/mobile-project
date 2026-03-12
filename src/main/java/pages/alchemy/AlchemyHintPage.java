package pages.alchemy;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.appium.SelenideAppiumElement;
import io.appium.java_client.AppiumBy;
import java.time.Duration;
import static com.codeborne.selenide.appium.SelenideAppium.$;

public class AlchemyHintPage {
    private final SelenideAppiumElement watchAdText = $(AppiumBy.xpath("//android.view.View[android.widget.TextView[@text='For watching ads']]//android.widget.TextView[@text='Watch' or @text='Loading']"));
    private final SelenideAppiumElement watchAdBtn = $(AppiumBy.xpath("//android.view.View[android.widget.TextView[@text='For watching ads']]//android.widget.Button"));

    public void clickWatchAd() {
        watchAdText.shouldHave(Condition.text("Watch"), Duration.ofSeconds(60));
        watchAdBtn.shouldBe(Condition.enabled).click();

        System.out.println("Ad started successfully. Returning control to test.");
    }
}