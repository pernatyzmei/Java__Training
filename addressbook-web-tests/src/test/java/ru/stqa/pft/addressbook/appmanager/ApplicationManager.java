package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class ApplicationManager {
  WebDriver wd;
  private SessionHelper sessionHelper;
  private  ContactHelper contactHelper;
  private  NavigationHelper navigationHelper;
  private  GroupHelper groupHelper;
  private StringBuffer verificationErrors = new StringBuffer();
  private String browser;

  public ApplicationManager(String browser) {
    this.browser = browser;
  }

  public void init() {
    if (browser.equals(BrowserType.FIREFOX)){
      wd = new FirefoxDriver();
    } else if (browser.equals(BrowserType.CHROME)){
      wd = new ChromeDriver();
    } else if (browser.equals(BrowserType.IE)){
      wd = new InternetExplorerDriver();
    }

    wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    groupHelper = new GroupHelper(wd);
    navigationHelper = new NavigationHelper(wd);
    contactHelper = new ContactHelper(wd);
    sessionHelper = new SessionHelper(wd);
    wd.get("http://localhost/addressbook/");
    sessionHelper.login("admin", "secret");
  }



  public void stop() {
    wd.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
      sessionHelper.logout();
    }
  }


  public GroupHelper getGroupHelper() {
    return groupHelper;
  }

  public NavigationHelper getNavigationHelper() {
    return navigationHelper;
  }

  public ContactHelper getContactHelper() {
    return contactHelper;
  }
}
