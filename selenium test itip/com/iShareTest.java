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

public class iShareTest{
	private ErrSnapshot Snap;
	private WebDriver driver;
    private final String APP = "ishare";

	@Before
	public void setUp() throws Exception {
		System.out.println("<ISHARE>");
		System.setProperty("webdriver.ie.driver", "C:\\eclipse-standard-luna-R-win32-x86_64\\IEDriverServer.exe");
		Snap = new ErrSnapshot();
		driver = new InternetExplorerDriver();
		driver.manage().window().maximize();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		System.out.println("\n");		
	}

	@Test
	public void test() {
		ReadXML userinfo = new ReadXML();
		ArrayList<String> names  = userinfo.readXML(APP);
		
		try {
			driver.get(names.get(0));
			Popup pop = new Popup(); 
			if(pop.isAlertPresent(driver)) {
				pop.stupidpopup();
			}			
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} catch (Throwable e) {
			System.out.println("<ISHARE Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to access iShare site***");
		}
		
		try {
			
			driver.findElement(By.linkText("Applications")).click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} catch (Throwable e) {
			System.out.println("<ISHARE Failure>");
			Snap.take(driver, APP);
			fail("***Error: Claims Management Page not found***");
		}
		System.out.println("PASS");
	}
}

