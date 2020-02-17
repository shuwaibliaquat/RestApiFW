package com.qa.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.util.TestBase;
import com.qa.util.TestUtil;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

import java.io.IOException;

import org.testng.Assert.*;

public class WeatherInfoTest extends TestBase {
	@BeforeMethod
	public void startUp() throws IOException {
		TestBase.init();
	}
	@DataProvider
	public Object[][] getData() {
		Object[][] testdata = TestUtil.getDataFromSheet(TestUtil.WeatherSheetName);
		return testdata;
	}

	@Test(dataProvider = "getData")
	public void getWeatherInfoDetailsTest(String city, String HTTPMethod, String humidity, String temperature,
			String weatherdescription, String windspeed, String winddirectiondegree) {
		// 1.Define the base URL:

		RestAssured.baseURI = prop.getProperty("serviceURL");

		// 2. Define the HTTP Request:

		RequestSpecification httpRequest = RestAssured.given();

		// 3. Make a request or execute the request:

		Response response = httpRequest.request(Method.GET, "/"+city);
		System.out.println(response);// It will print something meaningless since its is not converted yet

		// 4. Get the reponse Body:
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is:---> " + response.getBody().asString());// It will convert the response to
																						// String
		Assert.assertEquals(responseBody.contains(city), true);
		// 5.Get the status code received from response body and validate it:

		String statusLine = response.getStatusLine();
		int statusCode = response.getStatusCode();
		System.out.println("The Status Code is: " + statusCode);
		Assert.assertEquals(TestUtil.RESPONSE_CODE_200, statusCode);
		System.out.println("The Status line is: " + statusLine);

		// Get headers from the body:

		Headers headers = response.getHeaders();
		System.out.println("Headers are:--> " + headers);

		// Locating a specific header from headers:

		String contentType = response.header("Content-Type");
		System.out.println("Content Type from headers: " + contentType);

		// Get the Key-Value using JasonPath:

		JsonPath jsaonPathValue = response.jsonPath();
		String cityJson = jsaonPathValue.get("City");
		System.out.println("Value of city is: " + cityJson);
	}

}
