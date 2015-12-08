package com.wordpressTest.framework;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataGenerator {

    public static String appendTimeStamp(String text) {
        DateFormat format = new SimpleDateFormat("-yyMMddhhmmss");
        String timeStamp = format.format(new Date());
        return text + timeStamp;
    }


    public static String getRandomEmail() {
        return "tester-" + new Date().getTime() + "@test.kainos.com";
    }

    public static String getRandomPostcode() {
        DateFormat format = new SimpleDateFormat("ddhhmmss");
        return format.format(new Date());
    }

    public static String getRandomFlatName() {
        return new Date().getTime() + "";
    }

    public static String getRandomStreet() {
        return "StreetName" + new Date().getTime();
    }

    public static String getRandomTown() {
        return "Town Name" + new Date().getTime();
    }


    public static String getRandomLandLineNumber() {
        return getRandomPhoneNumberWithPrefix("01");
    }

    public static String getRandomMobileNumber() {
        return getRandomPhoneNumberWithPrefix("07");
    }

    public static String getRandomPhoneNumberWithPrefix(String prefix) {
        DateFormat format = new SimpleDateFormat("Mddhhmmss");
        String timeStamp = format.format(new Date());
        return prefix + timeStamp;
    }

    public static String getVATRegistrationNo() {
        return RandomStringUtils.randomNumeric(9);
    }

    public static String getRandomNino() {
        DateFormat format = new SimpleDateFormat("ssSSS");
        String ninoPart = format.format(new Date());
        return "AA1" + ninoPart + "A";
    }

    public static String getRandomRegNo() {
        return RandomStringUtils.randomNumeric(8);
    }
}
