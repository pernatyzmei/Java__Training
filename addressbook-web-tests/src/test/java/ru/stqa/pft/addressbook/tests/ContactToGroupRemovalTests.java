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

public class ContactToGroupRemovalTests extends TestBase {
  private Properties properties;

  public ContactToGroupRemovalTests() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  @BeforeMethod
  public void ensurePreconditions() {

    if (app.db().contacts().size() ==0) {
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
    if (app.db().groups().size() ==0){
      app.goTo().GroupPage();
      app.group().create(new GroupData().
              withName(properties.getProperty("prim.name")).
              withHeader(properties.getProperty("prim.header")).
              withFooter(properties.getProperty("prim.footer")));
    }

    // гарантирует, что есть хотя бы одна связь контакта с группой
    app.goTo().HomePage();
    Contacts contactList = app.db().contacts();
    Groups groupList = app.db().groups();
    app.contact().addToGroup(contactList.iterator().next(), groupList.iterator().next());
    System.out.println("1");

  }

  @Test // (enabled = false)
  public void TestContactToGroupRemoval() {

    Groups beforeGroupList = app.db().groups();

    //получение списка групп, содержащих контакты
    Groups groupsHaveContacts = new Groups();
    for (GroupData group : beforeGroupList) {
     if (group.getContacts().size() != 0) {
       groupsHaveContacts.add(group);
      }
    }

    //выбор группы и контакта для удаления из списка контактов в этой группе
    GroupData groupRemoveContact = groupsHaveContacts.iterator().next();
    ContactData removedContact = groupRemoveContact.getContacts().iterator().next();

    // удаление контакта из группы с помощью GUI
    app.goTo().HomePage();
    app.contact().selectGroupToOpen(groupRemoveContact.getId());
    app.contact().removeContact(removedContact, groupRemoveContact);
    app.contact().returnToHomePage();



    //проверки на то, что в связующей таблице отсутствует строка со связью контакт-группа

  }


}
