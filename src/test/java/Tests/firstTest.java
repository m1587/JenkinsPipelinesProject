package Tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import mobile.pageObjects.chrom.HomePage;
import mobile.pageObjects.chrom.Timer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class firstTest {
    AndroidDriver driver;

    @BeforeEach
    public void beforeEach() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("emulator-5554");
        options.setAutomationName("UiAutomator2");
        options.setPlatformName("Android");
        options.setPlatformVersion("15");
        options.setAppPackage("com.google.android.deskclock");
        options.setAppActivity("com.android.deskclock.DeskClock");
        options.setNoReset(false);
        options.setFullReset(false);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
    }

    @Test
    public void test1()  {

        HomePage homePage = new HomePage(driver);
        Assertions.assertTrue(homePage.IsExist());
        homePage.clickTimer();
        Timer timer = new Timer(driver);
        String text="00h 00m 60s";
        Assertions.assertTrue( timer.clickSeconds(text));
        timer.Play();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        timer.PauseTimer();
        Assertions.assertTrue(timer.GetSeconds()<=30,"true");
    }

    @AfterEach
    public void afterEach(){
        driver.quit();
    }

}
