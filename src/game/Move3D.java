package game;

import java.util.ArrayList;

import javafx.scene.paint.Color;

/**
 * Move3D.
 *
 * @author Kiaan Castillo A01024604
 * @version 2018
 */
public class Move3D extends Move {
    
    /**
     * The number 7.
     */
    public static final int SEVEN = 7;
    /**
     * The number 6.
     */
    public static final int SIX = 6;
    /**
     * The number 5.
     */
    public static final int FIVE = 5;
    /**
     * The number 4.
     */
    public static final int FOUR = 4;
    /**
     * The number 3.
     */
    public static final int THREE = 3;
    /**
     * Dimensions of the board.
     */
    public static final int EIGHT = 8;

    protected Board3D layer1;
    
    protected Board3D layer2;
    
    protected Board3D layer3;
    
    protected Tile3D current;
    
    protected Tile3D[][] dataBoard1;
    
    protected Tile3D[][] dataBoard2;
    
    protected Tile3D[][] dataBoard3;
    
    protected Tile3D[][] placeHolder;
    
    protected Tile3D[][] placeHolder2;
    
    protected ArrayList<Tile3D> possibleMoves3D;
    
    protected int currentLayer;
    
    protected static String turn;
    
    private int boardDifference1;
    
    private int boardDifference2;
    
    public Move3D() {
        dataBoard1 = new Tile3D[EIGHT][EIGHT];
        dataBoard2 = new Tile3D[EIGHT][EIGHT];
        dataBoard3 = new Tile3D[EIGHT][EIGHT];
        placeHolder = new Tile3D[EIGHT][EIGHT];
        placeHolder2 = new Tile3D[EIGHT][EIGHT];
        possibleMoves3D = new ArrayList<Tile3D>();
        processing = false;
        current = null;
        currentX = -1;
        currentY = -1;
        firstMove = true;
        colour = null;
        currentLayer = -1;
        turn = "White";
    }
    
    protected void setBoards(Board3D layer1, Board3D layer2, Board3D layer3) {
        this.layer1 = layer1;
        this.layer2 = layer2;
        this.layer3 = layer3;
    }
    
    protected void addtoBoard(int x, int y, Tile3D tile3D, int layer) {
        if (layer == 1) {
            dataBoard1[x][y] = tile3D;
        } else if (layer == 2) {
            dataBoard2[x][y] = tile3D;
        } else {
            dataBoard3[x][y] = tile3D;
        }
    }
    
    /**
     * Switches the turn.
     */
    protected static void switchTurns() {
        if (turn.equals("White")) {
            turn = "Black";
        } else {
            turn = "White";
        }
    }
    
    protected void createPieces() {
        String[] pieceNames = {"Rook", "Knight", "Bishop", "King", 
                "Queen", "Bishop", "Knight", "Rook"};
        String colour = "Black";      

        for (int x = 0; x < 1; x++) {
            for (int y = 0; y < EIGHT; y++) {
                Tile3D temp = dataBoard1[x][y];
                String currPiece = pieceNames[y];
                temp.setPiece(currPiece, colour);
                temp.setOccupied(true);
            }
        }

        for (int y = 0; y < EIGHT; y++) {
            Tile3D temp = dataBoard1[1][y];
            String currPiece = "Pawn";
            temp.setPiece(currPiece, colour);
            temp.setOccupied(true);
        }

        colour = "White";

        for (int x = SEVEN; x < EIGHT; x++) {
            for (int y = 0; y < EIGHT; y++) {
                Tile3D temp = dataBoard1[x][y];
                String currPiece = pieceNames[y];
                temp.setPiece(currPiece, colour);
                temp.setOccupied(true);
            }
        }

        for (int y = 0; y < EIGHT; y++) {
            Tile3D temp = dataBoard1[SIX][y];
            String currPiece = "Pawn";
            temp.setPiece(currPiece, colour);
            temp.setOccupied(true);
        }
    }
    
    protected void calculate(Tile3D current) {
        this.current = current;
        processing = true;
        firstMove = current.piece.getFirstMove();
        currentX = current.getXCoordinate();
        currentY = current.getYCoordinate();
        colour = current.getColour();
        currentLayer = current.getCurrentLayer();
        String type = current.getType();

        if (type.equals("Pawn")) {
            pawn();
        } else if (type.equals("Bishop")) {
            bishop();
        } else if (type.equals("Rook")) {
            rook();
        } else if (type.equals("Knight")) {
            knight();
        } else if (type.equals("Queen")) {
            queen();
        } else {
            king();
        }
    }
    
