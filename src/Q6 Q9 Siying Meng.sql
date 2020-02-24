#insert some data
LOAD DATA INFILE '/tmp/users.csv' INTO TABLE Users
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES;

INSERT INTO PoliceStations (PoliceStationID,Name, Address1, Address2, Phone, PositionID)
	VALUES (9999, 'Unknown', 'Unknown', 'Unknown', 'Unknown', 1);

#LOAD DATA LOCAL INFILE '/tmp/policestations.csv' INTO TABLE PoliceStations 
#FIELDS TERMINATED BY ',' ENCLOSED BY '"'
#LINES TERMINATED BY '\r\n'
#IGNORE 1 LINES
#(@col2,@col3,@col4,@col5);

LOAD DATA INFILE '/tmp/PoliceSampleData-PM3.csv' INTO TABLE Polices
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES;

LOAD DATA INFILE '/tmp/OfficialUpdatesSampleData-PM3.csv' INTO TABLE OfficialUpdates
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES;


# 6. List the top 10 police officers who have created most official updates after January 2016? 
# (Exclude police officers who are not in the system)@Siying Meng
SELECT Polices.UserName, COUNT(*) AS UpdatesTotal
FROM Polices INNER JOIN OfficialUpdates ON Polices.UserName = OfficialUpdates.UserName
 LEFT OUTER JOIN  (
	SELECT OffenseID, OffenseStartDate
    FROM Offenses  
    WHERE OffenseStartDate > '2016-01-01') AS tmpOffenses
    ON OfficialUpdates.OffenseID = tmpOffenses.OffenseID
 GROUP BY Polices.UserName
 ORDER BY COUNT(*) DESC, Polices.UserName ASC
 LIMIT 10;
 
 # 9. What is the number of offense happen between Jan 2016 to May 2016 @Siying Meng
 
SELECT COUNT(*) AS NumberOfOffenses
FROM Offenses
WHERE OffenseStartDate > '2016-01-01' AND OffenseStartDate < '2016-05-31';


