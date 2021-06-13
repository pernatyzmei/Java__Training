package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import org.openqa.selenium.json.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {
  private Properties properties;

  public ContactCreationTests() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    try(BufferedReader reader = new BufferedReader(new FileReader(new File(properties.getProperty("web.contactFileUrl"))))){
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

    @Test(dataProvider = "validContactsFromJson") //(enabled = false)

  public void testContactCreation(ContactData contact) throws Exception {
    Contacts before = app.db().contacts();
    app.contact().create(contact);
    Contacts after = app.db().contacts();
    assertThat(after.size(), equalTo(before.size()+1));
    assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test// (enabled = false)
  public void testBadContactCreation() throws Exception {
    Contacts before = app.db().contacts();
    ContactData contact =
            new ContactData().withFirstname(properties.getProperty("prim.firstname") + "'").
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
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after =app.db().contacts();
    assertThat(after, equalTo(before));
  }


}
