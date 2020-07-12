package cts.automation.courseraWebAutomation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class test 
{
	static WebDriver driver;
	private static final String FILE_NAME = "C:\\Users\\asus\\Desktop\\Major Project\\output.xlsx";
	private static final String FILE_NAME_1 = "C:\\Users\\asus\\Desktop\\Major Project\\Language.txt";
	private static final String FILE_NAME_2 = "C:\\Users\\asus\\Desktop\\Major Project\\Level.txt";
	
	public static void main(String[] args) throws InterruptedException, InvalidFormatException, IOException 
	{
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//driver//chromedriver.exe");
		driver = new ChromeDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.manage().window().maximize();
		driver.get("https://www.coursera.org/search?query=Web+Development&");
		Thread.sleep(2000);
		
		//Filtering all Web Development courses in English language
		String originalUrl = driver.getCurrentUrl();
		String newUrl = originalUrl + "index=prod_all_products_term_optimization";
		String firstFilter = newUrl + "&allLanguages=" + "English";

		//Second filtering all the English languages courses on the basis of Learning Level
		String secondFilter = firstFilter + "&productDifficultyLevel=" + "Beginner";
		driver.get(secondFilter);
		
		//Getting all the courses offered for Web Development after applying filter
		String homePage = driver.getWindowHandle();  //Storing the parent window
		Thread.sleep(3000);
		
		//Making a list of the Courses by initializing a List
		List<WebElement> noOfCourses = driver.findElements(By.xpath("//h2[@class='color-primary-text card-title headline-1-text']"));
		//System.out.println("Courses are:");
	
		InputStream inp = new FileInputStream(FILE_NAME); 
	    Workbook wb = WorkbookFactory.create(inp); 
	    Sheet sheet = wb.getSheetAt(0); 
	    //int num = sheet.getLastRowNum(); 
	    Row row2 = sheet.createRow(1);
	    
		//Traversing one by one and getting the names of the courses
		for (int i = 0; i < 6; i++) 
		{
			//System.out.println(noOfCourses.get(i).getText());
			row2.createCell(i).setCellValue(noOfCourses.get(i).getText());
			Thread.sleep(1000);
		}
		  
	    Thread.sleep(1000);
	    
		//System.out.println("-----------------------------------------------------------------");
		Thread.sleep(3000);
		
		//Making a list of the Ratings by initializing a List
		List<WebElement> ratings = driver.findElements(By.xpath("//span[@class='ratings-text']"));
		//System.out.println("Ratings  are:");

		//Traversing one by one and getting the ratings of the first three courses
		Row row4 = sheet.createRow(3);
		for (int i = 0; i < 6; i++) {
			row4.createCell(i).setCellValue(ratings.get(i).getText());
			Thread.sleep(1000);
		}
		
		//System.out.println("-----------------------------------------------------------------");
		Thread.sleep(5000);
		WebElement firstTitle=driver.findElement(By.xpath("(//h2[@class='color-primary-text card-title headline-1-text'])[1]"));
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
		js.executeScript("window.scrollBy(0,800)"); //Window scroll
		Thread.sleep(3000);
		String durationOf1 = driver.findElement(By.xpath("//div[@class='ProductGlance']//span[contains(text(),'Approx. 6 months to complete')]")).getText();
		Row row6 = sheet.createRow(5);
		row6.createCell(0).setCellValue(durationOf1);
		//"Duration of 1st Course :  || " + 

		driver.close(); //Closing the Tab	
     	Thread.sleep(3000);
     	
     	//switching back to the parent window
     	driver.switchTo().window(homePage);
     	
		//Clicking on the second course
		WebElement secondCourse=driver.findElement(By.xpath("(//h2[@class='color-primary-text card-title headline-1-text'])[2]"));
		secondCourse.click();
        String secondTab=driver.getWindowHandle();                  
        Set<String> windoidss = driver.getWindowHandles(); 		
   		Iterator<String> iteration1 = windoidss.iterator();
   		homePage = iteration1.next();
   		secondTab = iteration1.next();
   		
   	    driver.switchTo().window(secondTab); //switching the tab to the second course and pass the driver   
        Thread.sleep(5000);
        
        //Putting it into the Mobile view for better Clarification
   		Dimension dimn = new Dimension(1051,806); //Setting the window size
   		driver.manage().window().setSize(dimn);
        js.executeScript("window.scrollBy(0,800)");
        Thread.sleep(3000);
        String durationOf2=driver.findElement(By.xpath("//div[@class='ProductGlance _9cam1z p-t-2']//span[contains(text(),'Approx. 25 hours to complete')]")).getText();
        //Row row8 = sheet.createRow(7);
		row6.createCell(1).setCellValue(durationOf2);
		//Duration of 2nd Course :  || " + 
        //System.out.println("Duration of 2nd Course :  || " + durationOf2);
        driver.manage().window().maximize(); //Maximizing the window
        driver.close(); //Closing the Tab
             
        driver.switchTo().window(homePage);
        Thread.sleep(2000);
        
        driver.get("https://www.coursera.org/search?query=web%20development&");	
		//JavascriptExecutor js = (JavascriptExecutor) driver;
		//Resizing the windows ( mobile view ) to get better view and clarification
		Dimension dimension = new Dimension(900,806);
		driver.manage().window().setSize(dimension);
		Thread.sleep(2000);
		
		//Pressing the filter button
		driver.findElement(By.xpath("//button[@id='toggle_filters_button_button']")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//span[@class='filter-name' and contains(text(),'Language')]")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//button[@class='ais-RefinementList-showMore' and contains(text(),'Show more')]")).click();
		
		//Retrieving all the languages from the filter
		List<WebElement> Languages = driver.findElements(By.xpath("//*[@id=\"rendered-content\"]/div/div/div[1]/div[2]/div/div[1]/div[2]/div[1]/div/div[2]/div[2]/ul/div[1]/li/div[2]/div/div/div/ul"));
        //System.out.println(Languages.size());
		//int count=sheet.getLastRowNum();
		//count=count+1;
		FileWriter fw=new FileWriter(FILE_NAME_1);
        for (WebElement webElement : Languages) 
        {
        	//Row row10 = sheet.createRow(count);
            String languageName = webElement.getText();
            fw.write(languageName);
            //row10.createCell(0).setCellValue(languageName);
            //count++;
        }
        
        fw.close();
        Thread.sleep(4000);
		driver.findElement(By.xpath("//span[@class='filter-name' and contains(text(),'Language')]")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//span[@class='filter-name' and contains(text(),'Level')]")).click();
		
		////Retrieving all the levels from the filter
		List<WebElement> Levels = driver.findElements(By.xpath("//*[@id=\"rendered-content\"]/div/div/div[1]/div[2]/div/div[1]/div[2]/div[1]/div/div[2]/div[2]/ul/div[2]/li/div[2]"));
        //System.out.println(Levels.size());
		//int count1=sheet.getLastRowNum();
		FileWriter fw1=new FileWriter(FILE_NAME_2);
        for (WebElement webElement : Levels) 
        {
        	//Row row12 = sheet.createRow(count1);
            String LevelName = webElement.getText();
            fw1.write(LevelName);
            //row12.createCell(0).setCellValue(LevelName);
           // count1++;
        }
        
        fw1.close();
        FileOutputStream fileOut = new FileOutputStream(FILE_NAME); 
	    wb.write(fileOut); 
	    fileOut.close();
	    
        Thread.sleep(2000);
        //driver.quit();
        //String homePage = driver.getWindowHandle();
        driver.switchTo().window(homePage);
        
        //Dimension dimn = new Dimension(1051,806); //Setting the window size
   		driver.manage().window().setSize(dimn);
   		driver.manage().window().maximize();
   		
   		Thread.sleep(4000);
   		
   		driver.get("https://www.coursera.org/");
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
        driver.findElement(By.xpath("//a[contains(text(),'For Enterprise')]")).click();
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
        driver.findElement(By.xpath("//a[@class='cta-button' and contains(text(),'Get started')]")).click();
        
        //Inputting the details
        Thread.sleep(5000);
        driver.findElement(By.xpath("//input[@id='FirstName']")).sendKeys("Dibyajit");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@id='LastName']")).sendKeys("Tripathy");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@id='Email']")).sendKeys("djiyt@gm.");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@id='Title']")).sendKeys("PAT");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@id='Phone']")).sendKeys("123456789");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@id='Company']")).sendKeys("Cognizant");
        
        //JavascriptExecutor js = (JavascriptExecutor) driver; //Scrolling the windows to make the element visible 
        js.executeScript("window.scrollBy(0,500)");
        
        driver.findElement(By.xpath("//select[@id='Institution_Type__c']")).click();
        driver.findElement(By.xpath("//option[contains(text(),'Private University')]")).click();
        Thread.sleep(1000); 
        
        //selecting the dropdowns
        Select students = new Select(driver.findElement(By.xpath("//select[@id='Employee_Range__c']")));
        students.selectByIndex(2);
        Thread.sleep(3000);      
        Select users = new Select(driver.findElement(By.xpath("//select[@id='Self_reported_employees_to_buy_for__c']")));
        users.selectByIndex(3);
        Thread.sleep(3000);      
        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        js1.executeScript("window.scrollBy(0,190)");
        Thread.sleep(3000);   
        WebElement country=driver.findElement(By.xpath("//select[@id='Country']")); //selecting country
        Select Country=new Select(country);
        Country.selectByIndex(105);
        Thread.sleep(3000);       
        WebDriverWait wait = new WebDriverWait(driver, 40); 
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id='State']")));        
        WebElement state=driver.findElement(By.xpath("//select[@id='State']")); //selecting state
        Select State=new Select(state);
        State.selectByIndex(20);
        Thread.sleep(3000);
        
        //Submitting the form
        driver.findElement(By.xpath("//button[text()='Connect with us']")).click();
        Thread.sleep(2000);
        
        //Printing the Error Message
        String errorMessage=driver.findElement(By.xpath("//div[@class='mktoErrorMsg']")).getText();
        System.out.println("Error Message is :- " + errorMessage);
        
        //quitting the driver to get close 
        driver.quit();

	}

}
