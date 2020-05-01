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
    private static boolean canGo = true;
    //graphics parameters
    private static Scene mainScene;
    private static GraphicsContext graphicsContext;
    private static int WIDTH = 1200;        //Ширина окна
    private static int HEIGHT = 800;        //Длина окна
    private static HashSet<String> currentlyActiveKeys;
    static final long startNanoTime = System.nanoTime();
    //game objects
    static Map map;
    static Player player;

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
        player = new Player(80, 80);

        //обработчик нажатий клавиш с клавиатуры
        prepareActionHandlers();
        graphicsContext = canvas.getGraphicsContext2D();

        /*
         * Main "game" loop
         */
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                tickAndRender(currentNanoTime);    //отображение определенной кнопки
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

    private static void tickAndRender(long currentNanoTime)
    {
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        // clear canvas
        graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);
        for(int i = 0; i < map.getMapSize(); i++){
            for(int j = 0; j < map.getMapSize(); j++) {
                graphicsContext.drawImage(map.getFields()[i][j].getFrame(), map.getFields()[i][j].getX(), map.getFields()[i][j].getY());
                if((player.getX() == map.getFields()[i][j].getX()) && (player.getY() == map.getFields()[i][j].getY())){
                    if(map.getFields()[i][j].isWall()){
                        canGo = false;
                    }
                }
                else {
                    canGo = true;
                }
            }
        }
        if (!currentlyActiveKeys.contains("RIGHT") && !currentlyActiveKeys.contains("LEFT") && !currentlyActiveKeys.contains("UP")
                && !currentlyActiveKeys.contains("DOWN")) {
            graphicsContext.drawImage(player.getFrame(), player.getX() + 8, player.getY() - 10);
        }

        if (currentlyActiveKeys.contains("UP") && canGo) {
            player.Move(0, -1);
            graphicsContext.drawImage(player.getFramesForward(t), player.getX() + 8, player.getY() - 10);
        }
        else if (currentlyActiveKeys.contains("DOWN") && canGo) {
            player.Move(0, 1);
            System.out.println(player.getX() + " " + player.getY());
            graphicsContext.drawImage(player.getFramesBack(t), player.getX() + 8, player.getY() - 10);
        }
        else if (currentlyActiveKeys.contains("LEFT") && canGo) {
            player.Move(-1, 0);

            graphicsContext.drawImage(player.getFramesLeft(t), player.getX(), player.getY() - 10);
        }
        else if (currentlyActiveKeys.contains("RIGHT") && canGo) {
            player.Move(1, 0);
            graphicsContext.drawImage(player.getFramesRight(t), player.getX(), player.getY() - 10);
        }
        else {
        }
    }

}