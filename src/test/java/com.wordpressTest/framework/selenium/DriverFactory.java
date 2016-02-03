package com.wordpressTest.framework.selenium;

import com.saucelabs.saucerest.SauceREST;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.wordpressTest.framework.WebElementMap.getLocator;

public class DriverFactory extends FunctionalCfg {

    private static DesiredCapabilities capabilities;
    private static String sauceLabsSessionId;
    private static String testClassNameForVerboseReporting;
    private static final int IMPLICIT_WAIT_TIMEOUT = Integer.parseInt(FunctionalCfg.getEnvironmentProperty("IMPLICIT_WAIT_TIMEOUT"));


    public static WebDriver Instance;

    private DriverFactory(String testClassName, List<String> unsupportedBrowserCapabilities) {
        if (this.Instance == null) {
            if (unsupportedBrowserCapabilities.isEmpty()) {
                Instance = getDriver(testClassName);
            } else {
                Instance = getSauceLabsBrowser(testClassName, unsupportedBrowserCapabilities);
            }
        }
    }

    public static void initialize(String testClassName, List<String> unsupportedBrowserCapabilities) {
        new DriverFactory(testClassName, unsupportedBrowserCapabilities);
        TurnOnWait();
    }

    public static String baseAddress() {
        return getBaseUrl();
    }

    public static WebElement byElement(String locator) {
        try {
            return waitUntilPresent(getLocator(locator));
        } catch (StaleElementReferenceException sere) {
            return waitUntilPresent(getLocator(locator));
        }
    }

    public static List<WebElement> byElements(String tagName) {
        try {
            return Instance.findElements(getLocator(tagName));
        } catch (StaleElementReferenceException sere) {
            return Instance.findElements(getLocator(tagName));
        }
    }

    private static void TurnOnWait() {
        if (!getBrowserName().toString().equalsIgnoreCase("Safari"))
            Instance.manage().timeouts().implicitlyWait(30, TimeUnit.MILLISECONDS);
    }

    private static WebDriverWait driverWait() {
        Instance.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
        return new WebDriverWait(Instance, IMPLICIT_WAIT_TIMEOUT, 5);
    }

