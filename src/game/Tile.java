package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * Tile is a single tile on the board and also carries pieces if it is
 * occupied.
 *
 * @author Kiaan Castillo A01024604
 * @version 2018
 */
public class Tile extends Rectangle {

    /**
     * The stroke width of the outline of a tile.
     */
    public static final double STROKE_WIDTH = 2.5;
    /**
     * The length and width of each tile.
     */
    public static final double DIMENSION = 50.0;
    /**
     * Is true if a piece is occupying this tile.
     */
    protected boolean occupied;
    /**
     * The X (row) coordinate of the tile.
     */
    protected int x;
    /**
     * The Y (column) coordinate of the tile.
     */
    protected int y;
    /**
     * The type of the piece.
     */
    protected String type;
    /**
     * The colour of the piece.
     */
    protected String colour;
    /**
     * The piece on the tile.
     */
    protected TilePiece piece;

    /**
     * Constructs an object of type Tile.
     * @param x
     *          the x coordinate
     * @param y
     *          the y coordinate
     * @param occupied
     *          the occupancy of this tile
     * @param type
     *          the type occupying this tile
     * @param colour
     *          the colour of the piece occupying this tile
     */
    public Tile(int x, int y, boolean occupied, String type, String colour) {
        super(DIMENSION, DIMENSION);
        this.x = x;
        this.y = y;
        this.type = type;
        this.colour = colour;

        this.piece = new TilePiece();
        if (type == null) {
            piece.setImage(null);   
        } else {
            piece.setPiece(type, colour);
        }
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
     * Returns true if this tile is occupied.
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    
    /**
     * Returns true if this tile is occupied.
     * @return occupied
     */
    public boolean getOccupied() {
        return occupied;
    }

    /**
     * Returns the type of this tile if it has a piece.
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the colour of this tile if it has a piece.
     * @return colour
     */
    public String getColour() {
        return colour;
    }

    /**
     * Returns this tile's x coordinate.
     * @return x
     */
    public int getXCoordinate() {
        return x;
    }

    /**
     * Returns this tile's y coordinate.
     * @return y
     */
    public int getYCoordinate() {
        return y;
    }

    /**
     * tilePiece.
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
