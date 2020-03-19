package Scic.dal;

import Scic.model.OfficialUpdates;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class OfficialUpdatesDao {
  protected Scic.dal.ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static OfficialUpdatesDao instance = null;
  protected OfficialUpdatesDao() {
    connectionManager = new ConnectionManager();
  }
  public static OfficialUpdatesDao getInstance() {
    if(instance == null) {
      instance = new OfficialUpdatesDao();
    }
    return instance;
  }






  public OfficialUpdates create(OfficialUpdates officialUpdate)
      throws FileNotFoundException, SQLException {

    String inserter = "INSERT INTO OfficialUpdates(offenseId, userName, comment, attachment) VALUES(?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(inserter, Statement.RETURN_GENERATED_KEYS);

      insertStmt.setInt(1, officialUpdate.getOffenseId());
      insertStmt.setString(2, officialUpdate.getUserName());
      insertStmt.setString(3, officialUpdate.getComment());
      insertStmt.setBlob(4, officialUpdate.getAttachment());

      insertStmt.executeUpdate();
      ResultSet resultKey = null;
      resultKey = insertStmt.getGeneratedKeys();
      int resvId = -1;
      if(resultKey.next()) {
        resvId = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      officialUpdate.setOfficialUpdateId(resvId);
      return officialUpdate;
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


  public OfficialUpdates delete(OfficialUpdates officialUpdates) throws SQLException {
    String deleteStr;
    deleteStr = "DELETE FROM OfficialUpdates WHERE officialUpdateId=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteStr);
      deleteStmt.setInt(1, officialUpdates.getOfficialUpdateId());
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

  public OfficialUpdates getOfficialUpdatesByOfficialUpdateId(Integer officialUpdateId)
      throws SQLException {
    String selectStr = "SELECT officialUpdateId, offenseId, userName, comment, attachment FROM OfficialUpdates WHERE officialUpdateId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectStr);
      selectStmt.setInt(1, officialUpdateId);

      results = selectStmt.executeQuery();

      if(results.next()) {
        String userName, comment;
        Integer offenseId;
        InputStream attachment;
        userName = results.getString("userName");
        officialUpdateId = results.getInt("officialUpdateId");
        offenseId = results.getInt("offenseId");
        comment = results.getString("comment");
        attachment = results.getBinaryStream("attachment");
        return new OfficialUpdates(officialUpdateId, offenseId, userName, comment, attachment);
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

  public OfficialUpdates update(OfficialUpdates officialUpdates, String newComments)
      throws SQLException {
    String updatePerson = "UPDATE OfficialUpdates SET comment=? WHERE officialUpdateId=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updatePerson);
      updateStmt.setString(1, newComments);
      updateStmt.setInt(2, officialUpdates.getOfficialUpdateId());
      updateStmt.executeUpdate();

      officialUpdates.setComment(newComments);
      return officialUpdates;
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
