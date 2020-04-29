/*
 * Абстрактный класс, отвечающий за расположение
 */
package maze.lib;

import javafx.scene.image.Image;

public abstract class GameObject {
    //координаты объекта
    protected int x;
    protected int y;

    //getters and setters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    //abstract method
    public abstract Image getFrame();                       //вернуть кадр
    public abstract void setAnimation(String fileName);     //установить анимацию
}
