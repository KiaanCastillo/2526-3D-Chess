package game;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

/**
 * Board3D.
 *
 * @author Kiaan Castillo A01024604
 * @version 2018
 */
public class Board3D extends Board {
    
    protected Move3D move3D;
    
    protected int layer;
    
    protected Tile3D tile3D;
    
    public Board3D(Move3D move3D, int layer) {
        this.move3D = move3D;
        this.layer = layer;
        turn = "White";
        
     // x = row, y = column
        for (int x = 0; x < EIGHT; x++) {
            for (int y = 0; y < EIGHT; y++) {
                tile3D = new Tile3D(x, y, false, null, null, layer);
                tile3D.setOnMouseClicked(this::selected3D);
                tile3D.setOnMouseEntered(this::hover3D);
                tile3D.setOnMouseExited(this::exit3D);

                if ((x % 2) == 0) {
                    if ((y % 2) == 0) {
                        tile3D.setFill(Color.SEASHELL);
                        tile3D.setStroke(Color.SEASHELL);
                        tile3D.setStrokeWidth(WIDTH);
                        tile3D.setStrokeType(StrokeType.INSIDE);
                    } else {
                        tile3D.setFill(Color.DARKSEAGREEN);
                        tile3D.setStroke(Color.DARKSEAGREEN);
                        tile3D.setStrokeWidth(WIDTH);
                        tile3D.setStrokeType(StrokeType.INSIDE);
                    }
                } else {
                    if ((y % 2) == 1) {
                        tile3D.setFill(Color.SEASHELL);
                        tile3D.setStroke(Color.SEASHELL);
                        tile3D.setStrokeWidth(WIDTH);
                        tile3D.setStrokeType(StrokeType.INSIDE);
                    } else {
                        tile3D.setFill(Color.DARKSEAGREEN);
                        tile3D.setStroke(Color.DARKSEAGREEN);
                        tile3D.setStrokeWidth(WIDTH);
                        tile3D.setStrokeType(StrokeType.INSIDE);
                    }
                }
                add(tile3D, y, x);
                add(tile3D.piece, y, x);
                move3D.addtoBoard(x, y, tile3D, layer);
            }
        }
    }

    /**
     * Processes what to do next when a tile is selected.
     * @param e
     *          the tile selected
     */
    protected void selected3D(MouseEvent e) {
        Tile3D current = (Tile3D) e.getSource();
        
        if (!move3D.getProcessing()) {
            if (!current.getOccupied() 
                    || !current.getColour().equals(Move3D.turn)) {
                move3D.reset();
                current.setStroke(Color.RED);
            } else {
                move3D.reset();
                current.setStroke(Color.SPRINGGREEN);
                move3D.calculate(current);
            }
        } else {
            if (move3D.isPossibleMove(current)) {
                move3D.move(current);
            } else {
                move3D.reset();
                if (!current.getOccupied() 
                        || !current.getColour().equals(turn)) {
                    move3D.reset();
                    current.setStroke(Color.RED);
                } else {
                    current.setStroke(Color.SPRINGGREEN);
                    move3D.calculate(current);
                }
            }
        }
    }

    /**
     * Processes what to do when a tile is hovered on.
     * @param e
     *          the tile hovered on
     */
    protected void hover3D(MouseEvent e) {
        Tile3D current = (Tile3D) e.getSource();
        if (!move3D.getProcessing()) {
            move3D.reset();
            current.setStroke(Color.AQUAMARINE);
        }
    }

    /**
     * Processes what to do when a tile is exited.
     * @param e
     *          the tile exited
     */
    protected void exit3D(MouseEvent e) {
        Tile3D current = (Tile3D) e.getSource();
        if (!move3D.getProcessing()) {
            current.setStroke(current.getFill());
        }
    }
}
