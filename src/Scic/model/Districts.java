package model;

public class Districts {
	Positions position;
	String district;
	
	public Districts(Positions position, String district) {
		this.position = position;
		this.district = district;
	}

	public Positions getPosition() {
		return position;
	}

	public void setPosition(Positions position) {
		this.position = position;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	
}
