package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitNewContact() {
    wd.findElement(By.name("submit")).click();
  }

  public void fillContactForm(ContactData contactData) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("nickname"), contactData.getNickname());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("email"), contactData.getFirstMail());
    type(By.name("email2"), contactData.getSecondMail());
    type(By.name("email3"), contactData.getThirdMail());
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  private void initContactModificationById(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }

  public void initContactModification() {
    click(By.xpath(".//img[@alt='Edit']"));
  }

  public void submitContactModification() {
    click(By.xpath(".//input[22]"));
  }

  public void deleteSelectedContact() {
    click(By.xpath(".//input[@value='Delete']"));
  }

  public void confirmDeletion() {
    assertConfirmation();
  }

  public void returnToHomePage() {
    click(By.linkText("home"));
  }

  public void create(ContactData data) {
    initContactCreation();
    fillContactForm(data);
    submitNewContact();
    contactCache = null;
    returnToHomePage();
  }

  public void modify(ContactData contact) {
    selectContactById(contact.getId());
    initContactModificationById(contact.getId());
    fillContactForm(contact);
    submitContactModification();
    contactCache = null;
    returnToHomePage();
  }

  public void delete(int index) {
    selectContact(index);
    deleteSelectedContact();
    confirmDeletion();
    returnToHomePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContact();
    confirmDeletion();
    contactCache = null;
    returnToHomePage();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      String lastName = element.findElement(By.xpath(".//td[2]")).getText();
      String firstName = element.findElement(By.xpath(".//td[3]")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      contacts.add(new ContactData().withId(id).withFirstname(firstName).withLastname(lastName));
    }
    return contacts;
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      String lastName = element.findElement(By.xpath(".//td[2]")).getText();
      String firstName = element.findElement(By.xpath(".//td[3]")).getText();
      String allPhones = element.findElement(By.xpath(".//td[6]")).getText();
      String address = element.findElement(By.xpath(".//td[4]")).getText();
      String allMails = element.findElement(By.xpath(".//td[5]")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      contactCache.add(new ContactData().withId(id).withFirstname(firstName).
              withLastname(lastName).withAllPhones(allPhones).withAddress(address).withAllMails(allMails));
    }
    return new Contacts(contactCache);
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String firstMail = wd.findElement(By.name("email")).getAttribute("value");
    String secondMail = wd.findElement(By.name("email2")).getAttribute("value");
    String thirdMail = wd.findElement(By.name("email3")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work)
            .withAddress(address)
            .withFirstMail(firstMail).withSecondMail(secondMail).withThirdMail(thirdMail);
  }

  //for Contact-Group connection manipulation

  public void selectGroupToAdd(int id){
    wd.findElement(By.xpath(".//div[4]/select/option[@value='" + id + "']")).click();
  }

  public void submitContactAddition(){
    wd.findElement(By.name("add")).click();
  }

  public void addToGroup(ContactData contact, GroupData group){
    selectContactById(contact.getId());
    selectGroupToAdd(group.getId());
    submitContactAddition();
    returnToHomePage();
  }

  public void removeFromGroup(){
    wd.findElement(By.name("remove")).click();
  }

  public void selectGroupToOpen(int id){
    wd.findElement(By.xpath(".//select/option[@value='" + id + "']")).click();
  }

  public void viewAllContacts(){
    wd.findElement(By.xpath(".//select/option[@value='All']")).click();
  }

  public void removeContact(ContactData contact, GroupData group){
    selectGroupToOpen(group.getId());
    selectContactById(contact.getId());
    removeFromGroup();
    returnToHomePage();
    viewAllContacts();
  }

}

