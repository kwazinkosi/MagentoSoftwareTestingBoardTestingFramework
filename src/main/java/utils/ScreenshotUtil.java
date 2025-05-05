package utils;

import org.openqa.selenium.WebDriver;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/*
 * ScreenshotUtil is a utility class for capturing screenshots in Selenium WebDriver tests.
 * It provides methods to capture screenshots and save them to a specified location.
 */
public class ScreenshotUtil {
	
	/**
	 * Captures a screenshot of the current state of the WebDriver and saves it to a file. 
	 * @param driver The WebDriver instance used for the test.
	 * @param testName The name of the test, used to create a unique filename for the screenshot.
	 */
    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String destPath = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
            FileUtils.copyFile(scrFile, new File(destPath));
            return destPath;
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Captures a screenshot of the current state of the WebDriver and returns it as a Base64 encoded string.
     * This can be useful for embedding the screenshot in reports or sending it over the network.
     * @param driver The WebDriver instance used for the test.
     */
	public static String captureScreenshotAsBase64(WebDriver driver) {
		try {
			return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		} catch (Exception e) {
			System.err.println("Failed to capture screenshot: " + e.getMessage());
			return null;
		}
	}
}