package com.nirachi.testcomponents;

import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.nirachi.utility.ExtentReportNg;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners extends BaseTest implements ITestListener {

  ExtentTest test;
  ExtentReports extent = ExtentReportNg.getReportObject();
  ThreadLocal<ExtentTest> extentTest = new ThreadLocal();

  @Override
  public void onTestStart(ITestResult result) {
    test = extent.createTest(result.getMethod().getMethodName());
    extentTest.set(test); // unique thread id(ErrorValidatoinTest)
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    extentTest.get().log(Status.PASS, "Test passed");
  }

  @Override
  public void onTestFailure(ITestResult result) {

    extentTest.get().fail(result.getThrowable());

    try {
      driver = (WebDriver) result.getTestClass().getRealClass()
        .getField("driver").get(result.getInstance());
    } catch (Exception e) {
      e.printStackTrace();
    }

    //test.log(Status.FAIL, "Test failed");
    //test.fail(result.getThrowable().getMessage());
    String filepath = null;

    //Screenshot, Attach to report
    try {
      filepath = getScreenshot(result.getMethod().getMethodName(), driver);
    } catch (IOException e) {
      e.printStackTrace();
    }
    extentTest.get().addScreenCaptureFromBase64String(filepath, result.getMethod().getMethodName());


  }

  @Override
  public void onTestSkipped(ITestResult result) {
    ITestListener.super.onTestSkipped(result);
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
  }

  @Override
  public void onTestFailedWithTimeout(ITestResult result) {
    ITestListener.super.onTestFailedWithTimeout(result);
  }

  @Override
  public void onStart(ITestContext context) {
    ITestListener.super.onStart(context);
  }

  @Override
  public void onFinish(ITestContext context) {
    extent.flush();
  }
}
