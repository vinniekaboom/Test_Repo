package network.ite.run.com;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class esmTest {
	private final String APP = "esm";	
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;
	
	@Before
	public void setUp() throws Exception {
		System.out.println("<ESM>");
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
		driver.manage().timeouts().pageLoadTimeout(280,  TimeUnit.SECONDS);
		try {
			driver.get(names.get(0));Thread.sleep(5000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(5000);
			Set<String> Windows = driver.getWindowHandles();
			Iterator<String> it = Windows.iterator();
			String newwindow = it.next();
			driver.switchTo().window(newwindow);
			assertTrue(driver.getPageSource().contains("Welcome to the TSA Staffing Simulation and Planning Tool"));
		} catch (Throwable e) {
			System.out.println("<ESM Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to access the ESM application***");
		}
		System.out.println("PASS");
	}
}
