package CAMS2;

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

public class LOGIN {

	private static WebDriver driver;

	public static void captureScreenshot() throws IOException {

		Date d = new Date();
		String fileName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// FileUtils.copyFile(screenshot, new File(".//screenshot//" + fileName));
		FileHandler.copy(screenshot,
				new File("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\screenshots_cams_fail\\" + fileName));

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
				"C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\screenshots_cams_fail\\" + fileName);
		// FileUtils.copyFile(screenshot, screenshotLocation);
		FileHandler.copy(screenshot, screenshotLocation);

	}
	
	
	public static void captureScreenshotPass() throws IOException {

		Date d = new Date();
		String fileName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// FileUtils.copyFile(screenshot, new File(".//screenshot//" + fileName));
		FileHandler.copy(screenshot,
				new File("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\screenshots_cams_pass\\" + fileName));

	}

	public static void captureEleScreenshotPass(WebElement ele) throws IOException {

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
				"C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\screenshots_cams_pass\\" + fileName);
		// FileUtils.copyFile(screenshot, screenshotLocation);
		FileHandler.copy(screenshot, screenshotLocation);

	}

	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	private void setUp(@Optional("chrome") String browser) throws IOException {

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

	
		// MAXIMIZE BROWSER WINDOW
		driver.manage().window().maximize();

		try
		{
		
//		OPEN CAMS LOGIN PAGE 	
		String url = "https://stage.cams.assethub.com.au/#/login";
		driver.get(url);
		System.out.println("Page is opened.");

//       SLEEP FOR 2 SECONDS
		sleep(2000);

//		ENTER USERNAME
		WebElement username = driver.findElement(
				By.xpath("/html/body/app-login/div/main/div/div/div/div/div/div/div/form/div[1]/div[1]/input"));
		username.sendKeys("devangini.tandel@rmit.edu.au");
		//sleep(2000);

//		ENTER PASSWORD
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys("Devangini");
		//sleep(2000);

//		CLICK LOGIN BUTTON
		WebElement logInButton = driver.findElement(
				By.xpath("/html/body/app-login/div/main/div/div/div/div/div/div/div/form/div[3]/div[1]/button"));
		logInButton.click();
		sleep(5000);
		
	
		} catch (Exception e) {

			System.out.println(e.getMessage());

			System.out.println("TEST FAILED");
			captureScreenshot();

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		}
		
	
		@Test(priority = 1)
		public void doLogin() throws IOException {

			System.out.println("Executing loginTest");
		
		
		// WRITE AND READ FROM EXCEL FILE

		File src = new File("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\LOGINUSERS.xlsx");
		FileInputStream fis = new FileInputStream(src);

		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(fis);

		XSSFSheet Sheet1 = wb.getSheetAt(0);
		XSSFSheet Sheet3 = wb.getSheetAt(2);

		int rowcount = Sheet1.getLastRowNum();

		System.out.println("Total No of Rows are : " + rowcount);

		try {
			// USER LOGOUT
			WebElement usertab = driver
					.findElement(By.xpath("//app-dashboard/header/ul[3]//a[@role='button']/img"));
			usertab.click();
			sleep(1000);

			WebElement Logout = driver
					.findElement(By.xpath("//app-dashboard[@class='ng-star-inserted']/header/ul[3]/li/div/a[2]"));
			Logout.click();

			// SLEEP FOR 2 SECONDS
			sleep(2000);
			
		

			// System.out.println("Starting incorrectUsernameTest");

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

				Row row1 = Sheet3.getRow(i);
				Cell cell1 = row1.createCell(7);
				Cell cell2 = row1.createCell(8);

				FileOutputStream fos = new FileOutputStream(src);

				// ENTER USERNAME
				WebElement username = driver.findElement(
						By.xpath("/html/body/app-login/div/main/div/div/div/div/div/div/div/form/div[1]/div[1]/input"));
				username.clear();
				username.sendKeys(Data0);
				//sleep(2000);

				// ENTER PASSWORD
				WebElement password = driver.findElement(By.id("password"));
				password.clear();
				password.sendKeys(Data1);
				//sleep(2000);

				// CLICK LOGIN BUTTON
				WebElement logInButton = driver.findElement(By
						.xpath("/html/body/app-login/div/main/div/div/div/div/div/div/div/form/div[3]/div[1]/button"));
				logInButton.click();
				sleep(3000);

				// SUCCESSFULL LOGIN MESSAGE ON VALID USER CREDENTIALS

				try {

					String actualUrl = "https://stage.cams.assethub.com.au/#/admin";
					String expectedUrl = driver.getCurrentUrl();

					// Assert.assertEquals(expectedUrl,actualUrl);

					if (actualUrl.equalsIgnoreCase(expectedUrl)) {
						System.out.println("LOGIN SUCCESSFUL");
						cell.setCellValue("PASS");
						wb.write(fos);

						

					} else {
						System.out.println("LOGIN FAILED");
						cell.setCellValue("PASS");
						wb.write(fos);
						captureScreenshotPass();

						driver.navigate().refresh();
					}
					
					FileOutputStream fos1 = new FileOutputStream(src);
					cell1.setCellValue("AS EXPECTED");
					cell2.setCellValue("PASS");
					wb.write(fos1);
					captureScreenshotPass();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					
					cell.setCellValue("FAIL");
					wb.write(fos);
					captureScreenshot();
				}
			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

			System.out.println("TEST FAILED");
			captureScreenshot();

			Row row1 = Sheet3.getRow(1);
			Cell cell1 = row1.createCell(7);
			Cell cell2 = row1.createCell(8);

			FileOutputStream fos1 = new FileOutputStream(src);
			cell1.setCellValue("NOT AS EXPECTED");
			cell2.setCellValue("FAIL");
			wb.write(fos1);

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
		
		
		
		@Test(priority = 2)
		public void fPassword() throws IOException {

			System.out.println("Executing Forgot Password");
		
		
		// WRITE AND READ FROM EXCEL FILE

		File src = new File("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\LOGINUSERS.xlsx");
		FileInputStream fis = new FileInputStream(src);

		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(fis);

		XSSFSheet Sheet1 = wb.getSheetAt(1);
		XSSFSheet Sheet3 = wb.getSheetAt(2);

		int rowcount = Sheet1.getLastRowNum();

		System.out.println("Total No of Rows are : " + rowcount);

		try {
			// USER LOGOUT
			WebElement usertab = driver
					.findElement(By.xpath("//app-dashboard/header/ul[3]//a[@role='button']/img"));
			usertab.click();
			sleep(1000);

			WebElement Logout = driver
					.findElement(By.xpath("//app-dashboard[@class='ng-star-inserted']/header/ul[3]/li/div/a[2]"));
			Logout.click();

			// SLEEP FOR 2 SECONDS
			sleep(2000);
			
			//CLICK ON FORGOT PASSWORD LINK
			
			WebElement fpwd = driver.findElement(By.xpath("//body[@class='app sidebar-lg-show']/app-login[@class='ng-star-inserted']/div[@class='app-body']/main/div[@class='container']/div[@class='row']//form//button[@class='btn btn-link px-0']"));
			fpwd.click();
			sleep(2000);
	

			// System.out.println("Starting incorrectUsernameTest");

			for (int i = 1; i <= rowcount; i++) {

				// READING DATA FROM THE EXCEL FILE

				String Data0 = Sheet1.getRow(i).getCell(0).getStringCellValue();
				System.out.println("Username IS " + Data0);

				//String Data1 = Sheet1.getRow(i).getCell(1).getStringCellValue();
				//System.out.println("Password is :" + Data1);
				// System.out.println("Data from the Excel Sheet Row :" +i+ "is" +Data0);

//	 			WRITING IN EXCEL FILE	

				Row row = Sheet1.getRow(i);
				Cell cell = row.createCell(1);

				Row row1 = Sheet3.getRow(5);
				Cell cell1 = row1.createCell(7);
				Cell cell2 = row1.createCell(8);

				FileOutputStream fos = new FileOutputStream(src);

				// ENTER USER EMAIL
				WebElement username = driver.findElement(
						By.xpath("//body[@class='app sidebar-lg-show']/app-forgot-password[@class='ng-star-inserted']/div[@class='app-body']/main/div[@class='container']/div[@class='row']//form//input[@placeholder='Username']"));
				username.clear();
				username.sendKeys(Data0);
				//sleep(2000);

				

				// CLICK RESET BUTTON
				WebElement resetButton = driver.findElement(By
						.xpath("//body[@class='app sidebar-lg-show']/app-forgot-password[@class='ng-star-inserted']/div[@class='app-body']/main/div[@class='container']/div[@class='row']/div[@class='col-md-6 mx-auto']//button[@class='btn btn-primary px-4']"));
				resetButton.click();
				sleep(3000);

				// SUCCESSFULL LOGIN MESSAGE ON VALID USER CREDENTIALS

				try {

					String actualUrl = "https://stage.cams.assethub.com.au/#/login";
					String expectedUrl = driver.getCurrentUrl();

					// Assert.assertEquals(expectedUrl,actualUrl);

					if (actualUrl.equalsIgnoreCase(expectedUrl)) {
						System.out.println("RESET SUCCESSFUL");
						cell.setCellValue("PASS");
						wb.write(fos);

						

					} else {
						System.out.println("RESET FAILED");
						cell.setCellValue("PASS");
						wb.write(fos);
						captureScreenshotPass();

						driver.navigate().refresh();
					}
					
					FileOutputStream fos1 = new FileOutputStream(src);
					cell1.setCellValue("AS EXPECTED");
					cell2.setCellValue("PASS");
					wb.write(fos1);
					captureScreenshotPass();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					
					cell.setCellValue("FAIL");
					wb.write(fos);
					captureScreenshot();
				}
			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

			System.out.println("TEST FAILED");
			captureScreenshot();

			Row row1 = Sheet3.getRow(5);
			Cell cell1 = row1.createCell(7);
			Cell cell2 = row1.createCell(8);

			FileOutputStream fos1 = new FileOutputStream(src);
			cell1.setCellValue("NOT AS EXPECTED");
			cell2.setCellValue("FAIL");
			wb.write(fos1);

			// TODO Auto-generated catch block
			e.printStackTrace();
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