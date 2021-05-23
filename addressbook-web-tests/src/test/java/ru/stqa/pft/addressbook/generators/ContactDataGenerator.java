package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

  @Parameter(names= "-c", description = "Contact count")
  public int count;

  @Parameter(names= "-f", description = "Target file")
  public String file;

  public static void main(String[] args) throws IOException {

    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex){
      jCommander.usage();
      return;
    }
    generator.run();

  }

  private void run ()  throws IOException{
    List<ContactData> contacts = generateContacts(count);
    save(contacts, new File(file));
  }


  private  List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().withFirstname(String.format("Test Firstname %s", i)).
              withMiddlename(String.format("Test Middlename %s", i)).withLastname(String.format("Test Lastname %s", i)).
              withNickname(String.format("Test Nickname %s", i)).withCompany(String.format("Test Company %s", i)).
              withAddress(String.format("Test Address %s", i)).withHomePhone(String.format("1-23-4%s", i)).
              withMobilePhone(String.format("8(911)110-67-5%s", i)).withWorkPhone(String.format("2 95 8%s", i)).
              withFirstMail(String.format("TestFirst@Mail.ru %s", i)).withSecondMail(String.format("TestSecond@Mail.ru %s", i)).
              withThirdMail(String.format("TestThird@Mail.ru %s", i)));
    }
    return contacts;
  }

  private void save(List<ContactData> contacts, File file) throws IOException {
    Writer writer = new FileWriter(file);
    for (ContactData contact:contacts){
      writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getFirstname(), contact.getMiddlename(), contact.getLastname(),
              contact.getNickname(), contact.getCompany(), contact.getAddress(), contact.getHomePhone(), contact.getMobilePhone(),
              contact.getWorkPhone(), contact.getFirstMail(), contact.getSecondMail(), contact.getThirdMail()));
    }
    writer.close();
  }


}
