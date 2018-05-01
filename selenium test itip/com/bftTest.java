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

public class bftTest {
	
    private final String APP = "bft";
    private ArrayList<String> names;
	private ErrSnapshot Snap;
	private WebDriver driver;
    private ReadXML userinfo;
    private String IEDriver = "C:\\eclipse-standard-luna-R-win32-x86_64\\IEDriverServer.exe";
	private static final String KILL = "taskkill /F /IM ";
    
	@Before
	public void setUp() throws Exception {
		System.out.println("<BFT>");
		System.setProperty("webdriver.ie.driver", IEDriver);
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
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[class='img-sitetitlelink'][src='/_layouts/images/blank.gif']")));	
			}		
			assertTrue(driver.getPageSource().contains("OIA"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<OIA BFT Failure>");
			fail("***Error: Budget & Finance Tool (BFT) is unavailable***");
		}
		
		try {
			driver.findElement(By.xpath("//div[@id='zz18_TopNavigationMenuV4']/div/ul/li/ul/li[1]/a/span/span")).click();
			assertTrue(driver.getPageSource().contains("BFT"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<OIA BFT Failure>");
			fail("***Error: Budget & Finance Tool (BFT) Admin access failed***");
		}	
		
		try {
			driver.findElement(By.xpath("//div[@id='zz18_TopNavigationMenuV4']/div/ul/li/ul/li[3]/a/span/span")).click();
		}catch(Throwable e){
			Snap.take(driver, APP);
			System.out.println("<OIA BFT Failure>");
			fail("***Error: Spend Plan by Categories access failed***");
		}
		
		try {
			driver.findElement(By.xpath("//div[@id='zz18_TopNavigationMenuV4']/div/ul/li/ul/li[4]/a/span/span")).click();
			assertTrue(driver.getPageSource().contains("Spend"));
		}catch(Throwable e){
			Snap.take(driver, APP);
			System.out.println("<OIA BFT Failure>");
			fail("***Error: Spend Plan by Categories access failed***");
		}
		System.out.println("PASS");
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
	}	
}