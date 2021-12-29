package pages.locators;

import org.openqa.selenium.By;

public class LoginPageLocators {
	
	public static final By EMAIL_INPUT = By.xpath("//input[@type='text']");
	public static final By PASSWORD_INPUT = By.xpath("//input[@type='password']");
	public static final By LOGIN_BUTTON = By.xpath("//button[@type='submit']");
	public static final By MANUFACTURING_BUTTON= By.xpath("//a[contains(@id, 'result')]/div[text()='Manufacturing']");
	public static final By INVENTORY_BUTTON= By.xpath("//a[contains(@id, 'result')]/div[text()='Inventory']");
	public static final By MENU_PRODUCT_BUTTON = By.xpath("//a[contains(text(),'Products')]");
	public static final By PRODUCT_ITEM_BUTTON = By.xpath("//a[@role='menuitem']/span[text()='Products']");
	public static final By PRODUCT_CREATE_BUTTON = By.xpath("//button[@type='button' and ./ text()]");
	public static final By PRODUCT_NAME_INPUT = By.xpath("//input[@placeholder='Product Name']");
	public static final By PRODUCT_TYPE_INPUT = By.xpath("//select[@name='type']");
	public static final By PRODUCT_SAVE_BUTTON = By.xpath("//button[contains(text(),'Save')]");
	public static final By INPUT_SEARCH = By.xpath("//input[@type='text']");
	public static final By UPDATE_QUANTITY_BUTTON = By.xpath("//button[@type='button']/span[text()='Update Quantity']");
	public static final By CREATE_NEW_QUANTITY_BUTTON = By.xpath("//button[contains(text(),'Create')]");
	public static final By LOCATION_ITEM_INPUT = By.xpath("//input[@class='o_input ui-autocomplete-input']");
	public static final By LOCATION_ITEM_OPTION = By.xpath("//a[text()='WH/Stock']");
	public static final By ON_HAND_QUANTITY_INPUT = By.xpath("//input[@name='inventory_quantity']");
	public static final By SAVE_QUANTITY_BUTTON = By.xpath("//button[contains(text(),'Save')]");
	

	
}
