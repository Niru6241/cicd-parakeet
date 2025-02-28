package com.nirachi.pageobjects;

import com.nirachi.abstractcomponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends AbstractComponent {

  WebDriver driver;

  public LandingPage(WebDriver driver) {
    super(driver);
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  //WebElement userEmail = driver.findElement(By.id("userEmail"));

  // PageFactory

  @FindBy(id="userEmail")
  WebElement userEmail;

  @FindBy(id = "userPassword")
  WebElement userPassword;

  @FindBy(id = "login")
  WebElement submit;

  @FindBy(css="[class*='flyInOut']")
  WebElement errorMessage;

  public ProductCatalog loginApplication(String email, String password) {

    userEmail.sendKeys(email);
    userPassword.sendKeys(password);
    submit.click();
    return new ProductCatalog(driver);
  }

  public String getErrorMessage() throws InterruptedException {
    waitForWebElementToAppear(errorMessage);
    return errorMessage.getText();
  }

  public void goToLandingPage() {
    driver.get("https://rahulshettyacademy.com/client/");
  }
}
