package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.*;

public class BlocksDao {

	protected ConnectionManager connectionManager;

	private static BlocksDao instance = null;
	protected BlocksDao() {
		connectionManager = new ConnectionManager();
	}
	public static BlocksDao getInstance() {
		if(instance == null) {
			instance = new BlocksDao();
		}
		return instance;
	}
	
	
	public Blocks create(Blocks block) throws SQLException {
		String insertPositions =
			"INSERT INTO Blocks(PositionID,Block) " +
			"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPositions);
			insertStmt.setInt(1, block.getPosition().getPositionID());
			insertStmt.setString(2, block.getBlock());
			insertStmt.executeUpdate();
			
			
			return block;
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
	
	public Blocks delete(Blocks bk) throws SQLException {
		String deleteBlocks = "DELETE FROM Blocks WHERE PositionID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteBlocks);
			deleteStmt.setInt(1, bk.getPosition().getPositionID());
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
	
	public Blocks updateBlocks(Blocks bk, String newbk) throws SQLException {
		String updateBlocks = "UPDATE Blocks SET Block=? WHERE PositionID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateBlocks);
			updateStmt.setString(1, newbk);
			updateStmt.setInt(2, bk.getPosition().getPositionID());
			updateStmt.executeUpdate();

			// Update the position param before returning to the caller.
			bk.setBlock(newbk);
			return bk;
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
	
	public Blocks getBlocksById(int positionID) throws SQLException {
		String selectDistricts =
			"SELECT PositionID,Block " +
			"FROM Blocks " +
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
				String resultDistrict = results.getString("Block");

				Positions position = positionsDao.getPositionsById(resultPositionID);
				Blocks bk = new Blocks(position,resultDistrict);
				return bk;
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
