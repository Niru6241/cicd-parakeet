package com.nirachi.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportNg {

  public static ExtentReports getReportObject() {
    String path = "reports/ExtentReport.html";
    ExtentSparkReporter reporter = new ExtentSparkReporter(path);
    reporter.config().setReportName("Web Automation Results");
    reporter.config().setDocumentTitle("Test Results");


    ExtentReports extent = new ExtentReports();
    extent.attachReporter(reporter);
    extent.setSystemInfo("Tester", "Nick");
    return extent;
  }
}
