package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.*;

public class GUI implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	
	private Grid grid;
	private int gridHeight;
	private int gridWidth;
	private JFrame frame;
	private DrawPanel panel;
	private JScrollPane scrollPane;
	private int frameHeight;
	private int frameWidth;
	private int cellSize;
	private Point origin;
	
	public GUI(Grid grid, int cellSize) {
		
		this.grid = grid;
		this.cellSize = cellSize;
		this.gridHeight = this.grid.getHeight();
		this.gridWidth = this.grid.getWidth();
		this.frameHeight = this.gridHeight * this.cellSize;
		this.frameWidth = this.gridWidth * this.cellSize;
		this.frame = new JFrame();
		//this.frame.setPreferredSize(new Dimension(this.frameHeight, this.frameWidth));
		//this.panel = new JPanel();
		//this.panel.setBackground(Color.BLACK);
		//this.panel.setOpaque(true);
		//this.panel.setLayout(new GridLayout(this.grid.getHeight(), this.grid.getWidth()));
		this.panel = new DrawPanel(this.grid, this.cellSize, new Dimension(this.frameWidth, this.frameHeight));
		this.panel.setBackground(Color.BLACK);
		this.panel.addMouseListener(this);
		this.panel.addMouseMotionListener(this);
		this.panel.addMouseWheelListener(this);
		this.panel.setPreferredSize(new Dimension(this.frameHeight, this.frameWidth));
		this.scrollPane = new JScrollPane(this.panel);
		this.scrollPane.setWheelScrollingEnabled(false);
		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//this.frame.add(this.panel);
		this.frame.add(this.scrollPane);
		redrawGrid();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.setSize(new Dimension(Math.min(1000, this.frameHeight), Math.min(1000, this.frameWidth)));
		this.frame.setVisible(true);
		this.frame.addKeyListener(this);
		
	}

	public void redrawGrid() {
		this.panel.repaint();
		this.panel.revalidate();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				Main.changeMillisToNextGen(50);
				break;
			case KeyEvent.VK_RIGHT:
				Main.changeMillisToNextGen(-50);
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				Main.switchLoopState();
				break;
			case KeyEvent.VK_R:
				Main.switchClearState();
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point point = e.getPoint();
		switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				//System.out.println("Left Click x: " + point.x + " | " + "y: " + point.y);
				this.grid.getCell(grid.getCellIndex(point.y / cellSize, point.x / cellSize)).reviveCell();
				//System.out.println(grid.getCellIndex(point.y / cellSize, point.x / cellSize));
				redrawGrid();
				break;
			case MouseEvent.BUTTON3:
				//System.out.println("Right Click x: " + point.x + " | " + "y: " + point.y);
				this.grid.getCell(grid.getCellIndex(point.y / cellSize, point.x / cellSize)).killCell();
				//System.out.println(grid.getCellIndex(point.y / cellSize, point.x / cellSize));
				redrawGrid();
				break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON2:
			origin = e.getPoint();
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (this.origin != null) {
            JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, this.panel);
            if (viewPort != null) {
                int deltaX = this.origin.x - e.getX();
                int deltaY = this.origin.y - e.getY();

                Rectangle view = viewPort.getViewRect();
                view.x += deltaX;
                view.y += deltaY;

                this.panel.scrollRectToVisible(view);
            }
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		this.panel.changeCellSize(e.getUnitsToScroll());
		this.redrawGrid();
	}
}
