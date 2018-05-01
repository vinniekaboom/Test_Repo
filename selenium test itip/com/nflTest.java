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

public class nflTest {

    private final String APP = "noflylist";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;

	
	@Before
	public void setUp() throws Exception {
		System.out.println("<No Fly List>");
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
		driver.manage().timeouts().pageLoadTimeout(60,  TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(60,  TimeUnit.SECONDS);	
		
		try {
			driver.get(names.get(0));
			driver.findElement(By.cssSelector("input[type='checkbox'][value='Y']")).click();
			driver.findElement(By.cssSelector("input[id='P101_USERNAME'][type='text']")).sendKeys(names.get(1));
			driver.findElement(By.cssSelector("input[id='P101_PASSWORD'][type='password']")).sendKeys(names.get(2));Thread.sleep(2000);
			driver.findElement(By.cssSelector("input[type='button'][value='Login']")).click();
			assertTrue(driver.getPageSource().contains("Welcome To TSIS List Management System"));
		} catch (Throwable e) {
			System.out.println("<No Fly List Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to login***");
		}
		
		try {
			driver.findElement(By.linkText("No-Fly List")).click();
			assertTrue(driver.getPageSource().contains("Find A Person From No-Fly List"));
		} catch (Throwable e) {
			System.out.println("<No Fly List Failure>");
			Snap.take(driver, APP);
			fail("***Error: No Fly List page cannot be found***");
		}
		
		try {
			driver.findElement(By.cssSelector("input[id='P4000_LASTNAME'][type='text']")).sendKeys("Garcia");
			driver.findElement(By.cssSelector("input[id='P4000_FIND'][value='Find']")).click();
			Thread.sleep(3000);
			assertTrue(driver.getPageSource().contains("Result Set From No-Fly List"));
		} catch (Throwable e) {
			System.out.println("<No Fly List Failure>");
			Snap.take(driver, APP);
			fail("***Error: Results page cannot be found***");
		}
		System.out.println("PASS");
	}
}
