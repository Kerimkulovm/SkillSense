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
*/



-- Courses  START
CREATE TABLE [dbo].[Courses_1](
	[CoarseNo] [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	[EngName] [nvarchar](200) NULL,
	[RusName] [nvarchar](200) NULL,
	[isActive] [bit] NULL
) 

set IDENTITY_INSERT [Courses_1] ON
insert into  [Courses_1] (CoarseNo, EngName, RusName, isActive)
select CoarseNo, Equipment, Equipment, isactive from courses
set IDENTITY_INSERT [Courses_1] OFF


exec sp_rename 'courses', 'courses_del'
exec sp_rename 'Courses_1', 'courses'
-- Courses  END



-- Crews  START
CREATE TABLE [dbo].[Crews_1](
	[CrewNo] [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	CrewName varchar(200) ,
	[isActive] [bit],
) 

set IDENTITY_INSERT [Crews_1] ON
insert into  [Crews_1] ([CrewNo], CrewName, isActive)
select CrewNo, Crew, isactive from Crews
set IDENTITY_INSERT [Crews_1] OFF


exec sp_rename 'Crews', 'Crews_del'
exec sp_rename 'Crews_1', 'Crews'
-- Crews  END


-- Departments  START
CREATE TABLE [dbo].[Departments_1](
	[DeptId] [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	EngName varchar(200) ,
	RusName varchar(200) ,
	[isActive] [bit] 
) 


set IDENTITY_INSERT Departments_1 ON
insert into  Departments_1 (DeptId, EngName, RusName, isActive)
select deptId, [Departmant], russian, isactive from Departments
set IDENTITY_INSERT Departments_1 OFF


exec sp_rename 'Departments', 'Departments_del'
exec sp_rename 'Departments_1', 'Departments'
-- Departments  END


-- Instructor  START
CREATE TABLE [dbo].[Instructor_1](
	[InstructoId] [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	EngName varchar(200) ,
	RusName varchar(200) ,
	[isActive] [bit] 
) 

set IDENTITY_INSERT [Instructor_1] ON
insert into  [Instructor_1] ([InstructoId], EngName, RusName, isActive)
select InstructoId, InstructorName, Instructor, isActive from Instructor
set IDENTITY_INSERT [Instructor_1] OFF


exec sp_rename 'Instructor', 'Instructor_del'
exec sp_rename 'Instructor_1', 'Instructor'
-- Instructor  END


-- Position  Start
CREATE TABLE [dbo].[Position](
	[positionId] [int] identity ,
	EngName varchar(200) ,
	RusName varchar(200) ,
	[isActive] [bit] 
) 


insert  into [Position] (RusName, isactive)
select distinct RusTitle as RusName, 1 isActive from Employees where RusTitle is not null

alter table [Position] add constraint pk_Position primary key (positionId)
-- Position  END


-- SAFETY_NAMES START
CREATE TABLE [dbo].[SafetyNames_1](
	[ReviewNo] [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	EngName varchar(200) ,
	RusName varchar(200) ,
	[isActive] [bit] 
) 

set IDENTITY_INSERT [SafetyNames_1] ON
insert into  [SafetyNames_1] ([ReviewNo], EngName, RusName, isActive)
select [ReviewNo], ReviewName, course, isActive from SafetyNames
set IDENTITY_INSERT [SafetyNames_1] OFF


exec sp_rename 'SafetyNames', 'SafetyNames_del'
exec sp_rename 'SafetyNames_1', 'SafetyNames'
-- SAFETY_NAMES END  


-- Supervisors START 
CREATE TABLE [dbo].[Supervisors_1](
	[SupervisorId] [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	EngName varchar(200) ,
	RusName varchar(200) ,
	[isActive] [bit] 
) 

set IDENTITY_INSERT [Supervisors_1] ON
insert into  [Supervisors_1] ([SupervisorId], EngName, RusName, isActive)
select SupervisorId, Supervisor, Russian, isActive from Supervisors
set IDENTITY_INSERT [Supervisors_1] OFF


exec sp_rename 'Supervisors', 'Supervisors_del'
exec sp_rename 'Supervisors_1', 'Supervisors'
-- Supervisors END 


CREATE TABLE [dbo].[Employees_1](
	[RecID] [int]  NOT NULL IDENTITY(1,1) PRIMARY KEY,
	[EmployeeID] [varchar](20) NULL,
	RusName varchar(100) ,
	EngName varchar(100) ,
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




-- Qualified START

select * into qualified_temp from Qualified

alter table qualified_temp
add id int identity constraint pk_Qualified primary key clustered



CREATE TABLE [dbo].[Qualified_1](
	[RecID] [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	[Equipment] [int] NOT NULL,
	[EmployeeID] [int] NOT NULL,
	[Date] [datetime] NULL,
	[Experienced] [bit] ,
	[Qualified] [bit],
	[Approved] [bit] ,
	[Training] [bit] 
) 

set IDENTITY_INSERT [Qualified_1] ON
insert into  [Qualified_1] ( RecID, Equipment, EmployeeID, date, Experienced, Qualified, Approved, Training)
select  id, Equipment, EmployeeID, date, Experienced, Qualified, Approved, Training from qualified_temp
set IDENTITY_INSERT [Qualified_1] OFF


exec sp_rename 'Qualified', 'Qualified_del'
exec sp_rename '[Qualified_1]', 'Qualified'
-- Qualified END


-- AnnualTraining START
CREATE TABLE [dbo].[AnnualTraining_1](
	[RecID] [int]  NOT NULL IDENTITY(1,1) PRIMARY KEY,
	[EmployeeId] [int] NOT NULL,
	[ScheduledDate] [datetime] NULL,
	[LastDate] [datetime] NULL,
	[Coarse] [int] NOT NULL,
	[PHours] [float] NULL,
	[Fieldhours] [float] NULL,
	[Thours] [float] NULL,
	[Location] [nvarchar](200) NULL,
	[Instructor] [int] NULL,
	[Comments] [nvarchar](200) NULL,
	[Mark] [int] NULL,
) 


set IDENTITY_INSERT [AnnualTraining_1] ON
insert into  [AnnualTraining_1] (RecID, EmployeeId, ScheduledDate, LastDate, Coarse, PHours, Fieldhours, Thours, Location, Instructor, Comments, mark)
select RecID, EmployeeId, ScheduledDate, LastDate, Coarse, PHours, Fieldhours, Thours, Location, Instructor, Comments, mark from SRT
set IDENTITY_INSERT [AnnualTraining_1] OFF


exec sp_rename 'SRT', 'SRT_del'
exec sp_rename 'AnnualTraining_1', 'AnnualTraining'

-- AnnualTraining END



-- TrainingData Start
CREATE TABLE [dbo].[TrainingData_1](
	[RecID] [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	[EmployeeID] [int] NOT NULL,
	[Date] [datetime] ,
	[Thours] [float] ,
	[Phours] [float] ,
	[ExpHours] [float] ,
	[Fhours] [float] ,
	[mark] [int] ,
	[Coarse] [int] ,
	[Instructor] [int] NOT NULL
	
) 

set IDENTITY_INSERT [TrainingData_1] ON
insert into  [TrainingData_1] (RecID, EmployeeId, Date, Thours, PHours, ExpHours, Fhours, mark, Coarse, Instructor )
select RecID, EmployeeId, Date, Thours, PHours, ExpHours, Fhours, mark, Coarse, Instructor  from TrainingData
set IDENTITY_INSERT [TrainingData_1] OFF


exec sp_rename 'TrainingData', 'TrainingData_del'
exec sp_rename 'TrainingData_1', 'TrainingData'
-- TrainingData END




-- Employees Start
CREATE TABLE [dbo].[Employees_1](
	[RecID] [int]  identity,
	[EmployeeID] [varchar](20) NULL,
	RusName varchar(100) ,
	EngName varchar(100) ,
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

insert  into [Employees_1] (EmployeeID, RusName, EngName, HireDate, ReportsTo, position, Shiftn, Department, Terminated, SafteyOrin, Photo, local_A, local_B, local_V, local_G, local_D, local_E, local_E1,
gos_A, gos_A1, gos_B, gos_b1, gos_c, gos_c1, gos_d, gos_d1, gos_BE, gos_CE, gos_C1E, gos_DE, gos_D1E)

select EmployeeID, LastName, FirstName, HireDate, ReportsTo, p.positionId as position, Shiftn, Department, Terminated, SafteyOrin, Photo, kumtor_A, kumtor_B, kumtor_V, kumtor_G, kumtor_D, kumtor_E, kumtor_E1,
gos_A, gos_A1, gos_B, gos_b1, gos_c, gos_c1, gos_d, gos_d1, gos_BE, gos_CE, gos_C1E, gos_DE, gos_D1E 
from Employees e 
left join position p on p.RusName = e.RusTitle COLLATE Latin1_General_CI_AS ;


alter table [Employees_1] add constraint pk_Employees primary key (RecID)

exec sp_rename 'Employees', 'Employees_del'
exec sp_rename 'Employees_1', 'Employees'
-- Employees END









CREATE TABLE Users(
	UserId  [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	login varchar(200) ,
	psw varchar(200) ,
	role int,
	isActive int 
);


CREATE TABLE Roles(
	RoleId  [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	RoleName varchar(200) ,
	isActive int 
);


CREATE TABLE logs(
	LogId  [int] NOT NULL IDENTITY(1,1) PRIMARY KEY,
	date [datetime],
	Userid int,
	LogText varchar(2000) ,
	isActive int 
);




insert into Roles values
('admin', 1),
('user', 1),
('reader', 1);


insert into users values
('user', '123', 1, 1);