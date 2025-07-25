package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String name) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String directory = "./screenshots";
        String fileName = name + "_" + timestamp + ".png";
        String filePath = Paths.get(directory, fileName).toString();

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(filePath);

            // Ensure directory exists
            File screenshotDir = new File(directory);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            FileHandler.copy(src, dest);
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            e.printStackTrace();
        }

        return filePath;
    }
}

