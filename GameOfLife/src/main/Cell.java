package main;

public class Cell {
	
	private boolean alive;
	private boolean nextState;

	public Cell(boolean state) {
		this.alive = state;
	}
	
	public void updateState() {
		this.alive = this.nextState;
	}
	public void switchNextState() {
		if (this.alive == false) {
			this.nextState = true;
		}
		else if (this.alive == true) {
			this.nextState = false;
		}
	}
	public boolean isAlive() {
		return alive;
	}
	public boolean getNextState() {
		return nextState;
	}
	public void killCell() {
		this.alive = false;
		this.nextState = false;
	}
	public void reviveCell() {
		this.alive = true;
		this.nextState = true;
	}
}
