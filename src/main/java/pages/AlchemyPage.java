package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.appium.SelenideAppiumElement;
import io.appium.java_client.AppiumBy;
import java.time.Duration;

import static com.codeborne.selenide.appium.SelenideAppium.$;


public class AlchemyPage {

    private final SelenideAppiumElement playButton = $(AppiumBy.xpath("//*[@text='Play']"));

    private final SelenideAppiumElement addHintsPlus = $(AppiumBy.xpath("//android.view.View[android.widget.TextView]/android.view.View[2]"));

    private final SelenideAppiumElement watchAdBtn = $(AppiumBy.xpath("//android.view.View[android.widget.TextView[@text='For watching ads']]//android.widget.Button"));

    private final SelenideAppiumElement watchAdText = $(AppiumBy.xpath("//android.view.View[android.widget.TextView[@text='For watching ads']]//android.widget.TextView[@text='Watch' or @text='Loading']"));

    public void startGame() {
        playButton.shouldBe(Condition.visible, Duration.ofSeconds(20)).click();
    }

    public void openAddHintsMenu() {
        addHintsPlus.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void clickWatchAd() {
        watchAdText.shouldHave(Condition.text("Watch"), Duration.ofSeconds(60));

        watchAdBtn.shouldBe(Condition.enabled).click();

        System.out.println("Ad started successfully. Returning control to test.");
    }
}