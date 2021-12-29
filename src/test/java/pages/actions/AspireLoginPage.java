package pages.actions;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import base.driver.DriverManager;
import pages.locators.LoginPageLocators;
import utilities.CommonFunctions;

public class AspireLoginPage {

	private static class SingleTonHelper {
		private static final AspireLoginPage INSTANCE = new AspireLoginPage();
	}

	public static AspireLoginPage getInstance() {
		return SingleTonHelper.INSTANCE;
	}

	public void fillEmailInput() {
		CommonFunctions.getInstance().clearAndSenkeys(LoginPageLocators.EMAIL_INPUT, System.getProperty("dEmail"));
	}

	public void fillPasswordInput() {
		CommonFunctions.getInstance().clearAndSenkeys(LoginPageLocators.PASSWORD_INPUT,
				System.getProperty("dPassword"));
	}

	public void clickLoginBtn() {
		CommonFunctions.getInstance().clickElement(LoginPageLocators.LOGIN_BUTTON);
	}

	public void clickMF() {
		CommonFunctions.getInstance().clickElement(LoginPageLocators.MANUFACTURING_BUTTON);
	}

	public void clickInventory() {
		CommonFunctions.getInstance().clickElement(LoginPageLocators.INVENTORY_BUTTON);
	}

	public void clickMenuProduct() {
		CommonFunctions.getInstance().clickElement(LoginPageLocators.MENU_PRODUCT_BUTTON);
	}

	public void clickItemProduct() {
		CommonFunctions.getInstance().clickElement(LoginPageLocators.PRODUCT_ITEM_BUTTON);
	}

	public void clickCreateProduct() throws InterruptedException {
		Thread.sleep(6000);

		if (CommonFunctions.getInstance().checkElementsByVisibility(LoginPageLocators.PRODUCT_CREATE_BUTTON)) {

			CommonFunctions.getInstance().clickByJavascript(
					DriverManager.getInstance().getDriver().findElement(LoginPageLocators.PRODUCT_CREATE_BUTTON));

		} else {

			CommonFunctions.getInstance().clickByJavascript(
					DriverManager.getInstance().getDriver().findElement(LoginPageLocators.PRODUCT_CREATE_BUTTON));

		}
	}

	public void sendKeysProductName() throws InterruptedException {

		Thread.sleep(6000);

		if (CommonFunctions.getInstance().checkElementsByVisibility(LoginPageLocators.PRODUCT_NAME_INPUT)) {

			String productName = System.getProperty("dProductName").replace("[number]",
					CommonFunctions.getInstance().generateRandomAlphaNumeric(3));

			System.setProperty("Product_Name_Search", productName);

			CommonFunctions.getInstance().sendkeys(LoginPageLocators.PRODUCT_NAME_INPUT,
					System.getProperty("dProductName").replace("[number]",
							CommonFunctions.getInstance().generateRandomAlphaNumeric(3)));

		} else {
			CommonFunctions.getInstance().sendkeys(LoginPageLocators.PRODUCT_NAME_INPUT,
					System.getProperty("dProductName"));
		}
	}

	public void clickSaveButton() {
		CommonFunctions.getInstance().clickElement(LoginPageLocators.PRODUCT_SAVE_BUTTON);
	}

	public void clickUpdateQuantity() throws InterruptedException {

		Thread.sleep(6000);

		CommonFunctions.getInstance().clickElement(LoginPageLocators.UPDATE_QUANTITY_BUTTON);

	}

	public void clickCreateNewQuantity() throws InterruptedException {
		Thread.sleep(6000);

		if (CommonFunctions.getInstance().checkElementsByVisibility(LoginPageLocators.CREATE_NEW_QUANTITY_BUTTON)) {

			CommonFunctions.getInstance().clickElement(LoginPageLocators.CREATE_NEW_QUANTITY_BUTTON);

		}
	}

	public void sendKeysQuantity() {
		List<WebElement> listElement = CommonFunctions.getInstance()
				.findElementsByVisibility(LoginPageLocators.LOCATION_ITEM_INPUT);

		for (int i = 0; i < listElement.size(); i++) {
			if (i == 0) {
				listElement.get(i).clear();
				listElement.get(i).sendKeys("WH/Stock");
				CommonFunctions.getInstance().clickElement(LoginPageLocators.LOCATION_ITEM_OPTION);
			}
		}
	}

	public void sendKeyOnHandQuant() {
		CommonFunctions.getInstance().sendkeys(LoginPageLocators.ON_HAND_QUANTITY_INPUT, "11");
	}
	
	public void clickButtonSaveQuantity() {
		CommonFunctions.getInstance().clickElement(LoginPageLocators.SAVE_QUANTITY_BUTTON);
	}

}
