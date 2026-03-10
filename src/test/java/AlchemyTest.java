import config.BaseTest;
import org.junit.jupiter.api.Test;
import pages.AlchemyPage;

public class AlchemyTest extends BaseTest {
    AlchemyPage alchemyPage = new AlchemyPage();

    @Test
    void testVideoPlaybackLogic() {
        openApp("alchemy");

        alchemyPage.startGame();

        alchemyPage.openAddHintsMenu();

        alchemyPage.watchAdForHint();

        alchemyPage.verifyHintsCount("4");
    }
}