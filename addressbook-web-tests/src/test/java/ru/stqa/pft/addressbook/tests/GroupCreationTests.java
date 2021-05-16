package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @Test //(enabled = false)
  public void testGroupCreation() throws Exception {
    app.goTo().GroupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("Group1").withHeader("group_header_1").withFooter("group_footer_1");
    app.group().create(group);
    assertThat(app.group().count(),  equalTo(before.size() + 1));
    Groups after = app.group().all();
    assertThat(after, equalTo(before.withAdded(group.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));
  }

  @Test// (enabled = false)
  public void testBadGroupCreation() throws Exception {
    app.goTo().GroupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("Group'1").withHeader("group_header_1").withFooter("group_footer_1");
    app.group().create(group);
    assertThat(app.group().count(),  equalTo(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before));
  }
}

