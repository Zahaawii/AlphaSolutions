USE
alphasolutions;

-- Roles
INSERT INTO roles (role_Name)
VALUES ('Default'),
       ('Project Manager'),
       ('Admin');


-- Skills
INSERT INTO skill (skill_name)
VALUES
    ('Frontend'), ('Backend'), ('Java'), ('Python'), ('SQL'), ('JavaScript'), ('React'), ('Angular'), ('UI/UX Design'), ('Testing'), ('DevOps'), ('Cloud Architecture'), ('Project Management'), ('Agile Methodologies'), ('Database Design'), ('Network Security'), ('Business Analysis');

-- Employees med brugernavn og password (password er plaintext for test formål)
INSERT INTO employee (employee_Name, employee_email, employee_username, employee_password, roleID)
VALUES ('Anders Jensen', 'anders.jensen@alphasolutions.dk', 'ajen', '$2a$10$kem/osM1Q28t4Ruprxa7QOrl4N/jk8JS6tzEV6WZaYO68LDhs7Q3i', 3),
       ('Marie Nielsen', 'marie.nielsen@alphasolutions.dk', 'mnie', '$2a$10$msZIhgxQzBAZg9XpuOsldeTDyoHfYmHtc5QRGRDhtx9ggB.6Bm3km', 2),
       ('Peter Hansen', 'peter.hansen@alphasolutions.dk', 'phan', '$2a$10$FKQNyZpliZioU7eytpo/AuSj03rXpv61OcNIpjTQlbywJdF7v8ma6', 1),
       ('Louise Pedersen', 'louise.pedersen@alphasolutions.dk', 'lped', '$2a$10$wswo/7zSvyKpXoHg2BgFGOsSBffu8UyoJS1ODUWe/yrOeRsnnwhIe', 1),
       ('Mikkel Andersen', 'mikkel.andersen@alphasolutions.dk', 'mand', '$2a$10$tQrUeoURpXdAToeCuUcCU.VD4CIe/MLj7kEf2qLDD9ZRUCZ34yz1C', 1),
       ('Sofie Christensen', 'sofie.christensen@alphasolutions.dk', 'schr', '$2a$10$iISnakGRuO11G/1QWgS0yug0Nb3BylIgKKiUmlpBI9Y2VdxMrVLUm', 1),
       ('Jacob Larsen', 'jacob.larsen@alphasolutions.dk', 'jlar', '$2a$10$uPwv0Ro.N74gRhH.7IKgPuMB0tHlEIUI7Bg0AT7Ekm2efONGcR4we', 2),
       ('Emma Schmidt', 'emma.schmidt@alphasolutions.dk', 'esch', '$2a$10$V6rCgeg7JzKjqKjc2XPQv.12YNYQlSSyZGbKO6hw85CNIb7dVdft6', 2),
       ('Frederik Møller', 'frederik.moller@alphasolutions.dk', 'fmol', '$2a$10$ZLchF0M29j3Mcep8kr8jye97fr0.93y6GPMThaY.efzglGOlntEry', 2),
       ('Ida Olsen', 'ida.olsen@alphasolutions.dk', 'iols', '$2a$10$amvF4kaNuKut3cJ0A4MC9uMa/HZrvh7RA5I1RhnNaHoCIFA/EU5vu', 1),
       ('Christian Berg', 'christian.berg@alphasolutions.dk', 'cber', '$2a$10$TXGzNr/NEbcl0.rMeR..MeP.JhsmvEn7/B83VNcoReQypO3WSxWbm', 1),
       ('Sarah Poulsen', 'sarah.poulsen@alphasolutions.dk', 'spou', '$2a$10$2C1ZawkSPQ6n/HMm0EUOfe/5.qLcpN1VZJOe1kZNETn2T9dtwJMRi', 1),
       ('Thomas Kjær', 'thomas.kjaer@alphasolutions.dk', 'tkja', '$2a$10$8bMiHzhQAjEv8c9EjR5HQeElUBrCrohXw0R3acZzxEB/4GAYDtT7G', 1),
       ('Anne Bech', 'anne.bech@alphasolutions.dk', 'abec', '$2a$10$nd4c8iucF9rYTJ9kfAOPy.f7MIa3ql8r06cs1Woe5O37/ZOypa1OW', 1),
       ('Lars Winther', 'lars.winther@alphasolutions.dk', 'lwin', '$2a$10$JKM0eSeB54w9VQrt9NZvNuglsI1M/ZyK8dDYr89I8d5/8m2KnogyC', 1);

