/**
 * This program is to extract all the languages and different levels from the given website with its total count
 *  and display them.
 *  @author Plugin Immortals
 *  @since 2020-05-21
 */
package cts.automation.courseraWebAutomation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TotalCount 
{

	static WebDriver driver;
	
	By Filter = By.xpath("//button[@id='toggle_filters_button_button']");
	By Language = By.xpath("//span[@class='filter-name' and contains(text(),'Language')]");
	
	private static final String FILE_NAME_1 = System.getProperty("user.dir")+"//Output//TotalLanguage.txt";
	private static final String FILE_NAME_2 = System.getProperty("user.dir")+"//Output//TotalLevel.txt";
	
	public TotalCount(WebDriver driver)
	{
		this.driver=driver;
	}
	
	public void totalCountOfCourses() throws InterruptedException, IOException 
	{
		//System.setProperty("webdriver.chrome.driver", "C:/Users/asus/Desktop/Major Project/courseraWebDevelopment/driver/chromedriver.exe");
		//driver = new ChromeDriver();
		
		//System.out.println("1st Objective Complete");
		//Searching for the Web Development in the search textbox
		//String URL="https://www.coursera.org/search?query=web%20development&";
		//driver.navigate().to(URL);
		//driver.navigate().back();
		//String HomePage=driver.getWindowHandle();
		//driver.switchTo().window(HomePage);		
		//driver.navigate().back();

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//resources//cts//Data.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet worksheet = workbook.getSheetAt(0);		

		Properties prop=new Properties();
		FileInputStream input=new FileInputStream(System.getProperty("user.dir")+"//src//main//resources//cts//config.properties");
		prop.load(input);
		
		driver.get("https://www.coursera.org/search?query=web%20development&");	
		JavascriptExecutor js = (JavascriptExecutor) driver;
		//Resizing the windows ( mobile view ) to get better view and clarification
		Dimension dimension = new Dimension(900,806);
		driver.manage().window().setSize(dimension);
		Thread.sleep(2000);
		
		//Pressing the filter button
		driver.findElement(Filter).click();
		Thread.sleep(4000);
		driver.findElement(Language).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//button[@class='ais-RefinementList-showMore' and contains(text(),'Show more')]")).click();
		
		//Retrieving all the languages from the filter
		List<WebElement> Languages = driver.findElements(By.xpath("//*[@id=\"rendered-content\"]/div/div/div[1]/div[2]/div/div[1]/div[2]/div[1]/div/div[2]/div[2]/ul/div[1]/li/div[2]/div/div/div/ul"));
        //System.out.println(Languages.size());
		FileWriter fw=new FileWriter(FILE_NAME_1);
        for (WebElement webElement : Languages) 
        {
        	Thread.sleep(1000);
            String languageName = webElement.getText();
            //System.out.println(languageName);
            fw.write(languageName);
        }             
        fw.close();
        Thread.sleep(4000);
		driver.findElement(By.xpath("//span[@class='filter-name' and contains(text(),'Language')]")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//span[@class='filter-name' and contains(text(),'Level')]")).click();
		
		////Retrieving all the levels from the filter
		List<WebElement> Levels = driver.findElements(By.xpath("//*[@id=\"rendered-content\"]/div/div/div[1]/div[2]/div/div[1]/div[2]/div[1]/div/div[2]/div[2]/ul/div[2]/li/div[2]"));
        //System.out.println(Levels.size());
		FileWriter fw1=new FileWriter(FILE_NAME_2);
        for (WebElement webElement : Levels) 
        {
            String LevelName = webElement.getText();
            //System.out.println(LevelName);
            fw1.write(LevelName);
        }

        Thread.sleep(2000);
        //driver.quit();
        String homePage = driver.getWindowHandle();
        driver.switchTo().window(homePage);
      
        Dimension dimn = new Dimension(1051,806); //Setting the window size
   		driver.manage().window().setSize(dimn);
   		driver.manage().window().maximize();
   		//driver.quit();
   		fw1.close();
	}

}
