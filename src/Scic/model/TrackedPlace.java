
package Scic.model;

public class TrackedPlace {
	protected int trackID;
	protected Users user;
	protected double longitude ;
	protected double latitude;
	
	public TrackedPlace(int trackID, Users user, double longitude, double latitude) {
		this.trackID = trackID;
		this.longitude = longitude;
		this.latitude = latitude;
		this.user = user;
	}
	
	public TrackedPlace(int trackID) {
		this.trackID = trackID;
	}
	
	public TrackedPlace( Users user, double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.user = user;
	}

	public int getTrackID() {
		return trackID;
	}

	public void setTrackID(int trackID) {
		this.trackID = trackID;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
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

