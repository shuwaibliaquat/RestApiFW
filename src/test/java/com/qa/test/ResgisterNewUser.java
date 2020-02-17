package com.qa.test;

import java.io.IOException;

import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.util.TestBase;
import com.qa.util.TestUtil;

import groovyjarjarasm.asm.commons.Method;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class ResgisterNewUser extends TestBase {
	@BeforeMethod
	public void startUp() throws IOException {
		TestBase.init();
	}

	@Test
	public void registerNewMember() {
		RestAssured.baseURI = prop.getProperty("registerURL");

		RequestSpecification httpRequest = RestAssured.given();

		org.json.simple.JSONObject requestJson = new org.json.simple.JSONObject();
		requestJson.put("FirstName", "Tom191511");
		requestJson.put("LastName", "Miah111591");
		requestJson.put("UserName", "tomiMia1159h11");
		requestJson.put("Password", "Tom192311511");
		requestJson.put("Email", "email1@yahoo5911giri.c1om");
		// =================================================================================
		httpRequest.header("Content-Type", "application/json");

		httpRequest.body(requestJson.toJSONString());

		Response response = httpRequest.post("/register");
		// =================================================================================
		
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is:---> " + response.getBody().asString());

		System.out.println(responseBody.contains("SuccessCode"));

		// 5.Get the status code received from response body and validate it:

		String statusLine = response.getStatusLine();
		int statusCode = response.getStatusCode();
		System.out.println("The Status Code is: " + statusCode);
		Assert.assertEquals(TestUtil.RESPONSE_CODE_201, statusCode);
		System.out.println("The Status line is: " + statusLine);

		// Get headers from the body:

		Headers headers = response.getHeaders();
		System.out.println("Headers are:--> " + headers);

		// Locating a specific header from headers:

		String contentType = response.header("Content-Type");
		System.out.println("Content Type from headers: " + contentType);
		
		JsonPath jsaonPathValue = response.jsonPath();
		String successCode = jsaonPathValue.get("SuccessCode");
		System.out.println(successCode);
	}
}
