package com.assessment.qatechassessment;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;


public class WeatherAPITest {
	
	JSONArray weatherAPIParams = null;
	String latitude = null, longitude = null , postCode = null, hours = null, key = null;
	Response response = null;
	
	@BeforeTest
	public void initialSetUp() throws IOException
	{
		// Read data from Json file
		File file = new File("C:\\Users\\tvija\\eclipse-workspace\\qatechassessment\\src\\main\\resources\\LatLonData.json");
        String content = FileUtils.readFileToString(file, "utf-8");

        // Convert JSON string to JSONObject
        JSONObject WeatherJsonObject = new JSONObject(content); 
        latitude = WeatherJsonObject.getString("Lat");
        longitude = WeatherJsonObject.getString("Lon");
        key = WeatherJsonObject.getString("Key");
        postCode = WeatherJsonObject.getString("PostCode");
        hours = WeatherJsonObject.getString("Hours");
        
        // Specify the base URL to the RESTful web service
		RestAssured.baseURI = "https://api.weatherbit.io/v2.0/";

	}

	@Test
	public void getCurrentWeatherDetails() throws IOException {
		
		try {
			response = (Response) RestAssured.given().when().queryParam("lat", latitude).queryParam("lon", longitude)
					   .queryParam("key", key).get("/current");
		} catch (Exception e) {
			e.printStackTrace();
		}

		String responseBody = response.getBody().asString();
		System.out.println("getCurrentWeatherDetails Data =>  " + responseBody);
		System.out.println("Response Status code..:" +response.getStatusCode());
		int status = response.getStatusCode();
		
		boolean result = false;

		JSONObject jsonObject = new JSONObject(responseBody);
		JSONArray  myResponse = (JSONArray) jsonObject.get("data");
       
	    ArrayList<String> list = new ArrayList<String>();

	    for(int i=0; i<myResponse.length(); i++){
	        list.add(myResponse.getJSONObject(i).getString("state_code"));
	    }

	    System.out.println("State_code..:" +list);	      
	    
		if (status == 200)
			 result = true;		
		Assert.assertTrue(result);

	}
	
	
	// GET 3 Hourly forecast based on passing an Australian Postal code
	@ Test
	public void get3HourlyWeatherDetails()
	{
		try {
			response = (Response) RestAssured.given().when().queryParam("postal_code", postCode)
						.queryParam("hours", hours).queryParam("key", key).get("/forecast/hourly");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String responseBody = response.getBody().asString();
		System.out.println("get3HourlyWeatherDetails Data =>  " + responseBody);
		
		int status = response.getStatusCode();
		System.out.println("Response Status code..:" +response.getStatusCode());
		
		JSONObject jsonObject = new JSONObject(responseBody);
		JSONArray  myResponse = (JSONArray) jsonObject.get("data");
		
		System.out.println("******************* The Weather Details are ****************** ");
		for(int i = 0; i < myResponse.length(); i++){            
            JSONObject weatherData = myResponse.getJSONObject(i);
            System.out.println("The timestamp_utc is "+weatherData.getString("timestamp_utc"));
            JSONObject hourlyWeather = weatherData.getJSONObject("weather");
            System.out.println("Weather Details = [icon = "+hourlyWeather.getString("icon")+", code = "+hourlyWeather.getInt("code")+", description = "+hourlyWeather.getString("description")+"]");            
        }         	  		
		System.out.println("******************* ********************** ******************* ");
		
		boolean result = false;
		if (status == 200)
			 result = true;
	    Assert.assertTrue(result);	
	}
	
}
