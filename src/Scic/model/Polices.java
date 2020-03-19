package Scic.model;

public class Polices extends Users {

  protected Integer policeStationId;

  public Polices(String userName, String password, String firstName, String lastName,
      String email, String phone, Integer policeStationId) {
    super(userName, password, firstName, lastName, email, phone);
    this.policeStationId = policeStationId;
  }

  public Integer getPoliceStationId() {
    return policeStationId;
  }

  public void setPoliceStationId(Integer policeStationId) {
    this.policeStationId = policeStationId;
  }
}
