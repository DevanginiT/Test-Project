package project_cams;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CreateOrganisation {
	
	private static WebDriver driver;

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
		sleep(3000);
		
	
		} catch (Exception e) {

			System.out.println(e.getMessage());

			System.out.println("TEST FAILED");


			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		}
	
	@Test(priority = 1)
	public void adminOrgAccount() throws IOException {
		
		System.out.println("Executing Organisation Account Test");

		

//		     CLICK ON ORGANISATION TAB
			WebElement UserOrg = driver.findElement(By.xpath("//app-dashboard//app-sidebar[@class='sidebar']/app-sidebar-nav-custom/ul[@class='nav']//span[.=' Org. Accounts ']"));
			UserOrg.click();
			sleep(2000);
			
			// This will scroll the web page till end.
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			
			sleep(2000);
		
//			   ADD NEW ORGANISATION BUTTON
			
				WebElement UserOrgnew = driver.findElement(By.xpath("/html/body/app-dashboard/div[1]/main/div/app-organisation-accounts/div/div[2]/div/div/button"));
				UserOrgnew.click();
				sleep(2000);
				
			
//	         ENTER ORGANISATION NAME		
			
				WebElement Orgnewname = driver.findElement(By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='Organisation name']"));
				Orgnewname.sendKeys("NEWT ORGANISATION");
				sleep(2000);
			
//	             ENTER ORGANISATION SHORT NAME		
				
				WebElement Orgshortname = driver.findElement(By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='No spaces allowed']"));
				Orgshortname.sendKeys("NEWT");
				sleep(2000);		
		
//	             ENTER ORGANISATION ADMIN EMAIL		
				
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
				

				
//	          CLICK ON SAVE BUTTON	
				
				WebElement Orgsavebtn = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/div[@class='row']//i[@class='fa fa-cloud']"));
				Orgsavebtn.click();
				sleep(2000);
		
			
				
//			     EDIT ORGANISATION
				
				
				// Java Script for scroll up
				JavascriptExecutor js1 = (JavascriptExecutor) driver;

				js1.executeScript("window.scrollTo(0,-250)");
				
				
				
						
//					     CLICK ON ORGANISATION TAB
						
				WebElement clOrgUser = driver.findElement(By.xpath("//div[@id='collapseOverlay']/a[@class='closebtn']"));
				clOrgUser.click();
				sleep(3000);
						
						
						
						WebElement editOrgAcc = driver.findElement(By.xpath("//body/app-dashboard[@class='ng-star-inserted']/div[@class='app-body']/main[@class='main']//app-organisation-accounts[@class='ng-star-inserted']//table/tbody/tr[2]/td[1]"));
						editOrgAcc.click();
						sleep(2000);
						
						
//				         ENTER ORGANISATION NAME		
						
							WebElement EditOrgname = driver.findElement(By.xpath("//div[@id='collapseOverlay']//form//input[@placeholder='Organisation name']"));
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
					
							
//				          CLICK ON SAVE BUTTON	
							
							WebElement editOrgsavebtn = driver.findElement(By.xpath("//div[@id='collapseOverlay']/div[@class='col-11']/div[@class='row']//i[@class='fa fa-cloud']"));
							editOrgsavebtn.click();
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
