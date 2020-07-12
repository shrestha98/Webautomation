/**
 * This program searches all the web development courses and applies various filters 
 *  and display the first two courses with name,rating and duration.
 *  @author Plugin Immortals
 *  @since 2020-05-21
 */
package cts.automation.courseraWebAutomation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CoursesOffered 
{

	static WebDriver driver;
	
	Properties properties;
	
	By durationOf_1 = By.xpath("//div[@class='ProductGlance']//span[contains(text(),'Approx. 6 months to complete')]");
	By durationOf_2 = By.xpath("//div[@class='ProductGlance _9cam1z p-t-2']//span[contains(text(),'Approx. 25 hours to complete')]");
	
	private static final String FILE_NAME = System.getProperty("user.dir")+"//Output//TotalCourse.xlsx";
	private static final String ScreenShot1 = System.getProperty("user.dir")+"//Output//ScreenShots//FirstCourse.png";
	private static final String ScreenShot2 = System.getProperty("user.dir")+"//Output//ScreenShots//SecondCourse.png";
	
	public CoursesOffered(WebDriver driver) 
	{
		this.driver = driver;
	}
	
	public void totalCoursesOffered() throws InterruptedException, IOException, InvalidFormatException 
	{
		
		//System.setProperty("webdriver.chrome.driver", "C:/Users/asus/Desktop/Major Project/courseraWebDevelopment/driver/chromedriver.exe");
		//driver = new ChromeDriver();
		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//resources//cts//Data.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet worksheet = workbook.getSheetAt(0);
		
		Properties prop=new Properties();
		FileInputStream input=new FileInputStream(System.getProperty("user.dir")+"//src//main//resources//cts//config.properties");
		prop.load(input);
		
		//worksheet.getRow(1).getCell(3).getStringCellValue()
		//String browserName=prop.getProperty("browser");
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		//Searching for the Web Development through text search
		driver.manage().window().maximize();
		String Course = prop.getProperty("COURSE");
		driver.get("https://www.coursera.org/search?query=" +Course+"&");
		Thread.sleep(2000);
		
		//Filtering all Web Development courses in English language
		String originalUrl = driver.getCurrentUrl();
		String newUrl = originalUrl + "index=prod_all_products_term_optimization";
		String Language = prop.getProperty("LANGUAGE");
		String firstFilter = newUrl + "&allLanguages=" + Language;
		
		//Second filtering all the English languages courses on the basis of Learning Level
		String Level = prop.getProperty("LEVEL");
		String secondFilter = firstFilter + "&productDifficultyLevel=" + Level;
		driver.get(secondFilter);
		
		//Getting all the courses offered for Web Development after applying filter
		String homePage = driver.getWindowHandle();  //Storing the parent window
		Thread.sleep(3000);
		
		//Making a list of the Courses by initializing a List
		List<WebElement> noOfCourses = driver.findElements(By.xpath(worksheet.getRow(1).getCell(1).getStringCellValue()));
		//System.out.println("Courses are:");
		
		InputStream inp = new FileInputStream(FILE_NAME); 
	    Workbook wb = WorkbookFactory.create(inp); 
	    Sheet sheet = wb.getSheetAt(0);
	    //int num = sheet.getLastRowNum();
	    Row row2 = sheet.createRow(1);
		//Traversing one by one and getting the names of the courses
	    
		for (int i = 0; i < 6; i++) {
			//System.out.println(noOfCourses.get(i).getText());
			row2.createCell(i).setCellValue(noOfCourses.get(i).getText());
			Thread.sleep(1000);
		}
		//System.out.println("-----------------------------------------------------------------");
		Thread.sleep(3000);
		
		//Making a list of the Ratings by initializing a List
		List<WebElement> ratings = driver.findElements(By.xpath(worksheet.getRow(2).getCell(1).getStringCellValue()));
		//System.out.println("Ratings  are:");

		//Traversing one by one and getting the ratings of the first three courses
		Row row4 = sheet.createRow(3);
		for (int i = 0; i < 6; i++) {
			
			//System.out.println(ratings.get(i).getText());
			row4.createCell(i).setCellValue(ratings.get(i).getText());
			Thread.sleep(1000);
		}
		//System.out.println("-----------------------------------------------------------------");
		Thread.sleep(5000);
		
		/*Now getting to the first window to click on every Courses
		 * to get their approximate time at which the 
		 * courses are completed
		 * We take first two courses and find the time taken to complete that 
		 */
		
		//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
		//Properties tabs = null;
		//driver.switchTo().window((String) tabs.get(1));
		
		//Clicking on the first course
		WebElement firstTitle=driver.findElement(By.xpath(worksheet.getRow(3).getCell(1).getStringCellValue()));
		firstTitle.click();
		Thread.sleep(3000);
		String firstTab=driver.getWindowHandle(); //Storing the second window

		//Switching to the windows to pass the driver
		Set<String> totalTabs = driver.getWindowHandles();	
		Iterator<String> iteration = totalTabs.iterator();
		homePage = iteration.next();
		firstTab = iteration.next();
		Thread.sleep(3000);
		driver.switchTo().window(firstTab); //Switching to the First course
		Thread.sleep(3000);
		TakesScreenshot scrShot =((TakesScreenshot)driver);
	    File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
	    File DestFile=new File(ScreenShot1);
	    FileUtils.copyFile(SrcFile, DestFile);
	    Thread.sleep(3000);
		js.executeScript("window.scrollBy(0,800)"); //Window scroll
		Thread.sleep(3000);
		String durationOf1 = driver.findElement(durationOf_1).getText();
		//System.out.println("Duration of 1st Course :  || " + durationOf1);
		Row row6 = sheet.createRow(5);
		row6.createCell(0).setCellValue(durationOf1);

		driver.close(); //Closing the Tab	
     	Thread.sleep(3000);
     	
     	//switching back to the parent window
     	driver.switchTo().window(homePage);
     	
		//Clicking on the second course
		WebElement secondCourse=driver.findElement(By.xpath(worksheet.getRow(4).getCell(1).getStringCellValue()));
		secondCourse.click();
        String secondTab=driver.getWindowHandle();                  
        Set<String> windoidss = driver.getWindowHandles(); 		
   		Iterator<String> iteration1 = windoidss.iterator();
   		homePage = iteration1.next();
   		secondTab = iteration1.next();
   		
   	    driver.switchTo().window(secondTab); //switching the tab to the second course and pass the driver   
        Thread.sleep(5000);
        TakesScreenshot scrShot1 =((TakesScreenshot)driver);
	    File SrcFile1=scrShot1.getScreenshotAs(OutputType.FILE);
	    File DestFile1=new File(ScreenShot2);
	    FileUtils.copyFile(SrcFile1, DestFile1);
	    Thread.sleep(3000);
        
        //Putting it into the Mobile view for better Clarification
   		Dimension dimn = new Dimension(1051,806); //Setting the window size
   		driver.manage().window().setSize(dimn);
        js.executeScript("window.scrollBy(0,800)");
        Thread.sleep(3000);
        String durationOf2=driver.findElement(durationOf_2).getText();
        row6.createCell(1).setCellValue(durationOf2);
        //System.out.println("Duration of 2nd Course :  || " + durationOf2);
        driver.manage().window().maximize(); //Maximizing the window
        driver.close(); //Closing the Tab        
        
        driver.switchTo().window(homePage);
        Thread.sleep(2000);
        //driver.quit();
        FileOutputStream fileOut = new FileOutputStream(FILE_NAME); 
	    wb.write(fileOut); 
	    fileOut.close();

	}

}
