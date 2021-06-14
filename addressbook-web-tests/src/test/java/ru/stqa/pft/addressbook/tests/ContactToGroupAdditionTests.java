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

public class ContactToGroupAdditionTests extends TestBase {
  private Properties properties;

  public ContactToGroupAdditionTests() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  @BeforeMethod
  public void ensurePreconditions() {
    /*
    предусловия для добавления в группу
    если есть связки контактов с группами -удалить, если нет:
    проверить, есть ли к-л контакты и группы в принципе (по бд)
    */
  }


  @Test
  public void TestContactToGroupAddition(){
    app.goTo().HomePage();
    Contacts contactList = app.db().contacts();
    ContactData addingContact = contactList.iterator().next(); //выбрали контакт, который сейчас будем добавлять
    Groups groupList = app.db().groups();
    GroupData group = groupList.iterator().next();
    app.contact().addToGroup(addingContact, group);

    //проверки на то, что в связующей таблице появилась строка со связью контакт-группа
  }

}
