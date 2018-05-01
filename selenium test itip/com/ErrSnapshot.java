package network.ite.run.com;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ErrSnapshot {
 public void take(WebDriver driver, String app){
	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	try {
		FileUtils.copyFile(scrFile, new File("C:\\testdaily\\snaplogs\\" + app + "ErrorSnap-" + new Date().getTime() + ".png"));
	} catch (IOException e1) {
		e1.printStackTrace();
	}		
  }	
}