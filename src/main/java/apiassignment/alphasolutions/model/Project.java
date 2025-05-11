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
    private Integer sum;

    public Project() {}

    public Project(int projectId, String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
    }

    //adding full args constructor for testing purpose
    public Project(int projectId, String projectName, int employeeId, Date projectStartDate, Date projectEndDate, String projectDescription, String projectStatus) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.employeeId = employeeId;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.projectDescription = projectDescription;
        this.projectStatus = projectStatus;
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

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getSum() {
        return sum;
    }


    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", employeeId=" + employeeId +
                ", projectStartDate=" + projectStartDate +
                ", projectEndDate=" + projectEndDate +
                ", projectDescription='" + projectDescription + '\'' +
                ", projectStatus='" + projectStatus + '\'' +
                '}';
    }
}