    /**
     * Movement calculations for the PAWN.
     */
    protected void pawn() {
        if (currentLayer == 1) {
            placeHolder = dataBoard1;
        } else if (currentLayer == 2) {
            placeHolder = dataBoard2;
        } else {
            placeHolder = dataBoard3;
        }
        
        int max = 2;
        if (colour.equals("White")) {
            if (firstMove) {
                max = THREE; 
            } 
            
            for (int counter = 1; counter < max; counter++) {
                if (currentX - counter > -1) {
                    if (!placeHolder[currentX - counter]
                            [currentY].getOccupied()) {
                        possibleMoves3D.add(placeHolder[currentX - counter]
                                [currentY]);
                    } else {
                        break;
                    }
                }
            }
            
            if (currentX - 1 > -1 & currentY + 1 < EIGHT) {
                if (placeHolder[currentX - 1][currentY + 1].getOccupied()) {
                    if (oppositeTeam(placeHolder[currentX - 1][currentY + 1])) {
                        possibleMoves3D.add(placeHolder[currentX - 1]
                                [currentY + 1]);
                    }
                }
            }
            
            if (currentX - 1 > -1 & currentY - 1 > -1) {
                if (placeHolder[currentX - 1][currentY - 1].getOccupied()) {
                    if (oppositeTeam(placeHolder[currentX - 1][currentY - 1])) {
                        possibleMoves3D.add(placeHolder[currentX - 1]
                                [currentY - 1]);
                    }
                }
            }
            
        } else {
            if (firstMove) {
                max = THREE; 
            } 
            
            for (int counter = 1; counter < max; counter++) {
                if (currentX + counter < EIGHT) {
                    if (!placeHolder[currentX + counter]
                            [currentY].getOccupied()) {
                        possibleMoves3D.add(placeHolder[currentX + counter]
                                [currentY]);
                    } else {
                        break;
                    }
                }
            }
            
            if (currentX + 1 < EIGHT & currentY + 1 < EIGHT) {
                if (placeHolder[currentX + 1][currentY + 1].getOccupied()) {
                    if (oppositeTeam(placeHolder[currentX + 1][currentY + 1])) {
                        possibleMoves3D.add(placeHolder[currentX + 1]
                                [currentY + 1]);
                    }
                }
            }
            
            if (currentX + 1 < EIGHT & currentY - 1 > -1) {
                if (placeHolder[currentX + 1][currentY - 1].getOccupied()) {
                    if (oppositeTeam(placeHolder[currentX + 1][currentY - 1])) {
                        possibleMoves3D.add(placeHolder[currentX + 1]
                                [currentY - 1]);
                    }
                }
            }
        }
        
        pawn3D();
        
        for (int counter = 0; counter < possibleMoves3D.size(); counter++) {
            possibleMoves3D.get(counter).setStroke(Color.DODGERBLUE);
        }
    }

