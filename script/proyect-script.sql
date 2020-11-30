-- Script - Proyecto Fundamentos de bases de datos --
-- Create database --
CREATE DATABASE B88782_B90127_B90514;
-- Use --
USE B88782_B90127_B90514;
-- Create Line table --
CREATE TABLE [Line]
(
    [Telephone_Number] INT NOT NULL PRIMARY KEY,
    [Points_Quantity] INT DEFAULT 0 NOT NULL,
    [Type] TINYINT NOT NULL,
    [Status] CHAR DEFAULT 'A' NOT NULL,
    [Creation_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Creation_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL,
    [Last_Update_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Last_Update_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL
)
GO
-- Create Line_Customer table --
CREATE TABLE [Line_Customer]
(
    [Telephone_Number] INT NOT NULL,
    [Customer_Id] INT NOT NULL,
    [Customer_First_Name] NVARCHAR(50) NOT NULL,
    [Customer_Last_Name] NVARCHAR(50) NOT NULL,
    [Customer_Address] NVARCHAR(50),
    [Customer_Email] NVARCHAR(50) NOT NULL,
    [Creation_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Creation_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL,
    [Last_Update_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Last_Update_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL
)
GO
-- Create Service table --
CREATE TABLE [Service]
(
    [Service_Code] INT IDENTITY PRIMARY KEY,
    [Name] NVARCHAR(50) NOT NULL,
    [Description] NVARCHAR(200) NOT NULL,
    [Cost] MONEY NULL,
    [Status] CHAR DEFAULT 'A' NOT NULL,
    [Creation_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Creation_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL,
    [Last_Update_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Last_Update_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL
)
GO
-- Create Field table --
CREATE TABLE [Field]
(
    [Field_Code] INT  IDENTITY  NOT NULL PRIMARY KEY,
    [Type] TINYINT NOT NULL,
    [Creation_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Creation_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL,
    [Last_Update_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Last_Update_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL
)
GO
-- Create Time_Zone table --
CREATE TABLE [Time_Zone]
(
    [Time_Zone_Id] INT  IDENTITY  NOT NULL PRIMARY KEY,
    [Description] NVARCHAR(20) NOT NULL,
    [Init_Day] TINYINT NOT NULL,
    [End_Day] TINYINT NOT NULL,
    [Init_Hour] TIME NOT NULL,
    [End_Hour] TIME NOT NULL,
    [Creation_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Creation_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL,
    [Last_Update_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Last_Update_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL
)
GO
-- Create Duration_Range table --
CREATE TABLE [Duration_Range]
(
    [Range_Id] INT  IDENTITY  NOT NULL PRIMARY KEY,
    [Init_Min] INT NOT NULL,
    [End_Min] INT NOT NULL,
    [Creation_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Creation_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL,
    [Last_Update_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Last_Update_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL
)
GO
-- Create Invoice table --
CREATE TABLE [Invoice]
(
    [No_Invoice] INT  IDENTITY  NOT NULL PRIMARY KEY,
    [Telephone_Number] INT NOT NULL,
    [Subtotal_Amount] MONEY NOT NULL,
    [Tax_Amount] MONEY NOT NULL,
    [Date]  DATE  NOT NULL,
    [Used_Points] INT NOT NULL,
    [Available_Points] INT NOT NULL,
    [Earned_Points] INT NOT NULL,
    [Total_Amount] MONEY NOT NULL,
    [Creation_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Creation_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL,
    [Last_Update_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Last_Update_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL
)
GO
-- Create Call table --
CREATE TABLE [Call]
(
    [No_Call] INT IDENTITY PRIMARY KEY,
    [Telephone_Number] INT NOT NULL,
    [Destination_Telephone_Number] INT NOT NULL,
    [Start_Date] DATETIME NOT NULL,
    [End_Date] DATETIME NULL,
    [Creation_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Creation_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL,
    [Last_Update_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Last_Update_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL
)
GO
-- Create Incompatible_Discount_Rate table --
CREATE TABLE [Incompatible_Discount_Rate]
(
    [Discount_Rate_Id_C] INT NOT NULL,
    [Discount_Rate_Id] INT NOT NULL,
    [Creation_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Creation_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL,
    [Last_Update_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Last_Update_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL
)
GO
-- Create Discount_Rate_Field table --
CREATE TABLE [Discount_Rate_Field]
(
    [Discount_Rate_Id] INT NOT NULL,
    [Field_Code] INT NOT NULL,
    [Creation_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Creation_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL,
    [Last_Update_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Last_Update_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL
)
GO
-- Create Line_Service table --
CREATE TABLE [Line_Service]
(
    [Telephone_Number] INT NOT NULL,
    [Service_Code] INT NOT NULL,
    [Creation_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Creation_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL,
    [Last_Update_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Last_Update_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL
)
GO
-- Create Line_Discount_Rate table --
CREATE TABLE [Line_Discount_Rate]
(
    [Discount_Rate_Id] INT NOT NULL,
    [Telephone_Number] INT NOT NULL,
    [Creation_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Creation_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL,
    [Last_Update_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Last_Update_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL
)
GO
-- Create Discount_Rate_Range_Time_Zone table --
CREATE TABLE [Discount_Rate_Range_Time_Zone]
(
    [Discount_Rate_Id] INT NOT NULL,
    [Discount_Range_Id] INT NOT NULL,
    [Time_Zone_Id] INT NOT NULL,
    [Price] MONEY NOT NULL,
    [Creation_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Creation_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL,
    [Last_Update_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Last_Update_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL
)
GO
-- Create Discount_Rate table --
CREATE TABLE [Discount_Rate]
(
    [Discount_Rate_Id] INT IDENTITY PRIMARY KEY,
    [Establishment_Call_Rate] MONEY DEFAULT 0 NOT NULL,
    [High_Rate] MONEY NOT NULL,
    [Discount_Range_Code] INT NOT NULL,
    [Name] NVARCHAR(50) NOT NULL,
    [Creation_Date]DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Creation_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL,
    [Last_Update_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Last_Update_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL
)
GO
-- Create Bonus table --
CREATE TABLE [Bonus]
(
    [Discount_Rate_Id] INT NOT NULL,
    [Type] NVARCHAR(10) NOT NULL,
    [Monthly_Rate] NVARCHAR(10) NOT NULL,
    [Creation_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Creation_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL,
    [Last_Update_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Last_Update_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL
)
GO
-- Create Discount_Plan table --
CREATE TABLE [Discount_Plan]
(
    [Discount_Rate_Id] INT NOT NULL,
    [Discount_Percentage] FLOAT NOT NULL,
    [Creation_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Creation_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL,
    [Last_Update_Date] DATETIME DEFAULT SYSDATETIME() NOT NULL,
    [Last_Update_User] NVARCHAR(128) DEFAULT SYSTEM_USER NOT NULL
)
GO
-- Add Checks --
-- Line --
ALTER TABLE [Line] ADD CHECK([Status] = 'A' OR [Status] = 'I');
ALTER TABLE [Line] ADD CHECK([Type] >= 0 AND [Type] <= 255); 
-- Service --
ALTER TABLE [Service] ADD CHECK([Status] = 'A' OR [Status] = 'I');
ALTER TABLE [Service] ADD CHECK([Cost] >= 0);
-- Field --
ALTER TABLE [Field] ADD CHECK([Type] >= 0 AND [Type] <= 255);
-- Time_Zone --
ALTER TABLE [Time_Zone] ADD CHECK([Init_Day] >= 0 AND [Init_Day] <= 6);
ALTER TABLE [Time_Zone] ADD CHECK([End_Day] >= 0 AND [End_Day] <= 6);
-- Duration_Range --
ALTER TABLE [Duration_Range] ADD CHECK([Init_Min] >= 0);
ALTER TABLE [Duration_Range] ADD CHECK([End_Min] > [Init_Min]);
-- Invoice --
ALTER TABLE [Invoice] ADD CHECK([Subtotal_Amount] >= 0);
ALTER TABLE [Invoice] ADD CHECK([Tax_Amount] >= 0);
ALTER TABLE [Invoice] ADD CHECK([Used_Points] >= 0);
ALTER TABLE [Invoice] ADD CHECK([Available_Points] >= 0);
ALTER TABLE [Invoice] ADD CHECK([Earned_Points] >= 0);
ALTER TABLE [Invoice] ADD CHECK([Total_Amount] >= 0);
-- Call --
ALTER TABLE [Call] ADD CHECK([End_Date] > [Start_Date]);
-- Discount_Rate_Range_Time_Zone --
ALTER TABLE [Discount_Rate_Range_Time_Zone] ADD CHECK([Price] >= 0);
-- Discount_Rate --
ALTER TABLE [Discount_Rate] ADD CHECK([Establishment_Call_Rate] >= 0);
ALTER TABLE [Discount_Rate] ADD CHECK([High_Rate] >= 0);
-- Discount_Plan --
ALTER TABLE [Discount_Plan] ADD CHECK([Discount_Percentage] >= 0);
-- Show checks --
SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS where CONSTRAINT_TYPE = 'CHECK'