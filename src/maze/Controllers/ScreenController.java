/*
 * Обработчик экранов окна
 */
package maze.Controllers;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.util.HashMap;

public class ScreenController {
    private HashMap<String, Pane> screenMap = new HashMap<>();//мапа экранов
    private Scene main;                                       //главный экран

    public ScreenController(Scene main) {
        this.main = main;
    }

    //добавить экран
    public void addScreen(String name, Pane pane){
        screenMap.put(name, pane);
    }

    //удалить экран
    public void removeScreen(String name){
        screenMap.remove(name);
    }

    //активировать экран
    public  void activate(String name){
        main.setRoot(screenMap.get(name));
    }
}
