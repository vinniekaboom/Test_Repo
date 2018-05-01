package network.ite.run.com;

import static org.junit.Assert.*;

//import java.awt.Robot;
//import java.awt.event.KeyEvent;
import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class smaTest {
    private final String APP = "sma";
	private ErrSnapshot Snap;
	private WebDriver driver;
	private ReadXML userinfo ;
	private ArrayList<String> names;
	private static final String KILL = "taskkill /F /IM ";

	@Before
	public void setUp() throws Exception {
		System.out.println("<SMA>");
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
	    	
			Thread.sleep(1000);
			driver.findElement(By.xpath("//input[@id='btnloginAD']")).click();
			Thread.sleep(1000);
			
			// pre 3/29/2016
			// driver.findElement(By.name("username")).sendKeys(names.get(1));
			// driver.findElement(By.name("pwd")).sendKeys(names.get(2));
			// driver.findElement(By.cssSelector("input[type='button'][value='Log In']")).click();
			
			// 3/29/2016 user/pass/submit fields changed
			driver.findElement(By.name("txtuserid")).sendKeys(names.get(1));
			driver.findElement(By.name("txtPassword")).sendKeys(names.get(2));
			driver.findElement(By.cssSelector("input[type='submit'][name='btnlogin']")).click();
			Thread.sleep(2000);
			
			//Thread.sleep(5000);			
			//Robot robot  = new Robot();
			//robot.keyPress(KeyEvent.VK_A);
			//robot.keyPress(KeyEvent.VK_S);
			//robot.keyPress(KeyEvent.VK_T);
			//robot.keyPress(KeyEvent.VK_PERIOD);
			//robot.keyPress(KeyEvent.VK_A);
			//robot.keyPress(KeyEvent.VK_D);
			//robot.keyPress(KeyEvent.VK_M);
			//robot.keyPress(KeyEvent.VK_I);
			//robot.keyPress(KeyEvent.VK_N);
			
		    //robot.keyPress(KeyEvent.VK_TAB);
		    //robot.keyRelease(KeyEvent.VK_TAB);

			//robot.keyPress(KeyEvent.VK_S);
			//robot.keyPress(KeyEvent.VK_M);
			//robot.keyPress(KeyEvent.VK_A);
			//robot.keyRelease(KeyEvent.VK_A);
			//robot.keyPress(KeyEvent.VK_A);
			//robot.keyPress(KeyEvent.VK_S);
			//robot.keyPress(KeyEvent.VK_T);
			//robot.keyPress(KeyEvent.VK_A);
			//robot.keyPress(KeyEvent.VK_D);
			//robot.keyPress(KeyEvent.VK_M);
			//robot.keyPress(KeyEvent.VK_I);
			//robot.keyPress(KeyEvent.VK_N);
		    
		    //Thread.sleep(2000);
			
			//robot.keyPress(KeyEvent.VK_ENTER);
			//robot.keyRelease(KeyEvent.VK_ENTER);
			//Thread.sleep(2000);
			
			//robot.keyPress(KeyEvent.VK_ENTER);
			//robot.keyRelease(KeyEvent.VK_ENTER);			
			
			//assertTrue(driver.getPageSource().contains("Welcome,"));
			
			assertTrue(driver.getPageSource().contains("User Agreement"));
		} catch (Throwable e) {
			System.out.println("<SMA Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable to access SMA***");
		}
		
		try {
			driver.findElement(
			By.cssSelector("input[type='submit'][value='Accept the Agreement']")).click();
			assertTrue(driver.getPageSource().contains("OSC Applications"));
		} catch (Throwable e) {
			System.out.println("<SMA Failure>");
			Snap.take(driver, APP);
			fail("***Error: OSC Applications page cannot be found***");
		}
		
		
		try {
			driver.findElement(By.linkText("Service Management Application (SMA) – Remedy")).click();
			assertTrue(driver.getPageSource().contains("BMC Remedy Action Request System"));
		} catch (Throwable e) {
			System.out.println("<SMA Failure>");
			Snap.take(driver, APP);
			fail("***Error: SMA login page cannot be found***");
		}
		System.out.println("PASS");
		
/*		
		try {
			//driver.findElement(By.linkText("Logout")).click();
			driver.findElement(By.linkText("Log Out")).click();
		}catch(Throwable e){
			System.out.println("<SMA Failure>");
			Snap.take(driver, APP);
			fail("***Error: Unable To Logout***");	
		}
		System.out.println("PASS");
	
*/
	}
	public static void killProcess() throws Exception {
		Runtime.getRuntime().exec(KILL + "iexplore.exe");
		}
}
