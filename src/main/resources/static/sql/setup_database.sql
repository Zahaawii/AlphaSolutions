-- Opretter databasen

CREATE DATABASE IF NOT EXISTS alphasolutions;
USE alphasolutions;

-- Slet tabeller i korrekt rækkefølge (pga foreign keys)
DROP TABLE IF EXISTS skillRelation;
DROP TABLE IF EXISTS subtaskAssignees;
DROP TABLE IF EXISTS taskAssignees;
DROP TABLE IF EXISTS subprojectAssignees;
DROP TABLE IF EXISTS projectAssignees;
DROP TABLE IF EXISTS subtask;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS subproject;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS skill;

CREATE TABLE roles (
                       roleID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT UNIQUE,
                       role_Name VARCHAR(100)
);

CREATE TABLE skill (
                       skillID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT UNIQUE,
                       skill_name VARCHAR(100)
);

CREATE TABLE employee (
                          employeeID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT UNIQUE,
                          employee_Name VARCHAR(100),
                          employee_email VARCHAR(100),
                          employee_username VARCHAR(100),
                          employee_password VARCHAR(256),
                          roleID INTEGER,
                          FOREIGN KEY (roleID) REFERENCES roles(roleID) ON DELETE SET NULL
);

CREATE TABLE project (
                         projectID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT UNIQUE,
                         project_Name VARCHAR(100) NOT NULL,
                         project_status VARCHAR(100),
                         project_start_date DATE,
                         project_end_date DATE,
                         project_description VARCHAR(256),
                         employeeID INTEGER,
                         FOREIGN KEY (employeeID) REFERENCES employee (employeeID) ON DELETE SET NULL
);

CREATE TABLE subproject (
                            subprojectID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT UNIQUE,
                            subproject_Name VARCHAR(100) NOT NULL,
                            subproject_start_date DATE,
                            subproject_end_date DATE,
                            subproject_description VARCHAR(256),
                            projectID INTEGER,
                            subproject_hours_spent INTEGER,
                            FOREIGN KEY (projectID) REFERENCES project (projectID) ON DELETE CASCADE
);

CREATE TABLE task
(
                            taskID           INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT UNIQUE,
                            task_Name        VARCHAR(100)        NOT NULL,
                            subProjectId     INTEGER,
                            FOREIGN KEY (subProjectId) REFERENCES subproject (subprojectID) ON DELETE CASCADE,
                            task_start_date  DATE,
                            task_end_date    DATE,
                            task_priority    VARCHAR(50),
                            task_description VARCHAR(256),
                            task_hours_spent INTEGER
);

CREATE TABLE subtask (
                         subtaskID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT UNIQUE,
                         subtask_Name VARCHAR(100) NOT NULL,
                         taskID INTEGER,
                         FOREIGN KEY (taskID) REFERENCES task(taskID) ON DELETE CASCADE,
                         subtask_estimate INTEGER NOT NULL,
                         subtask_start_date DATE,
                         subtask_end_date DATE,
                         subtask_priority VARCHAR(50),
                         subtask_description VARCHAR(256),
                         subtask_status VARCHAR(50),
                     subtask_hours_spent INTEGER
);

CREATE TABLE projectAssignees
(
    projectID INTEGER,
    employeeID INTEGER,
    PRIMARY KEY (projectID, employeeID),
    FOREIGN KEY (projectID) REFERENCES project (projectID) ON DELETE CASCADE,
    FOREIGN KEY (employeeID) REFERENCES employee (employeeID) ON DELETE CASCADE
);

CREATE TABLE subprojectAssignees
(
    subprojectID INTEGER,
    employeeID INTEGER,
    PRIMARY KEY (subprojectID, employeeID),
    FOREIGN KEY (subprojectID) REFERENCES subproject (subprojectID) ON DELETE CASCADE,
    FOREIGN KEY (employeeID) REFERENCES employee (employeeID) ON DELETE CASCADE
);

CREATE TABLE taskAssignees
(
    taskID INTEGER,
    employeeID INTEGER,
    PRIMARY KEY (taskID, employeeID),
    FOREIGN KEY (taskID) REFERENCES task (taskID) ON DELETE CASCADE,
    FOREIGN KEY (employeeID) REFERENCES employee (employeeID) ON DELETE CASCADE
);

CREATE TABLE subtaskAssignees
(
    subtaskID INTEGER,
    employeeID INTEGER,
    PRIMARY KEY (subtaskID, employeeID),
    FOREIGN KEY (subtaskID) REFERENCES subtask (subtaskID) ON DELETE CASCADE,
    FOREIGN KEY (employeeID) REFERENCES employee (employeeID) ON DELETE CASCADE
);

CREATE TABLE skillRelation
(
    skillID INTEGER,
    employeeID INTEGER,
    PRIMARY KEY (skillID, employeeID),
    FOREIGN KEY (skillID) REFERENCES skill (skillID) ON DELETE CASCADE,
    FOREIGN KEY (employeeID) REFERENCES employee (employeeID) ON DELETE CASCADE
);

CREATE TABLE awaitingEmployee (
    awaitingEmployeeID INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT UNIQUE,
    awaitingEmployee_name VARCHAR(100),
    awaitingEmployee_email VARCHAR(100),
    awaitingEmployee_username VARCHAR(100),
    awaitingEmployee_password VARCHAR(256),
    awaitingEmployee_status VARCHAR(100)
);




