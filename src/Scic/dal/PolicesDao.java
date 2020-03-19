package Scic.dal;

import Scic.model.Polices;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PolicesDao extends UsersDao {
  private static PolicesDao instance = null;
  protected PolicesDao() {
    connectionManager = new ConnectionManager();
  }
  public static PolicesDao getInstance() {
    if(instance == null) {
      instance = new PolicesDao();
    }
    return instance;
  }

  public Polices create(Polices polices) throws SQLException {
    String insertUser = "INSERT INTO Polices(UserName,PoliceStationId) VALUES(?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      super.create(polices);

      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertUser);
      insertStmt.setString(1, polices.getUserName());
      insertStmt.setInt(2, polices.getPoliceStationId());
      insertStmt.executeUpdate();
      return polices;
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

  public Polices delete(Polices polices) throws SQLException {
    String deleteStr;
    deleteStr = "DELETE FROM Polices WHERE userName=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteStr);
      deleteStmt.setString(1, polices.getUserName());
      deleteStmt.executeUpdate();

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

  public Polices getPolicesByUsername(String username)
      throws SQLException {
    String selectStr = "SELECT Polices.userName, policeStationId, password, firstName, lastName, email, phone "
        + "FROM Polices LEFT JOIN Users ON Polices.userName = Users.userName WHERE Polices.userName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectStr);
      selectStmt.setString(1, username);

      results = selectStmt.executeQuery();

      if(results.next()) {
        String password, firstName, lastName, email, phone;
        Integer policeStationId;

        password = results.getString("password");
        firstName = results.getString("firstName");
        lastName = results.getString("lastName");
        email = results.getString("email");
        phone = results.getString("phone");
        policeStationId = results.getInt("policeStationId");

        return new Polices(username, password, firstName, lastName,
            email, phone, policeStationId);
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


  public Polices updatePolicesStation(Polices polices, Integer policeStationId) throws SQLException {
    String updateUser = "UPDATE Polices SET policeStationId=? WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateUser);
      updateStmt.setInt(1, policeStationId);
      updateStmt.setString(2, polices.getUserName());
      updateStmt.executeUpdate();

      // Update the user param before returning to the caller.
      polices.setPoliceStationId(policeStationId);
      return polices;
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
}
