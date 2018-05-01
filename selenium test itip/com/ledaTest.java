package network.ite.run.com;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ledaTest {
	private ErrSnapshot Snap;
	private WebDriver driver;
	private WebDriverWait wait;
    private final String APP = "leda";
	private ReadXML userinfo;
	private ArrayList<String> names;

	@Before
	public void setUp() throws Exception {
		System.out.println("<LEDA>");
		System.setProperty("webdriver.ie.driver", "C:\\eclipse-standard-luna-R-win32-x86_64\\IEDriverServer.exe");
		Snap = new ErrSnapshot();
		driver = new InternetExplorerDriver();
		wait = new WebDriverWait(driver, 10);
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
			driver.findElement(By.name("username")).sendKeys(names.get(1));
			driver.findElement(By.name("password")).sendKeys(names.get(2));
			driver.findElement(By.name("password")).sendKeys(Keys.ENTER);
			String txt = ("https://topweb.tsa.dhs.gov/leda/static/base/img/dci_eagent_140x54.png");
			assertEquals(txt, driver.findElement(By.tagName("img")).getAttribute("src"));
		} catch (Throwable e) {
			System.out.println("<LEDA Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to login***");
		}

		try {
			WebElement ledaElem1 = wait.until(ExpectedConditions.elementToBeClickable(By.name("search")));
			ledaElem1.sendKeys("Zamora");
			driver.findElement(By.cssSelector("input[name='search'][role='textbox']")).sendKeys(Keys.ENTER);
			assertTrue(driver.getPageSource().contains("VINCENT.ZAMORA"));
		} catch (Throwable e) {
			System.out.println("<LEDA Failure>");
			Snap.take(driver, APP);
			fail("***Error: VINCENT.ZAMORA not found***");
		}
		
		try {
			driver.findElement(By.linkText("Logout")).click();
			Thread.sleep(2000);
		}catch(Throwable e){
			System.out.println("<LEDA Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to Logout***");			
		}
		System.out.println("PASS");
	}
}
