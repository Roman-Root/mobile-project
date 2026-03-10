package config;

import com.codeborne.selenide.WebDriverProvider;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class MyDriverProvider implements WebDriverProvider {

    private static final Properties config = loadConfig();

    private static Properties loadConfig() {
        Properties props = new Properties();
        String configFile = "config.properties";
        try (InputStream is = MyDriverProvider.class.getClassLoader().getResourceAsStream(configFile)) {
            if (is == null) {
                throw new RuntimeException("Configuration file not found: " + configFile);
            }
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration: " + configFile, e);
        }
        return props;
    }

    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {

        String configKey = System.getProperty("app.config", "vkvideo");

        String prefix = configKey + ".";

        String platformVersion = getRequiredProperty(prefix + "version");
        String deviceName = getRequiredProperty(prefix + "device");
        String appPath = getRequiredProperty(prefix + "path");
        String appPackage = getRequiredProperty(prefix + "package");
        String appActivity = getRequiredProperty(prefix + "activity");

        File appFile = new File(appPath);
        if (!appFile.exists()) {
            throw new RuntimeException("APK not found at path: " + appPath);
        }

        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android")
                .setPlatformVersion(platformVersion)
                .setDeviceName(deviceName)
                .setAutomationName("UiAutomator2")
                .setApp(appFile.getAbsolutePath())
                .setAppPackage(appPackage)
                .setAppActivity(appActivity)
                .setNoReset(false)
                .setFullReset(false)
                .setAutoGrantPermissions(true)
                .setAppWaitForLaunch(false)
                .setNewCommandTimeout(Duration.ofSeconds(180))
                .setAdbExecTimeout(Duration.ofSeconds(60));

        try {
            return new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL", e);
        }
    }

    private String getRequiredProperty(String key) {
        String value = config.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Required configuration parameter is missing: " + key);
        }
        return value;
    }
}