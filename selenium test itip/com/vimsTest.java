package network.ite.run.com;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class vimsTest {
	private final String APP = "vims";
	private ErrSnapshot Snap;
	private WebDriver driver;
    private ReadXML userinfo;
    private ArrayList<String> names;
	
	@Before
	public void setUp() throws Exception {
		System.out.println("<VIMS>");
		System.setProperty("webdriver.ie.driver", "C:\\eclipse-standard-luna-R-win32-x86_64\\IEDriverServer.exe");
		Snap = new ErrSnapshot();
		driver = new InternetExplorerDriver();
		userinfo = new ReadXML();
		names  = userinfo.readXML(APP);
		driver.manage().window().maximize();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		System.out.println("\n");
	}

	@Test
	public void test() {
		driver.manage().timeouts().pageLoadTimeout(160,  TimeUnit.SECONDS);				
		try {
			driver.get(names.get(0));
			Popup pop = new Popup();
			WebDriverWait wait = new WebDriverWait(driver, 120); 			
			if(pop.isAlertPresent(driver)) {
				pop.stupidpopup();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='button'][value='I Agree']")));				
			}
			driver.findElement(By.cssSelector("input[type='button'][value='I Agree']")).click();
			assertTrue(driver.getPageSource().contains("Welcome,"));
		} catch (Throwable e) {
			System.out.println("<VIMS Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to access VIMS***");
		}
		
		
		try {
			//7/28/2016 Reports menu left hand side column
			// id changed from gen89 to gen109, but does not match the embedded codes
			//driver.findElement(By.id("ext-gen89")).click();
			//Thread.sleep(3000);
			driver.findElement(By.cssSelector("span[class='x-panel-header-text'][id*='ext-gen109']")).click();
			//Thread.sleep(30000);
			assertTrue(driver.getPageSource().contains("VIMS - Reports"));
		} catch (Throwable e) {
			System.out.println("<VIMS Failure>");
			Snap.take(driver, APP);
			fail("***Error: VIMS - Reports page cannot be found***");
		}
		
		
		try {
			//7/28/2016 My Preferences menu left hand side column
			// id changed from gen90 to gen114
			//driver.findElement(By.id("ext-gen90")).click();			
			driver.findElement(By.id("ext-gen115")).click();
			assertTrue(driver.getPageSource().contains("My Preferences"));
		} catch (Throwable e) {
			System.out.println("<VIMS Failure>");
			Snap.take(driver, APP);
			fail("***Error: My Preferences page not found***");
		}

		
		/*
		try {
			//7/28/2016 Help menu left hand side column
			// id changed from gen104 to gen120
			//driver.findElement(By.id("ext-gen104")).click();				
			driver.findElement(By.id("ext-gen119")).click();
			assertTrue(driver.getPageSource().contains("Help"));
		} catch (Throwable e) {
			System.out.println("<VIMS Failure>");
			Snap.take(driver, APP);
			fail("***Error:  Help page not found***");
		}
		*/
		
		
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			//7/28/2016 Logout menu left hand side column
			// id changed from gen102 to gen156,159
			//driver.findElement(By.id("ext-gen102")).click();					
			driver.findElement(By.id("ext-gen155")).click();
			Alert myAlert = wait.until(ExpectedConditions.alertIsPresent());
			myAlert.accept();
		}catch(Throwable e){
			System.out.println("<VIMS Failure>");
			Snap.take(driver, APP);
			fail("***Error:  Unable to Logout***");	
		}
		System.out.println("PASS");
	}	
}
