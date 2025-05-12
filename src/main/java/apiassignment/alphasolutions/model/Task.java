package apiassignment.alphasolutions.model;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Task {
    private int taskId;
    private String taskName;
    private int subprojectId;
    private int taskEstimate;
    private java.sql.Date taskStartDate;
    private java.sql.Date taskEndDate;
    private String taskPriority;
    private String taskDescription;
    private String taskStatus;
    private List<SubTask> subtasks;
    private List<Employee> assignees;

    public Task() {
    }

    public Task(int taskId, String taskName, int subprojectId, int taskEstimate, java.sql.Date taskStartDate, java.sql.Date taskEndDate, String taskPriority, String taskDescription, String taskStatus, List<SubTask> subtasks) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.subprojectId = subprojectId;
        this.taskEstimate = taskEstimate;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
        this.taskPriority = taskPriority;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.subtasks = subtasks;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getSubprojectId() {
        return subprojectId;
    }

    public void setSubprojectId(int subprojectId) {
        this.subprojectId = subprojectId;
    }

    public Integer getTaskEstimate() {
        return taskEstimate;
    }

    public void setTaskEstimate(int taskEstimate) {
        this.taskEstimate = taskEstimate;
    }

    public java.sql.Date getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(java.sql.Date taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public java.sql.Date getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(java.sql.Date taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public List<SubTask> getSubtasks () {
        return subtasks;
    }

    public void setSubtasks (List<SubTask> subtasks) {
        this.subtasks = subtasks;
    }

    public List<Employee> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<Employee> assignees) {
        this.assignees = assignees;
    }

    public String remainingDays () {
        if (taskStartDate == null || taskEndDate == null) {
            return "0";
        }
        long diffInMillies = taskEndDate.getTime() - taskStartDate.getTime();
        int days = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return days + " days";
    }

    public int subtaskEstimateTotal() {
        int sum = 0;
        for (SubTask subtask : subtasks) {
            sum += subtask.getSubtaskEstimate();
        }
        return sum;
    }

    public String calculateStatus() {
        //hvis der ikke er subtasks, sæt task status til "not started"
        if (subtasks.isEmpty()) return "Not Started";

        boolean allComplete = true;
        boolean allNotstarted = true;

        /* loop gennem subtasks og tjek for deres statusser. Hvis en status er complete, opdateres allComplete boolean
         det samme tjek sker med "allNotstarted". Hvis begge statements bliver false returner vi 'in progress' */

        for (SubTask subtask : subtasks) {
            if (!subtask.getSubtaskStatus().equalsIgnoreCase("Completed")) {
                allComplete = false;
            }
            if (!subtask.getSubtaskStatus().equalsIgnoreCase("Not Started")) {
                allNotstarted = false;
            }
        }

        return allComplete ? "Completed" : allNotstarted ? "Not Started" : "In Progress";
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", subprojectId=" + subprojectId +
                ", taskEstimate=" + taskEstimate +
                ", taskStartDate=" + taskStartDate +
                ", taskEndDate=" + taskEndDate +
                ", taskPriority='" + taskPriority + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", subtasks=" + subtasks +
                ", assignees=" + assignees +
                '}';
    }
}
