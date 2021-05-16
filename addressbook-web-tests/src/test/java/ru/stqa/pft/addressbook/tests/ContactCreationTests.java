package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test //(enabled = false)
  public void testContactCreation() throws Exception {
    Contacts before = app.contact().all();
    ContactData contact =
            new ContactData().withFirstname("Chugunova").withMiddlename("Andreevna").withLastname("Yuliya").
                    withNickname("Loilek").withCompany("R-Tech").withAddress("Moscow").withHome("2-95-87");
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()+1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test //(enabled = false)
  public void testBadContactCreation() throws Exception {
    Contacts before = app.contact().all();
    ContactData contact =
            new ContactData().withFirstname("Chugun'ova").withMiddlename("Andreevna").withLastname("Yuliya").
                    withNickname("Loilek").withCompany("R-Tech").withAddress("Moscow").withHome("2-95-87");
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }


}

