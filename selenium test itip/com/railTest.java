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

public class railTest {

	private ErrSnapshot Snap;
	private WebDriver driver;
    private final String APP = "rail";
    private ArrayList<String> names;

	@Before
	public void setUp() throws Exception {
		System.out.println("<RAIL>");
		System.setProperty("webdriver.ie.driver", "C:\\eclipse-standard-luna-R-win32-x86_64\\IEDriverServer.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);
		capabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP,true);
		driver = new InternetExplorerDriver(capabilities);
		Snap = new ErrSnapshot();
		ReadXML userinfo = new ReadXML();
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
			assertTrue(driver.getPageSource().contains("Transportation Security Administration"));
		} catch (Throwable e) {
			System.out.println("<RAIL Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to access RAIL***");
		}

		try {
			driver.findElement(By.linkText("TSA Rail Super User")).click();
			driver.findElement(By.linkText("Dashboard")).click();
			assertTrue(driver.getPageSource().contains("RSSM Shipments"));
		} catch (Throwable e) {
			System.out.println("<RAIL Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Rail Dashboard page cannot be found***");
		}

		try {
			driver.findElement(By.linkText("Home")).click();
			assertTrue(driver.getPageSource().contains("Navigator"));
		} catch (Throwable e) {
			System.out.println("<RAIL Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Transportation Security Administration page cannot be found***");
		}

		try {
		    driver.findElement(By.linkText("Event Update")).click();
			assertTrue(driver.getPageSource().contains("Event Search Update"));
		} catch (Throwable e) {
			System.out.println("<RAIL Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Event Search Update page cannot be found***");
		}

		try {
			driver.findElement(By.linkText("Home")).click();
			assertTrue(driver.getPageSource().contains("Transportation Security Administration"));
		} catch (Throwable e) {
			System.out.println("<RAIL Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Transportation Security Administration page cannot be found***");
		}

		try {
			driver.findElement(By.linkText("BI Reports")).click();
			assertTrue(driver.getPageSource().contains("Rail Risk Reduction Verification System"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			fail("***Error: BI Reports page cannot be found***");
		}

	    try {
			driver.findElement(By.linkText("Home")).click();
			assertTrue(driver.getPageSource().contains("Transportation Security Administration"));
		} catch (Throwable e) {
			System.out.println("<RAIL Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Transportation Security Administration page cannot be found***");
		}

	    try {
	    	//WebDriverWait wait = new WebDriverWait(driver, 20);
	    	driver.findElement(By.linkText("Logout")).click();
	    	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	    	//driver.findElement(By.name("Close_Button")).click();
	    	//Alert myAlert = wait.until(ExpectedConditions.alertIsPresent());
	    	//myAlert.accept();
	    }catch(Throwable e){
	    	System.out.println("<RAIL Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to Logout***");	    	
	    }
	    System.out.println("PASS");
	}
}
