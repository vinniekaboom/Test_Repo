package network.ite.run.com;

import static org.junit.Assert.*;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class statTest {

    private final String APP = "stat";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;

	@Before
	public void setUp() throws Exception {
		System.out.println("<STAT>");
		System.setProperty("webdriver.ie.driver", "C:\\eclipse-standard-luna-R-win32-x86_64\\IEDriverServer.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,false);
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
		System.out.println("\n");
	}

	@Test
	public void test() {
		driver.manage().timeouts().pageLoadTimeout(180,  TimeUnit.SECONDS);
		
		try {
			driver.get(names.get(0));
			driver.findElement(By.cssSelector("input[type='submit'][value='Login']")).click();
			driver.findElement(By.id("username")).sendKeys(names.get(1));
			driver.findElement(By.id("password")).sendKeys(names.get(2));
			
			Thread.sleep(5000);
			
			Robot robot = new Robot();
		    robot.keyPress(java.awt.event.KeyEvent.VK_ENTER);
		    robot.keyRelease(java.awt.event.KeyEvent.VK_ENTER); 
			
			Thread.sleep(5000);

			assertTrue(driver.getPageSource().contains("Security Threat Assessment Tool (STAT) System"));
		} catch (Throwable e) {
			System.out.println("<STAT Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to access STAT***");
		}

		try {
			driver.findElement(By.partialLinkText("Search STAs")).click();
			assertTrue(driver.getPageSource().contains("Search STAs"));
		} catch (Throwable e) {
			System.out.println("<STAT Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Search STAs cannot be found***");
		}

		/*try {
			driver.findElement(By.name("staName")).sendKeys("%Smith%");
			driver.findElement(By.name("staStatus")).sendKeys("Passed");
			driver.findElement(By.cssSelector("input[type='submit'][value='search']")).click();
			
			WebDriverWait wait = new WebDriverWait(driver, 220); 
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@id='maincontent']/h1")));					
			assertTrue(driver.getPageSource().contains("Search STAs: Results"));
		} catch (Throwable e) {
			System.out.println("<STAT Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Search STAs: Failed***");
		}*/
	
		try {
			driver.findElement(By.linkText("Logout")).click();

		}catch(Throwable e){
			System.out.println("<STAT Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Logout Failed***");
		}
		System.out.println("PASS");
	}	
}
