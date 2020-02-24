LOAD DATA INFILE '/tmp/users.csv' INTO TABLE Users
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(@col1,@col2,@col3,@col4,@col5,@col6)
set UserName=@col1,Password=@col2,FirstName=@col3,LastName=@col4,Email=@col5,Phone=@col6;

LOAD DATA INFILE '/tmp/policestations.csv' INTO TABLE PoliceStations
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(@col1,@col2,@col3,@col4,@col5)
set Name=@col1,PositionId=@col2,Address1=@col3,Address2=@col4,Phone=@col5;

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



