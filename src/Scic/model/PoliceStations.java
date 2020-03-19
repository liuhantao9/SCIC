package Scic.model;

public class PoliceStations {

  protected Integer policeStationId;
  protected String name;
  protected String address1;
  protected String address2;
  protected String phone;
  protected Integer positionId;

  public PoliceStations(Integer policeStationId, String name, String address1,
      String address2, String phone, Integer positionId) {
    this.policeStationId = policeStationId;
    this.name = name;
    this.address1 = address1;
    this.address2 = address2;
    this.phone = phone;
    this.positionId = positionId;
  }

  public Integer getPoliceStationId() {
    return policeStationId;
  }

  public void setPoliceStationId(Integer policeStationId) {
    this.policeStationId = policeStationId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getPositionId() {
    return positionId;
  }

  public void setPositionId(Integer positionId) {
    this.positionId = positionId;
  }
}
