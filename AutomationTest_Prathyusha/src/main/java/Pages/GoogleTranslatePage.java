package Pages;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class GoogleTranslatePage {
    WebDriver driver;

    public GoogleTranslatePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//*[@class='akczyd']//button[@aria-label='More source languages']//div[@class='VfPpkd-Bz112c-RLmnJb'])[1]")
    public WebElement dropdownArrowSourceLanguage;

    @FindBy(xpath = "//*[@id=\"yDmH0d\"]/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[1]/div[1]/c-wiz/div[1]/c-wiz/div[5]/button/div[3]")
    public WebElement dropdownArrowTranslateLanguage;

    @FindBy(xpath = "//*[@aria-label='Source text']")
    public WebElement sourceTextField;

    @FindBy(xpath = "//*[@class='Q4iAWc']")
    public WebElement translateTextField;

    @FindBy(xpath = "//*[@id=\"ow25\"]/div/span/button/div[3]")
    public WebElement swipeBtn;

    @FindBy(xpath = "//*[@id=\"itamenu\"]/span/div/a[1]/span")
    public WebElement selectInputBtn;

    @FindBy(xpath = "//*[@id=\"K72\"]")
    public WebElement HChar;

    @FindBy(xpath = "//*[@id=\"K73\"]")
    public WebElement IChar;

    public void verifyGoogleTranslate() throws IOException, InterruptedException {

        File file = new File("AutomationTest.xls");

        FileInputStream inputStream = new FileInputStream(file);

        HSSFWorkbook wb = new HSSFWorkbook(inputStream);

        HSSFSheet sheet = wb.getSheet("Sheet1");

        HSSFRow row2 = sheet.getRow(1);

        HSSFCell cell1 = row2.getCell(0);
        HSSFCell cell2 = row2.getCell(1);
        HSSFCell cell3 = row2.getCell(2);
        HSSFCell cell4 = row2.getCell(3);

        String sourceLanguage = cell1.getStringCellValue();
        String translationLanguage = cell2.getStringCellValue();
        String initialText = cell3.getStringCellValue();
        String expectedResult = cell4.getStringCellValue();

        driver.get("https://translate.google.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //Select the source language and verify the translation
        dropdownArrowSourceLanguage.click();
        List<WebElement> sourceLanguages = driver.findElements(By.xpath("//*[@class='Llmcnf']"));
        for (WebElement language : sourceLanguages) {
            if (language.getText().equals(sourceLanguage)) {
                language.click();
                break;
            }
        }

        Thread.sleep(2000);
        dropdownArrowTranslateLanguage.click();
        List<WebElement> translateLanguages = driver.findElements(By.xpath("//*[@class='Llmcnf']"));
        for (WebElement language : translateLanguages) {
            if (language.getText().equals(translationLanguage)) {
                language.click();
                break;
            }
        }

        sourceTextField.sendKeys(initialText);
        wait.until(ExpectedConditions.visibilityOf(translateTextField));
        Assert.assertTrue(expectedResult.contains(translateTextField.getText()));

        //Click on Swipe button and verify the translated language
        Actions action = new Actions(driver);
        action.moveToElement(swipeBtn).build().perform();
        wait.until(ExpectedConditions.elementToBeClickable(swipeBtn));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", swipeBtn);
        Assert.assertTrue(initialText.contains(translateTextField.getText()));

        //Clear the source field
        sourceTextField.clear();

        //Click on Select input button
        //wait.until(ExpectedConditions.visibilityOf(selectInputBtn));
        selectInputBtn.click();

        //Click on H and I character
        wait.until(ExpectedConditions.visibilityOf(HChar));
        HChar.click();
        IChar.click();
    }
}

