package stepdefinitions;

import java.io.IOException;
import java.util.Collections;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.driver.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.actions.AspireLoginPage;
import utilities.CommonFunctions;

public class DemoSteps {

	private WebDriver driver;

	@Test
	public void testApp() throws IOException {
		try {

			// Go to url
			CommonFunctions.getInstance().goToUrl("https://aspireapp.odoo.com/");
			// Email
			AspireLoginPage.getInstance().fillEmailInput();
			// Password
			AspireLoginPage.getInstance().fillPasswordInput();
			// Login
			AspireLoginPage.getInstance().clickLoginBtn();
			//Click Inventory
			AspireLoginPage.getInstance().clickInventory();
			//Click Product
			AspireLoginPage.getInstance().clickMenuProduct();
			//Click Item
			AspireLoginPage.getInstance().clickItemProduct();
			//Create Product
			AspireLoginPage.getInstance().clickCreateProduct();
			//Product Name
			AspireLoginPage.getInstance().sendKeysProductName();
			//Save Button
			AspireLoginPage.getInstance().clickSaveButton();
			//Click Update Quantity
			AspireLoginPage.getInstance().clickUpdateQuantity();
			//Create New QUantity
			AspireLoginPage.getInstance().clickCreateNewQuantity();
			//Sendkey
			AspireLoginPage.getInstance().sendKeysQuantity();
			//OnHand
			AspireLoginPage.getInstance().sendKeyOnHandQuant();
			//Save Quantity
			AspireLoginPage.getInstance().clickButtonSaveQuantity();

			
		} catch (Exception e) {


			Assert.fail(e.getMessage());
		}

	}
	
	@BeforeClass
	public void setUpDriver() throws IOException {
		// Read properties files
		CommonFunctions.getInstance().funcReadPropertiesFile("src/test/resources/config/application.properties");
		CommonFunctions.getInstance().funcReadPropertiesFile("src/test/resources/data/data.properties");

		switch (System.getProperty("browserName")) {
		case "chrome":
			WebDriverManager.chromedriver().setup();

			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("useAutomationExtension", false);
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			options.addArguments("chrome.switches", "--disable-extensions");
//			options.addArguments("--disable-gpu");

//			WebDriver driver = new ChromeDriver(options);
			driver = new ChromeDriver(options);

			driver.manage().window().maximize();

			DriverManager.getInstance().setDriver(driver);

			System.out.println("Start driver: " + Thread.currentThread().getId());
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			FirefoxDriver firefoxDriver = new FirefoxDriver();
			firefoxDriver.manage().window().maximize();
			DriverManager.getInstance().setDriver(firefoxDriver);

			System.out.println("Start driver: " + Thread.currentThread().getId());
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			EdgeDriver edgeDriver = new EdgeDriver();
			edgeDriver.manage().window().maximize();
			DriverManager.getInstance().setDriver(edgeDriver);

			System.out.println("Start driver: " + Thread.currentThread().getId());
			break;
		}
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("Tear down thread: " + Thread.currentThread().getId());

		DriverManager.getInstance().closeBrowser();
	}

}
