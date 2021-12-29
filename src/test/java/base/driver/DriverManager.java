package base.driver;

import org.openqa.selenium.WebDriver;

public class DriverManager {
	
	protected ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	
	private DriverManager() {	
	}
	
	private static class SingletonDriver {
		private static final DriverManager INSTANCE = new DriverManager();
	}
	
	public static DriverManager getInstance() {
		return SingletonDriver.INSTANCE;
	}
	
	public WebDriver getDriver() {
		return driver.get();
	}
	
	public void setDriver(WebDriver driverT) {
		driver.set(driverT);
	}
	
	public void closeBrowser() {
		for(String s : driver.get().getWindowHandles()) {
			driver.get().switchTo().window(s).close();
		}
		driver.remove();
		
		System.out.println("Quit driver: " + Thread.currentThread().getId());
	}

}
