package main;

import java.util.ArrayList;
import java.util.Random;

public class Grid {
	
	private int gridHeight;
	private int gridWidth;
	private int gridArraySize;
	private int populationDensity;
	private ArrayList<Cell> grid;

	public Grid(int gridHeight, int gridWidth, int populationDensity) {
		this.gridHeight = gridHeight;
		this.gridWidth = gridWidth;
		this.gridArraySize = gridHeight * gridWidth;
		this.populationDensity = populationDensity;
		this.grid = generateGrid(this.gridHeight, this.gridWidth, this.populationDensity);
	}
	
	private ArrayList<Cell> generateGrid(int gridHeight, int gridWidth, int populationDensity) {
		Random density = new Random();
		ArrayList<Cell> grid = new ArrayList<Cell>(gridArraySize);
		for (int x = 0; x < gridArraySize; x++) {
			grid.add(new Cell(density.nextDouble() < (populationDensity / 100.)?true:false));
		}
		return grid;
	}
	public ArrayList<Cell> getGridArray() {
		return grid;
	}
	public Cell getCell(int index) {
		return this.grid.get(index);
	}
	public int getCellIndex(int y, int x) {
		int index = y * gridWidth + x;
		return index;
	}
	public void clearGrid() {
		for (Cell cell : this.grid) {
			cell.killCell();
		}
	}
	public void populateGrid() {
		Random density = new Random();
		for (Cell cell : this.grid) {
			if (density.nextDouble() < (populationDensity / 100)) {
				cell.reviveCell();
			}
		}
	}
	public int getHeight() {
		return gridHeight;
	}
	public int getWidth() {
		return gridWidth;
	}
	public int getGridArraySize() {
		return gridArraySize;
	}
}

