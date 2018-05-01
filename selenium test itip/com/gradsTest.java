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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class gradsTest {
	private final String APP = "grads";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";
	
	@Before
	public void setUp() throws Exception {
		System.out.println("<GRADS>");
		System.setProperty("webdriver.ie.driver", "C:\\eclipse-standard-luna-R-win32-x86_64\\IEDriverServer.exe");
		
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,false);
		driver = new InternetExplorerDriver(capabilities);

		Snap = new ErrSnapshot();
		driver = new InternetExplorerDriver(capabilities);
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
		Popup pop = new Popup();
		WebDriverWait wait = new WebDriverWait(driver, 120); 
		driver.manage().timeouts().pageLoadTimeout(180,  TimeUnit.SECONDS);		
		try {
			driver.get(names.get(0));			
			if(pop.isAlertPresent(driver)) {
				pop.stupidpopup();	
			}			
			assertTrue(driver.getPageSource().contains("/SiteCollectionImages/grads_ssi.png"));
		} catch (Throwable e) {
			System.out.println("<GRADS Failure>");
			Snap.take(driver, APP);
			fail("***Error: Failed to Access GRADS***");
		}		
		try {
			driver.findElement(By.cssSelector("img[name='Image4'][id='Image4'][alt='Search Assessments']")).click();
			assertTrue(driver.getPageSource().contains("Assessment"));
			driver.findElement(By.cssSelector("img[name='Image5'][id='Image5'][alt='Reports']")).click();
			if(pop.isAlertPresent(driver)) {
				pop.stupidpopup(); 
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("td[class='userNameContainer']")));	
			}
		} catch (Throwable e) {
			System.out.println("<GRADS Failure>");
			Snap.take(driver, APP);
			fail("***Error: Failed to Access GRADS Assessments***");
		}

		try {
			driver.findElement(By.linkText("Public Folders")).click();			
			assertTrue(driver.getPageSource().contains("Public Folders"));
			driver.findElement(By.linkText("My Folders")).click();
			assertTrue(driver.getPageSource().contains("My Folders"));
			driver.findElement(By.linkText("GRADS")).click();
			driver.findElement(By.linkText("Log Off")).click();
			assertTrue(driver.getPageSource().contains("You are logged off."));
		}catch(Throwable e){
			System.out.println("<GRADS Failure>");
			Snap.take(driver, APP);
			fail("***Error: Failed GRADS Reports***");			
		}	
		System.out.println("PASS");
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
	}
}
