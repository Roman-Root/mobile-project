package utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.appium.SelenideAppiumElement;
import io.appium.java_client.AppiumBy;
import java.time.Duration;

import static com.codeborne.selenide.appium.SelenideAppium.$;

public class AdHandler {

    private static final Duration DEFAULT_AD_WAIT = Duration.ofSeconds(45);
    private static final Duration OVERLAY_DISAPPEAR_WAIT = Duration.ofSeconds(10);
    private static final Duration GAME_RESUME_WAIT = Duration.ofSeconds(15);

    private static final String SKIP_CLOSE_XPATH =
            "//*[" +
                    "contains(@text, 'Пропустить') or contains(@text, 'Skip') or " +
                    "contains(@text, 'Закрыть') or contains(@text, 'Close') or " +
                    "contains(@text, 'Dismiss') or contains(@resource-id, 'close') or " +
                    "contains(@resource-id, 'skip') or contains(@resource-id, 'dismiss') or " +
                    "contains(@resource-id, 'btn_close') or contains(@content-desc, 'Close') or " +
                    "contains(@content-desc, 'Skip')" +
                    "]" +
                    "[@clickable='true' or @enabled='true']";

    private static final String GAME_ROOT_ID = "android:id/content";

    public static boolean handleAd(int waitSeconds) {
        Duration timeout = Duration.ofSeconds(waitSeconds > 0 ? waitSeconds : DEFAULT_AD_WAIT.getSeconds());
        System.out.println("AdHandler: Waiting close button (" + timeout.toSeconds() + " sec)...");

        boolean adWasDetected = false;
        SelenideAppiumElement skipButton = $(AppiumBy.xpath(SKIP_CLOSE_XPATH));

        try {
            skipButton.shouldBe(Condition.visible, timeout);

            System.out.println("AdHandler: Close button found and click");
            skipButton.click();
            adWasDetected = true;
        } catch (Throwable e) {
            System.out.println("AdHandler: Close button not found");
        }

        System.out.println("AdHandler: Checking overlay");
        $(AppiumBy.xpath("//*[contains(@resource-id, 'ad') or contains(@text, 'Ad')]"))
                .shouldNotBe(Condition.visible, OVERLAY_DISAPPEAR_WAIT);

        System.out.println("AdHandler: Back to the game");
        $(AppiumBy.id(GAME_ROOT_ID)).shouldBe(Condition.visible, GAME_RESUME_WAIT);

        return adWasDetected;
    }

    public static boolean handleAd() {
        return handleAd(45);
    }
}