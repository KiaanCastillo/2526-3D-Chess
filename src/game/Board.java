package game;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

/**
 * Board is the chess board that controls turns, creates tiles and their 
 * respective pieces, if any.
 *
 * @author Kiaan Castillo A01024604
 * @version 2018
 */
public class Board extends GridPane {

    /**
     * The width of a tile's stroke.
     */
    public static final double WIDTH = 2.5;
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
    /**
     * The current turn in terms of colour.
     */
    protected static String turn;
    /**
     * A tile on the board.
     */
    private Tile tile;
    /**
     * A serializabe tile.
     */
    private SavableTile savableTile;
    /**
     * The Move database to be used in this board.
     */
    protected Move move;
    /**
     * The turn.
     */
    protected static Label displayTurn;

    /**
     * Constructs an object of type Board.
     */
    /*public Board() {
        turn = "White";
        displayTurn = new Label("Turn: " + turn);

        // x = row, y = column
        for (int x = 0; x < EIGHT; x++) {
            for (int y = 0; y < EIGHT; y++) {
                tile = new Tile(x, y, false, null, null);
                tile.setOnMouseClicked(this::selected);
                tile.setOnMouseEntered(this::hover);
                tile.setOnMouseExited(this::exit);

                if ((x % 2) == 0) {
                    if ((y % 2) == 0) {
                        tile.setFill(Color.SEASHELL);
                        tile.setStroke(Color.SEASHELL);
                        tile.setStrokeWidth(WIDTH);
                        tile.setStrokeType(StrokeType.INSIDE);
                    } else {
                        tile.setFill(Color.DARKSEAGREEN);
                        tile.setStroke(Color.DARKSEAGREEN);
                        tile.setStrokeWidth(WIDTH);
                        tile.setStrokeType(StrokeType.INSIDE);
                    }
                } else {
                    if ((y % 2) == 1) {
                        tile.setFill(Color.SEASHELL);
                        tile.setStroke(Color.SEASHELL);
                        tile.setStrokeWidth(WIDTH);
                        tile.setStrokeType(StrokeType.INSIDE);
                    } else {
                        tile.setFill(Color.DARKSEAGREEN);
                        tile.setStroke(Color.DARKSEAGREEN);
                        tile.setStrokeWidth(WIDTH);
                        tile.setStrokeType(StrokeType.INSIDE);
                    }
                }
                add(tile, y, x);
                add(tile.piece, y, x);
                move.addtoBoard(x, y, tile);
            }
        }
        
        move.copytoSavableBoard();
        createPieces();
    }*/

    /**
     * Creates all the pieces on the board.
     */
    protected void createPieces() {
        String[] pieceNames = {"Rook", "Knight", "Bishop", "King", 
                "Queen", "Bishop", "Knight", "Rook"};
        String colour = "Black";      

        for (int x = 0; x < 1; x++) {
            for (int y = 0; y < EIGHT; y++) {
                Tile temp = move.dataBoard[x][y];
                String currPiece = pieceNames[y];
                temp.setPiece(currPiece, colour);
                temp.setOccupied(true);
            }
        }

        for (int y = 0; y < EIGHT; y++) {
            Tile temp = move.dataBoard[1][y];
            String currPiece = "Pawn";
            temp.setPiece(currPiece, colour);
            temp.setOccupied(true);
        }

        colour = "White";

        for (int x = SEVEN; x < EIGHT; x++) {
            for (int y = 0; y < EIGHT; y++) {
                Tile temp = move.dataBoard[x][y];
                String currPiece = pieceNames[y];
                temp.setPiece(currPiece, colour);
                temp.setOccupied(true);
            }
        }

        for (int y = 0; y < EIGHT; y++) {
            Tile temp = move.dataBoard[SIX][y];
            String currPiece = "Pawn";
            temp.setPiece(currPiece, colour);
            temp.setOccupied(true);
        }
        
        move.copytoSavableBoard();
    }

    /**
     * Processes what to do next when a tile is selected.
     * @param e
     *          the tile selected
     */
    protected void selected(MouseEvent e) {
        Tile current = (Tile) e.getSource();
        
        if (!move.getProcessing()) {
            if (!current.getOccupied() 
                    || !current.getColour().equals(turn)) {
                move.reset();
                current.setStroke(Color.RED);
            } else {
                move.reset();
                current.setStroke(Color.SPRINGGREEN);
                move.calculate(current);
            }
        } else {
            if (move.isPossibleMove(current)) {
                move.move(current);
            } else {
                move.reset();
                if (!current.getOccupied() 
                        || !current.getColour().equals(turn)) {
                    move.reset();
                    current.setStroke(Color.RED);
                } else {
                    current.setStroke(Color.SPRINGGREEN);
                    move.calculate(current);
                }
            }
        }
    }

    /**
     * Processes what to do when a tile is hovered on.
     * @param e
     *          the tile hovered on
     */
    protected void hover(MouseEvent e) {
        Tile current = (Tile) e.getSource();
        if (!move.getProcessing()) {
            move.reset();
            current.setStroke(Color.AQUAMARINE);
        }
    }

    /**
     * Processes what to do when a tile is exited.
     * @param e
     *          the tile exited
     */
    protected void exit(MouseEvent e) {
        Tile current = (Tile) e.getSource();
        if (!move.getProcessing()) {
            current.setStroke(current.getFill());
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
        displayTurn.setText("Turn: " + turn);
    }
    
    /**
     * Sets the turn of the board.
     * @param newTurn
     *          the new turn.
     */
    protected static void setTurn(String newTurn) {
        turn = newTurn;
        
        displayTurn.setText("Turn: " + turn);
    }
    
    /**
     * Loads the new board to be used.
     */
    protected void loadNewBoard() {
        getChildren().clear();
        move.reset();
        for (int x = 0; x < EIGHT; x++) {
            for (int y = 0; y < EIGHT; y++) {
                Tile tile =  move.dataBoard[x][y];
                tile.setOnMouseClicked(this::selected);
                tile.setOnMouseEntered(this::hover);
                tile.setOnMouseExited(this::exit);
                if (tile.getType() != null) {
                    tile.setOccupied(true);
                }
                
                if ((x % 2) == 0) {
                    if ((y % 2) == 0) {
                        tile.setFill(Color.SEASHELL);
                        tile.setStroke(Color.SEASHELL);
                        tile.setStrokeWidth(WIDTH);
                        tile.setStrokeType(StrokeType.INSIDE);
                    } else {
                        tile.setFill(Color.DARKSEAGREEN);
                        tile.setStroke(Color.DARKSEAGREEN);
                        tile.setStrokeWidth(WIDTH);
                        tile.setStrokeType(StrokeType.INSIDE);
                    }
                } else {
                    if ((y % 2) == 1) {
                        tile.setFill(Color.SEASHELL);
                        tile.setStroke(Color.SEASHELL);
                        tile.setStrokeWidth(WIDTH);
                        tile.setStrokeType(StrokeType.INSIDE);
                    } else {
                        tile.setFill(Color.DARKSEAGREEN);
                        tile.setStroke(Color.DARKSEAGREEN);
                        tile.setStrokeWidth(WIDTH);
                        tile.setStrokeType(StrokeType.INSIDE);
                    }
                }
                
                add(tile, y, x);
                add(tile.piece, y, x);
                move.addtoBoard(x, y, tile);
            }
        }
    }
}
