package project_cams;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

public class LoginCams {

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

        // CAPTURING SCREENSHOT
	 

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
	public void doLogin() throws IOException {

		System.out.println("Executing loginTest");

//		OPEN CAMS LOGIN PAGE 	
		String url = "https://stage.cams.assethub.com.au/#/login";
		driver.get(url);
		System.out.println("Page is opened.");

//       SLEEP FOR 2 SECONDS
		sleep(2000);

		System.out.println("Starting Login Using ExcelSheet Test");

		
		// WRITE AND READ FROM EXCEL FILE

		File src = new File("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\LoginTestDataCams.xlsx");
		FileInputStream fis = new FileInputStream(src);

		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(fis);

		XSSFSheet Sheet1 = wb.getSheetAt(0);

		int rowcount = Sheet1.getLastRowNum();

		System.out.println("Total No of Rows are : " + rowcount);

		try {
			for (int i = 1; i <= rowcount; i++) {

				// READING DATA FROM THE EXCEL FILE

				String Data0 = Sheet1.getRow(i).getCell(0).getStringCellValue();
				System.out.println("Username IS " + Data0);

				String Data1 = Sheet1.getRow(i).getCell(1).getStringCellValue();
				System.out.println("Password is :" + Data1);
				// System.out.println("Data from the Excel Sheet Row :" +i+ "is" +Data0);

//	 			WRITING IN EXCEL FILE	

				Row row = Sheet1.getRow(i);
				Cell cell = row.createCell(2);

				FileOutputStream fos = new FileOutputStream(src);

				// wb.write(fos);

//			ENTER USERNAME
				WebElement username = driver.findElement(
						By.xpath("/html/body/app-login/div/main/div/div/div/div/div/div/div/form/div[1]/div[1]/input"));
				username.click();
				username.clear();
				username.sendKeys(Data0);

//			ENTER PASSWORD
				WebElement password = driver.findElement(By.id("password"));
				password.clear();
				password.sendKeys(Data1);

//			CLICK LOGIN BUTTON
				WebElement logInButton = driver.findElement(By
						.xpath("/html/body/app-login/div/main/div/div/div/div/div/div/div/form/div[3]/div[1]/button"));
				logInButton.click();

				sleep(2000);

//				VALID AND INVALID USERNAME		  

				// SUCCESSFULL LOGIN MESSAGE ON VALID USER CREDENTIALS

				try {

					String actualUrl = "https://stage.cams.assethub.com.au/#/home/dashboard";
					String expectedUrl = driver.getCurrentUrl();

					// Assert.assertEquals(expectedUrl,actualUrl);

					if (actualUrl.equalsIgnoreCase(expectedUrl)) {
						System.out.println("VALID CREDENTIALS TEST PASSED");

						captureScreenshot();
						cell.setCellValue("PASS");
						wb.write(fos);
						sleep(2000);

						// USER LOGOUT
						WebElement usertab = driver
								.findElement(By.xpath("//app-dashboard/header/ul[3]//a[@role='button']/img"));
						usertab.click();
						sleep(2000);

						WebElement Logout = driver
								.findElement(By.xpath("//app-dashboard/header/ul[3]/li//a[@href='#/login']"));
						Logout.click();

						// cell.setCellValue("PASS");
						// wb.write(fos);

					} else {
						System.out.println("INVALID CREDENTIALS TEST PASSED");
						cell.setCellValue("PASS");
						wb.write(fos);
						sleep(3000);

						System.out.println("Capturing ScreenShot");
						captureScreenshot();
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					System.out.println("Test failed");
					cell.setCellValue("FAIL");
					wb.write(fos);
					sleep(3000);

					System.out.println("Capturing ScreenShot");
					captureScreenshot();

				}

				// fos.close();
				// fis.close();
				// wb.close();

			}
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










