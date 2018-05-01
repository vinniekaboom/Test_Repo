package network.ite.run.com;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class cognosTest {
	private final String APP = "cognos";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";
	
	@Before
	public void setUp() throws Exception {
		System.out.println("<cognos>");
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
			Popup pop = new Popup();
			if(pop.isAlertPresent(driver)) {
				pop.stupidpopup();
				WebDriverWait wait = new WebDriverWait(driver, 120); 
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("td[class='userNameContainer']")));	
			}
			assertTrue(driver.getPageSource().contains("c-automation"));
		} catch (Throwable e) {
			System.out.println("<COGNOS Failure>");
			Snap.take(driver, APP);
			fail("***Error: Failed to Access COGNOS***");			
		}
		
		try {	
			
			driver.findElement(By.linkText("Public Folders")).click();
			assertTrue(driver.getPageSource().contains("Public Folders"));
			driver.findElement(By.linkText("My Folders")).click();
			assertTrue(driver.getPageSource().contains("My Folders"));
			driver.findElement(By.linkText("GRADS")).click();
			driver.findElement(By.linkText("Log Off")).click();
			assertTrue(driver.getPageSource().contains("You are logged off."));
		} catch (Throwable e) {
			System.out.println("<COGNOS Failure>");
			Snap.take(driver, APP);
			fail("***Error: COGNOS***");
		}			
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
	}	
}
