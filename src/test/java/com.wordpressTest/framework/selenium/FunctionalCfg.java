package com.wordpressTest.framework.selenium;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static java.lang.System.getenv;

public class FunctionalCfg {

    private static final String SELENIUM_BROWSER_PROPERTIES = "TEST_PROPERTIES";
    private static final String SELENIUM_ENV_PROPERTIES = "SELENIUM_ENV_PROPERTIES";
    private static final String SELENIUM_GENERAL_PROPERTIES = "SELENIUM_GENERAL_PROPERTIES";
    private static final String PROPERTIES_DIR = "/";
    private static final String BROWSER_DIR = "selenium/cfgs/browser/";
    private static final String DEFAULT_BROWSER_PROPERTIES_FILE_PATH = PROPERTIES_DIR + BROWSER_DIR + "default.properties";
    private static final String MY_BROWSER_PROPERTIES_FILE_PATH = PROPERTIES_DIR + BROWSER_DIR + "my.properties";
    private static final String ENVIRONMENT_DIR = "selenium/cfgs/environment/";
    private static final String DEFAULT_ENVIRONMENT_PROPERTIES_FILE_PATH = PROPERTIES_DIR + ENVIRONMENT_DIR + "default.properties";
    private static final String MY_ENVIRONMENT_PROPERTIES_FILE_PATH = PROPERTIES_DIR + ENVIRONMENT_DIR + "my.properties";
    private static final String GENERAL_DIR = "selenium/cfgs/general/";
    private static final String DEFAULT_GENERAL_PROPERTIES_FILE_PATH = PROPERTIES_DIR + GENERAL_DIR + "general.properties";
    private static final String MY_GENERAL_PROPERTIES_FILE_PATH = PROPERTIES_DIR + GENERAL_DIR + "my.properties";
    private static String browser = System.getProperty("browser");

    static {
        props = new Properties();
        loadGeneralPropertiesFromFile();
        loadGeneralMyPropertiesFromFile();
        loadEnvironmentPropertiesFromFile();
        loadEnvironmentMyPropertiesFromFile();
        loadBrowserPropertiesFromFile();
        loadBrowserMyPropertiesFromFile();
    }

    private static final Properties props;

    private static void loadGeneralPropertiesFromFile() {
        loadPropertiesFromFile(DEFAULT_GENERAL_PROPERTIES_FILE_PATH, SELENIUM_GENERAL_PROPERTIES);
    }

    private static void loadGeneralMyPropertiesFromFile() {
        loadPropertiesFromFile(MY_GENERAL_PROPERTIES_FILE_PATH, SELENIUM_GENERAL_PROPERTIES);
    }

    private static void loadEnvironmentPropertiesFromFile() {
        loadPropertiesFromFile(DEFAULT_ENVIRONMENT_PROPERTIES_FILE_PATH, SELENIUM_ENV_PROPERTIES);
    }

    private static void loadEnvironmentMyPropertiesFromFile() {
        loadPropertiesFromFile(MY_ENVIRONMENT_PROPERTIES_FILE_PATH, SELENIUM_GENERAL_PROPERTIES);
    }

    private static void loadBrowserPropertiesFromFile() {
        loadPropertiesFromFile(DEFAULT_BROWSER_PROPERTIES_FILE_PATH, SELENIUM_BROWSER_PROPERTIES);
    }

    private static void loadBrowserMyPropertiesFromFile() {
        loadPropertiesFromFile(MY_BROWSER_PROPERTIES_FILE_PATH, SELENIUM_GENERAL_PROPERTIES);
    }

    private static void loadPropertiesFromFile(String defaultPropertiesFilePath, String envProperty) {
        String customPropertiesFilePath = getenv(envProperty);
        boolean useCustomProperties = (customPropertiesFilePath != null) && !customPropertiesFilePath.trim().isEmpty();

        try {
            InputStream propsStream;
            if (useCustomProperties) {
                propsStream = new FileInputStream(customPropertiesFilePath);
            } else {
                propsStream = FunctionalCfg.class.getResourceAsStream(defaultPropertiesFilePath);
            }
            props.load(propsStream);
        } catch (Exception e) {
            // ignore exception thrown if optional my.properties is missing
            if (!defaultPropertiesFilePath.contains("my.properties")) {
                e.printStackTrace();

                throw new RuntimeException("Problem loading test properties file. Is " +
                        (useCustomProperties ?
                                (customPropertiesFilePath + " valid file?") :
                                (defaultPropertiesFilePath + " on classpath?")), e);

            }
        }
    }

