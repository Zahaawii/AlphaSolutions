package apiassignment.alphasolutions.model;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Task {
    private int taskId;
    private String taskName;
    private int subprojectId;
    private int taskEstimate;
    private Date taskStartDate;
    private Date taskEndDate;
    private String taskPriority;
    private String taskDescription;
    private String taskStatus;
    private List<SubTask> subtasks;

    public Task() {
    }

    public Task(int taskId, String taskName, int subprojectId, int taskEstimate, Date taskStartDate, Date taskEndDate, String taskPriority, String taskDescription, String taskStatus, List<SubTask> subtasks) {
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

    public int getTaskEstimate() {
        return taskEstimate;
    }

    public void setTaskEstimate(int taskEstimate) {
        this.taskEstimate = taskEstimate;
    }

    public Date getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(Date taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public Date getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(Date taskEndDate) {
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

    public String remainingDays () {
        if (taskStartDate == null || taskEndDate == null) {
            return "0";
        }
        long diffInMillies = taskEndDate.getTime() - taskStartDate.getTime();
        int days = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return days + " days";
    }



}
