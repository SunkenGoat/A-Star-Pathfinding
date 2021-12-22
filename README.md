# A* Pathfinding

This project is a visualiser to display the A* Pathfinding Algorithm.

Graphics were handled with JavaFX.

What I learnt...

- Basic understanding of JavaFX and how to maniuplate scenes
- Greater understanding of time/space complexity for algorithms
- Understanding heaps to better optimise the algorithm

What improvements to make?

- More optimisations on the algorithm
- Restructuring code to be cleaner and introduce seperation of graphics and business logic
- Remove using GridPane to display nodes, and instead use the JavaFX Canvas. ASNode inherits from Region which is using unnessary memory. A better alternative is to render the individual pixels onto an image.
- Add more pathfinding algorithm options, including Dijkstra, Breadth/Depth First, etc.
