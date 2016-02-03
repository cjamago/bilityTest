package com.wordpressTest.tests.sanityTests;


import com.wordpressTest.framework.TestGroups;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class TestC {

    @Test(groups = TestGroups.SANITY)
    public void firstTestCaseB() throws Exception {
        Thread.sleep(100);
        System.out.println("im in first test case from ClassThree Class");
    }

    @Test(groups = TestGroups.SANITY)
    public void secondTestCaseB() throws Exception {
        Thread.sleep(5000);
        System.out.println("im in second test case from ClassThree Class");
        throw new SkipException("Skipping this test with exception");
    }
}
