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
                    "contains(@text, 'Пропустить рекламу') or contains(@text, 'Закрыть') or " +
                    "contains(@text, 'Close') or contains(@text, 'Скрыть') or " +
                    "contains(@text, 'Dismiss') or contains(@text, 'Пропустить через') or " +
                    "contains(@resource-id, 'close') or contains(@resource-id, 'skip') or " +
                    "contains(@resource-id, 'dismiss') or contains(@resource-id, 'ad_close') or " +
                    "contains(@resource-id, 'btn_close') or contains(@resource-id, 'close_button') or " +
                    "contains(@content-desc, 'Закрыть') or contains(@content-desc, 'Close') or " +
                    "contains(@content-desc, 'Пропустить') or contains(@content-desc, 'Skip')" +
                    "] " +
                    "[@clickable='true' or @enabled='true']]";

    private static final String GAME_ROOT_ID = "android:id/content";


    public static boolean handleAd(int waitSeconds) {
        Duration timeout = Duration.ofSeconds(waitSeconds > 0 ? waitSeconds : DEFAULT_AD_WAIT.getSeconds());

        System.out.println("AdHandler: Waiting load ad (" + timeout.getSeconds() + " sec)...");

        boolean adWasDetected = false;

        SelenideAppiumElement skipButton = $(AppiumBy.xpath(SKIP_CLOSE_XPATH));

        try {
            skipButton.shouldBe(
                    Condition.visible.because("Skip/clouse button ad is not open"),
                    timeout
            );

            if (skipButton.is(Condition.enabled) && skipButton.isDisplayed()) {
                System.out.println("AdHandler: Button found");
                skipButton.click();
                adWasDetected = true;
                System.out.println("AdHandler: Ad skip/clouse");
            }
        } catch (Throwable e) {
            System.out.println("AdHandler: Skip clpouse not found  " + timeout.getSeconds() + " sec");
            System.out.println("AdHandler: Waiting end ad");
        }

        System.out.println("AdHandler: Owerlay is hidden");
        $(AppiumBy.xpath(
                "//*[contains(@resource-id, 'ad') or contains(@resource-id, 'overlay') or " +
                        "contains(@text, 'Реклама') or contains(@text, 'Ad') or contains(@text, 'Sponsored')]"
        )).shouldNotBe(
                Condition.visible.because("Ad overlay is not hidden"),
                OVERLAY_DISAPPEAR_WAIT
        );

        // 3. Ждём возврата главного экрана игры (самый надёжный локатор)
        System.out.println("AdHandler: Go to game back");
        $(AppiumBy.id(GAME_ROOT_ID))
                .shouldBe(
                        Condition.visible.because("Game is opened"),
                        GAME_RESUME_WAIT
                );

        System.out.println("AdHandler: Ad stop");
        return adWasDetected;
    }


    public static boolean handleAd() {
        return handleAd(45);
    }
}