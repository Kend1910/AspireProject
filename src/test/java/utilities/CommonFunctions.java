package utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;

import base.driver.DriverManager;
import io.cucumber.java.Scenario;

public class CommonFunctions {

	private static class SingletonHelper {
		private static final CommonFunctions INSTANCE = new CommonFunctions();
	}

	public static CommonFunctions getInstance() {
		return SingletonHelper.INSTANCE;
	}

	int waitTimeout = 50;

	// Read properties file
	public void funcReadPropertiesFile(String fileProperties) throws IOException {
		// String strValue = null;
		Properties prop = new Properties();
		FileInputStream input = null;

		try {

			input = new FileInputStream(fileProperties);

			// load a properties file
			prop.load(input);

			for (String key : prop.stringPropertyNames()) {
				String val = prop.getProperty(key);

				if (val == null)
					val = "";
				else
					val = val.trim();

				System.setProperty(key, val);

				// System.out.println(key);
				// System.out.println(val);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// return prop;
	}

	public String getConfigProperties(String path, String propName) throws IOException {

		InputStream inputStream = new FileInputStream(path);
		Properties prop = new Properties();
		prop.load(inputStream);

		return prop.getProperty(propName);
	}

	public void goToUrl(String url) {
		DriverManager.getInstance().getDriver().get(url);
	}

	public void clickElement(By by) {
		waitForVisibilityOfElement(by, waitTimeout).click();
	}

	public void clearAndSenkeys(By by, String text) {
		waitForVisibilityOfElement(by, waitTimeout).clear();
		waitForVisibilityOfElement(by, waitTimeout).sendKeys(text);
	}

	public List<WebElement> findElementsByVisibility(By by) {
		return waitForVisibilityOfAllElements(by, 4);
	}

	public boolean checkElementsByVisibility(By by) {
		List<WebElement> lElement = waitForVisibilityOfAllElements(by, 4);

		if (lElement.size() != 0) {
			return true;
		}

		return false;
	}

	public List<WebElement> findElementsByVisibility(By by, int timeOut) {
		return waitForVisibilityOfAllElements(by, timeOut);
	}

	public void selectByVisibileText(By by, String text) {
		Select select = new Select(waitForVisibilityOfElement(by, waitTimeout));
		select.selectByVisibleText(text.trim());
	}
	
	public void selectByVisibileTextElement(WebElement elem, String text) {
		Select select = new Select(elem);
		select.selectByVisibleText(text.trim());
	}

	public WebElement waitForVisibilityOfElement(By by, long timeOut) {
		WebDriverWait wait = new WebDriverWait(DriverManager.getInstance().getDriver(), Duration.ofSeconds(timeOut));

		return wait.ignoring(NoSuchElementException.class).pollingEvery(Duration.ofSeconds(1))
				.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public List<WebElement> waitForVisibilityOfAllElements(By by, int timeOut) {
		WebDriverWait wait = new WebDriverWait(DriverManager.getInstance().getDriver(), Duration.ofSeconds(timeOut));

		try {
			return wait.ignoring(NoSuchElementException.class).pollingEvery(Duration.ofSeconds(1))
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
		} catch (Exception e) {
		}
		return new ArrayList<>();
	}

	public void waitForElementToDisappear(By by, int timeOut) {
		WebDriverWait wait = new WebDriverWait(DriverManager.getInstance().getDriver(), Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	public String getText(By by) {
		WebDriverWait wait = new WebDriverWait(DriverManager.getInstance().getDriver(), Duration.ofSeconds(10));

		return wait.ignoring(NoSuchElementException.class).pollingEvery(Duration.ofSeconds(1))
				.until(ExpectedConditions.visibilityOfElementLocated(by)).getText().trim();
	}

	public String getText(By by, int timeOut) {
		return waitForVisibilityOfElement(by, timeOut).getText().trim();
	}

	public void setClipboardData(String string) {
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	public void copyPasteByRobot() throws AWTException, InterruptedException {
		Robot rb = new Robot();
		// press Contol+V for pasting
		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_V);
		// release Contol+V for pasting
		rb.keyRelease(KeyEvent.VK_CONTROL);
		rb.keyRelease(KeyEvent.VK_V);
		// for pressing and releasing Enter
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
	}

	public void takeScreenshotIntoReport(Scenario scenario) {
		final byte[] screenshot = ((TakesScreenshot) DriverManager.getInstance().getDriver())
				.getScreenshotAs(OutputType.BYTES);

		scenario.attach(screenshot, "image/png", "Fail Img");
	}

	public void sendkeys(By by, String text) {
		DriverManager.getInstance().getDriver().findElement(by).clear();

		DriverManager.getInstance().getDriver().findElement(by).sendKeys(text);
	}

	public void pressKeys(By by, Keys key) {
		DriverManager.getInstance().getDriver().findElement(by).sendKeys(key);
	}

	public void clickByJavascript(WebElement ele) {
		((JavascriptExecutor) DriverManager.getInstance().getDriver()).executeScript("arguments[0].click();", ele);
	}

	public void actionClearAndSendkeys(By by, String text) {
		Actions actions = new Actions(DriverManager.getInstance().getDriver());

		actions.doubleClick(CommonFunctions.getInstance().waitForVisibilityOfElement(by, 20)).keyDown(Keys.CONTROL)
				.sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).build().perform();

		CommonFunctions.getInstance().waitForVisibilityOfElement(by, 20).sendKeys(text);
	}

	public String getPageTitle(String title, int timeOut) {
		WebDriverWait wait = new WebDriverWait(DriverManager.getInstance().getDriver(), Duration.ofSeconds(timeOut));

		try {
			wait.ignoring(NoSuchElementException.class).pollingEvery(Duration.ofSeconds(1))
					.until(ExpectedConditions.titleContains(title));
			return DriverManager.getInstance().getDriver().getTitle();
		} catch (Exception e) {
			return "";
		}
	}

	public String generateRandomAlphaNumeric(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}

	public List<String> getAllTabs() {
		return DriverManager.getInstance().getDriver().getWindowHandles().stream().collect(Collectors.toList());
	}

	public void openNewTab(String url) {
		DriverManager.getInstance().getDriver().switchTo().newWindow(WindowType.TAB);
		DriverManager.getInstance().getDriver().get(url);
	}

	public void switchTab(String tab) {
		DriverManager.getInstance().getDriver().switchTo().window(tab);
	}

	public void switchFrame(WebElement element) {
		DriverManager.getInstance().getDriver().switchTo().frame(element);
	}

	public void closeTab(String tab) {
		DriverManager.getInstance().getDriver().switchTo().window(tab).close();
	}
	
	public String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
	
	public void waitForPageLoad(WebDriver driver, int timeout) {
		Wait<WebDriver> wait = new WebDriverWait(driver, timeout);

		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				System.out.println("Current Window State       : "
						+ String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));

				return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
						.equals("complete");
			}
		});
	}
	
	public void setAttribute(WebElement elem, String value) {
		JavascriptExecutor js = (JavascriptExecutor) DriverManager.getInstance().getDriver();
	    js.executeScript("arguments[0].setAttribute(arguments[1],arguments[2])",
	        elem, "value", value
	    );
    }
}
