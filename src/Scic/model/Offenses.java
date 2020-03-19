package Scic.model;

import java.util.Date;

public class Offenses {

  public enum OffenseType {
    VEHICLE_THEFT("VEHICLE THEFT"),
    OTHER_PROPERTY("OTHER PROPERTY"),
    ASSAULT("ASSAULT"),
    TRAFFIC("TRAFFIC"),
    FRAUD("FRAUD"),
    STOLEN_PROPERTY("STOLEN PROPERTY"),
    WARRANT_ARREST("WARRANT ARREST"),
    BIKE_THEFT("BIKE THEFT"),
    BURGLARY("BURGLARY"),
    THREATS("THREATS"),
    DISTURBANCE("DISTURBANCE"),
    CAR_PROWL("CAR PROWL"),
    EXTORTION("EXTORTION"),
    SHOPLIFTING("SHOPLIFTING"),
    PROPERTY_DAMAGE("PROPERTY DAMAGE"),
    COUNTERFEIT("COUNTERFEIT"),
    WEAPON("WEAPON"),
    NARCOTICS("NARCOTICS"),
    ROBBERY("ROBBERY"),
    BURGLARY_SECURE_PARKING_RES("BURGLARY-SECURE PARKING-RES"),
    TRESPASS("TRESPASS"),
    LOST_PROPERTY("LOST PROPERTY"),
    DUI("DUI"),
    INJURY("INJURY"),
    OBSTRUCT("OBSTRUCT"),
    ELUDING("ELUDING"),
    MAIL_THEFT("MAIL THEFT"),
    VIOLATION_OF_COURT_ORDER("VIOLATION OF COURT ORDER"),
    HOMICIDE("HOMICIDE"),
    EMBEZZLE("EMBEZZLE"),
    RECKLESS_BURNING("RECKLESS BURNING"),
    FORGERY("FORGERY"),
    ANIMAL_COMPLAINT("ANIMAL COMPLAINT"),
    THEFT_OF_SERVICES("THEFT OF SERVICES"),
    DISPUTE("DISPUTE"),
    DISORDERLY_CONDUCT("DISORDERLY CONDUCT"),
    PURSE_SNATCH("PURSE SNATCH"),
    ILLEGAL_DUMPING("ILLEGAL DUMPING"),
    PROSTITUTION("PROSTITUTION"),
    PICKPOCKET("PICKPOCKET"),
    RECOVERED_PROPERTY("RECOVERED PROPERTY"),
    LIQUOR_VIOLATION("LIQUOR VIOLATION"),
    FALSE_REPORT("FALSE REPORT"),
    LOITERING("LOITERING"),
    HARBOR_CALLs("HARBOR CALLs"),
    STAY_OUT_OF_AREA_OF_DRUGS("STAY OUT OF AREA OF DRUGS"),
    FRAUD_AND_FINANCIAL("FRAUD AND FINANCIAL"),
    PORNOGRAPHY("PORNOGRAPHY"),
    INC_CASE_DC_USE_ONLY("[INC - CASE DC USE ONLY]"),
    ESCAPE("ESCAPE"),
    PUBLIC_NUISANCE("PUBLIC NUISANCE"),
    FIREWORK("FIREWORK"),
    BIAS_INCIDENT("BIAS INCIDENT"),
    STAY_OUT_OF_AREA_OF_PROSTITUTION("STAY OUT OF AREA OF PROSTITUTION"),
    GAMBLE("GAMBLE"),
    METRO("METRO");

    private String name;

    OffenseType(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }


  protected Integer offenseId;
  protected OffenseType offenseType;
  protected String offenseDescription;
  protected Date reportDate;
  protected Date offenseStartDate;
  protected Date offenseEndDate;
  protected Integer positionId;
  protected String userName;
  protected Boolean authenticated;
  protected Boolean archived;
  protected Integer severity;

