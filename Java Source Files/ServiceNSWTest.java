package com.assessment.qatechassessment;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class ServiceNSWTest {
	
	public String baseUrl = null;
	public WebDriver driver = null;
	
	@BeforeTest
	public void init() {	
		baseUrl = "https://www.service.nsw.gov.au/";
		String driverPath = "C:\\chromedriver.exe";
		driver = new ChromeDriver();
	}

	@Test (priority=1)
	public void verifyPageforNumberPlate() {

		driver.get(baseUrl);
		// Maximise browser window
		driver.manage().window().maximize();

		String expectedTitle = "Apply for a number plate";

		// Initializing web element searchBox
		WebElement searchBox = driver.findElement(By.name("q"));

		// Writing a text "Apply for a number plate" in the search box
		String searchTerm = "Apply for a number plate";
		searchBox.sendKeys(searchTerm, Keys.TAB);
		searchBox.sendKeys(Keys.ENTER);

		// Need to Validate the navigation to appropriate page
		driver.findElement(By.linkText(searchTerm)).click();

		boolean sameTitle = driver.getTitle().contains(expectedTitle);

		Assert.assertTrue(sameTitle);
		
	}

	@Test (priority=2)
	public void findServiceLocations() throws IOException {
				
		String suburb = null, serviceCentre = null;
		
		// Read data from Json file...
		File dataFilePath = new File("C:\\Users\\tvija\\eclipse-workspace\\qatechassessment\\src\\main\\resources\\ServiceCentres.json");
		String content = FileUtils.readFileToString(dataFilePath, "utf-8");

        // Convert JSON string to JSONObject
        JSONObject WeatherJsonObject = new JSONObject(content); 
        suburb = WeatherJsonObject.getString("suburb");
        serviceCentre = WeatherJsonObject.getString("servicecentre");
                     		
		// lets get back to home page
		driver.get(baseUrl);
		
		//Locate the "Find locations" link and click it
		driver.findElement(By.linkText("Find locations")).click();

		WebElement locatorSearchBox = driver.findElement(By.id("locatorTextSearch"));
        
		// Writing a text "Sydney 2000" in the search box
		locatorSearchBox.sendKeys(suburb, Keys.TAB);
		locatorSearchBox.sendKeys(Keys.ENTER);

		boolean  locationExist;
		
		List<WebElement> locations = driver.findElements(By.xpath(("//*[@id=\"locatorListView\"]/div/div/div/div")));
      		for (int i = 1; i <= locations.size(); i++) {
           		String href = driver.findElement(By.xpath("//*[@id=\"locatorListView\"]/div/div/div/div[" + i + "]/a")).getText();
           		if (serviceCentre.equalsIgnoreCase(href))
            		    locationExist =  true;
        	}
		
		Assert.assertTrue(locationExist);		
	}
	
	
	
    @AfterTest
    public void afterTestDemo()
    {
		driver.close();       
    }
}



