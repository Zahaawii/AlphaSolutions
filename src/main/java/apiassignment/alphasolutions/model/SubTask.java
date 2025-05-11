package apiassignment.alphasolutions.model;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SubTask {
    private int subtaskID;
    private String subtaskName;
    private int taskID;
    private int subtaskEstimate;
    private java.sql.Date subtaskStartDate;
    private java.sql.Date subtaskEndDate;
    private String subtaskPriority;
    private String subtaskDescription;
    private String subtaskStatus;
    private List<Employee> assignees;

    public SubTask() {
    }

    public SubTask(int subtaskID, String subtaskName, int taskID, int subtaskEstimate, java.sql.Date subtaskStartDate, java.sql.Date subtaskEndDate, String subtaskPriority, String subtaskDescription, String subtaskStatus, List<Employee> assignees) {
        this.subtaskID = subtaskID;
        this.subtaskName = subtaskName;
        this.taskID = taskID;
        this.subtaskEstimate = subtaskEstimate;
        this.subtaskStartDate = subtaskStartDate;
        this.subtaskEndDate = subtaskEndDate;
        this.subtaskPriority = subtaskPriority;
        this.subtaskDescription = subtaskDescription;
        this.subtaskStatus = subtaskStatus;
        this.assignees = assignees;
    }

    public int getSubtaskID() {
        return subtaskID;
    }

    public void setSubtaskID(int subtaskID) {
        this.subtaskID = subtaskID;
    }

    public String getSubtaskName() {
        return subtaskName;
    }

    public void setSubtaskName(String subtaskName) {
        this.subtaskName = subtaskName;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public Integer getSubtaskEstimate() {
        return subtaskEstimate;
    }

    public void setSubtaskEstimate(int subtaskEstimate) {
        this.subtaskEstimate = subtaskEstimate;
    }

    public java.sql.Date getSubtaskStartDate() {
        return subtaskStartDate;
    }

    public void setSubtaskStartDate(java.sql.Date subtaskStartDate) {
        this.subtaskStartDate = subtaskStartDate;
    }

    public java.sql.Date getSubtaskEndDate() {
        return subtaskEndDate;
    }

    public void setSubtaskEndDate(java.sql.Date subtaskEndDate) {
        this.subtaskEndDate = subtaskEndDate;
    }

    public String getSubtaskPriority() {
        return subtaskPriority;
    }

    public void setSubtaskPriority(String subtaskPriority) {
        this.subtaskPriority = subtaskPriority;
    }

    public String getSubtaskDescription() {
        return subtaskDescription;
    }

    public void setSubtaskDescription(String subtaskDescription) {
        this.subtaskDescription = subtaskDescription;
    }

    public String getSubtaskStatus() {
        return subtaskStatus;
    }

    public void setSubtaskStatus(String subtaskStatus) {
        this.subtaskStatus = subtaskStatus;
    }

    public List<Employee> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<Employee> assignees) {
        this.assignees = assignees;
    }

    public String remainingDays () {
        if (subtaskStartDate == null || subtaskEndDate == null) {
            return "0";
        }
        long diffInMillies = subtaskEndDate.getTime() - subtaskStartDate.getTime();
        int days = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return days + " days";
    }
}
