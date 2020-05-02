package maze.lib;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public abstract class GameObject extends Pane {
    protected Rectangle entity; //представление объекта
    protected int size;

    //abstract methods
    protected abstract void setAnimation(String nameImage);//установить анимацию
}
