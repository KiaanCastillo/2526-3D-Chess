package game;

import java.io.Serializable;

/**
 * 
 * SavableTile is a serializable version of the Tile class.
 *
 * @author Kiaan Castillo A01024604
 * @version 2018
 */
public class SavableTile implements Serializable {
    /**
     * The serialVersionUID of the SavableTile class.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The x coordinate of this SavableTile.
     */
    private int x;
    /**
     * The y coordinate of this SavableTile.
     */
    private int y;
    /**
     * The occupancy of this tile.
     */
    private boolean occupied;
    /**
     * The type of the piece occupying this tile if any.
     */
    private String type;
    /**
     * The colour of the piece occupying this tile if any.
     */
    private String colour;
    /**
     * True if it is this piece's first move.
     */
    private boolean firstMove;
    
    /**
     * Constructs an object of type SavableTile.
     * @param x
     *          the x coordinate
     * @param y
     *          the y coordinate
     * @param occupied
     *          the occupancy
     * @param type
     *          the type occupying
     * @param colour
     *          the colour occupying
     */
    public SavableTile(int x, int y, boolean occupied, 
            String type, String colour, boolean firstMove) {
        this.x = x;
        this.y = y;
        this.occupied = occupied;
        this.type = type;
        this.colour = colour;
        this.firstMove = firstMove;
    }

    /**
     * Returns the x coordinate.
     * @return x
     */
    protected int getX() {
        return x;
    }

    /**
     * Sets the x coordinate.
     * @param x
     *          the x coordinate
     */
    protected void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y coordinate.
     * @return y    
     */
    protected int getY() {
        return y;
    }

    /**
     * Sets the y coordinate.
     * @param y
     *          the y coordinate
     */
    protected void setY(int y) {
        this.y = y;
    }
    
    /**
     * Gets the occupancy.
     * @return occupied
     */
    protected boolean getOccupied() {
        return occupied;
    }
    
    /**
     * Sets the occupancy.
     * @param occupied
     *          the occupancy
     */
    protected void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    /**
     * Gets the type occupying.
     * @return type
     */
    protected String getType() {
        return type;
    }

    /**
     * Sets the type occupying.
     * @param type
     *          the type occupying
     */
    protected void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the colour of the occupying piece.
     * @return colour
     */
    protected String getColour() {
        return colour;
    }

    /**
     * Sets the colour of the occupying piece.
     * @param colour
     *          the colour of the occupying piece
     */
    protected void setColour(String colour) {
        this.colour = colour;
    }
    
    /**
     * Returns true if it is this piece's first move.
     * @return firstMove
     */
    protected boolean getFirstMove() {
        return firstMove;
    }

    /**
     * Sets the firstMove boolean of the occupying piece.
     * @param firstMove
     *          true if it is this piece's first move.
     */
    protected void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
}