  public Offenses(Integer offenseId, OffenseType offenseType, String offenseDescription,
      Date reportDate, Date offenseStartDate, Date offenseEndDate, Integer positionId,
      String userName, Boolean authenticated, Boolean archived, Integer severity) {
    this.offenseId = offenseId;
    this.offenseType = offenseType;
    this.offenseDescription = offenseDescription;
    this.reportDate = reportDate;
    this.offenseStartDate = offenseStartDate;
    this.offenseEndDate = offenseEndDate;
    this.positionId = positionId;
    this.userName = userName;
    this.authenticated = authenticated;
    this.archived = archived;
    this.severity = severity;
  }

  public Integer getOffenseId() {
    return offenseId;
  }

  public void setOffenseId(Integer offenseId) {
    this.offenseId = offenseId;
  }

  public OffenseType getOffenseType() {
    return offenseType;
  }

  public void setOffenseType(OffenseType offenseType) {
    this.offenseType = offenseType;
  }

  public String getOffenseDescription() {
    return offenseDescription;
  }

  public void setOffenseDescription(String offenseDescription) {
    this.offenseDescription = offenseDescription;
  }

  public Date getReportDate() {
    return reportDate;
  }

  public void setReportDate(Date reportDate) {
    this.reportDate = reportDate;
  }

  public Date getOffenseStartDate() {
    return offenseStartDate;
  }

  public void setOffenseStartDate(Date offenseStartDate) {
    this.offenseStartDate = offenseStartDate;
  }

  public Date getOffenseEndDate() {
    return offenseEndDate;
  }

  public void setOffenseEndDate(Date offenseEndDate) {
    this.offenseEndDate = offenseEndDate;
  }

  public Integer getPositionId() {
    return positionId;
  }

