package apiassignment.alphasolutions.DTO;


import java.util.ArrayList;
import java.util.List;

public class DTOEmployee {
    private int employeeId;
    private String employeeName;
    private String employeeEmail;
    private String employeeUsername;
    private String employeePassword;
    private List<Integer> skills = new ArrayList<>();
    private int roleId;

    public DTOEmployee() {}

    public DTOEmployee(int employeeId, String employeeName) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    public DTOEmployee(int employeeId, String employeeName, String employeeEmail, String employeeUsername, String employeePassword, List<Integer> skills, int roleId) {
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

    public List<Integer> getSkills() {
        return skills;
    }

    public void setSkills(List<Integer> skills) {
        this.skills = skills;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
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

