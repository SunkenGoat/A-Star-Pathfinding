package application;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.awt.Point;

public class ASNode extends Region implements Comparable<ASNode> {
	
	private double gCost;
	private double hCost;
	
	private int gridX;
	private int gridY;
	
	private ASNode parent;
	
	public boolean walkable;
	int heapIndex;
	
	public ASNode( boolean walkable, int gridX, int gridY) {
		super();
		this.gridX = gridX;
		this.gridY = gridY;
		this.walkable = walkable;
		this.setNodeParent(null);
		this.gCost = 0;
		this.hCost = 0;
	}
	
	public double getfCost() {
		return gCost + hCost;
	}

	@Override
	public int compareTo(ASNode node) {
		int compare = Double.compare(getfCost(), node.getfCost());
		if (compare == 0) {
			compare = Double.compare(hCost, node.hCost);
		}
		return -compare;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		ASNode node = (ASNode) obj;
		Point firstPosition =  this.getPosition();
		Point secondPosition = node.getPosition();
		return firstPosition.equals(secondPosition);
	}
	
	public void setNodeStyle(Color color) {
		
		String colorString = color.toString();
		colorString = colorString.replace("0x", "#");
		String style = "-fx-background-color: " + colorString + "; -fx-border-style: solid; -fx-border-width: 1;"
					+ " -fx-border-color: black; -fx-min-width: 20; -fx-min-height:20; -fx-max-width:20;"
					+ " -fx-max-height: 20;";
		
		this.setStyle(style);

	}

	public double getgCost() {
		return gCost;
	}

	public void setgCost(double gCost) {
		this.gCost = gCost;
	}

	public double gethCost() {
		return hCost;
	}

	public void sethCost(double hCost) {
		this.hCost = hCost;
	}
	
	public Point getPosition() {
		return new Point(gridX, gridY);
	}
	
	public int getGridX() {
		return gridX;
	}

	public void setGridX(int gridX) {
		this.gridX = gridX;
	}

	public int getGridY() {
		return gridY;
	}

	public void setGridY(int gridY) {
		this.gridY = gridY;
	}

	public ASNode getNodeParent() {
		return parent;
	}

	public void setNodeParent(ASNode parent) {
		this.parent = parent;
	}
	
	
	

}
