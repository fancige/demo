CREATE DATABASE stums;
USE stums;

CREATE TABLE student (
	stuId VARCHAR(15) KEY NOT NULL,
	stuName VARCHAR(30) NOT NULL,
	stuSex ENUM ('男','女') NOT NULL,
	stuAge char(7) NOT NULL,
	major VARCHAR(30) NOT NULL,
	minor VARCHAR(30),
	politicsStatus VARCHAR(30),
	stuAddress VARCHAR(50),
	enterTime VARCHAR(30) NOT NULL,
	deptId VARCHAR(15) NOT NULL,
	dormId VARCHAR(15) NOT NULL,
	classId VARCHAR(15) NOT NULL
);

CREATE TABLE teacher (
	tId VARCHAR(15) KEY NOT NULL,
	tName VARCHAR(30) NOT NULL,
	tel VARCHAR(50),
	position VARCHAR(50) NOT NULL
);

CREATE TABLE course (
	courId VARCHAR(15) KEY NOT NULL,
	courName VARCHAR(30) NOT NULL,
	courType VARCHAR(30),
	credit CHAR(7) NOT NULL,
	totalGrade CHAR(7)
);

CREATE TABLE dormitory (
	dormId CHAR(7) KEY NOT NULL,
	dormAddress VARCHAR(50),
	accessNum CHAR(7),
	hasNum CHAR(7)
);

CREATE TABLE department (
	departId VARCHAR(15) KEY NOT NULL,
	departName VARCHAR(50),
	departHead VARCHAR(50) NOT NULL
);

CREATE TABLE class (
	classId VARCHAR(15) KEY NOT NULL,
	className VARCHAR(50) NOT NULL,
	num VARCHAR(9)
);

CREATE TABLE sc (
	stuId VARCHAR(15) NOT NULL,
	courId VARCHAR(30) NOT NULL,
	getGredit CHAR(7) NOT NULL,
	getGarde CHAR(7) NOT NULL
);

CREATE TABLE userlist (
	username VARCHAR(15) KEY NOT NULL,
	password VARCHAR(30) DEFAULT '123' NOT NULL,
	authority ENUM ('admin', 'teacher', 'student') NOT NULL
);
INSERT INTO userlist VALUES ('admin', 'admin', 'admin');
