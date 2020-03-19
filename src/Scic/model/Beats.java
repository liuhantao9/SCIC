package model;

public class Beats {
	Positions position;
	String beat;
	
	public Beats(Positions position, String beat) {
		this.position = position;
		this.beat= beat;
	}

	public Positions getPosition() {
		return position;
	}

	public void setPosition(Positions position) {
		this.position = position;
	}

	public String getBeat() {
		return beat;
	}

	public void setBeat(String beat) {
		this.beat = beat;
	}
	
	
}
