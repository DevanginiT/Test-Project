package project_cams;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

public class MetaDataCams {

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

	@SuppressWarnings("unchecked")

	public void readWriteJSON() throws InterruptedException, IOException, ParseException {

		System.out.println("Reading Json file");

		JSONParser jsonParser = new JSONParser();

		try {

			FileReader reader = new FileReader(
					"C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\BuildingMetadata.json");

			// Read JSON file

			Object obj = jsonParser.parse(reader);

			JSONArray array = new JSONArray();
			array.add(obj);

			for (int i = 0; i < array.size(); i++) {

				JSONObject Building = (JSONObject) array.get(i);

				System.out.println("Buildings -> " + Building);

				JSONObject Bdata = (JSONObject) Building.get("Entity");

				String Name = (String) Bdata.get("Name");
				System.out.println(Name);

				JSONArray Fields = (JSONArray) Bdata.get("Fields");

				System.out.println("Following are the fields of the Building");

				for (int j = 0; j < Fields.size(); j++) {

					JSONObject BuildingFields = (JSONObject) Fields.get(j);

					String Name1 = (String) BuildingFields.get("Name");
					System.out.println("Name :" + Name1);

					String dispName = (String) BuildingFields.get("DisplayName");
					System.out.println("Display Name :" + dispName);

					String errMsg = (String) BuildingFields.get("ValidationErrorMessage");
					System.out.println("Error Message :" + errMsg);

					if (dispName.equals("Building Type")) {

						BuildingFields.put("ValidationErrorMessage", "Changed Validation Error Message");

						System.out.println("Updated ValidationErrorMessage: " + errMsg);

						// Write JSON file

						try (FileWriter file = new FileWriter(
								"C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\TestMetadata1.json")) {

							file.append(array.toJSONString());

							file.flush();

						} catch (IOException e) {

							e.printStackTrace();

						}

						System.out.println("Updating jason file successfully" + BuildingFields);

					}

				}
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();

		}
	}

	@Test(priority = 1)
	public void doLogin() throws IOException, InterruptedException, ParseException {

		System.out.println("Executing loginTest");

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
				"/html/body/app-dashboard/div[1]/main/ol/li[3]/div/li/div/div/a[8]"));
		drpdnRecord.click();
		sleep(2000);

//     CLICK ON ADMIN ICON
		//WebElement adminTab = driver.findElement(By.xpath("/html/body/app-dashboard/header/ul[1]/li[2]/a/img"));
		//adminTab.click();
		//sleep(2000);

//     CLICK ON BUILDING ICON
		WebElement buldIcn = driver.findElement(By.xpath("/html/body/app-dashboard/header/ul[1]/li[2]/a/img"));
		buldIcn.click();
		sleep(2000);

//     CLICK ON METADATA TAB
		WebElement Metadata = driver.findElement(
				By.xpath("/html/body/app-dashboard/div[1]/app-sidebar/app-sidebar-nav-custom/ul/div[6]/a"));
		Metadata.click();
		sleep(2000);

//     CLICK ON EXPORT
		WebElement Export = driver.findElement(
				By.xpath("/html/body/app-dashboard/div[1]/main/div/app-metadata/div[1]/div[2]/div/div/div/div/button"));
		Export.click();
		sleep(2000);

		readWriteJSON();
		sleep(3000);

//      IMPORT METADATA

		WebElement Import = driver.findElement(
				By.xpath("/html/body/app-dashboard/div[1]/main/div/app-metadata/div[2]/div[2]/div/div/div/div/button/i"));
		Import.click();
		sleep(2000);

		Runtime.getRuntime().exec("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\UploadFile.exe"); // Used an AutoIT script to handle window popup
																													
		sleep(3000);

		// WebElement Cnclbtn = driver.findElement(By.id("Cancel"));
		// Cnclbtn.click();
		// sleep(2000);

		WebElement Confrmbtn = driver.findElement(By.xpath("/html//button[@id='Confirm']"));
		Confrmbtn.click();
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
