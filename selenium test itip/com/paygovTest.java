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
import org.openqa.selenium.remote.DesiredCapabilities;

public class paygovTest {

	private final String APP = "paygov";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";


	@Before
	public void setUp() throws Exception {
		System.out.println("<PAY.GOV>");
		System.setProperty("webdriver.ie.driver", "C:\\eclipse-standard-luna-R-win32-x86_64\\IEDriverServer.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, false);
		capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);
		capabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP,true);
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
			// Under 'Already have an IACMS User Account?' section, click 'LOGIN' button
			driver.get(names.get(0));
			driver.findElement(By.cssSelector("input[type='submit'][value='Login']")).click();
			Thread.sleep(3000);
			
			// At 'TSA APPLICATIONS' login screen, enter username/password, click 'I AGREE' button
			driver.findElement(By.name("username")).sendKeys(names.get(1));
			driver.findElement(By.name("password")).sendKeys(names.get(2));
			driver.findElement(By.cssSelector("input[type='submit'][value='I AGREE']")).click();
			
			// does 'Indirect Air Carrier Management System' page load?
			assertTrue(driver.getPageSource().contains("Indirect Air Carrier Management System"));
		} catch (Throwable e) {
			System.out.println("<PAY.GOV Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to access IACMS (pay.gov)***");
		}

		try {
			// at left menus, click 'Search STAs'
			Thread.sleep(2000);
			driver.findElement(By.partialLinkText("Search STAs")).click();
			Thread.sleep(2000);
			// 'Search Security Threat Assessments (STAs)' page loads
			// enter search criteria
			// for 'First Name', enter 'test'
			
			//driver.findElement(By.name("localStaCriteria.firstName")).sendKeys("Vincent");
			driver.findElement(By.name("localStaCriteria.firstName")).sendKeys("test");
			Thread.sleep(2000);
			// click 'Search' button
			driver.findElement(By.cssSelector("input[name='submitButtonName'][type='submit'][value='search']")).click();
			Thread.sleep(2000);
			// does 'Results' page load?
			assertTrue(driver.getPageSource().contains("Search STAs: Results"));
		} catch (Throwable e) {
			System.out.println("<PAY.GOV Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Search STAs page cannot be found***");
		}
		
		/*
		try {
			driver.findElement(By.cssSelector("input[name='selectedItems'][type='checkbox'][value='61130163']")).click();
			Thread.sleep(2000);
			driver.findElement(By.cssSelector("input[type='submit'][value='Add to Cart']")).click();
			Thread.sleep(3000);
		} catch (Throwable e) {
			System.out.println("<PAY.GOV Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Search STAs page cannot be found***");
		}
		*/
		
		
		// get info of '61130163' record
		/*
		try {
			driver.findElement(By.cssSelector("input[name='selectedItems'][type='checkbox'][value='61130163']")).click();
			Thread.sleep(3000);
			driver.findElement(By.cssSelector("input[type='submit'][value='Checkout']")).click();
			assertTrue(driver.getPageSource().contains("Submission Date Timestamp"));
		} catch (Throwable e) {
			System.out.println("<PAY.GOV Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Select STAs not found***");
		}
		*/
		
		// select/tick '84D0AF08ABAB' record and then 'add to cart' 86309908
		// 108617519
		try {
			Thread.sleep(2000);
			//driver.findElement(By.cssSelector("input[name='selectedItems'][type='checkbox'][value='86309908']")).click();
			driver.findElement(By.cssSelector("input[name='selectedItems'][type='checkbox'][value='108617519']")).click();
			Thread.sleep(2000);
			driver.findElement(By.cssSelector("input[name='staCatalogForm.button'][type='submit'][value='Add to Cart']")).click();
			Thread.sleep(2000);
			// does 'STA Payment Results' page load
			assertTrue(driver.getPageSource().contains("STA Payment Results"));
			
		} catch (Throwable e) {
			System.out.println("<PAY.GOV Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Select STAs not found***");
		}
		
		
		
		// select/tick '84D0AF08ABAB' record and then 'checkout' 86309908
		//xpath = /html/body/table[3]/tbody/tr/td[3]/form/table/tbody/tr[4]/td/table/tbody/tr[4]/td[1]/input
		try {
			
			Thread.sleep(2000);
			//driver.findElement(By.cssSelector("input[name='selectedItems'][type='checkbox'][value='86309908']")).click();
			driver.findElement(By.cssSelector("input[name='selectedItems'][type='checkbox'][value='108617519']")).click();
			Thread.sleep(2000);
			
			/*
			driver.findElement(By.cssSelector("input[name='selectedItems'][type='checkbox'][value='86309908']")).click();
			Thread.sleep(3000);
			*/
			
			//driver.findElement(By.xpath("//html/body/table[3]/tbody/tr/td[3]/form/table/tbody/tr[4]/td/table/tbody/tr[4]/td[1]/input")).click();
			
			//shoppingCart_staCatalogForm_button
			//by id = shoppingCart_staCatalogForm_button
			
			//Thread.sleep(6000);
			//driver.findElement(By.id("shoppingCart_staCatalogForm_button")).click();
			driver.findElement(By.cssSelector("input[name='staCatalogForm.button'][class=button][id=shoppingCart_staCatalogForm_button][type='submit'][value='Checkout']")).click();
			Thread.sleep(2000);
			// does 'STA Payment Results' page load
			assertTrue(driver.getPageSource().contains("Would you like to continue"));
			
		} catch (Throwable e) {
			System.out.println("<PAY.GOV Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Select STAs not found***");
		}
		
		
		
		/*
		try {
			driver.findElement(By.cssSelector("input[type='submit'][value='continue']")).click();
			Thread.sleep(3000);
			assertTrue(driver.getPageSource().contains("Online Payment"));
		} catch (Throwable e) {
			System.out.println("<PAY.GOV Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Pay.Gov Failed***");
		}	
		*/
		
		// do 'checkout'
		/*
		try {
			Thread.sleep(2000);
			driver.findElement(By.cssSelector("input[type='submit'][value='Checkout']")).click();
			Thread.sleep(3000);
			// does 'Would you like to continue?' page load?
			assertTrue(driver.getPageSource().contains("Would you like to continue?"));
		} catch (Throwable e) {
			System.out.println("<PAY.GOV Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Pay.Gov Failed***");
		}	
		*/
		
		
		
		/*
		try {
			driver.findElement(By.cssSelector("input[type='submit'][value='continue']")).click();
			Thread.sleep(3000);
			// does 'Enter Payment Information' page load?
			assertTrue(driver.getPageSource().contains("Enter Payment Information"));
		} catch (Throwable e) {
			System.out.println("<PAY.GOV Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Pay.Gov Failed***");
		}	
		// done with PAY.GOV verification
		
		
		// go back to logout
		try {
			driver.findElement(By.cssSelector("input[type='submit'][value='Cancel']")).click();
			// STA Payment Results page loads
		} catch (Throwable e) {
			System.out.println("<PAY.GOV Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Return to STA Payment Results***");
		}
		
		
		try {			
			driver.findElement(By.linkText("Logout")).click();
			Thread.sleep(5000);
		}catch(Throwable e){
			System.out.println("<PAY.GOV Faiure>");
			Snap.take(driver, APP);
			fail("***Error: Logout failed***");
		}
		System.out.println("PASS");
		
		*/
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
	}
}
