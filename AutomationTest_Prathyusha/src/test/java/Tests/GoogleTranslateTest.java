package Tests;

import Pages.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class GoogleTranslateTest {
    Common common = new Common();
    WebDriver driver = common.setup();
    GoogleTranslatePage googleTranslatePage = new GoogleTranslatePage(driver);

    @Test
    public void googleTranslateValidation() throws IOException, InterruptedException {
        googleTranslatePage.verifyGoogleTranslate();
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