    protected static String getBaseUrl() {
        return getProp("test_url");
    }

    protected static String getEnvironmentProperty(String sPropName) {
        String sProp = "";
        try {
            sProp = getProp(sPropName);
        } catch (Exception x) {
        }
        return sProp;
    }

    protected static String getEnvironmentName() {
        String url = getProp("test_url");
        int envNameStart = url.indexOf('/') + 2;
        int envNameEnd = url.indexOf('.');
        return url.substring(envNameStart, envNameEnd).toUpperCase().replace("-INTERNAL", "");
    }

    protected static SupportedBrowsers getBrowserName(String browserString) {

        if (browserString.isEmpty() && !commandLineIsEnabled()) {
            browserString = getProp("test_browser_name");
        }
        if (browserString.isEmpty() && commandLineIsEnabled()) {
            browserString = browser.toLowerCase();
        }


        browserString = ((browserString != null) ? browserString.trim() : null);
        if ("firefox".equalsIgnoreCase(browserString)) {
            return SupportedBrowsers.FIREFOX;
        } else if ("chrome".equalsIgnoreCase(browserString)) {
            return SupportedBrowsers.CHROME;
        } else if ("phantomjs".equalsIgnoreCase(browserString)) {
            return SupportedBrowsers.PHANTOMJS;
        } else if ("safari".equalsIgnoreCase(browserString)) {
            return SupportedBrowsers.SAFARI;
        } else if ("ie".equalsIgnoreCase(browserString)) {
            return SupportedBrowsers.IE;
        } else if ("htmlunit".equalsIgnoreCase(browserString)) {
            return SupportedBrowsers.HTMLUNIT;
        } else {
            throw new RuntimeException("Provided browser name is not correct!");
        }
    }


    protected static SupportedBrowsers getBrowserName() {
        return getBrowserName("");
    }

    private static boolean commandLineIsEnabled() {
        return Boolean.parseBoolean(getProp("test_command_line"));
    }

    protected static String getBrowserVersion(List<String> browser) {
        String browserVersion = (browser.isEmpty()) ? getProp("test_browser_version") : browser.get(1);
        if ((browserVersion != null) && !browserVersion.trim().isEmpty()) {
            return browserVersion;
        } else {
            throw new RuntimeException("Browser version not provided!");
        }
    }

    protected static String getBrowserVersion() {
        return getBrowserVersion(Collections.<String>emptyList());
    }

    protected static String getChromeDriverPath() {
        return getProp("test_chromedriver_path");
    }

    protected static String getPhantomJSDriverPathm() {
        return getProp("test_phantomjsdriver_path");
    }

    protected static String getPlatform(List<String> browser) {
        String platform = (browser.isEmpty()) ? getProp("test_platform") : browser.get(2);
        if ((platform != null) && !platform.trim().isEmpty()) {
            return platform;
        } else {
            throw new RuntimeException("Platform not provided!");
        }
    }

    protected static String getSauceLabsUrl() {
        return String.format(getProp("test_saucelabs_url_template"), getSauceLabsUser(), getSauceLabsKey());
    }

    protected static String getSauceLabsUser() {
        return getProp("test_saucelabs_username");
    }

    protected static String getSauceLabsKey() {
        return getProp("test_saucelabs_access_key");
    }

    protected static boolean useSauceLabs() {
        return Boolean.parseBoolean(getProp("test_saucelabs_enabled"));
    }

    protected static String getProxyTunnelName() {
        return getProp("tunnel_name");
    }

    private static String getProp(String key) {
        String s = props.getProperty(key);
        return (s != null) ? s.trim() : null;
    }

    public static String getUserName() {
        return getProp("USERNAME");
    }

    public static String getPassword() {
        return getProp("PASSWORD");
    }
}
