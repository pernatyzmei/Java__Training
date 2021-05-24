package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {


  private Properties properties;

  public ContactModificationTests() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }


  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData().withFirstname(properties.getProperty("prim.firstname")).
              withMiddlename(properties.getProperty("prim.middlename")).
              withLastname(properties.getProperty("prim.lastname")).
              withNickname(properties.getProperty("prim.nickname")).
              withCompany(properties.getProperty("prim.company")).
              withAddress(properties.getProperty("prim.address")).
              withHomePhone(properties.getProperty("prim.homePhone")).
              withMobilePhone(properties.getProperty("prim.mobilePhone")).
              withWorkPhone(properties.getProperty("prim.workPhone")).
              withFirstMail(properties.getProperty("prim.firstMail")).
              withSecondMail(properties.getProperty("prim.secondMail")).
              withThirdMail(properties.getProperty("prim.thirdMail")));
    }
  }

  @Test//(enabled = false)

  public void testContactModification() {

    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact =
           new ContactData().withId(modifiedContact.getId()).
            withFirstname(properties.getProperty("sec.firstname")).
            withMiddlename(properties.getProperty("sec.middlename")).
            withLastname(properties.getProperty("sec.lastname")).
            withNickname(properties.getProperty("sec.nickname")).
            withCompany(properties.getProperty("sec.company")).
            withAddress(properties.getProperty("sec.address")).
            withHomePhone(properties.getProperty("sec.homePhone")).
            withMobilePhone(properties.getProperty("sec.mobilePhone")).
            withWorkPhone(properties.getProperty("sec.workPhone")).
            withFirstMail(properties.getProperty("sec.firstMail")).
            withSecondMail(properties.getProperty("sec.secondMail")).
            withThirdMail(properties.getProperty("sec.thirdMail"));

    app.contact().modify(contact);
    assertEquals(app.contact().count(), before.size());
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }


}

