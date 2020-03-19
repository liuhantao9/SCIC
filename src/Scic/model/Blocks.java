package model;

public class Blocks {
	Positions position;
	String block;
	
	public Blocks(Positions position, String block) {
		this.position = position;
		this.block = block;
	}

	public Positions getPosition() {
		return position;
	}

	public void setPosition(Positions position) {
		this.position = position;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	
}