-- Employee Skills
INSERT INTO skillRelation (skillID, employeeID)
VALUES (1, 2),
       (1, 3),
       (1, 11),
       (1, 13),
       (2, 3),
       (2, 11),
       (3, 2),
       (3, 3),
       (3, 10),
       (3, 13),
       (4, 4),
       (4, 8),
       (4, 14),
       (5, 4),
       (5, 8),
       (5, 14),
       (6, 4),
       (6, 8),
       (7, 4),
       (7, 14),
       (8, 5),
       (8, 12),
       (9, 9),
       (10, 9),
       (11, 1),
       (11, 15),
       (12, 7),
       (12, 1),
       (12, 15),
       (13, 6),
       (13, 2),
       (14, 9),
       (15, 6);

-- Projects
INSERT INTO project (project_Name, project_status, project_start_date, project_end_date, project_description,
                     employeeID)
VALUES ('Customer Portal Redesign', 'In Progress', '2025-01-15', '2025-06-30',
        'Revamping the existing customer portal to enhance user experience, accessibility, and performance.', 1),
       ('Data Warehouse Migration', 'Planning', '2025-03-01', '2025-08-15',
        'Migrating legacy data systems into a centralized, scalable data warehouse using cloud technologies.', 15),
       ('Mobile App Development', 'In Progress', '2024-11-01', '2025-05-20',
        'Developing a cross-platform mobile app to expand user engagement and improve mobile access.', 7),
       ('Cloud Infrastructure Upgrade', 'Completed', '2024-09-10', '2025-01-31',
        'Upgraded existing infrastructure to a more scalable and secure cloud environment.', 9),
       ('Security Compliance Project', 'On Hold', '2025-04-01', '2025-09-30',
        'Ensuring the organization meets new regulatory standards for data privacy and cybersecurity.', 1),
       ('E-commerce Platform', 'Planning', '2025-06-01', '2025-12-15',
        'Building a modern e-commerce platform with enhanced checkout flow, inventory integration, and analytics.', 15),
       ('Internal HR System', 'Not Started', '2025-07-15', '2025-11-30',
        'Designing an internal HR management system to streamline onboarding, payroll, and performance reviews.', 1);


-- Project Assignees
INSERT INTO projectAssignees (projectID, employeeID)
VALUES (1, 1),  -- Anders på Customer Portal
       (1, 4),  -- Louise på Customer Portal
       (1, 5),  -- Mikkel på Customer Portal
       (1, 8),  -- Emma på Customer Portal
       (1, 12), -- Sarah på Customer Portal
       (1, 14), -- Anne på Customer Portal
       (2, 15), -- Lars på Data Warehouse
       (2, 2),  -- Marie på Data Warehouse
       (2, 6),  -- Sofie på Data Warehouse
       (2, 10), -- Ida på Data Warehouse
       (2, 13), -- Thomas på Data Warehouse
       (3, 7),  -- Jacob på Mobile App
       (3, 2),  -- Marie på Mobile App
       (3, 3),  -- Peter på Mobile App
       (3, 4),  -- Louise på Mobile App
       (3, 8),  -- Emma på Mobile App
       (3, 11), -- Christian på Mobile App
       (3, 14), -- Anne på Mobile App
       (4, 9),  -- Frederik på Cloud Infrastructure
       (4, 10), -- Ida på Cloud Infrastructure
       (4, 3),  -- Peter på Cloud Infrastructure
       (4, 11), -- Christian på Cloud Infrastructure
       (5, 1),  -- Anders på Security Compliance
       (5, 9),  -- Frederik på Security Compliance
       (5, 13), -- Thomas på Security Compliance
       (6, 15), -- Lars på E-commerce Platform
       (6, 4),  -- Louise på E-commerce Platform
       (6, 8),  -- Emma på E-commerce Platform
       (6, 11), -- Christian på E-commerce Platform
       (6, 14), -- Anne på E-commerce Platform
       (7, 1),  -- Anders på Internal HR System
       (7, 6),  -- Sofie på Internal HR System
       (7, 7),  -- Jacob på Internal HR System
       (7, 10);
-- Ida på Internal HR System

