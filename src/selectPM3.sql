USE SCIC;

# Kobe
LOAD DATA INFILE '/Users/hantaoliu/Projects/SCIC/data/users.csv' INTO TABLE Users
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(@col1,@col2,@col3,@col4,@col5,@col6)
set UserName=@col1,Password=@col2,FirstName=@col3,LastName=@col4,Email=@col5,Phone=@col6;

LOAD DATA INFILE '/Users/hantaoliu/Projects/SCIC/data/policestations.csv' INTO TABLE PoliceStations
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(@col1,@col2,@col3,@col4,@col5)
set Name=@col1,PositionId=@col2,Address1=@col3,Address2=@col4,Phone=@col5;

LOAD DATA INFILE '/Users/hantaoliu/Projects/SCIC/data/PoliceSampleData-PM3.csv' INTO TABLE Polices
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(@col1,@col2)
set UserName=@col1,PoliceStationID=@col2;

LOAD DATA INFILE '/Users/hantaoliu/Projects/SCIC/data/OfficialUpdatesSampleData-PM3.csv' INTO TABLE OfficialUpdates
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(@col1,@col2,@col3)
set OffenseID=@col1,UserName=@col2,Comment=@col3;

SET SQL_SAFE_UPDATES = 0;

UPDATE scic.offenses
SET UserName = 'bruce'
WHERE UserName IS NULL AND RAND()*680000 < 2000;

UPDATE scic.offenses
SET UserName = 'kevin'
WHERE UserName IS NULL AND RAND()*680000 < 2000;

UPDATE scic.offenses
SET UserName = 'foo'
WHERE UserName IS NULL AND RAND()*680000 < 3000;

UPDATE scic.offenses
SET UserName = 'bar'
WHERE UserName IS NULL AND RAND()*680000 < 4000;

UPDATE scic.offenses
SET UserName = 'faz'
WHERE UserName IS NULL AND RAND()*680000 < 1500;

UPDATE scic.offenses
SET UserName = 'daz'
WHERE UserName IS NULL AND RAND()*680000 < 3000;

UPDATE scic.offenses
SET UserName = 'gez'
WHERE UserName IS NULL AND RAND()*680000 < 2000;

UPDATE scic.offenses
SET UserName = 'bob'
WHERE UserName IS NULL AND RAND()*680000 < 2500;

UPDATE scic.offenses
SET UserName = 'james'
WHERE UserName IS NULL AND RAND()*680000 < 2500;

UPDATE scic.offenses
SET UserName = 'danny'
WHERE UserName IS NULL AND RAND()*680000 < 2000;



UPDATE scic.offenses
SET UserName = 'tom', Authenticated = false
WHERE UserName IS NULL AND RAND()*680000 < 50;

UPDATE scic.offenses
SET UserName = 'tom', Authenticated = true
WHERE UserName IS NULL AND RAND()*680000 < 10;

UPDATE scic.offenses
SET UserName = 'lisa', Authenticated = false
WHERE UserName IS NULL AND RAND()*680000 < 40;

UPDATE scic.offenses
SET UserName = 'lisa', Authenticated = true
WHERE UserName IS NULL AND RAND()*680000 < 10;

UPDATE scic.offenses
SET Authenticated = true
WHERE Authenticated = false AND RAND()*680000 < 650000;

SET SQL_SAFE_UPDATES = 1;

# Lucas
#insert comment data
LOAD DATA INFILE '/Users/hantaoliu/Projects/SCIC/data/comments.csv' INTO TABLE comments
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(@col1,@col2,@col3)
set OffenseID=@col1,UserName=@col2,Comment=@col3;

# 1 Find the top 10 risky blocks.
use scic;
SELECT Blocks.block, Count(*) AS CNT
FROM blocks INNER JOIN positions
ON blocks.PositionId = positions.PositionId
INNER JOIN offenses
ON positions.PositionId = offenses.PositionId
GROUP BY blocks.Block
ORDER BY Count(*) DESC
LIMIT 10;

# 2. Find the top 10 districts where offense(theft) often happen
SELECT D.District, COUNT(*) AS CNT
FROM Districts AS D INNER JOIN Offenses AS O ON D.PositionID = O.PositionID
WHERE O.OffenseType IN ('BIKE THEFT','MAIL THEFT','VEHICLE THEFT','THEFT OF SERVICE')
GROUP BY D.District
ORDER BY CNT DESC
LIMIT 10;

