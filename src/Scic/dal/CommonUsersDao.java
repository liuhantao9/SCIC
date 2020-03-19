
package Scic.dal;

import Scic.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Data access object (DAO) class to interact with the underlying CommonUsers table in your
 * MySQL instance. This is used to store {@link CommonUsers} into your MySQL instance and 
 * retrieve {@link CommonUsers} from MySQL instance.
 */
public class CommonUsersDao extends UsersDao {
	// Single pattern: instantiation is limited to one object.
	private static CommonUsersDao instance = null;
	protected CommonUsersDao() {
		super();
	}
	public static CommonUsersDao getInstance() {
		if(instance == null) {
			instance = new CommonUsersDao();
		}
		return instance;
	}

	public CommonUsers create(CommonUsers commonUsers) throws SQLException {
		// Insert into the superclass table first.
		create(new Users(commonUsers.getUserName(), commonUsers.getPassword(), commonUsers.getFirstName(),
			commonUsers.getLastName(), commonUsers.getEmail(), commonUsers.getPhone()));

		String insertCommonUsers = "INSERT INTO CommonUsers(UserName) VALUES(?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCommonUsers);
			insertStmt.setString(1, commonUsers.getUserName());
			insertStmt.executeUpdate();
			return commonUsers;
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
		}
	}

	/**  
	 * Delete the CommonUsers instance.
	 * This runs a DELETE statement.
	 */
	public CommonUsers delete(CommonUsers commonUsers) throws SQLException {
		String deleteCommonUsers = "DELETE FROM CommonUsers WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCommonUsers);
			deleteStmt.setString(1, commonUsers.getUserName());
			deleteStmt.executeUpdate();
			super.delete(commonUsers);

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

	public CommonUsers getCommonUsersByUsername(String username) throws SQLException {
		// To build an CommonUsers object, we need the Users record, too.
		String selectCommonUsers =
			"SELECT CommonUsers.UserName AS UserName,Password,FirstName,LastName,Email,Phone " +
			"FROM CommonUsers INNER JOIN Users " +
			"  ON CommonUsers.UserName = Users.UserName " +
			"WHERE CommonUsers.UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCommonUsers);
			selectStmt.setString(1, commonUsers.getUserName());
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultUserName = results.getString("UserName");
				String resultPassword = results.getString("Password");
				String resultFirstName = results.getString("FirstName");
				String resultLastName = results.getString("LastName");
				String resultEmail = results.getString("Email");
				String resultPhone = results.getString("Phone");
				CommonUsers commonUsers = new CommonUsers(resultUserName, resultPassword， resultFirstName,resultLastName,resultEmail，resultPhone);
				return commonUsers;
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
