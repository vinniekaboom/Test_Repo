package network.ite.run.com;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ksmsTest {
	private ErrSnapshot Snap;
	private WebDriver driver;
	private final String APP = "ksms";
	private ReadXML userinfo;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";

	@Before
	public void setUp() throws Exception {
		System.out.println("<KSMS>");
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
	    killProcess();
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
			assertTrue(driver.getPageSource().contains("Transportation Security Administration"));
		} catch (Throwable e) {
			System.out.println("<KSMS Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to access KSMS***");
		}

		try {
			driver.findElement(By.linkText("KSMS Customers Online TSA Admin")).click();
			assertTrue(driver.getPageSource().contains("Known Shipper Management System"));
		} catch (Throwable e) {
			System.out.println("<KSMS Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Known Shipper Management System page cannot be found***");
		}

		try {
			driver.findElement(By.linkText("Add New Shipper")).click();
			assertTrue(driver.getPageSource().contains("KSMS - Create Single Shipper"));
		} catch (Throwable e) {
			System.out.println("<KSMS Faiure>");
			Snap.take(driver, APP);
			fail("***Error: KSMS - Create Single Shipper page cannot be found***");
		}

		try {
			driver.findElement(By.linkText("Search Shippers")).click();
			assertTrue(driver.getPageSource().contains("Search Shippers"));
		} catch (Throwable e) {
			System.out.println("<KSMS Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Search Shippers failed***");
		}

		try {
			driver.findElement(By.name("ShipperName")).sendKeys("Executive Express, Inc.");
			driver.findElement(By.name("State")).sendKeys("Virginia");
			driver.findElement(By.name("Status")).sendKeys("Known");
			driver.findElement(By.id("Search")).click();
			assertTrue(driver.getPageSource().contains("Search Results"));
		} catch (Throwable e) {
			System.out.println("<KSMS Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Search Results page cannot be found***");
		}
		
		try {
			driver.findElement(By.linkText("Logout")).click();		
		}catch(Throwable e){
			System.out.println("<KSMS Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to Logout***");			
		}
		System.out.println("PASS");
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
	}	
}