-- Subprojects
INSERT INTO subproject (subproject_Name, subproject_start_date, subproject_end_date, projectID)
VALUES ('User Interface Design', '2025-01-15', '2025-03-15', 1),
       ('Backend API Development', '2025-02-01', '2025-04-30', 1),
       ('T<esting and >QA', '2025-04-15', '2025-06-15', 1),
       ('Legacy System Analysis', '2025-03-01', '2025-04-15', 2),
       ('Migration Planning', '2025-04-01', '2025-05-15', 2),
       ('Implementation Phase', '2025-05-15', '2025-07-30', 2),
       ('Data Conversion', '2025-07-01', '2025-08-15', 2),
       ('iOS Development', '2024-11-01', '2025-03-15', 3),
       ('Android Development', '2024-11-15', '2025-03-30', 3),
       ('Backend Services', '2024-12-01', '2025-04-15', 3),
       ('Server Migration', '2024-09-10', '2024-11-15', 4),
       ('Network Reconfiguration', '2024-10-20', '2025-01-15', 4),
       ('Security Audit', '2025-04-01', '2025-05-30', 5),
       ('Compliance Documentation', '2025-05-01', '2025-08-15', 5),
       ('Product Catalog Design', '2025-06-01', '2025-07-30', 6),
       ('Payment Processing System', '2025-07-01', '2025-09-15', 6),
       ('User Authentication System', '2025-08-01', '2025-10-30', 6),
       ('Employee Portal', '2025-07-15', '2025-09-30', 7),
       ('Leave Management System', '2025-08-15', '2025-10-30', 7),
       ('Performance Review Module', '2025-09-15', '2025-11-30', 7);


-- Tasks
INSERT INTO task (task_Name, subProjectId, task_start_date, task_end_date, task_priority, task_description)
VALUES
    ('Design User Dashboard', 1, '2025-01-15', '2025-02-05', 'High',
     'Create user dashboard layouts and wireframes'),
    ('Design Customer Profile Page', 1, '2025-01-25', '2025-02-15', 'Medium',
     'Design profile pages with account information'),
    ('Implement Design System', 1, '2025-02-01', '2025-03-10', 'High',
     'Create reusable components and style guidelines'),
    ('Design Mobile Responsive Views', 1, '2025-02-10', '2025-03-15', 'Medium',
     'Ensure all designs work well on mobile devices'),
    ('Develop Authentication API', 2, '2025-02-01', '2025-03-01', 'High',
     'Create secure authentication endpoints'),
    ('Create Data Access Layer', 2, '2025-02-15', '2025-03-30', 'Medium',
     'Develop database access and ORM implementation'),
    ('API Documentation', 2, '2025-03-15', '2025-04-15', 'Low',
     'Document API endpoints for front-end developers'),
    ('Implement REST Endpoints', 2, '2025-03-01', '2025-04-30', 'High',
     'Create RESTful API endpoints for core functionality'),
    ('Unit Testing Framework', 3, '2025-04-15', '2025-05-01', 'Medium',
     'Set up testing environment and frameworks'),
    ('Integration Testing', 3, '2025-05-01', '2025-06-01', 'High',
     'Test API and frontend integration'),
    ('Usability Testing', 3, '2025-05-15', '2025-06-15', 'Medium',
     'Conduct usability tests with representative users'),
    ('Database Schema Analysis', 4, '2025-03-01', '2025-03-20', 'High',
     'Analyze current database schemas'),
    ('Legacy Code Review', 4, '2025-03-15', '2025-04-10', 'Medium',
     'Review legacy codebase for dependencies'),
    ('Data Migration Strategy', 5, '2025-04-01', '2025-04-30', 'High',
     'Develop comprehensive migration strategy'),
    ('Dependency Mapping', 5, '2025-04-15', '2025-05-15', 'Medium',
     'Map all system dependencies for migration planning'),
    ('iOS App UI Development', 8, '2024-11-01', '2024-12-15', 'High',
     'Develop user interface for iOS application'),
    ('iOS Core Functionality', 8, '2024-12-01', '2025-02-15', 'High',
     'Implement core app functionality for iOS'),
    ('Android UI Development', 9, '2024-11-15', '2024-12-30', 'High',
     'Develop user interface for Android application'),
    ('Android Core Functionality', 9, '2025-01-05', '2025-03-15', 'High',
     'Implement core app functionality for Android'),
    ('Cloud Server Configuration', 11, '2024-09-10', '2024-10-20', 'High',
     'Configure new cloud server infrastructure');

