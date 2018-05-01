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

public class cmsTest {
	private ErrSnapshot Snap;
	private WebDriver driver;
	private final String APP = "cms";
	private ReadXML userinfo;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";

	@Before
	public void setUp() throws Exception {
		System.out.println("<CMS>");
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
		killProcess();
		System.out.println("\n");
	}

	@Test
	public void test() {
		driver.manage().timeouts().pageLoadTimeout(180,  TimeUnit.SECONDS);		
		
		try {
			driver.get(names.get(0));
			driver.findElement(By.id("P101_AGREE_0")).click();
			driver.findElement(By.id("P101_USERNAME")).sendKeys(names.get(1));
			driver.findElement(By.id("P101_PASSWORD")).sendKeys(names.get(2));
			driver.findElement(By.cssSelector("input[type='button'][value='Login']")).click();
			assertTrue(driver.getPageSource().contains("CMS"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<CMS Failure>");
			fail("**Error: Unable to access cms***");
		}
		
		try {
			driver.findElement(By.linkText("Claims Management")).click();
			assertTrue(driver.getPageSource().contains("Claims Management"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<CMS Failure>");
			fail("***Error: Claims Management Page not found**");
		}
		
		try {
			driver.findElement(By.linkText("Incident Reports")).click();
			assertTrue(driver.getPageSource().contains("Search Criteria"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<CMS Failure>");
			fail("**Error: Incident Reports Page not found**");
		}
		System.out.println("PASS");		
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
	}
}
