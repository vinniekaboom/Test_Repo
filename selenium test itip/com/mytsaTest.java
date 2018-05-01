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

public class mytsaTest {
	
	private ErrSnapshot Snap;
	private WebDriver driver;
    private final String APP = "mytsa";
    private ReadXML userinfo;
    private ArrayList<String> names;
    
	@Before
	public void setUp() throws Exception {
		System.out.println("<MY TSA>");
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
		driver.manage().timeouts().pageLoadTimeout(180,  TimeUnit.SECONDS);
		
		try {
			driver.get(names.get(0));
			assertTrue(driver.getPageSource().contains("Choose your airport for real-time conditions"));
		} catch (Throwable e) {
			System.out.println("<MY TSA Failure>");
			Snap.take(driver, APP);
			fail("***Error: MY TSA is unavailable***");
		}
		
		try {
			driver.findElement(By.linkText("Can I Bring?")).click();
			assertTrue(driver.getPageSource().contains("Can I bring my...through the security checkpoint?"));
		} catch (Throwable e) {
			System.out.println("<MY TSA Failure>");
			Snap.take(driver, APP);
			fail("***Error: What Can I Bring Link failed***");
		}	

		try {
			driver.findElement(By.linkText("Home")).click();
			assertTrue(driver.getPageSource().contains("Choose your airport for real-time conditions"));
		} catch (Throwable e) {
			System.out.println("<MY TSA Failure>");
			Snap.take(driver, APP);
			fail("***Error: What Can I Bring Link failed***");
		}
		
		try {
			driver.findElement(By.id("search_field")).sendKeys("Denver");
			driver.findElement(By.cssSelector("input[type='submit'][value='Submit']")).click();
			assertTrue(driver.getPageSource().contains("My TSA"));
		}catch(Throwable e){
			System.out.println("<MY TSA Failure>");
			Snap.take(driver, APP);
			fail("***Error: Search Results***");
		}
		System.out.println("PASS");
	}
}