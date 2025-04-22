package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.HomePage;
import PageObjects.LoginPage;
import PageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

//automating the datasrive testcase

public class TC003_LoginDDT extends BaseClass {
	
	@Test (dataProvider="LoginData",dataProviderClass=DataProviders.class, groups="Datadriven") //getting data provider from different class 
	
	//methods name: verify_LoginDDT
	public void verify_LoginDDT(String email, String pwd, String exp) //string exp is expected value from excel whether it is valid or not
	{
		logger.info("******** Starting TC003_LoginDDT ***********");
		
		try
		{
		
		//HomePage
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();

		
		//LoginPage
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		//MyAccount
		MyAccountPage macc= new MyAccountPage(driver);
		boolean TargetPage= macc.isMyAccountPageExists();
		
		/* 
		 * Data is valid - login sucess - test pass - logout 
		 * data is valid - login fail - test fail
		 * 
		 * 
		 * data is invalid -login success - test fail - logout 
		 * data is valid - login failed - test pass - logout
		 * */
		
		if(exp.equalsIgnoreCase("Valid")) 
		{
		if (TargetPage==true) 
		{
	    macc.clicklogout();
		Assert.assertTrue(true);
		
		}
		else 
		{
			Assert.assertTrue(false);
		}
		}
		
		//data is invalid -login success - test fail - logout 
		//data is valid - login failed - test pass - logout
		if(exp.equalsIgnoreCase("Invalid")) 
		{
		if (TargetPage==true) 
		{
			macc.clicklogout();
			Assert.assertTrue(false);
		}
		else 
		{
			Assert.assertTrue(true);
		}
		}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("******** finished TC003_LoginDDT ***********");

	}

}
