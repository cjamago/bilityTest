package com.wordpressTest.framework;

import com.wordpressTest.framework.selenium.FunctionalCfg;
import org.openqa.selenium.By;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by chijiokea on 30/10/2015.
 */
public class WebElementMap {

    private static final String WEB_ELEMENT_PROPERTIES = "WEB_ELEMENT_PROPERTIES";
    private static final String WEBELEMENT_MAP_PATH = "/selenium/cfgs/general/webelementsmap.properties";
    private static final Properties properties;

    static {
        properties = new Properties();
    }

    public static By getLocator(String logicalElementName) {

        String customPropertiesFilePath = System.getenv(WEB_ELEMENT_PROPERTIES);
        boolean useCustomProperties = (customPropertiesFilePath != null) && !customPropertiesFilePath.trim().isEmpty();

        try {
            InputStream in;
            if (useCustomProperties)
                in = new FileInputStream(customPropertiesFilePath);
            else {
                in = FunctionalCfg.class.getResourceAsStream(WEBELEMENT_MAP_PATH);
            }
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();

            throw new RuntimeException("Problem loading test properties file. Is " +
                    (useCustomProperties ?
                            (customPropertiesFilePath + " valid file?") :
                            (WEBELEMENT_MAP_PATH + " on classpath?")), e);


        }

        //Read value using the logical name as key
        String locator = properties.getProperty(logicalElementName);

        //Split the value which contains locator type and locator name
        String locatorType = locator.split(">")[0];
        String locatorValue = locator.split(">")[1];

        switch (locatorType.toLowerCase()) {
            case "id":
                return By.id(locatorValue);
            case "name":
                return By.name(locatorValue);
            case "classname" :
                return By.className(locatorValue);
            case "tagname" :
                return By.tagName(locatorValue);
            case "linktext" :
                return By.linkText(locatorValue);
            case "partialinktext" :
                return By.partialLinkText(locatorValue);
            case "cssselector" :
                return By.cssSelector(locatorValue);
            case "xpath" :
                return By.xpath(locatorValue);
            default:
                throw new RuntimeException("Locator type '" + locatorType + "' not defined!");
        }


//        if (locatorType.equalsIgnoreCase("id"))
//            return By.id(locatorValue);
//        else if (locatorType.equalsIgnoreCase("name"))
//            return By.name(locatorValue);
//        else if (locatorType.equalsIgnoreCase("classname"))
//            return By.className(locatorValue);
//        else if (locatorType.equalsIgnoreCase("tagname"))
//            return By.tagName(locatorValue);
//        else if (locatorType.equalsIgnoreCase("linktext"))
//            return By.linkText(locatorValue);
//        else if (locatorType.equalsIgnoreCase("partialinktext"))
//            return By.partialLinkText(locatorValue);
//        else if (locatorType.equalsIgnoreCase("cssselector"))
//            return By.cssSelector(locatorValue);
//        else if (locatorType.equalsIgnoreCase("xpath"))
//            return By.xpath(locatorValue);
//        else
//            throw new RuntimeException("Locator type '" + locatorType + "' not defined!");

    }
}
