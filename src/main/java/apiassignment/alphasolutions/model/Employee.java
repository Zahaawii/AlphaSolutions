package apiassignment.alphasolutions.model;


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

    public Employee() {}

    public Employee(int employeeId, String employeeName) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    public Employee(int employeeId, String employeeName, String employeeEmail, String employeeUsername, String employeePassword, List<Skill> skills, int roleId) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeeUsername = employeeUsername;
        this.employeePassword = employeePassword;
        this.skills = skills;
        this.roleId = roleId;
    }

    public int getEmployeeId() {
        return employeeId;
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


    public int getRoleId() {
        return roleId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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



    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public String calculateRole() {
        return switch (roleId) {
            case 1 -> "Developer";
            case 2 -> "Project Manager";
            case 3 -> "Admin";
            default -> "No role";
        };
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", employeeUsername='" + employeeUsername + '\'' +
                ", employeePassword='" + employeePassword + '\'' +
                ", skills=" + skills +
                ", roleId=" + roleId +
                '}';
    }
}

