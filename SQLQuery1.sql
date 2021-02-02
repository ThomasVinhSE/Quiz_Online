
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
INSERT INTO [dbo].[tbl_Role]
           ([roleId]
           ,[name]
           ,[description])
     VALUES
           (1
           ,'student'
           ,'take quiz')
GO
INSERT INTO [dbo].[tbl_Role]
           ([roleId]
           ,[name]
           ,[description])
     VALUES
           (0
           ,'admin'
           ,'full control')
GO



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

create table Detail_History
(
	historyId int foreign key references tbl_History(historyId),
	choiceId int null foreign key references tbl_Choice(choiceId),
	questionId int foreign key references tbl_Question(questionId),
	primary key (historyId,questionId)
)



INSERT INTO [dbo].[tbl_Subject]
           ([name]
           ,[numOfQuestion]
           ,[timeForQuiz])
     VALUES
           ('PRJ311'
           ,5
           ,1)
GO
INSERT INTO [dbo].[tbl_Subject]
           ([name]
           ,[numOfQuestion]
           ,[timeForQuiz])
     VALUES
           ('PDP200'
           ,5
           ,5)
GO
INSERT INTO [dbo].[tbl_Subject]
           ([name]
           ,[numOfQuestion]
           ,[timeForQuiz])
     VALUES
           ('LAB301'
           ,5
           ,10)
GO

INSERT INTO [dbo].[tbl_Question]
           ([question_content]
           ,[createDate]
           ,[subjectId]
           ,[point]
           ,[status])
     VALUES
           ('PRJ-1',
           GETDATE()+5
           ,1
           ,5
           ,1)
GO
INSERT INTO [dbo].[tbl_Question]
           ([question_content]
           ,[createDate]
           ,[subjectId]
           ,[point]
           ,[status])
     VALUES
           ('PRJ-2',
           GETDATE()+4
           ,1
           ,5
           ,1)
GO
INSERT INTO [dbo].[tbl_Question]
           ([question_content]
           ,[createDate]
           ,[subjectId]
           ,[point]
           ,[status])
     VALUES
           ('PRJ-3',
           GETDATE()+1
           ,1
           ,5
           ,1)
GO
INSERT INTO [dbo].[tbl_Question]
           ([question_content]
           ,[createDate]
           ,[subjectId]
           ,[point]
           ,[status])
     VALUES
           ('PRJ-4',
           GETDATE()+2
           ,1
           ,5
           ,1)
GO
INSERT INTO [dbo].[tbl_Question]
           ([question_content]
           ,[createDate]
           ,[subjectId]
           ,[point]
           ,[status])
     VALUES
           ('PRJ-5',
           GETDATE()+3
           ,1
           ,5
           ,1)
GO

INSERT INTO [dbo].[tbl_Question]
           ([question_content]
           ,[createDate]
           ,[subjectId]
           ,[point]
           ,[status])
     VALUES
           ('PDP-1',
           GETDATE()+1
           ,2
           ,5
           ,1)
GO

INSERT INTO [dbo].[tbl_Question]
           ([question_content]
           ,[createDate]
           ,[subjectId]
           ,[point]
           ,[status])
     VALUES
           ('PDP-2',
           GETDATE()+2
           ,2
           ,5
           ,1)
GO
INSERT INTO [dbo].[tbl_Question]
           ([question_content]
           ,[createDate]
           ,[subjectId]
           ,[point]
           ,[status])
     VALUES
           ('PDP-3',
          GETDATE()+3
           ,2
           ,5
           ,1)
GO
INSERT INTO [dbo].[tbl_Question]
           ([question_content]
           ,[createDate]
           ,[subjectId]
           ,[point]
           ,[status])
     VALUES
           ('PDP-4',
           GETDATE()+4
           ,2
           ,5
           ,1)
GO
INSERT INTO [dbo].[tbl_Question]
           ([question_content]
           ,[createDate]
           ,[subjectId]
           ,[point]
           ,[status])
     VALUES
           ('PDP-5',
           GETDATE()+5
           ,2
           ,5
           ,1)
