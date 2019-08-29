package au.com.assethub.app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class BuildingRegisterCams {

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
		 * ------------------------------------------ LOGIN
		 * PAGE----------------------------------------------------------------------
		 */

		// IMPLICITLY WAIT
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		System.out.println("Executing BuildingRegister Test");

//		OPEN CAMS LOGIN PAGE 	
		String url = "https://app.assethub.com.au/";
		driver.get(url);
		System.out.println("Page is opened.");
		sleep(2000);

		/*
		 * ------------------------------- WRITE AND READ FROM EXCEL FILE
		 * ----------------------------------------------------------------
		 */

		File src = new File("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\TestData.xlsx");
		FileInputStream fis = new FileInputStream(src);

		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(fis);

		XSSFSheet Sheet1 = wb.getSheetAt(0);
		XSSFSheet Sheet2 = wb.getSheetAt(1);

		int rowcount = Sheet1.getLastRowNum();

		System.out.println("Total No of Rows are : " + rowcount);

		String Data0 = Sheet1.getRow(0).getCell(1).getStringCellValue();
		String Data1 = Sheet1.getRow(0).getCell(2).getStringCellValue();

//			WRITING IN EXCEL FILE	

		Row row = Sheet2.getRow(1);
		Cell Actcell = row.createCell(7);
		Cell Recell = row.createCell(8);
		// Cell Screenshot = row.createCell(9);
		// cell.setCellValue("Pass");

		// Cell cell1 = row.createCell(3);

		FileOutputStream fos = new FileOutputStream(src);

		// wb.write(fos);

//		ENTER USERNAME
		WebElement username = driver.findElement(By.id("txtUsername"));
		username.sendKeys(Data0);

//		ENTER PASSWORD
		WebElement password = driver.findElement(By.name("txtPassword"));
		password.sendKeys(Data1);

//		CLICK LOGIN BUTTON
		WebElement logInButton = driver.findElement(By.id("cmdLogin"));
		logInButton.click();
		sleep(2000);
		System.out.println("Login Successfull");

		Actcell.setCellValue("As Expected");
		Recell.setCellValue("PASS");

		// Screenshot.setCellValue();

		wb.write(fos);

		/*----------------------------------------- BUILDING REGISTER --------------------------------------------------------------*/

		WebElement BuildingRegister = driver.findElement(By.xpath("//a[text()='Building Register']"));
		BuildingRegister.click();
		// sleep(2000);

		Row row1 = Sheet2.getRow(2);
		Cell Actcell1 = row1.createCell(7);
		Cell Recell1 = row1.createCell(8);

		FileOutputStream fos1 = new FileOutputStream(src);

		Actcell1.setCellValue("As Expected");
		Recell1.setCellValue("PASS");
		wb.write(fos1);

		/*----------------------------------------- BUILDING TYPE  -------------------------------------------------------------------*/

		// CLICK ON BUILDING TYPE LINK
		WebElement BuildingType = driver.findElement(By.xpath("//a[text()='Building Type']"));
		BuildingType.click();
		// sleep(5000);
		System.out.println("Click on Building Type");

		/*---------------------------------------- ADDING NEW BUILDING TYPE --------------------------------------------------------*/

		// SWITCH FRAME
		driver.switchTo().frame("contentFrame");

		// ADD NEW BUILDING TYPE

		WebElement BuildTypeAdd = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnAdd']"));
		BuildTypeAdd.click();
		sleep(2000);

		/*
		 * try { for(int i=1; i<=rowcount ; i++) {
		 * 
		 * 
		 * // READING DATA FROM THE EXCEL FILE
		 * 
		 * String Data0 = Sheet1.getRow(i).getCell(0).getStringCellValue();
		 * System.out.println("Building Type is " + Data0);
		 * 
		 * 
		 * String Data1 = Sheet1.getRow(i).getCell(1).getStringCellValue();
		 * System.out.println("Building Description is :" +Data1);
		 * //System.out.println("Data from the Excel Sheet Row :" +i+ "is" +Data0);
		 */

		// CLICK ON RESET BUTTON

		WebElement BuildTypeReset = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnReset']"));
		BuildTypeReset.click();
		sleep(2000);

		String Data2 = Sheet1.getRow(1).getCell(1).getStringCellValue();
		String Data3 = Sheet1.getRow(1).getCell(2).getStringCellValue();

		// ADDING NEW BUILDING TYPE

		WebElement NewBuildTypeAdd = driver
				.findElement(By.xpath("/html//input[@id='ContentBody_txtBuildingTypeName']"));
		NewBuildTypeAdd.sendKeys(Data2);
		sleep(2000);

		WebElement NewBuildTypeDesc = driver.findElement(By.xpath("/html//textarea[@id='ContentBody_txtDescription']"));
		NewBuildTypeDesc.sendKeys(Data3);

		// CLICK ON SUBMIT BUTTON

		WebElement BuildTypeAddSubmit = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSubmit']"));
		BuildTypeAddSubmit.click();
		sleep(2000);

		/* Get access to HSSFCellStyle */
		// CellStyle style = wb.createCellStyle();
		/* We will now specify a background cell color */

		// style.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		// my_style.setFillForegroundColor(new HSSFColor.BLUE().getIndex());
		// my_style.setFillBackgroundColor(new HSSFColor.RED().getIndex());

		Row row2 = Sheet2.getRow(3);
		Cell Actcell2 = row2.createCell(7);
		Cell Recell2 = row2.createCell(8);

		FileOutputStream fos2 = new FileOutputStream(src);

		Actcell2.setCellValue("As Expected");
		Recell2.setCellValue("PASS");
		// Actcell2.setCellStyle(style);
		wb.write(fos2);

		// fos.close();

		/*
		 * } } catch (Exception e) { // TODO Auto-generated catch block
		 * System.out.println(e.getMessage()); }
		 */

		// fis.close();
		// wb.close();

		// CLICK ON BACK BUTTON

		WebElement BuildTypeBack = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnBack']"));
		BuildTypeBack.click();
		sleep(2000);

		// PAGE RECORDS REPORT

		Select drpPageRecord = new Select(driver.findElement(By.xpath("/html//select[@id='ContentBody_ddlPage']")));
		drpPageRecord.selectByVisibleText("30");
		sleep(2000);

		// WebElement PageRecord =
		// driver.findElement(By.xpath("/html/body/form/div[3]/div/div/div/table/tbody/tr/td/div/div/div[2]/table/tbody/tr/td/div/table/tbody/tr[12]/td/table/tbody/tr/td[2]/a"));
		// PageRecord.click();

		// BUILDING TYPE TEXTBOX IN SEARCH PANEL

		WebElement BuildTypeText1 = driver.findElement(By.xpath("/html//input[@id='ContentBody_txtSBuildingName']"));
		BuildTypeText1.click();
		BuildTypeText1.clear();
		BuildTypeText1.sendKeys("Test");
		sleep(2000);

		// SEARCH BUTTON

		WebElement BuildTypeSearch1 = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSearch']"));
		BuildTypeSearch1.click();
		sleep(2000);

		Row row3 = Sheet2.getRow(4);
		Cell Actcell3 = row3.createCell(7);
		Cell Recell3 = row3.createCell(8);

		FileOutputStream fos3 = new FileOutputStream(src);

		Actcell3.setCellValue("As Expected");
		Recell3.setCellValue("PASS");
		wb.write(fos3);

		// DELETE ALL FUNCTION

		// WebElement DeleteAll =
		// driver.findElement(By.xpath("/html//input[@id='ContentBody_btnDeleteAll']"));
		// DeleteAll.click();

		// SELECT RECORD FROM THE SEARCH REPORT

		List<WebElement> rownum = driver.findElements(By.xpath(
				"/html/body/form/div[3]/div/div/div/table/tbody/tr/td/div/div/div[2]/table/tbody/tr/td/div/table/tbody/tr"));
		System.out.println("Total num of Rows are" + rownum.size());

		List<WebElement> colnum = driver.findElements(By.xpath(
				"/html/body/form/div[3]/div/div/div/table/tbody/tr/td/div/div/div[2]/table/tbody/tr/td/div/table/tbody/tr[2]/td"));
		System.out.println("Total num of Columns are" + colnum.size());

		// for(int rows=1; rows <=rownum.size();rows++) { for(int cols=1; cols
		// <=colnum.size(); cols++) { System.out.println(driver.findElement(By.xpath(
		// "//table[@id='ContentBody_gvBuildingType']/tbody/tr[2]/td")).getText());

		// } }

		WebElement report = driver.findElement(By.xpath(
				"/html/body/form/div[3]/div/div/div/table/tbody/tr/td/div/div/div[2]/table/tbody/tr/td/div/table/tbody/tr[2]/td[5]/a"));
		report.click();
		sleep(2000);

		Row row4 = Sheet2.getRow(5);
		Cell Actcell4 = row4.createCell(7);
		Cell Recell4 = row4.createCell(8);

		FileOutputStream fos4 = new FileOutputStream(src);

		Actcell4.setCellValue("As Expected");
		Recell4.setCellValue("PASS");
		wb.write(fos4);

		// CLICK ON BACK BUTTON
		WebElement BuildTypeBack1 = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnBack']"));
		BuildTypeBack1.click(); // sleep(2000);

		// BUILDING TYPE TEXTBOX IN SEARCH PANEL
		WebElement BuildTypeText2 = driver.findElement(By.xpath("/html//input[@id='ContentBody_txtSBuildingName']"));
		BuildTypeText2.click();
		BuildTypeText2.clear();
		BuildTypeText2.sendKeys("Test");
		sleep(2000);

		// SEARCH BUTTON
		WebElement BuildTypeSearch2 = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSearch']"));
		BuildTypeSearch2.click();
		sleep(2000);

		// CLICK ON DOWNLOAD BUTTON

		WebElement BuildTypeDownload = driver
				.findElement(By.xpath("/html//table[@id='ContentBody_tblPaging']//div[@title='Export Excel']"));
		BuildTypeDownload.click();
		sleep(3000);
		System.out.println("Capturing ScreenShot");
		captureScreenshot();

		Row row5 = Sheet2.getRow(6);
		Cell Actcell5 = row5.createCell(7);
		Cell Recell5 = row5.createCell(8);

		FileOutputStream fos5 = new FileOutputStream(src);

		Actcell5.setCellValue("As Expected");
		Recell5.setCellValue("PASS");
		wb.write(fos5);

		/*
		 * //DELETE RECORD WebElement report1 =
		 * driver.findElement(By.xpath("/html//input[@id='ContentBody_btnDeleteAll']"));
		 * report1.click(); sleep(5000);
		 */

		/* IMPORT BUILDING TYPE USING EXCEL FILE */

		driver.switchTo().defaultContent();

		// CLICK ON BUILDING TYPE LINK
		WebElement BuildingImport = driver.findElement(By.xpath(
				"//div[@id='tree']/ul[@class='dynatree-container']/li[1]/ul/li[@class='dynatree-lastsib']/ul/li[1]/span/a[@href='#']"));
		BuildingImport.click();
		sleep(2000);

		Row rowImp1 = Sheet2.getRow(7);
		Cell ActcellImp1 = rowImp1.createCell(7);
		Cell RecellImp1 = rowImp1.createCell(8);

		FileOutputStream fosImp1 = new FileOutputStream(src);

		ActcellImp1.setCellValue("As Expected");
		RecellImp1.setCellValue("PASS");
		wb.write(fosImp1);

		driver.switchTo().frame("contentFrame");

		// CLICK ON upload button
		WebElement BuildingImport1 = driver.findElement(By.xpath("//*[@id=\'ContentBody_fuBuildingTypes\']"));
		// BuildingImport1.click();
		sleep(2000);

		System.out.println("Click on Import link");

		// WebElement ImportText =
		// driver.findElement(By.xpath("//*[@id=\'ContentBody_vceFUBuildingTypes_ClientState\']"));
		// ImportText.sendKeys("C:\\Users\\devan\\OneDrive\\SELENIUM\\DOCS\\BuildingType.xlsx");

		// driver.switchTo().alert().sendKeys("C:\\Users\\devan\\OneDrive\\SELENIUM\\DOCS\\BuildingType.xlsx");
		// driver.switchTo().alert().accept();

		BuildingImport1.sendKeys("C:\\Users\\devan\\OneDrive\\SELENIUM\\DOCS\\BuildingType.xlsx");
		sleep(2000);
		System.out.println("Click on upload button box");

		// CLICK ON BUILDING TYPE LINK
		WebElement BuildingImport2 = driver
				.findElement(By.xpath("/html//input[@id='ContentBody_btnUploadBuildingTypes']"));
		BuildingImport2.click();
		sleep(2000);
		System.out.println("Click on upload building type");

		Row rowImp = Sheet2.getRow(8);
		Cell ActcellImp = rowImp.createCell(7);
		Cell RecellImp = rowImp.createCell(8);

		FileOutputStream fosImp = new FileOutputStream(src);

		ActcellImp.setCellValue("As Expected");
		RecellImp.setCellValue("PASS");
		wb.write(fosImp);

//		     CAPTURING SCREENSHOT		
		captureScreenshot();

		/*------------------------------------- BUILDING  ----------------------------------------------------------------------------------------*/

		driver.switchTo().defaultContent();

		// CLICK ON BUILDING LINK
		WebElement BuildingLink = driver.findElement(
				By.xpath("//div[@id='tree']/ul[@class='dynatree-container']/li[1]/ul/li[2]/span/a[@href='#']"));
		BuildingLink.click();
		sleep(5000);

		// CLICK ON ADD NEW BUILDING
		driver.switchTo().frame("contentFrame");

		JavascriptExecutor js = (JavascriptExecutor) driver;

		// SELECT RECORD PER PAGE FROM THE DROPDOWN

		Select drpBTypeRecord1 = new Select(driver.findElement(By.xpath("/html//select[@id='ContentBody_ddlPage']")));
		drpBTypeRecord1.selectByVisibleText("30");
		sleep(3000);

		// This will scroll the web page till end.
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		sleep(5000);

		Select drpBTypeRecord2 = new Select(driver.findElement(By.xpath("/html//select[@id='ContentBody_ddlPage']")));
		drpBTypeRecord2.selectByVisibleText("50");
		sleep(3000);

		Row rowPR = Sheet2.getRow(10);
		Cell ActcellPR = rowPR.createCell(7);
		Cell RecellPR = rowPR.createCell(8);

		FileOutputStream fosPR = new FileOutputStream(src);

		ActcellPR.setCellValue("As Expected");
		RecellPR.setCellValue("PASS");
		wb.write(fosPR);

		// This will scroll the web page till end.
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		sleep(5000);

		System.out.println("Adding New Building");
		WebElement BuildingAdd = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnAdd']"));
		BuildingAdd.click();
		sleep(5000);

		// ADDING NEW BUILDING

		String Data6 = Sheet1.getRow(2).getCell(1).getStringCellValue();
		String Data7 = Sheet1.getRow(2).getCell(2).getStringCellValue();
		String Data8 = Sheet1.getRow(2).getCell(3).getStringCellValue();

		// SELECTING BUILDING TYPE FROM DROPDOWN

		Select drpBType = new Select(driver.findElement(By.id("ContentBody_ddlBuildingType")));
		drpBType.selectByVisibleText(Data6);
		// sleep(3000);

		WebElement BuildText = driver.findElement(By.xpath("/html//input[@id='ContentBody_txtBuildingName']"));
		BuildText.sendKeys(Data7);
		// sleep(2000);

		WebElement AssetText = driver.findElement(By.xpath("/html//input[@id='ContentBody_txtBuildingCode']"));
		AssetText.sendKeys(Data8);
		// sleep(2000);

		WebElement BuildingSubmit = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSubmit']"));
		BuildingSubmit.click();
		sleep(2000);

		Row row7 = Sheet2.getRow(9);
		Cell Actcell7 = row7.createCell(7);
		Cell Recell7 = row7.createCell(8);

		FileOutputStream fos7 = new FileOutputStream(src);

		Actcell7.setCellValue("As Expected");
		Recell7.setCellValue("PASS");
		wb.write(fos7);

		WebElement BuildingBack = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnBack']"));
		BuildingBack.click();
		sleep(2000);

		// BUILDING REPORT

		String DataBT = Sheet1.getRow(2).getCell(1).getStringCellValue();
		// String DataBl = Sheet1.getRow(1).getCell(2).getStringCellValue();

		Select drpBType1 = new Select(
				driver.findElement(By.xpath("/html//select[@id='ContentBody_ddlSBuildingType']")));
		drpBType1.selectByVisibleText(DataBT);
		sleep(3000);

		WebElement BuildingSearch = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSearch']"));
		BuildingSearch.click();
		sleep(2000);

		Row row8 = Sheet2.getRow(11);
		Cell Actcell8 = row8.createCell(7);
		Cell Recell8 = row8.createCell(8);

		FileOutputStream fos8 = new FileOutputStream(src);

		Actcell8.setCellValue("As Expected");
		Recell8.setCellValue("PASS");
		wb.write(fos8);

		// EXPORT BUILDING REPORT DATA

		WebElement BuildTypeDownload1 = driver
				.findElement(By.xpath("/html//table[@id='ContentBody_tblPaging']//div[@title='Export Excel']"));
		BuildTypeDownload1.click();
		sleep(3000);
		System.out.println("Capturing ScreenShot");
		captureScreenshot();

		Row rowEXP = Sheet2.getRow(12);
		Cell ActcellEXP = rowEXP.createCell(7);
		Cell RecellEXP = rowEXP.createCell(8);

		FileOutputStream fosEXP = new FileOutputStream(src);

		ActcellEXP.setCellValue("As Expected");
		RecellEXP.setCellValue("PASS");
		wb.write(fosEXP);

		/*
		 * // SELECT RECORD FROM THE REPORT
		 * 
		 * WebElement report12 = driver .findElement(By.xpath(
		 * "//table[@id='ContentBody_gvBuilding']/tbody/tr[2]//a[@title='Select']"));
		 * report12.click(); sleep(5000);
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
		 * Row row9 = Sheet2.getRow(13); Cell Actcell9 = row9.createCell(7); Cell
		 * Recell9 = row9.createCell(8);
		 * 
		 * FileOutputStream fos9 = new FileOutputStream(src);
		 * 
		 * Actcell9.setCellValue("As Expected"); Recell9.setCellValue("PASS");
		 * wb.write(fos9);
		 */

		/*
		 * --------------------------------------- FUNCTION AREA TYPE
		 * --------------------------------------------------------------------------
		 */

		// FUNCTIONAL AREA TYPE

		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.switchTo().defaultContent();

		// CLICK ON FUNCTIONAL AREA TYPE LINk
		WebElement FuncArea1 = driver.findElement(
				By.xpath("//div[@id='tree']/ul[@class='dynatree-container']/li[1]/ul/li[3]/span/a[@href='#']"));
		FuncArea1.click();
		sleep(2000);

		// ADDING NEW FUNCTIONAL AREA TYPE

		driver.switchTo().frame("contentFrame");
		WebElement FuncAreaAdd = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnAdd']"));
		FuncAreaAdd.click();
		sleep(2000);

		String DataFunc = Sheet1.getRow(3).getCell(1).getStringCellValue();
		// String DataFunc1 = Sheet1.getRow(3).getCell(2).getStringCellValue();
		// String DataFunc2 = Sheet1.getRow(3).getCell(3).getStringCellValue();

		WebElement FuncTypeAdd = driver.findElement(By.xpath("/html//input[@id='ContentBody_txtFuncAreaTypeName']"));
		FuncTypeAdd.sendKeys(DataFunc);
		sleep(2000);

		WebElement BuildingSubmitFunc = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSubmit']"));
		BuildingSubmitFunc.click();
		sleep(3000);

		Row rowFunc = Sheet2.getRow(15);
		Cell ActcellFunc = rowFunc.createCell(7);
		Cell RecellFunc = rowFunc.createCell(8);

		FileOutputStream fosFunc = new FileOutputStream(src);

		ActcellFunc.setCellValue("As Expected");
		RecellFunc.setCellValue("PASS");
		wb.write(fosFunc);

		WebElement BuildingBackFunc = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnBack']"));
		BuildingBackFunc.click();
		sleep(3000);

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
		sleep(2000);

		Row rowFunc1 = Sheet2.getRow(16);
		Cell ActcellFunc1 = rowFunc1.createCell(7);
		Cell RecellFunc1 = rowFunc1.createCell(8);

		FileOutputStream fosFunc1 = new FileOutputStream(src);

		ActcellFunc1.setCellValue("As Expected");
		RecellFunc1.setCellValue("PASS");
		wb.write(fosFunc1);

		// EDIT FUNCTION AREA TYPE

		WebElement ReportSelect = driver.findElement(By.xpath(
				"/html/body/form/div[3]/div/div/div/table/tbody/tr/td/div/div/div[2]/table/tbody/tr/td/div/table/tbody/tr[3]/td[5]/a"));
		ReportSelect.click();
		sleep(2000);

		WebElement BuildingBackFunc1 = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnBack']"));
		BuildingBackFunc1.click();
		sleep(2000);

		Row rowFunc2 = Sheet2.getRow(17);
		Cell ActcellFunc2 = rowFunc2.createCell(7);
		Cell RecellFunc2 = rowFunc2.createCell(8);

		FileOutputStream fosFunc2 = new FileOutputStream(src);

		ActcellFunc2.setCellValue("As Expected");
		RecellFunc2.setCellValue("PASS");
		wb.write(fosFunc2);

		/*-------------------------  BUILDING FUNCTIONAL AREA --------------------------------------------------------------*/

		// BUILDING FUNCTIONAL AREA

		driver.switchTo().defaultContent();

		// CLICK ON BUILDING FUNCTIONAL AREA

		WebElement BuildFuncArea = driver.findElement(
				By.xpath("//div[@id='tree']/ul[@class='dynatree-container']/li[1]/ul/li[4]/span/a[@href='#']"));
		BuildFuncArea.click();
		sleep(2000);

		driver.switchTo().frame("contentFrame");

		Select drpBTypeBuild = new Select(
				driver.findElement(By.xpath("/html//select[@id='ContentBody_ddlSBuildingType']")));
		drpBTypeBuild.selectByVisibleText("TestBuilding2");
		sleep(2000);

		WebElement BuildingFuncSearch = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSearch']"));
		BuildingFuncSearch.click();
		sleep(2000);

		Row rowBFunc = Sheet2.getRow(18);
		Cell ActcellBFunc = rowBFunc.createCell(7);
		Cell RecellBfunc = rowBFunc.createCell(8);

		FileOutputStream fosBFunc = new FileOutputStream(src);

		ActcellBFunc.setCellValue("As Expected");
		RecellBfunc.setCellValue("PASS");
		wb.write(fosBFunc);

		// CLICK ON CHECKBOX

		WebElement BuildFuncCheckbox = driver.findElement(By.xpath("/html//input[@id='ContentBody_chbDeleSearch']"));
		BuildFuncCheckbox.click();
		sleep(2000);

		WebElement BuildingFuncSearch1 = driver.findElement(By.xpath("/html//input[@id='ContentBody_btnSearch']"));
		BuildingFuncSearch1.click();
		sleep(2000);

		Row rowBFunc1 = Sheet2.getRow(19);
		Cell ActcellBFunc1 = rowBFunc1.createCell(7);
		Cell RecellBfunc1 = rowBFunc1.createCell(8);

		FileOutputStream fosBFunc1 = new FileOutputStream(src);

		ActcellBFunc1.setCellValue("As Expected");
		RecellBfunc1.setCellValue("PASS");
		wb.write(fosBFunc1);

		// RESTORE RECORD

		WebElement ReportRestore = driver
				.findElement(By.xpath("/html//a[@id='ContentBody_gvBuildingFuncArea_btnRestoreItem_0']"));
		ReportRestore.click();
		sleep(2000);

//     CAPTURING SCREENSHOT	

		System.out.println("Capturing ScreenShot");
		captureScreenshot();
	}

	/*
	 * -------------------------------------- LOGOUT AND CLOSE
	 * BROWSER-----------------------------------------------
	 */

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
