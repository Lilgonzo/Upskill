create database IF NOT EXISTS UpSkillDB;
use UpSkillDB;

CREATE TABLE IF NOT EXISTS loginInfo(
	user varchar (15) PRIMARY KEY, 
	email varchar(15), 
	pass varchar(15)
    );
