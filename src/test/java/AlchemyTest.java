package tests; // проверь свой путь к пакету

import com.codeborne.selenide.Condition;
import config.BaseTest;
import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Test;
import pages.AlchemyPage;
import utils.AdHandler;

import java.time.Duration;

import static com.codeborne.selenide.appium.SelenideAppium.$;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlchemyTest extends BaseTest {
    AlchemyPage alchemyPage = new AlchemyPage();

    @Test
    void AlchemyTest() {
        openApp("alchemy");
        System.out.println("Open");

        alchemyPage.startGame();
        System.out.println("Start");

        alchemyPage.openAddHintsMenu();
        System.out.println("Menu");

        alchemyPage.clickWatchAd();
        System.out.println("Watch ad");

        System.out.println("AdHandler");
        AdHandler.handleAd(45);

        // Ждём именно экран меню подсказок с текстом "Your hints"
        System.out.println("Ожидание экрана меню подсказок ('Your hints')...");
        $(AppiumBy.xpath("//android.widget.TextView[contains(@text, 'Your hints')]"))
                .shouldBe(
                        Condition.visible.because(" Your hints is not open"),
                        Duration.ofSeconds(30)  // 30 сек — должно хватить
                );

        System.out.println("Final — Its all good");
    }
}