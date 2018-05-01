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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class oscisbftTest {
    private final String APP = "oscbft";
    private ArrayList<String> names;
	private ErrSnapshot Snap;
	private WebDriver driver;
    private ReadXML userinfo;
    private String IEDriver = "C:\\eclipse-standard-luna-R-win32-x86_64\\IEDriverServer.exe";
    private static final String KILL = "taskkill /F /IM ";
    
	@Before
	public void setUp() throws Exception {
		System.out.println("<OSC-IS BFT>");
		System.setProperty("webdriver.ie.driver", IEDriver);
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, false);
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
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[class='img-sitetitlelink']")));				
			}			
			
			assertTrue(driver.getPageSource().contains("OSC"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<OSC-IS BFT Failure>");
			fail("***Error: OSC-IS Budget & Finance Tool is unavailable***");
		}
		
		try {
			driver.findElement(By.xpath("//div[@id='zz15_TopNavigationMenuV4']/div/ul/li/ul/li[1]/a/span/span")).click();
			assertTrue(driver.getPageSource().contains("OSC"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<OSC-IS BFT Failure>");
			fail("***Error: OSC-IS Budget & Finance Tool Administration access failed***");
		}
				
		try {
			driver.findElement(By.xpath("//div[@id='zz15_TopNavigationMenuV4']/div/ul/li/ul/li[3]/a/span/span[1]")).click();
			assertTrue(driver.getPageSource().contains("OSC"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<OSC-IS BFT Failure>");
			fail("***Error: ​OSC-IS Budget & Finance Tool Reconciliation access failed***");
		}
		System.out.println("PASS");
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
		}
}
