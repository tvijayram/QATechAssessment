# QATechAssignment

## Build and Run Environment for the UI & API Automation Task

- Choice of IDE: Eclipse
- Choice of programming Language: Java (JRE 1.8.0_231)
- Choice of Automation Tool:  Selenium Web Driver (Chrome)
- Choice of reporting framework: TestNG
- Choice of REST API Testing :  REST Assured 
- Choice of Performance Test Tool : JMeter v 5.2

### Environment Setup:

Download latest version of Eclipse and create a Maven based project

Get the Maven dependencies related to **selenium, TestNG, JSON, REST Assured** and updated the pom.xml in the project created above. (Attached pom.xml)


### WEB Test Framework details for UI Automation:

Source File:  **ServiceNSWTest.java**

Test cases: 
- verifyPageforNumberPlate 
- findServiceLocations

Data Driven Testing:

For data driven testing here we are passing the required data in json file as key value pairs. For this scenario we had used ServiceCentres.json file wherein we will provide the suburb and the expected service centre.

ServiceCentres.json

{
"suburb": "Sydney Domestic Airport 2020",
"servicecentre": "Rockdale Service Centre"
}


### API Test Framework for REST API Automation:

Source File:  **WeatherAPITest.java**

Test Cases: 
- getCurrentWeatherDetails
- get3HourlyWeatherDetails

Data Driven Testing

For data driven testing here we are passing the required data in json file as key value pairs. For this scenario we had used LatLonData.json file wherein we will provide the Latitude, Longitude, PostCode, hours and API Access key.

LatLonData.json

{
"Lat": "40.730610",
"Lon": "-73.935242",
"PostCode": "2150",
"Hours":"3",
"Key":"6ab69a0e45824c4eb45eb616397d10f2"
}

.
### Run the Tests:

We can run the tests individually for each class or as a test suite. Here we are going to execute them using a test suite.

- Create a new file in the project and name it as **TestNG.xml.** TestNG.xml contains all configuration (classnames, testnames and suitnames).
- After creating xml file as shown above, in next step, we will execute the tests.
- Right click on the TestNG.xml and select run as TestNG Suite option in eclipse for execution of the suite; once execution is done we can see all the results.

### Reports:

To view WEB & API Test Report – open **index.html** present under test-output folder in the project. It will contain details on the Test Results.

### Performance Test - JMeter

PreRequirements :

    JAVA, JMETER v5.2

    Run performance test
        jmeter -n -t <JMX_FILE_PATH> -JlatlonCSV=<LATLON_CSV_PATH> -JpostcodeCSV=<POSTCODE_CSV_PATH> -l <DIR>/outputfile.jtl

Enter the below command on the command prompt:

    >>jmeter -n -t performanceTests/APIPerformanceScript.jmx -JlatlonCSV=performanceTests/LatLon.csv -JpostcodeCSV=performanceTests/PostCodes.csv -l performanceTests/perfoutput_555.jtl

To produce results in HTML format use below command:

    >>jmeter -g performanceTests/perfoutput_555.jtl -o performanceTests/html

