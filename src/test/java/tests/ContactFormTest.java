package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExcelUtils;
import utils.ScreenshotUtil;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


@Epic("CYLNDR Contact Form")
@Feature("Form Submission")
public class ContactFormTest extends BaseTest {

    @DataProvider(name = "formData")
    public Object[][] formData() throws Exception {
        return ExcelUtils.getTestData("./testdata/ContactFormData.xlsx", "Sheet1");
    }

    @Test(dataProvider = "formData", description = "Submit contact form with different data")
    @Severity(SeverityLevel.CRITICAL)
    public void testContactForm(String fullName, String email, String phone, String message) throws InterruptedException {
        driver.get("https://cylndr.experiencecommerce.com/contact-us");

        driver.findElement(By.name("name")).sendKeys(fullName);
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("phone")).sendKeys(phone);
        driver.findElement(By.name("message")).sendKeys(message);
        driver.findElement(By.xpath("//button[normalize-space()='SEND']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successMsg = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("fluentform_3_success"))
            
        );

        System.out.println("Please solve CAPTCHA manually......");
        Thread.sleep(20000);

        String expectedMsg = "Thank you for contacting us. We will be in touch with you shortly!";
        System.out.println(expectedMsg);
        Assert.assertTrue(successMsg.getText().contains(expectedMsg), "Form submission failed: Success message not found");
        
        System.out.println("Test Case Passed");
        System.out.println("Form has been submited successfully......");
    }

    @AfterMethod
    public void onTestFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
        	ScreenshotUtil.captureScreenshot(driver, result.getName());

        }
    }
}
