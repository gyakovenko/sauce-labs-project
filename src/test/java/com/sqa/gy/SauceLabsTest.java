package com.sqa.gy;

import java.net.*;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;
import org.testng.annotations.*;

import com.sqa.gy.helpers.*;
import com.sqa.gy.helpers.exceptions.*;

public abstract class SauceLabsTest extends BasicTest {

	private String accessKey;
	private Browser browser;
	private String platform;
	private String username;
	private String version;

	public SauceLabsTest(String baseUrl) {
		super(baseUrl);
	}

	@DataProvider
	public Object[][] allThreeSetsOfData() {
		return DataHelper.joinData(credentials(), browserConfig(), dp());
		// this combo will have length cred x length browser x length dp sets of
		// data as DataProvider
	}

	public Object[][] browserConfig() {
		return new Object[][] { new Object[] { Browser.IE, "11", "Windows 8.1" },
				new Object[] { Browser.CHROME, "41", "Windows XP" }, new Object[] { Browser.SAFARI, "7", "OS X 10.9" },
				new Object[] { Browser.FIREFOX, "35", "Windows 7" } };
	}

	public Object[][] credentials() {
		return new Object[][] { new Object[] { "gyakovenkoyahoo", "e0835d68-e4d9-402b-be52-6e16ac8495e1" } };
	}

	abstract public Object[][] dp();

	public String getAccessKey() {
		return this.accessKey;
	}

	public Browser getBrowser() {
		return this.browser;
	}

	public String getPlatform() {
		return this.platform;
	}

	public String getUsername() {
		return this.username;
	}

	public String getVersion() {
		return this.version;
	}

	public void preTestSetUp(int count, String username, String accessKey, Browser browser, String version,
			String platform) throws UnsupportedBrowserException {
		this.username = username;
		this.accessKey = accessKey;
		this.browser = browser;
		this.version = version;
		this.platform = platform;
		setDriver(setUpSpecificDriver(count));
		getDriver().get(getBaseUrl());
	}

	@Override
	@BeforeMethod
	public void setUpChrome() {
	}

	@Override
	@BeforeMethod
	public void setUpFirefox() {
	}

	@Override
	@BeforeMethod
	public void setUpSafari() {
	}

	private WebDriver setUpSpecificDriver(int count) throws UnsupportedBrowserException {
		DesiredCapabilities capabilities;
		switch (getBrowser()) {
		case IE:
			capabilities = DesiredCapabilities.internetExplorer();
			break;
		case CHROME:
			capabilities = DesiredCapabilities.chrome();
			break;
		case SAFARI:
			capabilities = DesiredCapabilities.safari();
			break;
		case FIREFOX:
			capabilities = DesiredCapabilities.firefox();
			break;
		default:
			throw new UnsupportedBrowserException();
		}
		capabilities.setCapability("platform", this.platform);
		capabilities.setCapability("version", this.version);
		capabilities.setCapability("passed", true);
		String testName = getClass().getSimpleName() + " with " + getBrowser().toString().toLowerCase() + " on "
				+ getPlatform() + " " + count;
		capabilities.setCapability("name", testName);
		URL url = null;
		try {
			url = new URL("http://" + this.username + ":" + this.accessKey + "@ondemand.saucelabs.com:80/wd/hub");
		} catch (MalformedURLException e) {
			System.out.println("Can not connect to Sauce Labs URL[http://" + this.username + ":" + this.accessKey
					+ "@ondemand.saucelabs.com:80/wd/hub]");
		}
		return new RemoteWebDriver(url, capabilities);
	}
}
