package apiassignment.alphasolutions.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private int employeeID;
    private String employeeName;
    private String employeeEmail;
    private String employeeUsername;
    private String employeePassword;
    private List<Skill> skills = new ArrayList<>();
    private int roleID;

    public Employee() {}

    public Employee(int employeeID, String employeeName) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
}

