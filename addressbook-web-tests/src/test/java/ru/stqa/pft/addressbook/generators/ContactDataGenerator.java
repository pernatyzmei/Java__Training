package ru.stqa.pft.addressbook.generators;

import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
  public static void main(String[] args) throws IOException {

    int count = Integer.parseInt(args[0]);
    File file = new File(args[1]);

    List<ContactData> contacts = generateContacts(count);
    save(contacts, file);

  }

  private static List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().withFirstname(String.format("Test Firstname %s", i)).
              withMiddlename(String.format("Test Middlename %s", i)).withLastname(String.format("Test Lastname %s", i)).
              withNickname(String.format("Test Nickname %s", i)).withCompany(String.format("Test Company %s", i)).
              withAddress(String.format("Test Address %s", i)).withHomePhone(String.format("1-23-4%s", i)).
              withMobilePhone(String.format("8(911)110-67-5%s", i)).withWorkPhone(String.format("2 95 8%s", i)).
              withFirstMail(String.format("TestFirst@Mail.ru %s", i)).withSecondMail(String.format("TestSecond@Mail.ru %s", i)).
              withThirdMail(String.format("TestThirdMail.ru %s", i)));
    }
    return contacts;
  }

  private static void save(List<ContactData> contacts, File file) throws IOException {
    Writer writer = new FileWriter(file);
    for (ContactData contact:contacts){
      writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getFirstname(), contact.getMiddlename(), contact.getLastname(),
              contact.getNickname(), contact.getCompany(), contact.getAddress(), contact.getHomePhone(), contact.getMobilePhone(),
              contact.getWorkPhone(), contact.getFirstMail(), contact.getSecondMail(), contact.getThirdMail()));
    }
    writer.close();
  }


}
