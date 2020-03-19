package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.CensusTracts;
import model.Positions;

public class CensusTractsDao {
	protected ConnectionManager connectionManager;

	private static CensusTractsDao instance = null;
	protected CensusTractsDao() {
		connectionManager = new ConnectionManager();
	}
	public static CensusTractsDao getInstance() {
		if(instance == null) {
			instance = new CensusTractsDao();
		}
		return instance;
	}
	
	public CensusTracts create(CensusTracts census) throws SQLException {
		String insertBeats =
			"INSERT INTO CensusTracts(PositionID,CensusTract) " +
			"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertBeats);
			insertStmt.setInt(1, census.getPosition().getPositionID());
			insertStmt.setDouble(2, census.getCensus());
			insertStmt.executeUpdate();
			
			
			return census;
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
	
	public CensusTracts delete(CensusTracts census) throws SQLException {
		String deleteDistricts = "DELETE FROM CensusTracts WHERE PositionID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteDistricts);
			deleteStmt.setInt(1, census.getPosition().getPositionID());
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
	
	public CensusTracts updateCensusTracts(CensusTracts census, Double newcensus) throws SQLException {
		String updatePositions = "UPDATE CensusTracts SET CensusTract=? WHERE PositionID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePositions);
			updateStmt.setDouble(1, newcensus);
			updateStmt.setInt(2, census.getPosition().getPositionID());
			updateStmt.executeUpdate();

			// Update the position param before returning to the caller.
			census.setCensus(newcensus);;
			return census;
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
	
	public CensusTracts getCensusTractsById(int positionID) throws SQLException {
		String selectDistricts =
			"SELECT PositionID,CensusTract " +
			"FROM CensusTracts " +
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
				Double resultDistrict = results.getDouble("CensusTract");

				Positions position = positionsDao.getPositionsById(resultPositionID);
				CensusTracts bt = new CensusTracts(position,resultDistrict);
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
