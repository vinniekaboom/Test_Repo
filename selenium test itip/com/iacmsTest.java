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

public class iacmsTest {
	private final String APP = "iacms";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";

	@Before
	public void setUp() throws Exception {
		System.out.println("<IACMS>");
		System.setProperty("webdriver.ie.driver", "C:\\eclipse-standard-luna-R-win32-x86_64\\IEDriverServer.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, false);
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
		driver.manage().timeouts().pageLoadTimeout(280,  TimeUnit.SECONDS);		
		try {
			driver.get(names.get(0));
			driver.findElement(By.cssSelector("input[type='submit'][value='Login']")).click();
			driver.findElement(By.name("username")).sendKeys(names.get(1));
			driver.findElement(By.name("password")).sendKeys(names.get(2));
			driver.findElement(By.cssSelector("input[type='submit'][value='I AGREE']")).click();
			assertTrue(driver.getPageSource().contains("Indirect Air Carrier Management System"));
		} catch (Throwable e) {
			System.out.println("<IACMS Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to access IACMS***");
		}

		try {
			driver.findElement(By.partialLinkText("View IAC Apps")).click();
			Thread.sleep(2000);
			assertTrue(driver.getPageSource().contains("IAC Applications Waiting Approval"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			fail("***Error: IAC Applications Waiting Approval cannot be found***");
		}

		try {
			driver.findElement(By.partialLinkText("Search STAs")).click();
			Thread.sleep(2000);
			assertTrue(driver.getPageSource().contains("Search Security Threat Assessments (STAs)"));
		} catch (Throwable e) {
			System.out.println("<IACMS Failure>");
			Snap.take(driver, APP);
			fail("***Error: Search Security Threat Assessments (STAs) not found***");
		}

		try {
			driver.findElement(By.name("localStaCriteria.staCertId")).sendKeys("EAE%");
			driver.findElement(By.name("submitButtonName")).click();
			Thread.sleep(2000);
			assertTrue(driver.getPageSource().contains("Search STAs: Results"));
		} catch (Throwable e) {
			System.out.println("<IACMS Failure>");
			Snap.take(driver, APP);
			fail("***Error: Search STAs: Results not found***");
		}
		
		try {
			driver.findElement(By.linkText("Logout")).click();
			Thread.sleep(5000);
			
		} catch (Throwable e) {
			System.out.println("<IACMS Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to logout***");
		}
		System.out.println("PASS");
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
	}	
}
