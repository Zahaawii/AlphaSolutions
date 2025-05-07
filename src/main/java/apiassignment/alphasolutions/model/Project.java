package apiassignment.alphasolutions.model;

import java.util.Date;

public class Project {
    private int projectId;
    private String projectName;
    private int employeeId;
    private Date projectStartDate;
    private Date projectEndDate;
    private String projectDescription;
    private String projectStatus;

    public Project() {}

    public Project(int projectId, String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public java.sql.Date getProjectStartDate() {
        return (java.sql.Date) projectStartDate;
    }

    public java.sql.Date getProjectEndDate() {
        return (java.sql.Date) projectEndDate;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public void setProjectEndDate(Date projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }
}