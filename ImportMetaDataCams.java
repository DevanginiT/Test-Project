package project_cams;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.imageio.ImageIO;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
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

public class ImportMetaDataCams {

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
	public void doLogin() throws IOException, InterruptedException, ParseException, AWTException {

		System.out.println("Executing loginTest");

//		OPEN CAMS LOGIN PAGE 	
		String url = "https://stage.cams.assethub.com.au/#/login";
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
		
	
//   CLICK ON DROPDOWN		
		WebElement drpdown = driver.findElement(By.xpath("//a[@id='dropdownMenu']/i"));
		drpdown.click();
		sleep(3000);
		
//   SELECT VALUES FROM DROPDOWN
		
		WebElement drpdnRecord = driver.findElement(By.xpath("/html/body/app-dashboard/div[1]/main/ol/li[3]/div/li/div/div/a[3]"));
		drpdnRecord.click();
		sleep(3000);
		
		
		
		
		
		//Select drpdnRecord = new Select(driver.findElement(By.xpath("//app-dashboard//ol[@class='breadcrumb']//div[@role='group']/li[@class='nav-item']//div[@class='dropdown-menu dropdown-menu-right show']/a[@class='dropdown-item']")));
		//drpdnRecord.selectByVisibleText("Bentota");
		//sleep(2000);
		
		

//     CLICK ON ADMIN ICON
		//WebElement adminTab = driver.findElement(By.xpath("/html/body/app-dashboard/header/ul[1]/li[2]/a/img"));
		//adminTab.click();
		//sleep(3000);

//     CLICK ON BUILDING ICON
		WebElement buldIcn = driver.findElement(By.xpath("/html/body/app-dashboard/header/ul[1]/li[2]/a/img"));
		buldIcn.click();
		sleep(3000);

//     CLICK ON METADATA TAB
		WebElement Metadata = driver.findElement(
				By.xpath("/html/body/app-dashboard/div[1]/app-sidebar/app-sidebar-nav-custom/ul/div[6]/a"));
		Metadata.click();
		sleep(3000);

//	     CLICK ON EXPORT
		/*
		 * String downloadFilepath =
		 * "C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners"; Map<String,
		 * Object> preferences = new Hashtable<String, Object>();
		 * preferences.put("profile.default_content_settings.popups", 0);
		 * preferences.put("download.prompt_for_download", "false");
		 * preferences.put("download.default_directory", downloadFilepath);
		 * ChromeOptions options = new ChromeOptions();
		 * options.setExperimentalOption("prefs", preferences); WebDriver driver = new
		 * ChromeDriver(options);
		 */
			WebElement Export = driver.findElement(
					By.xpath("/html/body/app-dashboard/div[1]/main/div/app-metadata/div[1]/div[2]/div/div/div/div/button"));
			Export.click();
			sleep(3000);

		
	
	
//      IMPORT METADATA
			
			//File filepath = new File ("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\BuildingMetadata.json");
		
		WebElement Import = driver.findElement(
				By.xpath("/html/body/app-dashboard/div[1]/main/div/app-metadata/div[2]/div[2]/div/div/div/div/button"));
		Import.click();
		sleep(2000);
		
		//WebElement Fileupload = driver.findElement(By.id("fileUploader"));
		//Fileupload.sendKeys("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\BuildingMetadata.json");
		//sleep(2000);
		
		Runtime.getRuntime().exec("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\UploadFile.exe");
		sleep(3000);
		
		//driver.switchTo().activeElement().sendKeys("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\BuildingMetadata.json");
		//driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		
		
		
		
		// switch to the file upload window
		//Alert alert = driver.switchTo().alert();

		// enter the filename
		//alert.sendKeys("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\BuildingMetadata.json");
		
		
		//StringSelection ss = new StringSelection ("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\BuildingMetadata.json");

		//Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		// hit enter
		//Robot r = new Robot();
		//r.keyPress(KeyEvent.VK_ENTER);
		//r.keyRelease(KeyEvent.VK_ENTER);

		// switch back
		//driver.switchTo().alert().dismiss();
		
		
	

		WebElement Cnclbtn = driver.findElement(By.id("Cancel"));
		Cnclbtn.click();
		sleep(2000);
	
		//WebElement Confrmbtn = driver.findElement(By.xpath("/html//button[@id='Confirm']"));
		//Confrmbtn.click();
		//sleep(2000);

		
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
