-- Database schema for lab3 application
-- SQL Server script compatible with the current db.properties configuration

USE master;
GO

IF NOT EXISTS (SELECT 1 FROM sys.server_principals WHERE name = 'fox')
BEGIN
    CREATE LOGIN fox WITH PASSWORD = '1234', CHECK_POLICY = OFF;
END
ELSE
BEGIN
    ALTER LOGIN fox WITH PASSWORD = '1234', CHECK_POLICY = OFF;
END
GO

IF DB_ID('company_db') IS NULL
BEGIN
    CREATE DATABASE company_db;
END
GO

USE company_db;
GO

IF NOT EXISTS (SELECT 1 FROM sys.database_principals WHERE name = 'fox')
BEGIN
    CREATE USER fox FOR LOGIN fox;
END
GO

IF NOT EXISTS (
    SELECT 1 FROM sys.database_role_members drm
    JOIN sys.database_principals r ON drm.role_principal_id = r.principal_id
    JOIN sys.database_principals m ON drm.member_principal_id = m.principal_id
    WHERE r.name = 'db_datareader' AND m.name = 'fox')
BEGIN
    ALTER ROLE db_datareader ADD MEMBER fox;
END
GO

IF NOT EXISTS (
    SELECT 1 FROM sys.database_role_members drm
    JOIN sys.database_principals r ON drm.role_principal_id = r.principal_id
    JOIN sys.database_principals m ON drm.member_principal_id = m.principal_id
    WHERE r.name = 'db_datawriter' AND m.name = 'fox')
BEGIN
    ALTER ROLE db_datawriter ADD MEMBER fox;
END
GO

-- Drop tables in dependency-safe order
IF OBJECT_ID('dbo.employees', 'U') IS NOT NULL
    DROP TABLE dbo.employees;
GO

IF OBJECT_ID('dbo.departments', 'U') IS NOT NULL
    DROP TABLE dbo.departments;
GO

IF OBJECT_ID('dbo.users', 'U') IS NOT NULL
    DROP TABLE dbo.users;
GO

-- Table for application users
CREATE TABLE dbo.users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) NOT NULL UNIQUE,
    password NVARCHAR(100) NOT NULL,
    full_name NVARCHAR(100) NULL,
    role NVARCHAR(50) NULL,
    created_at DATETIME2 DEFAULT SYSDATETIME()
);
GO

-- Table for departments used in console statistics
CREATE TABLE dbo.departments (
    id INT IDENTITY(1,1) PRIMARY KEY,
    dept_name NVARCHAR(100) NOT NULL UNIQUE,
    description NVARCHAR(255) NULL
);
GO

-- Table for employee records
CREATE TABLE dbo.employees (
    emp_code NVARCHAR(20) PRIMARY KEY,
    full_name NVARCHAR(100) NOT NULL,
    email NVARCHAR(150) NULL,
    phone NVARCHAR(20) NULL,
    gender NVARCHAR(10) NULL,
    birth_date DATE NULL,
    department NVARCHAR(100) NULL,
    position NVARCHAR(100) NULL,
    salary DECIMAL(18,2) NULL,
    CONSTRAINT FK_Employees_Department FOREIGN KEY (department)
        REFERENCES dbo.departments(dept_name)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);
GO

-- Sample seed data
INSERT INTO dbo.users (username, password, full_name, role)
VALUES
    ('admin', 'admin123', 'Administrator', 'admin'),
    ('fox', '1234', 'Fox User', 'user');
GO

INSERT INTO dbo.departments (dept_name, description)
VALUES
    ('Sales', 'Sales department'),
    ('HR', 'Human resources'),
    ('IT', 'Information technology');
GO

INSERT INTO dbo.employees (emp_code, full_name, email, phone, gender, birth_date, department, position, salary)
VALUES
    ('E001', 'Nguyen Van A', 'a@example.com', '0123456789', 'Male', '1990-01-15', 'Sales', 'Sales Executive', 12000000.00),
    ('E002', 'Tran Thi B', 'b@example.com', '0987654321', 'Female', '1992-03-30', 'HR', 'HR Specialist', 10000000.00),
    ('E003', 'Le Van C', 'c@example.com', '0912345678', 'Male', '1988-07-20', 'IT', 'Developer', 15000000.00);
GO

PRINT 'company_db schema and sample data created.';