  public void setPositionId(Integer positionId) {
    this.positionId = positionId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Boolean getAuthenticated() {
    return authenticated;
  }

  public void setAuthenticated(Boolean authenticated) {
    this.authenticated = authenticated;
  }

  public Boolean getArchived() {
    return archived;
  }

  public void setArchived(Boolean archived) {
    this.archived = archived;
  }

  public Integer getSeverity() {
    return severity;
  }

  public void setSeverity(Integer severity) {
    this.severity = severity;
  }

  @Override
  public String toString() {
    return "Offenses{" +
        "offenseId=" + offenseId +
        ", offenseType=" + offenseType +
        ", offenseDescription='" + offenseDescription + '\'' +
        ", reportDate=" + reportDate +
        ", offenseStartDate=" + offenseStartDate +
        ", offenseEndDate=" + offenseEndDate +
        ", positionId=" + positionId +
        ", userName='" + userName + '\'' +
        ", authenticated=" + authenticated +
        ", archived=" + archived +
        ", severity=" + severity +
        '}';
  }

  public static OffenseType toOffenseType(String ot) {

    OffenseType offenseType = null;
    switch(ot) {
      case "VEHICLE THEFT": offenseType = OffenseType.VEHICLE_THEFT; break;
      case "OTHER PROPERTY": offenseType = OffenseType.OTHER_PROPERTY; break;
      case "ASSAULT": offenseType = OffenseType.ASSAULT; break;
      case "TRAFFIC": offenseType = OffenseType.TRAFFIC; break;
      case "FRAUD": offenseType = OffenseType.FRAUD; break;
      case "STOLEN PROPERTY": offenseType = OffenseType.STOLEN_PROPERTY; break;
      case "WARRANT ARREST": offenseType = OffenseType.WARRANT_ARREST; break;
      case "BIKE THEFT": offenseType = OffenseType.BIKE_THEFT; break;
      case "BURGLARY": offenseType = OffenseType.BURGLARY; break;
      case "THREATS": offenseType = OffenseType.THREATS; break;
      case "DISTURBANCE": offenseType = OffenseType.DISTURBANCE; break;
      case "CAR PROWL": offenseType = OffenseType.CAR_PROWL; break;
      case "EXTORTION": offenseType = OffenseType.EXTORTION; break;
      case "SHOPLIFTING": offenseType = OffenseType.SHOPLIFTING; break;
      case "PROPERTY DAMAGE": offenseType = OffenseType.PROPERTY_DAMAGE; break;
      case "COUNTERFEIT": offenseType = OffenseType.COUNTERFEIT; break;
      case "WEAPON": offenseType = OffenseType.WEAPON; break;
      case "NARCOTICS": offenseType = OffenseType.NARCOTICS; break;
      case "ROBBERY": offenseType = OffenseType.ROBBERY; break;
      case "BURGLARY-SECURE PARKING-RES": offenseType = OffenseType.BURGLARY_SECURE_PARKING_RES; break;
      case "TRESPASS": offenseType = OffenseType.TRESPASS; break;
      case "LOST PROPERTY": offenseType = OffenseType.LOST_PROPERTY; break;
      case "DUI": offenseType = OffenseType.DUI; break;
      case "INJURY": offenseType = OffenseType.INJURY; break;
      case "OBSTRUCT": offenseType = OffenseType.OBSTRUCT; break;
      case "ELUDING": offenseType = OffenseType.ELUDING; break;
      case "MAIL THEFT": offenseType = OffenseType.MAIL_THEFT; break;
      case "VIOLATION OF COURT ORDER": offenseType = OffenseType.VIOLATION_OF_COURT_ORDER; break;
      case "HOMICIDE": offenseType = OffenseType.HOMICIDE; break;
      case "EMBEZZLE": offenseType = OffenseType.EMBEZZLE; break;
      case "RECKLESS BURNING": offenseType = OffenseType.RECKLESS_BURNING; break;
      case "FORGERY": offenseType = OffenseType.FORGERY; break;
      case "ANIMAL COMPLAINT": offenseType = OffenseType.ANIMAL_COMPLAINT; break;
      case "THEFT OF SERVICES": offenseType = OffenseType.THEFT_OF_SERVICES; break;
      case "DISPUTE": offenseType = OffenseType.DISPUTE; break;
      case "DISORDERLY CONDUCT": offenseType = OffenseType.DISORDERLY_CONDUCT; break;
      case "PURSE SNATCH": offenseType = OffenseType.PURSE_SNATCH; break;
      case "ILLEGAL DUMPING": offenseType = OffenseType.ILLEGAL_DUMPING; break;
      case "PROSTITUTION": offenseType = OffenseType.PROSTITUTION; break;
      case "PICKPOCKET": offenseType = OffenseType.PICKPOCKET; break;
      case "RECOVERED PROPERTY": offenseType = OffenseType.RECOVERED_PROPERTY; break;
      case "LIQUOR VIOLATION": offenseType = OffenseType.LIQUOR_VIOLATION; break;
      case "FALSE REPORT": offenseType = OffenseType.FALSE_REPORT; break;
      case "LOITERING": offenseType = OffenseType.LOITERING; break;
      case "HARBOR CALLs": offenseType = OffenseType.HARBOR_CALLs; break;
      case "STAY OUT OF AREA OF DRUGS": offenseType = OffenseType.STAY_OUT_OF_AREA_OF_DRUGS; break;
      case "FRAUD AND FINANCIAL": offenseType = OffenseType.FRAUD_AND_FINANCIAL; break;
      case "PORNOGRAPHY": offenseType = OffenseType.PORNOGRAPHY; break;
      case "[INC - CASE DC USE ONLY]": offenseType = OffenseType.INC_CASE_DC_USE_ONLY; break;
      case "ESCAPE": offenseType = OffenseType.ESCAPE; break;
      case "PUBLIC NUISANCE": offenseType = OffenseType.PUBLIC_NUISANCE; break;
      case "FIREWORK": offenseType = OffenseType.FIREWORK; break;
      case "BIAS INCIDENT": offenseType = OffenseType.BIAS_INCIDENT; break;
      case "STAY OUT OF AREA OF PROSTITUTION": offenseType = OffenseType.STAY_OUT_OF_AREA_OF_PROSTITUTION; break;
      case "GAMBLE": offenseType = OffenseType.GAMBLE; break;
      case "METRO": offenseType = OffenseType.METRO; break;
    }
    return offenseType;
}

}
