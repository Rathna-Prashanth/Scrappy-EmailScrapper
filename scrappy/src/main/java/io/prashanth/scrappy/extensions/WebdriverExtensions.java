package io.prashanth.scrappy.extensions;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebdriverExtensions {

	public WebDriver driver;
	public static WebDriver driver2;
	public static String Pagesource="";
	
	public static void OpenBrowser() {
		System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
		driver2 = new ChromeDriver(chromeOptions);
		driver2.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
		driver2.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver2.manage().window().maximize();
	}
	
	public static String GetPageSource(final String URL) {
		
		ExecutorService executor = Executors.newCachedThreadPool();
		Callable<Object> task = new Callable<Object>() {
		   public Object call() {
				driver2.get(URL);
				Pagesource = driver2.getPageSource();
			    return"";
		   }
		};
		Future<Object> future = executor.submit(task);
		try {
		   Object result = future.get(180, TimeUnit.SECONDS); 
		} catch (TimeoutException ex) {
		  System.out.println("---------- Driver Timedout Out 1-------------");
		  Pagesource="Email";
		} catch (InterruptedException e) {
			 System.out.println("---------- Driver Timedout Out 2-------------");
			  Pagesource="Email";
		} catch (ExecutionException e) {
			 System.out.println("---------- Driver Timedout Out 3-------------");
			  Pagesource="Email";
		} finally {
		   future.cancel(true); // may or may not desire this
		}
		
		return Pagesource;
	
	}
	
	public static void CloseBroswer() {
		driver2.quit();
	}

	public void InvokeBrowser() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.nmlsconsumeraccess.org/");
		Thread.sleep(5000);
	}

	public void InvokeBrowserFirefox() throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/Driver/geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.nmlsconsumeraccess.org/");
		Thread.sleep(5000);
	}

	public void DriverQuite() {
		driver.quit();
	}

	public String getPageSource(String URL) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(URL);
		Thread.sleep(5000);
		String pageSource = driver.getPageSource();
		driver.quit();
		return pageSource;
	}

	public String getEmailID(String NMLSID) throws InterruptedException {
		String val;
		driver.findElement(By.id("searchText")).sendKeys(NMLSID);
		Thread.sleep(2000);
		driver.findElement(By.id("searchButton")).click();
		driver.findElement(By.xpath("//a[@class='individual']")).click();
		try {
			driver.findElement(By.xpath("//a[@class='goToBranch']")).click();
			Thread.sleep(5000);
			val = driver.findElement(By.xpath("//tr/td[contains(text(),'Email')]/following-sibling::td")).getText();
			System.out.println("NMLSID : " + NMLSID + "  Email : " + driver
					.findElement(By.xpath("//tr/td[contains(text(),'Email')]/following-sibling::td")).getText());
		} catch (Exception e) {
			System.out.println("NMLSID : " + NMLSID + "  Email : " + "#NA");
			val = "#N/A";
		}

		driver.findElement(By.xpath("(//img[@alt='back to search results'])[2]")).click();

		Thread.sleep(5000);
		return val;
	}

}
