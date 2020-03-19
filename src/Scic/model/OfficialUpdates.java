package Scic.model;

import java.io.InputStream;

public class OfficialUpdates {
  protected Integer officialUpdateId;
  protected Integer offenseId;
  protected String userName;
  protected String comment;
  protected InputStream attachment;

  public OfficialUpdates(Integer officialUpdateId, Integer offenseId, String userName,
      String comment, InputStream attachment) {
    this.officialUpdateId = officialUpdateId;
    this.offenseId = offenseId;
    this.userName = userName;
    this.comment = comment;
    this.attachment = attachment;
  }

  public Integer getOfficialUpdateId() {
    return officialUpdateId;
  }

  public void setOfficialUpdateId(Integer officialUpdateId) {
    this.officialUpdateId = officialUpdateId;
  }

  public Integer getOffenseId() {
    return offenseId;
  }

  public void setOffenseId(Integer offenseId) {
    this.offenseId = offenseId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public InputStream getAttachment() {
    return attachment;
  }

  public void setAttachment(InputStream attachment) {
    this.attachment = attachment;
  }

  @Override
  public String toString() {
    return "OfficialUpdates{" +
        "officialUpdateId=" + officialUpdateId +
        ", offenseId=" + offenseId +
        ", userName='" + userName + '\'' +
        ", comment='" + comment + '\'' +
        ", attachment=" + attachment +
        '}';
  }
}
