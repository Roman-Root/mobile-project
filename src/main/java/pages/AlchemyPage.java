package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.appium.SelenideAppiumElement;
import org.openqa.selenium.By;
import static com.codeborne.selenide.appium.SelenideAppium.$;

public class AlchemyPage {
    private final SelenideAppiumElement playButton = $(By.xpath("//*[@text='Играть']"));
    private final SelenideAppiumElement addHintsBtn = $(By.id("com.ilyin.alchemy:id/add_hints_button"));
    private final SelenideAppiumElement watchAdBtn = $(By.id("com.ilyin.alchemy:id/watch_ad_button"));
    private final SelenideAppiumElement hintCounter = $(By.id("com.ilyin.alchemy:id/hint_count_text"));

    public void startGame() {
        playButton.click();
    }

    public void requestAdHint() {
        addHintsBtn.click();
        watchAdBtn.click();
    }

    public void checkHintsCount(String expectedCount) {
        hintCounter.shouldHave(Condition.text(expectedCount));
    }
}