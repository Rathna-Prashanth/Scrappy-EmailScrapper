package io.prashanth.scrappy;

import io.prashanth.scrappy.extensions.ExcelRead;
import io.prashanth.scrappy.extensions.WebdriverExtensions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScrappingModal {

    public WebDriver driver;
    public int currentScrapID = 1;
    public String ANSI_RED = "\\u001B[41m";

    public String[] filterModal(String firstName, String lastName, String company, String companyFirstName)
            throws IOException {
        String[] returnData = new String[2];
        String finalEmail = "";
        String finalTitile = "";
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--no-sandbox");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get("https://www.google.co.in/");
            Thread.sleep(5000);
        } catch (Exception e) {
            returnData[0] = "#N/A";
            returnData[1] = "#N/A";
            return returnData;
        }
        driver.findElement(By.xpath("//input[@title='Search']"))
                .sendKeys(firstName + " " + lastName + " " + company, Keys.ENTER);

        List<String> link = new LinkedList<>();
        Set<String> slink = new HashSet<>();
        Set<String> level1 = new HashSet<>();
        Set<String> domainMail = new HashSet<>();
        Set<String> nameEmail = new HashSet<>();
        Set<String> titles = new HashSet<>();

        try {
            List<WebElement> findElements = driver.findElements(By.xpath("//div[@class='rc']/div/a"));
            int cnt = 0;
            Pattern pattern = Pattern.compile("([a-z0-9_.-]+)@([a-z0-9_.-]+[a-z])");
            Matcher matcher = pattern.matcher(driver.getPageSource());
            while (matcher.find()) {
                slink.add(matcher.group());
                System.out.println("Link Email : " + matcher.group());
            }
            for (WebElement ele : findElements) {
                if (!ele.getAttribute("href").contains(".pdf") /* && ele.getAttribute("href").contains(".txt") */) {
                    link.add(ele.getAttribute("href"));
                    link.set(cnt, ele.getAttribute("href"));
                    System.out.println(ele.getAttribute("href"));
                    cnt++;
                }
            }
        } catch (Exception e) {
            System.out.println("----------- No Google Results Found --------------");
        }
        driver.quit();
        String[] grabbedTitles = new ExcelRead().getTitiles();
        WebdriverExtensions.OpenBrowser();
        for (int i = 0; i < 5; i++) {
            System.out.println("Searching in top results");
            String pageSource = "";
            if (GoogleScrappingEngine.DriverInfo) {
                try {
                    pageSource = WebdriverExtensions.GetPageSource(link.get(i));
                    pageSource = pageSource.toLowerCase();
                } catch (Exception e) {
                    System.out.println(e);
                    pageSource = "Email";
                }
            } else {
                try {
                    URL url = new URL(link.get(i));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    BufferedReader br = null;
                    if (connection.getResponseCode() == 200) {
                        br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String strCurrentLine;
                        while ((strCurrentLine = br.readLine()) != null) {
                            pageSource = strCurrentLine;
                        }
                    } else {
                        br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        String strCurrentLine;
                        while ((strCurrentLine = br.readLine()) != null) {
                            System.out.println(strCurrentLine);
                        }
                        pageSource = new WebdriverExtensions().getPageSource(link.get(i));
                        pageSource = pageSource.toLowerCase();
                    }
                } catch (Exception e) {
                    pageSource = "No email";
                }
            }
            Pattern pattern = Pattern.compile("([a-z0-9_.-]+)@([a-z0-9_.-]+[a-z])");
            Matcher matcher = pattern.matcher(pageSource);
            while (matcher.find()) {
                slink.add(matcher.group());
            }

//		------------- For Title ---------------------
            for (String string : grabbedTitles) {
                if (pageSource.toLowerCase().contains(string.toLowerCase())) {
                    System.out.println(string);
                    titles.add(string);
                }
            }
        }
        WebdriverExtensions.CloseBroswer();
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("---------------------------------------------------------------------------");

//		------------- For Domain ---------------------
        for (String e : slink) {
            System.out.println(e);
            String[] split = e.split("@");
            String[] split2 = split[1].split("\\.");
            try {
                if (split[1].toLowerCase().contains(companyFirstName.substring(0, 3).toLowerCase())) {
                    level1.add(split2[0]);
                    domainMail.add(e);
                    System.out.println("==============> Matched Domain " + split2[0]);
                }
            } catch (Exception ge) {
                System.out.println(ge);
            }
        }

//		------------- For Name ---------------------
        for (String e : slink) {
            System.out.println(e);
            String[] split = e.split("@");
            try {
                if (split[0].toLowerCase().contains(firstName.toLowerCase())
                        || split[0].toLowerCase().contains(lastName.toLowerCase())
                        || split[0].toLowerCase().contains(firstName.toLowerCase().substring(0, 3))
                        || split[0].toLowerCase().contains(lastName.toLowerCase().subSequence(0, 3))) {
                    System.out.println("==============> Matched Name : " + split[0]);
                    nameEmail.add(e);
                }
            } catch (Exception e3) {
                System.out.println(e3);
            }
        }

        System.out.println("-----------------------------XXXXX-------------------------------------");
        System.out.println("----------------------------XXXXXXX--------------------------------------");
        System.out.println("-------------------------XXXXXXXXXXXX-------------------------------------");

        for (String e : domainMail) {
            if (e.toLowerCase().contains(".com") || e.toLowerCase().contains(".net") || e.toLowerCase().contains(".org")
                    || e.toLowerCase().contains(".cc")) {
                System.out.println("Domain : " + e);
                finalEmail = e;
            }
        }
        int bst = 0;
        for (String e : nameEmail) {
            if (e.toLowerCase().contains(".com") || e.toLowerCase().contains(".net") || e.toLowerCase().contains(".org")
                    || e.toLowerCase().contains(".cc")) {

                System.out.println("Name : " + e);
                finalEmail = e;

                if (e.toLowerCase().contains(firstName.toLowerCase())
                        || e.toLowerCase().contains(lastName.toLowerCase())) {
                    finalEmail = e;
                    bst++;
                }
                try {
                    if (e.toLowerCase().contains(companyFirstName.toLowerCase())
                            || e.toLowerCase().contains(companyFirstName.substring(0, 3).toLowerCase())) {
                        finalEmail = e;
                        bst++;
                    }
                } catch (Exception mNameException) {
                    System.out.println(mNameException);
                }
                if (bst == 2) {
                    System.out.println("Best Match Name : " + e);
                    finalEmail = e;
                    break;
                } else {
                    bst = 0;
                }
            }
        }

//		 -----------------------------------------------------------------------------------------------------------------------------

        if (finalEmail.isEmpty()) {
            System.out.println("========= Searching in Other Results =============");
            WebdriverExtensions.OpenBrowser();
            for (int i = 4; i < link.size(); i++) {
                String pageSource = "";
                if (GoogleScrappingEngine.DriverInfo) {
                    try {
                        pageSource = WebdriverExtensions.GetPageSource(link.get(i));
                        pageSource = pageSource.toLowerCase();
                    } catch (Exception e) {
                        System.out.println(e);
                        pageSource = "Email";
                    }
                } else {
                    try {
                        URL url = new URL(link.get(i));
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.connect();
                        BufferedReader br = null;
                        if (connection.getResponseCode() == 200) {
                            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String strCurrentLine;
                            while ((strCurrentLine = br.readLine()) != null) {
                                pageSource = strCurrentLine;
                                pageSource = pageSource.toLowerCase();
                            }
                        } else {
                            br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                            String strCurrentLine;
                            while ((strCurrentLine = br.readLine()) != null) {
                                System.out.println(strCurrentLine);
                            }
                            pageSource = new WebdriverExtensions().getPageSource(link.get(i));
                            pageSource = pageSource.toLowerCase();
                        }
                    } catch (Exception e) {
                        pageSource = "Email";
                    }
                }
                Pattern pattern = Pattern.compile("([a-z0-9_.-]+)@([a-z0-9_.-]+[a-z])");
                Matcher matcher = pattern.matcher(pageSource);

                while (matcher.find()) {
                    slink.add(matcher.group());
                }
            }
            WebdriverExtensions.CloseBroswer();

            System.out.println("---------------------------------------------------------------------------");
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("---------------------------------------------------------------------------");

            for (String e : slink) {
                System.out.println(e);
                String[] split = e.split("@");
                String[] split2 = split[1].split("\\.");
                try {
                    if (split[1].toLowerCase().contains(companyFirstName.substring(0, 3).toLowerCase())) {
                        level1.add(split2[0]);
                        domainMail.add(e);
                        System.out.println("==============> Matched Domain " + split2[0]);
                    }
                } catch (Exception linkException) {
                    System.out.println(linkException);
                }
            }

            for (String e : slink) {
                System.out.println(e);
                String[] split = e.split("@");
                try {
                    if (split[0].toLowerCase().contains(firstName.toLowerCase())
                            || split[0].toLowerCase().contains(lastName.toLowerCase())
                            || split[0].toLowerCase().contains(firstName.toLowerCase().substring(0, 3))
                            || split[0].toLowerCase().contains(lastName.toLowerCase().subSequence(0, 3))) {
                        System.out.println("==============> Matched Name : " + split[0]);
                        nameEmail.add(e);
                    }
                } catch (Exception e2) {
                    System.out.println(e2);
                }
            }

            System.out.println("-----------------------------XXXXX-------------------------------------");
            System.out.println("----------------------------XXXXXXX--------------------------------------");
            System.out.println("-------------------------XXXXXXXXXXXX-------------------------------------");

            for (String e : domainMail) {
                if (e.toLowerCase().contains(".com") || e.toLowerCase().contains(".net")
                        || e.toLowerCase().contains(".org") || e.toLowerCase().contains(".cc")) {
                    System.out.println("Domain : " + e);
                    finalEmail = e;
                }
            }
            int bst2 = 0;
            for (String e : nameEmail) {
                if (e.toLowerCase().contains(".com") || e.toLowerCase().contains(".net")
                        || e.toLowerCase().contains(".org") || e.toLowerCase().contains(".cc")) {

                    System.out.println("Name : " + e);
                    finalEmail = e;

                    if (e.toLowerCase().contains(firstName.toLowerCase())
                            || e.toLowerCase().contains(lastName.toLowerCase())) {
                        finalEmail = e;
                        bst2++;
                    }
                    try {
                        if (e.toLowerCase().contains(companyFirstName.toLowerCase())
                                || e.toLowerCase().contains(companyFirstName.substring(0, 3).toLowerCase())) {
                            finalEmail = e;
                            bst2++;
                        }
                    } catch (Exception nameException) {
                        System.out.println(nameException);
                    }
                    if (bst2 == 2) {
                        System.out.println("Best Match Name : " + e);
                        finalEmail = e;
                        break;
                    } else {
                        bst2 = 0;
                    }
                }
            }
            if (finalEmail.isEmpty()) {
                finalEmail = "#N/A";
            }
        }

        System.out.println("====================================================================");
        System.out.println("            Classified Email : " + finalEmail + "                   ");
        System.out.println("====================================================================" + currentScrapID);
        System.out.println("                                                                       ");

        final Set<String> set1 = new HashSet<>();
        int titleCnt = 1;
        boolean got = false;
        for (String s : titles) {

            if (set1.add(s) && titleCnt < 2) {
                System.out.println("Grabbed Title : " + s);
                finalTitile = s;
                titleCnt++;
                got = true;
            }
        }
        if (!got) {
            finalTitile = "#N/A";
            System.out.println("Grabbed titles : " + finalTitile);
        }
        currentScrapID++;
        System.out.println("                                                                        ");
        System.out.println("                                                                        ");
        returnData[0] = finalEmail;
        returnData[1] = finalTitile;
        return returnData;
    }

}
