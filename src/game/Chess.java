package game;

import javafx.scene.layout.HBox;

/**
 * Chess.
 *
 * @author Kiaan Castillo A01024604
 * @version 2018
 */
public class Chess extends HBox {
    
    /**
     * The chess board to be used.
     */
    private Board chessBoard;
    /**
     * The menu to be used.
     */
    private Menu menu;
    
    public Chess() {

    }

    /**
     * Sets the turn of the chess board.
     * @param turn
     *          the turn to be set
     */
    public void setBoardTurn(String turn) {
        chessBoard.setTurn(turn);
    }
    
    /**
     * Creates new chess board and menu and replaces the old ones
     * with a new one to create a new game.
     */
    protected void newGame() {
        chessBoard = new Board();
        menu = new Menu(this, chessBoard, chessBoard.move);
        getChildren().clear();
        getChildren().addAll(chessBoard, menu);
    }
}
