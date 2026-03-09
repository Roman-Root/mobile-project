package config;

import com.codeborne.selenide.WebDriverProvider;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import javax.annotation.Nonnull;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class MyDriverProvider implements WebDriverProvider {
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        String appPath = System.getProperty("user.dir") + File.separator + "apps" + File.separator + "vk_video.apk";
        File appFile = new File(appPath);

        if (!appFile.exists()) {
            throw new RuntimeException("APK не найден: " + appPath);
        }
        System.out.println("DEBUG: APK: " + appFile.getAbsolutePath());

        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android")
                .setPlatformVersion("11")
                .setDeviceName("emulator-5554")
                .setAutomationName("UiAutomator2")
                .setApp(appFile.getAbsolutePath())
                .setAppPackage("com.vk.vkvideo")
                .setAppActivity("com.vk.video.screens.main.MainActivity")
                .setNoReset(false)
                .setFullReset(true)
                .setAutoGrantPermissions(true)
                .setAppWaitForLaunch(false)
                .setNewCommandTimeout(Duration.ofSeconds(180))
                .setAdbExecTimeout(Duration.ofSeconds(60));

        try {
            System.out.println("DEBUG: start sessions...");
            return new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error URL Appium", e);
        }
    }
}