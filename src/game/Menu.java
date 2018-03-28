package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The menu located on the right of the game board. It has buttons that
 * allow saving, loading, exiting, and creating a new game.
 *
 * @author Kiaan Castillo A01024604
 * @version 2018
 */
public class Menu extends VBox {

    /**
     * Spacing for elements.
     */
    public static final double SPACING = 2.7;
    /**
     * The save button.
     */
    private Button save;
    /**
     * The load button.
     */
    private Button load;
    /**
     * The exit button.
     */
    private Button exit;
    /**
     * Creates a new game.
     */
    private Button newGame;
    /**
     * The logo of the game.
     */
    private Image logo;
    /**
     * ImageView to display the logo.
     */
    private ImageView view;
    /**
     * The chess game that controls this menu.
     */
    private Chess chess;
    /**
     * The board associated with this menu.
     */
    private Board chessBoard;
    /**
     * The move class that is associated with this menu.
     */
    private Move move;
    
    /**
     * Constructs an object of type Menu.
     * @param chess
     *          the chess game that controls this menu
     * @param chessBoard
     *          the chess board that is associated with this menu
     * @param move
     *          the move class that is associated with this menu
     */
    public Menu(Chess chess, Board chessBoard, Move move) {
        this.chess = chess;
        this.chessBoard = chessBoard;
        this.move = move;
                
        save = new Button("Save");
        save.setOnAction(event -> {
            try {
                saveGame(event);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        load = new Button("Load");
        load.setOnAction(event -> {
            try {
                loadGame(event);
            } catch (FileNotFoundException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        newGame = new Button("New Game");
        newGame.setOnAction(this::newGame);
        
        exit = new Button("Exit");
        exit.setOnAction(this::exitGame);

        logo = new Image("/images/logo.png");
        view = new ImageView(logo);

        setAlignment(Pos.CENTER);
        getStylesheets().add("/game/style.css");
        setSpacing(SPACING);
        getChildren().addAll(view, Board.displayTurn, save, 
                load, newGame, exit);
    }

    /**
     * Saves the game by serializing the chess board.
     * @param event
     *          the saving event
     * @throws FileNotFoundException
     *          for files not found when saving a game/turn
     */
    private void saveGame(ActionEvent event) throws FileNotFoundException {
        try {
            FileOutputStream fileOutputStream 
            = new FileOutputStream("savedChessGame.txt");
            ObjectOutputStream objectOutputStream;
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(move.savableDataBoard);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("No such file found.");
            e.printStackTrace();
        }
        
        try {
            FileOutputStream fileOutputStream 
            = new FileOutputStream("savedChessGameTurn.txt");
            ObjectOutputStream objectOutputStream;
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(chessBoard.turn);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("No such file found.");
            e.printStackTrace();
        }

    }

    /**
     * Loads a game.
     * @param event
     *          the loading event
     * @throws FileNotFoundException
     *          for files not found when loading games/turns
     * @throws ClassNotFoundException 
     *          for classes not found when casting
     */
    private void loadGame(ActionEvent event) 
            throws FileNotFoundException, ClassNotFoundException {
        try {
            FileInputStream fileInputStream
            = new FileInputStream("savedChessGame.txt");
            ObjectInputStream objectInputStream;
            objectInputStream = new ObjectInputStream(fileInputStream);
            SavableTile[][] newSavableBoard = (SavableTile[][]) 
                    objectInputStream.readObject();
            move.setSavableBoard(newSavableBoard);
            objectInputStream.close();  
        } catch (IOException e) {
            System.out.println("No such file found.");
            e.printStackTrace();
        }
        
        try {
            FileInputStream fileInputStream
            = new FileInputStream("savedChessGameTurn.txt");
            ObjectInputStream objectInputStream;
            objectInputStream = new ObjectInputStream(fileInputStream);
            String newTurn = (String) objectInputStream.readObject();
            chess.setBoardTurn(newTurn);
            objectInputStream.close();  
        } catch (IOException e) {
            System.out.println("No such file found.");
            e.printStackTrace();
        }
    }

    /**
     * Creates a new game.
     * @param event
     *              the button event
     */
    private void newGame(ActionEvent event) {
        chess.newGame();
    }

    /**
     * exits the game.
     * @param event
     *          the button event
     */
    private void exitGame(ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

}
