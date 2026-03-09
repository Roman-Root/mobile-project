import config.BaseTest;
import org.junit.jupiter.api.Test;
import pages.AlchemyPage;

public class AlchemyTest extends BaseTest {
    AlchemyPage alchemyPage = new AlchemyPage();

    @Test
    void testHintsAccumulation() {
        alchemyPage.startGame();
        alchemyPage.requestAdHint();

        alchemyPage.checkHintsCount("4");
    }
}