package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
    /*
    предусловия для удаления из группы
    проверить, есть ли связки в бд, если да - продолжить, если нет:
    создать связь
    */
  }

  @Test (enabled = false)
  public void TestContactToGroupRemoval() {
    app.goTo().HomePage();
    /*
    вынуть из таблицы со связками группу, в которой есть контакт: взять любую строку и поле group_id
    по этому id уже искать группу и удалять контакт из нее
    */

    //проверки на то, что в связующей таблице отсутствует строка со связью контакт-группа

  }
}
