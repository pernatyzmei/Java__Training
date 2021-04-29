package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.initContactCreation();
    app.fillContactForm(new ContactData("Chugunova", "Andreevna", "Yuliya", "Loilek", "R-Tech", "Moscow", "2-95-87"));
    app.submitNewContact();
    app.returnToHomePage();

  }

}

