package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.HomePage;
import PageObjects.LoginPage;
import PageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{
	
	@Test (groups={"Sanity","Master"})
	public void verify_login() {
		
		logger.info("******** Starting TC002_LoginTest ***********");
		
		try
		{
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		//MyAccount
		MyAccountPage macc= new MyAccountPage(driver);
		boolean TargetPage= macc.isMyAccountPageExists();
		
		//Assert.assertEquals(TargetPage, true, "Login Failed.. ");
		Assert.assertTrue(TargetPage);
		
		

		} 
		catch (Exception e) 
		
		{
			Assert.fail();
		}
		
		logger.info("******** Finished TC002_LoginTest ***********");
	}

}
