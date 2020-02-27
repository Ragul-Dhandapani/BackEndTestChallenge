package com.freenow.suite;


import com.freenow.utils.ReusableHelper;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;

public class Hooks extends ReusableHelper {

    @BeforeSuite (alwaysRun = true)
    public void init() {
        extentReports = new ExtentReports ("Automation Report-Extent.html");
    }

    @AfterMethod(alwaysRun=true)
    public void getResult(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(LogStatus.FAIL, result.getThrowable());
        }

    }

    @AfterSuite (alwaysRun=true)
    public void end() {

        extentReports.flush();
//        extentReports.close();

    }
}
