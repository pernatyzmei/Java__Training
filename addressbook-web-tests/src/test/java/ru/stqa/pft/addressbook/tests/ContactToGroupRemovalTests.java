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

public class ContactToGroupRemovalTests extends TestBase {
  private Properties properties;
  Groups groupsHaveContacts = new Groups();

  public ContactToGroupRemovalTests() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  @BeforeMethod
  public void ensurePreconditions() {
//проверка, что существуют какие-либо контакты
    if (app.db().contacts().size() == 0) {
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
    //проверка, что существуют какие-либо группы
    if (app.db().groups().size() == 0) {
      app.goTo().GroupPage();
      app.group().create(new GroupData().
              withName(properties.getProperty("prim.name")).
              withHeader(properties.getProperty("prim.header")).
              withFooter(properties.getProperty("prim.footer")));
    }

    Groups beforeGroupList = app.db().groups();
    //получение списка групп, связанных с контактами
    for (GroupData group : beforeGroupList) {
      if (group.getContacts().size() != 0) {
        groupsHaveContacts.add(group);
      }
    }
    // если нет групп, связанных с контактами, то создается связка группы с контактом
    if (groupsHaveContacts.size() ==0){
      app.goTo().HomePage();
      Contacts contactList = app.db().contacts();
      Groups groupList = app.db().groups();
      app.contact().addToGroup(contactList.iterator().next(), groupList.iterator().next());
    }

  }

  @Test // (enabled = false)
  public void TestContactToGroupRemoval() {

    //выбор группы с контактом внутри и контакта для удаления из списка контактов в этой группе
    GroupData groupWithContact = groupsHaveContacts.iterator().next();
    ContactData contactWithGroup = groupWithContact.getContacts().iterator().next();

    // удаление контакта из группы
    app.goTo().HomePage();
    app.contact().selectGroupToOpen(groupWithContact.getId());
    app.contact().removeContact(contactWithGroup, groupWithContact);
    app.contact().returnToHomePage();

    //проверки на то, что в связующей таблице отсутствует строка со связью контакт-группа: по содержимому списков групп

    Contacts contactList = app.db().contacts();
    ContactData contactWithoutGroup = app.contact().refresh(contactWithGroup, contactList);
    assertThat(contactWithoutGroup.getGroups(), equalTo(contactWithGroup.getGroups().without(groupWithContact)));
  }
}
