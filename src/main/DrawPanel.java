package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawPanel extends JPanel {
	
	Grid grid;
	int cellSize;
	int frameWidth;
	int frameHeight;
	private static final int minCellSize = 5;
	private static final int maxCellSize = 15;
	private static final long serialVersionUID = 1L;

	public DrawPanel(Grid grid, int cellSize, Dimension frameSize) {
		
		this.grid = grid;
		this.cellSize = cellSize;
		this.frameWidth = frameSize.width;
		this.frameHeight = frameSize.height;
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		for (int y = 0; y < grid.getHeight(); y++) {
			for (int x = 0; x < grid.getWidth(); x++) {
				if (grid.getCell(grid.getCellIndex(y, x)).isAlive()) {
					g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
				}
			}
		}
	}
	public void changeCellSize(int amount) {
		int newCellSize = Math.min(maxCellSize, this.cellSize + amount);
		newCellSize = Math.max(minCellSize, this.cellSize + amount);
		this.cellSize = newCellSize;
	}
}



