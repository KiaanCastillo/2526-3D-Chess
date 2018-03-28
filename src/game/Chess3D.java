package game;

import javafx.scene.layout.HBox;

/**
 * Chess3D.
 *
 * @author Kiaan Castillo A01024604
 * @version 2018
 */
public class Chess3D extends Chess {
    
    public static final int THREE = 3;
    /**
     * Layer1.
     */
    private Board3D layer1;
    /**
     * Layer2.
     */
    private Board3D layer2;
    /**
     * Layer3.
     */
    private Board3D layer3;
    
    private Move3D move3D;
    /**
     * The menu to be used.
     */
    private Menu menu;
    
   /**
    * Constructs an object of type Chess.
    * @throws IOException
    * @throws ClassNotFoundException
    */
    public Chess3D() {
        move3D = new Move3D();
        layer1 = new Board3D(move3D, 1);
        layer1.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-color: brown;");
        
        layer2 = new Board3D(move3D, 2);
        layer2.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-color: brown;");
        
        layer3 = new Board3D(move3D, THREE);
        layer3.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-color: brown;");
        
        move3D.setBoards(layer1, layer2, layer3);
        move3D.createPieces();

        getChildren().addAll(layer1, layer2, layer3);
    }

}
