package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData().withFirstname("Chugunova").withMiddlename("Andreevna").withLastname("Yuliya").
              withNickname("Loilek").withCompany("R-Tech").withAddress("Moscow").
              withHomePhone("2-95-87").withMobilePhone("+7(903)110").withWorkPhone("3 64 21")
              .withAddress("Moscow\nOchakovskaya\n33-301")
              .withFirstMail("111@123.ru").withSecondMail("222@123.ru").withThirdMail("333@123.ru"));
    }
  }

  @Test//(enabled = false)

  public void testContactModification() {

    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact =
            new ContactData().withId(modifiedContact.getId()).withFirstname("Chugunova").withMiddlename("Andreevna").withLastname("Yuliya").
            withNickname("Loilek").withCompany("R-Tech").withAddress("Moscow").
            withHomePhone("2-95-87").withMobilePhone("+7(903)110").withWorkPhone("3 64 21")
            .withAddress("Moscow\nOchakovskaya\n33-301")
            .withFirstMail("111@123.ru").withSecondMail("222@123.ru").withThirdMail("333@123.ru");
    app.contact().modify(contact);
    assertEquals(app.contact().count(), before.size());
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }


}

