package main;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class UpdateHandler {
	
	private Grid grid;
	private int height;
	private int width;
	private int threadAmount;
	UpdateThread thread;
	ThreadPoolExecutor executor;

	UpdateHandler(Grid grid) {
		this.grid = grid;
		this.height = grid.getHeight();
		this.width = grid.getWidth();
		this.threadAmount = Math.max(this.grid.getGridArraySize() / 60000, 1);
		this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadAmount);
	}
	
	public void update() {
		createThreads();
	}
	public void update(int startIndex, int endIndex) {
		for (int iteration = startIndex; iteration < endIndex; iteration++) {
			if (grid.getCell(iteration).isAlive()) {
				if (getLiveCellNeighboursCount(iteration) < 2) {
					grid.getCell(iteration).switchNextState();
				}
				if (getLiveCellNeighboursCount(iteration) > 3) {
					grid.getCell(iteration).switchNextState();
				}
			}
			else {
				if (getLiveCellNeighboursCount(iteration) == 3) {
					grid.getCell(iteration).switchNextState();
				}
			}
		}
		for (int iteration = startIndex; iteration < endIndex; iteration++) {
			grid.getCell(iteration).updateState();
		}
	}
	private void createThreads() {
		for (int i = 0; i < this.threadAmount; i++) {
			int threadSliceSize = this.grid.getGridArraySize() / threadAmount + 1;
			final int iteration = i;
			this.executor.submit(() -> {
				int threadStartIndex = threadSliceSize * iteration;
				int threadEndIndex = Math.min(threadSliceSize * (iteration + 1), this.grid.getGridArraySize());
				//System.out.println("threadAmount: " + threadAmount);
				//System.out.println("startIndex: " + threadStartIndex);
				//System.out.println("endIndex: " + threadEndIndex);
				this.update(threadStartIndex, threadEndIndex);
				return null;
			});
		}
	}
	private int getLiveCellNeighboursCount(int index) {
		int y = index / this.grid.getWidth();
		int x = index % this.grid.getWidth();
		return getLiveCellNeighboursCount(y, x);
	}
	private int getLiveCellNeighboursCount(int y, int x) {
		int xLeft = ((x - 1) + width) % width;
		int xRight = (x + 1) % width;
		int yUp = ((y - 1) + height) % height;
		int yDown = (y + 1) % height;
		int[] neighbourIndices = {grid.getCellIndex(yUp, xLeft),   grid.getCellIndex(yUp, x),   grid.getCellIndex(yUp, xRight),
								  grid.getCellIndex(y, xLeft),                             		grid.getCellIndex(y, xRight),
								  grid.getCellIndex(yDown, xLeft), grid.getCellIndex(yDown, x), grid.getCellIndex(yDown, xRight)};
		
		int count = 0;
		for (int neighbourIndex : neighbourIndices) {
			if (this.grid.getCell(neighbourIndex).isAlive()) {
				count++;
			}
		}
		return count;
	}
	public int getHeight() {
		return this.height;
	}
	public int getWidth() {
		return this.width;
	}
	public Grid getGrid() {
		return this.grid;
	}
}
