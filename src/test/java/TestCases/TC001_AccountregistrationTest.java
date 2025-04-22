package TestCases;
import org.testng.Assert;

import org.testng.annotations.Test;

import PageObjects.AccountRegistrationPage;
import PageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountregistrationTest extends BaseClass {

    @Test(groups={"Regression","Master"})
 
	public void verify_account_registration() {
		
		logger.info(" ******** Starting TC001_AccountregistrationTest ***********");
		logger.debug(" This is a debug log message ");

		try 
		{
		HomePage hp =new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Click on My Account");
		
		hp.clickRegister();
		logger.info("Click on Register Link");

		AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
		
		logger.info("Providing Cutomer Details");
		regpage.setFirstName("John");
		regpage.setLastName("Maven");
		regpage.setEmail(randomeString()+"@gmail.com");
		regpage.setTelephone(randomeNumber());
		
		String password =randomeAlphanumeric();
		
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		regpage.setPrivacyPolicy();
		regpage.ClickContinue();
		
		logger.info("Validating expected message..");
		String confmsg=regpage.getconfirmationMessage();
		if(confmsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test failed..");
			logger.debug("Debug Logs..");
			Assert.assertTrue(false);
		}
		//Assert.assertEquals(confmsg, "Your Accountttg Has Been Created!");
	    } 
		catch (Exception e) 
		{
		Assert.fail();
	    }
	
	
	}

}

