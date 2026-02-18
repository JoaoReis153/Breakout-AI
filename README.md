# Breakout AI 🎮🧠

A Java-based project that explores the intersection of classic arcade gaming and Artificial Intelligence. This project implements a custom version of the game **Breakout** and trains a **Neural Network** using a **Genetic Algorithm** to play it autonomously.

## 🚀 Project Overview

The core objective of this project is to demonstrate how evolutionary algorithms can be used to optimize a neural network for a specific task—in this case, keeping the ball in play and breaking bricks.

### Key Components:
-   **Game Engine**: A custom implementation of Breakout using Java Swing.
-   **Neural Network**: A simple feed-forward network that perceives the game state (Ball position, Paddle position, Brick position) and decides whether to move the paddle Left or Right.
-   **Genetic Algorithm (GA)**: The training mechanism. It evolves a population of neural networks over generations, selecting the "fittest" individuals (those who survive the longest and break the most bricks) to reproduce.

## 🛠️ How to Run

### Prerequisities
-   Java Development Kit (JDK) 8 or higher.

### Compile and Run
1.  **Compile the code**:
    Open your terminal in the project root directory and run:
    ```bash
    javac -d out -sourcepath src src/breakout/Main.java
    ```

2.  **Run the application**:
    ```bash

### Run with IntelliJ IDEA
1.  **Open the Project**:
    -   Launch IntelliJ IDEA.
    -   Select **Open** and choose the `Breakout-AI` folder.

2.  **Configure Project Structure**:
    -   Go to `File > Project Structure`.
    -   Under **Project**, ensure a valid **SDK** (Java 1.8 or higher) is selected.
    -   Under **Modules**, mark the `src` folder as **Sources**. (IntelliJ usually detects this automatically).

3.  **Run the Application**:
    -   Navigate to `src/breakout/Main.java`.
    -   Right-click on the file and select `Run 'Main.main()'`.


## ⚙️ Configuration

You can tweak the behavior of the AI and the game by modifying the `src/utils/Commons.java` file. This file acts as a central configuration hub.

**Key Settings:**

*   **`DEFAULT`**: Set to `true` to run the main Genetic Algorithm loop.
*   **`POPULATION_SIZE`**: The number of neural networks in each generation (Default: 100).
*   **`NUM_GENERATIONS`**: How many generations to train for (Default: 100).
*   **`MUTATION_RATE`**: The probability that a network's weights will be mutated (Default: 0.45).
*   **`SHOWEVERY_50`**: If `true`, a visual game window will open every 50 generations to show progress.

> [!NOTE]
> After changing any value in `Commons.java`, remember to recompile the project.

## 🧠 Educational Aspects & Lessons Learned

### What was used?
*   **Java Swing**: For rendering the game graphics and handling the game loop.
*   **Encapsulation & Inheritance**: Using Object-Oriented Programming (OOP) to structure the Game Board, Ball, Paddle, and Bricks.
*   **Neural Networks**: Implemented from scratch (without external libraries) to understand the math behind layers, weights, biases, and activation functions (Sigmoid).
*   **Genetic Algorithms**: Implemented biologically inspired operators like **Tournament Selection**, **Crossover**, and **Gaussian Mutation**.

### What was learned?
1.  **The Power of Evolution**: It's fascinating to see how random noise (random weights) can evolve into a coherent strategy. In early generations, the paddle moves erratically. By generation 50+, it tracks the ball with surprising accuracy.
2.  **Hyperparameter Sensitivity**: The success of the AI is heavily dependent on values like `MUTATION_RATE`. If it's too high, the AI never settles on a good strategy. If it's too low, it evolves too slowly.
3.  **State Representation**: Deciding *what* the AI sees is crucial. We simplified the input to just 7 numbers (coordinates of the ball, paddle, and target brick). Giving the AI raw pixel data would have required a much more complex network (like a CNN).
4.  **Fitness Function Design**: Defining "success" is tricky. Initially, we just rewarded time survived, but the AI learned to just avoid the ball to keep the game going if possible (or stuck in loops). Adding points for breaking bricks (`kills`) encouraged aggressive play.
