package maze.lib;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class Cell extends GameObject {
    private boolean isWall = false;     //это Стена?
    private Image frame;                //кадр

    public Cell(int x, int y, int size, int type) {
        //инициализация
        this.entity = new Rectangle(size, size, Color.GREEN);
        this.size = size;

        if(type == 1){
            this.isWall = true;
            this.setAnimation("wall");  //установка "стены"
            getChildren().add(entity);  //представляем стену в виде прямоугольника
        }
        else if(type == 0){
            this.setAnimation("cell");  //установка "поля"
        }
    }

    //Это стена?
    public boolean isWall() {
        return isWall;
    }

    //вернуть кадр
    public Image getFrame() {
        return frame;
    }

    //устанавливаем кадр
    @Override
    public void setAnimation(String nameImage) {
        File image = new File("src/maze/Resources/Pictures/" + nameImage + ".jpg");
        this.frame = new Image(image.toURI().toString(),80, 80, false, false);
    }
}
