package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionHelper extends HelperBase {


  public SessionHelper(WebDriver wd) {
    super(wd);
  }

  public void login(String username, String password) {
    type(By.name("pass"), password);
    type(By.name("user"), username);
    click(By.xpath("//input[@value='Login']"));
  }


  public void logout() {
    click(By.linkText("Logout"));
    wd.findElement(By.name("user")).clear();
    wd.findElement(By.name("user")).sendKeys("admin");
  }
}
