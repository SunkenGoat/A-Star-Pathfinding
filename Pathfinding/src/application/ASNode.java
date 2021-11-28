package application;

import javafx.scene.layout.Region;

public class ASNode extends Region implements IHeapItem<ASNode> {
	
	public int gCost;
	public int hCost;
	
	public int gridX;
	public int gridY;
	
	public ASNode parent;
	
	public boolean walkable;
	int heapIndex;
	
	public ASNode( boolean walkable, int gridX, int gridY) {
		super();
		this.gridX = gridX;
		this.gridY = gridY;
		this.walkable = walkable;
	}
	
	public int getfCost() {
		return gCost + hCost;
	}

	@Override
	public int compareTo(ASNode o) {
		int compare = Integer.compare(getfCost(), o.getfCost());
		if (compare == 0) {
			compare = Integer.compare(hCost, o.hCost);
		}
		return -compare;
	}

	@Override
	public int getIndex() {
		return heapIndex;
	}

	@Override
	public void setIndex(int heapIndex) {
		this.heapIndex = heapIndex;
		
	}
	

}
