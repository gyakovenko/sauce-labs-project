package com.sqa.gy;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.*;
import org.testng.annotations.*;

import com.sqa.gy.helpers.exceptions.*;

public class GeeboTestwSauceLabs extends SauceLabsTest {

	public GeeboTestwSauceLabs() {
		super("http://geebo.com");
	}

	@Override
	public Object[][] dp() {
		return new Object[][] { new Object[] { 1, "toys", "Merchandise" }, new Object[] { 2, "isha", "Community" } };
	}

	@Test(dataProvider = "allThreeSetsOfData")
	public void testGeebo(String username, String accessKey, Browser browser, String version, String platform,
			int count, String searchText, String categoryText)
			throws InterruptedException, UnsupportedBrowserException {
		preTestSetUp(count, username, accessKey, browser, version, platform);
		WebElement searchField = getDriver().findElement(By.id("header_search"));
		WebElement category = getDriver().findElement(By.id("header_search_controller"));
		Select categorySelect = new Select(category);
		searchField.sendKeys(searchText);
		categorySelect.selectByVisibleText(categoryText);
		WebElement searchButton = getDriver().findElement(By.cssSelector("img.search"));
		searchButton.click();
		String expectedTitle = "Free Classifieds Ads: " + categoryText + " at Geebo";
		Assert.assertEquals(getDriver().getTitle(), expectedTitle);
	}

}
