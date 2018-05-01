package network.ite.run.com;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class aimTest {
	private final String APP = "aim";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";
	
	@Before
	public void setUp() throws Exception {
		System.out.println("<AIM>");
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
		Popup pop = new Popup();
		driver.manage().timeouts().pageLoadTimeout(180,  TimeUnit.SECONDS);	
		try {
			WebDriverWait wait = new WebDriverWait(driver, 180); 	
			driver.get(names.get(0));
			
			// if there is Certificate Problem, then take screenshot and continue
			//Snap.take(driver, APP);
			//driver.findElement(By.id("overridelink")).click();
			
			if(pop.isAlertPresent(driver)) {
				pop.stupidpopup();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='button'][value='I Agree']")));		
			}
			driver.findElement(By.cssSelector("input[type='button'][value='I Agree']")).click();
			assertTrue(driver.getPageSource().contains("Welcome"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<AIM Failure>");
			fail("***Error: Unable to access AIM***" + e);
		}
		
		// "My Preference" link = ext-gen91
		// Employee (outter box) link = ext-gen92
		try {
			driver.findElement(By.id("ext-gen92")).click();
			assertTrue(driver.getPageSource().contains("My Preferences"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<AIM Failure>");
			fail("***Error: My Preferences page cannot be found***");
		}
		
		// Employees = ext-gen101
		try {
			driver.findElement(By.id("ext-gen101")).click();
			assertTrue(driver.getPageSource().contains("Launchpad"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<AIM Failure>");
			fail("***Error: Employees page not found***");
		}

		try {
			driver.findElement(By.id("ext-gen111")).click();
			assertTrue(driver.getPageSource().contains("Launchpad"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<AIM Failure>");
			fail("***Error: Physical Property Tracking page not found***");
		}
		
		try {
			driver.findElement(By.id("ext-gen121")).click();
			assertTrue(driver.getPageSource().contains("Launchpad"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<AIM Failure>");
			fail("***Error: Resoursces Page not found***");
		}
		
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			driver.findElement(By.id("ext-gen127")).click();
			Alert myAlert = wait.until(ExpectedConditions.alertIsPresent());
			myAlert.accept();
		}catch(Throwable e){
			Snap.take(driver, APP);
			System.out.println("<AIM Failure>");
			fail("***Error: Unable to logout***");
		}
		System.out.println("PASS");
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
	}
}
