

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


public class TrackedPlaceDao {
	protected ConnectionManager connectionManager;

	private static TrackedPlaceDao instance = null;
	protected TrackedPlaceDao() {
		connectionManager = new ConnectionManager();
	}
	public static TrackedPlaceDao getInstance() {
		if(instance == null) {
			instance = new TrackedPlaceDao();
		}
		return instance;
	}

	public TrackedPlace create(TrackedPlace trackedPlace) throws SQLException {
		String insertTrackedPlace =
			"INSERT INTO TrackedPlace(UserName,Longitude,Latitude) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertTrackedPlace,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, trackedPlace.getUser().getUserName());
			insertStmt.setDouble(2, trackedPlace.getLongitude());
			insertStmt.setDouble(3, trackedPlace.getLatitude());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int trackID = -1;
			if(resultKey.next()) {
				trackID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			trackedPlace.setTrackID(trackID);
			return trackedPlace;
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
	 * Update the content of the TrackedPlace instance.
	 * This runs a UPDATE statement.
	 */
	public TrackedPlace updateTrackedPlace(TrackedPlace trackedPlace, double newLongitude, double newLatitude) throws SQLException {
		String updateTrackedPlace = "UPDATE TrackedPlace SET Longitude=?,Latitude=? WHERE TrackID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateTrackedPlace);
			updateStmt.setDouble(1, newLongitude);
			updateStmt.setDouble(2, newLatitude);
			updateStmt.setInt(3, trackedPlace.getTrackID());
			updateStmt.executeUpdate();

			// Update the trackedPlace param before returning to the caller.
			trackedPlace.setLongitude(newLongitude);
			trackedPlace.setLatitude(newLatitude);
			return trackedPlace;
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
	 * Delete the TrackedPlace instance.
	 * This runs a DELETE statement.
	 */
	public TrackedPlace delete(TrackedPlace trackedPlace) throws SQLException {
		String deleteTrackedPlace = "DELETE FROM TrackedPlace WHERE TrackID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteTrackedPlace);
			deleteStmt.setInt(1, trackedPlace.getTrackID());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the TrackedPlace instance.
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

	public TrackedPlace getTrackedPlaceById(int trackID) throws SQLException {
		String selectTrackedPlace =
			"SELECT TrackID,UserName,Longitude,Latitude " +
			"FROM TrackedPlace " +
			"WHERE TrackID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTrackedPlace);
			selectStmt.setInt(1, trackID);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				int resultTrackID = results.getInt("TrackID");
				String resultUserName = results.getString("UserName");
				double resultLongitude = results.getDouble("Longitude");
				double resultLatitude = results.getDouble("Latitude");
				Users user = usersDao.getUserByUserName(resultUserName);
				TrackedPlace trackedPlace = new TrackedPlace(resultTrackID, user,
					resultLongitude, resultLatitude);
				return trackedPlace;
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
