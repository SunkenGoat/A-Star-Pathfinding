package application;

import java.awt.Point;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

	Point gridWorldSize = new Point(840, 840);
	public float nodeRadius = 10f;
	
	public GridPane grid = new GridPane();
	
	public ASNode startNode;
	public ASNode targetNode;
	
	Pathfinding pf = new Pathfinding(this.gridWorldSize, this.nodeRadius);
	
	@Override
	public void start(Stage primaryStage) {
		
		grid.setOnKeyPressed(
				event -> {
					if (event.getCode() == KeyCode.SPACE && this.startNode != null && this.targetNode != null) {
						System.out.println("Running");
						pf.FindPath(startNode, targetNode);
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

		for (int i = 0; i < pf.getGridSizeX(); i++) {
			for (int j = 0; j < pf.getGridSizeY(); j++) {
				
				final ASNode node = new ASNode(true, i, j);
				node.setNodeStyle(Color.BEIGE);
				
				node.setOnMouseDragEntered(
						event -> {
							if (event.getButton() == MouseButton.PRIMARY) {
								event.consume();
								if (startNode == null && node != targetNode) {
									node.setNodeStyle(Color.ORANGE);
									this.startNode = node;
								} else if (targetNode == null && node != startNode) {
									node.setNodeStyle(Color.BLUEVIOLET);
									this.targetNode = node;
								} else if (node != startNode && node != targetNode) {
									node.setNodeStyle(Color.RED);
									node.walkable = false;
								}
								
							} else if (event.getButton() == MouseButton.SECONDARY){
								event.consume();
								node.setNodeStyle(Color.BEIGE);
								if (node == startNode) {
									this.startNode = null;
								} else if (node == targetNode) {
									this.targetNode = null;
								}
								node.walkable = true;
							}
							
						});
				
				pf.setGridNode(i, j, node);
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

	public static void main(String[] args) {
		launch(args);
	}
	
	
}
