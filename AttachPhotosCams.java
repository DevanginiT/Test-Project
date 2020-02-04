package project_cams;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.imageio.ImageIO;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class AttachPhotosCams {

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

		FileHandler.copy(screenshot, screenshotLocation);

	}

	@Test(priority = 1)
	public void ImportBuilding() throws IOException, InterruptedException, ParseException {

		System.out.println("Executing Attach Photos Test");

//		OPEN CAMS LOGIN PAGE 	
		String url = "https://app.cams.assethub.com.au/#/login";
		driver.get(url);
		System.out.println("Page is opened.");

//       SLEEP FOR 2 SECONDS
		sleep(2000);

		System.out.println("Starting incorrectUsernameTest");

//		ENTER USERNAME
		WebElement username = driver.findElement(
				By.xpath("/html/body/app-login/div/main/div/div/div/div/div/div/div/form/div[1]/div[1]/input"));
		username.sendKeys("devangini.tandel@rmit.edu.au");
		sleep(2000);

//		ENTER PASSWORD
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys("Devangini");
		sleep(2000);

//		CLICK LOGIN BUTTON
		WebElement logInButton = driver.findElement(
				By.xpath("/html/body/app-login/div/main/div/div/div/div/div/div/div/form/div[3]/div[1]/button"));
		logInButton.click();
		sleep(6000);

		// CLICK ON DROPDOWN
		WebElement drpdown = driver.findElement(By.xpath("//a[@id='dropdownMenu']/i"));
		drpdown.click();
		sleep(2000);

		// SELECT VALUES FROM DROPDOWN

		WebElement drpdnRecord = driver.findElement(By.xpath(
				"//app-dashboard//ol[@class='breadcrumb']//div[@role='group']/li[@class='nav-item']//div[@class='dropdown-menu dropdown-menu-right show']/a[.=' VIC Training ']"));
		drpdnRecord.click();
		sleep(2000);


//     CLICK ON BUILDING ICON
		WebElement buldIcn = driver.findElement(By.xpath("//app-dashboard/header/ul[1]/li[@title='Buildings']//img[@class='icon']"));
		buldIcn.click();
		sleep(3000);

		
//	     CLICK ON ATTACH PHOTOS TAB
		
			WebElement AttachPhoto = driver.findElement(
					By.xpath("//app-dashboard//app-sidebar[@class='sidebar']/app-sidebar-nav-custom/ul[@class='nav']//span[.=' Attach Photos ']"));
			AttachPhoto.click();
			sleep(2000);

			// CLICK ON DROPDOWN
			
			WebElement drpdownCategory = driver.findElement(By.xpath("/html/body/app-dashboard/div[1]/main/div/app-attach-files/level-data-view/div/div/div[1]/div[4]/div/div/button/span"));
			drpdownCategory.click();
			sleep(2000);

			// SELECT VALUES FROM DROPDOWN

			WebElement SelectCategory = driver.findElement(By.xpath(
					"/html//div[@id='card']/div[@class='row rowSpace']//div[@class='dropdown-menu show']/a[1]"));
			SelectCategory.click();
			sleep(2000);
			
			
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,1250)");
			sleep(2000);
	
			
			// DOWNLOAD ATTACHMENT TEMPLATE
			
			WebElement AttachTemplate = driver.findElement(By.xpath(
					"/html/body/app-dashboard/div[1]/main/div/app-attach-files/div[1]/div[2]/div/div/button"));
			AttachTemplate.click();
			sleep(2000);
			
			// CLICK ON IMPORT ATTACHMENT
			
			WebElement ImportAttachment = driver.findElement(By.xpath(
					"/html/body/app-dashboard/div[1]/main/div/app-attach-files/div[2]/div[2]/div/div[1]/button"));
			ImportAttachment.click();
			sleep(2000);
			
			// IMPORT EXCEL PHOTO ATTACHMENT TEMPLATE
			
			Runtime.getRuntime().exec("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\ImportPhotoAttachment.exe");
			sleep(3000);
			
			// IMPORT PHOTOS TO BE ATTACHED
			
			WebElement ImportPhotos = driver.findElement(By.xpath(
					"/html/body/app-dashboard/div[1]/main/div/app-attach-files/div[3]/div[2]/div/div[1]/button"));
			ImportPhotos.click();
			sleep(2000);

			
			Runtime.getRuntime().exec("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\ImportPhotos.exe");
			sleep(3000);
			
			
			
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
