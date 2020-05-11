/*
 * Контроллер, управляющий экраном меню
 */
package maze.Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import maze.Main;
import maze.lib.Cell;
import maze.lib.Player;
import maze.lib.Prize;

import static maze.Main.*;

public class menuSceneController {
    @FXML
    private Button firstButton;

    @FXML
    private Button secondButton;

    @FXML
    private Button thirdButton;

    //обработка при нажатии на кнопку
    @FXML
    public void firstButtonClicked(){
        initializeGameObject("first");//инициализация игровых объектов
        //переход с экрана меню на игровой экран
        isMenu = false;
        isGame = true;
    }

    //обработка при нажатии на кнопку
    @FXML
    public void secondButtonClicked(){
        initializeGameObject("second");//инициализация игровых объектов
        //переход с экрана меню на игровой экран
        isMenu = false;
        isGame = true;
    }

    //обработка при нажатии на кнопку
    @FXML
    public void thirdButtonClicked(){
        initializeGameObject("third");//инициализация игровых объектов
        //переход с экрана меню на игровой экран
        isMenu = false;
        isGame = true;
    }

    //инициализация игровых объектов
    private static void initializeGameObject(String lvlName){
        map = getMap(lvlName);  //получаем карту нужного уровня
        player = new Player(ENTRANCE_X, ENTRANCE_Y, PLAYER_SIZE);  //инициализация игрок
        setStartPrizeLocation();//устанавливаем стартовое положение приза
        coin = new Prize(PRIZE_START_X, PRIZE_START_Y, PRIZE_SIZE);//инициализация приза
        //добавляем в иерархию экрана
        gameRoot.getChildren().add(coin);
        gameRoot.getChildren().add(player);
    }

    //получить карту определенного уровня
    private static Cell[][] getMap(String nameFile){
        Cell[][] map = new Cell[MAP_SIZE][MAP_SIZE];
        //находим файл с нужным уровнем
        File fileMap = new File("src/maze/Resources/Map/"+ nameFile + "Lvl.txt");
        Scanner scanner = null;
        //инициализация reader
        try {
            scanner = new Scanner(fileMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int typeCell = 0;   //тип клетки(0 - ничего, 1 - стена)
        //инициализируем каждую клетку поля
        for(int i = 0; i < MAP_SIZE; i++){
            for(int j = 0; j < MAP_SIZE; j++){
                if(scanner != null && scanner.hasNext()){
                    typeCell = scanner.nextInt();         //читаем тип поля
                }
                map[i][j] = new Cell(i * CELL_SIZE, j*CELL_SIZE, CELL_SIZE, typeCell);//инициализация

                gameRoot.getChildren().add(map[i][j]);    //добавляем игровую клетку в узел
            }
        }
        return map;
    }

    //возвращает координаты приза
    private static void setStartPrizeLocation(){
        int prizeNewX;
        int prizeNewY;
        //пока не равно пустому полю, не являющемуся точкой старта игрока
        do {
            prizeNewX = (int) (Math.random() * 10);
            prizeNewY = (int) (Math.random() * 10);
        } while(Main.map[prizeNewX][prizeNewY].isWall() || (prizeNewX == ENTRANCE_X/CELL_SIZE && prizeNewY == ENTRANCE_Y/CELL_SIZE));
        //назначаем координаты
        PRIZE_START_X = prizeNewX * CELL_SIZE + 15;
        PRIZE_START_Y = prizeNewY * CELL_SIZE + 15;
    }
}
