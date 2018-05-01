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

public class tripTest {
	
	private ErrSnapshot Snap;
	private WebDriver driver;
    private final String APP = "trip";
    private ReadXML userinfo;
    private ArrayList<String> names;

	@Before
	public void setUp() throws Exception {
		
		try{
			System.out.println("<TRIP>");
			System.setProperty("webdriver.ie.driver", "C:\\eclipse-standard-luna-R-win32-x86_64\\IEDriverServer.exe");
			Snap = new ErrSnapshot();
			driver = new InternetExplorerDriver();
			userinfo = new ReadXML();
			names  = userinfo.readXML(APP);
			driver.manage().window().maximize();
		}catch (Throwable e){
			System.out.println("<TRIP Failure>");
			Snap.take(driver, APP);
			fail("***Error: Setup Failed***");			
		}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		System.out.println("\n");
	}

	@Test
	public void test() {
		driver.manage().timeouts().pageLoadTimeout(120,  TimeUnit.SECONDS);
		
		
		try {
			driver.get(names.get(0));
			// if there is Certificate Problem, then take screenshot and continue
			//Snap.take(driver, APP);
			//driver.findElement(By.id("overridelink")).click();
		} catch (Throwable e) {
			System.out.println("<TRIP Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to access TRIP***");
		}
		
		
		try{
			driver.findElement(By.cssSelector("a[href='status.aspx']")).click();
			assertTrue(driver.getPageSource().contains("DHS TRIP Case Status"));
		}catch(Throwable e){
			System.out.println("<TRIP Failure>");
			Snap.take(driver, APP);
			fail("***Error: TRIP Case Status TRIP***");			
		}
		
		try{
			driver.findElement(By.linkText("Click here to return and continue.")).click();
			assertTrue(driver.getPageSource().contains("Submit an Application to DHS TRIP"));
		}catch(Throwable e){
			System.out.println("<TRIP Failure>");
			Snap.take(driver, APP);
			fail("***Error: DHS TRIP TRIP***");			
		}
		System.out.println("PASS");
	}
}