-- Task Assignees
INSERT INTO taskAssignees (taskID, employeeID)
VALUES (1, 4),   -- Louise på Design User Dashboard
       (1, 14),  -- Anne på Design User Dashboard
       (2, 4),   -- Louise på Design Customer Profile
       (2, 8),   -- Emma på Design Customer Profile
       (2, 14),  -- Anne på Design Customer Profile
       (3, 4),   -- Louise på Implement Design System
       (3, 8),   -- Emma på Implement Design System
       (3, 14),  -- Anne på Implement Design System
       (4, 4),   -- Louise på Design Mobile Responsive Views
       (4, 14),  -- Anne på Design Mobile Responsive Views
       (5, 2),   -- Marie på Authentication API
       (5, 3),   -- Peter på Authentication API
       (5, 13),  -- Thomas på Authentication API
       (6, 2),   -- Marie på Data Access Layer
       (6, 11),  -- Christian på Data Access Layer
       (7, 2),   -- Marie på API Documentation
       (7, 13),  -- Thomas på API Documentation
       (8, 3),   -- Peter på Implement REST Endpoints
       (8, 11),  -- Christian på Implement REST Endpoints
       (8, 13),  -- Thomas på Implement REST Endpoints
       (9, 5),   -- Mikkel på Unit Testing Framework
       (9, 12),  -- Sarah på Unit Testing Framework
       (10, 5),  -- Mikkel på Integration Testing
       (10, 12), -- Sarah på Integration Testing
       (11, 5),  -- Mikkel på Usability Testing
       (11, 12), -- Sarah på Usability Testing
       (12, 6),  -- Sofie på Database Schema Analysis
       (12, 10), -- Ida på Database Schema Analysis
       (13, 6),  -- Sofie på Legacy Code Review
       (13, 10), -- Ida på Legacy Code Review
       (14, 15), -- Lars på Data Migration Strategy
       (14, 6),  -- Sofie på Data Migration Strategy
       (15, 15), -- Lars på Dependency Mapping
       (15, 6),  -- Sofie på Dependency Mapping
       (16, 3),  -- Peter på iOS App UI Development
       (16, 11), -- Christian på iOS App UI Development
       (17, 3),  -- Peter på iOS Core Functionality
       (17, 11), -- Christian på iOS Core Functionality
       (18, 2),  -- Marie på Android UI Development
       (18, 11), -- Christian på Android UI Development
       (19, 2),  -- Marie på Android Core Functionality
       (19, 11), -- Christian på Android Core Functionality
       (20, 9),  -- Frederik på Cloud Server Configuration
       (20, 10);
-- Ida på Cloud Server Configuration

-- Subtasks
INSERT INTO subtask (subtask_Name, taskID, subtask_estimate, subtask_start_date, subtask_end_date, subtask_priority,
                     subtask_description, subtask_status, subtask_hours_spent)
