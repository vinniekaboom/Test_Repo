package network.ite.run.com;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class altirisTest {

	private final String APP = "altiris";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;
	
	@Before
	public void setUp() throws Exception {
		System.out.println("<ALTIRIS>");
		System.setProperty("webdriver.ie.driver", "C:\\eclipse-standard-luna-R-win32-x86_64\\IEDriverServer.exe");
		Snap = new ErrSnapshot();
		driver = new InternetExplorerDriver();
		userinfo = new ReadXML();
		names  = userinfo.readXML(APP);
		driver.manage().window().maximize();
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("\n");
		driver.quit();
	}

	@Test
	public void test() {
		try {
					
			driver.get(names.get(0));
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			assertTrue(driver.getPageSource().contains("User"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<ALTIRIS Failure>");
			fail("Error: Cannot Access Altiris");
		}
		System.out.println("PASS");
	}
}
