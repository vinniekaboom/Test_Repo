package network.ite.run.com;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;

public class edbTest {
	private final String APP = "edb";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";
	
	@Before
	public void setUp() throws Exception {
		System.out.println("<EDB>");
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
			assertTrue(driver.getPageSource().contains("My Applications"));
		} catch (Throwable e) {
			System.out.println("<EDB Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to access EDB application***");
		}
		
		try {
			Actions userAction = new Actions(driver);
			WebElement expandLink = driver.findElement(By.className("rtIn"));
			Actions dblClick = userAction.doubleClick(expandLink);
			dblClick.build().perform();
		} catch (Throwable e) {
			System.out.println("<EDB Failure>");
			Snap.take(driver, APP);
			fail("***Error: My Preferences page cannot be found***");
		}
		
		try {
			driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolderMain_RadTreeAppList']/ul/li/ul/li/ul/li/div/span[text()='Checkpoints']")).click();
			assertTrue(driver.getPageSource().contains("Airport Categories"));	
		} catch (Throwable e) {
			System.out.println("<EDB Failure>");
			Snap.take(driver, APP);
			fail("***Error: Checkpoints page cannot be found***");
		}
		
		try {
			driver.findElement(By.xpath("/html/body/div/div[4]/form/div[4]/div[3]/div[1]/div[1]/div[4]/div/div[1]/div/ul/li[3]/div/span[2]")).click();
			assertTrue(driver.getPageSource().contains("Airport Categories"));	
		} catch (Throwable e) {
			System.out.println("<EDB Failure>");
			Snap.take(driver, APP);
			fail("***Error: Airport Categories page cannot be found***");
		}
		System.out.println("PASS");
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
	}	
}

