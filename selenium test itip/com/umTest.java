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

public class umTest {
    private final String APP = "um";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;

	@Before
	public void setUp() throws Exception {
		System.out.println("<User Management>");
		System.setProperty("webdriver.ie.driver", "C:\\eclipse-standard-luna-R-win32-x86_64\\IEDriverServer.exe");
		driver = new InternetExplorerDriver();
		Snap = new ErrSnapshot();
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
			driver.findElement(By.id("username")).sendKeys(names.get(1));
			driver.findElement(By.id("password")).sendKeys(names.get(2));
			driver.findElement(By.cssSelector("input[type='submit'][value='I AGREE']")).click();
			assertTrue(driver.getPageSource().contains("User Management Home Page"));
		} catch (Throwable e) {
			System.out.println("<User Management Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to access User Management***");
		}

		try {
			driver.findElement(By.linkText("IACMS")).click();
			assertTrue(driver.getPageSource().contains("Add/Remove IACMS Roles"));
		} catch (Throwable e) {
			System.out.println("<User Management Failure>");
			Snap.take(driver, APP);
			fail("***Error: Add/Remove IACMS Users Roles cannot be found***");
		}

		try {
			driver.findElement(By.linkText("IMS")).click();
			assertTrue(driver.getPageSource().contains("Add/Remove IMS Users"));
		} catch (Throwable e) {
			System.out.println("<User Management Failure>");
			Snap.take(driver, APP);
			fail("***Error: Add/Remove IMS Users cannot be found***");
		}

		try {
			driver.findElement(By.linkText("KSMS")).click();
			assertTrue(driver.getPageSource().contains("Add/Remove KSMS Users"));
		} catch (Throwable e) {
			System.out.println("<User Management Failure>");
			Snap.take(driver, APP);
			fail("***Error: Add/Remove KSMS Users cannot be found***");
		}

		try {
			driver.findElement(By.linkText("LInKS")).click();
			assertTrue(driver.getPageSource().contains("Add/Remove LInKS Users"));
		} catch (Throwable e) {
			System.out.println("<User Management Failure>");
			Snap.take(driver, APP);
			fail("***Error: Add/Remove LInKS Users cannot be found***");
		}

		try {
			//WebDriverWait wait = new WebDriverWait(driver, 20);
			driver.findElement(By.linkText("Logout")).click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			//driver.findElement(By.name("Close_Button")).click();
			//Alert myAlert = wait.until(ExpectedConditions.alertIsPresent());
			//myAlert.accept();
		}catch(Throwable e){
			System.out.println("<User Management Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to Logout***");		
		}
		System.out.println("PASS");
	}
}
