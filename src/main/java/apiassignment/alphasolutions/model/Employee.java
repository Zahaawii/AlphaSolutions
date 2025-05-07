package apiassignment.alphasolutions.model;

import jdk.jfr.DataAmount;

import java.util.ArrayList;
import java.util.List;


public class Employee {
    private int employeeId;
    private String employeeName;
    private String employeeEmail;
    private String employeeUsername;
    private String employeePassword;
    private List<Skill> skills = new ArrayList<>();
    private int roleId;

    public Employee(int employeeId, String employeeName, String employeeEmail, String employeeUsername, String employeePassword, int roleId) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeeUsername = employeeUsername;
        this.employeePassword = employeePassword;
        this.roleId = roleId;
    }
    public Employee(){

    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", employeeUsername='" + employeeUsername + '\'' +
                ", employeePassword='" + employeePassword + '\'' +
                ", roleId=" + roleId +
                '}';
    }

}
