/*drop table AnnualTraining;
drop table Courses;
drop table Crews;
drop table Departments;
drop table Employees;
drop table Instructor;
drop table Position;
drop table Qualified;
drop table SafetyNames;
drop table Supervisors;
drop table TrainingData;
drop table users;
drop table roles;
drop table logs;
*/

CREATE TABLE [dbo].[AnnualTraining](
	[RecID] [int]  NOT NULL IDENTITY(1,1) PRIMARY KEY,
	[EmployeeId] [int] NOT NULL,
	[ScheduledDate] [datetime] NULL,
	[LastDate] [datetime] NULL,
	[Coarse] [int] NOT NULL,
	[PHours] [float] NULL,
	[Fieldhours] [float] NULL,
	[Thours] [float] NULL,
	[Location] [nvarchar](200) COLLATE Cyrillic_General_CI_AS NULL,
	[Instructor] [int] NULL,
	[Comments] [nvarchar](200) COLLATE Cyrillic_General_CI_AS NULL,
	[Mark] [int] NULL,
	[isActive] [bit]
) 



CREATE TABLE [dbo].[Courses](
	[CoarseNo] [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	[EngName] [nvarchar](200) COLLATE Cyrillic_General_CI_AS NULL,
	[RusName] [nvarchar](200) COLLATE Cyrillic_General_CI_AS NULL,
	[isActive] [bit] NULL
) 



CREATE TABLE [dbo].[Crews](
	[CrewNo] [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	CrewName varchar(200) COLLATE Cyrillic_General_CI_AS,
	[isActive] [bit],
) 



CREATE TABLE [dbo].[Departments](
	[DeptId] [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	EngName varchar(200) COLLATE Cyrillic_General_CI_AS,
	RusName varchar(200) COLLATE Cyrillic_General_CI_AS,
	[isActive] [bit] 
) 



CREATE TABLE [dbo].[Employees](
	[RecID] [int]  NOT NULL IDENTITY(1,1) PRIMARY KEY,
	[EmployeeID] [varchar](20) NULL,
	RusName varchar(100)  COLLATE Cyrillic_General_CI_AS ,
	EngName varchar(100)  COLLATE Cyrillic_General_CI_AS ,
	[HireDate] [datetime] NULL,
	[ReportsTo] [int] NULL,
	[position] [int] NULL,
	[Shiftn] [int] NULL,
	[Department] [int] NULL,
	[Terminated] [bit] NOT NULL,
	[SafteyOrin] [datetime] NULL,
	[Photo] [image] NULL,
	[local_A] [smalldatetime] NULL,
	[local_B] [smalldatetime] NULL,
	[local_V] [smalldatetime] NULL,
	[local_G] [smalldatetime] NULL,
	[local_D] [smalldatetime] NULL,
	[local_E] [smalldatetime] NULL,
	[local_E1] [smalldatetime] NULL,
	[gos_A] [smalldatetime] NULL,
	[gos_A1] [smalldatetime] NULL,
	[gos_B] [smalldatetime] NULL,
	[gos_B1] [smalldatetime] NULL,
	[gos_C] [smalldatetime] NULL,
	[gos_C1] [smalldatetime] NULL,
	[gos_D] [smalldatetime] NULL,
	[gos_D1] [smalldatetime] NULL,
	[gos_BE] [smalldatetime] NULL,
	[gos_CE] [smalldatetime] NULL,
	[gos_C1E] [smalldatetime] NULL,
	[gos_DE] [smalldatetime] NULL,
	[gos_D1E] [smalldatetime] NULL
)  




CREATE TABLE [dbo].[Instructor](
	[InstructoId] [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	EngName varchar(200) COLLATE Cyrillic_General_CI_AS,
	RusName varchar(200) COLLATE Cyrillic_General_CI_AS,
	[isActive] [bit] 
) 



CREATE TABLE [dbo].[Position](
	[positionId] [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	EngName varchar(200) COLLATE Cyrillic_General_CI_AS,
	RusName varchar(200) COLLATE Cyrillic_General_CI_AS,
	[isActive] [bit] 
) 



CREATE TABLE [dbo].[Qualified](
	[RecID] [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	[Equipment] [int]  COLLATE Cyrillic_General_CI_AS NOT NULL,
	[EmployeeID] [int] NOT NULL,
	[Date] [datetime] NULL,
	[Experienced] [bit] ,
	[Qualified] [bit],
	[Approved] [bit] ,
	[Training] [bit] 
) 




CREATE TABLE [dbo].[SafetyNames](
	[ReviewNo] [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	EngName varchar(200) COLLATE Cyrillic_General_CI_AS,
	RusName varchar(200) COLLATE Cyrillic_General_CI_AS,
	[isActive] [bit] 
) 



CREATE TABLE [dbo].[Supervisors](
	[SupervisorId] [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	EngName varchar(200) COLLATE Cyrillic_General_CI_AS,
	RusName varchar(200) COLLATE Cyrillic_General_CI_AS,
	[isActive] [bit] 
) 




CREATE TABLE [dbo].[TrainingData](
	[RecID] [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	[EmployeeID] [int] NOT NULL,
	[Date] [datetime] ,
	[Thours] [float] ,
	[Phours] [float] ,
	[ExpHours] [float] ,
	[Fhours] [float] ,
	[mark] [int] ,
	[Coarse] [int] ,
	[Instructor] [int] NOT NULL,
	[isActive] [bit]
) 



CREATE TABLE Users(
	UserId  [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	login varchar(200) COLLATE Cyrillic_General_CI_AS,
	psw varchar(200) COLLATE Cyrillic_General_CI_AS,
	role int,
	isActive int 
);


CREATE TABLE Roles(
	RoleId  [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	RoleName varchar(200) COLLATE Cyrillic_General_CI_AS,
	isActive int 
);


CREATE TABLE logs(
	LogId  [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	date [datetime],
	Userid int,
	LogText varchar(2000) COLLATE Cyrillic_General_CI_AS,
	LogRus varchar(2000) COLLATE Cyrillic_General_CI_AS,
	isActive int 
);




insert into Roles values
('admin', 1),
('user', 1),
('reader', 1);


insert into users values
('user', '123', 1, 1);