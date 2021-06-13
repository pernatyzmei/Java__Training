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

public class GroupDeletionTests extends TestBase {

  private Properties properties;

  public GroupDeletionTests() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() ==0){
      app.goTo().GroupPage();
      app.group().create(new GroupData().
              withName(properties.getProperty("prim.name")).
              withHeader(properties.getProperty("prim.header")).
              withFooter(properties.getProperty("prim.footer")));
    }
  }


  @Test //(enabled = false)
  public void testGroupDeletion() throws Exception {
    Groups before = app.db().groups();
    app.goTo().GroupPage();
    GroupData deletedGroup = before.iterator().next();
    app.group().delete(deletedGroup);
    assertEquals(app.db().groups().size(), before.size() - 1);
    Groups after = app.db().groups();
    assertThat(after, equalTo(before.without(deletedGroup)));
  }
}
