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

public class osofaqTest {
    private final String APP = "osofaq";
	private ErrSnapshot Snap;
	private WebDriver driver;
	ReadXML userinfo;
	ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";

	@Before
	public void setUp() throws Exception {
		System.out.println("<OSO FAQ>");
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
			WebDriverWait wait = new WebDriverWait(driver, 120); 			
			if(pop.isAlertPresent(driver)) {
				pop.stupidpopup();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='submit'][value='I Agree']")));				
			}					
			driver.findElement(By.cssSelector("input[type='submit'][value='I Agree']")).click();
		} catch (Throwable e) {
			System.out.println("<OSO FAQ Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to access OSOFAQ***");
		}
		
		try {
			driver.findElement(By.linkText("FAQs")).click();
			assertTrue(driver.getPageSource().contains("Frequently Asked Questions (FAQ)"));
		} catch (Throwable e) {
			System.out.println("<OSO FAQ Failure>");
			Snap.take(driver, APP);
			fail("***Error: Frequently Asked Questions Page not found***");
		}	
		
		try {
			driver.findElement(By.linkText("SOPs")).click();
			assertTrue(driver.getPageSource().contains("Standard Operating Procedures (SOP)"));
		} catch (Throwable e) {
			System.out.println("<OSO FAQ Failure>");
			Snap.take(driver, APP);
			fail("***Error: Standard Operating Procedures (SOP) Page not found***");
		}

		try {
			driver.findElement(By.linkText("QRGs")).click();
			assertTrue(driver.getPageSource().contains("Quick Reference Guides (QRG)"));
		} catch (Throwable e) {
			System.out.println("<OSO FAQ Failure>");
			Snap.take(driver, APP);
			fail("***Error: Quick Reference Guides (QRG) Page not found***");
		}
		
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);	
			driver.findElement(By.linkText("Logout")).click();
			Alert myAlert = wait.until(ExpectedConditions.alertIsPresent());
			myAlert.accept();
		}catch(Throwable e){
			System.out.println("<OSO FAQ Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to Logout***");
		}
		System.out.println("PASS");
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
		}	
}
