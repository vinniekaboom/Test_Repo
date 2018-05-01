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

public class osciseReview {
	private final String APP = "eReview";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";

	@Before
	public void setUp() throws Exception {
		System.out.println("<OSC IS eReview>");
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
			WebDriverWait wait = new WebDriverWait(driver, 220); 			
			if(pop.isAlertPresent(driver)) {
				pop.stupidpopup();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[id='ctl00_PlaceHolderSiteName_onetidProjectPropertyTitle']")));				
			}			
			assertTrue(driver.getPageSource().contains("OSC-IS eReview"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<OSC IS eReview Faiure>");
			fail("***Error: OSC IS eReview is unavailable***");
		}
		
		try{
			driver.findElement(By.linkText("OSC-IS eReview")).click();
			assertTrue(driver.getPageSource().contains("eReview Portal Dashboard"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<OSC IS eReview Failure>");
			fail("***Error: eReview Portal Dashboard***");
		}
		
		try{
			driver.findElement(By.linkText("OT ERF Dashboard")).click();
			assertTrue(driver.getPageSource().contains("Drafts"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<OSC IS eReview Failure>");
			fail("***Error: eReview Portal Dashboard***");
		}
		
		System.out.println("PASS");
	  }
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
		}
	}
