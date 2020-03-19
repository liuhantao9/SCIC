
package Scic.model;

public class Positions {
	protected int positionID;
	protected double longitude ;
	protected double latitude;
	
	public Positions(int positionID, double longitude, double latitude) {
		this.positionID = positionID;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Positions(int positionID) {
		this.positionID = positionID;
	}
	
	public Positions( double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public int getPositionID() {
		return positionID;
	}

	public void setPositionID(int positionID) {
		this.positionID = positionID;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
}

