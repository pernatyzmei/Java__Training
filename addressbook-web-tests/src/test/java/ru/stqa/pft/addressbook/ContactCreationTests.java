package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    initContactCreation();
    fillContactForm(new ContactData("Chugunova", "Andreevna", "Yuliya", "Loilek", "R-Tech", "Moscow", "2-95-87"));
    submitNewContact();
    returnToHomePage();

  }

}

