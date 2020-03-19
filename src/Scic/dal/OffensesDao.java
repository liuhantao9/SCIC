package Scic.dal;

import Scic.model.Offenses;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OffensesDao {
  protected Scic.dal.ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static OffensesDao instance = null;
  protected OffensesDao() {
    connectionManager = new ConnectionManager();
  }
  public static OffensesDao getInstance() {
    if(instance == null) {
      instance = new OffensesDao();
    }
    return instance;
  }

  public Offenses create(Offenses offense)
      throws SQLException {

    String inserter = "INSERT INTO Offenses(offenseType, offenseDescription, reportDate, offenseStartDate, offenseEndDate, positionId, userName, authenticated, archived, severity) VALUES(?,?,?,?,?,?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(inserter, Statement.RETURN_GENERATED_KEYS);

      insertStmt.setString(1, offense.getOffenseType().toString());
      insertStmt.setString(2, offense.getOffenseDescription());
      insertStmt.setTimestamp(3, new Timestamp(offense.getReportDate().getTime()));
      insertStmt.setTimestamp(4, new Timestamp(offense.getOffenseStartDate().getTime()));
      insertStmt.setTimestamp(5, new Timestamp(offense.getOffenseEndDate().getTime()));
      insertStmt.setInt(6, offense.getPositionId());
      insertStmt.setString(7, offense.getUserName());
      insertStmt.setBoolean(8, offense.getAuthenticated());
      insertStmt.setBoolean(9, offense.getArchived());
      insertStmt.setInt(10, offense.getSeverity());


      insertStmt.executeUpdate();
      ResultSet resultKey = null;
      resultKey = insertStmt.getGeneratedKeys();
      int resvId = -1;
      if(resultKey.next()) {
        resvId = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      offense.setOffenseId(resvId);
      return offense;
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


  public Offenses delete(Offenses offense) throws SQLException {
    String deleteStr;
    deleteStr = "DELETE FROM Offenses WHERE offenseId=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteStr);
      deleteStmt.setInt(1, offense.getOffenseId());
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


  public Offenses getOffensesByOffenseId(Integer offenseId)
      throws SQLException {
    String selectStr = "SELECT offenseType, offenseDescription, reportDate, offenseStartDate, offenseEndDate, positionId, userName, authenticated, archived, severity FROM Offenses WHERE offenseId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectStr);
      selectStmt.setInt(1, offenseId);

      results = selectStmt.executeQuery();

      if(results.next()) {
        String offenseDescription, userName, offenseType;
        Integer positionId, severity;
        Date reportDate, offenseStartDate, offenseEndDate;
        Boolean authenticated, archived;
        userName = results.getString("userName");
        offenseDescription = results.getString("offenseDescription");
        offenseType = results.getString("offenseType");
        positionId = results.getInt("positionId");
        severity = results.getInt("severity");
        reportDate = new Date(results.getTimestamp("reportDate").getTime());
        offenseStartDate = new Date(results.getTimestamp("offenseStartDate").getTime());
        offenseEndDate = new Date(results.getTimestamp("offenseEndDate").getTime());
        authenticated = results.getBoolean("authenticated");
        archived = results.getBoolean("archived");

        return new Offenses(offenseId, Offenses.toOffenseType(offenseType), offenseDescription,
            reportDate, offenseStartDate, offenseEndDate, positionId,
            userName, authenticated, archived, severity);
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


  public Offenses updateOffensesDescription(Offenses offense, String description)
      throws SQLException {
    String updatePerson = "UPDATE Offenses SET offenseDescription=? WHERE offenseId=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updatePerson);
      updateStmt.setString(1, description);
      updateStmt.setInt(2, offense.getOffenseId());
      updateStmt.executeUpdate();

      offense.setOffenseDescription(description);
      return offense;
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

  public List<String> getTopKOffenseType(Integer k) throws SQLException {
    String selectStr = "SELECT offenseType, COUNT(*) FROM Offenses GROUP BY offenseType ORDER BY COUNT(*) DESC LIMIT ?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    List<String> list = new ArrayList<>();
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectStr);
      selectStmt.setInt(1, k);
      results = selectStmt.executeQuery();

      while (results.next()) {
        list.add(results.getString("offenseType"));
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
    return list;
  }

  public List<String> getTopKDistrictByOffenseType(Integer k, String offenseType) throws SQLException {
    String selectStr = "SELECT Districts.district, COUNT(*) FROM Offenses LEFT JOIN Districts ON Offenses.positionId = Districts.positionId "
        + "WHERE offenseType = ? GROUP BY Districts.district ORDER BY COUNT(*) DESC LIMIT ?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    List<String> list = new ArrayList<>();
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectStr);
      selectStmt.setString(1, offenseType);
      selectStmt.setInt(2, k);
      results = selectStmt.executeQuery();

      while (results.next()) {
        list.add(results.getString("Districts.district"));
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
    return list;
  }


  public List<Offenses> getTopKOffensesWithMostComments(Integer k) throws SQLException {
    String selectStr =
        "SELECT Offenses.offenseId, offenseType, offenseDescription, reportDate, offenseStartDate, offenseEndDate, positionId, Offenses.userName, authenticated, archived, severity, COUNT(*) \n"
            + "FROM Offenses LEFT JOIN Comments ON Offenses.offenseId = Comments.offenseId \n"
            + "GROUP BY Offenses.offenseId ORDER BY COUNT(*) DESC LIMIT ?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    List<Offenses> list = new ArrayList<>();
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectStr);
      selectStmt.setInt(1, k);
      results = selectStmt.executeQuery();

      while (results.next()) {
        String offenseDescription, userName, offenseType;
        Integer positionId, severity, offenseId;
        Date reportDate, offenseStartDate, offenseEndDate;
        Boolean authenticated, archived;
        offenseId = results.getInt("Offenses.offenseId");
        userName = results.getString("userName");
        offenseDescription = results.getString("offenseDescription");
        offenseType = results.getString("offenseType");
        positionId = results.getInt("positionId");
        severity = results.getInt("severity");
        reportDate = new Date(results.getTimestamp("reportDate").getTime());
        offenseStartDate = new Date(results.getTimestamp("offenseStartDate").getTime());
        offenseEndDate = new Date(results.getTimestamp("offenseEndDate").getTime());
        authenticated = results.getBoolean("authenticated");
        archived = results.getBoolean("archived");

        list.add(new Offenses(offenseId, Offenses.toOffenseType(offenseType), offenseDescription,
            reportDate, offenseStartDate, offenseEndDate, positionId,
            userName, authenticated, archived, severity));
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
    return list;
  }

}
