
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
 * Data access object (DAO) class to interact with the underlying Administrator table in your
 * MySQL instance. This is used to store {@link Administrator} into your MySQL instance and 
 * retrieve {@link Administrator} from MySQL instance.
 */
public class AdministratorDao extends UsersDao {
	// Single pattern: instantiation is limited to one object.
	private static AdministratorDao instance = null;
	protected AdministratorDao() {
		super();
	}
	public static AdministratorDao getInstance() {
		if(instance == null) {
			instance = new AdministratorDao();
		}
		return instance;
	}

	public Administrator create(Administrator administrator) throws SQLException {
		// Insert into the superclass table first.
		create(new Users(administrator.getUserName(), administrator.getPassword(), administrator.getFirstName(),
			administrator.getLastName(), administrator.getEmail(), administrator.getPhone()));

		String insertAdministrator = "INSERT INTO Administrator(UserName) VALUES(?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertAdministrator);
			insertStmt.setString(1, administrator.getUserName());
			insertStmt.executeUpdate();
			return administrator;
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
	 * Delete the Administrator instance.
	 * This runs a DELETE statement.
	 */
	public Administrator delete(Administrator administrator) throws SQLException {
		String deleteAdministrator = "DELETE FROM Administrator WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteAdministrator);
			deleteStmt.setString(1, administrator.getUserName());
			deleteStmt.executeUpdate();
			super.delete(administrator);

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

	public Administrator getAdministrator(Administrator administrator) throws SQLException {
		// To build an Administrator object, we need the Users record, too.
		String selectAdministrator =
			"SELECT Administrator.UserName AS UserName,Password,FirstName,LastName,Email,Phone " +
			"FROM Administrator INNER JOIN Users " +
			"  ON Administrator.UserName = Users.UserName " +
			"WHERE Administrator.UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAdministrator);
			selectStmt.setString(1, administrator.getUserName());
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultUserName = results.getString("UserName");
				String resultPassword = results.getString("Password");
				String resultFirstName = results.getString("FirstName");
				String resultLastName = results.getString("LastName");
				String resultEmail = results.getString("Email");
				String resultPhone = results.getString("Phone");
				Administrator administrator = new Administrator(resultUserName, resultPassword， resultFirstName,resultLastName,resultEmail，resultPhone);
				return administrator;
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
