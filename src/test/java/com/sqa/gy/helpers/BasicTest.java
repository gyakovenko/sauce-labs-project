package com.sqa.gy.helpers;

import java.util.concurrent.*;

import org.apache.log4j.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.safari.*;
import org.testng.annotations.*;

public class BasicTest {

	private String baseUrl;
	private WebDriver driver;
	private Logger logger;

	public BasicTest(String baseUrl) {
		super();
		this.baseUrl = baseUrl;
		this.logger = Logger.getLogger(BasicTest.class); // you pass in the
															// class type. you
															// could put in a
															// string, but
															// unique to the
															// THIS logger.
		this.logger.info("Created a BasicTest object through constructor");
	}

	public String getBaseUrl() {
		return this.baseUrl;
	}

	public WebDriver getDriver() {
		return this.driver;
	}

	public Logger getLogger() {
		return this.logger;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@BeforeMethod // (enabled = false)
	public void setUpChrome() {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
		this.driver = new ChromeDriver();
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		this.driver.get(this.baseUrl);
	}

	@BeforeMethod(enabled = false)
	public void setUpFirefox() {
		this.driver = new FirefoxDriver();
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		this.driver.get(this.baseUrl);
	}

	@BeforeMethod(enabled = false) // doesnt work right now bc need later
									// selenium or earlier safari
	public void setUpSafari() {
		this.driver = new SafariDriver();
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		this.driver.get(this.baseUrl);
	}

	@AfterMethod
	public void tearDown() {
		this.driver.quit();
	}
}