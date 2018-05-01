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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class pmisTest {
	private ErrSnapshot Snap;
	private WebDriver driver;
	private final String APP = "pmis";
	private ReadXML userinfo;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";
	
	@Before
	public void setUp() throws Exception {
		System.out.println("<PMIS>");
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
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/form/table[2]/tbody/tr[2]/td/input[2]")));				
			}				
			Thread.sleep(1000);
			driver.findElement(By.xpath("/html/body/form/table[2]/tbody/tr[2]/td/input[2]")).click();
			assertTrue(driver.getPageSource().contains("PMIS"));
		} catch (Throwable e) {
			System.out.println("<PMIS Failure>");
			Snap.take(driver, APP);
			fail("***Error: PMIS is unavailable***");
		}
			
	    try {
	    	//8/8/2016 increase wait times for all.
			Thread.sleep(3000);
		    driver.switchTo().frame("topFrame");
		    Thread.sleep(1000);
		    driver.findElement(By.linkText("News")).click();
		    Thread.sleep(3000);
		    driver.findElement(By.linkText("Reviews")).click();
		    Thread.sleep(3000);
		    driver.findElement(By.linkText("Data Entry")).click();
		    Thread.sleep(3000);
		    driver.findElement(By.linkText("Reports")).click();
		    Thread.sleep(3000);
		    driver.findElement(By.linkText("Help")).click();
		    Thread.sleep(3000);
		    driver.switchTo().defaultContent();
		    driver.switchTo().frame("leftFrame");
		    driver.findElement(By.linkText("Information")).click();
		    driver.switchTo().defaultContent();
		    driver.switchTo().frame("main");
		    
		    assertTrue(driver.getPageSource().contains("Information")); 
		    driver.switchTo().defaultContent();
		} catch (Throwable e) {
			System.out.println("<PMIS Failure>");
			Snap.take(driver, APP);
			fail("***Error: PMIS Information***");
		}
	    
	    try {
		    driver.switchTo().frame("topFrame");
		    driver.findElement(By.linkText("Log off")).click();
		} catch (Throwable e) {
			System.out.println("<PMIS Failure>");
			Snap.take(driver, APP);
			fail("***Error: PMIS Log off***");
		}
		    	    
		System.out.println("PASS");
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
	}	
}
