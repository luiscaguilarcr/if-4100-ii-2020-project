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

-- Add FKs --
-- Bonus, Discount_Rate ---
ALTER TABLE [dbo].[Bonus]  WITH CHECK ADD  CONSTRAINT [Bonus_Discount_Rate_Discount_Rate_Id_fk] FOREIGN KEY([Discount_Rate_Id])
REFERENCES [dbo].[Discount_Rate] ([Discount_Rate_Id])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[Bonus] CHECK CONSTRAINT [Bonus_Discount_Rate_Discount_Rate_Id_fk]
GO
-- Call, Line --
ALTER TABLE [dbo].[Call]  WITH CHECK ADD  CONSTRAINT [Call_Line_Telephone_Number_fk] FOREIGN KEY([Telephone_Number])
REFERENCES [dbo].[Line] ([Telephone_Number])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Call] CHECK CONSTRAINT [Call_Line_Telephone_Number_fk]
GO
-- Discount_Plan, Discount_Rate --
ALTER TABLE [dbo].[Discount_Plan]  WITH CHECK ADD  CONSTRAINT [Discount_Plan_Discount_Rate_Discount_Rate_Id_fk] FOREIGN KEY([Discount_Rate_Id])
REFERENCES [dbo].[Discount_Rate] ([Discount_Rate_Id])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[Discount_Plan] CHECK CONSTRAINT [Discount_Plan_Discount_Rate_Discount_Rate_Id_fk]
GO
-- Dsicount_Rate_Field, Discount_Rate --
ALTER TABLE [dbo].[Discount_Rate_Field]  WITH CHECK ADD  CONSTRAINT [Discount_Rate_Field_Discount_Rate_Discount_Rate_Id_fk] FOREIGN KEY([Discount_Rate_Id])
REFERENCES [dbo].[Discount_Rate] ([Discount_Rate_Id])
GO
ALTER TABLE [dbo].[Discount_Rate_Field] CHECK CONSTRAINT [Discount_Rate_Field_Discount_Rate_Discount_Rate_Id_fk]
GO
-- Dsicount_Rate_Field, Field --
ALTER TABLE [dbo].[Discount_Rate_Field]  WITH CHECK ADD  CONSTRAINT [Discount_Rate_Field_Field_Field_Code_fk] FOREIGN KEY([Field_Code])
REFERENCES [dbo].[Field] ([Field_Code])
GO
ALTER TABLE [dbo].[Discount_Rate_Field] CHECK CONSTRAINT [Discount_Rate_Field_Field_Field_Code_fk]
GO
-- Discount_Rate_Range_Time_Zone, Discount_Rate --
ALTER TABLE [dbo].[Discount_Rate_Range_Time_Zone]  WITH CHECK ADD  CONSTRAINT [Discount_Rate_Range_Time_Zone_Discount_Rate_Discount_Rate_Id_fk] FOREIGN KEY([Discount_Rate_Id])
REFERENCES [dbo].[Discount_Rate] ([Discount_Rate_Id])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[Discount_Rate_Range_Time_Zone] CHECK CONSTRAINT [Discount_Rate_Range_Time_Zone_Discount_Rate_Discount_Rate_Id_fk]
GO
-- Discount_Rate_Range_Time_Zone, Duration_Range --
ALTER TABLE [dbo].[Discount_Rate_Range_Time_Zone]  WITH CHECK ADD  CONSTRAINT [Discount_Rate_Range_Time_Zone_Duration_Range_Range_Id_fk] FOREIGN KEY([Discount_Range_Id])
REFERENCES [dbo].[Duration_Range] ([Range_Id])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[Discount_Rate_Range_Time_Zone] CHECK CONSTRAINT [Discount_Rate_Range_Time_Zone_Duration_Range_Range_Id_fk]
GO
-- Discount_Rate_Range_Time_Zone, Time_Zone --
ALTER TABLE [dbo].[Discount_Rate_Range_Time_Zone]  WITH CHECK ADD  CONSTRAINT [Discount_Rate_Range_Time_Zone_Time_Zone_Time_Zone_Id_fk] FOREIGN KEY([Time_Zone_Id])
REFERENCES [dbo].[Time_Zone] ([Time_Zone_Id])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[Discount_Rate_Range_Time_Zone] CHECK CONSTRAINT [Discount_Rate_Range_Time_Zone_Time_Zone_Time_Zone_Id_fk]
GO
-- Incompatible_Discount_Rate, Discount_Rate --
ALTER TABLE [dbo].[Incompatible_Discount_Rate]  WITH CHECK ADD  CONSTRAINT [Incompatible_Discount_Rate_Discount_Rate_Discount_Rate_Id_fk] FOREIGN KEY([Discount_Rate_Id])
REFERENCES [dbo].[Discount_Rate] ([Discount_Rate_Id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Incompatible_Discount_Rate] CHECK CONSTRAINT [Incompatible_Discount_Rate_Discount_Rate_Discount_Rate_Id_fk]
GO
ALTER TABLE [dbo].[Incompatible_Discount_Rate]  WITH CHECK ADD  CONSTRAINT [Incompatible_Discount_Rate_Discount_Rate_Discount_Rate_Id_fk_2] FOREIGN KEY([Discount_Rate_Id_C])
REFERENCES [dbo].[Discount_Rate] ([Discount_Rate_Id])
GO
ALTER TABLE [dbo].[Incompatible_Discount_Rate] CHECK CONSTRAINT [Incompatible_Discount_Rate_Discount_Rate_Discount_Rate_Id_fk_2]
GO
-- Invoice, Line --
ALTER TABLE [dbo].[Invoice]  WITH CHECK ADD  CONSTRAINT [Invoice_Line_Telephone_Number_fk] FOREIGN KEY([Telephone_Number])
REFERENCES [dbo].[Line] ([Telephone_Number])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Invoice] CHECK CONSTRAINT [Invoice_Line_Telephone_Number_fk]
GO
-- Line_Customer], Line --
ALTER TABLE [dbo].[Line_Customer]  WITH CHECK ADD  CONSTRAINT [Line_Customer_Line_Telephone_Number_fk] FOREIGN KEY([Telephone_Number])
REFERENCES [dbo].[Line] ([Telephone_Number])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Line_Customer] CHECK CONSTRAINT [Line_Customer_Line_Telephone_Number_fk]
GO
-- Line_Discount_Rate], Discount_Rate --
ALTER TABLE [dbo].[Line_Discount_Rate]  WITH CHECK ADD  CONSTRAINT [Line_Discount_Rate_Discount_Rate_Discount_Rate_Id_fk] FOREIGN KEY([Discount_Rate_Id])
REFERENCES [dbo].[Discount_Rate] ([Discount_Rate_Id])
GO
ALTER TABLE [dbo].[Line_Discount_Rate] CHECK CONSTRAINT [Line_Discount_Rate_Discount_Rate_Discount_Rate_Id_fk]
GO
-- Line_Discount_Rate, Line --
ALTER TABLE [dbo].[Line_Discount_Rate]  WITH CHECK ADD  CONSTRAINT [Line_Discount_Rate_Line_Telephone_Number_fk] FOREIGN KEY([Telephone_Number])
REFERENCES [dbo].[Line] ([Telephone_Number])
GO
ALTER TABLE [dbo].[Line_Discount_Rate] CHECK CONSTRAINT [Line_Discount_Rate_Line_Telephone_Number_fk]
GO
-- Line_Service, Line --
ALTER TABLE [dbo].[Line_Service]  WITH CHECK ADD  CONSTRAINT [Line_Service_Line_Telephone_Number_fk] FOREIGN KEY([Telephone_Number])
REFERENCES [dbo].[Line] ([Telephone_Number])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Line_Service] CHECK CONSTRAINT [Line_Service_Line_Telephone_Number_fk]
GO
-- Line_Service, Service --
ALTER TABLE [dbo].[Line_Service]  WITH CHECK ADD  CONSTRAINT [Line_Service_Service_Service_Code_fk] FOREIGN KEY([Service_Code])
REFERENCES [dbo].[Service] ([Service_Code])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Line_Service] CHECK CONSTRAINT [Line_Service_Service_Service_Code_fk]
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

