package com.nirachi.pageobjects;

import java.util.List;

import com.nirachi.abstractcomponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends AbstractComponent {

  WebDriver driver;

  @FindBy(css = ".totalRow button")
  WebElement checkoutButton;

  @FindBy(css = ".cartSection h3")
  List<WebElement> productTitles;

  public CartPage(WebDriver driver) {
    super(driver);
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public Boolean verifyProductDisplay(String productName) {
    boolean match = productTitles.stream().anyMatch(p -> p.getText().equalsIgnoreCase(productName));
    return match;
  }

  public CheckoutPage gotoCheckOut() {
    checkoutButton.click();
    return new CheckoutPage(driver);
  }


}
