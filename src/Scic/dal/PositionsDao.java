Positions.java/PositionsDao.java:
Positions create(Positions position)
Positions getPositionById(Integer positionId)
Positions updatePosition(Positions position, Double longitude, Double latitude)
Positions delete(Positions position)



package Scic.dal;

import Scic.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PositionsDao {
	protected ConnectionManager connectionManager;

	private static PositionsDao instance = null;
	protected PositionsDao() {
		connectionManager = new ConnectionManager();
	}
	public static PositionsDao getInstance() {
		if(instance == null) {
			instance = new PositionsDao();
		}
		return instance;
	}

	public Positions create(Positions position) throws SQLException {
		String insertPositions =
			"INSERT INTO Positions(Longitude,Latitude) " +
			"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPositions,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setDouble(1, position.getLongitude());
			insertStmt.setDouble(2, position.getLatitude());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int positionID = -1;
			if(resultKey.next()) {
				positionID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			position.setPositionID(positionID);
			return position;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}

	/**
	 * Update the content of the Positions instance.
	 * This runs a UPDATE statement.
	 */

	public Positions updatePosition(Positions position, double newLongitude, double newLatitude) throws SQLException {
		String updatePositions = "UPDATE Positions SET Longitude=?,Latitude=? WHERE PositionID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePositions);
			updateStmt.setDouble(1, newLongitude);
			updateStmt.setDouble(2, newLatitude);
			updateStmt.setInt(3, position.getPositionID());
			updateStmt.executeUpdate();

			// Update the position param before returning to the caller.
			position.setLongitude(newLongitude);
			position.setLatitude(newLatitude);
			return position;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}

	/** 
	 * Delete the Positions instance.
	 * This runs a DELETE statement.
	 */
	public Positions delete(Positions position) throws SQLException {
		String deletePositions = "DELETE FROM Positions WHERE PositionID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePositions);
			deleteStmt.setInt(1, position.getPositionID());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Positions instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}

	public Positions getPositionsById(int positionID) throws SQLException {
		String selectPositions =
			"SELECT PositionID,Longitude,Latitude " +
			"FROM Positions " +
			"WHERE PositionID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPositions);
			selectStmt.setInt(1, positionID);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				int resultPositionID = results.getInt("PositionID");
				double resultLongitude = results.getDouble("Longitude");
				double resultLatitude = results.getDouble("Latitude");
				Positions position = new Positions(resultPositionID, 
					resultLongitude, resultLatitude);
				return position;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
}
