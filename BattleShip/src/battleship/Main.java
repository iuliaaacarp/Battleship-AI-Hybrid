package battleship;
import java.util.Scanner;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class Main {

    public static void promptEnterKey(Scanner input) {
        System.out.println("\nPress Enter and pass the move to another player");
        System.out.print("...");
        input.nextLine();
        input.nextLine();
    }

    private static void placePlayerShip(GameBoard gameBoard, String player, Scanner input) {
        System.out.println(player + ", place your ships on the game field\n");
        gameBoard.displayField();

        for (ShipType shipType : ShipType.values()) {

            System.out.println("\nEnter the coordinates of the " + shipType.getName() + " (" + shipType.getLength() + " cells):");
            while (true) {
                System.out.print("> ");

                String x = input.next();
                String y = input.next();

                Ship ship = new Ship(shipType, x, y);
                boolean done = ship.placeShip(gameBoard, shipType);

                if (done) {
                    gameBoard.addShip(ship);
                    System.out.println();
                    break;
                }
            }
            gameBoard.displayField();
        }
    }

    private static void placeAIShip(GameBoard gameBoard) {
        java.util.Random rand = new java.util.Random();
        System.out.println("The computer is placing its ships...\n");

        for (ShipType shipType : ShipType.values()) {
            boolean done = false;
            while (!done) {
                int startRow = rand.nextInt(10);
                int startCol = rand.nextInt(10) + 1;
                boolean isHorizontal = rand.nextBoolean();

                int endRow = startRow;
                int endCol = startCol;

                if (isHorizontal) {
                    endCol = startCol + shipType.getLength() - 1;
                } else {
                    endRow = startRow + shipType.getLength() - 1;
                }

                if (endCol > 10 || endRow > 9) continue;

                String startC = "" + (char)('A' + startRow) + startCol;
                String endC = "" + (char)('A' + endRow) + endCol;

                Ship aiShip = new Ship(shipType, startC, endC);
                if (aiShip.checkCoords(gameBoard) == 0){
                    aiShip.placeShip(gameBoard, shipType);
                    gameBoard.addShip(aiShip);
                    done = true;
                }
            }
        }
        System.out.println("The computer has placed its ships! Ready for battle!\n");
    }

    private static String makeAIMove(String playerShot) throws Exception{
        try {
            JSONObject requestData = new JSONObject();
            requestData.put("player_shot", playerShot != null ? playerShot : "START");

            HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8000/take-turn"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestData.toString()))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());

            return jsonResponse.getString("ai_counter_attack");
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return "A1";
        }
    }

    static void main() throws Exception {
        Scanner input = new Scanner(System.in);
        GameBoard gameBoard1 = new GameBoard();
        GameBoard gameBoard2 = new GameBoard();

        placePlayerShip(gameBoard1, "Player 1", input);
        promptEnterKey(input);

        placeAIShip(gameBoard2);

        boolean isPlayer1Turn = true;
        String lastPlayerShot = "";

        while (true) {
            GameBoard currGameboard = isPlayer1Turn ? gameBoard1 : gameBoard2;
            GameBoard opponentGameBoard = isPlayer1Turn ? gameBoard2 : gameBoard1;
            String currPlayerName = isPlayer1Turn ? "Player 1" : "Player 2";

            System.out.println();
            opponentGameBoard.displayFog();
            System.out.println("---------------------");
            currGameboard.displayField();

            if (isPlayer1Turn) {
                System.out.println("\n> " + currPlayerName + ", it's your turn\n");
            } else {
                System.out.println("\n>> It's computer's turn:\n");
            }

            while (true) {
                String shoot;
                if (isPlayer1Turn) {
                    System.out.print("> ");
                    shoot = input.next();
                    lastPlayerShot = shoot;
                } else{
                    shoot = makeAIMove(lastPlayerShot);
                    System.out.println("> " + shoot + "\n");
                }

                int res = opponentGameBoard.shootBoat(shoot);
                opponentGameBoard.displayMessage(res);

                if (res != -1){
                    if (res == 3){
                        input.close();
                        return;
                    }
                    break;
                }
            }
            if (isPlayer1Turn) {
                promptEnterKey(input);
            }
            isPlayer1Turn = !isPlayer1Turn;
        }
    }
}
