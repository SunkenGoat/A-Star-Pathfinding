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
			
			openSet.add(startNode);
			
			while (!openSet.isEmpty()) {
				
				ASNode currentNode = openSet.remove();
				System.out.println("X:" + currentNode.getGridX() + ", Y: " + currentNode.getGridY());
				
				closedSet.add(currentNode);
				
				if (currentNode == targetNode) {
					System.out.println("Target node has been found at " + targetNode.getGridX() + "," + targetNode.getGridY());
//					Main.setRunning(false);
					BacktrackPath(startNode, targetNode);
					
					return;
				}
				
				for (ASNode neighbour : getNeighbours(currentNode)) {
					
					if (!neighbour.walkable && closedSet.contains(currentNode)) {
						continue;
					}
					
					double newMovementCost = currentNode.getgCost()+ GetDistance(currentNode, neighbour);
					
					if (newMovementCost < neighbour.getgCost() || !openSet.contains(neighbour)) {
						
						neighbour.setgCost(newMovementCost);
						neighbour.sethCost(GetDistance(neighbour, targetNode));;
						// This does not create a path from the startNode to targetNode and instead creates an infinite loop by
						// assigning one node's parent to itself and vice versa. When running Backtracking() this causes the program
						// to crash.
						neighbour.setNodeParent(currentNode);;
						
						
						if (!openSet.contains(neighbour)) {
							openSet.add(neighbour);
						}
						
					}
					
				}
				
			}
			
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
	
	public List<ASNode> BacktrackPath(ASNode startNode, ASNode targetNode) {
			
			ASNode currentNode = targetNode;
			
			List<ASNode> path = new ArrayList<ASNode>();
			
			while (true) {
				System.out.println("X:" + currentNode.getGridX() + ", Y: " + currentNode.getGridY());
				if (currentNode.equals(startNode)) {
					System.out.println("Holo");
					break;
				}
				currentNode.setNodeStyle(Color.AQUA);
				path.add(currentNode);
				currentNode = currentNode.getNodeParent();
			}
			
			Collections.reverse(path);
			
			return path;
			
		}
	
	private double GetDistance(ASNode nodeA, ASNode nodeB) {
		
//		int distX = Math.abs(nodeA.gridX - nodeB.gridX);
//		int distY = Math.abs(nodeA.gridY - nodeB.gridY);
//		
//		if (distX > distY) {
//			return (14*distY) + 10*(distX - distY);
//		}
//		
//		return (14*distX) + 10*(distY - distX);
//		
		return Math.pow(10 *Math.pow(nodeA.getGridX()-nodeB.getGridX(), 2) + 10 * Math.pow(nodeA.getGridY() - nodeB.getGridY(), 2) , 0.5);
		
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