    private void pawn3D() {
        if (currentLayer == 1) {
            placeHolder = dataBoard2;
            boardDifference1 = 1;
            placeHolder2 = dataBoard3;
            boardDifference2 = 2;
        } else if (currentLayer == 2) {
            placeHolder = dataBoard1;
            boardDifference1 = 1;
            placeHolder2 = dataBoard3;
            boardDifference2 = 1;
        } else {
            placeHolder = dataBoard1;
            boardDifference1 = 2;
            placeHolder2 = dataBoard2;
            boardDifference2 = 1;
        }
        
        int max = 2;
        if (colour.equals("White")) {
            if (boardDifference1 > 1) {
                if (firstMove) {
                    max = THREE; 
                } 
            }
            
            for (int counter = 1; counter < max; counter++) {
                if (currentX - counter > -1) {
                    if (!placeHolder[currentX - counter]
                            [currentY].getOccupied()) {
                        possibleMoves3D.add(placeHolder[currentX - counter]
                                [currentY]);
                    } else {
                        break;
                    }
                }
            }
            
            if (currentX - 1 > -1 & currentY + 1 < EIGHT) {
                if (placeHolder[currentX - 1][currentY + 1].getOccupied()) {
                    if (oppositeTeam(placeHolder[currentX - 1][currentY + 1])) {
                        possibleMoves3D.add(placeHolder[currentX - 1]
                                [currentY + 1]);
                    }
                }
            }
            
            if (currentX - 1 > -1 & currentY - 1 > -1) {
                if (placeHolder[currentX - 1][currentY - 1].getOccupied()) {
                    if (oppositeTeam(placeHolder[currentX - 1][currentY - 1])) {
                        possibleMoves3D.add(placeHolder[currentX - 1]
                                [currentY - 1]);
                    }
                }
            }
            
        } else {
            if (boardDifference1 > 1) {
                if (firstMove) {
                    max = THREE; 
                } 
            }
            
            for (int counter = 1; counter < max; counter++) {
                if (currentX + counter < EIGHT) {
                    if (!placeHolder[currentX + counter]
                            [currentY].getOccupied()) {
                        possibleMoves3D.add(placeHolder[currentX + counter]
                                [currentY]);
                    } else {
                        break;
                    }
                }
            }
            
            if (currentX + 1 < EIGHT & currentY + 1 < EIGHT) {
                if (placeHolder[currentX + 1][currentY + 1].getOccupied()) {
                    if (oppositeTeam(placeHolder[currentX + 1][currentY + 1])) {
                        possibleMoves3D.add(placeHolder[currentX + 1]
                                [currentY + 1]);
                    }
                }
            }
            
            if (currentX + 1 < EIGHT & currentY - 1 > -1) {
                if (placeHolder[currentX + 1][currentY - 1].getOccupied()) {
                    if (oppositeTeam(placeHolder[currentX + 1][currentY - 1])) {
                        possibleMoves3D.add(placeHolder[currentX + 1]
                                [currentY - 1]);
                    }
                }
            }
        }
        
        placeHolder = placeHolder2;
        
        max = 2;
        if (colour.equals("White")) {
            if (boardDifference2 > 1) {
                if (firstMove) {
                    max = THREE; 
                } 
            }
            
            for (int counter = 1; counter < max; counter++) {
                if (currentX - counter > -1) {
                    if (!placeHolder[currentX - counter]
                            [currentY].getOccupied()) {
                        possibleMoves3D.add(placeHolder[currentX - counter]
                                [currentY]);
                    } else {
                        break;
                    }
                }
            }
            
            if (currentX - 1 > -1 & currentY + 1 < EIGHT) {
                if (placeHolder[currentX - 1][currentY + 1].getOccupied()) {
                    if (oppositeTeam(placeHolder[currentX - 1][currentY + 1])) {
                        possibleMoves3D.add(placeHolder[currentX - 1]
                                [currentY + 1]);
                    }
                }
            }
            
            if (currentX - 1 > -1 & currentY - 1 > -1) {
                if (placeHolder[currentX - 1][currentY - 1].getOccupied()) {
                    if (oppositeTeam(placeHolder[currentX - 1][currentY - 1])) {
                        possibleMoves3D.add(placeHolder[currentX - 1]
                                [currentY - 1]);
                    }
                }
            }
            
        } else {
            if (boardDifference2 > 1) {
                if (firstMove) {
                    max = THREE; 
                } 
            }
            
            for (int counter = 1; counter < max; counter++) {
                if (currentX + counter < EIGHT) {
                    if (!placeHolder[currentX + counter]
                            [currentY].getOccupied()) {
                        possibleMoves3D.add(placeHolder[currentX + counter]
                                [currentY]);
                    } else {
                        break;
                    }
                }
            }
            
            if (currentX + 1 < EIGHT & currentY + 1 < EIGHT) {
                if (placeHolder[currentX + 1][currentY + 1].getOccupied()) {
                    if (oppositeTeam(placeHolder[currentX + 1][currentY + 1])) {
                        possibleMoves3D.add(placeHolder[currentX + 1]
                                [currentY + 1]);
                    }
                }
            }
            
            if (currentX + 1 < EIGHT & currentY - 1 > -1) {
                if (placeHolder[currentX + 1][currentY - 1].getOccupied()) {
                    if (oppositeTeam(placeHolder[currentX + 1][currentY - 1])) {
                        possibleMoves3D.add(placeHolder[currentX + 1]
                                [currentY - 1]);
                    }
                }
            }
        }
    }
    
