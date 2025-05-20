-- BRUGES TIL TEST DATABASE FOR INTEGRATIONSTEST

-- Slår FK-Check fra så vi kan slette alle tabeller
SET REFERENTIAL_INTEGRITY FALSE;

DROP TABLE IF EXISTS subtaskAssignees;
DROP TABLE IF EXISTS taskAssignees;
DROP TABLE IF EXISTS projectAssignees;
DROP TABLE IF EXISTS skillRelation;
DROP TABLE IF EXISTS subtask;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS subproject;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS awaitingEmployee;
DROP TABLE IF EXISTS skill;
DROP TABLE IF EXISTS roles;

-- Tilføjer FK check igen
SET REFERENTIAL_INTEGRITY TRUE;

-- Opret tabeller
CREATE TABLE IF NOT EXISTS roles (
                                     roleID     INTEGER PRIMARY KEY AUTO_INCREMENT,
                                     role_Name  VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS skill (
                                     skillID     INTEGER PRIMARY KEY AUTO_INCREMENT,
                                     skill_name  VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS employee (
                                        employeeID       INTEGER PRIMARY KEY AUTO_INCREMENT,
                                        employee_Name    VARCHAR(100)    NOT NULL,
                                        employee_email   VARCHAR(100)    NOT NULL,
                                        employee_username VARCHAR(100)   NOT NULL,
                                        employee_password VARCHAR(256)   NOT NULL,
                                        roleID           INTEGER,
                                        FOREIGN KEY (roleID)
                                            REFERENCES roles(roleID)
                                            ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS project (
                                       projectID          INTEGER PRIMARY KEY AUTO_INCREMENT,
                                       project_Name       VARCHAR(100) NOT NULL,
                                       project_status     VARCHAR(100),
                                       project_start_date DATE,
                                       project_end_date   DATE,
                                       project_description VARCHAR(256),
                                       employeeID         INTEGER,
                                       FOREIGN KEY (employeeID)
                                           REFERENCES employee(employeeID)
                                           ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS subproject (
                                          subprojectID        INTEGER PRIMARY KEY AUTO_INCREMENT,
                                          subproject_Name     VARCHAR(100) NOT NULL,
                                          subproject_start_date DATE,
                                          subproject_end_date   DATE,
                                          subproject_description VARCHAR(256),
                                          projectID           INTEGER,
                                          subproject_hours_spent INTEGER,
                                          FOREIGN KEY (projectID)
                                              REFERENCES project(projectID)
                                              ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS task (
                                    taskID            INTEGER PRIMARY KEY AUTO_INCREMENT,
                                    task_Name         VARCHAR(100) NOT NULL,
                                    subProjectId      INTEGER,
                                    task_start_date   DATE,
                                    task_end_date     DATE,
                                    task_priority     VARCHAR(50),
                                    task_description  VARCHAR(256),
                                    task_hours_spent  INTEGER,
                                    FOREIGN KEY (subProjectId)
                                        REFERENCES subproject(subprojectID)
                                        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS subtask (
                                       subtaskID           INTEGER PRIMARY KEY AUTO_INCREMENT,
                                       subtask_Name        VARCHAR(100) NOT NULL,
                                       taskID              INTEGER,
                                       subtask_estimate    INTEGER    NOT NULL,
                                       subtask_start_date  DATE,
                                       subtask_end_date    DATE,
                                       subtask_priority    VARCHAR(50),
                                       subtask_description VARCHAR(256),
                                       subtask_status      VARCHAR(50),
                                       subtask_hours_spent INTEGER,
                                       FOREIGN KEY (taskID)
                                           REFERENCES task(taskID)
                                           ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS projectAssignees (
                                                projectID  INTEGER,
                                                employeeID INTEGER,
                                                PRIMARY KEY (projectID, employeeID),
                                                FOREIGN KEY (projectID)
                                                    REFERENCES project(projectID)
                                                    ON DELETE CASCADE,
                                                FOREIGN KEY (employeeID)
                                                    REFERENCES employee(employeeID)
                                                    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS taskAssignees (
                                             taskID     INTEGER,
                                             employeeID INTEGER,
                                             PRIMARY KEY (taskID, employeeID),
                                             FOREIGN KEY (taskID)
                                                 REFERENCES task(taskID)
                                                 ON DELETE CASCADE,
                                             FOREIGN KEY (employeeID)
                                                 REFERENCES employee(employeeID)
                                                 ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS subtaskAssignees (
                                                subtaskID  INTEGER,
                                                employeeID INTEGER,
                                                PRIMARY KEY (subtaskID, employeeID),
                                                FOREIGN KEY (subtaskID)
                                                    REFERENCES subtask(subtaskID)
                                                    ON DELETE CASCADE,
                                                FOREIGN KEY (employeeID)
                                                    REFERENCES employee(employeeID)
                                                    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS skillRelation (
                                             skillID    INTEGER,
                                             employeeID INTEGER,
                                             PRIMARY KEY (skillID, employeeID),
                                             FOREIGN KEY (skillID)
                                                 REFERENCES skill(skillID)
                                                 ON DELETE CASCADE,
                                             FOREIGN KEY (employeeID)
                                                 REFERENCES employee(employeeID)
                                                 ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS awaitingEmployee (
                                                awaitingEmployeeID   INTEGER PRIMARY KEY AUTO_INCREMENT,
                                                awaitingEmployee_name     VARCHAR(100) NOT NULL,
                                                awaitingEmployee_email    VARCHAR(100) NOT NULL,
                                                awaitingEmployee_username VARCHAR(100) NOT NULL,
                                                awaitingEmployee_password VARCHAR(256) NOT NULL,
                                                awaitingEmployee_status   VARCHAR(100) NOT NULL
);

INSERT INTO roles (role_Name)
VALUES
    ('Default'),
    ('Project Manager'),
    ('Admin');

-- Skills
INSERT INTO skill (skill_name)
VALUES
    ('Frontend'), ('Backend'), ('Java'), ('Python'), ('SQL'),
    ('JavaScript'), ('React'), ('Angular'), ('UI/UX Design'),
    ('Testing'), ('DevOps'), ('Cloud Architecture'),
    ('Project Management'), ('Agile Methodologies'),
    ('Database Design'), ('Network Security'),
    ('Business Analysis');

-- Employees
INSERT INTO employee (
    employee_Name, employee_email, employee_username, employee_password, roleID
) VALUES
      ('Anders Jensen',    'anders.jensen@alphasolutions.dk',   'ajen', 'Password123!', 3),
      ('Marie Nielsen',    'marie.nielsen@alphasolutions.dk',   'mnie', 'Password123!', 2),
      ('Peter Hansen',     'peter.hansen@alphasolutions.dk',    'phan', 'Password123!', 1),
      ('Louise Pedersen',  'louise.pedersen@alphasolutions.dk', 'lped', 'Password123!', 1),
      ('Mikkel Andersen',  'mikkel.andersen@alphasolutions.dk', 'mand', 'Password123!', 1),
      ('Sofie Christensen','sofie.christensen@alphasolutions.dk','schr', 'Password123!', 1),
      ('Jacob Larsen',     'jacob.larsen@alphasolutions.dk',    'jlar', 'Password123!', 2),
      ('Emma Schmidt',     'emma.schmidt@alphasolutions.dk',    'esch', 'Password123!', 2),
      ('Frederik Møller',  'frederik.moller@alphasolutions.dk', 'fmol', 'Password123!', 2),
      ('Ida Olsen',        'ida.olsen@alphasolutions.dk',       'iols', 'Password123!', 1),
      ('Christian Berg',   'christian.berg@alphasolutions.dk',  'cber', 'Password123!', 1),
      ('Sarah Poulsen',    'sarah.poulsen@alphasolutions.dk',   'spou', 'Password123!', 1),
      ('Thomas Kjær',      'thomas.kjaer@alphasolutions.dk',    'tkja', 'Password123!', 1),
      ('Anne Bech',        'anne.bech@alphasolutions.dk',       'abec', 'Password123!', 1),
      ('Lars Winther',     'lars.winther@alphasolutions.dk',    'lwin', 'Password123!', 1);

-- Employee Skills
INSERT INTO skillRelation (skillID, employeeID)
VALUES
    (1, 2),  (1, 3),  (1, 11), (1, 13),
    (2, 3),  (2, 11),
    (3, 2),  (3, 3),  (3, 10), (3, 13),
    (4, 4),  (4, 8),  (4, 14),
    (5, 4),  (5, 8),  (5, 14),
    (6, 4),  (6, 8),
    (7, 4),  (7, 14),
    (8, 5),  (8, 12),
    (9, 9),
    (10, 9),
    (11, 1), (11, 15),
    (12, 7), (12, 1), (12, 15),
    (13, 6), (13, 2),
    (14, 9),
    (15, 6);

-- Projects
INSERT INTO project (
    project_Name, project_status, project_start_date, project_end_date, project_description, employeeID
) VALUES
      ('Customer Portal Redesign', 'In Progress', '2025-01-15', '2025-06-30',
       'Revamping the existing customer portal to enhance user experience, accessibility, and performance.', 1),
      ('Data Warehouse Migration', 'Planning',    '2025-03-01', '2025-08-15',
       'Migrating legacy data systems into a centralized, scalable data warehouse using cloud technologies.', 15),
      ('Mobile App Development',   'In Progress', '2024-11-01', '2025-05-20',
       'Developing a cross-platform mobile app to expand user engagement and improve mobile access.', 7),
      ('Cloud Infrastructure Upgrade', 'Completed','2024-09-10','2025-01-31',
       'Upgraded existing infrastructure to a more scalable and secure cloud environment.', 9),
      ('Security Compliance Project', 'On Hold',  '2025-04-01','2025-09-30',
       'Ensuring the organization meets new regulatory standards for data privacy and cybersecurity.', 1),
      ('E-commerce Platform',      'Planning',    '2025-06-01','2025-12-15',
       'Building a modern e-commerce platform with enhanced checkout flow, inventory integration, and analytics.', 15),
      ('Internal HR System',       'Not Started','2025-07-15','2025-11-30',
       'Designing an internal HR management system to streamline onboarding, payroll, and performance reviews.', 1);

-- Project Assignees
INSERT INTO projectAssignees (projectID, employeeID)
VALUES
    (1, 1), (1, 4), (1, 5), (1, 8), (1, 12), (1, 14),
    (2, 15), (2, 2), (2, 6), (2, 10), (2, 13),
    (3, 7), (3, 2), (3, 3), (3, 4), (3, 8), (3, 11), (3, 14),
    (4, 9), (4, 10), (4, 3), (4, 11),
    (5, 1), (5, 9), (5, 13),
    (6, 15), (6, 4), (6, 8), (6, 11), (6, 14),
    (7, 1), (7, 6), (7, 7), (7, 10);

-- Subprojects
INSERT INTO subproject (
    subproject_Name, subproject_start_date, subproject_end_date, projectID
) VALUES
      ('User Interface Design',      '2025-01-15','2025-03-15', 1),
      ('Backend API Development',    '2025-02-01','2025-04-30', 1),
      ('T<esting and >QA',           '2025-04-15','2025-06-15', 1),
      ('Legacy System Analysis',     '2025-03-01','2025-04-15', 2),
      ('Migration Planning',         '2025-04-01','2025-05-15', 2),
      ('Implementation Phase',       '2025-05-15','2025-07-30', 2),
      ('Data Conversion',            '2025-07-01','2025-08-15', 2),
      ('iOS Development',            '2024-11-01','2025-03-15', 3),
      ('Android Development',        '2024-11-15','2025-03-30', 3),
      ('Backend Services',           '2024-12-01','2025-04-15', 3),
      ('Server Migration',           '2024-09-10','2024-11-15', 4),
      ('Network Reconfiguration',    '2024-10-20','2025-01-15', 4),
      ('Security Audit',             '2025-04-01','2025-05-30', 5),
      ('Compliance Documentation',   '2025-05-01','2025-08-15', 5),
      ('Product Catalog Design',     '2025-06-01','2025-07-30', 6),
      ('Payment Processing System',  '2025-07-01','2025-09-15', 6),
      ('User Authentication System', '2025-08-01','2025-10-30', 6),
      ('Employee Portal',            '2025-07-15','2025-09-30', 7),
      ('Leave Management System',    '2025-08-15','2025-10-30', 7),
      ('Performance Review Module',  '2025-09-15','2025-11-30', 7);

-- Tasks
INSERT INTO task (
    task_Name, subProjectId, task_start_date, task_end_date, task_priority, task_description
) VALUES
      ('Design User Dashboard',        1, '2025-01-15','2025-02-05','High',
       'Create user dashboard layouts and wireframes'),
      ('Design Customer Profile Page', 1, '2025-01-25','2025-02-15','Medium',
       'Design profile pages with account information'),
      ('Implement Design System',      1, '2025-02-01','2025-03-10','High',
       'Create reusable components and style guidelines'),
      ('Design Mobile Responsive Views',1,'2025-02-10','2025-03-15','Medium',
       'Ensure all designs work well on mobile devices'),
      ('Develop Authentication API',    2,'2025-02-01','2025-03-01','High',
       'Create secure authentication endpoints'),
      ('Create Data Access Layer',      2,'2025-02-15','2025-03-30','Medium',
       'Develop database access and ORM implementation'),
      ('API Documentation',             2,'2025-03-15','2025-04-15','Low',
       'Document API endpoints for front-end developers'),
      ('Implement REST Endpoints',      2,'2025-03-01','2025-04-30','High',
       'Create RESTful API endpoints for core functionality'),
      ('Unit Testing Framework',        3,'2025-04-15','2025-05-01','Medium',
       'Set up testing environment and frameworks'),
      ('Integration Testing',           3,'2025-05-01','2025-06-01','High',
       'Test API and frontend integration'),
      ('Usability Testing',             3,'2025-05-15','2025-06-15','Medium',
       'Conduct usability tests with representative users'),
      ('Database Schema Analysis',      4,'2025-03-01','2025-03-20','High',
       'Analyze current database schemas'),
      ('Legacy Code Review',            4,'2025-03-15','2025-04-10','Medium',
       'Review legacy codebase for dependencies'),
      ('Data Migration Strategy',       5,'2025-04-01','2025-04-30','High',
       'Develop comprehensive migration strategy'),
      ('Dependency Mapping',            5,'2025-04-15','2025-05-15','Medium',
       'Map all system dependencies for migration planning'),
      ('iOS App UI Development',       8,'2024-11-01','2024-12-15','High',
       'Develop user interface for iOS application'),
      ('iOS Core Functionality',       8,'2024-12-01','2025-02-15','High',
       'Implement core app functionality for iOS'),
      ('Android UI Development',       9,'2024-11-15','2024-12-30','High',
       'Develop user interface for Android application'),
      ('Android Core Functionality',   9,'2025-01-05','2025-03-15','High',
       'Implement core app functionality for Android'),
      ('Cloud Server Configuration',  11,'2024-09-10','2024-10-20','High',
       'Configure new cloud server infrastructure');

-- Task Assignees
INSERT INTO taskAssignees (taskID, employeeID)
VALUES
    (1, 4), (1, 14),
    (2, 4), (2, 8), (2, 14),
    (3, 4), (3, 8), (3, 14),
    (4, 4), (4, 14),
    (5, 2), (5, 3), (5, 13),
    (6, 2), (6, 11),
    (7, 2), (7, 13),
    (8, 3), (8, 11), (8, 13),
    (9, 5), (9, 12),
    (10, 5), (10, 12),
    (11, 5), (11, 12),
    (12, 6), (12, 10),
    (13, 6), (13, 10),
    (14, 15), (14, 6),
    (15, 15), (15, 6),
    (16, 3), (16, 11),
    (17, 3), (17, 11),
    (18, 2), (18, 11),
    (19, 2), (19, 11),
    (20, 9), (20, 10);

-- Subtasks
INSERT INTO subtask (
    subtask_Name, taskID, subtask_estimate, subtask_start_date, subtask_end_date,
    subtask_priority, subtask_description, subtask_status, subtask_hours_spent
) VALUES
      ('Create Dashboard Wireframes',      1, 15, '2025-01-15','2025-01-25','High',
       'Create initial wireframes for dashboard','Completed', 16),
      ('Design Data Visualization Components',1,25,'2025-01-20','2025-02-05','Medium',
       'Design charts and data display elements','Completed',27),
      ('Design Mobile Profile Layout',      2,15,'2025-01-25','2025-02-05','Medium',
       'Design mobile version of profile page','Completed',14),
      ('Design Desktop Profile Layout',     2,15,'2025-01-25','2025-02-10','Medium',
       'Design desktop version of profile page','Completed',15),
      ('Define Color Palette',              3,10,'2025-02-01','2025-02-10','Medium',
       'Finalize color schemes for the application','Completed',9),
      ('Create Component Library',          3,25,'2025-02-10','2025-03-01','High',
       'Build reusable UI components','In Progress',12),
      ('Document Style Guidelines',         3,15,'2025-02-15','2025-03-10','Low',
       'Create documentation for design system','Not Started',0),
      ('Design Tablet Views',               4,15,'2025-02-10','2025-02-25','Medium',
       'Create designs for tablet screen sizes','In Progress',8),
      ('Design Mobile Phone Views',         4,20,'2025-02-15','2025-03-15','High',
       'Create designs for mobile phone screen sizes','Not Started',0),
      ('Authentication Server Setup',       5,20,'2025-02-01','2025-02-15','High',
       'Configure auth server and security','Completed',21),
      ('Implement OAuth Flow',              5,25,'2025-02-15','2025-02-28','High',
       'Implement OAuth authentication flow','In Progress',10),
      ('Create User Management API',        5,15,'2025-02-20','2025-03-01','Medium',
       'Create API endpoints for user management','Not Started',0),
      ('Design Database Schema',            6,20,'2025-02-15','2025-03-01','High',
       'Design efficient database schema','Not Started',0),
      ('Implement Model Classes',           6,30,'2025-02-25','2025-03-15','Medium',
       'Create model classes for data access','Not Started',0),
      ('Create Repository Layer',           6,30,'2025-03-01','2025-03-30','Medium',
       'Implement repository pattern for data access','Not Started',0),
      ('Environment Setup',                 9,15,'2025-04-15','2025-04-22','High',
       'Set up testing environments','Not Started',0),
      ('Write Test Cases',                  9,25,'2025-04-22','2025-05-01','Medium',
       'Develop comprehensive test cases','Not Started',0),
      ('iOS Navigation Implementation',     16,20,'2024-11-01','2024-11-20','High',
       'Implement app navigation structure','Completed',22),
      ('iOS Core Screen Design',            16,25,'2024-11-10','2024-12-05','Medium',
       'Design main app screens','Completed',24),
      ('iOS UI Polish',                     16,25,'2024-11-25','2024-12-15','Low',
       'Fine-tune visual details and animations','Completed',23);

-- Subtask Assignees
INSERT INTO subtaskAssignees (subtaskID, employeeID)
VALUES
    (1, 4),  (1, 14),
    (2, 4),  (2, 14),
    (3, 8),  (3, 14),
    (4, 4),  (4, 14),
    (5, 4),  (5, 14),
    (6, 8),  (6, 14),
    (7, 4),  (7, 8),
    (8, 4),  (8, 14),
    (9, 4),  (9, 14),
    (10, 3), (10, 13),
    (11, 2), (11, 13),
    (12, 3), (12, 11),
    (13, 2), (13, 11),
    (14, 2), (14, 11),
    (15, 2), (15, 11),
    (16, 5), (16, 12),
    (17, 5), (17, 12),
    (18, 3), (18, 11),
    (19, 3), (19, 11),
    (20, 3), (20, 11);







