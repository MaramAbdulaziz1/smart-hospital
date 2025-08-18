package com.team10.smarthospital.model;

import java.time.LocalDate;

public class PatientProfileView extends BaseEntityView {

  private String name;

  private String email;

  private String phone;

  private GenderView sex;

  private Integer age;

  private LocalDate dateOfBirth;

  private String weight;

  private String height;

  private String bloodType;

  private String address;

  // Default constructor
  public PatientProfileView() {}

  // Constructor with required fields
  public PatientProfileView(String name, String email) {
    this.name = name;
    this.email = email;
  }

  // Getters and Setters
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public GenderView getSex() {
    return sex;
  }

  public void setSex(GenderView sex) {
    this.sex = sex;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getWeight() {
    return weight;
  }

  public void setWeight(String weight) {
    this.weight = weight;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public String getBloodType() {
    return bloodType;
  }

  public void setBloodType(String bloodType) {
    this.bloodType = bloodType;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  // For template use (formatted date of birth)
  public String getDob() {
    return dateOfBirth != null ? dateOfBirth.toString() : "";
  }

  // Expose ID for Thymeleaf templates
  public String getPatientId() {
    return getId() != null ? getId().toString() : "";
  }

  // Custom formatted Patient ID with fallback for mock data
  public String getFormattedPatientId() {
    if (getId() != null) {
      return "HAT" + String.format("%05d", getId()) + "D";
    }
    // Fallback for mock/test data when ID is null
    return "HAT17653D";
  }

  @Override
  public String toString() {
    return "PatientProfileView{"
      + "id="
      + getId()
      + ", name='"
      + name
      + '\''
      + ", email='"
      + email
      + '\''
      + ", phone='"
      + phone
      + '\''
      + ", sex="
      + sex
      + ", age="
      + age
      + '}';
  }
}
