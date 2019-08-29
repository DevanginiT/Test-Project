package cams;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Login {

	private static WebDriver driver;

	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	private void setUp(@Optional("chrome") String browser) {

//		CREATE DRIVER
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver = new ChromeDriver();
			break;

		case "firefox":
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
			driver = new FirefoxDriver();
			break;

		default:
			System.out.println("Do not know how to start " + browser + ", starting chrome instead");
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}

		// sleep for 2 seconds
		sleep(2000);

		// MAXIMIZE BROWSER WINDOW
		driver.manage().window().maximize();
	}

	public static void captureScreenshot() throws IOException {

		Date d = new Date();
		String fileName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// FileUtils.copyFile(screenshot, new File(".//screenshot//" + fileName));
		FileHandler.copy(screenshot,
				new File("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\screenshots\\" + fileName));

	}

	public static void captureEleScreenshot(WebElement ele) throws IOException {

		Date d = new Date();
		String fileName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		BufferedImage fullImg = ImageIO.read(screenshot);

		Point point = ele.getLocation();

		int eleWidth = ele.getSize().getWidth();
		int eleHeight = ele.getSize().getHeight();

		BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
		ImageIO.write(eleScreenshot, "jpg", screenshot);

		File screenshotLocation = new File(
				"C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\screenshots\\" + fileName);
		// FileUtils.copyFile(screenshot, screenshotLocation);
		FileHandler.copy(screenshot, screenshotLocation);

	}

	@Test(priority = 1)
	public void doLogin() throws IOException {

		System.out.println("Executing loginTest");

//		OPEN CAMS LOGIN PAGE 	
		String url = "https://app.assethub.com.au/";
		driver.get(url);
		System.out.println("Page is opened.");

//       SLEEP FOR 2 SECONDS
		sleep(2000);

		System.out.println("Starting incorrectUsernameTest");

//		ENTER USERNAME
		WebElement username = driver.findElement(By.id("txtUsername"));
		username.sendKeys("Devangini@training12");

//		ENTER PASSWORD
		WebElement password = driver.findElement(By.name("txtPassword"));
		password.sendKeys("d291353e");

//		CLICK LOGIN BUTTON
		WebElement logInButton = driver.findElement(By.id("cmdLogin"));
		logInButton.click();

		sleep(5000);

//		VALID AND INVALID USERNAME		  

		// SUCCESSFULL LOGIN MESSAGE ON VALID USER CREDENTIALS

		try {

			WebElement successMessage = driver.findElement(By.xpath(
					"/html//div[@id='headder']/table/tbody/tr/td[2]/table//font[.='CENTRAL ASSET MANAGEMENT SYSTEM']"));
			String expectedMessage = "CENTRAL ASSET MANAGEMENT SYSTEM";
			String actualMessage = successMessage.getText();
			Assert.assertEquals(actualMessage, expectedMessage, "Actual message is not the same as expected");
			Assert.assertTrue(actualMessage.contains(expectedMessage),
					"Actual message does not contain expected message.\nActual Message: " + actualMessage
							+ "\nExpected Message: " + expectedMessage);

			captureScreenshot();
			sleep(3000);

			driver.findElements(By.id("cmdLogOut")).get(0).click();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

		// VERIFICATION ON INVALID USER CREDENTIALS

		try {
			WebElement errorMessage = driver.findElement(By.xpath("/html//span[@id='lblMessage']"));
			String expectedErrorMessage = "Council Code is empty";
			String actualErrorMessage = errorMessage.getText();
			Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
					"Actual error message does not contain expected. \nActual: " + actualErrorMessage + "\nExpected: "
							+ expectedErrorMessage);

			sleep(3000);

			System.out.println("Capturing ScreenShot");
			captureScreenshot();

			// WebElement username1 = driver.findElement(By.id("txtUsername"));
			// username1.clear();
			// driver.navigate().refresh();
			// sleep(2000);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

	}

	@AfterMethod
	public void closeBrowser() {

		System.out.println("Closing the Browser");
		driver.quit();

	}

	private void sleep(long m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
