package application;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class Pathfinding {
	

	private int gridSizeX;
	private int gridSizeY;
	
	private Node[][] gridArray;
	
	public Pathfinding(Point _gridWorldSize, float _nodeRadius) {
		
		float nodeDiameter = _nodeRadius * 2;
		
		this.gridSizeX = (int) Math.round(_gridWorldSize.getX() / nodeDiameter);
		this.gridSizeY = (int) Math.round(_gridWorldSize.getY() / nodeDiameter);
		
		this.gridArray = new ASNode[gridSizeX][gridSizeY];
	}
	
	
	
	public void FindPath(ASNode startNode, ASNode targetNode) {
			
			Heap<ASNode> openSet = new Heap<ASNode>();
			HashSet<ASNode> closedSet = new HashSet<ASNode>();
			
			startNode.setOpen();
			openSet.add(startNode);
			
			startNode.setgCost(0);
			startNode.sethCost(GetDistance(startNode, targetNode));
			
			while(!openSet.isEmpty()){
				ASNode current = openSet.remove();
				if(current.equals(targetNode)){
					List<ASNode> path = BacktrackPath(targetNode);
					System.out.println("Path found from " + startNode.getGridX() + ", " + startNode.getGridY() +
							" to " + targetNode.getGridX() + ", " + targetNode.getGridY());
					System.out.println("In " + path.size() + " steps");
					return;
				}
				
				current.setClosed();
				closedSet.add(current);
				
				Set<ASNode> neighbours = getNeighbours(current);
				for(ASNode neighbour : neighbours){
					if(!neighbour.isClosed()){
						double tentativeCost = current.getgCost() + GetDistance(current, neighbour);
						
						if( (!neighbour.isOpen()) || (tentativeCost < neighbour.getgCost()) ){
							
							neighbour.setNodeParent(current);
							
							neighbour.setgCost(tentativeCost);
							neighbour.sethCost(GetDistance(neighbour, startNode));
							if(!neighbour.isOpen())
								neighbour.setOpen();
								openSet.add(neighbour);
							}
					}
						
				}
			}
			
			return;
			
		}
	
	public Set<ASNode> getNeighbours(ASNode node){
		Set<ASNode> neighbours = new HashSet<ASNode>();
		for(int i=-1; i<=1; i++){
			for(int j=-1; j<=1; j++){
				
				if(i==0 && j==0) continue;
				
				int checkX = node.getGridX() + i;
				int checkY = node.getGridY() + j;
				if(checkX >= 0 && checkX < gridSizeX && checkY >= 0 && checkY < gridSizeX){
					ASNode n = (ASNode) gridArray[checkX][checkY];
					if (n.walkable) neighbours.add(n);
				}
					
			}
		}
		return neighbours;
	}
	
	public List<ASNode> BacktrackPath(ASNode targetNode) {
			
		ArrayList<ASNode> path = new ArrayList<ASNode>();
		ASNode current = targetNode;
		
		while(current.getNodeParent() != null){
			current.setNodeStyle(Color.TURQUOISE);
			path.add(current.getNodeParent());
			current = current.getNodeParent();
		}
		Collections.reverse(path);
		
		for (ASNode node : path) {
			node.setNodeStyle(Color.BLACK);
		}
		
		return path;
		}
	
	private double GetDistance(ASNode from, ASNode to) {
		
		return Math.pow(Math.pow(from.getGridX()-to.getGridX(), 2) + Math.pow(from.getGridY() - to.getGridY(), 2) , 0.5);
		
	}
	
	public int getGridSizeX() {
		return this.gridSizeX;
	}
	
	public int getGridSizeY() {
		return this.gridSizeY;
	}
	
	public void setGridNode(int x, int y, ASNode node) {
		this.gridArray[x][y] = node;
	}
	
	public ASNode getGridNode(int x, int y) {
		return (ASNode) this.gridArray[x][y];
	}


}