    /**
     * Movement calculations for the BISHOP.
     */
    protected void bishop() {
        if (currentLayer == 1) {
            placeHolder = dataBoard1;
        } else if (currentLayer == 2) {
            placeHolder = dataBoard2;
        } else {
            placeHolder = dataBoard3;
        }
        
        int tempX = currentX;
        int tempY = currentY;

        tempX += 1;
        tempY += 1;
        while (tempX < EIGHT & tempY < EIGHT) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
                tempX++;
                tempY++;
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
                break;
            }
        }

        tempX = currentX + 1;
        tempY = currentY - 1;

        while (tempX < EIGHT & tempY > -1) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
                tempX++;
                tempY--;
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
                break;
            }
        }

        tempX = currentX - 1;
        tempY = currentY + 1;

        while (tempX > -1 & tempY < EIGHT) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
                tempX--;
                tempY++;
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
                break;
            }
        }

        tempX = currentX - 1;
        tempY = currentY - 1;

        while (tempX > -1 & tempY > -1) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
                tempX--;
                tempY--;
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
                break;
            }
        }

        bishop3D();
        
        for (int counter = 0; counter < possibleMoves3D.size(); counter++) {
            possibleMoves3D.get(counter).setStroke(Color.DODGERBLUE);
        }
    }
    
    private void bishop3D() {
        if (currentLayer == 1) {
            placeHolder = dataBoard2;
            boardDifference1 = 1;
            placeHolder2 = dataBoard3;
            boardDifference2 = 2;
        } else if (currentLayer == 2) {
            placeHolder = dataBoard1;
            boardDifference1 = 1;
            placeHolder2 = dataBoard3;
            boardDifference2 = 1;
        } else {
            placeHolder = dataBoard1;
            boardDifference1 = 2;
            placeHolder2 = dataBoard2;
            boardDifference2 = 1;
        }
        
        int tempX = currentX;
        int tempY = currentY;

        tempX += 1;
        tempY += 1;
        if (tempX < EIGHT & tempY < EIGHT) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
            }
        }

        tempX = currentX + 1;
        tempY = currentY - 1;

        if (tempX < EIGHT & tempY > -1) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
            }
        }

        tempX = currentX - 1;
        tempY = currentY + 1;

        if (tempX > -1 & tempY < EIGHT) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
            }
        }

        tempX = currentX - 1;
        tempY = currentY - 1;

        if (tempX > -1 & tempY > -1) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
                tempX--;
                tempY--;
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
            }
        }
        
        if (boardDifference1 > 1) {
            tempX = currentX;
            tempY = currentY;

            tempX += 2;
            tempY += 2;
            if (tempX < EIGHT & tempY < EIGHT) {
                if (!placeHolder[tempX][tempY].getOccupied()) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                } else {
                    if (oppositeTeam(placeHolder[tempX][tempY])) {
                        possibleMoves3D.add(placeHolder[tempX][tempY]);
                    }
                }
            }

            tempX = currentX + 2;
            tempY = currentY - 2;

            if (tempX < EIGHT & tempY > -1) {
                if (!placeHolder[tempX][tempY].getOccupied()) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                } else {
                    if (oppositeTeam(placeHolder[tempX][tempY])) {
                        possibleMoves3D.add(placeHolder[tempX][tempY]);
                    }
                }
            }

            tempX = currentX - 2;
            tempY = currentY + 2;

            if (tempX > -1 & tempY < EIGHT) {
                if (!placeHolder[tempX][tempY].getOccupied()) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                } else {
                    if (oppositeTeam(placeHolder[tempX][tempY])) {
                        possibleMoves3D.add(placeHolder[tempX][tempY]);
                    }
                }
            }

            tempX = currentX - 2;
            tempY = currentY - 2;

            if (tempX > -1 & tempY > -1) {
                if (!placeHolder[tempX][tempY].getOccupied()) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                    tempX--;
                    tempY--;
                } else {
                    if (oppositeTeam(placeHolder[tempX][tempY])) {
                        possibleMoves3D.add(placeHolder[tempX][tempY]);
                    }
                }
            }
        }
        
        placeHolder = placeHolder2;
        
        tempX = currentX;
        tempY = currentY;

        tempX += 1;
        tempY += 1;
        if (tempX < EIGHT & tempY < EIGHT) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
            }
        }

        tempX = currentX + 1;
        tempY = currentY - 1;

        if (tempX < EIGHT & tempY > -1) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
            }
        }

        tempX = currentX - 1;
        tempY = currentY + 1;

        if (tempX > -1 & tempY < EIGHT) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
            }
        }

        tempX = currentX - 1;
        tempY = currentY - 1;

        if (tempX > -1 & tempY > -1) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
                tempX--;
                tempY--;
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
            }
        }
        
        if (boardDifference2 > 1) {
            tempX = currentX;
            tempY = currentY;

            tempX += 2;
            tempY += 2;
            if (tempX < EIGHT & tempY < EIGHT) {
                if (!placeHolder[tempX][tempY].getOccupied()) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                } else {
                    if (oppositeTeam(placeHolder[tempX][tempY])) {
                        possibleMoves3D.add(placeHolder[tempX][tempY]);
                    }
                }
            }

            tempX = currentX + 2;
            tempY = currentY - 2;

            if (tempX < EIGHT & tempY > -1) {
                if (!placeHolder[tempX][tempY].getOccupied()) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                } else {
                    if (oppositeTeam(placeHolder[tempX][tempY])) {
                        possibleMoves3D.add(placeHolder[tempX][tempY]);
                    }
                }
            }

            tempX = currentX - 2;
            tempY = currentY + 2;

            if (tempX > -1 & tempY < EIGHT) {
                if (!placeHolder[tempX][tempY].getOccupied()) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                } else {
                    if (oppositeTeam(placeHolder[tempX][tempY])) {
                        possibleMoves3D.add(placeHolder[tempX][tempY]);
                    }
                }
            }

            tempX = currentX - 2;
            tempY = currentY - 2;

            if (tempX > -1 & tempY > -1) {
                if (!placeHolder[tempX][tempY].getOccupied()) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                    tempX--;
                    tempY--;
                } else {
                    if (oppositeTeam(placeHolder[tempX][tempY])) {
                        possibleMoves3D.add(placeHolder[tempX][tempY]);
                    }
                }
            }
        }
    }
    
    /**
     * Movement calculations for the ROOK.
     */
    protected void rook() {
        if (currentLayer == 1) {
            placeHolder = dataBoard1;
        } else if (currentLayer == 2) {
            placeHolder = dataBoard2;
        } else {
            placeHolder = dataBoard3;
        }
        
        int tempX = currentX + 1;
        int tempY = currentY;

        while (tempX < EIGHT) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
                tempX++;
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
                break;
            }
        }

        tempX = currentX - 1;

        while (tempX > -1) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
                tempX--;
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
                break;
            }
        }

        tempX = currentX;
        tempY += 1;

        while (tempY < EIGHT) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
                tempY++;
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
                break;
            }
        }

        tempY = currentY - 1;

        while (tempY > -1) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
                tempY--;
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
                break;
            }
        }
        
        rook3D();

        for (int counter = 0; counter < possibleMoves3D.size(); counter++) {
            this.possibleMoves3D.get(counter).setStroke(Color.DODGERBLUE);
        }
    }
    
    private void rook3D() {
        if (currentLayer == 1) {
            placeHolder = dataBoard2;
            boardDifference1 = 1;
            placeHolder2 = dataBoard3;
            boardDifference2 = 2;
        } else if (currentLayer == 2) {
            placeHolder = dataBoard1;
            boardDifference1 = 1;
            placeHolder2 = dataBoard3;
            boardDifference2 = 1;
        } else {
            placeHolder = dataBoard1;
            boardDifference1 = 2;
            placeHolder2 = dataBoard2;
            boardDifference2 = 1;
        }
        
        int tempX = currentX + 1;
        int tempY = currentY;

        if (tempX < EIGHT) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
            }
        }

        tempX = currentX - 1;

        if (tempX > -1) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
            }
        }

        tempX = currentX;
        tempY += 1;

        if (tempY < EIGHT) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
            }
        }

        tempY = currentY - 1;

        if (tempY > -1) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
            }
        }
        
        if (boardDifference1 > 1) {
            tempX = currentX + 2;
            tempY = currentY;

            if (tempX < EIGHT) {
                if (!placeHolder[tempX][tempY].getOccupied()) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                } else {
                    if (oppositeTeam(placeHolder[tempX][tempY])) {
                        possibleMoves3D.add(placeHolder[tempX][tempY]);
                    }
                }
            }

            tempX = currentX - 2;

            if (tempX > -1) {
                if (!placeHolder[tempX][tempY].getOccupied()) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                } else {
                    if (oppositeTeam(placeHolder[tempX][tempY])) {
                        possibleMoves3D.add(placeHolder[tempX][tempY]);
                    }
                }
            }

            tempX = currentX;
            tempY += 2;

            if (tempY < EIGHT) {
                if (!placeHolder[tempX][tempY].getOccupied()) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                } else {
                    if (oppositeTeam(placeHolder[tempX][tempY])) {
                        possibleMoves3D.add(placeHolder[tempX][tempY]);
                    }
                }
            }

            tempY = currentY - 2;

            if (tempY > -1) {
                if (!placeHolder[tempX][tempY].getOccupied()) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                } else {
                    if (oppositeTeam(placeHolder[tempX][tempY])) {
                        possibleMoves3D.add(placeHolder[tempX][tempY]);
                    }
                }
            }
        }
        
        placeHolder = placeHolder2;
        
        tempX = currentX + 1;
        tempY = currentY;

        if (tempX < EIGHT) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
            }
        }

        tempX = currentX - 1;

        if (tempX > -1) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
            }
        }

        tempX = currentX;
        tempY += 1;

        if (tempY < EIGHT) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
            }
        }

        tempY = currentY - 1;

        if (tempY > -1) {
            if (!placeHolder[tempX][tempY].getOccupied()) {
                possibleMoves3D.add(placeHolder[tempX][tempY]);
            } else {
                if (oppositeTeam(placeHolder[tempX][tempY])) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                }
            }
        }
        
        if (boardDifference2 > 1) {
            tempX = currentX + 2;
            tempY = currentY;

            if (tempX < EIGHT) {
                if (!placeHolder[tempX][tempY].getOccupied()) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                } else {
                    if (oppositeTeam(placeHolder[tempX][tempY])) {
                        possibleMoves3D.add(placeHolder[tempX][tempY]);
                    }
                }
            }

            tempX = currentX - 2;

            if (tempX > -1) {
                if (!placeHolder[tempX][tempY].getOccupied()) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                } else {
                    if (oppositeTeam(placeHolder[tempX][tempY])) {
                        possibleMoves3D.add(placeHolder[tempX][tempY]);
                    }
                }
            }

            tempX = currentX;
            tempY += 2;

            if (tempY < EIGHT) {
                if (!placeHolder[tempX][tempY].getOccupied()) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                } else {
                    if (oppositeTeam(placeHolder[tempX][tempY])) {
                        possibleMoves3D.add(placeHolder[tempX][tempY]);
                    }
                }
            }

            tempY = currentY - 2;

            if (tempY > -1) {
                if (!placeHolder[tempX][tempY].getOccupied()) {
                    possibleMoves3D.add(placeHolder[tempX][tempY]);
                } else {
                    if (oppositeTeam(placeHolder[tempX][tempY])) {
                        possibleMoves3D.add(placeHolder[tempX][tempY]);
                    }
                }
            }
        }
    }

    /**
     * Movement calculations for the KNIGHT.
     */
    protected void knight() {
        if (currentX + 2 < EIGHT & currentY + 1 < EIGHT) {
            if (!placeHolder[currentX + 2][currentY + 1].getOccupied()) {
                possibleMoves3D.add(placeHolder[currentX + 2][currentY + 1]);
                possibleMoves3D.add(dataBoard2[currentX + 2][currentY + 1]);
                possibleMoves3D.add(dataBoard3[currentX + 2][currentY + 1]);
            } else {
                if (oppositeTeam(placeHolder[currentX + 2][currentY + 1])) {
                    possibleMoves3D.add(placeHolder[currentX + 2][currentY + 1]);
                    possibleMoves3D.add(dataBoard2[currentX + 2][currentY + 1]);
                    possibleMoves3D.add(dataBoard3[currentX + 2][currentY + 1]);
                }
            }
        }

        if (currentX + 2 < EIGHT & currentY - 1 > -1) {
            if (!placeHolder[currentX + 2][currentY - 1].getOccupied()) {
                possibleMoves3D.add(placeHolder[currentX + 2][currentY - 1]);
                possibleMoves3D.add(dataBoard2[currentX + 2][currentY - 1]);
                possibleMoves3D.add(dataBoard3[currentX + 2][currentY - 1]);
            } else {
                if (oppositeTeam(placeHolder[currentX + 2][currentY - 1])) {
                    possibleMoves3D.add(placeHolder[currentX + 2][currentY - 1]);
                    possibleMoves3D.add(dataBoard2[currentX + 2][currentY - 1]);
                    possibleMoves3D.add(dataBoard3[currentX + 2][currentY - 1]);
                }
            }
        }

        if (currentX - 2 > -1 & currentY + 1 < EIGHT) {
            if (!placeHolder[currentX - 2][currentY + 1].getOccupied()) {
                possibleMoves3D.add(placeHolder[currentX - 2][currentY + 1]);
                possibleMoves3D.add(dataBoard2[currentX - 2][currentY + 1]);
                possibleMoves3D.add(dataBoard3[currentX - 2][currentY + 1]);
            } else {
                if (oppositeTeam(placeHolder[currentX - 2][currentY + 1])) {
                    possibleMoves3D.add(placeHolder[currentX - 2][currentY + 1]);
                    possibleMoves3D.add(dataBoard2[currentX - 2][currentY + 1]);
                    possibleMoves3D.add(dataBoard3[currentX - 2][currentY + 1]);
                }
            }
        }

        if (currentX - 2 > -1 & currentY - 1 > -1) {
            if (!placeHolder[currentX - 2][currentY - 1].getOccupied()) {
                possibleMoves3D.add(placeHolder[currentX - 2][currentY - 1]);
                possibleMoves3D.add(dataBoard2[currentX - 2][currentY - 1]);
                possibleMoves3D.add(dataBoard3[currentX - 2][currentY - 1]);
            } else {
                if (oppositeTeam(placeHolder[currentX - 2][currentY - 1])) {
                    possibleMoves3D.add(placeHolder[currentX - 2][currentY - 1]);
                    possibleMoves3D.add(dataBoard2[currentX - 2][currentY - 1]);
                    possibleMoves3D.add(dataBoard3[currentX - 2][currentY - 1]);
                }
            }
        }

        if (currentX + 1 < EIGHT & currentY + 2 < EIGHT) {
            if (!placeHolder[currentX + 1][currentY + 2].getOccupied()) {
                possibleMoves3D.add(placeHolder[currentX + 1][currentY + 2]);
                possibleMoves3D.add(dataBoard2[currentX + 1][currentY + 2]);
                possibleMoves3D.add(dataBoard3[currentX + 1][currentY + 2]);
            } else {
                if (oppositeTeam(placeHolder[currentX + 1][currentY + 2])) {
                    possibleMoves3D.add(placeHolder[currentX + 1][currentY + 2]);
                    possibleMoves3D.add(dataBoard2[currentX + 1][currentY + 2]);
                    possibleMoves3D.add(dataBoard3[currentX + 1][currentY + 2]);
                }
            }
        }

        if (currentX - 1 > -1 & currentY + 2 < EIGHT) {
            if (!placeHolder[currentX - 1][currentY + 2].getOccupied()) {
                possibleMoves3D.add(placeHolder[currentX - 1][currentY + 2]);
                possibleMoves3D.add(dataBoard2[currentX - 1][currentY + 2]);
                possibleMoves3D.add(dataBoard3[currentX - 1][currentY + 2]);
            } else {
                if (oppositeTeam(placeHolder[currentX - 1][currentY + 2])) {
                    possibleMoves3D.add(placeHolder[currentX - 1][currentY + 2]);
                    possibleMoves3D.add(dataBoard2[currentX - 1][currentY + 2]);
                    possibleMoves3D.add(dataBoard3[currentX - 1][currentY + 2]);
                }
            }
        }

        if (currentX + 1 < EIGHT & currentY - 2 > -1) {
            if (!placeHolder[currentX + 1][currentY - 2].getOccupied()) {
                possibleMoves3D.add(placeHolder[currentX + 1][currentY - 2]);
                possibleMoves3D.add(dataBoard2[currentX + 1][currentY - 2]);
                possibleMoves3D.add(dataBoard3[currentX + 1][currentY - 2]);
            } else {
                if (oppositeTeam(placeHolder[currentX + 1][currentY - 2])) {
                    possibleMoves3D.add(placeHolder[currentX + 1][currentY - 2]);
                    possibleMoves3D.add(dataBoard2[currentX + 1][currentY - 2]);
                    possibleMoves3D.add(dataBoard3[currentX + 1][currentY - 2]);
                }
            }
        }

        if (currentX - 1 > -1 & currentY - 2 > -1) {
            if (!placeHolder[currentX - 1][currentY - 2].getOccupied()) {
                possibleMoves3D.add(placeHolder[currentX - 1][currentY - 2]);
                possibleMoves3D.add(dataBoard2[currentX - 1][currentY - 2]);
                possibleMoves3D.add(dataBoard3[currentX - 1][currentY - 2]);
            } else {
                if (oppositeTeam(placeHolder[currentX - 1][currentY - 2])) {
                    possibleMoves3D.add(placeHolder[currentX - 1][currentY - 2]);
                    possibleMoves3D.add(dataBoard2[currentX - 1][currentY - 2]);
                    possibleMoves3D.add(dataBoard3[currentX - 1][currentY - 2]);
                }
            }
        }

        for (int counter = 0; counter < possibleMoves3D.size(); counter++) {
            this.possibleMoves3D.get(counter).setStroke(Color.DODGERBLUE);
        }
    }

    /**
     * Movement calculations for the QUEEN.
     */
    protected void queen() {
        rook();
        bishop();
    }

    /**
     * Movement calculations for the KING.
     */
    protected void king() {
        if (currentX + 1 < EIGHT) {
            if (!placeHolder[currentX + 1][currentY].getOccupied()) {
                possibleMoves3D.add(placeHolder[currentX + 1][currentY]); 
                possibleMoves3D.add(dataBoard2[currentX + 1][currentY]); 
                possibleMoves3D.add(dataBoard3[currentX + 1][currentY]); 
            } else {
                if (oppositeTeam(placeHolder[currentX + 1][currentY])) {
                    possibleMoves3D.add(placeHolder[currentX + 1][currentY]);
                    possibleMoves3D.add(dataBoard2[currentX + 1][currentY]); 
                    possibleMoves3D.add(dataBoard3[currentX + 1][currentY]); 
                }
            }
        }

        if (currentX - 1 > -1) {
            if (!placeHolder[currentX - 1][currentY].getOccupied()) {
                possibleMoves3D.add(placeHolder[currentX - 1][currentY]);
                possibleMoves3D.add(dataBoard2[currentX - 1][currentY]); 
                possibleMoves3D.add(dataBoard3[currentX - 1][currentY]); 
            } else {
                if (oppositeTeam(placeHolder[currentX - 1][currentY])) {
                    possibleMoves3D.add(placeHolder[currentX - 1][currentY]);
                    possibleMoves3D.add(dataBoard2[currentX - 1][currentY]); 
                    possibleMoves3D.add(dataBoard3[currentX - 1][currentY]); 
                }
            }
        }

        if (currentY + 1 < EIGHT) {
            if (!placeHolder[currentX][currentY + 1].getOccupied()) {
                possibleMoves3D.add(placeHolder[currentX][currentY + 1]); 
                possibleMoves3D.add(dataBoard2[currentX][currentY + 1]); 
                possibleMoves3D.add(dataBoard3[currentX][currentY + 1]); 
            } else {
                if (oppositeTeam(placeHolder[currentX][currentY + 1])) {
                    possibleMoves3D.add(placeHolder[currentX][currentY + 1]);
                    possibleMoves3D.add(dataBoard2[currentX][currentY + 1]); 
                    possibleMoves3D.add(dataBoard3[currentX][currentY + 1]); 
                }
            }
        }

        if (currentY - 1 > -1) {
            if (!placeHolder[currentX][currentY - 1].getOccupied()) {
                possibleMoves3D.add(placeHolder[currentX][currentY - 1]);
                possibleMoves3D.add(dataBoard2[currentX][currentY - 1]); 
                possibleMoves3D.add(dataBoard3[currentX][currentY - 1]); 
            } else {
                if (oppositeTeam(placeHolder[currentX][currentY - 1])) {
                    possibleMoves3D.add(placeHolder[currentX][currentY - 1]);
                    possibleMoves3D.add(dataBoard2[currentX][currentY - 1]); 
                    possibleMoves3D.add(dataBoard3[currentX][currentY - 1]); 
                }
            }
        }

        if (currentX + 1 < EIGHT & currentY + 1 < EIGHT) {
            if (!placeHolder[currentX + 1][currentY + 1].getOccupied()) {
                possibleMoves3D.add(placeHolder[currentX + 1][currentY + 1]);
                possibleMoves3D.add(dataBoard2[currentX + 1][currentY + 1]); 
                possibleMoves3D.add(dataBoard3[currentX + 1][currentY + 1]); 
            } else {
                if (oppositeTeam(placeHolder[currentX + 1][currentY + 1])) {
                    possibleMoves3D.add(placeHolder[currentX + 1][currentY + 1]);
                    possibleMoves3D.add(dataBoard2[currentX + 1][currentY + 1]); 
                    possibleMoves3D.add(dataBoard3[currentX + 1][currentY + 1]); 
                }
            }
        }

        if (currentX + 1 < EIGHT & currentY - 1 > -1) {
            if (!placeHolder[currentX + 1][currentY - 1].getOccupied()) {
                possibleMoves3D.add(placeHolder[currentX + 1][currentY - 1]);
                possibleMoves3D.add(dataBoard2[currentX + 1][currentY - 1]); 
                possibleMoves3D.add(dataBoard3[currentX + 1][currentY - 1]); 
            } else {
                if (oppositeTeam(placeHolder[currentX + 1][currentY - 1])) {
                    possibleMoves3D.add(placeHolder[currentX + 1][currentY - 1]);
                    possibleMoves3D.add(dataBoard2[currentX + 1][currentY - 1]); 
                    possibleMoves3D.add(dataBoard3[currentX + 1][currentY - 1]); 
                }
            }
        }

        if (currentX - 1 > -1 & currentY - 1 > -1) {
            if (!placeHolder[currentX - 1][currentY - 1].getOccupied()) {
                possibleMoves3D.add(placeHolder[currentX - 1][currentY - 1]);
                possibleMoves3D.add(dataBoard2[currentX - 1][currentY - 1]); 
                possibleMoves3D.add(dataBoard3[currentX - 1][currentY - 1]); 
            } else {
                if (oppositeTeam(placeHolder[currentX - 1][currentY - 1])) {
                    possibleMoves3D.add(placeHolder[currentX - 1][currentY - 1]);
                    possibleMoves3D.add(dataBoard2[currentX - 1][currentY - 1]); 
                    possibleMoves3D.add(dataBoard3[currentX - 1][currentY - 1]); 
                }
            }
        }

        if (currentX - 1 > -1 & currentY + 1 < EIGHT) {
            if (!placeHolder[currentX - 1][currentY + 1].getOccupied()) {
                possibleMoves3D.add(placeHolder[currentX - 1][currentY + 1]);
                possibleMoves3D.add(dataBoard2[currentX - 1][currentY + 1]); 
                possibleMoves3D.add(dataBoard3[currentX - 1][currentY + 1]); 
            } else {
                if (oppositeTeam(placeHolder[currentX - 1][currentY + 1])) {
                    possibleMoves3D.add(placeHolder[currentX - 1][currentY + 1]);
                    possibleMoves3D.add(dataBoard2[currentX - 1][currentY + 1]); 
                    possibleMoves3D.add(dataBoard3[currentX - 1][currentY + 1]); 
                }
            }
        }

        for (int counter = 0; counter < possibleMoves3D.size(); counter++) {
            this.possibleMoves3D.get(counter).setStroke(Color.DODGERBLUE);
        }
    }

    /**
     * Executed once a second click has been made if the processing boolean
     * is set to true.
     * Moves the current TilePiece to the target TilePiece.
     * @param target
     *              the target tile
     */
    public void move(Tile3D target) {
        for (int counter = 0; counter < possibleMoves3D.size(); counter++) {
            if (possibleMoves3D.get(counter) == target) {
                target.setPiece(current.getType(), current.getColour());
                target.piece.setFirstMove(false);
                target.setOccupied(true);
                current.setOccupied(false);
                current.removePiece();
                switchTurns();
                reset();
                break;
            }
        }
    }

    /**
     * Returns true if the tile sent in is a possible move.
     * @param target
     *              the tile being tested
     * @return true if a possible move
     */
    public boolean isPossibleMove(Tile3D target) {
        for (int counter = 0; counter < possibleMoves3D.size(); counter++) {
            if (possibleMoves3D.get(counter) == target) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the tile sent in is on the opposite team from
     * the current tile.
     * @param test
     *          the tile being tested
     * @return true if tile is in the opposite team
     */
    protected boolean oppositeTeam(Tile3D test) {
        if (!test.getColour().equals(colour)) {
            return true;
        }
        return false;
    }

    /**
     * Executed after a successful movement is made. Resets all members to their
     * default values, removes the stroke from all Tiles in the possibleMoves3D 
     * ArrayList
     * and.
     */
    protected void reset() {
        for (int x = 0; x < EIGHT; x++) {
            for (int y = 0; y < EIGHT; y++) {
                Tile3D temp = dataBoard1[x][y];
                temp.setStroke(temp.getFill());
                temp = dataBoard2[x][y];
                temp.setStroke(temp.getFill());
                temp = dataBoard3[x][y];
                temp.setStroke(temp.getFill());
            }
        }

        colour = null;
        current = null;
        currentX = -1;
        currentY = -1;
        possibleMoves3D.clear();
        processing = false;
        currentLayer = -1;
    }
}
