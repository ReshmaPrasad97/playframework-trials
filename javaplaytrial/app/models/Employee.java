package models;

import java.sql.Date;

public class Employee {
    private int empId;
    private String empName;
    private Date DOB;
    private  String gender;
    private  int phoneNumber;
    private String address;
    private  String emailId;
  
    public Employee() {
    }

    public Employee(int empId, String empName, Date DOB, String gender, int phoneNumber, String address, String emailId) {
        this.empId = empId;
        this.empName = empName;
        this.DOB = DOB;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.emailId = emailId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", DOB=" + DOB +
                ", gender='" + gender + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", address='" + address + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }

    public void setId(int id) {
    }
}
