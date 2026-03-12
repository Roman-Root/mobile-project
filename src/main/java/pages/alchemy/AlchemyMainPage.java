package pages.alchemy;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.appium.SelenideAppiumElement;
import io.appium.java_client.AppiumBy;
import java.time.Duration;
import static com.codeborne.selenide.appium.SelenideAppium.$;

public class AlchemyMainPage {
    private final SelenideAppiumElement addHintsPlus = $(AppiumBy.xpath("//android.view.View[android.widget.TextView]/android.view.View[2]"));

    public void openAddHintsMenu() {
        addHintsPlus.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }
}