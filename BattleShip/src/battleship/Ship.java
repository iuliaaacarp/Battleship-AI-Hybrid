package battleship;

class Ship {
    public final ShipType type;
    public final String startC;
    public final String endC;

    private final int [][] parts;

    public Ship(ShipType type, String startC, String endC) {
        this.type = type;
        this.startC = startC;
        this.endC = endC;
        this.parts = new int[type.getLength()][2];
    }

    private int getCol(String coord){
        return Integer.parseInt(coord.substring(1));
    }

    private char getRow(String coord){
        return coord.charAt(0);
    }

    public boolean checkSurroundings(GameBoard board){
        int minCol = Math.min(getCol(startC), getCol(endC));
        int maxCol = Math.max(getCol(startC), getCol(endC));
        char minRow = (char) Math.min(getRow(startC), getRow(endC));
        char maxRow = (char) Math.max(getRow(startC), getRow(endC));

        char startRowCheck = (char) Math.max('A', minRow - 1);
        char endRowCheck = (char) Math.min('J', maxRow + 1);
        int startColCheck = Math.max(1, minCol - 1);
        int endColCheck = Math.min(10, maxCol + 1);

        for (char r = startRowCheck; r <= endRowCheck; r++) {
            for (int c = startColCheck; c <= endColCheck; c++) {
                if (!board.isFieldEmpty(r - 'A' + 1, c)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int checkCoords(GameBoard board){
        int col1 = getCol(startC);
        int col2 = getCol(endC);
        char row1 = getRow(startC);
        char row2 = getRow(endC);

        if (col1 < 1 || col1 > 10 || col2 < 1 || col2 > 10) return 1;
        if (row1 < 'A' || row1 > 'J' || row2 < 'A' || row2 > 'J') return 1;
        if (row1 != row2 && col1 != col2)  return 1;

        if (!checkSurroundings(board)) return 2;

        return 0;
    }

    public boolean isSunk(GameBoard board){
        for (int[] part : parts) {
            int row = part[0];
            int col = part[1];
            if (!board.isHit(row, col))
                return false;
        }
        return true;
    }

    public boolean occupies(int hitRow, int hitCol) {
        for (int[] part : parts) {
            if (part[0] == hitRow && part[1] == hitCol) {
                return true;
            }
        }
        return false;
    }

    public boolean placeShip(GameBoard board, ShipType type) {

        if (checkCoords(board) == 1) {
            System.out.println("\nError! Wrong ship location! Try again:");
            return false;
        }
        else if (checkCoords(board) == 2) {
            System.out.println("\nError! You placed it too close to another one. Try again:");
            return false;
        }
        int col1 = getCol(startC);
        int col2 = getCol(endC);
        char row1 = getRow(startC);
        char row2 = getRow(endC);

        int length;
        int partIndex = 0;

        if (row1 == row2) {
            length = Math.abs(col1 - col2) + 1;
            if (length != type.getLength()) {
                System.out.println("\nError! Wrong length of the " + type.getName() + "! Try again:");
                return  false;
            }
            int start = Math.min(col1, col2);
            int end = Math.max(col1, col2);
            for (int i = start; i <= end; i++) {
                board.placeShipOnField(row1 - 'A' + 1, i);

                parts[partIndex][0] = row1 - 'A' + 1;
                parts[partIndex][1] = i;
                partIndex++;
            }
        }
        else {
            length = Math.abs(row1 - row2) + 1;
            if (length != type.getLength()) {
                System.out.println("\nError! Wrong length of the " + type.getName() + "! Try again:");
                return false;
            }
            char start = (char) Math.min(row1, row2);
            char end = (char) Math.max(row1, row2);
            for (char i = start; i <= end; i++) {
                board.placeShipOnField(i - 'A' + 1, col1);

                parts[partIndex][0] = i - 'A' + 1;
                parts[partIndex][1] = col1;
                partIndex++;
            }
        }

        return true;
    }
}
