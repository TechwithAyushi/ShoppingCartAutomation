package testBase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseClass {
	
	public static WebDriver driver; 
	public Logger logger;
	public Properties p;
	
	@BeforeClass(groups = {"Sanity", "Regression", "Master", "Datadriven"})
	@Parameters({"os", "browser"})
	public  synchronized void setup(String os, String br) throws MalformedURLException, IOException {
	        FileReader file = new FileReader("./src//test//resources//config.properties");
	        p = new Properties();
	        p.load(file);
	        
	logger= LogManager.getLogger(this.getClass());
	boolean selenuimGrid = Boolean.parseBoolean(p.getProperty("selenuimGrid")); 
    String gridURL = p.getProperty("gridURL");
   //URL gridUrl = new URL("http://localhost:4444/wd/hub");
	logger.info( "selenuimGrid:" + selenuimGrid +", OS: " + os + ", Browser: " + br );
	
	
	if (selenuimGrid) {
		try {
			
			DesiredCapabilities capabilities= new DesiredCapabilities();
			    if (br.equalsIgnoreCase("chrome")) {
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--headless=new",
                    	    "--no-sandbox",
                    	    "--disable-dev-shm-usage",
                    	    "--disable-gpu",
                    	    "--disable-software-rasterizer",
                    	    "--disable-extensions",
                    	    "--disable-infobars",
                    	    "--window-size=1920,1080");
                    driver = new RemoteWebDriver(new URL("http://192.168.198.1:4444/wd/hub"), options.merge(capabilities));
                    logger.info("Grid URL: " + gridURL);
                    logger.info("RemoteDriver instance created for grid in headless mode for Chrome");
                } else if (br.equalsIgnoreCase("firefox")) {
                    FirefoxOptions options = new FirefoxOptions();
                    options.addArguments("--headless");
                    driver = new RemoteWebDriver(new URL(gridURL), options.merge(capabilities));
                    logger.info("RemoteDriver instance created for grid in headless mode for Firefox");
                } else if (br.equalsIgnoreCase("MicrosoftEdge")) {
                    EdgeOptions options = new EdgeOptions();
                    options.addArguments("--headless");
                    driver = new RemoteWebDriver(new URL(gridURL), options.merge(capabilities));
                    logger.info("RemoteDriver instance created for grid in headless mode for Edge");
                } else {
                    logger.warn("Browser not supported for grid execution: " + br);
                }
			} catch (MalformedURLException e) {
				throw new RuntimeException("Invalid Grid URL: " + e.getMessage());
			}
	}else {
	            // Local execution logic
			 switch (br.toLowerCase()) {
			 case "chrome" : driver = new ChromeDriver(); break;
	         case "firefox" : driver = new FirefoxDriver(); break;
	         case "edge" : driver = new EdgeDriver(); break;
	         default : System.out.println("Invalid browser.. "); return;
	         }
	     } 
	
	// Common WebDriver setup
	if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));  // Timeout setting
            driver.get(p.getProperty("appurl"));  // Load the application URL from properties file
            driver.manage().window().maximize();  // Maximize the browser window
            logger.info("Browser setup completed and application loaded.");
        } else {
            logger.error("Driver is null. Setup failed.");
        }
		
	}
	
	@AfterClass(groups= {"Sanity","Regression","Master"})
	public void teardown() {
		driver.quit();
	}

	
	public String randomeString() 
	{
		String generatedString=RandomStringUtils.randomAlphabetic(5);
		return generatedString;
		
	}
	public String randomeNumber() 
	{
		String generatedNumber=RandomStringUtils.randomNumeric(10);
		return generatedNumber;
		
	}
	public String randomeAlphanumeric() 
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(3);
		String generatednumber=RandomStringUtils.randomNumeric(3);
		return (generatedstring+"@"+generatednumber);

		
	}
	
	//we will capture the screenshot whenever their is a failure
	public String captureScreen(String tname) throws IOException 
	{
		String timeStamp= new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); 
		
		//interface to take screenshot
		TakesScreenshot takesScreenshot=(TakesScreenshot) driver;
		File sourceFile=takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		//it will capture screenshot and keep in screenshot folder this will attach to the report 
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\"+ tname + "_" + timeStamp +".png";
		File targetFile= new File(targetFilePath);
		
		//copy the sourcefile into targetfile
		sourceFile.renameTo(targetFile);
		
		return targetFilePath; //path of the screenshot path in targetfilepath variable
		
	}
	
}
