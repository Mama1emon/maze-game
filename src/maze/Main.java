package maze;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import maze.lib.*;
import java.util.HashSet;

public class Main extends Application {
    //application parameters
    private static Scene mainScene;                 //главная сцена
    private static GraphicsContext graphicsContext; //позволяет накладывать графику
    private static final int WIDTH = 1200;          //Ширина окна
    private static final int HEIGHT = 800;          //Длина окна
    private static Group appRoot;                   //корень компонентов
    private static Pane gameRoot;                   //компонент, содержащий игровые объекты
    //game objects
    public static Cell[][] map;                    //игровая карта
    private static Player player;                   //игрок
    //game parameters
    public static final int MAP_SIZE = 10;          //размер игровой карты
    private static final int CELL_SIZE = 80;         //размер игровой клетки
    private static final int ENTRANCE_X = 95;
    private static final int ENTRANCE_Y = 90;
    public static final int PLAYER_SIZE = 62;
    //
    private static HashSet<String> currentlyActiveKeys;//множество, содержащее активные кнопки
    static final long startNanoTime = System.nanoTime();

    //создание иерархии узлов приложения
    private static Parent createContent(){
        //initial
        appRoot = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);      //чистый холст
        graphicsContext = canvas.getGraphicsContext2D();//позволяем отображать графические объекты
        gameRoot = new Pane();
        gameRoot.setPrefSize(800, 800);          //устанавливаем размер
        map = new Cell[MAP_SIZE][MAP_SIZE];
        for(int i = 0; i < MAP_SIZE; i++){
            for(int j = 0; j < MAP_SIZE; j++){
                map[i][j] = new Cell(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE);
                map[i][j].setTranslateX(i * CELL_SIZE);
                map[i][j].setTranslateY(j * CELL_SIZE);

                gameRoot.getChildren().add(map[i][j]); //добавляем игровую карту в узел
            }
        }
        player = new Player(ENTRANCE_X, ENTRANCE_Y, PLAYER_SIZE);
        gameRoot.getChildren().add(player);            //добавляем игрока в узел
        appRoot.getChildren().add(gameRoot);           //добавляем в корень
        appRoot.getChildren().add(canvas);             //добавляем в корень
        return appRoot;
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

    @Override
    public void start(Stage mainStage) {
        //initial
        mainStage.setTitle("MAZE");             //название окна
        mainScene = new Scene(createContent()); //создаем сцену
        mainStage.setScene(mainScene);          //присваиваем сцену окну

        //обработчик нажатий клавиш с клавиатуры
        prepareActionHandlers();

        //Main "game" loop
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                tickAndRender(currentNanoTime);    //отображение определенной кнопки
            }
        }.start();

        mainStage.show();           //отобразить окно
    }

    private static void tickAndRender(long currentNanoTime)
    {
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        // clear canvas
        graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);
        for(int i = 0; i < MAP_SIZE; i++){
            for(int j = 0; j < MAP_SIZE; j++) {
                graphicsContext.drawImage(map[i][j].getFrame(), map[i][j].getTranslateX(), map[i][j].getTranslateY());
            }
        }
        if (!currentlyActiveKeys.contains("RIGHT") && !currentlyActiveKeys.contains("LEFT") && !currentlyActiveKeys.contains("UP")
                && !currentlyActiveKeys.contains("DOWN")) {
            graphicsContext.drawImage(player.getFrame(), player.getTranslateX() - 5, player.getTranslateY() - 15);
        }
        System.out.println(player.getTranslateX() + " " + player.getTranslateY());
        if (currentlyActiveKeys.contains("UP")) {
            player.Move(0, -1);
            graphicsContext.drawImage(player.getFramesForward(t), player.getTranslateX() - 5, player.getTranslateY() - 15);
        }
        else if (currentlyActiveKeys.contains("DOWN")) {
            player.Move(0, 1);
            graphicsContext.drawImage(player.getFramesBack(t), player.getTranslateX() - 5, player.getTranslateY() - 15);
        }
        else if (currentlyActiveKeys.contains("LEFT")) {
            player.Move(-1, 0);
            graphicsContext.drawImage(player.getFramesLeft(t), player.getTranslateX()-1, player.getTranslateY() - 15);
        }
        else if (currentlyActiveKeys.contains("RIGHT")) {
            player.Move(1, 0);
            graphicsContext.drawImage(player.getFramesRight(t), player.getTranslateX()-27, player.getTranslateY() - 15);
        }
        else {
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}