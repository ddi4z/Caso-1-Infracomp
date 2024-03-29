## Description
This repository contains the code related to Case 1 of Infracomp.

The project aimed to implement "Conway's Game of Life" on a square matrix of size n×n. The peculiarity was that each cell should have a mailbox to receive the state of its neighboring cells, and these mailboxes had a maximum capacity determined by the cell's row number. The game ran for a specific number of generations, and upon completion, the final state was printed. The additional challenge, besides integrating the mailboxes, was to manage all the behavior using Java threads, which required developing strategies to ensure proper concurrency.

## How to run?
Follow these steps to execute the code on your local machine:

1. Clone the repository to your local machine.
2. Drag the test case file into the root folder of the repository. (Example: `a.in`)
3. Open the `src` folder.
4. Run the `Game.java` file.


