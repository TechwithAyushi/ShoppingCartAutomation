package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener{
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	
	String repName;
	
	public void onStart(ITestContext testContext) {
		
		/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String currentdatetimestamp=df.format(dt); */
		
		//same as above comment
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //time stamp
		
		
		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); //specify location
		
		sparkReporter.config().setDocumentTitle("opencart Automation Report"); //title of report
		sparkReporter.config().setReportName("opencart Functional Testing"); //name of report
		sparkReporter.config().setTheme(Theme.DARK);
		
		//common data we populated
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "opencart");
		extent.setSystemInfo("Moudle", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name")); //current user of the system who is runing the system tester name
		extent.setSystemInfo("Environment", "QA"); //hardcode environmenet name
		
		//values passed in xml files instead of hardcoding it 
		String os = testContext.getCurrentXmlTest().getParameter("os"); //Current xml file browser and os name testconetxt captures the value
		extent.setSystemInfo("Operating System", os);
		
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		//addtional stuff for reports for groups testng one
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups(); //groups name we have specified will be captured in report
		if(!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());  //check if not groups are specified empty .tostring checks the group
		}
	}
	
	public void onTestSuccess(ITestResult result) 
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); //to display groups in reports
		test.log(Status.PASS,result.getName()+" Got Successfully Executed");
	}
	
	//this method will trigger when method is fail this will capture the screenshot 
	public void onTestFailure(ITestResult result) 
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); //to display groups in reports
		
		test.log(Status.FAIL,result.getName()+" Got Failed");
		test.log(Status.INFO,result.getThrowable().getMessage()); //error message in the report
		
		//bellow code shows the screenshot in report , captureScreen is wriiten in baseclass
		try
		{
			//this will attach the screenshot to the report
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} catch (IOException e1) {
			e1.printStackTrace(); //exception if not captured properly
		}

		
	}
	
	public void onTestSkipped(ITestResult result) 
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); //to display groups in reports

		test.log(Status.SKIP,result.getName() + "Got Skipped");
		test.log(Status.INFO,result.getThrowable().getMessage()); //error message in the report
	}
		
		
	public void onFinish(ITestContext testContext){
	{

		extent.flush(); //consolidate all in one report 
		
		//to automate to open the report automatically not manually below is the code
		String pathOFExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File ExtentReports = new File(pathOFExtentReport);
		
		try {
			Desktop.getDesktop().browse(ExtentReports.toURI()); //desktop is a predefined class will open the report in the browser automatically
		} catch (IOException e) {
			e.printStackTrace(); //exception if it doesn't work 
		}
	}
	
	
	
	
	
	//to send email as soon as it generates the report 
	/*try {
		URL url = new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
		//Create the email message 
		ImageHtmlEmail email = new ImageHtmlEmail();
		email.setDataSourceResolver(new DataSourceUrlResolver(url));
		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("ayushipandey0196@gmail.com" , "password"));
		email.setSSLOnConnect(true);
		email.setFrom("ayushipandey0196@gmail.com"); //sender
		email.setSubject("Test Results");
		email.setMsg("Please find the attached Report");
		email.addTo("ayushipandey0196@gmail.com"); //reciever
		email.attach(url, "extent report","Please check report..");
		email.send(); //send email
	} catch (Exception e) {
		e.printStackTrace();
	}*/
	
	}
	
}
