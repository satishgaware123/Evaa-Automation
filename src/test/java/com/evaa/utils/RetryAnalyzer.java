package com.evaa.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {


    int retryCount = 0;
    int maxRetryCount = 1;
    
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {          
                retryCount++;
                return true;          
        }
        return false;
    }
}