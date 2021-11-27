package application;

import javafx.scene.layout.Region;

public class ASNode extends Region{
	
	public int gCost;
	public int hCost;
	
	public int gridX;
	public int gridY;
	
	public ASNode parent;
	
	public boolean walkable;
	
	public ASNode( boolean walkable, int gridX, int gridY) {
		super();
		this.gridX = gridX;
		this.gridY = gridY;
		this.walkable = walkable;
	}
	
	public int getfCost() {
		return gCost + hCost;
	}
	

}