    public static WebElement waitUntilPresent(By locator) {
        return driverWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement waitUntilClickable(final By locator) {
        return driverWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean waitUntilVisible(final By locator) {
        return driverWait().until(ExpectedConditions.visibilityOfElementLocated(locator)) != null;
    }

    public static boolean waitUntilElementIsPresent(By by) {
        Boolean wait = false;
        try {
            wait = (new WebDriverWait(Instance, 10)).until((WebDriver d) -> d.findElement(by)).isDisplayed();

        } catch (StaleElementReferenceException e) {
            e.getCause();
        }
        return wait;
    }

    public static void NoWait(Actions action) {
        TurnOffWait();
        action.build();
        driverWait();
    }

    public static void Wait() {
        TurnOnWait();
    }

    private static void TurnOffWait() {
        Instance.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
    }

    // Sauce Labs Support Request #11881 - disable proxy when running test in IE
    private static boolean isBrowserIE(SupportedBrowsers browserName) {
        return (browserName.toString().equals("IE"));
    }

    public static WebDriver getDriver() {
        return Instance;
    }

    private WebDriver getDriver(String testClassName) {
        if (getBrowserName() != null) {
            if (useSauceLabs()) {
                testClassNameForVerboseReporting = testClassName;
                return getSauceLabsBrowser(testClassName);
            } else {
                return getLocalBrowser();
            }
        } else {
            throw new RuntimeException("Browser is null");
        }
    }

    private WebDriver getSauceLabsBrowser(String testClassName, List<String> browserCapabilities) {
        testClassName = extractTestClassName(testClassName);
        SupportedBrowsers browserName = getBrowserName((browserCapabilities.isEmpty()) ? "" : browserCapabilities.get(0));
        String version = getBrowserVersion(browserCapabilities);

        DesiredCapabilities capabilities = determineBrowser(browserName);
        capabilities.setCapability("version", version);
        capabilities.setCapability("platform", getPlatform(browserCapabilities));
        capabilities.setCapability("name", String.format(getEnvironmentName() + " - %s- %s - %s:%s", testClassName, browserName, version, DateTime.now()));
        if (isBrowserIE(browserName)) {
            capabilities.setCapability("avoid-proxy", true);
        }

        if (FunctionalCfg.getProxyTunnelName() != null) {
            capabilities.setCapability("tunnel-identifier", FunctionalCfg.getProxyTunnelName());
        }

        try {
            RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(getSauceLabsUrl()), capabilities);
            setSauceLabsSessionId(remoteDriver.getSessionId().toString());
            printSessionId(testClassName);
            return remoteDriver;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Problem configuring RemoteWebDriver with SauceLabs url", e);
        }
    }

    private static String getSauceLabsSessionId() {
        return sauceLabsSessionId;
    }

    private void setSauceLabsSessionId(String sessionId) {
        sauceLabsSessionId = sessionId;
    }

    /**
     * If test method failed, set Sauce Labs session status to FAIL
     */
    public static void setSauceLabsSessionStatusToFailed(ITestResult result) {
        if (useSauceLabs()) {
            SauceREST client = new SauceREST(getSauceLabsUser(), getSauceLabsKey());
            if (!result.isSuccess()) {
                if (getBrowserName().toString().equalsIgnoreCase("safari")) {
                    // This is the only way to fetch URL in Safari 8
                    System.out.println(String.format("Test class '%s' failed, current URL is '%s'", extractTestClassName(testClassNameForVerboseReporting), Instance.getCurrentUrl()));
                }
                Map<String, Object> sauceJob = new HashMap<>();
                sauceJob.put("passed", false);
                client.updateJobInfo(getSauceLabsSessionId(), sauceJob);
            }
        }
    }

    /**
     * Output job name - required for Sauce Labs and TeamCity integration
     */
    private void printSessionId(String testClassName) {
        String message;
        if (isTestStartedFromTeamCityServer()) {
            message = String.format("SauceOnDemandSessionID=%s job-name=%s", getSauceLabsSessionId(), testClassName);
        } else {
            message = String.format("https://saucelabs.com/jobs/%s", getSauceLabsSessionId());
        }
        System.out.println(message);
    }

    private WebDriver getSauceLabsBrowser(String testClassName) {
        return getSauceLabsBrowser(testClassName, Collections.<String>emptyList());
    }

    private DesiredCapabilities determineBrowser(SupportedBrowsers browser) {
        switch (browser) {
            case FIREFOX: {
                return DesiredCapabilities.firefox();
            }
            case CHROME: {
                return DesiredCapabilities.chrome();
            }
            case PHANTOMJS: {
                return DesiredCapabilities.phantomjs();
            }
            case SAFARI: {
                return DesiredCapabilities.safari();
            }
            case HTMLUNIT: {
                return DesiredCapabilities.htmlUnit();
            }
            case IE: {
                return DesiredCapabilities.internetExplorer();
            }
            default: {
                throw new RuntimeException("Proper browser not provided for setting Desired Capabilities!");
            }
        }
    }

    private WebDriver getLocalBrowser() {
        DesiredCapabilities capabilities;
        switch (getBrowserName()) {
            case FIREFOX: {
                initFirefoxDriver();
                break;
            }
            case CHROME: {
                initChromeDriver();
                break;
            }
            case SAFARI: {
                initSafariDriver();
                break;
            }
            case PHANTOMJS: {
                capabilities = DesiredCapabilities.phantomjs();
                Instance = new PhantomJSDriver(capabilities);
                break;
            }
            case HTMLUNIT: {
                capabilities = DesiredCapabilities.htmlUnit();
                Instance = new HtmlUnitDriver(capabilities);
                break;
            }
            case IE: {
                Instance = new InternetExplorerDriver();
                break;

            }
            default: {
                throw new RuntimeException("Proper local browser not provided! ");
            }
        }

        return Instance;
    }

    private static String extractTestClassName(String fullyQualifiedTestClassName) {
        return fullyQualifiedTestClassName.replace("com.com.com.wordpressTest.tests.", "");
    }

    private boolean isTestStartedFromTeamCityServer() {
        return !System.getProperty("os.name").equals("Mac OS X");
    }

    public static void endSession() {
        Instance.quit();
    }

    protected static WebDriver initChromeDriver() {
        System.setProperty("webdriver.chrome.driver", getChromeDriverPath());
        capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("chrome.switches", Arrays.asList("--disable-web-security"));
        Instance = new ChromeDriver(capabilities);
        return Instance;
    }

    protected static WebDriver initFirefoxDriver() {
        FirefoxProfile fp = new FirefoxProfile();
        fp.setPreference("getBrowser.download.manager.showWhenStarting", false);
        fp.setPreference("getBrowser.helperApps.neverAsk.saveToDisk", "application/pdf");
        Instance = new FirefoxDriver(fp);
        return Instance;
    }

    protected static WebDriver initSafariDriver() {
        capabilities = DesiredCapabilities.safari();
        Instance = new SafariDriver(capabilities);
        return Instance;
    }
}
