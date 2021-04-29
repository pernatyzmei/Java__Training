package ru.stqa.pft.addressbook;

import org.testng.annotations.*;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {

    goToGroupPage();
    initGroupCreation();
    fillGroupForm(new GroupData("Group1", "group_header_1", "group_footer_1"));
    submitGroupCreation();
    returnToGroupPage();

  }

}

