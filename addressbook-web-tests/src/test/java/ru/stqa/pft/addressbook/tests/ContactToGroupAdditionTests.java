package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactToGroupAdditionTests extends TestBase {
  private Properties properties;

  public ContactToGroupAdditionTests() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  Contacts contactsWithoutGroups = new Contacts();

  @BeforeMethod
  public void ensurePreconditions() {

    Contacts beforeContactList = app.db().contacts();
    // получение списка контактов без группы
    for (ContactData contact : beforeContactList) {
      if (contact.getGroups().size() == 0) {
        contactsWithoutGroups.add(contact);
      }
    }
    if (contactsWithoutGroups.size() == 0) {
      ContactData newContact = new ContactData().withFirstname(properties.getProperty("prim.firstname")).
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
              withThirdMail(properties.getProperty("prim.thirdMail"));
      app.contact().create(newContact);
      Contacts afterContactList = app.db().contacts();
      // получение списка контактов, не включенных в группы
      for (ContactData contact : afterContactList) {
        if (contact.getGroups().size() == 0) {
          contactsWithoutGroups.add(contact);
        }
      }

      if (app.db().groups().size() == 0) {
        app.goTo().GroupPage();
        app.group().create(new GroupData().
                withName(properties.getProperty("prim.name")).
                withHeader(properties.getProperty("prim.header")).
                withFooter(properties.getProperty("prim.footer")));
      }

    }
  }


  @Test
  public void TestContactToGroupAddition() {
    app.goTo().HomePage();
    ContactData before = contactsWithoutGroups.iterator().next();
    Groups groupList = app.db().groups();
    GroupData group = groupList.iterator().next();
    app.contact().addToGroup(before, group);

    //проверка на то, что это контакт связан с конкретной группой
    Contacts contactList = app.db().contacts();
    ContactData after = app.contact().refresh(before, contactList);
    assertThat(after.getGroups(), equalTo(before.getGroups().withAdded(group)));

  }
}
