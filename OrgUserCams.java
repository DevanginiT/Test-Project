package project_cams;

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

public class OrgUserCams {

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
	public void doLogin() throws IOException, InterruptedException, ParseException {

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

		// CLICK ON DROPDOWN
		WebElement drpdown = driver.findElement(By.xpath("//a[@id='dropdownMenu']/i"));
		drpdown.click();
		sleep(2000);

		// SELECT VALUES FROM DROPDOWN

		WebElement drpdnRecord = driver.findElement(By.xpath(
				"//app-dashboard//ol[@class='breadcrumb']//div[@role='group']/li[@class='nav-item']//div[@class='dropdown-menu dropdown-menu-right show']/a[.=' DPT ']"));
		drpdnRecord.click();
		sleep(3000);

//     CLICK ON ADMIN ICON
		WebElement adminTab = driver.findElement(By.xpath("/html/body/app-dashboard/header/ul[1]/li[2]/a/img"));
		adminTab.click();
		sleep(3000);

//     CLICK ON ORGANISATION USERS
		WebElement UserOrg = driver.findElement(By.xpath("/html/body/app-dashboard/div[1]/app-sidebar/app-sidebar-nav-custom/ul/div[5]/a/span"));
		UserOrg.click();
		sleep(2000);
		
//	   ADD NEW ORGANISATION USERS
		WebElement UserOrgnew = driver.findElement(By.xpath("/html/body/app-dashboard/div[1]/main/div/app-organisation-users/div/div[2]/div/div/button"));
		UserOrgnew.click();
		sleep(2000);
		
		
		
//     ADD USER EMAIL
		
//		ENTER USERNAME
		WebElement useremail = driver.findElement(
				By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='Email address']"));
		useremail.sendKeys("gini17t@gmail.com");
		sleep(3000);
		
		
//		ENTER USER FIRST NAME
		WebElement userfname = driver.findElement(
				By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='First Name']"));
		userfname.sendKeys("gini");
		sleep(3000);
		
		
//		ENTER USER LAST NAME
		WebElement userlname = driver.findElement(
				By.xpath("/html/body/app-dashboard/div[1]/main/div/app-organisation-users/div/div[1]/collapse-overlay/div/div/form/div[3]/input"));
		userlname.sendKeys("tndl");
		sleep(3000);
		
		
//		CLICK ON CHECKBOX
		WebElement uadmin = driver.findElement(
				By.xpath("/html/body/app-dashboard/div[1]/main/div/app-organisation-users/div/div[1]/collapse-overlay/div/div/form/div[4]/input"));
		uadmin.click();
		sleep(2000);
		
		
//		CLICK ON SAVE
		WebElement usersave = driver.findElement(
				By.xpath("/html/body/app-dashboard/div[1]/main/div/app-organisation-users/div/div[1]/collapse-overlay/div/div/div[2]/div[1]/button"));
		usersave.click();
		sleep(2000);
		
		
//   EDIT ORGANISATION USERS
		
//		CLICK ON USER TO EDIT
		WebElement edituser = driver.findElement(
				By.xpath("//div[@id='card']/div[2]/div/table//td[.='dmtandel1730@gmail.com']"));
		edituser.click();
		sleep(2000);
		
		
//		EDIT USER FIRST NAME
		WebElement editfname = driver.findElement(
				By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='First Name']"));
		editfname.clear();
		editfname.sendKeys("  ");
		sleep(3000);
		
		
//		EDIT USER LAST NAME
		WebElement editlname = driver.findElement(
				By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='Last Name']"));
		editlname.clear();
		editlname.sendKeys("  ");
		sleep(3000);
		
		
//		CLICK ON SAVE TO UPDATE
	//	WebElement editsave = driver.findElement(
		//		By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']//div[@class='col-4']/button[@type='button']"));
	//	editsave.click();
		//sleep(6000);		
		
//		CLICK ON DISABLE BUTTON
		//WebElement disableuser = driver.findElement(
			//	By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']//div[@class='col-4 disableBtnDiv']/button[@type='button']"));
		//disableuser.click();
		//sleep(6000);		
		

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
