# Legendary PM2

CREATE SCHEMA IF NOT EXISTS SCIC;
USE SCIC;

DROP TABLE IF EXISTS CensusTracts;
DROP TABLE IF EXISTS Districts;
DROP TABLE IF EXISTS Beats;
DROP TABLE IF EXISTS Blocks;


DROP TABLE IF EXISTS Comments;
DROP TABLE IF EXISTS OfficialUpdates;

DROP TABLE IF EXISTS Offenses;
DROP TABLE IF EXISTS Positions;


DROP TABLE IF EXISTS Polices;
DROP TABLE IF EXISTS PoliceStations;
DROP TABLE IF EXISTS CommonUsers;
DROP TABLE IF EXISTS Administrator;
DROP TABLE IF EXISTS TrackedPlace;
DROP TABLE IF EXISTS Users;



CREATE TABLE Users (
    UserName VARCHAR(255) NOT NULL,
    Password VARCHAR(255),
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    Email VARCHAR(255),
    Phone VARCHAR(255),
    CONSTRAINT pk_Users_UserName
        PRIMARY KEY (UserName)
);


CREATE TABLE TrackedPlace (
    TrackID INT AUTO_INCREMENT,
    UserName VARCHAR(255),
    Longitude DECIMAL NOT NULL,
    Latitude DECIMAL NOT NULL,
    CONSTRAINT pk_TrackedPlace_TrackID
        PRIMARY KEY (TrackID),
    CONSTRAINT fk_TrackedPlace_UserName
        FOREIGN KEY (UserName)
        REFERENCES Users(UserName)
        ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Administrator (
    UserName VARCHAR(255) NOT NULL,
    CONSTRAINT pk_Administrator_UserName
        PRIMARY KEY (UserName),
    CONSTRAINT fk_Administrator_UserName
        FOREIGN KEY (UserName)
        REFERENCES Users(UserName)
        ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE CommonUsers (
    UserName VARCHAR(255) NOT NULL,
    CONSTRAINT pk_CommonUsers_UserName
        PRIMARY KEY (UserName),
    CONSTRAINT fk_CommonUsers_UserName
        FOREIGN KEY (UserName)
        REFERENCES Users(UserName)
        ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE PoliceStations (
    PoliceStationID INT AUTO_INCREMENT,
    Name VARCHAR(255),
    Address1 VARCHAR(255),
    Address2 VARCHAR(255),
    Phone VARCHAR(255),
    CONSTRAINT pk_PoliceStations_PoliceStationID
        PRIMARY KEY (PoliceStationID)
);


CREATE TABLE Polices (
    UserName VARCHAR(255) NOT NULL,
    PoliceStationID INT NOT NULL,
    CONSTRAINT pk_Polices_UserName
        PRIMARY KEY (UserName),
    CONSTRAINT fk_Polices_PoliceStationID
        FOREIGN KEY (PoliceStationID)
        REFERENCES PoliceStations(PoliceStationID)
        ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Positions (
    PositionID INT AUTO_INCREMENT,
    Longitude DECIMAL NOT NULL,
    Latitude DECIMAL NOT NULL,
    CONSTRAINT pk_Positions_PositionID
        PRIMARY KEY (PositionID)
);

CREATE TABLE Offenses (
    OffenseID INT AUTO_INCREMENT,
    OffenseType ENUM('VEHICLE THEFT','OTHER PROPERTY','ASSAULT','TRAFFIC','FRAUD','STOLEN PROPERTY','WARRANT ARREST','BIKE THEFT','BURGLARY','THREATS','DISTURBANCE','CAR PROWL','EXTORTION','SHOPLIFTING','PROPERTY DAMAGE','COUNTERFEIT','WEAPON','NARCOTICS','ROBBERY','BURGLARY-SECURE PARKING-RES','TRESPASS','LOST PROPERTY','DUI','INJURY','OBSTRUCT','ELUDING','MAIL THEFT','VIOLATION OF COURT ORDER','HOMICIDE','EMBEZZLE','RECKLESS BURNING','FORGERY','ANIMAL COMPLAINT','THEFT OF SERVICES','DISPUTE','DISORDERLY CONDUCT','PURSE SNATCH','ILLEGAL DUMPING','PROSTITUTION','PICKPOCKET','RECOVERED PROPERTY','LIQUOR VIOLATION','FALSE REPORT','LOITERING','HARBOR CALLs','STAY OUT OF AREA OF DRUGS','FRAUD AND FINANCIAL','PORNOGRAPHY','[INC - CASE DC USE ONLY]','ESCAPE','PUBLIC NUISANCE','FIREWORK','BIAS INCIDENT','STAY OUT OF AREA OF PROSTITUTION','GAMBLE','METRO'),
    ReportDate DATETIME NOT NULL,
    OffenseStartDate DATETIME NOT NULL,
    OffenseEndDate DATETIME DEFAULT NULL,
    PositionID INT NOT NULL,
    UserName VARCHAR(255),
    Authenticated BOOLEAN DEFAULT FALSE,
    Archived BOOLEAN DEFAULT FALSE,
    Severity INT NOT NULL,
    CONSTRAINT pk_Offenses_OffenseID
        PRIMARY KEY (OffenseID),
    CONSTRAINT fk_Positions_PositonID
        FOREIGN KEY (PositionID)
        REFERENCES Positions(PositionID)
        ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE OfficialUpdates (
    OfficialUpdateID INT AUTO_INCREMENT,
    OffenseID INT NOT NULL,
    UserName VARCHAR(255),
    Comment VARCHAR(255),
    Attachment BLOB,
    CONSTRAINT pk_OfficialUpdates_OfficialUpdateID
        PRIMARY KEY (OfficialUpdateID),
    CONSTRAINT fk_OfficialUpdates_OffenceID
        FOREIGN KEY (OffenseID)
        REFERENCES Offenses(OffenseID)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_OfficialUpdates_UserName
        FOREIGN KEY (UserName)
        REFERENCES Polices(UserName)
        ON UPDATE CASCADE ON DELETE SET NULL
);


CREATE TABLE Comments (
    CommentsID INT AUTO_INCREMENT,
    OffenseID INT,
	UserName VARCHAR(255),
    Comment VARCHAR(255),
    Time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_Commnets_CommentsID
        PRIMARY KEY (CommentsID),
    CONSTRAINT fk_Commnets_OffenseID
        FOREIGN KEY (OffenseID)
        REFERENCES Offenses(OffenseID)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_Commnets_UserName
        FOREIGN KEY (UserName)
        REFERENCES Users(UserName)
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Districts (
    PositionID INT AUTO_INCREMENT,
    District VARCHAR(255) NOT NULL,
    CONSTRAINT pk_Districts_PositionID
        PRIMARY KEY (PositionID),
    CONSTRAINT fk_Districts_PositionID
        FOREIGN KEY (PositionID)
        REFERENCES Positions(PositionID)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Beats (
    PositionID INT AUTO_INCREMENT,
    Beat VARCHAR(255) NOT NULL,
    CONSTRAINT pk_Beats_PositionID
        PRIMARY KEY (PositionID),
    CONSTRAINT fk_Beats_PositionID
        FOREIGN KEY (PositionID)
        REFERENCES Positions(PositionID)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Blocks (
    PositionID INT AUTO_INCREMENT,
    Block  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_Blocks_PositionID
        PRIMARY KEY (PositionID),
    CONSTRAINT fk_Blocks_PositionID
        FOREIGN KEY (PositionID)
        REFERENCES Positions(PositionID)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE CensusTracts (
    PositionID INT AUTO_INCREMENT,
    CensusTract DECIMAL (10,0),
    CONSTRAINT pk_CensusTracts_PositionID
        PRIMARY KEY (PositionID),
    CONSTRAINT fk_CensusTracts_PositionID
        FOREIGN KEY (PositionID)
        REFERENCES Positions(PositionID)
        ON UPDATE CASCADE ON DELETE CASCADE
);

LOAD DATA INFILE 'C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\SPD_Reports_PositionId_NEWTIME.csv' INTO TABLE Positions
  FIELDS TERMINATED BY ',' ENCLOSED BY '"'
  LINES TERMINATED BY '\r\n'
  IGNORE 1 LINES
  (@col1,@col2,@col3,@col4,@col5,@col6,@col7,@col8,@col9,@col10,@col11,@col12,@col13) set Longitude=@col10,Latitude=@col11;
  

  LOAD DATA INFILE 'C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\SPD_Reports_PositionId_NEWTIME.csv' INTO TABLE Blocks
  FIELDS TERMINATED BY ',' ENCLOSED BY '"'
  LINES TERMINATED BY '\r\n'
  IGNORE 1 LINES
  (@col1,@col2,@col3,@col4,@col5,@col6,@col7,@col8,@col9,@col10,@col11,@col12,@col13) set Blocks=@col6;
  
  LOAD DATA INFILE 'C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\SPD_Reports_PositionId_NEWTIME.csv' INTO TABLE Districts
  FIELDS TERMINATED BY ',' ENCLOSED BY '"'
  LINES TERMINATED BY '\r\n'
  IGNORE 1 LINES
  (@col1,@col2,@col3,@col4,@col5,@col6,@col7,@col8,@col9,@col10,@col11,@col12,@col13) set Districts=@col7;
  
  LOAD DATA INFILE 'C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\SPD_Reports_PositionId_NEWTIME.csv' INTO TABLE Beats
  FIELDS TERMINATED BY ',' ENCLOSED BY '"'
  LINES TERMINATED BY '\r\n'
  IGNORE 1 LINES
  (@col1,@col2,@col3,@col4,@col5,@col6,@col7,@col8,@col9,@col10,@col11,@col12,@col13) set Beats=@col8;
  
  LOAD DATA INFILE 'C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\SPD_Reports_PositionId_NEWTIME.csv' INTO TABLE Censustracts
  FIELDS TERMINATED BY ',' ENCLOSED BY '"'
  LINES TERMINATED BY '\r\n'
  IGNORE 1 LINES
  (@col1,@col2,@col3,@col4,@col5,@col6,@col7,@col8,@col9,@col10,@col11,@col12,@col13) set Censustracts=@col9;

 LOAD DATA INFILE 'C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\SPD_Reports_PositionId_NEWTIME.csv' INTO TABLE Offenses
  FIELDS TERMINATED BY ',' ENCLOSED BY '"'
  LINES TERMINATED BY '\r\n'
  IGNORE 1 LINES
  (@col1,@col2,@col3,@col4,@col5,@col6,@col7,@col8,@col9,@col10,@col11,@col12,@col13) set OffenseType=@col2, ReportDate=@col3, OffenseStartDate=@col4, OffenseEndDate=@col13,PositionId=@col12;
  

LOAD DATA INFILE 'C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Uploads\\SPD_Reports_PositionId_NEWTIME.csv' INTO TABLE  TrackedPlace
  FIELDS TERMINATED BY ',' ENCLOSED BY '"'
  LINES TERMINATED BY '\r\n'
  IGNORE 1 LINES
  (@col1,@col2,@col3,@col4,@col5,@col6,@col7,@col8,@col9,@col10,@col11,@col12,@col13) set Longitude=@col10,Latitude=@col11;











