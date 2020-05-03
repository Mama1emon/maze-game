package maze;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import maze.lib.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class Main extends Application {
    //application parameters
    public static Scene mainScene;                 //главная сцена
    private static GraphicsContext graphicsContext; //позволяет накладывать графику
    private static final int WIDTH = 1200;          //Ширина окна
    private static final int HEIGHT = 800;          //Длина окна
    private static Group appRoot;                   //корень компонентов
    private static Pane gameRoot;                   //компонент, содержащий игровые объекты
    public static boolean isFinishScene = false;
    //game objects
    public static Cell[][] map;                     //игровая карта
    private static Player player;                   //игрок
    public static Prize coin;                       //игровой приз
    //game parameters
    public static final int MAP_SIZE = 10;          //размер игровой карты
    public static final int CELL_SIZE = 80;         //размер игровой клетки
    private static final int ENTRANCE_X = 95;       //координаты начала игры по X(игрока)
    private static final int ENTRANCE_Y = 90;       //координаты начала игры по Y(игрока)
    public static final int PLAYER_SIZE = 62;       //размер игрока
    private static final int PRIZE_SIZE = 50;       //размер приза
    private static int PRIZE_START_X;               //стартовая позиция приза по X
    private static int PRIZE_START_Y;               //стартовая позиция приза по Y
    public static int score = 0;                    //игровой счет
    //status panel parameters
    private static final int WIDTH_PANEL = 400;     //ширина дополнительной панели
    private static final int HEIGHT_PANEL = 800;    //высота дополнительной панели
    private static Label labelScore;                //отображение счета на панели
    private static Image arrowUp;                   //изображения на дополнительной панели
    private static Image arrowDown;
    private static Image arrowLeft;
    private static Image arrowRight;
    private static Image arrowActiveUp;
    private static Image arrowActiveDown;
    private static Image arrowActiveLeft;
    private static Image arrowActiveRight;
    private static Image iconLabelScore;

    private static HashSet<String> currentlyActiveKeys;//множество, содержащее активные кнопки
    static final long startNanoTime = System.nanoTime();

    //создание иерархии узлов приложения
    private static Parent createContent(){
        //initial
        appRoot = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);      //чистый холст
        graphicsContext = canvas.getGraphicsContext2D();//позволяем отображать графические объекты
        //создаем доплнительную панель
        Pane statusPanel = new Pane();
        statusPanel.setPrefSize(WIDTH_PANEL, HEIGHT_PANEL);
        statusPanel.setTranslateX(800);
        statusPanel.setTranslateY(0);
        statusPanel.setBackground(new Background(new BackgroundFill(Color.web("#c7d5ba"), CornerRadii.EMPTY, Insets.EMPTY)));
        loadImage();          //подгружаем изображения для дополнительной панели
        //текст на дополнительной панели
        labelScore = new Label("Score: " + score, new ImageView(iconLabelScore));
        labelScore.setTextFill(Color.web("#000000"));
        labelScore.setFont(Font.font("Arial",20));
        labelScore.setTranslateX(20);
        labelScore.setTranslateY(20);
        statusPanel.getChildren().addAll(labelScore);
        //создаем игровые объекты
        gameRoot = new Pane();
        gameRoot.setPrefSize(800, 800);          //устанавливаем размер
        map = new Cell[MAP_SIZE][MAP_SIZE];
        for(int i = 0; i < MAP_SIZE; i++){
            for(int j = 0; j < MAP_SIZE; j++){
                map[i][j] = new Cell(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE);
                map[i][j].setTranslateX(i * CELL_SIZE);
                map[i][j].setTranslateY(j * CELL_SIZE);

                gameRoot.getChildren().add(map[i][j]); //добавляем игровую клетку в узел
            }
        }
        player = new Player(ENTRANCE_X, ENTRANCE_Y, PLAYER_SIZE);

        getStartPrizeLocation();    //получаем координаты приза
        coin = new Prize(PRIZE_START_X, PRIZE_START_Y, PRIZE_SIZE);
        //добавляем в иерархию
        gameRoot.getChildren().add(coin);
        gameRoot.getChildren().add(player);            //добавляем игрока в узел
        appRoot.getChildren().add(statusPanel);
        appRoot.getChildren().add(gameRoot);           //добавляем в корень
        appRoot.getChildren().add(canvas);             //добавляем в корень
        return appRoot;
    }

    //возвращает координаты приза
    private static void getStartPrizeLocation(){
        int prizeNewX = (int) (Math.random() * 10);
        int prizeNewY = (int) (Math.random() * 10);
        while(Main.map[prizeNewX][prizeNewY].isWall()){
            prizeNewX = (int) (Math.random() * 10);
            prizeNewY = (int) (Math.random() * 10);
        }
        PRIZE_START_X = prizeNewX * CELL_SIZE + 15;
        PRIZE_START_Y = prizeNewY * CELL_SIZE + 15;
    }

    //загрузка изображений для дполнительной панели
    private static void loadImage(){
        //image for status panel
        File image = new File("src/maze/Resources/Pictures/StatusPanel/arrowLeft.png");
        arrowLeft = new Image(image.toURI().toString());
        image = new File("src/maze/Resources/Pictures/StatusPanel/arrowActiveLeft.png");
        arrowActiveLeft = new Image(image.toURI().toString());

        image = new File("src/maze/Resources/Pictures/StatusPanel/arrowRight.png");
        arrowRight = new Image(image.toURI().toString());
        image = new File("src/maze/Resources/Pictures/StatusPanel/arrowActiveRight.png");
        arrowActiveRight = new Image(image.toURI().toString());

        image = new File("src/maze/Resources/Pictures/StatusPanel/arrowUp.png");
        arrowUp = new Image(image.toURI().toString());
        image = new File("src/maze/Resources/Pictures/StatusPanel/arrowActiveUp.png");
        arrowActiveUp = new Image(image.toURI().toString());

        image = new File("src/maze/Resources/Pictures/StatusPanel/arrowDown.png");
        arrowDown = new Image(image.toURI().toString());
        image = new File("src/maze/Resources/Pictures/StatusPanel/arrowActiveDown.png");
        arrowActiveDown = new Image(image.toURI().toString(), 128, 128, false, false);

        iconLabelScore = new Image(new File("src/maze/Resources/Pictures/StatusPanel/labelCoin.png").toURI().toString());
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

    //визуализация карты
    private static void renderMap(){
        for(int i = 0; i < MAP_SIZE; i++){
            for(int j = 0; j < MAP_SIZE; j++) {
                graphicsContext.drawImage(map[i][j].getFrame(), map[i][j].getTranslateX(), map[i][j].getTranslateY());
            }
        }
    }

    //визуализация приза
    private static void renderPrize(double time){
        if(coin.isVisiblePrize()){
            graphicsContext.drawImage(coin.getFrame(time), coin.getTranslateX(), coin.getTranslateY());
        }
    }

    //визуализация дополнительной панели
    private static void renderStatusPanel(){
        labelScore.setText("Score: " + score);
        graphicsContext.drawImage(arrowLeft, WIDTH - WIDTH_PANEL + 50, HEIGHT_PANEL - 300);
        graphicsContext.drawImage(arrowRight, WIDTH - 50 - 128 , HEIGHT_PANEL - 300);
        graphicsContext.drawImage(arrowUp, WIDTH - WIDTH_PANEL + 136, HEIGHT_PANEL - 400);
        graphicsContext.drawImage(arrowDown, WIDTH - WIDTH_PANEL + 136, HEIGHT_PANEL - 200);
        if (currentlyActiveKeys.contains("UP")) {
            graphicsContext.drawImage(arrowActiveUp, WIDTH - WIDTH_PANEL + 136, HEIGHT_PANEL - 400);
        }
        if (currentlyActiveKeys.contains("DOWN")) {
            graphicsContext.drawImage(arrowActiveDown, WIDTH - WIDTH_PANEL + 136, HEIGHT_PANEL - 200);
        }
        if (currentlyActiveKeys.contains("LEFT")) {
            graphicsContext.drawImage(arrowActiveLeft, WIDTH - WIDTH_PANEL + 50, HEIGHT_PANEL - 300);
        }
        if (currentlyActiveKeys.contains("RIGHT")) {
            graphicsContext.drawImage(arrowActiveRight, WIDTH - 50 - 128 , HEIGHT_PANEL - 300);
        }
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
                tickAndRender(currentNanoTime); //отображение определенной кнопки
            }
        }.start();

        mainStage.show();                       //отобразить окно
    }

    private static void tickAndRender(long currentNanoTime) {
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        // clear canvas
        graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);
        //визуализация окна
        renderStatusPanel();
        renderMap();
        renderPrize(t);

        //Если игрок не движется
        if (!currentlyActiveKeys.contains("RIGHT") && !currentlyActiveKeys.contains("LEFT") && !currentlyActiveKeys.contains("UP")
                && !currentlyActiveKeys.contains("DOWN")) {
            graphicsContext.drawImage(player.getFrame(), player.getTranslateX() - 5, player.getTranslateY() - 15);
        }
        /*
         * Если зажата кнопка
         * передвижение и рендеринг
         */
        if (currentlyActiveKeys.contains("UP")) {
            player.Move(0, -2);
            graphicsContext.drawImage(player.getFramesForward(t), player.getTranslateX() - 5, player.getTranslateY() - 15);
        }
        else if (currentlyActiveKeys.contains("DOWN")) {
            player.Move(0, 2);
            graphicsContext.drawImage(player.getFramesBack(t), player.getTranslateX() - 5, player.getTranslateY() - 15);
        }
        else if (currentlyActiveKeys.contains("LEFT")) {
            player.Move(-2, 0);
            graphicsContext.drawImage(player.getFramesLeft(t), player.getTranslateX()-1, player.getTranslateY() - 15);
        }
        else if (currentlyActiveKeys.contains("RIGHT")) {
            player.Move(2, 0);
            graphicsContext.drawImage(player.getFramesRight(t), player.getTranslateX()-27, player.getTranslateY() - 15);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}