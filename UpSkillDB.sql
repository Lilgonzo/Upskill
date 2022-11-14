create database IF NOT EXISTS UpSkillDB;
use UpSkillDB;

CREATE TABLE IF NOT EXISTS loginInfo(
	userID varchar(15) PRIMARY KEY,
	user varchar (15), 
	email varchar(15), 
	pass varchar(15)
    );
    
CREATE TABLE IF NOT EXISTS profileInfo(
	userID varchar(15) PRIMARY KEY,
    bio varchar(15),
    interests varchar(15),
    dislikes varchar(15)
    );
    
CREATE TABLE IF NOT EXISTS likes(
	userID varchar(15) PRIMARY KEY,
	likedUserID varchar(15),
    likeDislike boolean
    );

CREATE TABLE IF NOT EXISTS messages(
	userID varchar(15) PRIMARY KEY,
    userIDRecipient varchar(15),
    message varchar(180)
    );
    