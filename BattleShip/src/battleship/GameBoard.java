package battleship;

import java.util.Arrays;

class GameBoard {
    private final String[][] field;
    private final String[][] fog;

    private final Ship[] fleet;
    private int shipsPlaced = 0;

    public GameBoard() {
        field = new String[11][11];
        fog = new String[11][11];
        fleet = new Ship[5];
        constructField();
    }

    public void constructField(){
        for (int i = 0; i < field.length; i++) {
            Arrays.fill(field[i], "~");
            Arrays.fill(fog[i], "~");
        }
        for (int i = 1; i <= 10; i++) {
            field[0][i] = String.valueOf(i);
            fog[0][i] = String.valueOf(i);
            field[i][0] = String.valueOf((char) ('A' + i - 1));
            fog[i][0] = String.valueOf((char) ('A' + i - 1));
        }
        field[0][0] = " ";
        fog[0][0] = " ";
    }

    public void addShip(Ship ship){
        if (shipsPlaced < 5) {
            fleet[shipsPlaced] = ship;
            shipsPlaced++;
        }
    }

    public boolean isFieldEmpty(int row, int col) {
        return field[row][col].equals("~");
    }

    public boolean isHit(int row, int col) {
        return field[row][col].equals("X");
    }

    public void placeShipOnField(int x, int y){
        field[x][y] = "O";
    }

    public void displayField(){
        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void displayFog(){
        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                System.out.print(fog[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean checkInBoard(int x, int y){
        return x >= 0 && x < 11 && y >= 0 && y < 11;
    }

    public int shootBoat(String coord) {
        int col = Integer.parseInt(coord.substring(1));
        int row = coord.charAt(0) - 'A' + 1;

        if (!checkInBoard(row, col)) {
            return -1;
        }
        else if (field[row][col].equals("~")) {
            field[row][col] = "M";
            fog[row][col] = "M";
            return 0;
        }
        else if (field[row][col].equals("M")) {
            return 0;
        }
        else if (field[row][col].equals("X")) {
            return 1;
        }
        else if (field[row][col].equals("O")) {
            field[row][col] = "X";
            fog[row][col] = "X";

            for (Ship ship : fleet) {
                if (ship != null && ship.occupies(row, col)) {
                    if (ship.isSunk(this)) {
                        if (isGameDone()) {
                            return 3;
                        }
                        return 2;
                    }
                    return 1;
                }
            }
            return 1;
        }
        return -1;
    }

    public void displayMessage(int res){
        if (res == -1){
            System.out.println("Error! You entered the wrong coordinates! Try again:");
        }
        else if (res == 0){
            System.out.println("You missed.");
        }
        else if (res == 1){
            System.out.println("You hit a ship!");
        }
        else if (res == 2){
            System.out.println("You sank a ship!");
        }
        else if (res == 3){
            System.out.println("You sank the last ship. You won. Congratulations!");
        }
        System.out.println();
    }

    public boolean isGameDone() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j].equals("O") && fog[i][j].equals("~")) {
                    return false;
                }
            }
        }
        return true;
    }
}

