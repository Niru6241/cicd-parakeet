package com.nirachi.testcomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nirachi.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

  public WebDriver driver;
  public LandingPage landingPage;

  public WebDriver InitializeDriver() throws IOException {

    // properties class
    Properties prop = new Properties();
    FileInputStream fis = new FileInputStream("src/test/java/com/nirachi/properties/global.properties");
    prop.load(fis);
    String browser = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");


    if (browser.equalsIgnoreCase("chrome")) {
      WebDriverManager.chromedriver().setup();
      driver = new ChromeDriver();
    } else if (browser.equalsIgnoreCase("firefox")) {
      WebDriverManager.firefoxdriver().setup();
      driver = new FirefoxDriver();
    }

    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    driver.manage().window().maximize();
    return driver;
  }

  public List<HashMap<String, String>> getJsonData(String filePath) throws IOException {

    // read json to string
    String jsonContent =  FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

    //String to hashmap
    ObjectMapper mapper = new ObjectMapper();
    List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
    });

    return data;
  }

  public String getScreenshot(String tesCaseName, WebDriver driver) throws IOException {
    TakesScreenshot ts = (TakesScreenshot) driver;
    File source = ts.getScreenshotAs(OutputType.FILE);
    File file = new File(System.getProperty("user.dir") + "/src/test/resources/" + tesCaseName + "_screenshot.png");
    FileUtils.copyFile(source, file);

    return System.getProperty("user.dir") + "/src/test/resources/" + tesCaseName + "_screenshot.png";
  }

  @BeforeMethod(alwaysRun = true)
  public LandingPage launchApplication() throws IOException {

    driver = InitializeDriver();
    landingPage = new LandingPage(driver);
    landingPage.goToLandingPage();
    return landingPage;
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    driver.close();
  }
}
