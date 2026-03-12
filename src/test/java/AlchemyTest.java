package tests;

import config.BaseTest;
import org.junit.jupiter.api.Test;
import utils.AdHandler;

import static pages.Pages.*;

public class AlchemyTest extends BaseTest {

    @Test
    void testAlchemyAd() {

        openApp("alchemy");

        startPage.startGame();
        mainPage.openAddHintsMenu();
        hintPage.clickWatchAd();

        boolean detected = AdHandler.handleAd(60);

        System.out.println("Ad result: " + detected);
    }
}