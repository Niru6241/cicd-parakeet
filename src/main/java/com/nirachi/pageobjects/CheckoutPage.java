package com.nirachi.pageobjects;

import com.nirachi.abstractcomponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends AbstractComponent {

  WebDriver driver;

  public CheckoutPage(WebDriver driver) {
    super(driver);
    this.driver =driver;
    PageFactory.initElements(driver, this);
  }

  @FindBy(css = "[placeholder='Select Country']")
  WebElement country;

  @FindBy(css = ".action__submit")
  WebElement submitButton;

  @FindBy(xpath = "(//button[contains(@class, 'ta-item')])[2]")
  WebElement pickCountry;

  By results = By.cssSelector(".ta-results");


  public void selectCountry(String countryName) throws InterruptedException {
    country.sendKeys(countryName);
    waitForElementToAppear(results);
    pickCountry.click();
  }

  public ConfirmationPage submitOrder() {
    submitButton.click();
    return new ConfirmationPage(driver);
  }
}
