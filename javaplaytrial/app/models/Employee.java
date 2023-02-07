package models;

public class Employee {
    private int id;
    private String empName;
    private String gender;
    private int phoneNumber;
    private String address;
    private String emailId;

    public Employee() {
    }

    public Employee(int id, String empName, String gender, int phoneNumber, String address, String emailId) {
        this.id = id;
        this.empName = empName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.emailId = emailId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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
                "id=" + id +
                ", empName='" + empName + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", address='" + address + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
