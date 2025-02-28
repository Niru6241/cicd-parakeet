package com.nirachi.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.nirachi.pageobjects.CartPage;
import com.nirachi.pageobjects.CheckoutPage;
import com.nirachi.pageobjects.ConfirmationPage;
import com.nirachi.pageobjects.OrderPage;
import com.nirachi.pageobjects.ProductCatalog;
import com.nirachi.testcomponents.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SubmitOrderTest extends BaseTest {

  @Test(dataProvider = "getData", groups = {"Purchase"})
  public void submitOrder(HashMap<String, String> input) throws InterruptedException, IOException {


    ProductCatalog productCatalog = landingPage.loginApplication(input.get("email"), input.get("password"));

    List<WebElement> products = productCatalog.getProductList();
    productCatalog.addProductToCart(input.get("productName"));
    CartPage cartPage = productCatalog.gotoCartPage();

    boolean match = cartPage.verifyProductDisplay(input.get("productName"));
    Assert.assertTrue(match);
    CheckoutPage checkoutPage = cartPage.gotoCheckOut();

    checkoutPage.selectCountry("india");
    ConfirmationPage confirmationPage = checkoutPage.submitOrder();

    String confirmMessage = confirmationPage.getConfirmationMessage();

    Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
    System.out.println("done");

  }


  @Test(dependsOnMethods = {"submitOrder"}, dataProvider = "getData")
  public void orderHistory(HashMap<String, String> input) throws InterruptedException, IOException {
    ProductCatalog productCatalog =
      landingPage.loginApplication(input.get("email"), input.get("password"));

    OrderPage orderPage = productCatalog.gotoOrderPage();
    Assert.assertTrue(orderPage.verifyOrderDisplay(input.get("productName")));

  }

  @DataProvider
  public Object[][] getData() throws IOException {
    List<HashMap<String, String>> data = getJsonData("src/test/java/com/nirachi/data/purchaseOrder.json");
    return new Object[][] {{data.get(0)}, {data.get(1)}};
  }
}

//    HashMap<String, String> map1 = new HashMap<>();
//    map1.put("email", "simpletest@mail.com");
//    map1.put("password", "SimpleTest!@34");
//    map1.put("productName", "ZARA COAT 3");
//
//    HashMap<String, String> map2 = new HashMap<>();
//    map2.put("email", "simpletest@mail.com");
//    map2.put("password", "SimpleTest!@34");
//    map2.put("productName", "ADIDAS ORIGINAL");