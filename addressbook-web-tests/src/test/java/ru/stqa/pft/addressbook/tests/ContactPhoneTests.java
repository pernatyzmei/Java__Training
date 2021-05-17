package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData().withFirstname("Chugunova").withMiddlename("Andreevna").withLastname("Yuliya").
              withNickname("Loilek").withCompany("R-Tech").withAddress("Moscow").
              withHomePhone("2-95-87").withMobilePhone("+7(903)110").withWorkPhone("3 64 21")
              .withAddress("Moscow\nOchakovskaya\n33-301")
              .withFirstMail("111@123.ru").withSecondMail("222@123.ru").withThirdMail("333@123.ru"));
    }
  }

  @Test
  public void TestContactPhones(){
    app.goTo().HomePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
    assertThat(contact.getAllMails(), equalTo(mergeMails(contactInfoFromEditForm)));
  }



  private String mergeMails(ContactData contact) {
    return Arrays.asList(contact.getFirstMail(), contact.getSecondMail(), contact.getThirdMail())
            .stream().filter((s) -> !s.equals(""))
            .map(ContactPhoneTests::cleaned)
            .collect(Collectors.joining("\n"));

  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
            .stream().filter((s) -> !s.equals(""))
            .map(ContactPhoneTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  public static String cleaned(String anyContactInfo){
    return anyContactInfo.replaceAll("\\s","").replaceAll("[-()]", "");
  }
}
