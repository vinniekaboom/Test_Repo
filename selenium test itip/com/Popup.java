package network.ite.run.com;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

public class Popup {

	public void stupidpopup() throws InterruptedException{
		Robot robot;
		try {
			robot = new Robot();
 
	        robot.keyPress(KeyEvent.VK_C);
	        robot.keyPress(KeyEvent.VK_MINUS);
	        robot.keyPress(KeyEvent.VK_A);
	        robot.keyPress(KeyEvent.VK_U);
	        robot.keyPress(KeyEvent.VK_T);
	        robot.keyPress(KeyEvent.VK_O);
	        robot.keyPress(KeyEvent.VK_M);
	        robot.keyPress(KeyEvent.VK_A);
	        robot.keyPress(KeyEvent.VK_T);
	        robot.keyPress(KeyEvent.VK_I);
	        robot.keyPress(KeyEvent.VK_O);
	        robot.keyPress(KeyEvent.VK_N);        
	
	        robot.keyPress(KeyEvent.VK_TAB);
	        robot.keyRelease(KeyEvent.VK_TAB);
	        		        
	        robot.keyPress(KeyEvent.VK_SHIFT); 
	        robot.keyPress(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_SHIFT);
	        robot.keyPress(KeyEvent.VK_O);
	        robot.keyPress(KeyEvent.VK_L);
	        robot.keyPress(KeyEvent.VK_I);
	        robot.keyPress(KeyEvent.VK_B);
	        robot.keyPress(KeyEvent.VK_A);
	        robot.keyPress(KeyEvent.VK_L); 
	        robot.keyPress(KeyEvent.VK_4);
	        robot.keyPress(KeyEvent.VK_1);
	        
	        robot.keyPress(KeyEvent.VK_ENTER);
	        robot.keyRelease(KeyEvent.VK_ENTER);
	        
            Thread.sleep(3000);
        
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
	}
	
	public boolean isAlertPresent(WebDriver drive) {
		try {
		  drive.switchTo().alert();
		  return true;
		} catch (NoAlertPresentException e) {
		  return false;
		}
	}
}

