package maze;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import maze.lib.*;
import java.util.HashSet;

public class Main extends Application {
    //graphics parameters
    static Scene mainScene;
    static GraphicsContext graphicsContext;
    static int WIDTH = 1200;        //Ширина окна
    static int HEIGHT = 900;        //Длина окна
    static HashSet<String> currentlyActiveKeys;
    //game objects
    static Map map;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) {
        mainStage.setTitle("MAZE");
        Group root = new Group();
        mainScene = new Scene(root);
        mainStage.setScene(mainScene);

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        //инициализация игровых объектов
        map = new Map(10);       //игровая карта

        //обработчик нажатий клавиш с клавиатуры
        prepareActionHandlers();

        graphicsContext = canvas.getGraphicsContext2D();

        /*
         * Main "game" loop
         */
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                tickAndRender();    //отображение определенной кнопки
            }
        }.start();

        mainStage.show();           //отобразить окно
    }

    //обработчик нажатий с клавиатуры
    private static void prepareActionHandlers() {
        // use a set so duplicates are not possible
        currentlyActiveKeys = new HashSet<String>();
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                currentlyActiveKeys.add(event.getCode().toString());
            }
        });

        mainScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                currentlyActiveKeys.remove(event.getCode().toString());
            }
        });
    }

    private static void tickAndRender()
    {
        // clear canvas
        graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);
        for(int i = 0; i < map.getMapSize(); i++){
            for(int j = 0; j < map.getMapSize(); j++) {
                graphicsContext.drawImage(map.getFields()[i][j].getFrame(), map.getFields()[i][j].getX(), map.getFields()[i][j].getY());
            }
        }

        if (currentlyActiveKeys.contains("LEFT")) {
            //graphicsContext.drawImage(leftGreen, 64 ,64);
        }
        else
        {
            //graphicsContext.drawImage(left, 64 ,64);
        }

        if (currentlyActiveKeys.contains("RIGHT"))
        {
            //graphicsContext.drawImage(rightGreen, 320, 64);
        }
        else
        {
            //graphicsContext.drawImage(right, 320, 64);
        }
    }

}