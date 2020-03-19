package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.*;

public class DistrictsDao {
	protected ConnectionManager connectionManager;

	private static DistrictsDao instance = null;
	protected DistrictsDao() {
		connectionManager = new ConnectionManager();
	}
	public static DistrictsDao getInstance() {
		if(instance == null) {
			instance = new DistrictsDao();
		}
		return instance;
	}
	
	public Districts create(Districts dist) throws SQLException {
		String insertDistricts =
			"INSERT INTO Districts(PositionID,District) " +
			"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertDistricts);
			insertStmt.setInt(1, dist.getPosition().getPositionID());
			insertStmt.setString(2, dist.getDistrict());
			insertStmt.executeUpdate();
			
			
			return dist;
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
	
	public Districts delete(Districts dist) throws SQLException {
		String deleteDistricts = "DELETE FROM Districts WHERE PositionID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteDistricts);
			deleteStmt.setInt(1, dist.getPosition().getPositionID());
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
	
	public Districts updateDistricts(Districts dist, String newDist) throws SQLException {
		String updatePositions = "UPDATE Districts SET District=? WHERE PositionID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePositions);
			updateStmt.setString(1, newDist);
			updateStmt.setInt(2, dist.getPosition().getPositionID());
			updateStmt.executeUpdate();

			// Update the position param before returning to the caller.
			dist.setDistrict(newDist);
			return dist;
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
	
	public Districts getDistrictsById(int positionID) throws SQLException {
		String selectDistricts =
			"SELECT PositionID,District " +
			"FROM Districts " +
			"WHERE PositionID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectDistricts);
			selectStmt.setInt(1, positionID);
			results = selectStmt.executeQuery();
			//UsersDao usersDao = UsersDao.getInstance();
			PositionsDao positionsDao = PositionsDao.getInstance();
			if(results.next()) {
				int resultPositionID = results.getInt("PositionID");
				String resultDistrict = results.getString("District");

				Positions position = positionsDao.getPositionsById(resultPositionID);
				Districts dist = new Districts(position,resultDistrict);
				return dist;
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
