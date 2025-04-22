package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

//contains only data provider methods
public class DataProviders {
	
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path=".\\testData\\OpenCart_LoginData.xlsx"; //taking excel file from testdata
		
		ExcelUtility xlutil= new ExcelUtility(path); //creating an object for utility 
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1",1);
		
		String logindata[][] =new String[totalrows][totalcols]; //created for two dimensional array
		
		
		for(int i=1;i<=totalrows;i++)
		{
			for(int j=0;j<totalcols;j++) 
			{
			logindata[i-1][j]= xlutil.getCellData("Sheet1", i, j);
		    }
		}
		return logindata;  //returning two dimensional array
  		
	}
}
