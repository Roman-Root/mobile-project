package config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.appium.SelenideAppium;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    static {
        Configuration.browserSize = null;
//        Configuration.screensaver = null;
        Configuration.browser = MyDriverProvider.class.getName();
    }

    @BeforeEach
    void setUp() {
        // не забыть добавить логирование
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }

    protected void openApp(String configName) {
        System.setProperty("app.config", configName);
        Selenide.open();
    }
}