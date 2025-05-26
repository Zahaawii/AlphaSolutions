package apiassignment.alphasolutions.model;


import java.util.ArrayList;
import java.util.List;

public class SubProject {

    private int subprojectID;
    private String subprojectName;
    private String subprojectDescription;
    private java.sql.Date subprojectStartDate;
    private java.sql.Date subprojectEndDate;
    private int projectID;
    private List<Employee> assignees = new ArrayList<>();
    private List<SubTask> subtasks = new ArrayList<>();

    public SubProject(int subprojectID, String subprojectName, java.sql.Date subprojectStartDate, java.sql.Date subprojectEndDate, int projectID) {
        this.subprojectID = subprojectID;
        this.subprojectName = subprojectName;
        this.subprojectStartDate = subprojectStartDate;
        this.subprojectEndDate = subprojectEndDate;
        this.projectID = projectID;
    }

    public SubProject() {

    }

    public int getSubprojectID() {
        return subprojectID;
    }

    public void setSubprojectID(int subprojectID) {
        this.subprojectID = subprojectID;
    }

    public String getSubprojectName() {
        return subprojectName;
    }

    public java.sql.Date getSubprojectStartDate() {
        return subprojectStartDate;
    }

    public void subprojectEndDate(java.sql.Date subprojectEndDate) {
        this.subprojectEndDate = subprojectEndDate;
    }

    public java.sql.Date getSubprojectEndDate() {
        return subprojectEndDate;
    }

    public void setSubprojectName(String subprojectName) {
        this.subprojectName = subprojectName;

    }

    public void setSubprojectStartDate(java.sql.Date subprojectStartDate) {
        this.subprojectStartDate = subprojectStartDate;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public void setSubprojectEndDate(java.sql.Date subprojectEndDate) {
        this.subprojectEndDate = subprojectEndDate;
    }

    public List<Employee> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<Employee> assignees) {
        this.assignees = assignees;
    }

    public List<SubTask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<SubTask> subtasks) {
        this.subtasks = subtasks;
    }

    public String getSubprojectDescription() {
        return subprojectDescription;
    }

    public void setSubprojectDescription(String subprojectDescription) {
        this.subprojectDescription = subprojectDescription;
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
        return "SubProject{" +
                "subprojectID=" + subprojectID +
                ", subprojectName='" + subprojectName + '\'' +
                ", subprojectDescription='" + subprojectDescription + '\'' +
                ", subprojectStartDate=" + subprojectStartDate +
                ", subprojectEndDate=" + subprojectEndDate +
                ", projectID=" + projectID +
                ", assignees=" + assignees +
                ", subtasks=" + subtasks +
                '}';
    }
}
