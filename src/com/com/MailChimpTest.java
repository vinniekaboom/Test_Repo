package com;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class MailChimpTest  {

public static String url= "http://mailchimp.com/";
public static WebDriver driver;
public static ChromeOptions options;
public static List<WebElement> myList;
ArrayList<String> bio  = new ArrayList<String>();

String person, title, description;

@Before
public void setUp() throws Exception {
	System.setProperty("webdriver.chrome.driver", "C://Drivers/chromedriver.exe");
	options = new ChromeOptions();
	options.setExperimentalOption("useAutomationExtension", false);
	options.addArguments("--start-maximized");
	driver = new ChromeDriver(options);
  }
  
@Test
public void test() throws InterruptedException {	  
	driver.get(url);
	driver.findElement(By.xpath("//*[@id='page-home']/body/footer/div/div[1]/div[4]/ul/li[1]/a")).click();
    myList=driver.findElements(By.className("mb1"));
    
	int x = 1;  
	for(int i = 0; i < myList.size(); i++){
		person = driver.findElement(By.xpath("//*[@id=\"page-about\"]/body/div[2]/article/div/section[5]/div[2]/div[" + x + "]/a/h3")).getText();
		title  = driver.findElement(By.xpath("//*[@id=\"page-about\"]/body/div[2]/article/div/section[5]/div[2]/div[" + x + "]/a/span")).getText();
		driver.findElement(By.xpath("//*[@id=\"page-about\"]/body/div[2]/article/div/section[5]/div[2]/div[" + x + "]/a/h3")).click();		
		Thread.sleep(3000);		
		description = driver.findElement(By.xpath("//*[@id=\"bio_view\"]/div/div[2]/div[2]/p")).getText();		
		driver.findElement(By.xpath("//*[@id=\"bio_view\"]/div/span")).click();	
		bio.add(person + "," + title + "," + description);
		Thread.sleep(3000);
		
		x++;
	}
	
	FileCSV.writeToCVS (bio);		
}
  
@After
public void tearDown() throws Exception {
	driver.close();
	}
}