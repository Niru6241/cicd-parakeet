package com.nirachi.test;

import java.io.IOException;
import java.util.List;

import com.nirachi.pageobjects.CartPage;
import com.nirachi.pageobjects.ProductCatalog;
import com.nirachi.testcomponents.BaseTest;
import com.nirachi.testcomponents.Retry;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ErrorValidationTest extends BaseTest {

  @Test(retryAnalyzer = Retry.class, groups = {"ErrorHandling"})
  public void loginErrorTest() throws InterruptedException, IOException {

    landingPage.loginApplication("simpletes@mail.com", "SimpleTes!@34");

    Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());

  }

  @Test()
  public void productErrorTest() throws InterruptedException, IOException {
    String productName = "ZARA COAT 3";

    ProductCatalog productCatalog =
      landingPage.loginApplication("simpletest@mail.com", "SimpleTest!@34");

    List<WebElement> products = productCatalog.getProductList();
    productCatalog.addProductToCart(productName);
    CartPage cartPage = productCatalog.gotoCartPage();

    boolean match = cartPage.verifyProductDisplay("ZARA COAT 22");
    Assert.assertFalse(match);
  }
}
