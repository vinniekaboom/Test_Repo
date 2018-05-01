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

public class voviciTest {
    private final String APP = "vovici";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";
	
	@Before
	public void setUp() throws Exception {
		System.out.println("<VOVICI>");
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
			driver.findElement(By.id("ctl00_cphBody_txtUserName")).sendKeys(names.get(1));
			driver.findElement(By.id("ctl00_cphBody_txtPassword")).sendKeys(names.get(2));
			driver.findElement(By.linkText("Login")).click();
			assertTrue(driver.getPageSource().contains("My Surveys"));
		}catch (Throwable e){
			System.out.println("<VOVICI Failure>");
			Snap.take(driver, APP);
			fail("***Error: My Serveys page did not appear. Login Failed.***");
		}
	
		try {
			driver.findElement(By.id("ctl00_hypPolls")).click();
			assertTrue(driver.getPageSource().contains("Polls"));
		} catch (Throwable e) {
			System.out.println("<VOVICI Failure>");
			Snap.take(driver, APP);
			fail("***Error: Polls page cannot be found***");
		}
		
		try {
			driver.findElement(By.id("ctl00_hypPanel")).click();
			assertTrue(driver.getPageSource().contains("Panelists"));
		} catch (Throwable e) {
			System.out.println("<VOVICI Failure>");
			Snap.take(driver, APP);
			fail("***Error: Panelists page cannot be found***");
		}
		
		try {
			driver.findElement(By.id("ctl00_ctlUtilities_btnLogout")).click();
			assertTrue(driver.getPageSource().contains("Customer Login"));
		} catch (Throwable e) {
			System.out.println("<VOVICI Failure>");
			Snap.take(driver, APP);
			fail("***Error: Panelists page cannot be found***");
		}
		System.out.println("PASS");
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
	}	
}
