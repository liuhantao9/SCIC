package Scic.dal;

import Scic.model.PoliceStations;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PoliceStationsDao {
  protected ConnectionManager connectionManager;

  private static PoliceStationsDao instance = null;
  protected PoliceStationsDao() {
    connectionManager = new ConnectionManager();
  }
  public static PoliceStationsDao getInstance() {
    if(instance == null) {
      instance = new PoliceStationsDao();
    }
    return instance;
  }

  public PoliceStations create(PoliceStations policeStation)
      throws FileNotFoundException, SQLException {

    String inserter = "INSERT INTO PoliceStations(name, address1, address2, phone, positionId) VALUES(?,?,?,?.?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(inserter, Statement.RETURN_GENERATED_KEYS);

      insertStmt.setString(1, policeStation.getName());
      insertStmt.setString(2, policeStation.getAddress1());
      insertStmt.setString(3, policeStation.getAddress2());
      insertStmt.setString(4, policeStation.getPhone());
      insertStmt.setInt(5, policeStation.getPositionId());

      insertStmt.executeUpdate();
      ResultSet resultKey = null;
      resultKey = insertStmt.getGeneratedKeys();
      int resvId = -1;
      if(resultKey.next()) {
        resvId = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      policeStation.setPoliceStationId(resvId);
      return policeStation;
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

  public PoliceStations delete(PoliceStations policeStation) throws SQLException {
    String deleteUser = "DELETE FROM PoliceStations WHERE policeStationId=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteUser);
      deleteStmt.setInt(1, policeStation.getPoliceStationId());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the Users instance.
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

  public PoliceStations getPoliceStationById(Integer policeStationId) throws SQLException {
    String selectUser = "SELECT name, address1, address2, phone, positionId FROM PoliceStations WHERE policeStationId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectUser);
      selectStmt.setInt(1, policeStationId);
      results = selectStmt.executeQuery();
      if(results.next()) {
        String name, address1, address2, phone;
        Integer positionId;
        name = results.getString("name");
        address1 = results.getString("address1");
        address2 = results.getString("address2");
        phone = results.getString("phone");
        positionId = results.getInt("positionId");

        return new PoliceStations(policeStationId, name, address1, address2, phone, positionId);
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


  public PoliceStations updatePoliceStationName(PoliceStations policeStation, String name) throws SQLException {
    String updateUser = "UPDATE PoliceStations SET name=? WHERE policeStationId=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateUser);
      updateStmt.setString(1, name);
      updateStmt.setInt(2, policeStation.getPoliceStationId());
      updateStmt.executeUpdate();

      // Update the user param before returning to the caller.
      policeStation.setName(name);
      return policeStation;
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
