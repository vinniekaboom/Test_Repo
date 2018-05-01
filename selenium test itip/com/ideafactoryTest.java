package network.ite.run.com;

import static org.junit.Assert.*;

import java.util.ArrayList;
//import java.util.Set;
import java.util.concurrent.TimeUnit;

//import javax.swing.text.html.HTMLDocument.Iterator;








import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ideafactoryTest {
	
	private final String APP = "idea";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";

	@Before
	public void setUp() throws Exception {
		System.out.println("<Idea Factory>");
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

		try {
			driver.get(names.get(0));
			Popup pop = new Popup();
			WebDriverWait wait = new WebDriverWait(driver, 120); 
			if(pop.isAlertPresent(driver)) {
				pop.stupidpopup();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("_rfdSkinnedAgreeToTouCheckBox")));
			}			
			driver.findElement(By.id("_rfdSkinnedAgreeToTouCheckBox")).click();
			driver.findElement(By.cssSelector("input[type='submit'][value='I Agree']")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			assertTrue(driver.getPageSource().contains("Welcome:"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<Idea Factory Failure>");
			fail("***Error: Unable access Idea Factory***");
		}
		
		try {
						
			driver.findElement(By.id("ctl00_NewIdeaImageButton")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			assertTrue(driver.getPageSource().contains("Build New Idea"));
		} catch (Throwable e) {
			Snap.take(driver, APP);
			System.out.println("<Idea Factory Failure>");
			fail("***Error: Build New Idea Page failed***");
		}
		System.out.println("PASS");		
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
	}	
}
