package apiassignment.alphasolutions.model;



public class SubProject {

    private int subprojectID;
    private String subprojectName;
    private java.sql.Date subprojectStartDate;
    private java.sql.Date subprojectEndDate;
    private int projectID;

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
        return (java.sql.Date) subprojectStartDate;
    }

    public void subprojectEndDate(java.sql.Date subprojectEndDate) {
        this.subprojectEndDate = subprojectEndDate;
    }

    public java.sql.Date getSubprojectEndDate() {
        return (java.sql.Date) subprojectEndDate;
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

    @Override
    public String toString() {
        return "subProject{" +
                "subprojectID=" + subprojectID +
                ", subproject_name='" + subprojectName + '\'' +
                ", subproject_start_date=" + subprojectStartDate +
                ", subproject_end_date=" + subprojectEndDate +
                ", projectID=" + projectID +
                '}';
    }
}
