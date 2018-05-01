package network.ite.run.com;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ooiwebappsTest {
	private final String APP = "ooiwebapps";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";
	
	@Before
	public void setUp() throws Exception {
		System.out.println("<OOIWebApps>");
	
		File IEDriver = new File("C:\\eclipse-standard-luna-R-win32-x86_64\\IEDriverServer.exe");
		System.setProperty("webdriver.ie.driver", IEDriver.getAbsolutePath() ); 
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, false);
		driver = new InternetExplorerDriver(capabilities);
		Snap = new ErrSnapshot();
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
			WebDriverWait wait = new WebDriverWait(driver, 220); 			
			if(pop.isAlertPresent(driver)) {
				pop.stupidpopup();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[class='LogoImg']")));				
			}
			//Thread.sleep(2000);
			assertTrue(driver.getPageSource().contains("OI Web Apps version"));
		} catch (Throwable e) {
			System.out.println("<OOIWebApps Failure>: OI Web Apps version is not listed.");
			Snap.take(driver, APP);
			fail("***Error: Unable to access OOIWebApps***");
		}
		
		
/*		
		try {
			// (left column, 1st object) Audits/Inspections + Assigned Audits/Inspections link
			Thread.sleep(4000);
			driver.findElement(By.id("ctl00_cphBody_GridButtons2_lbnReviews")).click();
			Thread.sleep(2000);
			assertTrue(driver.getPageSource().contains("All Projects"));
		} catch (Throwable e) {
			System.out.println("<OOIWebApps Failure>: All Projects not found.");
			Snap.take(driver, APP);
			fail("***Error: Internal Reviews***");
		}
*/		
		
		
	    try{
	    	// (top menu, 3rd item, Links link) Links -> (3rd item) Criminal Investigation Time Report
			//Thread.sleep(4000);
			WebElement firstElement = driver.findElement(By.xpath("//div[@id='ctl00_Header1_Menu1']/ul/li[3]/a/span"));
			//Thread.sleep(1000);
			WebElement secondElement = driver.findElement(By.xpath("//div[@id='ctl00_Header1_Menu1']/ul/li[3]/div/ul/li[3]/a/span"));
			//Thread.sleep(2000);
			Actions builder = new Actions(driver);
			builder.moveToElement(firstElement).perform();
			//Thread.sleep(2000);
			builder.moveToElement(secondElement).click().perform();
			//Thread.sleep(2000);
			assertTrue(driver.getPageSource().contains("Criminal Investigation Time Report"));
		 }catch (Throwable e){
			 System.out.println("<OOIWebApps Failure>");
			 Snap.take(driver, APP);
			 fail("***Error: Criminal Investigation Time Report***");	    	
		 }
	}
	
	
	
	
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
		}
}