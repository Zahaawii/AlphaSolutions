package apiassignment.alphasolutions.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project {
    private int projectId;
    private String projectName;
    private int employeeId;
    private Date projectStartDate;
    private Date projectEndDate;
    private String projectDescription;
    private String projectStatus;
    private Integer sum;
    private List<Employee> assignees = new ArrayList<>();
    private List<SubTask> subtasks = new ArrayList<>();


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

    public List<Employee> getAssignees() {
        return assignees;
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

    public void setAssignees(List<Employee> assignees) {
        this.assignees = assignees;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getSum() {
        return sum;
    }

    public List<SubTask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<SubTask> subtasks) {
        this.subtasks = subtasks;
    }

    public int calculateCompletion() {
        int subtaskcount = subtasks.size();
        int subtaskscomplete = 0;

        for (SubTask subtask : subtasks) {
            if (subtask.getSubtaskStatus().equalsIgnoreCase("Completed")) {
                subtaskscomplete++;
            }
        }

        return Math.round(((float) subtaskscomplete / subtaskcount) * 100);
    }

    public int calculateTotalEstimate() {
        int sum = 0;
        for (SubTask subtask : subtasks) {
            sum += subtask.getSubtaskEstimate();
        }
        return sum;
    }

    public int calculateTotalActual() {
        int sum = 0;
        for (SubTask subtask : subtasks) {
            sum += subtask.getSubtaskHoursSpent();
        }
        return sum;
    }

    public double calculatePredictionRatio() {
        double actual = calculateTotalActual();
        double estimate = calculateTotalEstimate();



        if (actual / estimate == 0) return 0.00;


        double ratio = (estimate / actual);

        return Math.round(ratio * 100.0) / 100.0;
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

