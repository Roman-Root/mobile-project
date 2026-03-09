package config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.appium.SelenideAppium;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    @BeforeEach
    void setUp() {
        // Указываем Selenide использовать наш провайдер вместо стандартного
        Configuration.browser = MyDriverProvider.class.getName();
        SelenideAppium.launchApp();
    }

    @AfterEach
    void tearDown() {
        SelenideAppium.terminateApp("ru.vk.video");
    }
}