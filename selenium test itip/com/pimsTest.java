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

public class pimsTest {
	
	private ErrSnapshot Snap;
	private WebDriver driver;
    private final String APP = "pims";
    private ReadXML userinfo;
    private ArrayList<String> names;
    
	@Before
	public void setUp() throws Exception {
		System.out.println("<PIMS>");
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
		System.out.println("\n");
	}

	@Test
	public void test() {
		driver.manage().timeouts().pageLoadTimeout(180,  TimeUnit.SECONDS);		
		try {
			driver.get(names.get(0));	
			Popup pop = new Popup();
			WebDriverWait wait = new WebDriverWait(driver, 120); 	
			
			// if there is Certificate Problem, then take screenshot and continue
			//Snap.take(driver, APP);
			//driver.findElement(By.id("overridelink")).click();
			
			if(pop.isAlertPresent(driver)) {
				pop.stupidpopup();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("PIMS BI Tool")));				
			}			
			assertTrue(driver.getPageSource().contains("PIMS BI Tool"));
		} catch (Throwable e) {
			System.out.println("<PIMS Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to access PIMS***");
		}
		
		try {
			driver.findElement(By.linkText("PIMS BI Tool")).click();
			assertTrue(driver.getPageSource().contains("Create Report"));
		} catch (Throwable e) {
			System.out.println("<PIMS Failure>");
			Snap.take(driver, APP);
			fail("***Error: PIMS BI Toolfile failed***");
		}	
		
		try {
			driver.findElement(By.linkText("Logout")).click();
		}catch(Throwable e){
			System.out.println("<PIMS Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to Logout***");
		}
		System.out.println("PASS");
	}
}