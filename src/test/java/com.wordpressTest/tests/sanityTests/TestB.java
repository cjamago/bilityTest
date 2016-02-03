package com.wordpressTest.tests.sanityTests;

import com.wordpressTest.framework.TestGroups;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestB {

    @Test(groups = TestGroups.SANITY)
    public void firstTestCaseB() throws Exception {
        Thread.sleep(2000);
        System.out.println("im in first test case from ClassTwo Class");
    }

    @Test(groups = TestGroups.SANITY)
    public void secondTestCaseB() throws Exception  {
        Thread.sleep(500);
        System.out.println("im in second test case from ClassTwo Class");
        Assert.fail("Failing this Test");
    }

    @Test(groups = TestGroups.SANITY)
    public void thirdTestCaseB() throws Exception {
        Thread.sleep(2000);
        System.out.println("im in third test case from ClassTwo Class");
    }

    @Test(groups = TestGroups.SANITY)
    public void fourthTestCaseB() throws Exception {
        Thread.sleep(2000);
        System.out.println("im in fourth test case from ClassTwo Class");
    }

    @Test(groups = TestGroups.SANITY)
    public void fifthTestCaseB() throws Exception {
        Thread.sleep(2000);
        System.out.println("im in fifth test case from ClassTwo Class");
    }
}