package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test

  public void testContactModification(){
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Chugunova", "Andreevna", "Yuliya", "Loilek", "R-Tech", "Moscow", "2-95-87"));
    }
    int before = app.getContactHelper().getContactCount();
    app.getContactHelper().selectContact(before-1);
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Chugunova", "Andreevna", "Yuliya", "Loilek", "R-Tech", "Moscow", "2-95-87"));
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before);
  }
}

