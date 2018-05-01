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

public class oscistmsTest {

	private final String APP = "tms";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";

	@Before
	public void setUp() throws Exception {
		System.out.println("<OSC IS tms>");
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
		driver.manage().timeouts().pageLoadTimeout(280,  TimeUnit.SECONDS);
		try {
			driver.get(names.get(0));
			Popup pop = new Popup();
			WebDriverWait wait = new WebDriverWait(driver, 120); 			
			if(pop.isAlertPresent(driver)) {
				pop.stupidpopup();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='WebPartWPQ22']/table/tbody/tr/td[1]/a/img")));				
			}
			
			assertTrue(driver.getPageSource().contains("OSC"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<OSC IS TMS Faiure>");
			fail("***Error: OSC-IS TMS is unavailable***");
		}

		try {
			driver.findElement(By.xpath("//div[@id='WebPartWPQ22']/table/tbody/tr/td[1]/a/img")).click();
			assertTrue(driver.getPageSource().contains("New Item"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<OSC IS TMS Faiure>");
			fail("***Error: New Transaction page is unavailable***");
		}
		System.out.println("PASS");
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
	}
}
