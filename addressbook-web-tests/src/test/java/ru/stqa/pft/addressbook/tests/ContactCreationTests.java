package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTests extends TestBase {

  @Test //(enabled = false)
  public void testContactCreation() throws Exception {
    Set<ContactData> before = app.contact().all();
    ContactData contact =
            new ContactData().withFirstname("Chugunova").withMiddlename("Andreevna").withLastname("Yuliya").
                    withNickname("Loilek").withCompany("R-Tech").withAddress("Moscow").withHome("2-95-87");
    app.contact().create(contact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size() +1);

    contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals(before, after);
  }



}

