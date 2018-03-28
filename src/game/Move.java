package game;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.paint.Color;

/**
 * Move handles the controls of the pieces and determines what 
 * each piece can do at every situation.
 * The multi-dimension array that holds the state of every 
 * piece lives in this class.
 * There is also another multi-dimensional array that does the same
 * thing as the first one but is serializable.
 *
 * @author Kiaan Castillo A01024604
 * @version 2018
 */
public class Move implements Serializable {

    /**
     * The max for the pawn for loop if firstMove is true.
     */
    public static final int THREE = 3;
    /**
     * The size of the array.
     */
    public static final int EIGHT = 8;
    /**
     * The multi-dimensional array that hold the state of every piece.
     */
    protected Tile[][] dataBoard;
    /**
     * A savable databoard.
     */
    protected SavableTile[][] savableDataBoard;
    /**
     * An arraylist of the current possible moves.
     */
    private ArrayList<Tile> possibleMoves;
    /**
     * The current Tile selected.
     */
    private Tile current;
    /**
     * The x coordinate of the current Tile.
     */
    protected int currentX;
    /**
     * The y coordinate of the current Tile.
     */
    protected int currentY;
    /**
     * True if its this piece's first move. Significant for the pawn.
     */
    protected boolean firstMove;
    /**
     * The colour of the current piece.
     */
    protected String colour;
    /**
     * True if a Tile is currently selected already for processing a move.
     */
    protected boolean processing;
    /**
     * The current board being used.
     */
    private Board board;

    /**
     * 
     * Constructs an object of type Move.
     * @param board
     *          the board associated with this move object.
     */
    public Move() {
        this.board = board;
        dataBoard = new Tile[EIGHT][EIGHT];
        savableDataBoard = new SavableTile[EIGHT][EIGHT];
        possibleMoves = new ArrayList<>();
        processing = false;
        current = null;
        currentX = -1;
        currentY = -1;
        firstMove = true;
        colour = null;
    }

    /**
     * Adds a Tile object to the dataBoard array.
     * @param x
     *          the x coordinate
     * @param y
     *          the y coordinate
     * @param tile
     *          the new tile to be added
     */
    protected void addtoBoard(int x, int y, Tile tile) {
        dataBoard[x][y] = tile;
    }
    
    /**
     * Loads the new savable board and replaces the old one.
     * @param newSavableBoard
     *          the new savable board.
     */
    public void setSavableBoard(SavableTile[][] newSavableBoard) {
        for (int x = 0; x < EIGHT; x++) {
            for (int y = 0; y < EIGHT; y++) {
                SavableTile temp = newSavableBoard[x][y];
                SavableTile newSavable = new SavableTile(
                        temp.getX(),
                        temp.getY(),
                        temp.getOccupied(),
                        temp.getType(), 
                        temp.getColour(),
                        temp.getFirstMove()
                        );
                savableDataBoard[x][y] = newSavable;
            }
        }

        copytoBoard();
        board.loadNewBoard();
    }
    
    /**
     * Copies everything in the dataBoard to the savableDataboard.
     */
    protected void copytoBoard() {
        for (int x = 0; x < EIGHT; x++) {
            for (int y = 0; y < EIGHT; y++) {
                SavableTile temp = savableDataBoard[x][y];
                Tile newTile = new Tile(
                        temp.getX(),
                        temp.getY(),
                        temp.getOccupied(),
                        temp.getType(),
                        temp.getColour()
                        );
                newTile.piece.setFirstMove(temp.getFirstMove());
                dataBoard[x][y] = newTile;
            }
        }
    }

    /**
     * Copies everything int the dataBoard to the savableDataboard.
     */
    public void copytoSavableBoard() {
        for (int x = 0; x < EIGHT; x++) {
            for (int y = 0; y < EIGHT; y++) {
                Tile temp = dataBoard[x][y];
                SavableTile tempSavable = new SavableTile(
                        temp.getXCoordinate(),
                        temp.getYCoordinate(),
                        temp.getOccupied(),
                        temp.getType(), 
                        temp.getColour(),
                        temp.piece.getFirstMove()
                        );
                savableDataBoard[x][y] = tempSavable;
            }
        }
    }
    
