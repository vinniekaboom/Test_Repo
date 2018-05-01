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

public class cwsTest {

    private final String APP = "cws";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";

	
	@Before
	public void setUp() throws Exception {
		System.out.println("<CWS>");
		System.setProperty("webdriver.ie.driver", "C:\\eclipse-standard-luna-R-win32-x86_64\\IEDriverServer.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, false);
		capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);
		capabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP,true);
		driver = new InternetExplorerDriver(capabilities);
		Snap = new ErrSnapshot();
		userinfo = new ReadXML();
		names = userinfo.readXML(APP);
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
			assertTrue(driver.getPageSource().contains("<b>TSA End User Agreement and Usage Warning</b>"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<CWS Failure>");
			fail("***Error: Unable to access CWS***");
		}
		
		try {
			driver.findElement(By.xpath("//div[@id='bySelection']/div[2]/div/span")).click();
			assertTrue(driver.getPageSource().contains("Photo of the Week"));
		} catch (Throwable e) {
			System.out.println("<CWS Failure>");
			Snap.take(driver, APP);
			fail("***Error: Training Aids page cannot be found***");
		}
		
		try {		
			driver.findElement(By.id("ctl00_lbttnLogout2")).click();
			assertTrue(driver.getPageSource().contains("You have successfully signed out."));		
		} catch (Throwable e) {
			System.out.println("<BFT Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to logout***");
		}
		System.out.println("PASS");
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
	}		
}
