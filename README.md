# ⚓ Battleship AI Hybrid: Distributed Game Architecture

A classic Battleship game evolved into a modern distributed application. This project features a robust Object-Oriented Java client that handles local game state and rendering, seamlessly integrated with a Python FastAPI REST backend that serves as an automated AI opponent.

## ✨ Key Features

* **Hybrid Client-Server Architecture:** Transitioned from a local hotseat PvP loop to a distributed system. The Java application communicates over `localhost` with a Python backend using HTTP POST requests and dynamic JSON payloads.
* **Stateful AI Opponent:** The Python server maintains internal game state memory across stateless HTTP requests, tracking its own fleet placement and recording fired shots to generate valid, randomized counter-attacks.
* **The Fog of War:** A dual-board user interface. During your turn, you see your opponent's hidden board at the top (tracking your hits and misses) and your own fully visible board at the bottom.
* **Advanced Coordinate Validation:**
  * Prevents diagonal placements and out-of-bounds errors.
  * Implements a bounding-box algorithm to ensure no two ships are ever touching or overlapping.
  * Provides detailed, specific error messages when a player makes an invalid move, trapping them in a loop until a valid coordinate is entered.
* **Smart Fleet Tracking:** The game doesn't just track hits; it knows exactly which ship was struck. It maps individual coordinate segments to specific objects, tracking the health of individual vessels, announcing when a specific ship is sunk, and detecting the exact moment the final ship goes down to declare a winner.

## 🧠 Technical Architecture & What I Learned

This project served as a deep dive into mastering Object-Oriented Programming (OOP) and cross-language network communication. Key technical highlights include:

* **Cross-Language REST API Integration:** Built a native Java `HttpClient` to transmit gameplay events as JSON payloads to a Python `Uvicorn` server, parsing the API responses using the `org.json` library to update the local game loop.
* **Custom Classes (`Ship`, `GameBoard`):** Separating the logic so that the `GameBoard` handles rendering and grid state, while the `Ship` class manages its own coordinates, health, and boundary validation.
* **Java Enums (`ShipType`):** Utilizing Enums to securely store constant data (like ship names and lengths: Aircraft Carrier (5), Battleship (4), etc.) rather than hardcoding values throughout the application.
* **Memory Management & Arrays:** Extensive use of 2D arrays (`String[][]`) to map the X and Y coordinates of the game grids.
* **Input Sanitization:** Using Java's `Scanner` class to parse user input, converting alphanumeric grid coordinates (e.g., "F3") into readable array indexes while handling exceptions.

## 🚀 Getting Started

To run this project locally, you will need to start both the Python AI server and the Java game client.

### 1. Start the Python AI Backend
Ensure you have Python 3 installed, navigate to the `p1` directory, and install the required FastAPI dependencies:
```bash
cd p1
pip install fastapi uvicorn pydantic
```
Start the local server:
```
uvicorn server:app --reload
```
The server will run on http://127.0.0.1:8000 and wait for API requests.

### 2. Launch the Java Game Client
Ensure you have Java 11+ installed. You will also need the org.json library included in your classpath.
Navigate to the BattleShip directory, compile, and run the Main class:
```
cd BattleShip/src
javac -cp .:/path/to/json.jar battleship/*.java
java -cp .:/path/to/json.jar battleship.Main
```
Place your ships via the terminal prompts, and the AI will automatically deploy its fleet and counter-attack via the REST API.
