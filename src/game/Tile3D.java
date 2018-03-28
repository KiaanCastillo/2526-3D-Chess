package game;

import game.Tile.TilePiece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Tile3D.
 *
 * @author Kiaan Castillo A01024604
 * @version 2018
 */
public class Tile3D extends Tile {
    
    protected TilePiece piece;
    
    protected int currentLayer;
    
    public Tile3D(int x, int y, boolean occupied, String type, String colour, int currentLayer) {
        super(x, y, occupied, type, colour);
        this.currentLayer = currentLayer;

        this.piece = new TilePiece();
        if (type == null) {
            piece.setImage(null);   
        } else {
            piece.setPiece(type, colour);
        }
    }
    
    protected int getCurrentLayer() {
        return currentLayer;
    }
    
    protected void setCurrentLayer(int currentLayer) {
        this.currentLayer = currentLayer;
    }
    
    /**
     * Removes the piece from this tile.
     */
    public void removePiece() {
        type = null;
        colour = null;
        occupied = false;
        
        piece.remove();
    }
    
    /**
     * Sets a piece to be on this tile.
     * @param type
     *          the type of the piece
     * @param colour
     *          the colour of the piece
     */
    public void setPiece(String type, String colour) {
        this.type = type;
        this.colour = colour;
        this.occupied = true;
        
        piece.setPiece(type, colour);
    }
    
    /**
     * 
     * TilePiece.
     *
     * @author Kiaan Castillo A01024604
     * @version 2018
     */
    protected final class TilePiece extends ImageView {

        /**
         * The image for this piece to display.
         */
        protected Image pieceImage;
        /**
         * True if it is this piece's first move.
         */
        protected boolean firstMove;
        
        /**
         * Constructs an object of type TilePiece.
         */
        public TilePiece() {
            this.firstMove = true;
            setFitHeight(50.0);
            setFitWidth(50.0);
        }
        
        /**
         * Returns true if it is this piece's first move.
         * @return firstMove
         */
        public boolean getFirstMove() {
            return firstMove;
        }
        
        /**
         * Sets this piece's firstMove.
         * @param firstMove
         *              true if it is this piece's first move
         */
        public void setFirstMove(Boolean firstMove) {
            this.firstMove = firstMove;
        }

        /**
         * Sets the type/image of this piece.
         * @param type
         *          the type of the piece
         * @param colour
         *          the colour of the piece
         */
        protected void setPiece(String type, String colour) {
            String imageName = "/images/" + type + colour + ".png";
            pieceImage = new Image(imageName);
            this.setImage(pieceImage);
            this.setMouseTransparent(true);
        }
        
        /**
         * Removes the image from this piece.
         */
        protected void remove() {
            this.setImage(null);
        }
    }
}