GO

INSERT INTO [dbo].[tbl_Question]
           ([question_content]
           ,[createDate]
           ,[subjectId]
           ,[point]
           ,[status])
     VALUES
           ('LAB-1',
           GETDATE()+1
           ,3
           ,5
           ,1)
GO

INSERT INTO [dbo].[tbl_Question]
           ([question_content]
           ,[createDate]
           ,[subjectId]
           ,[point]
           ,[status])
     VALUES
           ('LAB-2',
           GETDATE()+2
           ,3
           ,5
           ,1)
GO
INSERT INTO [dbo].[tbl_Question]
           ([question_content]
           ,[createDate]
           ,[subjectId]
           ,[point]
           ,[status])
     VALUES
           ('LAB-3',
           GETDATE()+3
           ,3
           ,5
           ,1)
GO
INSERT INTO [dbo].[tbl_Question]
           ([question_content]
           ,[createDate]
           ,[subjectId]
           ,[point]
           ,[status])
     VALUES
           ('LAB-4',
           GETDATE()+4
           ,3
           ,5
           ,1)
GO
INSERT INTO [dbo].[tbl_Question]
           ([question_content]
           ,[createDate]
           ,[subjectId]
           ,[point]
           ,[status])
     VALUES
           ('LAB-5',
           GETDATE()+5
           ,3
           ,5
           ,1)
GO
---------------------------------
SET IDENTITY_INSERT [dbo].[tbl_Choice] ON 
INSERT INTO [dbo].[tbl_Choice]
           ([choiceId],[questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (78
           ,null
           ,'',0)
GO
SET IDENTITY_INSERT [dbo].[tbl_Choice] OFF 
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (1
           ,'a'
           ,1)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (1
           ,'b'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (1
           ,'c'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (1
           ,'d'
           ,0)
GO

INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (2
           ,'a'
           ,1)
GO

INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (2
           ,'b'
           ,0)
GO

INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (2
           ,'c'
           ,0)
GO

INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (2
           ,'d'
           ,0)
GO

INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (3
           ,'a'
           ,1)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (4
           ,'a'
           ,1)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (5
           ,'a'
           ,1)
GO

INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (3
           ,'b'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (4
           ,'b'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (5
           ,'b'
           ,0)
GO

INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (3
           ,'c'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (4
           ,'c'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (5
           ,'c'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (3
           ,'d'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (4
           ,'d'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (5
           ,'d'
           ,0)
GO

INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (6
           ,'a'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (7
           ,'a'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (8
           ,'a'
           ,0)
GO

INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (6
           ,'b'
           ,1)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (7
           ,'b'
           ,1)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (8
           ,'b'
           ,1)
GO

INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (6
           ,'c'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (7
           ,'c'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (8
           ,'c'
           ,0)
GO

INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (6
           ,'d'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (7
           ,'d'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (8
           ,'d'
           ,0)
GO

INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (9
           ,'a'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (10
           ,'a'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (9
           ,'b'
           ,1)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (10
           ,'b'
           ,1)
GO

INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (9
           ,'c'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (10
           ,'c'
           ,0)
GO

INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (9
           ,'d'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (10
           ,'d'
           ,0)
GO
------
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (11
           ,'a'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (12
           ,'a'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (13
           ,'a'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (14
           ,'a'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (15
           ,'a'
           ,0)
GO

INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (11
           ,'b'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (12
           ,'b'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (13
           ,'b'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (14
           ,'b'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (15
           ,'b'
           ,0)
GO

INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (11
           ,'c'
           ,1)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (12
           ,'c'
           ,1)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (13
           ,'c'
           ,1)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (14
           ,'c'
           ,1)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (15
           ,'c'
           ,1)
GO

INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (11
           ,'d'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (12
           ,'d'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (13
           ,'d'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (14
           ,'d'
           ,0)
GO
INSERT INTO [dbo].[tbl_Choice]
           ([questionId]
           ,[choice_content]
           ,[isCorrect])
     VALUES
           (15
           ,'d'
           ,0)
GO