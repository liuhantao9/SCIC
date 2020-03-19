package model;

public class CensusTracts {
	Positions position;
	Double census;
	
	public CensusTracts(Positions position,Double census) {
		this.position = position;
		this.census= census;
	}

	public Positions getPosition() {
		return position;
	}

	public void setPosition(Positions position) {
		this.position = position;
	}

	public Double getCensus() {
		return census;
	}

	public void setCensus(Double census) {
		this.census = census;
	}
	
	
}