-- Add View --
-- Call, Service and Line -- 
CREATE VIEW Line_Call_Service_Customer 
AS 
SELECT L.[Telephone_Number] AS 'Telephone_Number', LC.[Customer_Id] AS 'Customer_ID', LC.[Customer_First_Name] AS 'Customer_First_Name', LC.[Customer_Last_Name] AS 'Customer_Last_Name', L.[Status] AS 'Status', S.[Name] AS 'Name'
FROM [dbo].[Line] L, [dbo].[Call] C , [dbo].[Service] S, [dbo].[Line_Customer] LC, [dbo].[Line_Service] LS
WHERE L.[Telephone_Number] = LS.[Telephone_Number]
AND LS.[Service_Code] = S.[Service_Code]
AND L.Telephone_Number = LC.Telephone_Number
GO

SELECT * FROM [dbo].[Line_Call_Service_Customer]
WHERE [Name] = 'contestador'
AND [Status] = 'A'


DROP VIEW Line_Call_Service_Customer

INSERT INTO [dbo].[Line_Service] ([Telephone_Number], [Service_Code]) VALUES (11111111, 1)
INSERT INTO [dbo].[Line_Service] ([Telephone_Number], [Service_Code]) VALUES (8747445, 5)
INSERT INTO [dbo].[Line_Service] ([Telephone_Number], [Service_Code]) VALUES (11111111, 1)
INSERT INTO [dbo].[Line_Service] ([Telephone_Number], [Service_Code]) VALUES (11111111, 1)

SELECT * FROM [dbo].[Line_Service]

SELECT * FROM [dbo].[Line_Service]
