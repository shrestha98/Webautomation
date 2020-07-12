/**
 * This program searches for "Enterprise" in the homepage and 
 * looks into the courses for "Campus under Product"
 * and fills the "Ready to transform" form with any one input as invalid 
 * and captures the error message and displays the error message.
 *  and display them.
 *  @author Plugin Immortals
 *  @since 2020-05-19
 */
package cts.automation.courseraWebAutomation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ForEnterprise 
{

	static WebDriver driver;
	By Enterprise = By.xpath("//a[contains(text(),'For Enterprise')]");
	By Started = By.xpath("//a[@class='cta-button' and contains(text(),'Get started')]");
	By Connect = By.xpath("//button[text()='Connect with us']");
	
	By Institution = By.xpath("//select[@id='Institution_Type__c']");
	By University = By.xpath("//option[contains(text(),'Private University')]");
	
	public ForEnterprise(WebDriver driver)
	{
		this.driver=driver;
	}
	
	public void errorMessageAtEnterprise() throws InterruptedException, IOException 
	{
		//System.setProperty("webdriver.chrome.driver", "C:/Users/asus/Desktop/Major Project/courseraWebDevelopment/driver/chromedriver.exe");
        
		//Disabling all the notifications by chrome and allowing to automate the browser
		//ChromeOptions options = new ChromeOptions();       
       // options.addArguments("disable-infobars");
       // driver = new ChromeDriver(options);
       // ChromeOptions options1 = new ChromeOptions();
       // options1.setPageLoadStrategy(PageLoadStrategy.NONE);
        
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//resources//cts//Data.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet worksheet = workbook.getSheetAt(0);
		

		Properties prop=new Properties();
		FileInputStream input=new FileInputStream(System.getProperty("user.dir")+"//src//main//resources//cts//config.properties");
		prop.load(input);
				
        //Getting the Home Page of the Coursera
        driver.manage().window().maximize();
        driver.get("https://www.coursera.org/");
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
        driver.findElement(Enterprise).click();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
        
        //Clicking the Product 
        Actions action = new Actions(driver);
        WebElement product = driver.findElement(By.linkText("Product"));
        action.moveToElement(product).perform();
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
        driver.findElement(By.linkText("For Campus")).click();
        
        //Switching to the next tab to pass the driver
        String parentwindow=driver.getWindowHandle();
        Set<String> winHandles = driver.getWindowHandles();
	    for(String newTab: winHandles)
	     {
	       if(!newTab.equals(parentwindow))
	       {
	        driver.switchTo().window(newTab);
	       }
	     }
	    
	    //Clicking the element to start filling up the form 
	    driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
	    Thread.sleep(2000);
	    //driver.findElement(By.cssSelector("a.cta-button:nth-child(3)"));
		driver.findElement(By.cssSelector("a.cta-button:nth-child(3)")).click();
        //driver.findElement(Started).click();
        
        //Inputting the details
        Thread.sleep(5000);
        driver.findElement(By.xpath(worksheet.getRow(9).getCell(1).getStringCellValue())).sendKeys(prop.getProperty("FirstName"));
        Thread.sleep(1000);
        driver.findElement(By.xpath(worksheet.getRow(10).getCell(1).getStringCellValue())).sendKeys(prop.getProperty("LastName"));
        Thread.sleep(1000);
        driver.findElement(By.xpath(worksheet.getRow(11).getCell(1).getStringCellValue())).sendKeys(prop.getProperty("EMailId"));
        Thread.sleep(1000);
        driver.findElement(By.xpath(worksheet.getRow(12).getCell(1).getStringCellValue())).sendKeys(prop.getProperty("Profession"));
        Thread.sleep(1000);
        driver.findElement(By.xpath(worksheet.getRow(13).getCell(1).getStringCellValue())).sendKeys(prop.getProperty("PhoneNo"));
        Thread.sleep(1000);
        driver.findElement(By.xpath(worksheet.getRow(14).getCell(1).getStringCellValue())).sendKeys(prop.getProperty("Company"));
               
        JavascriptExecutor js = (JavascriptExecutor) driver; //Scrolling the windows to make the element visible 
        js.executeScript("window.scrollBy(0,500)");
    
        driver.findElement(Institution).click();
        driver.findElement(University).click();
        Thread.sleep(1000); 
        
        //selecting the dropdowns
        Select students = new Select(driver.findElement(By.xpath(worksheet.getRow(15).getCell(1).getStringCellValue())));
        students.selectByIndex(2);
        Thread.sleep(3000);      
        Select users = new Select(driver.findElement(By.xpath(worksheet.getRow(16).getCell(1).getStringCellValue())));
        users.selectByIndex(3);
        Thread.sleep(3000);      
        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        js1.executeScript("window.scrollBy(0,190)");
        Thread.sleep(3000);   
        WebElement country=driver.findElement(By.xpath(worksheet.getRow(17).getCell(1).getStringCellValue())); //selecting country
        Select Country=new Select(country);
        Country.selectByIndex(105);
        Thread.sleep(3000);       
        WebDriverWait wait = new WebDriverWait(driver, 40); 
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id='State']")));        
        WebElement state=driver.findElement(By.xpath(worksheet.getRow(18).getCell(1).getStringCellValue())); //selecting state
        Select State=new Select(state);
        State.selectByIndex(20);
        Thread.sleep(3000);
        
        //Submitting the form
        driver.findElement(Connect).click();
        Thread.sleep(2000);
        
        //Printing the Error Message
        String errorMessage=driver.findElement(By.xpath(worksheet.getRow(19).getCell(1).getStringCellValue())).getText();
        System.out.println("Error Message is :- " + errorMessage);

        

	}

}
