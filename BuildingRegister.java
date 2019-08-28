package cams;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class BuildingRegister {

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

		sleep(2000);
		driver.manage().window().maximize();
	}

	/*------------------------------------------ CAPTURE SCREENSHOT ------------------------------------------------------------------------------------*/

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
	public void buildingRegister() throws IOException {

		/*
		 * ------------------------------------------ LOGIN PAGE -------------------------------------------------------------------------*/
		
		
		// IMPLICITLY WAIT
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		System.out.println("Executing BuildingRegister Test");

//		OPEN CAMS LOGIN PAGE 	
		String url = "https://app.assethub.com.au/";
		driver.get(url);
		System.out.println("Page is opened.");
		sleep(2000);

//		ENTER USERNAME
		WebElement username = driver.findElement(By.id("txtUsername"));
		username.sendKeys("Devangini@training12");

//		ENTER PASSWORD
		WebElement password = driver.findElement(By.name("txtPassword"));
		password.sendKeys("d291353e");

//		CLICK LOGIN BUTTON
		WebElement logInButton = driver.findElement(By.id("cmdLogin"));
		logInButton.click();
		sleep(2000);
		System.out.println("Login Successfull");

		/*----------------------------------------- BUILDING REGISTER --------------------------------------------------------------*/

		WebElement BuildingRegister = driver.findElement(By.xpath("//a[text()='Building Register']"));
		BuildingRegister.click();
		// sleep(2000);

		/*----------------------------------------- BUILDING TYPE  -------------------------------------------------------------------*/

		// CLICK ON BUILDING TYPE LINK
		WebElement BuildingType = driver.findElement(By.xpath("//a[text()='Building Type']"));
		BuildingType.click();
		// sleep(5000);
		System.out.println("Click on Building Type");

		/*---------------------------------------- ADDING NEW BUILDING TYPE --------------------------------------------------------*/
		
		  //SWITCH FRAME 
		driver.switchTo().frame("contentFrame");
		  
		  //BUILDING TYPE TEXTBOX IN SEARCH PANEL 
		
		WebElement BuildTypeText = driver.findElement(By.xpath(
		  "/html//input[@id='ContentBody_txtSBuildingName']"));
		  BuildTypeText.sendKeys("TestBuilding2"); sleep(2000);
		  
		  //SEARCH BUTTON 
		  
		  WebElement BuildTypeSearch =
		  driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSearch']"));
		  BuildTypeSearch.click(); sleep(2000);
		  
		  //ADD NEW BUILDING TYPE
		  
		  WebElement BuildTypeAdd =
		  driver.findElement(By.xpath("/html//input[@id='ContentBody_btnAdd']"));
		  BuildTypeAdd.click(); sleep(2000);
		  
		  //CLICK ON RESET BUTTON
		  
		  WebElement BuildTypeReset =
		  driver.findElement(By.xpath("/html//input[@id='ContentBody_btnReset']"));
		  BuildTypeReset.click(); sleep(2000);
		  
		  
		  //ADDING NEW BUILDING TYPE 
		  
		  WebElement NewBuildTypeAdd =
		  driver.findElement(By.xpath(
		  "/html//input[@id='ContentBody_txtBuildingTypeName']"));
		  NewBuildTypeAdd.sendKeys("TestBuilding"); sleep(3000);
		  
		  
		  //CLICK ON SUBMIT BUTTON 
		  
		  WebElement BuildTypeAddSubmit =
		  driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSubmit']"));
		  BuildTypeAddSubmit.click(); sleep(2000);
		  
		  
		  
		  //CLICK ON BACK BUTTON 
		  
		  WebElement BuildTypeBack =
		  driver.findElement(By.xpath("/html//input[@id='ContentBody_btnBack']"));
		  BuildTypeBack.click(); sleep(2000);
		  
		  
		  //BUILDING TYPE TEXTBOX IN SEARCH PANEL 
		  
		  WebElement BuildTypeText1 =
		  driver.findElement(By.xpath(
		  "/html//input[@id='ContentBody_txtSBuildingName']"));
		  BuildTypeText1.sendKeys("Test"); sleep(2000);
		  
		  //SEARCH BUTTON 
		  
		  WebElement BuildTypeSearch1 =
		  driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSearch']"));
		  BuildTypeSearch1.click(); sleep(2000);
		  
		  
		  //WebDriverWait wait = new WebDriverWait (driver,10);
		  //wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(
		 // "/html//input[@id='ContentBody_txtSBuildingName'")));
		  //BuildTypeText.sendKeys("TestBuilding2");
		  
		  
		  
		/*  WebDriverWait wait = new WebDriverWait(driver, 10); try {
		  wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
		  "//input[@id='ContentBody_txtBuildingTypeName']")));
		  driver.findElement(By.xpath("/html//input[@id='ContentBody_txtSBuildingName'"
		  )).sendKeys("TestBUilding2"); } catch (TimeoutException exception) {
		  System.out.println("Exception catched: " + exception.getMessage()); //
		  sleep(3000); }*/
		  
		  
		  //sleep(2000);
		  
		  
		  // DELETE ALL FUNCTION
		  
		  //WebElement DeleteAll =
		 // driver.findElement(By.xpath("/html//input[@id='ContentBody_btnDeleteAll']"));
		  // DeleteAll.click();
		  
		  
		  // SELECT RECORD FROM THE SEARCH REPORT
		/*
		 * List <WebElement> rownum = driver.findElements(By.xpath(
		 * "/html/body/form/div[3]/div/div/div/table/tbody/tr/td/div/div/div[2]/table/tbody/tr/td/div/table/tbody/tr"
		 * )); System.out.println("Total num of Rows are"+rownum.size());
		 * 
		 * List <WebElement> colnum = driver.findElements(By.xpath(
		 * "/html/body/form/div[3]/div/div/div/table/tbody/tr/td/div/div/div[2]/table/tbody/tr/td/div/table/tbody/tr[2]/td"
		 * )); System.out.println("Total num of Columns are"+colnum.size());
		 * 
		 * 
		 * 
		 * for(int rows=1; rows <=rownum.size();rows++) { for(int cols=1; cols
		 * <=colnum.size(); cols++) { System.out.println(driver.findElement(By.xpath(
		 * "//table[@id='ContentBody_gvBuildingType']/tbody/tr[2]/td")).getText());
		 * 
		 * } }
		 */
		  
		  
		  // WebElement report = driver.findElement(By.xpath(
		 // "/html/body/form/div[3]/div/div/div/table/tbody/tr/td/div/div/div[2]/table/tbody/tr/td/div/table/tbody/tr[2]/td[5]/a"
		 // )); // report.click(); // sleep(5000);
		  
		  //CLICK ON BACK BUTTON //WebElement BuildTypeBack1 =
		 // driver.findElement(By.xpath("/html//input[@id='ContentBody_btnBack']"));
		  //BuildTypeBack1.click(); //sleep(2000);
		  
		  
		  //BUILDING TYPE TEXTBOX IN SEARCH PANEL 
		  WebElement BuildTypeText2 =
		  driver.findElement(By.xpath(
		  "/html//input[@id='ContentBody_txtSBuildingName']"));
		  BuildTypeText2.sendKeys("Test"); 
		  sleep(2000);
		  
		  //SEARCH BUTTON 
		  WebElement BuildTypeSearch2 =
		  driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSearch']"));
		  BuildTypeSearch2.click(); 
		  sleep(2000);
		  
		  
		  
		  // CLICK ON DOWNLOAD BUTTON
		  
		  //WebElement BuildTypeDownload = driver.findElement(By.
		  //xpath("/html//table[@id='ContentBody_tblPaging']//div[@title='Export Excel']"
		 // )); //BuildTypeDownload.click(); //sleep(5000);
		  //System.out.println("Capturing ScreenShot"); // captureScreenshot();
		  
		  
		  //DELETE RECORD // WebElement report1 = driver.findElement(By.xpath(
		 // "//*[@id=\"ContentBody_gvBuildingType_btnDeleteItem_2\"]")); //
		 // report1.click(); // sleep(5000);
		  
		  
		  
		  
		  
			
		  /*IMPORT BUILDING TYPE USING EXCEL FILE*/
		  		
		  		
		  		driver.switchTo().defaultContent();
		  		
		  		
		  		
		  		// CLICK ON BUILDING TYPE LINK
		  				WebElement BuildingImport = driver.findElement(By.xpath(
		  						"//div[@id='tree']/ul[@class='dynatree-container']/li[1]/ul/li[@class='dynatree-lastsib']/ul/li[1]/span/a[@href='#']"));
		  				BuildingImport.click();
		  				sleep(5000);
		  				

		  				driver.switchTo().frame("contentFrame");

		  				// CLICK ON upload button
		  				WebElement BuildingImport1 = driver.findElement(By.xpath("//*[@id=\'ContentBody_fuBuildingTypes\']"));
		  				//BuildingImport1.click();
		  				sleep(5000);
		  				
		  				System.out.println("Click on Import link");


		  				//WebElement ImportText = driver.findElement(By.xpath("//*[@id=\'ContentBody_vceFUBuildingTypes_ClientState\']"));
		  				//ImportText.sendKeys("C:\\Users\\devan\\OneDrive\\SELENIUM\\DOCS\\BuildingType.xlsx");

		  				// driver.switchTo().alert().sendKeys("C:\\Users\\devan\\OneDrive\\SELENIUM\\DOCS\\BuildingType.xlsx");
		  				// driver.switchTo().alert().accept();

		  				 BuildingImport1.sendKeys("C:\\Users\\devan\\OneDrive\\SELENIUM\\DOCS\\BuildingType.xlsx");
		  				sleep(5000);
		  				System.out.println("Click on upload button box");

		  				// CLICK ON BUILDING TYPE LINK
		  				WebElement BuildingImport2 = driver
		  						.findElement(By.xpath("/html//input[@id='ContentBody_btnUploadBuildingTypes']"));
		  				BuildingImport2.click();
		  				sleep(5000);
		  				System.out.println("Click on upload building type");

//		  		     CAPTURING SCREENSHOT		
		  				captureScreenshot();

		  		
		  	
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
/*------------------------------------- BUILDING  ----------------------------------------------------------------------------------------*/
		  
		  
		  
		
		  driver.switchTo().defaultContent();
		  
		  // CLICK ON BUILDING LINK 
		  WebElement BuildingLink =
		  driver.findElement(By.xpath(
		  "//div[@id='tree']/ul[@class='dynatree-container']/li[1]/ul/li[2]/span/a[@href='#']"
		  )); BuildingLink.click(); sleep(5000);
		  
		  
		  // CLICK ON ADD NEW BUILDING 
		  driver.switchTo().frame("contentFrame");
		  
		  System.out.println("Adding New Building"); WebElement BuildingAdd =
		  driver.findElement(By.xpath("/html//input[@id='ContentBody_btnAdd']"));
		  BuildingAdd.click(); sleep(5000);
		  
		  
		  // ADDING NEW BUILDING
		  
		  //SELECTING BUILDING TYPE FROM DROPDOWN
		  
		  Select drpBType = new
		  Select(driver.findElement(By.id("ContentBody_ddlBuildingType")));
		  drpBType.selectByVisibleText("TestBuilding"); sleep(3000);
		  
		  WebElement BuildText =
		  driver.findElement(By.xpath("/html//input[@id='ContentBody_txtBuildingName']"
		  )); BuildText.sendKeys("Building T2"); sleep(2000);
		  
		  WebElement AssetText =
		  driver.findElement(By.xpath("/html//input[@id='ContentBody_txtBuildingCode']"
		  )); AssetText.sendKeys("Asset A2"); sleep(2000);
		  
		  WebElement BuildingSubmit =
		  driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSubmit']"));
		  BuildingSubmit.click(); sleep(5000);
		  
		  WebElement BuildingBack =
		  driver.findElement(By.xpath("/html//input[@id='ContentBody_btnBack']"));
		  BuildingBack.click(); sleep(5000);
		  
		  
		  // BUILDING REPORT
		  
		  Select drpBType1 = new Select(driver.findElement(By.xpath(
		  "/html//select[@id='ContentBody_ddlSBuildingType']")));
		  drpBType1.selectByVisibleText("TestBuilding"); sleep(3000);
		  
		  WebElement BuildingSearch =
		  driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSearch']"));
		  BuildingSearch.click(); sleep(5000);
		  
		/*
		 * // SELECT RECORD FROM THE REPORT
		 * 
		 * WebElement report1 = driver.findElement(By.xpath(
		 * "//table[@id='ContentBody_gvBuilding']/tbody/tr[2]//a[@title='Select']"));
		 * report1.click(); sleep(5000);
		 * 
		 * // EDIT BUILDING
		 * 
		 * WebElement BuildSiteId =
		 * driver.findElement(By.xpath("/html//input[@id='ContentBody_txtSiteID']"));
		 * BuildSiteId.sendKeys("BuildingSite S1"); sleep(2000);
		 * 
		 * WebElement BuildDescr = driver.findElement(By.xpath(
		 * "/html//textarea[@id='ContentBody_txtDescription']"));
		 * BuildDescr.sendKeys("desc"); sleep(2000);
		 * 
		 * Select drpBTypePr = new Select(driver.findElement(By.xpath(
		 * "/html//select[@id='ContentBody_ddlPriority']")));
		 * drpBTypePr.selectByVisibleText("Low"); sleep(2000);
		 * 
		 * WebElement BuildingSubmit1 =
		 * driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSubmit']"));
		 * BuildingSubmit1.click(); sleep(5000);
		 * 
		 * WebElement BuildingBack1 =
		 * driver.findElement(By.xpath("/html//input[@id='ContentBody_btnBack']"));
		 * BuildingBack1.click(); sleep(5000);
		 * 
		 * WebElement BuildingSearch1 =
		 * driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSearch']"));
		 * BuildingSearch1.click(); sleep(5000);
		 * 
		 * 
		 * 
		 */
				  
/*--------------------------------------------FUNCTIONAL AREA TYPE------------------------------------------------------*/ 
				  
				 // driver.navigate().refresh();
				 // sleep(3000);
				// driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		  		driver.getPageSource();

				 driver.switchTo().defaultContent();
				 
				 System.out.println("Switching Frames");
				  
			//System.out.println();
		  
		  //driver.switchTo().parentFrame();

		  
		// CLICK ON FUNCTIONAL AREA TYPE LINk
		WebElement FuncArea1 = driver.findElement(By.xpath("//div[@id='tree']/ul[@class='dynatree-container']/li[1]/ul/li[3]/span/a[@href='#']"));
		FuncArea1.click();
		sleep(5000);

		// ADDING NEW FUNCTIONAL AREA TYPE

		driver.switchTo().frame("contentFrame");
		WebElement FuncAreaAdd = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnAdd']"));
		FuncAreaAdd.click();
		sleep(5000);

		WebElement FuncTypeAdd = driver.findElement(By.xpath("/html//input[@id='ContentBody_txtFuncAreaTypeName']"));
		FuncTypeAdd.sendKeys("Testing Room");
		sleep(2000);

		WebElement BuildingSubmitFunc = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSubmit']"));
		BuildingSubmitFunc.click();
		sleep(5000);

		WebElement BuildingBackFunc = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnBack']"));
		BuildingBackFunc.click();
		sleep(5000);

		// SELECT RECORD PER PAGE FROM THE DROPDOWN

		Select drpBTypeRecord = new Select(driver.findElement(By.xpath("/html//select[@id='ContentBody_ddlPage']")));
		drpBTypeRecord.selectByVisibleText("30");
		sleep(2000);

		WebElement FuncTypeSearch = driver
				.findElement(By.xpath("/html//input[@id='ContentBody_txtSFuncAreaTypeName']"));
		FuncTypeSearch.sendKeys("Test");
		sleep(2000);

		WebElement FuncAreaSearch = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSearch']"));
		FuncAreaSearch.click();
		sleep(5000);

		// EDIT FUNCTION AREA TYPE

		WebElement ReportSelect = driver.findElement(By.xpath(
				"/html/body/form/div[3]/div/div/div/table/tbody/tr/td/div/div/div[2]/table/tbody/tr/td/div/table/tbody/tr[3]/td[5]/a"));
		ReportSelect.click();
		sleep(5000);

		WebElement BuildingBackFunc1 = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnBack']"));
		BuildingBackFunc1.click();
		sleep(5000);

		// BUILDING FUNCTIONAL AREA

		driver.switchTo().defaultContent();

		// CLICK ON BUILDING FUNCTIONAL AREA
        
		WebElement BuildFuncArea = driver.findElement(
				By.xpath("//div[@id='tree']/ul[@class='dynatree-container']/li[1]/ul/li[4]/span/a[@href='#']"));
		BuildFuncArea.click();
		sleep(5000);

		driver.switchTo().frame("contentFrame");

		Select drpBTypeBuild = new Select(
				driver.findElement(By.xpath("/html//select[@id='ContentBody_ddlSBuildingType']")));
		drpBTypeBuild.selectByVisibleText("TestBuilding2");
		sleep(2000);

		WebElement BuildingFuncSearch = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSearch']"));
		BuildingFuncSearch.click();
		sleep(5000);

		// CLICK ON CHECKBOX

		WebElement BuildFuncCheckbox = driver.findElement(By.xpath("/html//input[@id='ContentBody_chbDeleSearch']"));
		BuildFuncCheckbox.click();
		sleep(2000);

		WebElement BuildingFuncSearch1 = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSearch']"));
		BuildingFuncSearch1.click();
		sleep(5000);

		// RESTORE RECORD
		
		WebElement ReportRestore = driver
				.findElement(By.xpath("/html//a[@id='ContentBody_gvBuildingFuncArea_btnRestoreItem_0']"));
		ReportRestore.click();
		sleep(5000);
	
//     CAPTURING SCREENSHOT	
		
		System.out.println("Capturing ScreenShot");
		captureScreenshot();
	}

	/*
	 * -------------------------------------- LOGOUT AND CLOSE BROWSER----------------------------------------------- */

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
