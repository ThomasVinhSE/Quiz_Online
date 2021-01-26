use master
drop database Assignment2_NguyenQuocVinh
create database Assignment2_NguyenQuocVinh
use Assignment2_NguyenQuocVinh

create table tbl_Role
(
	roleId int primary key,
	name varchar(50) not null,
	description varchar(500)
)

create table tbl_Account
(
	email varchar(100) not null primary key,
	name varchar(50) not null,
	password varchar(64) not null,
	roleId int not null foreign key references tbl_Role(roleId),
	status varchar(10) default 'New'
)

create table tbl_Subject
(
	subjectId int identity(1,1) primary key,
	name varchar(20) not null,
	numOfQuestion int not null,
	timeForQuiz int not null
)

create table tbl_Question
(
	questionId int identity(1,1) primary key,
	question_content varchar(100) not null,
	createDate datetime default getdate(),
	subjectId int foreign key references tbl_Subject(subjectId),
	point float not null default 0,
	status bit default 1
)
create table tbl_Choice
(
	choiceId int identity(1,1) primary key,
	questionId int foreign key references tbl_Question(questionId),
	choice_content varchar(100) not null,
	isCorrect bit default 0
)

create table tbl_History
(
	historyId int identity(1,1) primary key,
	email varchar(100) foreign key references tbl_Account(email),
	subjectId int foreign key references tbl_Subject(subjectId),
	startTime datetime,
	endTime datetime,
	numOfCorrect int,
	numOfInCorrect int,
	total float
)



