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
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;

public class fasTest {
	private final String APP = "fas";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";

	@Before
	public void setUp() throws Exception {
		System.out.println("<FAS>");
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
		driver.manage().timeouts().pageLoadTimeout(180,  TimeUnit.SECONDS);	
		
		try {
			driver.get(names.get(0));
			driver.findElement(By.xpath("//input[@id='username']")).sendKeys(names.get(1));
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys(names.get(2));Thread.sleep(3000);
			driver.findElement(By.cssSelector("button[id='loginButton'][type='button']")).click();
			
			Thread.sleep(3000);
			
			// handle Login Disclaimer Accept
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ENTER);
			action.perform();
			
			Thread.sleep(3000);
			
			//there is not a valid login, check for invalid login message for a Pass
			assertTrue(driver.getPageSource().contains("Invalid login"));
			
		} catch (Throwable e) {
			System.out.println("<FAS Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to access FAS***");
		}	
		
		/*
		try {
			driver.findElement(By.linkText("Reports")).click();
			assertTrue(driver.getPageSource().contains("Start by selecting a report from the left menu"));
			assertTrue(driver.getPageSource().contains("Invalid login"));
		} catch (Throwable e) {
			System.out.println("<FAS Failure>");
			Snap.take(driver, APP);
			fail("***Error: Reports Page not found***");
		}
		
		try {
			driver.findElement(By.xpath("//div[@id='appmenu1']/ul/li[1]/a")).click();
		}catch(Throwable e){
			System.out.println("<FAS Failure>");
			Snap.take(driver, APP);
			fail("***Error: Failed to Logout***");	
		}
		
		*/
		
		System.out.println("PASS");
	}
	
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
	}
}
