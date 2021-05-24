package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupModificationsTests extends TestBase {

  private Properties properties;

  public GroupModificationsTests() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().GroupPage();
    if (app.group().list().size()==0) {
      app.group().create(new GroupData().
              withName(properties.getProperty("prim.name")).
              withHeader(properties.getProperty("prim.header")).
              withFooter(properties.getProperty("prim.footer")));
    }
  }

  @Test //(enabled = false)
  public void testGroupModification() {
    Groups before = app.group().all();
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData().withId(modifiedGroup.getId()).
            withName(properties.getProperty("sec.name")).
            withHeader(properties.getProperty("sec.header")).
            withFooter(properties.getProperty("sec.footer"));
    app.group().modify(group);
    assertEquals(app.group().count(), before.size());
   Groups after = app.group().all();
    assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
  }

}
