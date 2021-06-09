package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.Objects;

@Entity
@Table (name = "addressbook")

public class ContactData {

  @Id
  @Column(name = "id")
  private int id = Integer.MAX_VALUE;

  @Column(name = "firstname")
  @Expose
  private String firstname;

  @Column(name = "middlename")
  @Expose
  private String middlename;

  @Column(name = "lastname")
  @Expose
  private String lastname;

  @Transient
  private String group;

  @Column(name = "nickname")
  @Expose
  private String nickname;

  @Column(name = "company")
  @Expose
  private String company;

  @Column(name = "address")
  @Type(type = "text")
  @Expose
  private String address;

  @Column(name = "home")
  @Type(type = "text")
  @Expose
  private String homePhone;

  @Column(name = "mobile")
  @Type(type = "text")
  @Expose
  private String mobilePhone;

  @Column(name = "work")
  @Type(type = "text")
  @Expose
  private String workPhone;

  @Column(name = "photo")
  @Type(type = "text")
  @Expose
  private String photo;

  @Transient
  private String allPhones;

  @Transient
  private String allMails;

  @Column(name = "email")
  @Type(type = "text")
  @Expose
  private String firstMail;

  @Column(name = "email2")
  @Type(type = "text")
  @Expose
  private String secondMail;

  @Column(name = "email3")
  @Type(type = "text")
  @Expose
  private String thirdMail;


  //setters


  public ContactData withId(int id) {
    this.id = id;
    return this;
  }

  public ContactData withFirstname(String firstname) {
    this.firstname = firstname;
    return this;
  }

  public ContactData withMiddlename(String middlename) {
    this.middlename = middlename;
    return this;
  }

  public ContactData withLastname(String lastname) {
    this.lastname = lastname;
    return this;
  }

  public ContactData withNickname(String nickname) {
    this.nickname = nickname;
    return this;
  }

  public ContactData withCompany(String company) {
    this.company = company;
    return this;
  }

  public ContactData withAddress(String address) {
    this.address = address;
    return this;
  }

  public ContactData withHomePhone(String home) {
    this.homePhone = home;
    return this;
  }

  public ContactData withMobilePhone(String mobile) {
    this.mobilePhone = mobile;
    return this;
  }

  public ContactData withWorkPhone(String work) {
    this.workPhone = work;
    return this;
  }

  public ContactData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public ContactData withAllMails(String allMails) {
    this.allMails = allMails;
    return this;
  }

  public ContactData withFirstMail(String firstMail) {
    this.firstMail = firstMail;
    return this;
  }

  public ContactData withSecondMail(String secondMail) {
    this.secondMail = secondMail;
    return this;
  }

  public ContactData withThirdMail(String thirdMail) {
    this.thirdMail = thirdMail;
    return this;
  }

  public ContactData withPhoto(File photo) {
    this.photo = photo.getPath();
    return this;
  }

  public void setId(int id) {
    this.id = id;
  }


//getters

  public int getId() {
    return id;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getMiddlename() {
    return middlename;
  }

  public String getLastname() {
    return lastname;
  }

  public String getNickname() {
    return nickname;
  }

  public String getCompany() {
    return company;
  }

  public String getAddress() {
    return address;
  }

  public String getHomePhone() {
    return homePhone;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public String getWorkPhone() {
    return workPhone;
  }

  public String getAllPhones() {
    return allPhones;
  }

  public String getAllMails() {
    return allMails;
  }

  public String getFirstMail() {
    return firstMail;
  }

  public String getSecondMail() {
    return secondMail;
  }

  public String getThirdMail() {
    return thirdMail;
  }

  public File getPhoto() {
    return new File(photo);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return id == that.id && Objects.equals(firstname, that.firstname)
            && Objects.equals(middlename, that.middlename)
            && Objects.equals(lastname, that.lastname)
            && Objects.equals(group, that.group)
            && Objects.equals(nickname, that.nickname)
            && Objects.equals(company, that.company)
             && Objects.equals(homePhone, that.homePhone)
            && Objects.equals(mobilePhone, that.mobilePhone)
            && Objects.equals(workPhone, that.workPhone)
            && Objects.equals(allPhones, that.allPhones)
            && Objects.equals(allMails, that.allMails)
            && Objects.equals(firstMail, that.firstMail)
            && Objects.equals(secondMail, that.secondMail)
            && Objects.equals(thirdMail, that.thirdMail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id,  firstname, middlename, lastname, group, nickname, company,
          homePhone, mobilePhone, workPhone, allPhones, allMails, firstMail, secondMail, thirdMail );
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id='" + id + '\'' +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            '}';
  }


}
