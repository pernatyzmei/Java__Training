package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

  @Parameter(names= "-d", description = "Data format")
  public String format;

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
    if(format.equals("json")) {
      saveAsJson(contacts, new File(file));
    } else {
      System.out.println("Unrecognized format" + format);

    }
  }

  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson= new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    try( Writer writer = new FileWriter(file);){
      writer.write(json);
    }
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


}
