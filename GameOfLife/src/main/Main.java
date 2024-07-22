package main;

public class Main {
	
	private final static int columns = 50;
	private final static int rows = 50;
	private final static int populationChance = 25;
	private final static int cellDisplaySize = 10;
	private final static int maxMillis = 500;
	private final static int minMillis = 10;
	private static int millisToNextGen = 100;
	private static volatile boolean loop;
	private static volatile boolean clear;

	public static void main(String[] args) {
		
		Grid grid = new Grid(columns, rows, populationChance);
		UpdateHandler updateHandler = new UpdateHandler(grid);
		GUI gui = new GUI(grid, cellDisplaySize);
		boolean start = true;
		Main.loop = true;
		while (start) {
			try {
				Thread.sleep(millisToNextGen);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (Main.clear) {
				System.out.println("Grid cleared");
				grid.clearGrid();
				gui.redrawGrid(); 
				Main.clear = false; 
			}
			if (Main.loop) {
				updateHandler.update();
				gui.redrawGrid();
			}
		}
	}
	public static void switchLoopState() {
		Main.loop = !Main.loop;
		if (loop) {
			System.out.println("Resumed");
		}
		else {
			System.out.println("Paused");
		}
	}
	public static void switchClearState() {
		Main.clear = !Main.clear;
	}
	public static void changeMillisToNextGen(int millis) {
		int newValue = Main.millisToNextGen + millis;
		newValue = Math.max(newValue, Main.minMillis);
		newValue = Math.min(newValue, Main.maxMillis);
		System.out.println("Simulation Speed: " + newValue);
		Main.millisToNextGen = newValue;
	}
}
