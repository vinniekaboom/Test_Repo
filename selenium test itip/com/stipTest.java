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

public class stipTest {

    private final String APP = "stip";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";

	@Before
	public void setUp() throws Exception {
		System.out.println("<STIP>");
		System.setProperty("webdriver.ie.driver","C:\\eclipse-standard-luna-R-win32-x86_64\\IEDriverServer.exe");
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
			Thread.sleep(1000);
			driver.findElement(By.xpath("//input[@id='btnloginAD']")).click();
			Thread.sleep(1000);
			driver.findElement(By.name("txtuserid")).sendKeys(names.get(1));
			driver.findElement(By.name("txtPassword")).sendKeys(names.get(2));
			driver.findElement(By.cssSelector("input[type='submit'][value='Login']")).click();
			assertTrue(driver.getPageSource().contains("User Agreement"));
		} catch (Throwable e) {
			System.out.println("<STIP Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to access STIP***");
		}
		
		try {
			driver.findElement(
			By.cssSelector("input[type='submit'][value='Accept the Agreement']")).click();
			assertTrue(driver.getPageSource().contains("OSC Applications"));
		} catch (Throwable e) {
			System.out.println("<STIP Failure>");
			Snap.take(driver, APP);
			fail("***Error: OSC Applications page cannot be found***");
		}

/*
 * 4/19/2016 Per Jay Madda, no other links are to be tested.		
		
		try {
			driver.findElement(By.id("Link_TIP Reports")).click();
			assertTrue(driver.getPageSource().contains("TIP Performance Reports"));
		} catch (Throwable e) {
			System.out.println("<STIP Failure>");
			Snap.take(driver, APP);
			fail("***Error: TIP Performance Reports page cannot be found***");
		}
		
		try {
			driver.findElement(By.linkText("Back to Portal")).click();
			assertTrue(driver.getPageSource().contains("OSC Applications"));
		} catch (Throwable e) {
			System.out.println("<STIP Failure>");
			Snap.take(driver, APP);
			fail("***Error: OSC Applications page cannot be found***");
		}
*/
		
		try {
			driver.findElement(By.linkText("Log Out")).click();
			assertTrue(driver.getPageSource().contains("Select a method for login."));
		} catch (Throwable e) {
			System.out.println("<STIP Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to Logout***");
		}
		System.out.println("PASS");
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
	}	
}
