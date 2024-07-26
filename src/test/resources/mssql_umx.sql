USE [master]
GO

CREATE DATABASE [umx]
GO

USE [umx]
GO
/****** Object:  Table [dbo].[application]    Script Date: 14/10/2019 11:57:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- DROP SCHEMA dbo;

CREATE SCHEMA dbo;
-- umx.dbo.application definition

-- Drop table

-- DROP TABLE umx.dbo.application GO

CREATE TABLE umx.dbo.application (
  id bigint IDENTITY(1,1) NOT NULL,
  name nvarchar(128) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  configurationName nvarchar(128) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  recipientList nvarchar(256) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
  attributeList nvarchar(500) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  mailSubject varchar(1000) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
  mailBody varchar(5000) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
  serverId varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  status bit NOT NULL,
  CONSTRAINT application_PK PRIMARY KEY (id)
) GO;


-- umx.dbo.server definition

-- Drop table

-- DROP TABLE umx.dbo.server GO

CREATE TABLE umx.dbo.server (
  id varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  name varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  url varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  username varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  password varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  CONSTRAINT server_PK PRIMARY KEY (id)
) GO;


-- umx.dbo.server_configuration definition

-- Drop table

-- DROP TABLE umx.dbo.server_configuration GO

CREATE TABLE umx.dbo.server_configuration (
  serverId varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  configurationName varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL
) GO;


-- umx.dbo.setting definition

-- Drop table

-- DROP TABLE umx.dbo.setting GO

CREATE TABLE umx.dbo.setting (
  id bigint IDENTITY(1,1) NOT NULL,
  name nvarchar(128) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  value nvarchar(512) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  sortorder int NULL,
  description nvarchar(512) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
  CONSTRAINT setting_PK PRIMARY KEY (id)
) GO;


-- umx.dbo.dispensation definition

-- Drop table

-- DROP TABLE umx.dbo.dispensation GO

CREATE TABLE umx.dbo.dispensation (
  id bigint IDENTITY(1,1) NOT NULL,
  applicationId bigint NOT NULL,
  name nvarchar(128) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  CONSTRAINT dispensation_PK PRIMARY KEY (id),
  CONSTRAINT dispensation_FK FOREIGN KEY (applicationId) REFERENCES umx.dbo.application(id) ON DELETE CASCADE
) GO;


-- umx.dbo.execution definition

-- Drop table

-- DROP TABLE umx.dbo.execution GO

CREATE TABLE umx.dbo.execution (
  id varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  applicationId bigint NOT NULL,
  status varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  startDt datetime NOT NULL,
  completedDt datetime NULL,
  name varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
  CONSTRAINT execution_PK PRIMARY KEY (id),
  CONSTRAINT execution_FK FOREIGN KEY (applicationId) REFERENCES umx.dbo.application(id) ON DELETE CASCADE
) GO;


-- umx.dbo.execution_data definition

-- Drop table

-- DROP TABLE umx.dbo.execution_data GO

CREATE TABLE umx.dbo.execution_data (
  executionId varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  whiteListCompliant int NOT NULL,
  whitelistNonCompliant int NOT NULL,
  whitelistProcessed int NOT NULL,
  normalCompliant int NOT NULL,
  normalNonCompliant int NOT NULL,
  normalProcessed int NOT NULL,
  rulesProcessed int NOT NULL,
  accountsProcessed int NOT NULL,
  CONSTRAINT execution_data_PK PRIMARY KEY (executionId),
  CONSTRAINT execution_data_FK FOREIGN KEY (executionId) REFERENCES umx.dbo.execution(id) ON DELETE CASCADE
) GO;


-- umx.dbo.execution_violation definition

-- Drop table

-- DROP TABLE umx.dbo.execution_violation GO

CREATE TABLE umx.dbo.execution_violation (
  executionId varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  accountId varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  roleList nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  accountType varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  userId varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  CONSTRAINT execution_violation_PK PRIMARY KEY (executionId,accountId),
  CONSTRAINT execution_violation_FK FOREIGN KEY (executionId) REFERENCES umx.dbo.execution(id) ON DELETE CASCADE
) GO;


-- umx.dbo.[rule] definition

-- Drop table

-- DROP TABLE umx.dbo.[rule] GO

CREATE TABLE umx.dbo.[rule] (
  id bigint IDENTITY(1,1) NOT NULL,
  applicationId bigint NOT NULL,
  attributeMap nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  rolename nvarchar(256) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
  hash varchar(256) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
  CONSTRAINT rule_PK PRIMARY KEY (id),
  CONSTRAINT rule_FK FOREIGN KEY (applicationId) REFERENCES umx.dbo.application(id) ON DELETE CASCADE
) GO;

-- umx.dbo.business_role definition
CREATE TABLE umx.dbo.business_role (
    id varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
    name nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
    hrRole nvarchar(200) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
    description nvarchar(500) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    createdDt datetime NOT NULL DEFAULT GETDATE()
) GO;

-- umx.dbo.business_role_item definition
CREATE TABLE umx.dbo.business_role_item (
    roleId varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
    application nvarchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
    [role] nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
    CONSTRAINT business_role_item_PK PRIMARY KEY (roleId,application,[role])
) GO;

TRUNCATE TABLE [setting];

INSERT INTO umx.dbo.setting (name,value,sortorder,description) VALUES 
('umx.url','http://localhost:8080/umx',1,'UMX URL')
,('mail.enable','false',2,'flag to turn Email Sending on and off')
,('mail.server.host','192.168.0.32',3,'Mail Server Host')
,('mail.server.port','25',4,'Mail Server Port')
,('mail.sender','no-reply@ic.sg',5,'Mail Sender')
,('mail.subject','Please remove excessive roles now',6,'Mail Subject')
,('mail.content','Hi, Updated content to include this.',7,'Mail Content')
,('ce.api.key','ocoIRDGSQe0fpp4H4hS3u5haXBL6frNN1',8,'CE API Key');