CREATE DATABASE alphasolutions;
USE alphasolutions;
DROP table IF EXISTS ´project´;
DROP table IF EXISTS ´subproject´;
DROP table IF EXISTS ´employee´;
DROP table IF EXISTS ´roles´;
DROP table IF EXISTS ´skills´;
DROP table IF EXISTS ´task´;
DROP table IF EXISTS ´subtask´;
DROP table IF EXISTS ´project_assignees´;
DROP table IF EXISTS ´subproject_assignees´;
DROP table IF EXISTS ´task_assignees´;
DROP table IF EXISTS ´subtask_assignees´;

CREATE TABLE project (
projectID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT UNIQUE,
project_Name VARCHAR(100) NOT NULL,
project_status VARCHAR(100) NOT NULL,
project_start_date DATE,
project_end_date DATE,
FOREIGN KEY (employeeID) REFERENCES employee(employeeID)
);

CREATE TABLE subproject (
subprojectID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT UNIQUE,
subproject_Name VARCHAR(100) NOT NULL,
subproject_start_date DATE,
subproject_end_date DATE,
FOREIGN KEY (projectId) REFERENCES project(projectId)
);

CREATE TABLE task (
taskID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT UNIQUE,
task_Name VARCHAR(100) NOT NULL,
FOREIGN KEY (subProjectId) REFERENCES subProject(subProjectId),
task_estimate Integer(100) NOT NULL,
task_start_date DATE,
task_end_date DATE,
task_priority VARCHAR(50),
task_description VARCHAR(256),
task_status VARCHAR (50)
);


CREATE TABLE subtask (
subtaskID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT UNIQUE,
subtask_Name VARCHAR (100) NOT NULL,
FOREIGN KEY (taskID) REFERENCES task(taskID),
subtask_estimate INTEGER(100) NOT NULL,
subtask_start_date DATE,
subtask_end_date DATE,
subtask_priority varchar(50),
subtask_description varchar(256),
subtask_status varchar(50)
);

CREATE TABLE employee ( 
employeeID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT UNIQUE,
employee_Name VARCHAR(100),
employee_email VARCHAR(100),
FOREIGN KEY (roleID) REFERENCES roles(roleID)
);

CREATE TABLE skill (
skillID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT UNIQUE,
skill_name VARCHAR(100)
);

CREATE TABLE roles (
roleID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT UNIQUE,
role_Name VARCHAR(100)
);

CREATE TABLE projectAssginees (
FOREIGN KEY (projectID) REFERENCES project(projectID),
FOREIGN KEY (employeeID) REFERENCES employee(employeeID)
);

CREATE TABLE subprojectAssignees (
FOREIGN KEY (subprojectID) REFERENCES subproject(subprojectID),
FOREIGN KEY (employeeID) REFERENCES employee(employeeID)
);

CREATE TABLE taskAssignees (
FOREIGN KEY (taskID) REFERENCES task(taskID),
FOREIGN KEY (employeeID) REFERENCES employee(employeeID)
);

CREATE TABLE subtaskAssignees ( 
FOREIGN KEY (subtaskID) REFERENCES subtask(subtaskID),
FOREIGN KEY (employeeID) REFERENCES employee(employeeID)
);

CREATE TABLE skillRelation (
FOREIGN KEY (skillID) REFERENCES skill(skillID),
FOREIGN KEY (employeeID) REFERENCES employee(employeeID)
);






