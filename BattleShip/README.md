# ⚓ Java Battleship: Command-Line Edition

> A complete, two-player terminal implementation of the classic strategic board game, Battleship. Built entirely in Java to showcase Object-Oriented Programming (OOP) architecture, state management, and algorithmic validation.

## 📖 Project Overview

Tracing its roots back to pencil-and-paper games from the First World War, Battleship is a timeless game of strategy, deduction, and luck. This project brings that classic experience to the command line. 

Designed for two players sharing a single screen, the game handles everything from strict placement rules to the fog of war. Players take turns deploying their fleets, clearing the screen to maintain secrecy, and firing upon the enemy grid until one commander reigns supreme.

This project was built from the ground up as a deep dive into **Java**, focusing on clean class structures, modular design, and robust error handling.

---

## ✨ Key Features

* **🎭 Two-Player PvP (Hotseat):** A fully realized two-player loop with an integrated screen-clearing mechanic that ensures players cannot peek at each other's ship placements.
* **🌫️ The Fog of War:** A dual-board user interface. During your turn, you see your opponent's hidden board at the top (tracking your hits and misses) and your own fully visible board at the bottom.
* **🛡️ Advanced Coordinate Validation:**
  * Prevents diagonal placements and out-of-bounds errors.
  * Implements a **bounding-box algorithm** to ensure no two ships are ever touching or overlapping.
  * Provides detailed, specific error messages when a player makes an invalid move, trapping them in a loop until a valid coordinate is entered.
* **🚢 Smart Fleet Tracking:** The game doesn't just track hits; it knows exactly which ship was struck. It tracks the health of individual vessels, announcing when a specific ship is sunk and detecting the exact moment the final ship goes down to declare a winner.

---

## 🧠 Technical Architecture & What I Learned

This project was a stepping stone in mastering **Object-Oriented Programming (OOP)**. Key technical highlights include:

* **Custom Classes (`Ship`, `GameBoard`):** Separating the logic so that the `GameBoard` handles rendering and grid state, while the `Ship` class manages its own coordinates, health, and validation.
* **Java Enums (`ShipType`):** Utilizing Enums to securely store constant data (like ship names and lengths: *Carrier (5), Battleship (4), etc.*) rather than hardcoding values throughout the application.
* **Memory Management & Arrays:** Extensive use of 2D arrays (`String[][]`) to map the X and Y coordinates of the game grids.
* **Input Sanitization:** Using Java's `Scanner` class to parse user input, converting alphanumeric grid coordinates (e.g., "F3") into readable array indexes while handling exceptions.

---

## 🚀 How to Run the Game Locally

1. **Prerequisites:** Ensure you have the [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) installed on your machine.

2. **Clone the repository:**
   ```bash
   git clone [https://github.com/your-username/battleship-java.git](https://github.com/your-username/battleship-java.git)
