package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Beats;

import model.Positions;

public class BeatsDao {
	protected ConnectionManager connectionManager;

	private static BeatsDao instance = null;
	protected BeatsDao() {
		connectionManager = new ConnectionManager();
	}
	public static BeatsDao getInstance() {
		if(instance == null) {
			instance = new BeatsDao();
		}
		return instance;
	}
	
	public Beats create(Beats bt) throws SQLException {
		String insertBeats =
			"INSERT INTO Beats(PositionID,Beat) " +
			"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertBeats);
			insertStmt.setInt(1, bt.getPosition().getPositionID());
			insertStmt.setString(2, bt.getBeat());
			insertStmt.executeUpdate();
			
			
			return bt;
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
	
	public Beats delete(Beats bt) throws SQLException {
		String deleteDistricts = "DELETE FROM Beats WHERE PositionID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteDistricts);
			deleteStmt.setInt(1, bt.getPosition().getPositionID());
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
	
	public Beats updateBeats(Beats bt, String newbt) throws SQLException {
		String updatePositions = "UPDATE Beats SET Beat=? WHERE PositionID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePositions);
			updateStmt.setString(1, newbt);
			updateStmt.setInt(2, bt.getPosition().getPositionID());
			updateStmt.executeUpdate();

			// Update the position param before returning to the caller.
			bt.setBeat(newbt);;
			return bt;
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
	
	public Beats getBeatsById(int positionID) throws SQLException {
		String selectDistricts =
			"SELECT PositionID,Beat " +
			"FROM Beats " +
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
				String resultDistrict = results.getString("Beat");

				Positions position = positionsDao.getPositionsById(resultPositionID);
				Beats bt = new Beats(position,resultDistrict);
				return bt;
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
