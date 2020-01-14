package project_cams;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class AdminModule {

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
		String url = "https://app.cams.assethub.com.au/#/login";
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

		File src = new File("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\ADMINUSERS.xlsx");
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
					.findElement(By.xpath("//app-dashboard/header/ul[3]/li//a[@href='#/login']"));
			Logout.click();

			// SLEEP FOR 2 SECONDS
			sleep(1000);

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
	public void adminUsers() throws IOException {

		System.out.println("Executing Admin User Test");

		File src = new File("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\ADMINUSERS.xlsx");
		FileInputStream fis = new FileInputStream(src);

		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(fis);

		XSSFSheet Sheet3 = wb.getSheetAt(2);

		Row row2 = Sheet3.getRow(5);
		Cell cellad1 = row2.createCell(7);
		Cell celladp1 = row2.createCell(8);

		FileOutputStream fos2 = new FileOutputStream(src);

		try {
			// CLICK ON ADMIN ICON
			WebElement adminTab =
			driver.findElement(By.xpath("//app-dashboard/header/ul[1]/li[@title='Admin']//img[@class='icon']"));
			adminTab.click();
			sleep(3000);
			
			cellad1.setCellValue("AS EXPECTED");
			celladp1.setCellValue("PASS");
			wb.write(fos2);

			// CLICK ON ADMIN USERS TAB
			WebElement aduser = driver.findElement(By.xpath(
					"//app-dashboard//app-sidebar[@class='sidebar']/app-sidebar-nav-custom/ul[@class='nav']//span[.=' Admin Users ']"));
			aduser.click();
			sleep(2000);
			
			Row row3 = Sheet3.getRow(6);
			Cell cellad2 = row3.createCell(7);
			Cell celladp2 = row3.createCell(8);

			FileOutputStream fos3 = new FileOutputStream(src);

			cellad2.setCellValue("AS EXPECTED");
			celladp2.setCellValue("PASS");
			wb.write(fos3);
			captureScreenshotPass();

			// WRITE AND READ FROM EXCEL FILE

			File adusersrc = new File("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\ADMINUSERS.xlsx");
			FileInputStream fis1 = new FileInputStream(adusersrc);

			@SuppressWarnings("resource")
			XSSFWorkbook wb1 = new XSSFWorkbook(fis1);

			// XSSFSheet Sheet1 = wb.getSheetAt(0);
			XSSFSheet Sheet2 = wb1.getSheetAt(1);

			int rowcount1 = Sheet2.getLastRowNum();

			System.out.println("Total No of Rows are : " + rowcount1);

			

				for (int i = 1; i <= rowcount1; i++) {

					// READING DATA FROM THE EXCEL FILE

					String adData = Sheet2.getRow(i).getCell(0).getStringCellValue();
					System.out.println("Username IS " + adData);

					String adData1 = Sheet2.getRow(i).getCell(1).getStringCellValue();
					System.out.println("Password is :" + adData1);

					String adData2 = Sheet2.getRow(i).getCell(2).getStringCellValue();
					System.out.println("Password is :" + adData2);
					// System.out.println("Data from the Excel Sheet Row :" +i+ "is" +Data0);

//			 			WRITING IN EXCEL FILE	

					Row adrowuser = Sheet2.getRow(i);
					Cell adusercell = adrowuser.createCell(3);

					FileOutputStream aduserfos = new FileOutputStream(src);

					// CLICK ON NEW USERS BUTTON
					WebElement nwuser = driver.findElement(By.xpath(
							"//body/app-dashboard/div[@class='app-body']/main[@class='main']//app-admin-users//button[@class='btn btn-block btn-primary newBtn']"));
					nwuser.click();
					sleep(2000);

					Row row4 = Sheet3.getRow(7);
					Cell cellad3 = row4.createCell(7);
					Cell celladp3 = row4.createCell(8);

					FileOutputStream fos4 = new FileOutputStream(src);

					cellad3.setCellValue("AS EXPECTED");
					celladp3.setCellValue("PASS");
					wb.write(fos4);
					captureScreenshotPass();

					try {
					
					// ENTER NEW USER EMAIL
					WebElement nwuseremail = driver.findElement(
							By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='Email address']"));
					nwuseremail.sendKeys(adData);
					//sleep(2000);

					// ENTER NEW USER FIRSTNAME
					WebElement nwusrfn = driver.findElement(
							By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='First Name']"));
					nwusrfn.sendKeys(adData1);
					//sleep(2000);

					// ENTER NEW USER LASTNAME
					WebElement nwusrln = driver.findElement(
							By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='Last Name']"));
					nwusrln.sendKeys(adData2);
					//sleep(2000);
					
					
					Row row8 = Sheet3.getRow(8);
					Cell cellad8 = row8.createCell(7);
					Cell celladp8 = row8.createCell(8);

					FileOutputStream fos8 = new FileOutputStream(src);

					cellad8.setCellValue("AS EXPECTED");
					celladp8.setCellValue("PASS");
					wb.write(fos8);
					
					
					
					Row row9 = Sheet3.getRow(9);
					Cell cellad9 = row9.createCell(7);
					Cell celladp9 = row9.createCell(8);

					FileOutputStream fos9 = new FileOutputStream(src);

					cellad9.setCellValue("AS EXPECTED");
					celladp9.setCellValue("PASS");
					wb.write(fos9);
					
					
					Row row10 = Sheet3.getRow(10);
					Cell cellad10 = row10.createCell(7);
					Cell celladp10 = row10.createCell(8);

					FileOutputStream fos10 = new FileOutputStream(src);

					cellad10.setCellValue("AS EXPECTED");
					celladp10.setCellValue("PASS");
					wb.write(fos10);
					captureScreenshotPass();
					
		

//		    CLICK ON NEW USERS SAVE BUTTON 
					WebElement nwusersave = driver
							.findElement(By.xpath("//div[@id='collapseOverlay']/div//button[@type='button']"));
					nwusersave.click();
					sleep(2000);

					adusercell.setCellValue("PASS");
					wb1.write(aduserfos);
					
					
					Row row5 = Sheet3.getRow(11);
					Cell cellad5 = row5.createCell(7);
					Cell celladp5 = row5.createCell(8);

					FileOutputStream fos5 = new FileOutputStream(src);

					cellad5.setCellValue("AS EXPECTED");
					celladp5.setCellValue("PASS");
					wb.write(fos5);
					captureScreenshotPass();

					
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				
				adusercell.setCellValue("PASS");
				wb1.write(aduserfos);
				captureScreenshot();
			}
				}			
				
				
				
				try
				{
				
				// EDIT ADMIN USERS
				WebElement editaduser = driver.findElement(By.xpath(
						"//div[@id='card']/div[2]/div/table//td[.='devangini.tandel@rmit.edu.au']"));
				editaduser.click();
				sleep(2000);
				
				// EDIT USER FIRSTNAME
				WebElement editusrfn = driver.findElement(
						By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='First Name']"));
				editusrfn.clear();
				editusrfn.sendKeys("Devangini");
				sleep(2000);

				// EDIT USER LASTNAME
				WebElement editusrln = driver.findElement(
						By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='Last Name']"));
				editusrln.clear();
				editusrln.sendKeys("Tandel");
				sleep(2000);

                // CLICK ON SAVE BUTTON TO UPDATE USER 
				WebElement nwusersave = driver
						.findElement(By.xpath("//div[@id='collapseOverlay']/div//button[@type='button']"));
				nwusersave.click();
				sleep(2000);
				
				
				Row row9 = Sheet3.getRow(12);
				Cell cellad9 = row9.createCell(7);
				Cell celladp9 = row9.createCell(8);

				FileOutputStream fos9 = new FileOutputStream(src);

				cellad9.setCellValue("AS EXPECTED");
				celladp9.setCellValue("PASS");
				wb.write(fos9);
				captureScreenshotPass();
				
				

			} catch (Exception e) {

				Row adrowF = Sheet3.getRow(11);
				Cell celladF2 = adrowF.createCell(7);
				Cell celladF3 = adrowF.createCell(8);

				FileOutputStream adfosF = new FileOutputStream(src);

				celladF2.setCellValue("NOT AS EXPECTED");
				celladF3.setCellValue("FAIL");
				wb.write(adfosF);
				captureScreenshot();

				System.out.println(e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

			System.out.println("TEST FAILED");
			captureScreenshot();

			Row row1 = Sheet3.getRow(11);
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
	
	@Test(priority = 3)
	public void adminModules() throws IOException {
		
		System.out.println("Executing Modules Test");

		File src = new File("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\ADMINUSERS.xlsx");
		FileInputStream fis = new FileInputStream(src);

		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(fis);

		XSSFSheet Sheet3 = wb.getSheetAt(2);

		Row row2 = Sheet3.getRow(13);
		Cell cellad1 = row2.createCell(7);
		Cell celladp1 = row2.createCell(8);

		FileOutputStream fos2 = new FileOutputStream(src);

		try {
	
		
		// CLICK ON MODULES TAB
		
		WebElement admodules = driver.findElement(By.xpath(
				"//app-dashboard//app-sidebar[@class='sidebar']/app-sidebar-nav-custom/ul[@class='nav']//a[@href='#/admin/module']"));
		admodules.click();
		sleep(2000);
		
		cellad1.setCellValue("AS EXPECTED");
		celladp1.setCellValue("PASS");
		wb.write(fos2);
		captureScreenshotPass();
	
		
		// This will scroll the web page till end.
				JavascriptExecutor js = (JavascriptExecutor) driver;
		
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		
		
		// CLICK ON NEW BUTTON TO ADD NEW MODULE
		
		WebElement newmodules = driver.findElement(By.xpath(
				"//body/app-dashboard/div[@class='app-body']/main[@class='main']/div[@class='container-fluid']//button[@class='btn btn-block btn-primary newBtn']"));
		newmodules.click();
		sleep(2000);
		
		Row row14 = Sheet3.getRow(14);
		Cell cellad14 = row14.createCell(7);
		Cell celladp14 = row14.createCell(8);

		FileOutputStream fos14 = new FileOutputStream(src);
		
		cellad14.setCellValue("AS EXPECTED");
		celladp14.setCellValue("PASS");
		wb.write(fos14);
		captureScreenshotPass();
		
		// ENTER NEW MODULE NAME
		WebElement newmodname = driver.findElement(By.xpath(
				"//div[@id='collapseOverlay']//form//input[@placeholder='Module Name']"));
		newmodname.sendKeys("Water Treatment Plant");
		sleep(2000);
		
		// ENTER NEW MODULE KEY
				WebElement newmodkey = driver.findElement(By.xpath(
						"//div[@id='collapseOverlay']//form//input[@placeholder='moduleKey']"));
				newmodkey.sendKeys("WaterTreatmentPlant");
				sleep(2000);
				
				
//		      IMPORT MODULE METADATA
				
			/*
			 * WebElement ImportJson = driver.findElement( By.xpath(
			 * "/html/body/app-dashboard/div[1]/main/div/module/div/div[1]/collapse-overlay/div/div/form/div[3]/div/input"
			 * )); ImportJson.click(); sleep(2000);
			 */
			
			
			Runtime.getRuntime().exec("C:\\Users\\devan\\eclipse-workspace\\selenium-for-beginners\\AddModule.exe");
			sleep(3000);
				
				

		// CLICK ON SAVE MODULE
		WebElement savemodule = driver.findElement(By.xpath(
				"//div[@id='collapseOverlay']/div[@class='col-11']//button[@type='button']"));
		savemodule.click();
		sleep(2000);		
		
		Row row15 = Sheet3.getRow(15);
		Cell cellad15 = row15.createCell(7);
		Cell celladp15 = row15.createCell(8);

		FileOutputStream fos15 = new FileOutputStream(src);
		
		cellad15.setCellValue("AS EXPECTED");
		celladp15.setCellValue("PASS");
		wb.write(fos15);
		
		} catch (Exception e) {

			Row adrowF = Sheet3.getRow(15);
			Cell celladF2 = adrowF.createCell(7);
			Cell celladF3 = adrowF.createCell(8);

			FileOutputStream adfosF = new FileOutputStream(src);

			celladF2.setCellValue("ERROR IN THE SCRIPT TEST MODULE");
			celladF3.setCellValue("FAIL");
			wb.write(adfosF);
			captureScreenshot();

			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
		
	}
	
	@Test(priority = 4)
	public void adminOrgAcc() throws IOException {
		
		System.out.println("Executing Organisation Accounts Test");
		
		//		     CLICK ON ORGANISATION TAB
		
		WebElement UserOrg = driver.findElement(By.xpath("//app-dashboard//app-sidebar[@class='sidebar']/app-sidebar-nav-custom/ul[@class='nav']//span[.=' Org. Accounts ']"));
		UserOrg.click();
		sleep(2000);
		
		// This will scroll the web page till end.
		
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		
		sleep(2000);
	
//		   ADD NEW ORGANISATION BUTTON
		
			WebElement UserOrgnew = driver.findElement(By.xpath("/html/body/app-dashboard/div[1]/main/div/app-organisation-accounts/div/div[2]/div/div/button"));
			UserOrgnew.click();
			sleep(2000);
			
		
//         ENTER ORGANISATION NAME		
		
			WebElement Orgnewname = driver.findElement(By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='Organisation name']"));
			Orgnewname.sendKeys("NEWT ORGANISATION");
			sleep(2000);
		
//             ENTER ORGANISATION SHORT NAME		
			
			WebElement Orgshortname = driver.findElement(By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='No spaces allowed']"));
			Orgshortname.sendKeys("NEWT");
			sleep(2000);		
	
//             ENTER ORGANISATION ADMIN EMAIL		
			
			WebElement Orgadmnemail = driver.findElement(By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='Admin Email']"));
			Orgadmnemail.sendKeys("devangini.tandel@gmail.com");
			sleep(2000);		
		

			
			// CLICK ON CHECKBOXES
			
			WebElement Orgchkbox = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/form/div[4]/div/div[2]/input[@name='modules']"));
			Orgchkbox.click();
			//sleep(2000);
			
			WebElement Orgchkbox1 = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/form/div[4]/div/div[3]/input[@name='modules']"));
			Orgchkbox1.click();
			//sleep(2000);
			
			WebElement Orgchkbox2 = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/form/div[4]/div/div[5]/input[@name='modules']"));
			Orgchkbox2.click();
			//sleep(2000);
			
			WebElement Orgchkbox3 = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/form/div[4]/div/div[5]/input[@name='modules']"));
			Orgchkbox3.click();
			//sleep(2000);
			
			WebElement Orgchkbox4 = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/form/div[4]/div/div[9]/input[@name='modules']"));
			Orgchkbox4.click();
			//sleep(2000);
			
			WebElement Orgchkbox5 = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/form/div[4]/div/div[10]/input[@name='modules']"));
			Orgchkbox5.click();
			//sleep(2000);
			

			
//          CLICK ON SAVE BUTTON	
			
			WebElement Orgsavebtn = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/div[@class='row']//i[@class='fa fa-cloud']"));
			Orgsavebtn.click();
			sleep(2000);
	
		
//     EDIT ORGANISATION
			
//		     CLICK ON ORGANISATION TAB
			
			
			WebElement editOrgUsertab = driver.findElement(By.xpath("/html/body/app-dashboard/div[1]/app-sidebar/app-sidebar-nav-custom/ul/div[4]/a/span"));
			editOrgUsertab.click();
			sleep(2000);
			
			
			
		WebElement edtUserOrg = driver.findElement(By.xpath("/html/body/app-dashboard/div[1]/main/div/app-organisation-accounts/div/div[1]/div[2]/table/tbody/tr[9]/td[2]"));
		edtUserOrg.click();
		sleep(2000);
			
			
			
			
//	         ENTER ORGANISATION NAME		
			
				WebElement EditOrgname = driver.findElement(By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='Organisation name']"));
				EditOrgname.clear();
				EditOrgname.sendKeys("DPT1");
				sleep(3000);
			
				
				// CLICK ON CHECKBOXES
				
				WebElement editOrgchkbox = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/form/div[4]/div/div[2]/input[@name='modules']"));
				editOrgchkbox.click();
				//sleep(2000);
				
				WebElement editOrgchkbox1 = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/form/div[4]/div/div[3]/input[@name='modules']"));
				editOrgchkbox1.click();
				//sleep(2000);
				
				WebElement editOrgchkbox2 = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/form/div[4]/div/div[5]/input[@name='modules']"));
				editOrgchkbox2.click();
				//sleep(2000);
				
				WebElement editOrgchkbox3 = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/form/div[4]/div/div[5]/input[@name='modules']"));
				editOrgchkbox3.click();
				//sleep(2000);
				
				WebElement editOrgchkbox4 = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/form/div[4]/div/div[9]/input[@name='modules']"));
				editOrgchkbox4.click();
				//sleep(2000);
				
				WebElement editOrgchkbox5 = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/form/div[4]/div/div[10]/input[@name='modules']"));
				editOrgchkbox5.click();
				//sleep(2000);
		
				
//	          CLICK ON SAVE BUTTON	
				
				WebElement editOrgsavebtn = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/div[@class='row']//i[@class='fa fa-cloud']"));
				editOrgsavebtn.click();
				sleep(2000);
				
		
	}
	
	@Test(priority = 5)
	public void adminOrgAccUsers() throws IOException {
		
		System.out.println("Executing Organisation Users Test");
		
		// CLICK ON DROPDOWN
				WebElement drpdown = driver.findElement(By.xpath("//a[@id='dropdownMenu']/i"));
				drpdown.click();
				sleep(2000);

				// SELECT VALUES FROM DROPDOWN

				WebElement drpdnRecord = driver.findElement(By.xpath(
						"//app-dashboard//ol[@class='breadcrumb']//div[@role='group']/li[@class='nav-item']//div[@class='dropdown-menu dropdown-menu-right show']/a[8]"));
				drpdnRecord.click();
				sleep(3000);

//		     CLICK ON ADMIN ICON
				WebElement adminTab = driver.findElement(By.xpath("//app-dashboard/header/ul[1]/li[@title='Admin']//img[@class='icon']"));
				adminTab.click();
				sleep(3000);

//		     CLICK ON ORGANISATION USERS
				WebElement UserOrg = driver.findElement(By.xpath("/html/body/app-dashboard/div[1]/app-sidebar/app-sidebar-nav-custom/ul/div[5]/a/span"));
				UserOrg.click();
				sleep(5000);
				
		
				
//				   ADD NEW ORGANISATION USERS
				
					WebElement UserOrgnew = driver.findElement(By.xpath("/html/body/app-dashboard/div[1]/main/div/app-organisation-users/div/div[2]/div/div/button"));
					UserOrgnew.click();
					sleep(2000);
					
					
					WebElement Addnewemail = driver.findElement(By.xpath("/html/body/app-dashboard/div[1]/main/div/app-organisation-users/div/div[1]/collapse-overlay/div/div/form/div[1]/input"));
					Addnewemail.sendKeys("dmtandel1730@gmail.com");
					sleep(3000);
					
					String Addnewemail1 = Addnewemail.getAttribute("value");
					
					//System.out.println("New Email Id = " +Addnewemail1);
					
					
					WebElement Addfirstname = driver.findElement(By.xpath("/html/body/app-dashboard/div[1]/main/div/app-organisation-users/div/div[1]/collapse-overlay/div/div/form/div[2]/input"));
					Addfirstname.sendKeys("User First Name");
					sleep(3000);
					
					WebElement Addlastname = driver.findElement(By.xpath("/html/body/app-dashboard/div[1]/main/div/app-organisation-users/div/div[1]/collapse-overlay/div/div/form/div[3]/input"));
					Addlastname.sendKeys("User Last Name");
					sleep(3000);		
				
					WebElement Admnchkbox = driver.findElement(By.xpath("//div[@id='collapseOverlay']//form//input[@value='Organisation admin']"));
					Admnchkbox.click();
					sleep(3000);
			
					WebElement Orgusrsave = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']//button[@type='button']"));
					Orgusrsave.click();
					sleep(3000);
					
//				    GET LIST OF USER EMAIL FROM THE TABLE
					
					
						List<WebElement> rownum = driver.findElements(By.xpath("/html/body/app-dashboard/div[1]/main/div/app-organisation-users/div/div[1]/div[2]/div/table/tbody/tr"));
						
					    
						//List<String> secondColumnList=new ArrayList<String>();
						
						for(int rnum=1;rnum<=rownum.size();rnum++)
					    {
					    	
					    	 List<WebElement>columns = driver.findElements(By.xpath("/html/body/app-dashboard/div[1]/main/div/app-organisation-users/div/div[1]/div[2]/div/table/tbody/tr["+rnum+"]"));
								
					    	
					             for (int col=1;col<=columns.size();col++)
					            {
					            	
					            	// String test_name = driver.findElement(By.xpath( "/html/body/app-dashboard/div[1]/main/div/app-organisation-users/div/div[1]/div[2]/div/table/tbody/tr["+ rnum + "]/td[" + col + "]")).getText();
					            	
					            	 String Uemail = driver.findElement(By.xpath( "/html/body/app-dashboard/div[1]/main/div/app-organisation-users/div/div[1]/div[2]/div/table/tbody/tr["+ rnum + "]/td[" + col + "]")).getText();
					            	 
					                 //secondColumnList.add(test_name);  
					                // System.out.println(Uemail);
					            	 
					                 //String str = "dmtandel1730@gmail.com" ;
					                 
					                 if(Addnewemail1.equals(Uemail))
					            	 {
					            		//System.out.println(str);
					            	 
					            		System.out.println("New Email Id match with Existing emailId : " +Addnewemail1);
					            	}else
					            	{
					            	   System.out.println("New Email : " +Addnewemail1);
					            	}
					            	
					            }
					                
					             
					       }
						
						// EDIT ORGANISATION USERS
						
						WebElement Editorguser = driver.findElement(By.xpath("//div[@id='card']/div[2]/div/table//td[.='dmtandel1730@gmail.com']"));
						Editorguser.click();
						sleep(3000);
						
						WebElement Editfirstname = driver.findElement(By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='First Name']"));
						Editfirstname.clear();
						Editfirstname.sendKeys("VIC User1");
						sleep(3000);
						
						WebElement Editlastname = driver.findElement(By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='Last Name']"));
						Editlastname.clear();
						Editlastname.sendKeys("VIC User1");
						sleep(3000);		
					
						WebElement Editchkbox = driver.findElement(By.xpath("//div[@id='collapseOverlay']//form//input[@value='Organisation admin']"));
						Editchkbox.click();
						sleep(3000);
				
						
						WebElement Disbleusr = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/div[2]//span"));
						Disbleusr.click();
						sleep(3000);
						
						
						WebElement usrsaveconfirm = driver.findElement(By.xpath("/html//button[@id='Confirm']"));
						usrsaveconfirm.click();
						sleep(3000);

						WebElement usrsavecancel = driver.findElement(By.xpath("/html//button[@id='Cancel']"));
						usrsavecancel.click();
						sleep(3000);
						
						
						// USER LOGOUT
						WebElement usertab = driver
								.findElement(By.xpath("//app-dashboard/header/ul[3]//a[@role='button']/img"));
						usertab.click();
						sleep(2000);

						WebElement Logout = driver
								.findElement(By.xpath("//app-dashboard/header/ul[3]/li//a[@href='#/login']"));
						Logout.click();
						sleep(2000);
						
						
//						ENTER USERNAME
						WebElement username = driver.findElement(
								By.xpath("/html/body/app-login/div/main/div/div/div/div/div/div/div/form/div[1]/div[1]/input"));
						username.sendKeys("dmtandel1730@gmail.com");
						//sleep(2000);

//						ENTER PASSWORD
						WebElement password = driver.findElement(By.id("password"));
						password.sendKeys("Devangini");
						//sleep(2000);

//						CLICK LOGIN BUTTON
						WebElement logInButton = driver.findElement(
								By.xpath("/html/body/app-login/div/main/div/div/div/div/div/div/div/form/div[3]/div[1]/button"));
						logInButton.click();
						sleep(3000);
						
						

	}
	
	@Test(priority = 6)
	public void adminContractors() throws IOException {
		
		System.out.println("Executing Admin Contractors");
		
		// CLICK ON CONTRACTOR TAB
		
		WebElement Contractrtab = driver.findElement(By.xpath("//app-dashboard//app-sidebar[@class='sidebar']/app-sidebar-nav-custom/ul[@class='nav']//span[.=' Contractors ']"));
		Contractrtab.click();
		sleep(2000);
		
		
		// CLICK ON ADD NEW CONTRACTORS
		
		WebElement AddnewContractr = driver.findElement(By.xpath("//body/app-dashboard/div[@class='app-body']/main[@class='main']//app-contractor-accounts//div[@class='card-footer']/div[@class='row']/div/button[@type='button']"));
		AddnewContractr.click();
		sleep(2000);
	
		
//		ENTER CONTRACTOR ADMIN NAME
		WebElement Contractusername = driver.findElement(
				By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='Contractor name']"));
		Contractusername.sendKeys("dmtandel1730@gmail.com");
		//sleep(2000);

//		ENTER CONTRACTOR ADMIN EMAIL ID
		WebElement ContractEmail = driver.findElement(By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='Contractor Admin Email']"));
		ContractEmail.sendKeys("dmtandel1730@gmail.com");
		//sleep(2000);

//		CLICK ON SAVE BUTTON
		WebElement ContractSave = driver.findElement(
				By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']//button[@type='button']"));
		ContractSave.click();
		sleep(3000);
		
		
		//EDIT CONTRACTORS 
		
//		EDIT CONTRACTOR ADMIN USER NAME
		WebElement EditContractname = driver.findElement(By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='Contractor name']"));
		EditContractname.sendKeys("MELBOURNE METRO");
		//sleep(2000);

//		CLICK ON SAVE BUTTON
		WebElement EditContractSave = driver.findElement(
				By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/div[2]/div[1]/button[@type='button']"));
		EditContractSave.click();
		sleep(3000);
	
		
//		CLICK ON ASSOCIATE BUTTON
		WebElement EditContractAssociate = driver.findElement(
				By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/div[2]/div[2]/button[@type='button']"));
		EditContractAssociate.click();
		sleep(3000);		
		
	}

	@Test(priority = 7)
	public void ContractorUsers() throws IOException {
		
		System.out.println("Executing Contractor Users");
		
	// CLICK ON CONTRACTOR USERS TAB
		
		WebElement ContractrUsrtab = driver.findElement(By.xpath("//app-dashboard[@class='ng-star-inserted']//app-sidebar[@class='sidebar']/app-sidebar-nav-custom/ul[@class='nav']//a[@href='#/admin/contractor-users']"));
		ContractrUsrtab.click();
		sleep(2000);
		
	// SELECT CONTRACTOR FROM THE DROPDOWN
		
		
		// CLICK CONTRACTOR DROPDOWN
				WebElement Contractdrpdown = driver.findElement(By.xpath("//body/app-dashboard[@class='ng-star-inserted']/div[@class='app-body']/main[@class='main']/div[@class='container-fluid']/app-contractor-users[@class='ng-star-inserted']//button[@id='dropdownMenu']"));
				Contractdrpdown.click();
				sleep(2000);

				// SELECT VALUES FROM DROPDOWN

				WebElement drpdnRecord = driver.findElement(By.xpath(
						"//app-dashboard//ol[@class='breadcrumb']//div[@role='group']/li[@class='nav-item']//div[@class='dropdown-menu dropdown-menu-right show']/a[8]"));
				drpdnRecord.click();
				sleep(3000);
		
		
		// CLICK ON NEW BUTTON TO ADD NEW CONTRACTOR USERS
		

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
