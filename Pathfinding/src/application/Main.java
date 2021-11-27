package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

	Point2D gridWorldSize = new Point2D(200, 200);
	public int gridSizeX;
	public int gridSizeY;
	
	public float nodeRadius = 10f;
	
	public Node[][] gridArray = null;
	public GridPane grid = new GridPane();
	
	public ASNode startNode;
	public ASNode targetNode;
	public boolean running = false;
	
	@Override
	public void start(Stage primaryStage) {
		
		float nodeDiameter = nodeRadius * 2;
		
		gridSizeX = (int) Math.round(gridWorldSize.getX() / nodeDiameter);
		gridSizeY = (int) Math.round(gridWorldSize.getY() / nodeDiameter);

		System.out.println(gridSizeX + gridSizeY);
		
		this.gridArray = new ASNode[gridSizeX][gridSizeY];

		
		
		grid.setOnKeyPressed(
				event -> {
					if (event.getCode() == KeyCode.SPACE && !running) {
						System.out.println("Running");
						FindPath();
						running = true;
						event.consume();
					}
				});
		
		grid.setOnDragDetected(
				event -> {
					if (event.getButton() == MouseButton.PRIMARY) {
						event.consume();
						grid.startFullDrag();
					} else if (event.getButton() == MouseButton.SECONDARY) {
						event.consume();
						grid.startFullDrag();
					}
				});

		for (int i = 0; i < gridSizeX; i++) {
			for (int j = 0; j < gridSizeY; j++) {
				
				final ASNode node = new ASNode(true, i, j);
				node.setStyle(getStyle(Color.BEIGE));
				
				node.setOnMouseDragEntered(
						event -> {
							if (event.getButton() == MouseButton.PRIMARY) {
								event.consume();
								if (startNode == null && node != targetNode) {
									node.setStyle(getStyle(Color.ORANGE));
									this.startNode = node;
								} else if (targetNode == null && node != startNode) {
									node.setStyle(getStyle(Color.BLUEVIOLET));
									this.targetNode = node;
								} else if (node != startNode && node != targetNode) {
									node.setStyle(getStyle(Color.RED));
									node.walkable = false;
								}
								
							} else {
								event.consume();
								node.setStyle(getStyle(Color.BEIGE));
								if (node == startNode) {
									this.startNode = null;
								} else if (node == targetNode) {
									this.targetNode = null;
								}
								node.walkable = true;
							}
							
						});
				
				this.gridArray[i][j] = node;
				grid.add(node, i, j);
			}
		}

		primaryStage.setTitle("A* Pathfinding Algorithm");

		primaryStage.setResizable(false);
		Scene scene = new Scene(grid, gridWorldSize.getX(), gridWorldSize.getY(), Color.DARKSLATEGRAY);

//		Region n = (Region) gridArray[10][1];
//		n.setStyle(getStyle(Color.RED));
//		gridArray[20][23].setStyle(getStyle(Color.TOMATO));
		

		primaryStage.setScene(scene);
		primaryStage.show();
		
		grid.setFocusTraversable(true);
		grid.requestFocus();

	}

	private void FindPath() {
		
		PriorityQueue<ASNode> openSet = new PriorityQueue<ASNode>(new Comparator<ASNode>() {

			@Override
			public int compare(ASNode o1, ASNode o2) {
				int compare = Integer.compare(o1.getfCost(), o2.getfCost());
				if (compare == 0) {
					compare = Integer.compare(o1.hCost, o2.hCost);
				}
				System.out.println(-compare);
				return -compare;
			}
		});
		HashSet<ASNode> closedSet = new HashSet<ASNode>();
		
		openSet.add(startNode);
		
		while (!openSet.isEmpty()) {
			
			ASNode currentNode = openSet.remove();
			
			closedSet.add(currentNode);
			
			if (currentNode == targetNode) {
				BacktrackPath();
				System.out.println("Target node has been found");
			}
			
			for (ASNode neighbour : getNeighbours(currentNode)) {
				
				if (!neighbour.walkable && closedSet.contains(currentNode)) {
					continue;
				}
				
				int newMovementCost = currentNode.gCost + GetDistance(currentNode, neighbour);
				
				if (newMovementCost < neighbour.gCost || !openSet.contains(neighbour)) {
					
					neighbour.gCost = newMovementCost;
					neighbour.hCost = GetDistance(neighbour, targetNode);
					
					neighbour.parent = currentNode;
					
					if (!openSet.contains(neighbour)) {
						openSet.add(neighbour);
					}
					
				}
				
			}
			
		}
		
	}

	private void BacktrackPath() {
		
		List<ASNode> path = new ArrayList<ASNode>();
		
		ASNode currentNode = targetNode;
		
		while (currentNode != startNode) {
			path.add(currentNode);
			currentNode = currentNode.parent;
		}
		
		Collections.reverse(path);
		
		for (ASNode n : path) {
			n.setStyle(getStyle(Color.AQUA));
		}
		
	}

	private int GetDistance(ASNode nodeA, ASNode nodeB) {
		
		int distX = Math.abs(nodeA.gridX - nodeB.gridX);
		int distY = Math.abs(nodeA.gridY - nodeB.gridY);
		
		if (distX > distY) {
			return 14*distY + 10 * (distX - distY);
		}
		
		return 14*distX + 10 * (distY - distX);
		
	}

	private List<ASNode> getNeighbours(ASNode node) {
		
		List<ASNode> neighbours = new ArrayList<ASNode>();
		
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0) {
					continue;
				}
				
				int checkX = node.gridX + i;
				int checkY = node.gridY + j;
				
				if (checkX >= 0 && checkX < gridSizeX && checkY >= 0 && checkY < gridSizeX) {
					neighbours.add((ASNode) gridArray[checkX][checkY]);
				}
				
			}
		}
		return neighbours;
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	private String getStyle(Color color) {
		
		String colorString = color.toString();
		colorString = colorString.replace("0x", "#");
		String style = "-fx-background-color: " + colorString + "; -fx-border-style: solid; -fx-border-width: 1;"
					+ " -fx-border-color: black; -fx-min-width: 20; -fx-min-height:20; -fx-max-width:20;"
					+ " -fx-max-height: 20;";

		return style;
	}
}
