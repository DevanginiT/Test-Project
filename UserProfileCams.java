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

public class UserProfileCams {

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
		
		FileHandler.copy(screenshot, screenshotLocation);

	}

	@Test(priority = 1)
	public void doLogin() throws IOException {
		
		
		
		// CHANGE PASSWORD WRITE AND READ FROM EXCEL FILE
				try {

					File src = new File("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\LoginTestDataCams.xlsx");
					FileInputStream fis = new FileInputStream(src);

					@SuppressWarnings("resource")
					XSSFWorkbook wb = new XSSFWorkbook(fis);

					XSSFSheet Sheet1 = wb.getSheetAt(1);

					int rowcount = Sheet1.getLastRowNum();

					System.out.println("Total No of Rows are : " + rowcount);

					for (int i = 1; i <= rowcount; i++) {


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
		username.clear();
		username.sendKeys("devangini.tandel@rmit.edu.au");
		sleep(2000);

//		ENTER PASSWORD
		WebElement password = driver.findElement(By.id("password"));
		password.clear();
	
		password.sendKeys("Devangini");
		sleep(2000);

//		CLICK LOGIN BUTTON
		WebElement logInButton = driver.findElement(
				By.xpath("/html/body/app-login/div/main/div/div/div/div/div/div/div/form/div[3]/div[1]/button"));
		logInButton.click();
		sleep(5000);
		captureScreenshot();

//     CLICK ON ADMIN ICON
		WebElement adminTab = driver.findElement(By.xpath("/html/body/app-dashboard/header/ul[1]/li[2]/a/img"));
		adminTab.click();
		sleep(3000);

//     CLICK ON USER 
		WebElement user = driver.findElement(By.xpath("/html/body/app-dashboard/header/ul[3]/li/a/img"));
		user.click();
		sleep(2000);

//     USER PROFILE

		WebElement userProfile = driver.findElement(By.xpath("/html/body/app-dashboard/header/ul[3]/li/div/a[1]"));
		userProfile.click();
		sleep(2000);

//     EDIT PROFILE
		WebElement editUser = driver.findElement(By.xpath(
				"/html/body/app-dashboard/div[1]/main/div/app-user-profile/div/div/div/div/div[2]/div/form/div[2]/input"));
		editUser.clear();
		editUser.sendKeys("Devangini");
		sleep(2000);

		WebElement editname = driver.findElement(By.xpath(
				"/html/body/app-dashboard/div[1]/main/div/app-user-profile/div/div/div/div/div[2]/div/form/div[3]/input"));
		editname.clear();
		editname.sendKeys("Tandel");
		sleep(2000);

		WebElement userSave = driver.findElement(By.xpath(
				"/html/body/app-dashboard/div[1]/main/div/app-user-profile/div/div/div/div/div[2]/div/div[2]/div/button"));
		userSave.click();
		sleep(2000);

//    CHANGE PASSWORD


				// READING DATA FROM THE EXCEL FILE

				String Data0 = Sheet1.getRow(i).getCell(0).getStringCellValue();
				System.out.println("Current Password is: " + Data0);

				String Data1 = Sheet1.getRow(i).getCell(1).getStringCellValue();
				System.out.println("New Password is :" + Data1);

				String Data2 = Sheet1.getRow(i).getCell(2).getStringCellValue();
				System.out.println("Confirm Password is :" + Data2);

//	 			WRITING IN EXCEL FILE	

				Row row = Sheet1.getRow(i);
				Cell cell = row.createCell(3);

				FileOutputStream fos = new FileOutputStream(src);

				WebElement changePwd = driver.findElement(By.xpath(
						"/html/body/app-dashboard/div[1]/main/div/app-user-profile/div/div/div/div/div[3]/div[2]/form/div[1]/input"));
				changePwd.clear();
				changePwd.sendKeys(Data0);

				sleep(1000);

				WebElement newPwd = driver.findElement(By.xpath(
						"/html/body/app-dashboard/div[1]/main/div/app-user-profile/div/div/div/div/div[3]/div[2]/form/div[2]/input"));
				newPwd.clear();
				newPwd.sendKeys(Data1);
				sleep(1000);

				WebElement newPwd1 = driver.findElement(By.xpath(
						"/html/body/app-dashboard/div[1]/main/div/app-user-profile/div/div/div/div/div[3]/div[2]/form/div[3]/input"));
				newPwd1.clear();
				newPwd1.sendKeys(Data2);
				sleep(1000);

				WebElement pwdSave = driver.findElement(By.xpath(
						"/html/body/app-dashboard/div[1]/main/div/app-user-profile/div/div/div/div/div[3]/div[2]/div[2]/div/button"));
				pwdSave.click();
				sleep(2000);

				captureScreenshot();
				
				
				//WebElement successMessage = driver.findElement(By.xpath("/html/body/app-dashboard/div[1]/main/div/app-user-profile/div/div/div/div/div[3]/div[2]/div[2]/div/button"));
				//String expectedMessage = "Password Changed Successfully";
				//String actualMessage = successMessage.getText();
				//Assert.assertEquals(actualMessage, expectedMessage, "Actual message is not the same as expected");
				
				
				//LOGOUT
				
				// USER LOGOUT
				WebElement usertab = driver
						.findElement(By.xpath("//app-dashboard/header/ul[3]//a[@role='button']/img"));
				usertab.click();
				sleep(2000);

				WebElement Logout = driver
						.findElement(By.xpath("//app-dashboard/header/ul[3]/li//a[@href='#/login']"));
				Logout.click();
				
//				ENTER USERNAME
				WebElement username1 = driver.findElement(
						By.xpath("/html/body/app-login/div/main/div/div/div/div/div/div/div/form/div[1]/div[1]/input"));
				username1.clear();
				username1.sendKeys("devangini.tandel@rmit.edu.au");
				sleep(2000);

//				ENTER PASSWORD
				WebElement password1 = driver.findElement(By.id("password"));
				password1.clear();
				password1.sendKeys(Data1);
				sleep(2000);

//				CLICK LOGIN BUTTON
				WebElement logInButton1 = driver.findElement(
						By.xpath("/html/body/app-login/div/main/div/div/div/div/div/div/div/form/div[3]/div[1]/button"));
				logInButton1.click();
				sleep(5000);
				//captureScreenshot();
				
				String actualUrl = "https://stage.cams.assethub.com.au/#/home/dashboard";
				String expectedUrl = driver.getCurrentUrl();
				

				if (actualUrl.equalsIgnoreCase(expectedUrl)) {
					System.out.println(" TEST PASSED");

					captureScreenshot();
					cell.setCellValue("PASS");
					
					String newPassword = password1.getText();
					System.out.println(" New Password is :"+newPassword);
					
					
					wb.write(fos);
					sleep(2000);
				} else {
					System.out.println("TEST FAILED");

				
					captureScreenshot();
					cell.setCellValue("FAIL");
					
					wb.write(fos);
					sleep(2000);
				}

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
