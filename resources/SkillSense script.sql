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



CREATE TABLE AnnualTraining(
	RecID int primary key generated always as identity,
	EmployeeId int ,
	ScheduledDate timestamp ,
	LastDate timestamp ,
	Coarse int ,
	PHours float ,
	Fieldhours float ,
	Thours float ,
	Location varchar(200) ,
	Instructor int ,
	Comments varchar(200) ,
	Mark int 
) ;






CREATE TABLE Courses(
	CoarseNo int primary key generated always as identity,
	EngName varchar(200) ,
	RusName varchar(200) ,
	isActive int 
);



CREATE TABLE Crews(
	CrewNo int primary key generated always as identity,
	CrewName varchar(200) ,
	isActive int 
);


CREATE TABLE Departments(
	DeptId int primary key generated always as identity,
	EngName varchar(200) ,
	RusName varchar(200) ,
	isActive int 
);



CREATE TABLE Employees(
	RecID int primary key generated always as identity,
	EmployeeID varchar(20) ,
	RusName varchar(100) ,
	EngName varchar(100) ,
	HireDate timestamp ,
	ReportsTo int ,
	position int ,
	Shiftn int ,
	Department int ,
	Terminated int ,
	SafteyOrin timestamp ,
	Photo bytea ,
	local_A timestamp ,
	local_B timestamp ,
	local_V timestamp ,
	local_G timestamp ,
	local_D timestamp ,
	local_E timestamp ,
	local_E1 timestamp ,
	gos_A timestamp ,
	gos_A1 timestamp ,
	gos_B timestamp ,
	gos_B1 timestamp ,
	gos_C timestamp ,
	gos_C1 timestamp ,
	gos_D timestamp ,
	gos_D1 timestamp ,
	gos_BE timestamp ,
	gos_CE timestamp ,
	gos_C1E timestamp ,
	gos_DE timestamp ,
	gos_D1E timestamp 
) ;





CREATE TABLE Instructor(
	InstructoId int primary key generated always as identity,
	EngName varchar(200) ,
	RusName varchar(200) ,
	isActive int 
);




CREATE TABLE Position(
	positionId int primary key generated always as identity,
	EngName varchar(200) ,
	RusName varchar(200) ,
	isActive int 	
);





CREATE TABLE Qualified(
	RecID  int primary key generated always as identity,
	Equipment int ,
	EmployeeID int ,
	Date timestamp ,
	Experienced int ,
	Qualified int ,
	Approved int ,
	Training int 
);





CREATE TABLE SafetyNames(
	ReviewNo int primary key generated always as identity,
	EngName varchar(200) ,
	RusName varchar(200) ,
	isActive int 
);





CREATE TABLE Supervisors(
	SupervisorId  int primary key generated always as identity,
	EngName varchar(200) ,
	RusName varchar(200) ,
	isActive int 
);




CREATE TABLE TrainingData(
	RecID  int primary key generated always as identity,
	EmployeeID int ,
	Date timestamp ,
	Thours float ,
	Phours float ,
	ExpHours float ,
	Fhours float ,
	mark int ,
	Coarse int ,
	Instructor int 	
);



CREATE TABLE Users(
	UserId  int primary key generated always as identity,
	login varchar(200) ,
	psw varchar(200) ,
	role int,
	isActive int
);


CREATE TABLE Roles(
	RoleId  int primary key generated always as identity,
	RoleName varchar(200) ,
	isActive int
);


CREATE TABLE logs(
	LogId  int primary key generated always as identity,
	date timestamp,
	Userid int,
	LogText varchar(2000) ,
	isActive int
);


