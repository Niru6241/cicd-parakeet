package com.nirachi.pageobjects;

import java.util.List;

import com.nirachi.abstractcomponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderPage extends AbstractComponent {

  WebDriver driver;


  @FindBy(css = "tr td:nth-child(3)") List<WebElement> productNames;

  public OrderPage(WebDriver driver) {
    super(driver);
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public Boolean verifyOrderDisplay(String productName) {
    boolean match = productNames.stream().anyMatch(p -> p.getText().equalsIgnoreCase(productName));
    return match;
  }
}