VALUES
    ('Create Dashboard Wireframes', 1, 15, '2025-01-15', '2025-01-25', 'High',
     'Create initial wireframes for dashboard', 'Completed', 16),
    ('Design Data Visualization Components', 1, 25, '2025-01-20', '2025-02-05', 'Medium',
     'Design charts and data display elements', 'Completed', 27),
    ('Design Mobile Profile Layout', 2, 15, '2025-01-25', '2025-02-05', 'Medium',
     'Design mobile version of profile page', 'Completed', 14),
    ('Design Desktop Profile Layout', 2, 15, '2025-01-25', '2025-02-10', 'Medium',
     'Design desktop version of profile page', 'Completed', 15),
    ('Define Color Palette', 3, 10, '2025-02-01', '2025-02-10', 'Medium',
     'Finalize color schemes for the application', 'Completed', 9),
    ('Create Component Library', 3, 25, '2025-02-10', '2025-03-01', 'High',
     'Build reusable UI components', 'In Progress', 12),
    ('Document Style Guidelines', 3, 15, '2025-02-15', '2025-03-10', 'Low',
     'Create documentation for design system', 'Not Started', 0),
    ('Design Tablet Views', 4, 15, '2025-02-10', '2025-02-25', 'Medium',
     'Create designs for tablet screen sizes', 'In Progress', 8),
    ('Design Mobile Phone Views', 4, 20, '2025-02-15', '2025-03-15', 'High',
     'Create designs for mobile phone screen sizes', 'Not Started', 0),
    ('Authentication Server Setup', 5, 20, '2025-02-01', '2025-02-15', 'High',
     'Configure auth server and security', 'Completed', 21),
    ('Implement OAuth Flow', 5, 25, '2025-02-15', '2025-02-28', 'High',
     'Implement OAuth authentication flow', 'In Progress', 10),
    ('Create User Management API', 5, 15, '2025-02-20', '2025-03-01', 'Medium',
     'Create API endpoints for user management', 'Not Started', 0),
    ('Design Database Schema', 6, 20, '2025-02-15', '2025-03-01', 'High',
     'Design efficient database schema', 'Not Started', 0),
    ('Implement Model Classes', 6, 30, '2025-02-25', '2025-03-15', 'Medium',
     'Create model classes for data access', 'Not Started', 0),
    ('Create Repository Layer', 6, 30, '2025-03-01', '2025-03-30', 'Medium',
     'Implement repository pattern for data access', 'Not Started', 0),
    ('Environment Setup', 9, 15, '2025-04-15', '2025-04-22', 'High',
     'Set up testing environments', 'Not Started', 0),
    ('Write Test Cases', 9, 25, '2025-04-22', '2025-05-01', 'Medium',
     'Develop comprehensive test cases', 'Not Started', 0),
    ('iOS Navigation Implementation', 16, 20, '2024-11-01', '2024-11-20', 'High',
     'Implement app navigation structure', 'Completed', 22),
    ('iOS Core Screen Design', 16, 25, '2024-11-10', '2024-12-05', 'Medium',
     'Design main app screens', 'Completed', 24),
    ('iOS UI Polish', 16, 25, '2024-11-25', '2024-12-15', 'Low',
     'Fine-tune visual details and animations', 'Completed', 23);


-- Subtask Assignees
INSERT INTO subtaskAssignees (subtaskID, employeeID)
VALUES (1, 4),   -- Louise på Dashboard Wireframes
       (1, 14),  -- Anne på Dashboard Wireframes
       (2, 4),   -- Louise på Data Visualization Components
       (2, 14),  -- Anne på Data Visualization Components
       (3, 8),   -- Emma på Mobile Profile Layout
       (3, 14),  -- Anne på Mobile Profile Layout
       (4, 4),   -- Louise på Desktop Profile Layout
       (4, 14),  -- Anne på Desktop Profile Layout
       (5, 4),   -- Louise på Color Palette
       (5, 14),  -- Anne på Color Palette
       (6, 8),   -- Emma på Component Library
       (6, 14),  -- Anne på Component Library
       (7, 4),   -- Louise på Style Guidelines
       (7, 8),   -- Emma på Style Guidelines
       (8, 4),   -- Louise på Design Tablet Views
       (8, 14),  -- Anne på Design Tablet Views
       (9, 4),   -- Louise på Design Mobile Phone Views
       (9, 14),  -- Anne på Design Mobile Phone Views
       (10, 3),  -- Peter på Auth Server Setup
       (10, 13), -- Thomas på Auth Server Setup
       (11, 2),  -- Marie på OAuth Flow
       (11, 13), -- Thomas på OAuth Flow
       (12, 3),  -- Peter på User Management API
       (12, 11), -- Christian på User Management API
       (13, 2),  -- Marie på Design Database Schema
       (13, 11), -- Christian på Design Database Schema
       (14, 2),  -- Marie på Implement Model Classes
       (14, 11), -- Christian på Implement Model Classes
       (15, 2),  -- Marie på Create Repository Layer
       (15, 11), -- Christian på Create Repository Layer
       (16, 5),  -- Mikkel på Environment Setup
       (16, 12), -- Sarah på Environment Setup
       (17, 5),  -- Mikkel på Write Test Cases
       (17, 12), -- Sarah på Write Test Cases
       (18, 3),  -- Peter på iOS Navigation Implementation
       (18, 11), -- Christian på iOS Navigation Implementation
       (19, 3),  -- Peter på iOS Core Screen Design
       (19, 11), -- Christian på iOS Core Screen Design
       (20, 3),  -- Peter på iOS UI Polish
       (20, 11); -- Christian på iOS UI Polish