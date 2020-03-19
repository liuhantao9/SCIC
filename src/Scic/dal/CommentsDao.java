package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;


import model.Comments;
import model.Offenses;
import model.Users;

public class CommentsDao {
	protected ConnectionManager connectionManager;

	private static CommentsDao instance = null;
	protected CommentsDao() {
		connectionManager = new ConnectionManager();
	}
	public static CommentsDao getInstance() {
		if(instance == null) {
			instance = new CommentsDao();
		}
		return instance;
	}
	
	public Comments create(Comments cm) throws SQLException {
		String insertBeats =
			"INSERT INTO Comments(OffenseID,UserName,Comment,Time) " +
			"VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertBeats,
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, cm.getOffense().getOffenseId());
			insertStmt.setString(2, cm.getUser().getUserName());
			insertStmt.setString(3,cm.getComment());
			insertStmt.setTimestamp(4,cm.getTime());
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			int id = -1;
			if(resultKey.next()) {
				id = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			cm.setCommentId(id);
			return cm;

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
	
	public Comments delete(Comments cm) throws SQLException {
		String deleteReshare = "DELETE FROM Comments WHERE CommentsID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReshare);
			deleteStmt.setInt(1, cm.getCommentId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Persons instance.
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
	
	public Comments updateComments(Comments cm, String newcm) throws SQLException {
		String updatePositions = "UPDATE Comments SET Comment=? WHERE CommentsID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePositions);
			updateStmt.setString(1, newcm);
			updateStmt.setInt(2, cm.getCommentId());
			updateStmt.executeUpdate();

			// Update the position param before returning to the caller.
			cm.setComment(newcm);
			return cm;
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
	
	public Comments  getCommentsById(int commentID) throws SQLException {
		String selectDistricts =
			"SELECT CommentsID,OffenseID,UserName,Comment,Time " +
			"FROM Comments " +
			"WHERE CommentsID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectDistricts);
			selectStmt.setInt(1, commentID );
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			OffensesDao offensesDao = OffensesDao.getInstance();
			
			if(results.next()) {
				int resultID = results.getInt("CommentsID");
				int resultOffenseId = results.getInt("OffenseID");
				String resultUserName = results.getString("UserName");
				String resultComment = results.getString("Comment");
				Timestamp resulttime = results.getTimestamp("Time");

				Users user = usersDao.getUserByUserName(resultUserName);
				Offenses offense = offensesDao.getOffensesByOffenseId(resultOffenseId);
				Comments cm = new Comments(resultID,offense,user,resultComment,resulttime);
				return cm;
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
