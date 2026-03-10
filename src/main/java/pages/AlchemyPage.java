package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.appium.SelenideAppiumElement;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

import java.time.Duration;
import java.util.function.Supplier;

import static com.codeborne.selenide.appium.SelenideAppium.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class AlchemyPage {

    private final SelenideAppiumElement playButton =
            $(AppiumBy.xpath("//*[@text='Play']"));

    private final SelenideAppiumElement addHintsPlus =
            $(AppiumBy.xpath("//android.view.View[android.widget.TextView]/android.view.View[2]"));

    private final SelenideAppiumElement watchAdBtn =
            $(AppiumBy.xpath("//android.view.View[android.widget.TextView[@text='For watching ads']]//android.widget.Button"));

    private final SelenideAppiumElement watchAdText =
            $(AppiumBy.xpath("//android.view.View[android.widget.TextView[@text='For watching ads']]//android.widget.TextView[@text='Watch' or @text='Loading']"));

    private final SelenideAppiumElement closeAdBtn =
            $(AppiumBy.xpath(
                    "//*[@resource-id='m-playable-close'] | " +
                            "//*[@resource-id='com.ilyin.alchemy:id/blgs_ad_btn_close'] | " +
                            "//*[@resource-id='ad_close_button'] | " +
                            "//*[contains(@content-desc,'close') or contains(@content-desc,'Close')]"
            ));

    private final SelenideAppiumElement confirmExitBtn =
            $(AppiumBy.xpath("//android.widget.Button[@text='Close' or @text='Exit' or @text='Yes']"));

    private final SelenideAppiumElement playableButtons =
            $(AppiumBy.xpath(
                    "//*[@text='Play'] | " +
                            "//*[@text='Skip'] | " +
                            "//*[@text='>>']"
            ));

    private final SelenideAppiumElement hintCounter =
            $(AppiumBy.xpath("//android.view.View[android.view.View]/android.widget.TextView[not(@text='Your hints')]"));

    public void startGame() {
        playButton.shouldBe(Condition.visible, Duration.ofSeconds(20)).click();
    }

    public void openAddHintsMenu() {
        addHintsPlus.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void watchAdForHint() {
        watchAdText.shouldHave(Condition.text("Watch"), Duration.ofSeconds(60));
        watchAdBtn.shouldBe(Condition.enabled).click();
        closeAdAfterVideo();
    }

    public void verifyHintsCount(String expectedCount) {
        hintCounter.shouldHave(Condition.text(expectedCount), Duration.ofSeconds(30));
    }

    private void closeAdAfterVideo() {
        AndroidDriver driver = (AndroidDriver) getWebDriver();

        waitForCondition(this::isAdFinished, Duration.ofSeconds(120), 3000);

        waitForCondition(() -> {
            if (driver.getCurrentPackage().equals("com.ilyin.alchemy")) {
                System.out.println("Returned to game. Ad closed.");
                return true;
            }

            clickIfVisible(closeAdBtn);
            clickIfVisible(confirmExitBtn);
            clickIfVisible(playableButtons);

            driver.navigate().back();
            tapTopRightCorner();

            return false;
        }, Duration.ofSeconds(60), 3000);
    }

    private boolean isAdFinished() {
        return isVisible(closeAdBtn) || isVisible(confirmExitBtn) || isVisible(playableButtons);
    }

    private boolean isVisible(SelenideAppiumElement element) {
        try {
            return element.is(Condition.visible);
        } catch (Exception ignored) {
            return false;
        }
    }

    private void clickIfVisible(SelenideAppiumElement element) {
        if (isVisible(element)) {
            element.click();
        }
    }

    private void waitForCondition(Supplier<Boolean> condition, Duration timeout, long pollMillis) {
        long start = System.currentTimeMillis();
        long end = start + timeout.toMillis();

        while (System.currentTimeMillis() < end) {
            if (condition.get()) {
                return;
            }
            try {
                Thread.sleep(pollMillis);
            } catch (InterruptedException ignored) {}
        }
    }


    private void tapTopRightCorner() {
        AndroidDriver driver = (AndroidDriver) getWebDriver();

        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        int x = (int) (width * 0.95);
        int y = (int) (height * 0.07);

        try {
            driver.executeScript("mobile: clickGesture",
                    ImmutableMap.of("x", x, "y", y));
            System.out.println("Tapped top-right corner");
        } catch (Exception ignored) {}
    }
}