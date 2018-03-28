package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main runs the Chess Javafx application.
 *
 * @author Kiaan Castillo A01024604
 * @version 2018
 */
public class Main extends Application {
    
    /**
     * The height of the scene.
     */
    public static final int SCENE_HEIGHT = 404;
    /**
     * The length of the scene.
     */
    public static final int SCENE_LENGTH = 1212;
    
    /**
     * Runs the Java FX application.
     * @see javafx.application.Application#start(javafx.stage.Stage)
     * @param primaryStage
     *              the stage to show the application
     * @throws Exception
     *              throws an exception
     */
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Set A Chess");
        Scene scene  = new Scene(new Chess3D(), SCENE_LENGTH,
                SCENE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Launches the JavaFX application.
     * @param args
     *          arguments to launch the program
     *              
     */
    public static void main(String[] args) {
        launch(args);
    }
}
