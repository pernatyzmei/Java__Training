package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData().withFirstname("Chugunova").withMiddlename("Andreevna").withLastname("Yuliya").
              withNickname("Loilek").withCompany("R-Tech").withAddress("Moscow").withHome("2-95-87"));
    }
  }

  @Test//(enabled = false)

  public void testContactModification() {

    Set<ContactData> before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact =
            new ContactData().withId(modifiedContact.getId()).withFirstname("Chugunova").withMiddlename("Andreevna").withLastname("Yuliya").
                    withNickname("Loilek").withCompany("R-Tech").withAddress("Moscow").withHome("2-95-87");
    app.contact().modify(contact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedContact);
    before.add(contact);
    Assert.assertEquals(before, after);

  }


}

