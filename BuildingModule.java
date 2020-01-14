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

public class BuildingModule {

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
		WebElement buldIcn = driver
				.findElement(By.xpath("//app-dashboard/header/ul[1]/li[@title='Buildings']//img[@class='icon']"));
		buldIcn.click();
		sleep(3000);

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
	public void ImportData() throws IOException, InterruptedException, ParseException {

		System.out.println("Executing Import Building Test....");

//     CLICK ON IMPORT DATA TAB
		WebElement ImportData = driver.findElement(By.xpath(
				"//app-dashboard//app-sidebar[@class='sidebar']/app-sidebar-nav-custom/ul[@class='nav']//span[.=' Import data ']"));
		ImportData.click();
		sleep(2000);

		try {
			// CLICK ON DATA IMPORT TEMPLATE
			WebElement DataImport = driver.findElement(
					By.xpath("/html/body/app-dashboard/div[1]/main/div/app-data-import/div[1]/div[2]/div/div/button"));
			DataImport.click();
			sleep(2000);

			// CLICK ON A BROWSE

			WebElement DataImport1 = driver.findElement(By.xpath(
					"/html/body/app-dashboard/div[1]/main/div/app-data-import/div[2]/div[2]/div/div/div/div/button"));
			DataImport1.click();
			sleep(2000);

			Runtime.getRuntime()
					.exec("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\ImportBuilding.exe");
			sleep(3000);

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,750)");
			sleep(2000);

			// CLICK ON CANCEL

			WebElement CancelImport1 = driver.findElement(By.xpath(
					"/html/body/app-dashboard/div[1]/main/div/app-data-import/div/div[2]/div/div/div/div[2]/button"));
			CancelImport1.click();
			sleep(2000);

			WebElement CancelConfirm = driver.findElement(By.xpath("//*[@id=\"Confirm\"]"));
			CancelConfirm.click();
			sleep(2000);

			// CLICK ON A BROWSE

			WebElement DataImport2 = driver.findElement(By.xpath(
					"/html/body/app-dashboard/div[1]/main/div/app-data-import/div[2]/div[2]/div/div/div/div/button"));
			DataImport2.click();
			sleep(2000);

			Runtime.getRuntime()
					.exec("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\ImportBuilding.exe");
			sleep(3000);

			JavascriptExecutor jse1 = (JavascriptExecutor) driver;
			jse1.executeScript("window.scrollBy(0,750)");
			sleep(2000);

			// CLICK ON SAVE

			WebElement SaveImport1 = driver.findElement(By.xpath(
					"/html/body/app-dashboard/div[1]/main/div/app-data-import/div/div[2]/div/div/div/div[1]/button"));
			SaveImport1.click();
			sleep(2000);

			WebElement SaveConfirm = driver.findElement(By.xpath("//*[@id=\"Confirm\"]"));
			SaveConfirm.click();
			sleep(2000);
		} catch (Exception e) {
			// TODO Auto-generated catch block

			System.out.println("Test Failed....");

			e.printStackTrace();
		}

	}

	@Test(priority = 2)
	public void AttachPhotos() throws IOException, InterruptedException, ParseException {

		System.out.println("Executing Attach Photos Test....");

//	     CLICK ON ATTACH PHOTOS TAB

		WebElement AttachPhoto = driver.findElement(By.xpath(
				"//app-dashboard//app-sidebar[@class='sidebar']/app-sidebar-nav-custom/ul[@class='nav']//span[.=' Attach Photos ']"));
		AttachPhoto.click();
		sleep(2000);

		try {
			// CLICK ON DROPDOWN

			WebElement drpdownCategory = driver.findElement(By.xpath(
					"/html/body/app-dashboard/div[1]/main/div/app-attach-files/level-data-view/div/div/div[1]/div[4]/div/div/button/span"));
			drpdownCategory.click();
			sleep(2000);

			// SELECT VALUES FROM DROPDOWN

			WebElement SelectCategory = driver.findElement(By
					.xpath("/html//div[@id='card']/div[@class='row rowSpace']//div[@class='dropdown-menu show']/a[1]"));
			SelectCategory.click();
			sleep(2000);

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,1250)");
			sleep(2000);

			// DOWNLOAD ATTACHMENT TEMPLATE

			WebElement AttachTemplate = driver.findElement(
					By.xpath("/html/body/app-dashboard/div[1]/main/div/app-attach-files/div[1]/div[2]/div/div/button"));
			AttachTemplate.click();
			sleep(2000);

			// CLICK ON IMPORT ATTACHMENT

			WebElement ImportAttachment = driver.findElement(By.xpath(
					"/html/body/app-dashboard/div[1]/main/div/app-attach-files/div[2]/div[2]/div/div[1]/button"));
			ImportAttachment.click();
			sleep(2000);

			// IMPORT EXCEL PHOTO ATTACHMENT TEMPLATE

			Runtime.getRuntime()
					.exec("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\ImportPhotoAttachment.exe");
			sleep(3000);

			// IMPORT PHOTOS TO BE ATTACHED

			WebElement ImportPhotos = driver.findElement(By.xpath(
					"/html/body/app-dashboard/div[1]/main/div/app-attach-files/div[3]/div[2]/div/div[1]/button"));
			ImportPhotos.click();
			sleep(2000);

			Runtime.getRuntime().exec("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\ImportPhotos.exe");
			sleep(3000);
		} catch (Exception e) {
			// TODO Auto-generated catch block

			System.out.println("TEST FAILED");

			e.printStackTrace();
		}

	}
	

	@Test(priority = 3)
	public void EditData() throws IOException, InterruptedException, ParseException {

		System.out.println("Executing Edit Data Test....");
		
		
		// CLICK ON EDIT DATA TAB

		WebElement EditDataTab = driver.findElement(By.xpath(
				"//app-dashboard//app-sidebar[@class='sidebar']/app-sidebar-nav-custom/ul[@class='nav']//a[@href='#/feature/data-edit']"));
		EditDataTab.click();
		sleep(2000);
		
		// CLICK ON DROPDOWN
		
					WebElement drpdownLevels = driver.findElement(By.xpath("/html//button[@id='dropdownMenuButton']"));
					drpdownLevels.click();
					sleep(2000);

					// SELECT VALUES FROM DROPDOWN

					WebElement SelectLevels = driver.findElement(By.xpath(
							"/html//div[@id='card']/div[@class='row rowSpace']//div[@class='dropdown-menu show']/a[1]"));
					SelectLevels.click();
					sleep(2000);
					
					
					JavascriptExecutor jse = (JavascriptExecutor)driver;
					jse.executeScript("window.scrollBy(0,1250)");
					sleep(2000);
		
					
					// DOWNLOAD EDIT DATA SHEET
					
					WebElement EditDataDownload = driver.findElement(By.xpath("/html/body/app-dashboard/div[1]/main/div/app-data-edit/div/div[1]/div[2]/div/div/div/div/button"));
					EditDataDownload.click();
					sleep(2000);
		
					// IMPORT EDIT DATA SHEET
					
					WebElement EditDataImport = driver.findElement(By.xpath("//body/app-dashboard/div[@class='app-body']/main[@class='main']//app-data-edit[@class='ng-star-inserted']/div[@class='ng-star-inserted']//button[@class='btn btn-primary marginMinWidth']"));
					EditDataImport.click();
					sleep(2000);
		
					
					// IMPORT EDITED DATA SHEET
					
		
					
					
					
					
					
					
	}
	
	
	
	
	@Test(priority = 4)
	public void DataExplorer() throws IOException, InterruptedException, ParseException {

		System.out.println("Executing Data Explorer Test....");
		
//	     CLICK ON DATA EXPLORER TAB
		
			WebElement DataExplorer = driver.findElement(
					By.xpath("/html/body/app-dashboard/div[1]/app-sidebar/app-sidebar-nav-custom/ul/div[5]/a/span"));
			DataExplorer.click();
			sleep(2000);

			// CLICK ON DROPDOWN
			
			WebElement drpdownCategory = driver.findElement(By.xpath("/html/body/app-dashboard/div[1]/main/div/app-data-explorer/level-data-view/div/div/div[1]/div[3]/div/div/button/span"));
			drpdownCategory.click();
			sleep(2000);

			// SELECT VALUES FROM DROPDOWN

			WebElement SelectLevels = driver.findElement(By.xpath(
					"/html/body/app-dashboard/div[1]/main/div/app-data-explorer/level-data-view/div/div/div[1]/div[3]/div/div/div/a[1]"));
			SelectLevels.click();
			sleep(2000);
			
			
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,1250)");
			sleep(2000);
	
		
	}
	
	@Test(priority = 5)
	public void MetaData() throws IOException, InterruptedException, ParseException {

		System.out.println("Executing Meta Data Test....");
		
		
//	     CLICK ON METADATA TAB
			WebElement Metadata = driver.findElement(
					By.xpath("//app-dashboard[@class='ng-star-inserted']//app-sidebar[@class='sidebar']/app-sidebar-nav-custom/ul[@class='nav']//span[.=' Configure data ']"));
			Metadata.click();
			sleep(3000);
			
//       Download MetaData			
			
			WebElement DownloadMetadata = driver.findElement(
					By.xpath("//body/app-dashboard[@class='ng-star-inserted']/div[@class='app-body']/main[@class='main']/div[@class='container-fluid']/app-metadata[@class='ng-star-inserted']/div[1]/div[@class='card-footer']/div[@class='row']/div[@class='col-sm-5']/div/div/button[@class='btn btn-primary']"));
			DownloadMetadata.click();
			sleep(2000);
			
			
			
			
//	      IMPORT METADATA
			
			WebElement Import = driver.findElement(
					By.xpath("//body/app-dashboard[@class='ng-star-inserted']/div[@class='app-body']/main[@class='main']/div[@class='container-fluid']/app-metadata[@class='ng-star-inserted']/div[2]/div[@class='card-footer']/div[@class='row']/div[@class='col-sm-5']/div/div/button[@type='button']"));
			Import.click();
			sleep(2000);
		
			Runtime.getRuntime().exec("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\UploadFile.exe");
			sleep(3000);
			
		

			WebElement Cnclbtn = driver.findElement(By.id("Cancel"));
			Cnclbtn.click();
			sleep(2000);
			
			
			
		
		
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
