package network.ite.run.com;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.DesiredCapabilities;

public class cimsTest {

	private final String APP = "cims";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;
	

	@Before
	public void setUp() throws Exception {
		System.out.println("<CIMS>");
		System.setProperty("webdriver.ie.driver", "C:\\eclipse-standard-luna-R-win32-x86_64\\IEDriverServer.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,false);
		capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);
		capabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP,true);
		driver = new InternetExplorerDriver(capabilities);
		Snap = new ErrSnapshot();
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
		driver.manage().timeouts().pageLoadTimeout(180,  TimeUnit.SECONDS);
			
		try {
			driver.get(names.get(0));
			driver.findElement(By.id("username")).sendKeys(names.get(1));
			driver.findElement(By.id("password")).sendKeys(names.get(2));
			driver.findElement(By.cssSelector("input[type='submit'][value='I AGREE']")).click();
			assertTrue(driver.getPageSource().contains("Mode:*"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<CIMS Failure>");
			fail("***Error: Unable to access the PARIS application***");
		}

		try {
			driver.findElement(By.cssSelector("input[type='radio'][value='1_Air']")).click();
			driver.findElement(By.cssSelector("input[type='submit'][value='Select']")).click();
			assertTrue(driver.getPageSource().contains("Welcome to PARIS"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<CIMS Failure>");
			fail("***Error: Welcome to PARIS page cannot be found***");
		}

		try {
			driver.findElement(By.linkText("Incidents")).click();
			assertTrue(driver.getPageSource().contains("Welcome to Incidents"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<CIMS Failure>");
			fail("***Error: Welcome to Incidents page cannot be found***");
		}

		try {
			driver.findElement(By.linkText("Search Incidents")).click();
			driver.findElement(By.name("portId")).sendKeys("ABQ-Albuquerque International Sunport Airport");
			driver.findElement(By.name("criteria.incidentType")).sendKeys("Access Control - Contained Security Incident");
			driver.findElement(By.cssSelector("input[type='submit'][value='search']")).click();
			assertTrue(driver.getPageSource().contains("Results"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<CIMS Failure>");
			fail("***Error: Search Incidents: Results page cannot be found***");
		}
		
		try {
			driver.findElement(By.linkText("Reports")).click();
			assertTrue(driver.getPageSource().contains("Welcome to Reports"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<CIMS Failure>");
			fail("***Error: Welcome to Reports page cannot be found***");
		}
	
		try {
			driver.findElement(By.cssSelector("a[href*='module=Profiles']")).click();
			assertTrue(driver.getPageSource().contains("View Profiles Reports"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<CIMS Failure>");
			fail("***Error: View Profiles Reports page cannot be found***");
		}

/*
 * 4/20/2016 CarrierReport does not exist		
 
		try {
			driver.findElement(By.cssSelector("a[href*='key=CarrierReport']")).click();
			assertTrue(driver.getPageSource().contains("Reports"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<CIMS Failure>");
			fail("***Error: Carrier Report page cannot be found***");
		}			
*/

		try {
			//WebDriverWait wait = new WebDriverWait(driver, 20);
			driver.findElement(By.linkText("Logout")).click();
	    	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			//driver.findElement(By.name("Close_Button")).click();
			//Alert myAlert = wait.until(ExpectedConditions.alertIsPresent());
			//myAlert.accept();
		}catch(Throwable e){
			Snap.take(driver, APP);
			System.out.println("<CIMS Failure>");
			fail("***Error: Unable to logout***");			
		}
		System.out.println("PASS");
	}		
}
