package project_cams;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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




public class CreateOrgUserCams {

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
				"//app-dashboard//ol[@class='breadcrumb']//div[@role='group']/li[@class='nav-item']//div[@class='dropdown-menu dropdown-menu-right show']/a[8]"));
		drpdnRecord.click();
		sleep(3000);

//     CLICK ON ADMIN ICON
		WebElement adminTab = driver.findElement(By.xpath("//app-dashboard/header/ul[1]/li[@title='Admin']//img[@class='icon']"));
		adminTab.click();
		sleep(3000);

//     CLICK ON ORGANISATION USERS
		WebElement UserOrg = driver.findElement(By.xpath("/html/body/app-dashboard/div[1]/app-sidebar/app-sidebar-nav-custom/ul/div[5]/a/span"));
		UserOrg.click();
		sleep(5000);
		
		
		
		
//		   ADD NEW ORGANISATION USERS
		
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
			
//		    GET LIST OF USER EMAIL FROM THE TABLE
			
			
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
		
				WebElement Editusrsave = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']//div[@class='col-4']/button[@type='button']"));
				Editusrsave.click();
				sleep(3000);
				
			

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
