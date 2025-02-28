package com.nirachi.test;

import java.time.Duration;
import java.util.List;

import com.nirachi.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class StandAloneTest {

  public static void main(String[] args) {

    String productName = "ZARA COAT 3";

    WebDriverManager.chromedriver().setup();
    WebDriver driver = new ChromeDriver();

    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.manage().window().maximize();
    driver.get("https://rahulshettyacademy.com/client/");
    LandingPage landingPage = new LandingPage(driver);
    driver.findElement(By.id("userEmail")).sendKeys("simpletest@mail.com");
    driver.findElement(By.id("userPassword")).sendKeys("SimpleTest!@34");
    driver.findElement(By.id("login")).click();

    WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
    explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

    List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

    WebElement product = products.stream().filter(p ->
      p.findElement(By.cssSelector("b")).getText().equals(productName)).
      findFirst().orElse(null);

    product.findElement(By.cssSelector(".card-body button:last-of-type")).click();

    explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
    // ng-animating
    explicitWait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

    WebElement cart = driver.findElement(By.cssSelector("[routerlink*='/cart']"));
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].click()", cart);

    List<WebElement> cartItems = driver.findElements(By.cssSelector(".cartSection h3"));

    boolean isMatch = cartItems.stream().anyMatch(p ->
      p.getText().equalsIgnoreCase(productName));

    Assert.assertTrue(isMatch);
    driver.findElement(By.cssSelector(".totalRow button")).click();

    driver.findElement(By.cssSelector("[placeholder='Select Country']")).sendKeys("india");

    explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

    driver.findElement(By.xpath("(//button[contains(@class, 'ta-item')])[2]")).click();
    driver.findElement(By.cssSelector(".action__submit")).click();

    String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();

    Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
    System.out.println("done");
    driver.quit();

  }
}