# 3. Find the busiest police station(district that has police station as well as having for the most number of crimes) 
SELECT Ps_Dis.Name, COUNT(*) AS CNT
FROM (
	SELECT PS.PositionID, D.District, PS.Name
  FROM Districts AS D INNER JOIN PoliceStations AS PS ON D.PositionID = PS.PositionID
) AS Ps_Dis LEFT OUTER JOIN ( # Join offense and district
	SELECT D.PositionID, D.District
  FROM Districts AS D INNER JOIN Offenses AS O ON D.PositionID = O.PositionID
) AS Off_Dis ON Ps_Dis.District = Off_Dis.District
GROUP BY Ps_Dis.Name
ORDER BY CNT DESC
LIMIT 1;

# 4. Find the top 10 most types of offense 
SELECT offensetype, count(*) AS TypeCnt
FROM offenses
GROUP BY offensetype
ORDER BY Typecnt DESC
LIMIT 10;

# 5. Calculate the average amount of official updates per offense.
SELECT UPDATECOUNT / OFFENSECOUNT AS RES
  FROM (SELECT
  (SELECT COUNT(*)
  FROM OfficialUpdates) 
  AS UPDATECOUNT,
  (SELECT COUNT(*)
  FROM Offenses) 
  AS OFFENSECOUNT) AS res;
  
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
 
# 7. Find the top 10 police stations who report the most offenses.
SELECT PoliceStations.Name, COUNT(*) AS OffenseCount
FROM PoliceStations INNER JOIN Polices ON PoliceStations.PoliceStationId = Polices.PoliceStationId
	INNER JOIN Offenses ON Polices.UserName = Offenses.UserName
GROUP BY PoliceStations.Name
ORDER BY OffenseCount DESC
LIMIT 10;

# 8. Find the user who reports the most unauthenticated offenses. 
SELECT UserName, COUNT(*) AS CountOfFakePosts
FROM scic.Offenses
WHERE Authenticated = false AND UserName IS NOT NULL
GROUP BY UserName
ORDER BY CountOfFakePosts DESC
LIMIT 1;
 
# 9. What is the number of offense happen between Jan 2016 to May 2016 @Siying Meng
SELECT COUNT(*) AS NumberOfOffenses
FROM Offenses
WHERE OffenseStartDate > '2016-01-01' AND OffenseStartDate < '2016-05-31';

# 10. Calculate the ratio of weekend offenses  over the  week offenses.
SELECT (weekend/week) AS RatioForWeekendOfWeek
FROM
(SELECT sum(cnt) as weekend
FROM(
SELECT dayofweek(reportdate) AS weekday, count(*) AS CNT
FROM offenses
WHERE dayofweek(reportdate)=6 OR dayofweek(reportdate) =7
GROUP BY weekday)AS table1
) AS final1
CROSS JOIN
(
SELECT sum(CNT) AS week
FROM(
SELECT dayofweek(reportdate) AS weekday, count(*) AS CNT
FROM offenses
GROUP BY weekday
) AS ta
) AS final2;

# 11. Find the weapon offense with most comment
SELECT 'WEAPON' AS OffenseType, offenses.offenseId, COUNT(*) AS CommentCNT
FROM offenses
INNER JOIN comments
ON offenses.OffenseID = comments.OffenseID
WHERE offenses.OffenseType = 'WEAPON'
GROUP BY OffenseID
ORDER BY CommentCNT DESC
LIMIT 1;

# 12. What is the ratio of offense(weapon) over offense(robbery) happen on the block name start with 5
SELECT (weaponcnt / robberycnt) AS ratio
FROM
(SELECT COUNT(*) AS robberycnt
FROM offenses
INNER JOIN blocks
ON offenses.PositionID = blocks.PositionID
WHERE SUBSTRING(blocks.Block, 1,1) = 5
GROUP BY OffenseType
HAVING offenses.OffenseType = 'ROBBERY') AS robberytb
CROSS JOIN
(SELECT COUNT(*) AS weaponcnt
FROM offenses
INNER JOIN blocks
ON offenses.PositionID = blocks.PositionID
WHERE SUBSTRING(blocks.Block, 1,1) = 5
GROUP BY OffenseType
HAVING offenses.OffenseType = 'WEAPON') AS weapontb;



