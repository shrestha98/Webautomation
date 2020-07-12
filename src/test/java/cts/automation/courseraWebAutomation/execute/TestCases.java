package cts.automation.courseraWebAutomation.execute;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import cts.automation.courseraWebAutomation.CoursesOffered;
import cts.automation.courseraWebAutomation.ForEnterprise;
import cts.automation.courseraWebAutomation.TotalCount;

public class TestCases 
{
	static ExtentTest test;
	static ExtentReports report;
	static WebDriver driver;
	CoursesOffered coursesOffered;
	ForEnterprise forEnterprise;
	TotalCount totalCount;
	Properties properties;
	String browserName;

	@BeforeClass
	public static void startTest()
	{
		report = new ExtentReports(System.getProperty("user.dir")+"\\Output\\ExtentReportResults.html");
		test = report.startTest("ExtentReport");
	}

	@Parameters({"browserName"})
	@BeforeTest

	public void beforeTest(String browserName) throws Exception
	{
		if(browserName.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//driver//chromedriver.exe");
			driver = new ChromeDriver();
		}

		else if(browserName.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"//driver//geckodriver.exe");  
			driver = new FirefoxDriver();
		}

		else
		{
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"//driver//IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}


	}


	@Test (priority=0)
	public void coursesSearched() throws InterruptedException, IOException, InvalidFormatException 
	{
		int count=0;
		coursesOffered=new CoursesOffered(driver);
		coursesOffered.totalCoursesOffered();
		count=count+1;
		if(count==1)
		{
			test.log(LogStatus.PASS, "Courses Searched Passed");
		}
		else
		{
			test.log(LogStatus.FAIL, "Courses Searched Failed");
		}
	}

	@Test (priority=2)
	public void errorMessage() throws InterruptedException, IOException
	{
		try {
			{
				int count=0;
				forEnterprise=new ForEnterprise(driver);
				forEnterprise.errorMessageAtEnterprise();
				count=count+1;
				if(count==1)
				{
					test.log(LogStatus.PASS, "Error Message Passed");
				}
				else
				{
					test.log(LogStatus.FAIL, "Error Message Failed");
				}
			}
		} 

		catch (InterruptedException | IOException e)

		{

			e.printStackTrace();
		}

	}


	@Test (priority=1)
	public void totalCourses() throws InterruptedException, IOException
	{
		int count=0;
		totalCount=new TotalCount(driver);
		totalCount.totalCountOfCourses();
		count=count+1;
		if(count==1)
		{
			test.log(LogStatus.PASS, "Total Count Passed");
		}
		else
		{
			test.log(LogStatus.FAIL, "Total Count Failed");
		}
	}


	@AfterClass
	public void afterClass() 
	{
		driver.quit();
		report.endTest(test);
		report.flush();
	}

}
