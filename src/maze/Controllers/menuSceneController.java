package maze.Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
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

    @FXML
    public void firstButtonClicked(){
        initializeGameObject("first");
        isMenu = false;
        isGame = true;
    }
    @FXML
    public void secondButtonClicked(){
        initializeGameObject("second");
        isMenu = false;
        isGame = true;
    }

    private static void initializeGameObject(String lvlName){
        map = getMap(lvlName);
        player = new Player(ENTRANCE_X, ENTRANCE_Y, PLAYER_SIZE);
        setStartPrizeLocation();
        coin = new Prize(PRIZE_START_X, PRIZE_START_Y, PRIZE_SIZE);
        //добавляем в иерархию
        gameRoot.getChildren().add(coin);
        gameRoot.getChildren().add(player);            //добавляем игрока в узел
    }

    //получить карта определенного уровня
    private static Cell[][] getMap(String nameFile){
        Cell[][] map = new Cell[MAP_SIZE][MAP_SIZE];
        File fileMap = new File("src/maze/Resources/Map/"+ nameFile + "Lvl.txt");
        Scanner scanner = null;
        try {
            FileReader fileReader = new FileReader(fileMap);
            scanner = new Scanner(fileMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int typeCell = 0;
        for(int i = 0; i < MAP_SIZE; i++){
            for(int j = 0; j < MAP_SIZE; j++){
                if(scanner != null && scanner.hasNext()){
                    typeCell = scanner.nextInt();
                    //System.out.println(typeCell);
                }
                map[i][j] = new Cell(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, typeCell);
                map[i][j].setTranslateX(i * CELL_SIZE);
                map[i][j].setTranslateY(j * CELL_SIZE);

                gameRoot.getChildren().add(map[i][j]); //добавляем игровую клетку в узел
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
            //System.out.println(ENTRANCE_X/CELL_SIZE + " " + ENTRANCE_Y/CELL_SIZE);
        } while(Main.map[prizeNewX][prizeNewY].isWall() || (prizeNewX == ENTRANCE_X/CELL_SIZE && prizeNewY == ENTRANCE_Y/CELL_SIZE));
        PRIZE_START_X = prizeNewX * CELL_SIZE + 15;
        PRIZE_START_Y = prizeNewY * CELL_SIZE + 15;
    }
}
