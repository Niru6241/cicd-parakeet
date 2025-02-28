package com.nirachi.pageobjects;

import java.util.List;

import com.nirachi.abstractcomponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductCatalog extends AbstractComponent {

  WebDriver driver;

  public ProductCatalog(WebDriver driver) {
    super(driver);
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  @FindBy(css =".mb-3")
  List<WebElement> products;

  @FindBy(css = ".ng-animating")
  WebElement animation;

  By productsBy = By.cssSelector(".mb-3");
  By addToCartBy = By.cssSelector(".card-body button:last-of-type");
  By toastContainerBy = By.cssSelector("#toast-container");

  public List<WebElement> getProductList() throws InterruptedException {
    waitForElementToAppear(productsBy);
    return products;
  }

  public WebElement getProductByName(String productName) {

    WebElement product =  products.stream().filter(p ->
      p.findElement(By.cssSelector("b")).getText().equals(productName)).
      findFirst().orElse(null);

    return product;
  }

  public void addProductToCart(String productName) throws InterruptedException {
    WebElement prod = getProductByName(productName);
    prod.findElement(addToCartBy).click();
    waitForElementToAppear(toastContainerBy);
    waitForElementToDisappear(animation);
  }

}