    /**
     * Sets the processing boolean.
     * @param processing
     *              true if there is currently a tile selected
     */
    public void setProcessing(Boolean processing) {
        this.processing = processing;
    }

    /**
     * Gets the processing boolean.
     * @return processing
     */
    public boolean getProcessing() {
        return processing;
    }

    /**
     * Takes in the current Tile selected and figures out which method to use to
     * calculate its possible moves.
     * @param newCurrent
     *              the current tile selected
     */
    protected void calculate(Tile newCurrent) {
        this.current = newCurrent;
        processing = true;
        firstMove = newCurrent.piece.getFirstMove();
        currentX = newCurrent.getXCoordinate();
        currentY = newCurrent.getYCoordinate();
        colour = newCurrent.getColour();
        String type = newCurrent.getType();

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
        int max = 2;
        if (colour.equals("White")) {
            if (firstMove) {
                max = THREE; 
            } 
            
            for (int counter = 1; counter < max; counter++) {
                if (currentX - counter > -1) {
                    if (!dataBoard[currentX - counter]
                            [currentY].getOccupied()) {
                        possibleMoves.add(dataBoard[currentX - counter]
                                [currentY]);
                    } else {
                        break;
                    }
                }
            }
            
            if (currentX - 1 > -1 & currentY + 1 < EIGHT) {
                if (dataBoard[currentX - 1][currentY + 1].getOccupied()) {
                    if (oppositeTeam(dataBoard[currentX - 1][currentY + 1])) {
                        possibleMoves.add(dataBoard[currentX - 1]
                                [currentY + 1]);
                    }
                }
            }
            
            if (currentX - 1 > -1 & currentY - 1 > -1) {
                if (dataBoard[currentX - 1][currentY - 1].getOccupied()) {
                    if (oppositeTeam(dataBoard[currentX - 1][currentY - 1])) {
                        possibleMoves.add(dataBoard[currentX - 1]
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
                    if (!dataBoard[currentX + counter]
                            [currentY].getOccupied()) {
                        possibleMoves.add(dataBoard[currentX + counter]
                                [currentY]);
                    } else {
                        break;
                    }
                }
            }
            
            if (currentX + 1 < EIGHT & currentY + 1 < EIGHT) {
                if (dataBoard[currentX + 1][currentY + 1].getOccupied()) {
                    if (oppositeTeam(dataBoard[currentX + 1][currentY + 1])) {
                        possibleMoves.add(dataBoard[currentX + 1]
                                [currentY + 1]);
                    }
                }
            }
            
            if (currentX + 1 < EIGHT & currentY - 1 > -1) {
                if (dataBoard[currentX + 1][currentY - 1].getOccupied()) {
                    if (oppositeTeam(dataBoard[currentX + 1][currentY - 1])) {
                        possibleMoves.add(dataBoard[currentX + 1]
                                [currentY - 1]);
                    }
                }
            }
        }
        
        for (int counter = 0; counter < possibleMoves.size(); counter++) {
            possibleMoves.get(counter).setStroke(Color.DODGERBLUE);
        }
    }

    /**
     * Movement calculations for the BISHOP.
     */
    protected void bishop() {
        int tempX = currentX;
        int tempY = currentY;

        tempX += 1;
        tempY += 1;
        while (tempX < EIGHT & tempY < EIGHT) {
            if (!dataBoard[tempX][tempY].getOccupied()) {
                possibleMoves.add(dataBoard[tempX][tempY]);
                tempX++;
                tempY++;
            } else {
                if (oppositeTeam(dataBoard[tempX][tempY])) {
                    possibleMoves.add(dataBoard[tempX][tempY]);
                }
                break;
            }
        }

        tempX = currentX + 1;
        tempY = currentY - 1;

        while (tempX < EIGHT & tempY > -1) {
            if (!dataBoard[tempX][tempY].getOccupied()) {
                possibleMoves.add(dataBoard[tempX][tempY]);
                tempX++;
                tempY--;
            } else {
                if (oppositeTeam(dataBoard[tempX][tempY])) {
                    possibleMoves.add(dataBoard[tempX][tempY]);
                }
                break;
            }
        }

        tempX = currentX - 1;
        tempY = currentY + 1;

        while (tempX > -1 & tempY < EIGHT) {
            if (!dataBoard[tempX][tempY].getOccupied()) {
                possibleMoves.add(dataBoard[tempX][tempY]);
                tempX--;
                tempY++;
            } else {
                if (oppositeTeam(dataBoard[tempX][tempY])) {
                    possibleMoves.add(dataBoard[tempX][tempY]);
                }
                break;
            }
        }

        tempX = currentX - 1;
        tempY = currentY - 1;

        while (tempX > -1 & tempY > -1) {
            if (!dataBoard[tempX][tempY].getOccupied()) {
                possibleMoves.add(dataBoard[tempX][tempY]);
                tempX--;
                tempY--;
            } else {
                if (oppositeTeam(dataBoard[tempX][tempY])) {
                    possibleMoves.add(dataBoard[tempX][tempY]);
                }
                break;
            }
        }

        for (int counter = 0; counter < possibleMoves.size(); counter++) {
            possibleMoves.get(counter).setStroke(Color.DODGERBLUE);
        }
    }

    /**
     * Movement calculations for the ROOK.
     */
    protected void rook() {
        int tempX = currentX + 1;
        int tempY = currentY;

        while (tempX < EIGHT) {
            if (!dataBoard[tempX][tempY].getOccupied()) {
                possibleMoves.add(dataBoard[tempX][tempY]);
                tempX++;
            } else {
                if (oppositeTeam(dataBoard[tempX][tempY])) {
                    possibleMoves.add(dataBoard[tempX][tempY]);
                }
                break;
            }
        }

        tempX = currentX - 1;

        while (tempX > -1) {
            if (!dataBoard[tempX][tempY].getOccupied()) {
                possibleMoves.add(dataBoard[tempX][tempY]);
                tempX--;
            } else {
                if (oppositeTeam(dataBoard[tempX][tempY])) {
                    possibleMoves.add(dataBoard[tempX][tempY]);
                }
                break;
            }
        }

        tempX = currentX;
        tempY += 1;

        while (tempY < EIGHT) {
            if (!dataBoard[tempX][tempY].getOccupied()) {
                possibleMoves.add(dataBoard[tempX][tempY]);
                tempY++;
            } else {
                if (oppositeTeam(dataBoard[tempX][tempY])) {
                    possibleMoves.add(dataBoard[tempX][tempY]);
                }
                break;
            }
        }

        tempY = currentY - 1;

        while (tempY > -1) {
            if (!dataBoard[tempX][tempY].getOccupied()) {
                possibleMoves.add(dataBoard[tempX][tempY]);
                tempY--;
            } else {
                if (oppositeTeam(dataBoard[tempX][tempY])) {
                    possibleMoves.add(dataBoard[tempX][tempY]);
                }
                break;
            }
        }

        for (int counter = 0; counter < possibleMoves.size(); counter++) {
            this.possibleMoves.get(counter).setStroke(Color.DODGERBLUE);
        }
    }

    /**
     * Movement calculations for the KNIGHT.
     */
    protected void knight() {
        if (currentX + 2 < EIGHT & currentY + 1 < EIGHT) {
            if (!dataBoard[currentX + 2][currentY + 1].getOccupied()) {
                possibleMoves.add(dataBoard[currentX + 2][currentY + 1]); 
            } else {
                if (oppositeTeam(dataBoard[currentX + 2][currentY + 1])) {
                    possibleMoves.add(dataBoard[currentX + 2][currentY + 1]);
                }
            }
        }

        if (currentX + 2 < EIGHT & currentY - 1 > -1) {
            if (!dataBoard[currentX + 2][currentY - 1].getOccupied()) {
                possibleMoves.add(dataBoard[currentX + 2][currentY - 1]); 
            } else {
                if (oppositeTeam(dataBoard[currentX + 2][currentY - 1])) {
                    possibleMoves.add(dataBoard[currentX + 2][currentY - 1]);
                }
            }
        }

        if (currentX - 2 > -1 & currentY + 1 < EIGHT) {
            if (!dataBoard[currentX - 2][currentY + 1].getOccupied()) {
                possibleMoves.add(dataBoard[currentX - 2][currentY + 1]); 
            } else {
                if (oppositeTeam(dataBoard[currentX - 2][currentY + 1])) {
                    possibleMoves.add(dataBoard[currentX - 2][currentY + 1]);
                }
            }
        }

        if (currentX - 2 > -1 & currentY - 1 > -1) {
            if (!dataBoard[currentX - 2][currentY - 1].getOccupied()) {
                possibleMoves.add(dataBoard[currentX - 2][currentY - 1]); 
            } else {
                if (oppositeTeam(dataBoard[currentX - 2][currentY - 1])) {
                    possibleMoves.add(dataBoard[currentX - 2][currentY - 1]);
                }
            }
        }

        if (currentX + 1 < EIGHT & currentY + 2 < EIGHT) {
            if (!dataBoard[currentX + 1][currentY + 2].getOccupied()) {
                possibleMoves.add(dataBoard[currentX + 1][currentY + 2]); 
            } else {
                if (oppositeTeam(dataBoard[currentX + 1][currentY + 2])) {
                    possibleMoves.add(dataBoard[currentX + 1][currentY + 2]);
                }
            }
        }

        if (currentX - 1 > -1 & currentY + 2 < EIGHT) {
            if (!dataBoard[currentX - 1][currentY + 2].getOccupied()) {
                possibleMoves.add(dataBoard[currentX - 1][currentY + 2]); 
            } else {
                if (oppositeTeam(dataBoard[currentX - 1][currentY + 2])) {
                    possibleMoves.add(dataBoard[currentX - 1][currentY + 2]);
                }
            }
        }

        if (currentX + 1 < EIGHT & currentY - 2 > -1) {
            if (!dataBoard[currentX + 1][currentY - 2].getOccupied()) {
                possibleMoves.add(dataBoard[currentX + 1][currentY - 2]); 
            } else {
                if (oppositeTeam(dataBoard[currentX + 1][currentY - 2])) {
                    possibleMoves.add(dataBoard[currentX + 1][currentY - 2]);
                }
            }
        }

        if (currentX - 1 > -1 & currentY - 2 > -1) {
            if (!dataBoard[currentX - 1][currentY - 2].getOccupied()) {
                possibleMoves.add(dataBoard[currentX - 1][currentY - 2]); 
            } else {
                if (oppositeTeam(dataBoard[currentX - 1][currentY - 2])) {
                    possibleMoves.add(dataBoard[currentX - 1][currentY - 2]);
                }
            }
        }

        for (int counter = 0; counter < possibleMoves.size(); counter++) {
            this.possibleMoves.get(counter).setStroke(Color.DODGERBLUE);
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
            if (!dataBoard[currentX + 1][currentY].getOccupied()) {
                possibleMoves.add(dataBoard[currentX + 1][currentY]); 
            } else {
                if (oppositeTeam(dataBoard[currentX + 1][currentY])) {
                    possibleMoves.add(dataBoard[currentX + 1][currentY]);
                }
            }
        }

        if (currentX - 1 > -1) {
            if (!dataBoard[currentX - 1][currentY].getOccupied()) {
                possibleMoves.add(dataBoard[currentX - 1][currentY]); 
            } else {
                if (oppositeTeam(dataBoard[currentX - 1][currentY])) {
                    possibleMoves.add(dataBoard[currentX - 1][currentY]);
                }
            }
        }

        if (currentY + 1 < EIGHT) {
            if (!dataBoard[currentX][currentY + 1].getOccupied()) {
                possibleMoves.add(dataBoard[currentX][currentY + 1]); 
            } else {
                if (oppositeTeam(dataBoard[currentX][currentY + 1])) {
                    possibleMoves.add(dataBoard[currentX][currentY + 1]);
                }
            }
        }

        if (currentY - 1 > -1) {
            if (!dataBoard[currentX][currentY - 1].getOccupied()) {
                possibleMoves.add(dataBoard[currentX][currentY - 1]); 
            } else {
                if (oppositeTeam(dataBoard[currentX][currentY - 1])) {
                    possibleMoves.add(dataBoard[currentX][currentY - 1]);
                }
            }
        }

        if (currentX + 1 < EIGHT & currentY + 1 < EIGHT) {
            if (!dataBoard[currentX + 1][currentY + 1].getOccupied()) {
                possibleMoves.add(dataBoard[currentX + 1][currentY + 1]); 
            } else {
                if (oppositeTeam(dataBoard[currentX + 1][currentY + 1])) {
                    possibleMoves.add(dataBoard[currentX + 1][currentY + 1]);
                }
            }
        }

        if (currentX + 1 < EIGHT & currentY - 1 > -1) {
            if (!dataBoard[currentX + 1][currentY - 1].getOccupied()) {
                possibleMoves.add(dataBoard[currentX + 1][currentY - 1]); 
            } else {
                if (oppositeTeam(dataBoard[currentX + 1][currentY - 1])) {
                    possibleMoves.add(dataBoard[currentX + 1][currentY - 1]);
                }
            }
        }

        if (currentX - 1 > -1 & currentY - 1 > -1) {
            if (!dataBoard[currentX - 1][currentY - 1].getOccupied()) {
                possibleMoves.add(dataBoard[currentX - 1][currentY - 1]); 
            } else {
                if (oppositeTeam(dataBoard[currentX - 1][currentY - 1])) {
                    possibleMoves.add(dataBoard[currentX - 1][currentY - 1]);
                }
            }
        }

        if (currentX - 1 > -1 & currentY + 1 < EIGHT) {
            if (!dataBoard[currentX - 1][currentY + 1].getOccupied()) {
                possibleMoves.add(dataBoard[currentX - 1][currentY + 1]); 
            } else {
                if (oppositeTeam(dataBoard[currentX - 1][currentY + 1])) {
                    possibleMoves.add(dataBoard[currentX - 1][currentY + 1]);
                }
            }
        }

        for (int counter = 0; counter < possibleMoves.size(); counter++) {
            this.possibleMoves.get(counter).setStroke(Color.DODGERBLUE);
        }
    }

    /**
     * Executed once a second click has been made if the processing boolean
     * is set to true.
     * Moves the current TilePiece to the target TilePiece.
     * @param target
     *              the target tile
     */
    public void move(Tile target) {
        for (int counter = 0; counter < possibleMoves.size(); counter++) {
            if (possibleMoves.get(counter) == target) {
                target.setPiece(current.getType(), current.getColour());
                target.piece.setFirstMove(false);
                target.setOccupied(true);
                current.setOccupied(false);
                current.removePiece();
                Board.switchTurns();
                copytoSavableBoard();
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
    public boolean isPossibleMove(Tile target) {
        for (int counter = 0; counter < possibleMoves.size(); counter++) {
            if (possibleMoves.get(counter) == target) {
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
    protected boolean oppositeTeam(Tile test) {
        if (!test.getColour().equals(colour)) {
            return true;
        }
        return false;
    }

    /**
     * Executed after a successful movement is made. Resets all members to their
     * default values, removes the stroke from all Tiles in the possibleMoves 
     * ArrayList
     * and.
     */
    protected void reset() {
        for (int x = 0; x < EIGHT; x++) {
            for (int y = 0; y < EIGHT; y++) {
                Tile temp = dataBoard[x][y];
                temp.setStroke(temp.getFill());
            }
        }

        colour = null;
        current = null;
        currentX = -1;
        currentY = -1;
        possibleMoves.clear();
        processing = false;
    }
}
